package de.raffi.strawberry.record.action;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ActionChangePosition extends RecordAction{
	
	
	public ActionChangePosition(int tick, Object[] o) {
		super(tick, o);
		loc = (Location) o[0];
	}


	public ActionChangePosition(int currentTick, Location location) {
		super(currentTick, location);
		loc = location;
	}


	private Location loc;
	

	@Override
	public void execute(Player p) {
		p.setAllowFlight(true);
		p.setFlying(true);
		p.teleport(loc);
	}

	

	

}
