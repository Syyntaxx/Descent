package descent.move;

import descent.Player;
import descent.Enemy;

public class BasicAttack extends Move{
	public BasicAttack() {
		super("Basic Attack", "A heavy strike", 5);
	}
	
	@Override
	public String execute(Player player, Enemy enemy) {
	    if (!player.hasEnergy(energyCost)) {
	        return "[FAIL]: not enough energy!";
	    }

	    player.useEnergy(energyCost);

	    int damage = 3 + player.getStrength() * 4;

	    boolean crit = player.isCrit();

	    if (crit) {
	        damage *= 2;
	    }

	    enemy.takeDamage(damage);

	    if (crit) {
	        return "[CRIT HIT]: You strike for " + damage + " damage!";
	    } else {
	        return "[HIT]: You strike for " + damage + " damage!";
	    }
	}
}