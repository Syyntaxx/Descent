package descent.item;

import descent.Console;
import descent.Player;

public class EnergyPotion extends Consumable{

	public EnergyPotion() {
		super("Energy potion", "Recharge 5 energy", Rarity.CONSUMABLE);
	}

	@Override
	public void use(Player player){
		int old = player.getEnergy();
		player.addEnergy(5);
		int rechargedAmount = Math.min(player.getEnergy() - old, 5);

		Console.println(Console.GREEN, "You used an energy potion!"
				+ " Recharged for " + rechargedAmount + " energy.");
		
	}
	
	public void unequip(Player player) {
		//consumable cant be unequipped
	}
}