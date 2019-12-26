package de.raffi.strawberry.setting;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;

public abstract class Setting {
	
	public FileConfiguration cfg;
	private File file;
	private String path;
	
	public Setting(FileConfiguration cfg, File file, String path) {
		this.cfg = cfg;
		this.file = file;
		this.path = path;
	}
	public abstract Object get();
	public abstract void set(Object set);
	public void save() {try {cfg.save(file);} catch (IOException e) {}}
	public String getPath() {return path;}
	public boolean exist() {
		return cfg.isSet(getPath());
	}
	public void delete() {
		cfg.set(getPath(), null);
		save();
	}
	
}
