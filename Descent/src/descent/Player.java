package descent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import descent.item.*;
import descent.move.*;

public class Player {
	
	private Random random = new Random();


	//Stats and values
	private String name;
	private int level;
	private int exp;
	private int expToNextLevel;
	private int statPoints;
	private int health;
	private int maxHealth;
	private int livesRemaining;
	private int strength;
	private int agility;
	private int vitality;
	private int energy;
	private int maxEnergy;
	private int luck;

	private int depth = 0;
	
	//Other
	private Inventory inventory;
	private Whistle whistle;
	private List<Move> moves;
	
	
	//Equipped armor
	private Equipment head;
	private Equipment torso;
	private Equipment boots;

	// DEFAULTS
	private static final int START_LEVEL = 1;
	private static final int START_EXP_TO_NEXT_LEVEL = 50;
	private static final int START_MAX_HEALTH = 50;
	private static final int START_LIVES = 3;
	private static final int START_STAT_POINTS = 8;
	private static final int START_MAX_ENERGY = 20;

	public Player(String name, int agility, int luck, int strength, int vitality) {

		this.name = name;
		this.maxHealth = START_MAX_HEALTH + vitality * 5;
		this.health = maxHealth;
		this.level = START_LEVEL;
		this.expToNextLevel = START_EXP_TO_NEXT_LEVEL;
		this.livesRemaining = START_LIVES;
		this.maxEnergy = START_MAX_ENERGY;
		this.energy = maxEnergy;
		this.agility = agility;
		this.luck = luck;
		this.strength = strength;
		this.vitality = vitality;
		this.statPoints = START_STAT_POINTS;
		this.whistle = Whistle.BELL;
		this.moves = new ArrayList<>();
		this.inventory = new Inventory();

		// TEMPORARY MOVES
		addMove(new BasicAttack());
		addMove(new QuickAttack());
		addMove(new Flee());
		
	

	}

	// rank along with exp scaling
	public enum Whistle {
		BELL(1.0), RED(1.2), BLUE(1.6), MOON(2.1), BLACK(2.7), WHITE(3.5);

		private final double expMult;

		Whistle(double expMult) {
			this.expMult = expMult;
		}
		
		public double getExpMult() { return expMult; }
	}

