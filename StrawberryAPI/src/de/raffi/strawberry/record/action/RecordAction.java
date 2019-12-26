package de.raffi.strawberry.record.action;

import org.bukkit.entity.Player;

public abstract class RecordAction {
	
	
	private int tick;
	private Object[] o;
	public RecordAction(int tick, Object... o) {
		this.tick = tick;
		this.o = o;
	}
	public Object[] o() {
		return o;
	}
	public abstract void execute(Player p);
	public int getTick() {
		return tick;
	}

}