package de.raffi.strawberry.setting;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import de.raffi.strawberry.StrawberryAPI;

public class StringSetting extends Setting{

	public StringSetting(FileConfiguration cfg, File file, String path) {
		super(cfg, file, path);
	}

	@Override
	public Object get() {
		return cfg.getString(getPath());
	}
	public String getString() {
		return (String) get();
	}
	@Override
	public void set(Object set) {
		cfg.set(getPath(), set);
		save();
	}
	public void set(String set) {
		set((Object)set);
	}
	/**
	 * 
	 * @return
	 * 
	 * @since 2.1
	 */
	public Location toLocation() {
		return StrawberryAPI.locationFromString(getString());
	}
	/**
	 * 
	 * @param loc
	 * 
	 * @since 2.1
	 */
	public void saveAsLocation(Location loc) {
		set(StrawberryAPI.locationToString(loc));
	}
}
