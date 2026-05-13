package descent;

import descent.item.*;

public class Enemy {

	private String name;
	private int level;
	private int maxHealth;
	private int health;
	private int attack;
	private int expReward;
	private Type type;
	
	
	public enum Type{
		SHROOMBEAR,
		HAMMERBEAK,
		SILKFANG,
		OTTOBAS,
		CRIMSON_SPLITJAW,
		STINGERHEAD,
		ROCK_LICKER,
		VALLEY_CROAKER
	}
	
	
	//Constructor=================================================================
	public Enemy(Type type, int level) {

		this.type = type;

		switch (type) {

		case SHROOMBEAR:
			this.name = "Shroombear";
			this.maxHealth = 10 + level * 5;
			this.attack = level * 5;
			this.expReward = (int) (level * 8.5);
			break;

		case HAMMERBEAK:
			this.name = "Hammerbeak";
			this.maxHealth = 10 + level * 4;
			this.attack = level * 3;
			this.expReward = (int) (level * 7);
			break;
		
		case SILKFANG:
			this.name = "Silkfang";
			this.maxHealth = 10 + level * 4;
			this.attack = level * 3;
			this.expReward = (int) (level * 5.5);
			break;
			
		case OTTOBAS:
			this.name = "Ottobas";
			this.maxHealth = 10 +level * 6;
			this.attack = level * 2;
			this.expReward = (int) (level * 4.5);
			break;
			
		case CRIMSON_SPLITJAW:
			this.name = "Crimson Splitjaw";
			this.maxHealth = 10 + level * 6;
			this.attack = (int) (level * 5.5);
			this.expReward = (int) (level * 3.5);
			break;

		case STINGERHEAD:
			this.name = "Stingerhead";
			this.maxHealth = 10 +level * 4;
			this.attack = level * 4;
			this.expReward = (int) (level * 9.5);
			break;

		case ROCK_LICKER:
			this.name = "Rock Licker";
			this.maxHealth = 10 +level * 3;
			this.attack =(int) (level * 1.3);
			this.expReward = (int) (level * 4.8);
			break;

		case VALLEY_CROAKER:
			this.name = "Valley Croaker";
			this.maxHealth = 10 +level * 4;
			this.attack = level * 3;
			this.expReward = (int) (level * 5.5);
			break;
		}
		
		this.health = maxHealth;
		this.level = level;
		
		
	}
//Combat methods==================================================================
	public boolean isDefeated() {
		return health <= 0;
	}

	public void takeDamage(int damage) {
		health = Math.max(health - damage, 0);
	}
	
	public void heal() {
		health = maxHealth;
	}
	
	public void heal (int amount) {
		health = Math.min(health + amount, maxHealth);
	}
	
	
	

	
	//Getters=====================================================================
	public String getName() { return name; }
	public int getLevel() { return level; }
	public int getHealth() { return health; }
	public int getMaxHealth() { return maxHealth; }
	public int getAttack() { return attack; } 
	public int getExpReward() { return expReward; }
	public Type getType() { return type; }
	
	//Loot========================================================================
	
	public LootDrop getLootDrop() {
	    switch (type) {
	    	case ROCK_LICKER:
	    		return new LootDrop(new CaveHood(), 1);
	    		
	    	case VALLEY_CROAKER:
	    		return new LootDrop(new LanternGoggles(), 1);
	    	
	        case SHROOMBEAR:
	            return new LootDrop(new SpeedBoots(), 1);

	        case HAMMERBEAK:
	           return new LootDrop(new HealthPotion(), 1);

	        case SILKFANG:
	            return new LootDrop(new StonePlate(), 1);

	        case OTTOBAS:
	        	return new LootDrop(new SpeedBoots(), 1);
	        	
	        default:
	            return new LootDrop(new EnergyPotion(), 1);
	    }
	}
	
}