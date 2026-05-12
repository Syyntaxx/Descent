package descent.item;


import descent.Player;

public class SpeedBoots extends Equipment{

	public SpeedBoots() {
		super("Speed Boots", "Boots that make you run faster (+3 agility)", Item.Rarity.UNCOMMON, Equipment.Slot.BOOTS,
				0, 3, 0, 0);
		//str, agl, vit, intel
	}
	
	
	public void unequip(Player player) {
		player.increaseAgility(-getAgilityBonus());

	}

	@Override
	public void equip(Player player) {
		player.increaseAgility(getAgilityBonus());
	}
	
}