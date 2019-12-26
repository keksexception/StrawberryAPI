package de.raffi.strawberry.setting;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

public class BooleanSetting extends Setting{

	public BooleanSetting(FileConfiguration cfg, File file, String path) {
		super(cfg, file, path);
	}

	@Override
	public Object get() {
		return isToggled();
	}
	public boolean getBoolean() {
		return (boolean) get();
	}
	public void set(boolean set) {
		cfg.set(getPath(), set);
		save();
	}
	public boolean isToggled() {
		if(cfg.isSet(getPath()))
			return cfg.getBoolean(getPath());
		else 
			return true;	
	}
	public void enable() {
		cfg.set(getPath(), true);	
		save();
	}
	public void disable() {
		cfg.set(getPath(), false);
		save();
	}
	/**
	 * 
	 * @return
	 * 
	 * if true: §aon
	 * if false: §coff
	 */
	public String getState() {
		return isToggled() ? "§aOn" : "§cOff";
	}
	public void toggle() {
		if(isToggled())
			cfg.set(getPath(), false);
		else 
			cfg.set(getPath(), true);		
		save();
	}

	@Override
	@Deprecated
	public void set(Object set) {}
}
