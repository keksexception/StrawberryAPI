package de.raffi.strawberry.utils;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class ParticleSystem {
	
	public void sendParticle(EnumParticle type, Location loc, float xOffset, float yOffset, float zOffset,
			float speed, int count) {
		float x = (float) loc.getX();
		float y = (float) loc.getY();
		float z = (float) loc.getZ();
		PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(type, true, x, y, z, xOffset, yOffset,
				zOffset, speed, count, null);
		for (Player p : Bukkit.getOnlinePlayers()) {
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);			
			
		}
	}

	public void sendParticle(EnumParticle type, double x, double y, double z, float xOffset, float yOffset,
			float zOffset, float speed, int count) {
		float xf = (float) x;
		float yf = (float) y;
		float zf = (float) y;
		PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(type, true, xf, yf, zf, xOffset, yOffset,
				zOffset, speed, count, null);
		for (Player p : Bukkit.getOnlinePlayers()) {
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		}
	}

	public void sendParticleTo(EnumParticle type, Player p, Location loc, float xOffset, float yOffset,
			float zOffset, float speed, int count) {
		float x = (float) loc.getX();
		float y = (float) loc.getY();
		float z = (float) loc.getZ();
		PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(type, true, x, y, z, xOffset, yOffset,
				zOffset, speed, count, null);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}

	public void sendParticleTo(EnumParticle type, Player p, double x, double y, double z, float xOffset,
			float yOffset, float zOffset, float speed, int count) {
		float xf = (float) x;
		float yf = (float) y;
		float zf = (float) y;
		PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(type, true, xf, yf, zf, xOffset, yOffset,
				zOffset, speed, count, null);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);

	}
	/**
	 * 
	 * @param loc
	 * @param m particle of blockcrack
	 * @param amount
	 * @param multiply
	 * 
	 * play blockcrack effect
	 */
	public void blockCrack(Location loc, Material m, int amount, int multiply) {
		for (int i = 0; i < multiply; i++) {
			loc.getWorld().playEffect(loc, Effect.TILE_BREAK, new MaterialData(m), amount);
			
		}
	}
	/**
	 * 
	 * @param loc
	 * 
	 * play blood effect
	 */
	public void blood(Location loc) {
		for (int i = 0; i < 50; i++) {
			loc.getWorld().playEffect(loc.add(0, 1, 0), Effect.TILE_BREAK, new MaterialData(Material.REDSTONE_BLOCK), 1000);			
		}
	}

}
