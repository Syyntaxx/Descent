package descent.item;


import descent.Player;

public class DelverCap extends Equipment{

	public DelverCap() {
		super("Delver Cap", "A cap for those on missions to explore the depths of the abyss. (+2 vitality, +1 intel)", Item.Rarity.UNCOMMON, Equipment.Slot.HEAD,
				0, 0, 2, 0);
		//str, agl, vit, intel
	}
	
	
	public void unequip(Player player) {
		player.increaseVitality(-getVitalityBonus());
	}

	@Override
	public void equip(Player player) {
		player.increaseVitality(getVitalityBonus());
		
	}
	
}