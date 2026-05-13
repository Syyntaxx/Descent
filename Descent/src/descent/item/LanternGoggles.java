package descent.item;


import descent.Player;

public class LanternGoggles extends Equipment{

	public LanternGoggles() {
		super("Lantern Goggles", "Goggles that provide visibility (+2 intelligence)", Item.Rarity.UNCOMMON, Equipment.Slot.HEAD,
				0, 0, 0, 0);
		//str, agl, vit, intel
	}
	
	
	public void unequip(Player player) {
		player.increaseLuck(-getLuckBonus());
	}

	@Override
	public void equip(Player player) {
		player.increaseLuck(-getLuckBonus());
		
	}
	
}