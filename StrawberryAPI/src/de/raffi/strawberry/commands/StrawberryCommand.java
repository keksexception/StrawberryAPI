package de.raffi.strawberry.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.raffi.strawberry.StrawberryAPI;

public class StrawberryCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 0 || !StrawberryAPI.getInstance().recorderEnabled()) {
			send(sender);
		} else if(args.length == 2) {
			Player p = (Player) sender;
			if(p.hasPermission("strawberryapi.recorder." + args[0])) {
				Player target = Bukkit.getPlayer(args[1]);
				if(target == null) return false;
				if(args[0].equalsIgnoreCase("toggle")) {
					StrawberryAPI.getInstance().recorder().toggleRecord(target);
					p.sendMessage("§6Record toggled");
				}
				if(args[0].equalsIgnoreCase("play")) {
					StrawberryAPI.getInstance().recorder().playRecord(target, p);
					p.sendMessage("§6now playing record");
				}
				
			} else p.sendMessage("§cNo permission: strawberryapi.recorder." + args[0]);
			
		}
		
		return false;
	}
	void send(CommandSender sender) {
		StrawberryAPI.sendURLMessage((Player) sender, "§cThis Server is using the §dStrawberryAPI §cby KeksException", "https://www.spigotmc.org/resources/authors/keksexception.598824/", "§6View resources");
	}
}
