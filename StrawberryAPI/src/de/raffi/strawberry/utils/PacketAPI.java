package de.raffi.strawberry.utils;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.WorldServer;

public class PacketAPI {
	
	public PacketAPI() {
	}
	
	public void sendPacket(CraftPlayer p, @SuppressWarnings("rawtypes") net.minecraft.server.v1_8_R3.Packet packet) {
		p.getHandle().playerConnection.sendPacket(packet);
	}
	public void sendPacketToAll(@SuppressWarnings("rawtypes") Packet packet) {
		for(Player all : Bukkit.getOnlinePlayers()) {
			sendPacket((CraftPlayer) all, packet);
		}
	}
	/**
	 * 
	 * @param p
	 * @param loc
	 * @param text
	 * @param small
	 * @param gravity
	 * @return id to destroy
	 */
	public int sendArmorStand(Player p, Location loc,String text, boolean small, boolean gravity) {
		WorldServer s = ((CraftWorld)p.getWorld()).getHandle();
        EntityArmorStand stand = new EntityArmorStand(s);
        stand.setLocation(loc.getX(), loc.getY(), loc.getZ(), 0, 0);
        stand.setCustomName(text);
        stand.setCustomNameVisible(true);
        stand.setGravity(gravity);
        stand.setSmall(small);
        stand.setInvisible(true);
        sendPacket((CraftPlayer) p, new PacketPlayOutSpawnEntityLiving(stand));
        return stand.getId();
	}
	/**
	 * 
	 * @param p
	 * @param id entityID
	 */
	public void destroyEntity(Player p, int id) {
		sendPacket((CraftPlayer) p, new PacketPlayOutEntityDestroy(id));
	}
	
}
