package descent;

import java.util.Random;

import descent.item.*;

public class Level {
	private Player player;
	private int layer;
	private Boss boss;
	private int minDepth;
	private int maxDepth;


	private boolean bossDefeated = false;
	private boolean bossAvailable = false;;
	private boolean levelComplete = false;

	private Random random = new Random();

	

	public Level(Player player) {
		this.player = player;
		
		this.layer = getLayer(player.getDepth());
	    this.minDepth = getMinDepth(layer);
	    this.maxDepth = getMaxDepth(layer);
	}

	public void start() {


		while (player.getHealth() > 0 && !levelComplete) {

			int depth = player.getDepth();
			int layer = getLayer(depth);
			
			displayStatus(depth, layer);

			int choice = getPlayerChoice();

			switch (choice) {
			case 1:
				explore(layer);
				break;
			case 2:
				player.getInventory().manageInventory(player);
				break;
			case 3:
				player.displayStats();
				break;
			case 4:
				// TODO possible 4th option (shop?)
				break;
			case 5:
				if (bossAvailable && !bossDefeated) {
					fightBoss();
				} else if (bossDefeated) {
					descend();
				}
				break;
			}

		}
	}

	private void descend() {
		Console.println(Console.CYAN, "You descend deeper into the abyss...");
		Console.sleep(800);

		levelComplete = true;

	}



	private void startCombat(int layer) {

	    Enemy enemy = generateEnemy(layer); 

	    Combat combat = new Combat(player, enemy);
	    boolean won = combat.startBattle();

	    if (won && !bossAvailable) {

	        int gain = 350 + random.nextInt(151);
	        int newDepth = player.getDepth() + gain;

	        if (newDepth > maxDepth) {
	            newDepth = maxDepth;
	        }

	        player.setDepth(newDepth);

	        Console.println(Console.CYAN, "You descend " + gain + "m...");
	        
	        boolean dropsLoot = random.nextInt(100) > 1;
	        
	        if(dropsLoot) {
		        LootDrop drop = enemy.getLootDrop();
		        handleLootChoice(drop);
	        }

	        checkBossTrigger();
	    }
	}

	private void explore(int layer) {

		int roll = random.nextInt(100);

		if (roll < 100) {
			startCombat(layer);
		} else if (roll < 100) { // 100 = no chance of encounterEvent();
			findLoot();
		} else {
			encounterEvent();
		}
	}

	private void fightBoss() {

	    Combat bossFight = new Combat(player, boss);
	    boolean won = bossFight.startBattle();

	    if (won) {
	        Console.println(Console.BOLD_YELLOW, "You've defeated the ruler of this layer.");

	        bossDefeated = true;
	    }
	}
	
	private Enemy generateEnemy(int layer) {

	    Enemy.Type[] possibleEnemies;

	    if (layer == 1) {
	        possibleEnemies = new Enemy.Type[] {
	            Enemy.Type.VALLEY_CROAKER,
	            Enemy.Type.ROCK_LICKER,
	            Enemy.Type.HAMMERBEAK
	        };
	    } else if (layer == 2) {
	        possibleEnemies = new Enemy.Type[] {
	            Enemy.Type.HAMMERBEAK,
	            Enemy.Type.OTTOBAS,
	            Enemy.Type.SILKFANG
	        };
	    } else {
	        possibleEnemies = new Enemy.Type[] {
	            Enemy.Type.SHROOMBEAR,
	            Enemy.Type.DRAGON,
	            Enemy.Type.SILKFANG
	        };
	    }

	    Enemy.Type type = possibleEnemies[random.nextInt(possibleEnemies.length)];

	    return new Enemy(type, layer);
	}
	
	private Boss generateBoss(int layer) {

		Boss.Ability ability;
		Enemy.Type type;

		if (layer == 1) {
			type = Enemy.Type.HAMMERBEAK;
			ability = Boss.Ability.ENRAGE;
		} else if (layer == 2) {
			type = Enemy.Type.CRIMSON_SPLITJAW;
			ability = Boss.Ability.REGENERATE;
		} else {
			type = Enemy.Type.SILKFANG;
			ability = Boss.Ability.POISON;
		}

		return new Boss(type, layer * 2, ability); // boss is twice the level of the depth

	}
	
	private void checkBossTrigger() {

	    if (!bossAvailable && player.getDepth() >= maxDepth - 200) {

	        bossAvailable = true;
	        boss = generateBoss(getLayer(player.getDepth()));

	        Console.println(Console.BOLD_PURPLE, "A powerful presence lurks below...");
	    }
	}
	
	
	private void handleLootChoice(LootDrop drop) {

	    Console.println(Console.BOLD_WHITE, "\nYou found loot:");
	    Console.println(Console.WHITE, drop.getItem().getName());

	    System.out.println("[1] Pick up");
	    System.out.println("[2] Leave it");

	    int choice = Console.errCheckInt(">>> ", 1, 2);

	    if (choice == 1) {
	        player.getInventory().addItem(drop.getItem(), drop.getAmount());
	    } else {
	        Console.println(Console.YELLOW, "You leave the loot behind.");
	    }
	}
	

	private void findLoot() {
		// TODO add loot tables
	}

	private void encounterEvent() {
		// TODO add an event encounter
	}

	private int getLayer(int depth) {
		if (depth <= 1350)
			return 1;
		else if (depth <= 2600)
			return 2;
		else if (depth <= 7000)
			return 3;
		else if (depth <= 12000)
			return 4;
		else if (depth <= 13000)
			return 5;
		else
			return 0;
	}
	
	private int getMinDepth(int layer) {
	    switch (layer) {
	        case 1: return 0;
	        case 2: return 1350;
	        case 3: return 2600;
	        default: return 0;
	    }
	}

	private int getMaxDepth(int layer) {
	    switch (layer) {
	        case 1: return 1350;
	        case 2: return 2600;
	        case 3: return 7000;
	        default: return 99999;
	    }
	}
	
	

	private int getPlayerChoice() {
		System.out.println("\nWhat would you like to do?");
		System.out.println("[1] Explore");
		System.out.println("[2] Inventory");
		System.out.println("[3] Stats");
		System.out.println("[4] Rest");

		if (bossDefeated) {
			System.out.println("[5] Descend into layer " + (getLayer(player.getDepth()) + 1) + ".");
			return Console.errCheckInt(">>> ", 1, 5);
		}
		else if (bossAvailable) {
			System.out.println("[5] Investigate mysterious presence");
			return Console.errCheckInt(">>> ", 1, 5);
		}

		return Console.errCheckInt(">>> ", 1, 4);
	}

	private void displayStatus(int depth, int layer) {
		Console.println(Console.BOLD_WHITE, "\n==== DESCENT ====");
		Console.println(Console.WHITE, "Depth: " + depth + "m");
		Console.println(Console.WHITE, "Layer: " + layer);
	}
}