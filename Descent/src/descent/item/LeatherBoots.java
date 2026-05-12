package descent.item;


import descent.Player;

public class LeatherBoots extends Equipment{

	public LeatherBoots() {
		super("LeatherBoots", "Dusty boots that smell. (+2 vitality)", Item.Rarity.UNCOMMON, Equipment.Slot.BOOTS,
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