package de.raffi.strawberry.record.action;

import org.bukkit.entity.Player;

import de.raffi.strawberry.StrawberryAPI;

public class ActionStop extends RecordAction{

	public ActionStop(int tick, Object[] o) {
		super(tick, o);

	}

	@Override
	public void execute(Player p) {
		p.sendMessage("§cRecord Stopped.");
		if(!StrawberryAPI.getInstance().recorder().stop.contains(p))
			StrawberryAPI.getInstance().recorder().stop.add(p);
		
	}

}
