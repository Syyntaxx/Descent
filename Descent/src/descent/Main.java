package descent;

import java.util.Scanner;

public class Main {
	
    public static void main(String[] args) {
    	
    	Scanner scanner = new Scanner(System.in);

    	// header
    	Console.println(Console.BOLD_WHITE, "------------------------------------------");
    	Console.println(Console.BOLD_WHITE, "               D E S C E N T              ");
    	Console.println(Console.BOLD_WHITE, "------------------------------------------");

    	// name Input
    	Console.println(Console.WHITE, "\nIDENTIFY YOURSELF, DELVER:");
    	System.out.print(Console.CYAN + " » " + Console.RESET);
    	String name = scanner.nextLine().trim();
    	if (name.isEmpty()) name = "Nameless Diver";

    	Player player = new Player(name, 0, 0, 0, 0);

    	// stat allocation Loop
    	while (player.getStatPoints() > 0) {
    	    Console.println(Console.BOLD_WHITE, "\n[ CHARACTER CUSTOMIZATION ]");
    	    Console.println(Console.WHITE, " Points Available: " + Console.YELLOW + player.getStatPoints() + Console.RESET);
    	    
    	    // display current stats in a clean horizontal bar
    	    String statBar = String.format(
    	        " STR: %-3d | VIT: %-3d | AGI: %-3d | LCK: %-3d",
    	        player.getStrength(), player.getVitality(), 
    	        player.getAgility(), player.getLuck()
    	    );
    	    Console.println(Console.CYAN, " " + statBar);
    	    Console.println(Console.WHITE, " ------------------------------------------");

    	    // Menu options
    	    System.out.println("  1. Strength      (Power)");
    	    System.out.println("  2. Vitality      (Health & Defense)");
    	    System.out.println("  3. Agility       (Speed & Evasion)");
    	    System.out.println("  4. Luck          (Crit & Fortune)");
    	    System.out.println("  5. [ Confirm Stats ]");

    	    int choice = Console.errCheckInt("\n UPGRADE > ", 1, 5);
    	    if (choice == 5) break;

    	    // Sub-prompt for amount
    	    System.out.print(" AMOUNT (1-" + player.getStatPoints() + ") > ");
    	    int amount = Console.errCheckInt("", 1, player.getStatPoints());

    	    switch (choice) {
    	        case 1 -> player.allocateStat("strength", amount);
    	        case 2 -> player.allocateStat("vitality", amount);
    	        case 3 -> player.allocateStat("agility", amount);
    	        case 4 -> player.allocateStat("luck", amount);
    	        
    	    }
    	}

    	Console.println(Console.BOLD_PURPLE, "\n*** The Abyss calls, " + name.toUpperCase() + "... ***\n");

        while (player.isAlive()) {

            Level currentLevel = new Level(player);
            currentLevel.start();

            if (!player.isAlive()) break;

        }
        scanner.close();
    }
    
    public static void allocateLevelUpStats(Player player, Scanner scanner) {

        Console.println(Console.BOLD_YELLOW, "\n=== LEVEL UP: STAT ALLOCATION ===");

        while (player.getStatPoints() > 0) {

            Console.println(Console.WHITE,
                    "Points: " + player.getStatPoints());

            Console.println(Console.WHITE,
                    "STR: " + player.getStrength() +
                    " | VIT: " + player.getVitality() +
                    " | AGI: " + player.getAgility() +
                    " | LCK: " + player.getLuck());

            Console.println(Console.WHITE, "\n[1] STR  [2] VIT  [3] AGI  [4] LCK  [5] Finish");

            int choice = Console.errCheckInt(">>> ", 1, 5);

            if (choice == 5) break;

            Console.println(Console.WHITE, "How many points?");
            int amount = Console.errCheckInt(">>> ", 1, player.getStatPoints());

            switch (choice) {
                case 1:
                	player.allocateStat("strength", amount);
                	break;
                case 2:
                	player.allocateStat("vitality", amount);
                	break;
                case 3:
                	player.allocateStat("agility", amount);
                	break;
                case 4:
                	player.allocateStat("luck", amount);
                	break;
            }
        }
    }
}