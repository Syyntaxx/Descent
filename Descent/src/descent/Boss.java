package descent;


import java.util.Random;

public class Boss extends Enemy {

	private Ability ability;

	public Boss(Type type, int level, Ability ability) {
		super(type, level);
		this.ability = ability;
	}

	public enum Ability {
		ENRAGE, REGENERATE, POISON, NONE
	}

	Random random = new Random();

	//special ability for the enemy
	public void useSpecialAbility(Player player) {
		switch (ability) {
			case ENRAGE:
				if (getHealth() <= getMaxHealth() / 2) {
				Console.println(Console.BOLD_RED,
						"\nThe " + getName() + " is enraged! It's attack power surges!");
				// combat handler check needed to handle dmg change
				break;
			}
			case REGENERATE:
				int healAmount = (int) (getMaxHealth() * 0.5);
				heal(healAmount);
				Console.println(Console.BOLD_RED, "\nThe " + getName() + " regenerates " + healAmount + " HP!");
				break;
				
			case POISON:
				int poisonAmount = (int) (getMaxHealth() * 0.03);
				player.takeDamage(poisonAmount);
				Console.println(Console.BOLD_RED, "\nThe " + getName() + "'s poisonous aura deals " + poisonAmount + " damage!");
				break;
			case NONE:
				break;
		}
			
	}
	
	//calculate if boss should use ability
	public boolean shouldUseAbility() {
        switch (ability) {
            case ENRAGE:
                //triggers when below 50% health
                return getHealth() <= getMaxHealth() / 2;

            case REGENERATE:
                //40% chance
            	if (getHealth() != getMaxHealth()) {
            		return random.nextInt(100) < 40;
            	}
            	return false;

            case POISON:
                return true;

            case NONE:
                return false;

            default:
                return false;
        }
    }
	
	public boolean isEnraged() {
		return ability == Ability.ENRAGE && getHealth() <= getMaxHealth() / 2;
	}

	
}