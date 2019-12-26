package de.raffi.strawberry;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import de.raffi.strawberry.commands.StrawberryCommand;
import de.raffi.strawberry.record.RecordListener;
import de.raffi.strawberry.record.Recorder;
import de.raffi.strawberry.server.ServerManager;
import de.raffi.strawberry.setting.InventorySetting;
import de.raffi.strawberry.setting.SuperSetting;
import de.raffi.strawberry.test.Test;
import de.raffi.strawberry.utils.PacketAPI;
import de.raffi.strawberry.utils.ParticleSystem;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class StrawberryAPI extends JavaPlugin{
	
	
	private static PacketAPI packetAPI;
	private static StrawberryAPI strawberryapi;
	private static ServerManager serverManager;
	private Recorder recorder;
	@Override
	public void onEnable() {
		strawberryapi = this;
		packetAPI = new PacketAPI();
		serverManager = new ServerManager();
		
		getCommand("strawberry").setExecutor(new StrawberryCommand());
		
		Bukkit.getPluginManager().registerEvents(new RecordListener(), this);
	}
	public Recorder recorder() {
		return recorder;
	}
	public Recorder enableRecorder() {
		recorder = new Recorder();
		return recorder;
	}
	public boolean recorderEnabled() {
		return recorder != null;
	}
	public static StrawberryAPI getInstance() {
		return strawberryapi;
	}
	public static ServerManager getServerManager() {
		return serverManager;
	}
	public static ParticleSystem getParticleSystem() {
		return new ParticleSystem();
	}
	/**
	 * 
	 * @return PacketAPI by KeksException
	 */
	public static PacketAPI getPacketAPI() {
		return packetAPI;
	}
	public static IChatBaseComponent toIChatBaseComponent(String message) {
		return ChatSerializer.a("{\"text\": \"" + message + "\"}");
	}

	public static void sendClickableMessage(Player p, String message, String clickMessage, String command) {
		IChatBaseComponent comp = ChatSerializer.a("{\"text\":\"" + message + " " + "\",\"extra\":[{\"text\":\""
				+ clickMessage + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + command + "\"}}]}");
		getPacketAPI().sendPacket((CraftPlayer) p, new PacketPlayOutChat(comp));
	}

	public static void sendClickableHoverMessage(Player p, String text, String command, String hover, String color) {
		IChatBaseComponent comp = ChatSerializer.a(
				"{\"text\":\"" + text + "\",\"color\":\"" + color + "\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" +command+"\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + hover + "\"}]}}}");
		getPacketAPI().sendPacket((CraftPlayer) p, new PacketPlayOutChat(comp));
	}
	public static void sendCommandSuggestion(Player p, String text, String command, String hover) {
		sendJSON(p, "{\"text\":\"" + text + "\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"" + command + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + hover + "\"}]}}}");
	}
	public static void sendURLMessage(Player p, String text, String url, String hover) {
		IChatBaseComponent comp = ChatSerializer.a("{\"text\":\"" + text + "\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"" + url + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + hover + "\"}]}}}");
		getPacketAPI().sendPacket((CraftPlayer) p, new PacketPlayOutChat(comp));
	}
	public static void sendJSON(Player p, String json) {
		IChatBaseComponent comp = ChatSerializer.a(json);
		getPacketAPI().sendPacket((CraftPlayer) p, new PacketPlayOutChat(comp));
	}
	public static void sendYesNoChoice(Player p, String yesTxt, String noTxt, String yesCmd, String noCmd) {
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + p.getName() + " [\"\",{\"text\":\"" + yesTxt +"\",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + yesCmd + "\"}},{\"text\":\"" + noTxt + "\",\"color\":\"dark_red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + noCmd + "\"}}]");
	}
	public static void sendYesNoChoice(Player p, String yesTxt, String noTxt, String yesCmd, String noCmd, String yesHover, String noHover) {
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + p.getName() + " [\"\",{\"text\":\"" + yesTxt + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + yesCmd + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + yesHover + "\"}]}}},{\"text\":\"" + noTxt + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + noCmd + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + noHover + "\"}]}}}]");
	}
	public static void dispatchConsoleCommand(String command) {
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
	}
	public static void sendActionbar(Player p, String message) {
		message = message.replace("_", "");
		IChatBaseComponent ibc = ChatSerializer.a("{\"text\": \"" + message + "\"}");
		getPacketAPI().sendPacket((CraftPlayer) p, new PacketPlayOutChat(ibc, (byte) 2));
	}
	/**
	 * 
	 * @return server plugins
	 */
	public Plugin[] getPlugins() {
		return getServer().getPluginManager().getPlugins();
	}
	/**
	 * 
	 * @param uuid
	 * @return saved inventory from config
	 */
	public InventorySetting inventory(UUID uuid) {
		return new SuperSetting(uuid, "inventory").inventory();
	}
	/**
	 * 
	 * @param face
	 * @return convert blockface to yaw; 360 if unknown
	 */
	public static int toYaw(BlockFace face) {
		if(face == BlockFace.EAST) return 270;
		if(face == BlockFace.NORTH) return 180;
		if(face == BlockFace.SOUTH) return 0;
		if(face == BlockFace.WEST) return 90;
		
		if(face == BlockFace.NORTH_EAST) return 225;
		if(face == BlockFace.NORTH_WEST) return 135; 
		if(face == BlockFace.SOUTH_WEST) return 45;
		if(face == BlockFace.SOUTH_EAST) return 315;
		return 360;
	}
	/**
	 * 
	 * @param pluginName
	 * @return
	 * 
	 * 		unload a plugin
	 */
	public boolean unloadPlugin(String pluginName) {
		try {
			getServer().getPluginManager().disablePlugin(getServer().getPluginManager().getPlugin(pluginName));
			return true;
		} catch (Exception e) {
			try {
				throw new Exception(new Throwable("Can not unload plugin " + pluginName));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return false;
		}

	}
	/**
	 * 
	 * @param pluginName
	 * @return
	 * 
	 * 		load a plugin
	 */
	public boolean loadPlugin(String pluginName) {
		try {
			getServer().getPluginManager().enablePlugin(getServer().getPluginManager().getPlugin(pluginName));
			return true;
		} catch (Exception e) {
			try {
				throw new Exception(new Throwable("Can not load plugin " + pluginName));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return false;
		}
	}
	
	/**
	 * 
	 * @param pluginName
	 * @return plugin state
	 */
	public boolean isPluginLoaded(String pluginName) {
		try {
			return getServer().getPluginManager().isPluginEnabled(pluginName);
		} catch (Exception e) {
			try {
				throw new Exception(new Throwable("The plugin " + pluginName + " does not exist"));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return false;
		}
	}
	public static String locationToString(Location loc) {
		return loc.getWorld().getName() + ";" + loc.getX() + ";" + loc.getY() + ";" + loc.getZ() + ";" + loc.getYaw() + ";" + loc.getPitch();
	}
	public static Location locationFromString(String s) {
		String[] s2 = s.split(";");
		World world = Bukkit.getWorld(s2[0]);
		double x = Double.valueOf(s2[1]);
		double y = Double.valueOf(s2[2]);
		double z = Double.valueOf(s2[3]);		
		float yaw = Float.valueOf(s2[4]);
		float pitch = Float.valueOf(s2[5]);
		return new Location(world, x, y, z, yaw, pitch);
	}
	/**
	 * 
	 * @return 
	 * most things does not work or are very buggy
	 * 
	 */
	public Test getStupidThingsITested() {
		return new Test();
	}
}
