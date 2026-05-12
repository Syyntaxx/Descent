package descent.item;

import descent.Player;

public abstract class Equipment extends Item {
	
	private Slot slot;
	
	private int strengthBonus;
	private int agilityBonus;
	private int vitalityBonus;
	private int intelligenceBonus;

	public Equipment(String name, String description, Rarity rarity, Slot slot, int str, int agl, int vit, int intl) {
		super(name, description, rarity);
		this.slot = slot;
		
		this.strengthBonus = str;
		this.agilityBonus = agl;
		this.vitalityBonus = vit;
	    this.intelligenceBonus = intl;
	}
	 	
	public enum Slot {
		HEAD,
		TORSO,
		BOOTS
	}

	public void use(Player player) {
		equip(player);
	}
	
	
	
	public int getStrengthBonus() { return strengthBonus; }
	public int getAgilityBonus() { return agilityBonus; }
	public int getVitalityBonus() { return vitalityBonus; }
	public int getIntelligenceBonus() { return intelligenceBonus; }	
	
	public Slot getSlot() { return slot; }

	public abstract void equip(Player player);

	@Override
	public abstract void unequip(Player player);
}