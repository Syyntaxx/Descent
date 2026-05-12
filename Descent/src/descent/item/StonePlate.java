package descent.item;


import descent.Player;

public class StonePlate extends Equipment{

	public StonePlate() {
		super("Stone Plate", "A sheet of rock. (+3 vit)", Item.Rarity.COMMON, Equipment.Slot.TORSO,
				0, 0, 3, 0);
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