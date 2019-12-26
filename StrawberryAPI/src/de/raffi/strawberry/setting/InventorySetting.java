package de.raffi.strawberry.setting;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventorySetting extends Setting {

	public InventorySetting(FileConfiguration cfg, File file, String path) {
		super(cfg, file, path);
	}

	@Override
	@Deprecated
	/**
	 * please use .getInv();
	 */
	public Object get() {
		return getInv();
	}
	/**
	 * 
	 * @return saved inv
	 */ 
	public Inventory getInv() {
		Inventory inv = Bukkit.createInventory(null, getSize(), getTitle());
		for(int i = 0; i < getSize(); i++) {
			inv.setItem(i, cfg.getItemStack(getPath() + "." + i));				
		}
		return inv;
	}
	/**
	 * 
	 * @return inventory size
	 */
	public int getSize() {
		return cfg.getInt(getPath() + ".size");
	}
	/**
	 * 
	 * @return inventory title
	 */
	public String getTitle() {
		return cfg.getString(getPath() + ".title");
	}
	/**
	 * 
	 * @return
	 * 
	 * 0 head
	 * 1 chestplate
	 * 2 leggins
	 * 3 boots
	 */
	public ItemStack[] getArmor() {
		ItemStack[] item = new ItemStack[]{cfg.getItemStack(getPath() + ".helmet"),cfg.getItemStack(getPath() + ".chest"),cfg.getItemStack(getPath() + ".leggins"), cfg.getItemStack(getPath() + ".boots")};
		return item;
	}
	/**
	 * @see getArmor()
	 * @param i
	 * @return armor in slot range 0-3
	 * 
	 * 0 head
	 * 1 chestplate
	 * 2 leggins
	 * 3 boots
	 */
	public ItemStack getArmor(int i) {
		return getArmor()[i];
	}
	/**
	 * 
	 * @param inv 
	 * @return this
	 * 
	 * automatic call .save();
	 */
	public InventorySetting set(Inventory inv) {
		cfg.set(getPath() + ".size", inv.getSize());
		cfg.set(getPath() + ".title", inv.getTitle());
		for(int i = 0; i < inv.getSize(); i++) {
			cfg.set(getPath() + "." + i, inv.getItem(i));
		}
		save();
		return this;
	}
	public InventorySetting setArmor(ItemStack helmet, ItemStack chestplate, ItemStack leggins, ItemStack boots) {
		cfg.set(getPath() + ".helmet", helmet);
		cfg.set(getPath() + ".chest", chestplate);
		cfg.set(getPath() + ".leggins", leggins);
		cfg.set(getPath() + ".boots", boots);
		save();
		return this;
	}
	/**
	 * 
	 * @param contents  [0] helmet, [1] chestplate, [2] leggings, [3] boots
	 * @return
	 * @see .setArmor()
	 */
	public InventorySetting setArmor(ItemStack[] contents) {
		setArmor(contents[0], contents[1], contents[2], contents[3]);
		return this;
	}
	/**
	 * 
	 * @return true, if armor is saved to the config
	 * 
	 * 
	 */
	public boolean hasArmor() {
		return cfg.isSet(getPath() + ".helmet") || cfg.isSet(getPath() + ".chest") || cfg.isSet(getPath() + ".leggins") || cfg.isSet(getPath() + ".boots");
	}
	/**
	 * 
	 * @param p player
	 * 
	 * set the saved inventory to the player
	 */
	public void applyInv(Player p) {
		for(int i = 0; i < getSize(); i++) {
			p.getInventory().setItem(i, cfg.getItemStack(getPath() + "." + i));				
		}
	}
	/**
	 * 
	 * @param p player
	 * 
	 * set the saved inventory  and the armor to the player
	 */
	public void applyAll(Player p) {
		applyInv(p);
		applyArmor(p);
	}
	/**
	 * 
	 * @param p player
	 * 
	 * set the armor from the config to the player
	 */
	public void applyArmor(Player p) {
		p.getInventory().setArmorContents(getArmor());
	}
	
	@Override
	@Deprecated
	/**
	 * please use .set(Inventory inv);
	 */
	public void set(Object set) {}
	
}
