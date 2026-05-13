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
	private boolean restAvailable = false;
	
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
			int layer = this.layer;
			
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
				if (restAvailable) {
			        player.heal(20);

			        Console.println(Console.GREEN, "You rest and recover 20 HP.");
			        restAvailable = false;
				}
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
	    player.setDepth(maxDepth); // push to layer boundary
		levelComplete = true;

	}



	private void startCombat(int layer) {

	    Enemy enemy = generateEnemy(layer); 

	    Combat combat = new Combat(player, enemy);
	    boolean won = combat.startBattle();

	    if (won && !bossAvailable) {

	    	restAvailable = true;
	        int gain = 100 + random.nextInt(151);
	        int newDepth = player.getDepth() + gain;

	        if (newDepth > maxDepth) {
	            newDepth = maxDepth;
	        }

	        int actualGain = newDepth - player.getDepth();
	        player.setDepth(newDepth);

	        Console.println(Console.CYAN, "You descend " + actualGain + "m...");
	        
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
	            Enemy.Type.SILKFANG
	        };
	    }

	    Enemy.Type type = possibleEnemies[random.nextInt(possibleEnemies.length)];
	    int enemyLevel = layer * (random.nextInt(5) + 1) + 1;
	    
	    return new Enemy(type, enemyLevel);
	}
	
	private Boss generateBoss(int layer) {

		Boss.Ability ability;
		Enemy.Type type;

		if (layer == 1) {
			type = Enemy.Type.HAMMERBEAK;
			ability = Boss.Ability.POISON;
		} else if (layer == 2) {
			type = Enemy.Type.CRIMSON_SPLITJAW;
			ability = Boss.Ability.ENRAGE;
		} else {
			type = Enemy.Type.SILKFANG;
			ability = Boss.Ability.POISON;
		}

		return new Boss(type, layer * 2, ability); // boss is twice the level of the depth

	}
	
	private void checkBossTrigger() {

	    if (!bossAvailable && player.getDepth() >= maxDepth - 200) {

	        bossAvailable = true;
	        boss = generateBoss(this.layer);

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
		if (depth < 1350)
			return 1;
		else if (depth < 2600)
			return 2;
		else if (depth < 7000)
			return 3;
		else if (depth < 12000)
			return 4;
		else if (depth < 13000)
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
	    // Header for the menu
	    System.out.println("\n " + Console.BOLD_WHITE + "== ACTION MENU ==" + Console.RESET);
	    
	    // Using a grid layout for standard actions
	    System.out.println("  [1] Explore    [2] Inventory");
	    System.out.println("  [3] Stats      [4] Rest");

	    int maxOption = 4;

	    // Progression logic with distinct visual weight
	    if (bossDefeated) {
	        maxOption = 5;
	        int nextLayer = getLayer(player.getDepth()) + 1;
	        System.out.println(Console.CYAN + "  ----------------------------");
	        System.out.println("  [5] DESCEND TO LAYER " + nextLayer + Console.RESET);
	    } 
	    else if (bossAvailable) {
	        maxOption = 5;
	        System.out.println(Console.RED + "  ----------------------------");
	        System.out.println("  [5] INVESTIGATE PRESENCE" + Console.RESET);
	    }

	    // A clean, minimal input prompt
	    System.out.print("\n SELECT > ");
	    return Console.errCheckInt("", 1, maxOption);
	}
	
	
	private void displayStatus(int depth, int layer) {
	    // A clean separator with a subtle color
	    Console.println(Console.CYAN, "________________________________________________");
	    
	    // Using tabs or padding to align the stats on one line
	    String status = String.format(
	        " %s DEPTH: %-8d %s LAYER: %-8d",
	        Console.BOLD_WHITE, depth, 
	        Console.BOLD_WHITE, layer
	    );
	    
	    System.out.println(status);
	    Console.println(Console.CYAN, "‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾");
	}
}