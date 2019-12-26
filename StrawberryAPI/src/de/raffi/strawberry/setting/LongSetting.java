package de.raffi.strawberry.setting;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

public class LongSetting extends Setting{

	public LongSetting(FileConfiguration cfg, File file, String path) {
		super(cfg, file, path);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object get() {
		// TODO Auto-generated method stub
		return getLong();
	}

	@Override
	public void set(Object set) {
		setLong((long) set);
		
	}
	public long getLong() {
		return cfg.getLong(getPath());
	}
	public void setLong(long l) {
		cfg.set(getPath(), l);
		save();
	}

}
