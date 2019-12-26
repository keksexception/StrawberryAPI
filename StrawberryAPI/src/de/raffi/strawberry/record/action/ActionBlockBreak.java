package de.raffi.strawberry.record.action;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ActionBlockBreak extends RecordAction{

	public ActionBlockBreak(int tick, Object... o) {
		super(tick, o);
		this.b = (Block) o[0];
	}
	Block b;
	@SuppressWarnings("deprecation")
	@Override
	public void execute(Player p) {
		p.sendBlockChange(b.getLocation(), Material.AIR, (byte)0);
		
	}

}
