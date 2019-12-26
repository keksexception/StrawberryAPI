package de.raffi.strawberry.record.action;

import org.bukkit.entity.Player;

public class ActionDamage extends RecordAction{
	
	private double health;
	private double damage;

	public ActionDamage(int tick, Object... o) {
		super(tick, o);
		health = (double) o[0];
		damage = (double) o[1];
	}
	
	@Override
	public void execute(Player p) {
		p.damage(damage);
		p.setHealth(health);
	}

}