	// GETTERS
	public String getName() { return name; }
    public int getLevel() { return level; }
    public int getExp() { return exp; }
    public int getExpToNextLevel() { return expToNextLevel; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getLivesRemaining() { return livesRemaining; }
    public int getStatPoints() { return statPoints; }
    public int getStrength() { return strength; }
    public int getAgility() { return agility; }
    public int getVitality() { return vitality; }
    public int getLuck() { return luck; }
    public int getEnergy() { return energy; }
    public int getMaxEnergy() { return maxEnergy; }
    public int getDepth() { return depth; }

    
    public Inventory getInventory() { return inventory; }
    public Whistle getWhistle() { return whistle; }
    public List<Move> getMoves() { return moves; }
    public Equipment getHead() { return head; }
    public Equipment getTorso() { return torso; }
    public Equipment getBoots() { return boots; }
    
    public int getExpReward(int baseExp) {
		int scaledExp = (int) Math.round(baseExp * whistle.getExpMult());
		return scaledExp;
	}
//Methods ======================================================

	public void allocateStat(String stat, int points) {
		if (statPoints <= 0)
			return;

		if (points > statPoints)
			points = statPoints;

		switch (stat.toLowerCase()) {
		case "strength":
			strength += points;
			energy += points * 5;
			maxEnergy += points * 5;
			break;

		case "agility":
			agility += points;
			break;

		case "vitality":
			vitality += points;
			maxHealth += points * 5;
			health += points * 5;
			
			if (health > maxHealth) health = maxHealth;
			
			break;

		case "luck":
			luck += points;
			break;

		default:
			System.out.println("Unknown stat: " + stat);
			return;
		}
		statPoints -= points;
		System.out.println("Allocated " + points + " points to " + stat + ". Points remaining: " + statPoints);

	}

	public void increaseStrength(int points) {
		strength += points;
	}

	public void increaseLuck(int points) {
		luck += points;
	}

	public void increaseAgility(int points) {
		agility += points;
	}

	public void increaseVitality(int points) {
		vitality += points;
		maxHealth += points * 5;
		health += points * 5;
		
		if (health > maxHealth) health = maxHealth;
		
	}

	public void increaseMaxHealth(int amount) {
		maxHealth += amount;
	}

	public void increaseMaxEnergy(int amount) {
		maxEnergy += amount;
	}

	public void takeDamage(int damage) {
		health -= damage;
		if (health <= 0) {
			health = 0;
			livesRemaining--;
		}

	}

	// check if player is alive
	public boolean isAlive() {
		if (livesRemaining > 0)
			return true;
		else
			return false;
	}

	// heal the user, catch overhealing
	public void heal(int amount) {
		health = Math.min(maxHealth, health + amount);
	}

	public void heal() {
		health = maxHealth;
	}

	public boolean hasEnergy(int amount) {
		if (energy < amount)
			return false;
		else {
			return true;
		}
	}

	public void useEnergy(int amount) {
		if (energy < amount) {
			return;
		}
		energy -= amount;
	}

	public void addEnergy(int amount) {
		energy = Math.min(energy + amount, maxEnergy);
	}

	public void addEnergy() {
		energy = maxEnergy;
	}
	

public boolean isCrit() {
	
    int baseCrit = 10; // 5%
    int critChance = baseCrit + (luck / 2); 

    return random.nextInt(100) < critChance;
}
	
	public void displayStats() {
	    String separator = "------------------------------------------";
	    String doubleSeparator = "==========================================";

	    Console.println(Console.CYAN, doubleSeparator);
	    Console.println(Console.BOLD_CYAN, "  CHARACTER PROFILE: " + name.toUpperCase());
	    Console.println(Console.CYAN, doubleSeparator);

	    // Basic Info: Level, Exp, Whistle
	    Console.println(Console.WHITE, " [Level]   " + level);
	    Console.println(Console.YELLOW, " (" + exp + " / " + expToNextLevel + " XP)");
	    Console.println(Console.WHITE, " [Whistle] " + whistle.toString() + " (x" + whistle.getExpMult() + " Multiplier)");
	    Console.println(Console.WHITE, " [Depth]   " + depth + " meters");
	    
	    Console.println(Console.CYAN, separator);

	    // Vital Stats: Health, Energy, Lives
	    String healthBar = "[" + health + "/" + maxHealth + "]";
	    String energyBar = "[" + energy + "/" + maxEnergy + "]";
	    
	    Console.println(Console.RED, " [HP] "); 
	    Console.println(Console.WHITE, String.format("%-15s", healthBar) + " [Lives: " + livesRemaining + "]");
	    
	    Console.println(Console.YELLOW, " [EN] "); 
	    Console.println(Console.WHITE, String.format("%-15s", energyBar));

	    Console.println(Console.CYAN, separator);

	    // Attributes
	    Console.println(Console.BOLD_WHITE, " ATTRIBUTES (" + statPoints + " Points Available)");
	    Console.println(Console.WHITE, String.format("  STR: %-5d  AGI: %-5d", strength, agility));
	    Console.println(Console.WHITE, String.format("  VIT: %-5d  INT: %-5d", vitality, luck));

	    Console.println(Console.CYAN, separator);

	    // Equipment Slots
	    Console.println(Console.BOLD_WHITE, " EQUIPMENT");
	    Console.println(Console.WHITE, "  HEAD:  " + (head != null ? head.getName() : "---"));
	    Console.println(Console.WHITE, "  TORSO: " + (torso != null ? torso.getName() : "---"));
	    Console.println(Console.WHITE, "  BOOTS: " + (boots != null ? boots.getName() : "---"));

	    Console.println(Console.CYAN, doubleSeparator);
	}

	public void gainExperience(int amount) {
		int scaledExp = (int) Math.round(amount * whistle.getExpMult());
		exp += scaledExp;
		while (exp >= expToNextLevel) {
			levelUp();
		}
	}
	
	//handle player depth ===================================
	public void increaseDepth(int amount) {
	    depth += amount;
	}
	
	public void setDepth(int amount) {
		depth = amount;
	}
	//=======================================================
	public void levelUp() { //player levels up
		exp -= expToNextLevel;
		level++;
		
		switch (level) {
			case 3:
				whistle = Whistle.RED;
				Console.println(Console.BOLD_CYAN, "\nYou are now a RED whistle!\n");
				break;
			case 10:
				whistle = Whistle.BLUE;
				Console.println(Console.BOLD_CYAN, "\nYou are now a BLUE whistle!\n");

				break;
			case 20:
				whistle = Whistle.MOON;
				Console.println(Console.BOLD_CYAN, "\nYou are now a MOON whistle!\n");

				break;
			case 30:
				whistle = Whistle.BLACK;
				Console.println(Console.BOLD_CYAN, "\nYou are now a BLACK whistle!\n");

				break;
			case 40:
				whistle = Whistle.WHITE;
				Console.println(Console.BOLD_CYAN, "\nYou are now a WHITE whistle!\n");
				break;
		}
			

		expToNextLevel = (int) Math.round(expToNextLevel * 1.12);
		maxHealth += 10;
		health = maxHealth;
		statPoints += 3; 
		
		

		Console.println(Console.BOLD_YELLOW,
				"LEVELED UP! You are now level " + level + ". You have " + statPoints + " stat points to allocate.");
	}

	public void addMove(Move move) {
		for (Move learnedMoves : moves)
			if (learnedMoves.getName().equalsIgnoreCase(move.getName())) {
				Console.println(Console.YELLOW, "You already know " + move.getName());
				return;
			}
		moves.add(move);

	}
	
	//inventory ============================================================
	public boolean isEquipped(Equipment eq) {
		 return (head != null && head.getName().equalsIgnoreCase(eq.getName())) ||
		        (torso != null && torso.getName().equalsIgnoreCase(eq.getName())) ||
		        (boots != null && boots.getName().equalsIgnoreCase(eq.getName()));
		}
	
	
	
	public void toggleEquip(Equipment eq) {
		switch (eq.getSlot()) {
		case HEAD:
			if (head != null && head.getName().equalsIgnoreCase(eq.getName())) {
				head.unequip(this);
				head = null;
				Console.println(Console.YELLOW, eq.getName() + " unequipped.");
			}
			else {
				if (head != null) {
					head.unequip(this);
				}
				eq.equip(this);
				head = eq;
				Console.println(Console.YELLOW, eq.getName() + " equipped.");
			}
			break;
			
		case TORSO:
			if (torso != null && torso.getName().equalsIgnoreCase(eq.getName())) {
				torso.unequip(this);
				torso = null;
				Console.println(Console.YELLOW, eq.getName() + " unequipped.");
			}
			else {
				if (torso != null) {
					torso.unequip(this);
				}
				eq.equip(this);
				torso = eq;
				Console.println(Console.YELLOW, eq.getName() + " equipped.");
			}
			break;
			
		case BOOTS:
			if (boots != null && boots.getName().equalsIgnoreCase(eq.getName())) {
				boots.unequip(this);
				boots = null;
				Console.println(Console.YELLOW, eq.getName() + " unequipped.");
			}
			else {
				if (boots != null) {
					boots.unequip(this);
				}
				eq.equip(this);
				boots = eq;
				Console.println(Console.YELLOW, eq.getName() + " equipped.");
			}
			break;
		}
	}
	
	
	//
	public void equipItem(String itemName) {
	    int index = inventory.getItemIndex(itemName);

	    if (index == -1) {
	        Console.println(Console.RED, "Item not found.");
	        return;
	    }

	    Item item = inventory.getItems().get(index);

	    if (!(item instanceof Equipment)) {
	        Console.println(Console.RED, "That item is not equippable.");
	        return;
	    }

	    toggleEquip((Equipment) item);
	}
	//=============================================================
	
	
	
	
}