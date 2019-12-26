package de.raffi.strawberry.record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import de.raffi.strawberry.StrawberryAPI;
import de.raffi.strawberry.record.action.ActionStop;
import de.raffi.strawberry.record.action.RecordAction;

public class Recorder {
	private List<Player> record;
	public List<Player> recordPlay;
	private HashMap<Player, List<RecordAction>> hash;
	private HashMap<Player, Integer> tick;
	private HashMap<Player, Player> spectator;
	private HashMap<Player, Integer> spectatorTick;
	private HashMap<Player, List<Block>> blocksDestroyed;
	private HashMap<Player, List<Block>> blocksPlaced;
	public List<Player> stop;
	private List<RecordEvent> event;
	
	public Recorder() {
		record = new ArrayList<>();
		recordPlay = new ArrayList<>();
		event = new ArrayList<>();
		stop = new ArrayList<>();
		
		hash = new HashMap<>();
		tick = new HashMap<>();
		spectator = new HashMap<>();
		spectatorTick = new HashMap<>();
		blocksDestroyed = new HashMap<>();
		blocksPlaced = new HashMap<>();
		
		runTick();
	}
	public List<Block> blocksDestroyed(Player p) {
		return blocksDestroyed.get(p);
	}
	public List<Block> blocksPlaced(Player p) {
		return blocksPlaced.get(p);
	}
	public void destroyBlock(Player p, Block b) {
		List<Block> l = blocksDestroyed(p);
		l.add(b);
		blocksDestroyed.put(p, l);
	}
	public void placeBlock(Player p, Block b) {
		List<Block> l = blocksPlaced(p);
		l.add(b);
		blocksPlaced.put(p, l);
	}
	public void registerRecordEvent(RecordEvent event) {
		this.event.add(event);
	}
	public List<RecordAction> getFor(Player p) {
		return hash.get(p);
	}
	public void addAction(Player p, RecordAction rec) {
		List<RecordAction> l = getFor(p);
		l.add(rec);
		hash.put(p, l);
	}
	public int currentTick(Player p) {
		return tick.get(p);
	}
	@SuppressWarnings("deprecation")
	public void playRecord(Player rec, Player play) {
		if(!recordPlay.contains(play))
			recordPlay.add(play);
		spectatorTick.put(play, 0);
		spectator.put(play, rec);		
		play.sendMessage("§eRecord took §c" + (currentTick(rec) / 20) + " §eseconds.");
		tick.put(rec, 0);	
		for(RecordEvent e : event)
			e.handleRecordPlayON(rec, play);
		
		for(Block breaked : blocksDestroyed(rec)) {
			play.sendBlockChange(breaked.getLocation(), Material.FURNACE, (byte) 0);
		}
		for(Block placed : blocksPlaced(rec)) {
			play.sendBlockChange(placed.getLocation(), Material.AIR, (byte)0);
		}
	}
	public Player getSpectated(Player p) {
		return spectator.get(p);
	}
	public int getSpectatorTick(Player p) {
		return spectatorTick.get(p);
	}
	public void toggleRecord(Player p) {
		if(isRecording(p)) {
			addAction(p, new ActionStop(currentTick(p), null));
			if(record.contains(p))
				record.remove(p); 
			for(RecordEvent e : event)
				e.handleRecordStop(p);
		}else record.add(p);
		
		if(isRecording(p)) {
			hash.put(p, new ArrayList<>());
			blocksDestroyed.put(p, new ArrayList<>());
			blocksPlaced.put(p, new ArrayList<>());
			tick.put(p, 0);
			for(RecordEvent e : event)
				e.handleRecordStart(p);
		} 
	}
	public boolean isRecording(Player p) {
		return record.contains(p);
	}
	public void runTick() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(StrawberryAPI.getInstance(), new Runnable() {
			public void run() {
				for(Player s : recordPlay) {
					Player get = getSpectated(s);
					StrawberryAPI.sendActionbar(s, "§c" + get.getName() + "§a " + getSpectatorTick(s) /20);
					for(RecordAction ra : getFor(get)) {
						if(ra.getTick() == getSpectatorTick(s)) {
							ra.execute(s);
						}
					}
					spectatorTick.put(s, getSpectatorTick(s)+1);
				}
				for(Player x : record) {
					tick.put(x, currentTick(x)+1);
				}
				for(Player rm : stop) {
					if(recordPlay.contains(rm))
						recordPlay.remove(rm);
					for(RecordEvent e : event)
						e.handleRecordPlayOFF(getSpectated(rm), rm);
				}
			}
		}, 1, 1);
	}
	

}
