package descent.item;

import descent.Player;

public abstract class Item {

	protected String name;
	protected String description;
	protected Rarity rarity;

	public enum Rarity {
		CONSUMABLE, COMMON, UNCOMMON, RARE, LEGENDARY
	}

	public Item(String name, String description, Rarity rarity) {
		this.name = name;
		this.description = description;
		this.rarity = rarity;
	}

	
	
	public abstract void use(Player player);
	
	public abstract void unequip(Player player);
	
	public String getName() { return name; }
	public String getDescription() { return description; }
	public Rarity getRarity() { return rarity; }
	
	
	
}