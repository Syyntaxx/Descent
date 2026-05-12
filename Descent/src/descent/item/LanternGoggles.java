package descent.item;


import descent.Player;

public class LanternGoggles extends Equipment{

	public LanternGoggles() {
		super("LanternGoggles", "Goggles that provide visibility (+2 intelligence)", Item.Rarity.UNCOMMON, Equipment.Slot.BOOTS,
				0, 0, 0, 0);
		//str, agl, vit, intel
	}
	
	
	public void unequip(Player player) {
		player.increaseIntelligence(-getIntelligenceBonus());
	}

	@Override
	public void equip(Player player) {
		player.increaseIntelligence(getIntelligenceBonus());
		
	}
	
}