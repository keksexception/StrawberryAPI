package de.raffi.strawberry.record.action;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ActionBlockPlace extends RecordAction{

	public ActionBlockPlace(int tick, Object... o) {
		super(tick, o);
		this.b = (Block) o[0];
	}
	Block b;
	@SuppressWarnings("deprecation")
	@Override
	public void execute(Player p) {
		p.sendBlockChange(b.getLocation(), b.getType(), (byte)0);
		
	}

}
