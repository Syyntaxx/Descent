package descent.item;


import descent.Player;

public class CaveHood extends Equipment{

	public CaveHood() {
		super("Cave Hood", "A plain red hood (+2 vitality)", Item.Rarity.UNCOMMON, Equipment.Slot.HEAD,
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