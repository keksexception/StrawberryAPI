package de.raffi.strawberry.setting;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

public class IntegerSetting extends Setting{

	public IntegerSetting(FileConfiguration cfg, File file, String path) {
		super(cfg, file, path);
	}

	@Override
	public Object get() {
		return cfg.getInt(getPath());
	}
	public int getInt() {
		return (int) get();
	}
	public void set(int set) {
		cfg.set(getPath(), set);
		save();
	}
	public void increase() {
		int i = (int) get();
		i++;
		set(i);		
	}
	public void decrease() {
		int i = (int) get();
		i--;
		set(i);		
	}
	public void decrease(int decrease) {
		int i = (int) get();
		i-=decrease;
		set(i);		
	}
	public void increase(int increase) {
		int i = (int) get();
		i+=increase;
		set(i);		
	}

	@Override
	@Deprecated
	public void set(Object set) {}
	
}
