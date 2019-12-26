package de.raffi.strawberry.test;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

import de.raffi.strawberry.StrawberryAPI;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockBreakAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.PlayerInteractManager;
import net.minecraft.server.v1_8_R3.WorldServer;

public class Test {
	public HashMap<Block, Integer> destroyLevel = new HashMap<>();
	public HashMap<Block, Integer> entityID = new HashMap<>();
	
	public void damageBlock(Block b) {		
		if(destroyLevel.get(b) == null)
			destroyLevel.put(b, 0);
		if(entityID.get(b) == null)
			entityID.put(b, new Random().nextInt());
		int d = destroyLevel.get(b);
		//if(d < 10)
			StrawberryAPI.getPacketAPI().sendPacketToAll(new PacketPlayOutBlockBreakAnimation(entityID.get(b), new BlockPosition(b.getX(), b.getY(), b.getZ()), destroyLevel.get(b)));
		if(d < 10)
			d++;
		else 
			d--;
		destroyLevel.put(b, d);
		
	}
	/**
	 * 
	 * @param player location
	 * @param displayname
	 * 
	 *                    does not really work
	 * @deprecated
	 */
	public void spawnFakePlayer(Player player, String displayname) {
		MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
		WorldServer world = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle();

		Player target = Bukkit.getServer().getPlayer(displayname);
		EntityPlayer npc;
		if (target != null) {
			npc = new EntityPlayer(server, world, new GameProfile(target.getUniqueId(), target.getName()),
					new PlayerInteractManager(world));
		} else {
			OfflinePlayer op = Bukkit.getServer().getOfflinePlayer(displayname);
			npc = new EntityPlayer(server, world, new GameProfile(op.getUniqueId(), displayname),
					new PlayerInteractManager(world));
		}
		Location loc = player.getLocation();
		npc.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());

		for (Player all : Bukkit.getOnlinePlayers()) {
			PlayerConnection connection = ((CraftPlayer) all).getHandle().playerConnection;
			connection.sendPacket(
					new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
			connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
		}
	}

	/**
	 * 
	 * @param loc
	 * @param displayname
	 * 
	 *                    does not really work
	 * @deprecated
	 */
	public void spawnFakePlayer(Location loc, String displayname) {
		MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
		WorldServer world = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle();

		Player target = Bukkit.getServer().getPlayer(displayname);
		EntityPlayer npc;
		if (target != null) {
			npc = new EntityPlayer(server, world, new GameProfile(target.getUniqueId(), target.getName()),
					new PlayerInteractManager(world));
		} else {
			OfflinePlayer op = Bukkit.getServer().getOfflinePlayer(displayname);
			npc = new EntityPlayer(server, world, new GameProfile(op.getUniqueId(), displayname),
					new PlayerInteractManager(world));

		}
		npc.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());

		for (Player all : Bukkit.getOnlinePlayers()) {
			PlayerConnection connection = ((CraftPlayer) all).getHandle().playerConnection;
			connection.sendPacket(
					new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
			connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
		}
	}
public static class NameTagManager {
		
		private static NameTagManager instance;
		
		public NameTagManager() {
			instance = this;
		}
		/**
		 * 
		 * @return the instance
		 */
		public static NameTagManager getInstance() {
			return instance;
		}
		private HashMap<UUID, String> oldname = new HashMap<>();
		
		/**
		 * 
		 * @param craftplayer
		 * @param name 
		 * 
		 * change the name above head
		 */
		public void setNickName(CraftPlayer craftplayer, String name) {
			oldname.put(craftplayer.getUniqueId(), craftplayer.getName());
			
			GameProfile pprofile = craftplayer.getProfile();
			
			try {
				java.lang.reflect.Field ff = pprofile.getClass().getDeclaredField("name");//don't change here
				ff.setAccessible(true);
				ff.set(pprofile, name);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/**
		 * 
		 * @param p
		 * 
		 * sets the real name
		 */
		public void resetDisplayname(CraftPlayer p) {
			String name = oldname.get(p.getUniqueId());
			setNickName(p, name);
		}
		/**
		 * 
		 * @param p
		 * @return real name of the player
		 */
		public String getOldName(Player p) {
			return oldname.get(p.getUniqueId());
		}
		/**
		 * 
		 * @param pprofile
		 * @param name
		 * 
		 * sets the nickname
		 */
		public void setNickName(GameProfile pprofile, String name) {
			try {
				java.lang.reflect.Field ff = pprofile.getClass().getDeclaredField("name");//don't change here
				ff.setAccessible(true);
				ff.set(pprofile, name);
			} catch (Exception e) {
				e.printStackTrace();	
			}
		}
		/**
		 * 
		 * @param UUID
		 * @param name
		 * 
		 * sets the real name
		 */
		public void setOldName(UUID UUID, String name) {
			Player p = Bukkit.getPlayer(UUID);
			oldname.put(p.getUniqueId(), name);
		}
		/**
		 * 
		 * @param p
		 * @param name
		 * 
		 * sets the realname
		 * 
		 * @see .setOldName(UUID, name)
		 */
		public void setOldName(Player p, String name) {
			oldname.put(p.getUniqueId(), name);
		}
		/**
		 * 
		 * @param UUID
		 * @return real name
		 */
		public String getOldName(UUID UUID) {
			return oldname.get(UUID);
		}
		/**
		 * 
		 * @param p
		 * @return gameprofile
		 */
		public GameProfile getGameProfile(CraftPlayer p) {
			GameProfile profile = p.getProfile();
			return profile;
		}
		/**
		 * 
		 * @param UUID
		 * @return gameprofile
		 */
		public GameProfile getGameProfile(UUID UUID) {
			Player p = Bukkit.getPlayer(UUID);
			GameProfile profile = ((CraftPlayer) p).getProfile();
			return profile;
		}
	}

}
