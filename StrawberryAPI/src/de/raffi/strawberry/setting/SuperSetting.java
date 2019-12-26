package de.raffi.strawberry.setting;

import java.io.File;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class SuperSetting {
	
	
	private File file = new File("plugins/StrawberryAPI", "supersettings.yml");
	private FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
	
	private UUID uuid;
	private String setting;
	
	public SuperSetting(UUID uuid, String setting) {
		this.uuid = uuid;
		this.setting = setting;
	}
	public SuperSetting(Player p, String setting) {
		this.uuid = p.getUniqueId();
		this.setting = setting;
	}
	public String getSetting() {
		return setting;
	}
	public BooleanSetting bool() {
		return new BooleanSetting(cfg, file, uuid.toString() + "." + setting);
	}
	public IntegerSetting integer() {
		return new IntegerSetting(cfg, file, uuid.toString() + "." + setting);
	}
	public StringSetting string() {
		return new StringSetting(cfg, file, uuid.toString() + "." + setting);
	}
	public InventorySetting inventory() {
		return new InventorySetting(cfg, file, uuid.toString() + "." + setting);
	}
	public ListSetting list() {
		return new ListSetting(cfg, file, uuid.toString() + "." + setting);
	}
	public LongSetting longSetting() {
		return new LongSetting(cfg, file, uuid.toString() + "." + setting);
	}
	public Object object() {
		return new ObjectSetting(cfg, file, uuid.toString() + "." + setting);
	}
	public SuperSetting changeConfig(File file) {
		this.file = file;
		this.cfg = YamlConfiguration.loadConfiguration(file);
		return this;
	}
	

	

	

}
