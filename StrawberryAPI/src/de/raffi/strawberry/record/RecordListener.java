package de.raffi.strawberry.record;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import de.raffi.strawberry.StrawberryAPI;
import de.raffi.strawberry.record.action.ActionBlockBreak;
import de.raffi.strawberry.record.action.ActionBlockPlace;
import de.raffi.strawberry.record.action.ActionChangePosition;
import de.raffi.strawberry.record.action.ActionDamage;

public class RecordListener implements Listener{
	
	private Recorder recorder = StrawberryAPI.getInstance().recorder();
	private boolean on = StrawberryAPI.getInstance().recorderEnabled();
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if(!on) return;
		if(recorder.isRecording(e.getPlayer())) {
			recorder.addAction(e.getPlayer(), new ActionChangePosition(recorder.currentTick(e.getPlayer()), e.getPlayer().getLocation()));
		}
	}
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if(!on) return;
		if(recorder.isRecording(e.getPlayer())) {
			recorder.destroyBlock(e.getPlayer(), e.getBlock());
			recorder.addAction(e.getPlayer(), new ActionBlockBreak(recorder.currentTick(e.getPlayer()), e.getBlock()));
		}		
	}
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if(!on) return;
		if(recorder.isRecording(e.getPlayer())) {
			recorder.placeBlock(e.getPlayer(), e.getBlockPlaced());
			recorder.addAction(e.getPlayer(), new ActionBlockPlace(recorder.currentTick(e.getPlayer()), e.getBlockPlaced()));
		}	
	}
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(!on) return;
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if(recorder.isRecording(p)) {
				recorder.addAction(p, new ActionDamage(recorder.currentTick(p), p.getHealth(), e.getDamage()));
			}
		}
	}
}
