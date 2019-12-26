package de.raffi.strawberry.setting;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

public class ListSetting extends Setting {

	public ListSetting(FileConfiguration cfg, File file, String path) {
		super(cfg, file, path);
	}

	@Override
	public Object get() {
		if(cfg.getList(getPath()) == null)
			return new ArrayList<>();
		else
			return cfg.getList(getPath());
	}

	@Override
	@Deprecated
	public void set(Object set) {}
	public ListSetting add(String s) {
		@SuppressWarnings("unchecked")
		List<String> l = (List<String>) get();
		l.add(s);
		cfg.set(getPath(), l);
		save();
		return this;
	}
	public ListSetting remove(String s) {
		@SuppressWarnings("unchecked")
		List<String> l = (List<String>) get();
		l.remove(s);
		cfg.set(getPath(), l);
		save();
		return this;
	}
	
}
