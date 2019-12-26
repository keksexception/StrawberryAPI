package de.raffi.strawberry.setting;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;

public class ServerSetting {
	
	private File file = new File("plugins/StrawberryAPI", "serversettings.yml");
	private FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
	
	private String setting;
	private Object standard;
	
	public ServerSetting(String setting, Object standard) {
		this.setting = setting;
		this.standard = standard;
	}
	public ServerSetting(String pluginName, String setting, Object standard) {
		this.setting = pluginName + "." + setting;
		this.standard = standard;
	}
	public String getSetting() {
		return setting;
	}
	public BooleanSetting bool() {
		if(!cfg.isSet(setting)) {
			cfg.set(getSetting(), getStandard());
			try {cfg.save(file);} catch (IOException e) {}
		}
		return new BooleanSetting(cfg, file, setting);
	}
	public IntegerSetting integer() {
		if(!cfg.isSet(setting)) {
			cfg.set(getSetting(), getStandard());
			try {cfg.save(file);} catch (IOException e) {}
		}
		return new IntegerSetting(cfg, file, setting);
	}
	public StringSetting string() {
		if(!cfg.isSet(setting)) {
			cfg.set(getSetting(), getStandard());
			try {cfg.save(file);} catch (IOException e) {}
		}
		return new StringSetting(cfg, file, setting);
	}
	public InventorySetting inventory() {
		if(!cfg.isSet(setting)) {
			cfg.set(getSetting(), new InventorySetting(cfg, file, setting).set((Inventory)getStandard()));
			try {cfg.save(file);} catch (IOException e) {}
		}
		return new InventorySetting(cfg, file, setting);
	}
	public ListSetting list() {
		if(!cfg.isSet(setting)) {
			cfg.set(getSetting(), getStandard());
			try {cfg.save(file);} catch (IOException e) {}
		}
		return new ListSetting(cfg, file, setting);
	}
	public ServerSetting changeConfig(File file) {
		this.file = file;
		this.cfg = YamlConfiguration.loadConfiguration(file);
		return this;
	}
	public Object getStandard() {
		return standard;
	}
	
}
