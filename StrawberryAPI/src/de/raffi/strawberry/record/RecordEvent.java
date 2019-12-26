package de.raffi.strawberry.record;

import org.bukkit.entity.Player;

public abstract class RecordEvent {
	
	
	public abstract void handleRecordStart(Player recorded);
	public abstract void handleRecordStop(Player recorded);
	
	public abstract void handleRecordPlayON(Player recorded, Player recorder);
	public abstract void handleRecordPlayOFF(Player recorded, Player recorder);

}
