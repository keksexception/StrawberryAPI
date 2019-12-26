package de.raffi.strawberry.setting;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

public class ObjectSetting extends Setting{

	public ObjectSetting(FileConfiguration cfg, File file, String path) {
		super(cfg, file, path);
	}

	@Override
	public Object get() {
		return cfg.get(getPath());
	}

	@Override
	public void set(Object set) {
		cfg.set(getPath(), set);
		save();
	}

}
