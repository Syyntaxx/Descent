package descent;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Console.println(Console.BOLD_WHITE, "=== DESCENT ===");

        // NAME
        Console.println(Console.WHITE, "\nEnter your name, delver:");
        System.out.print(">>> ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            name = "Nameless Diver";
        }

        Player player = new Player(name, 0, 0, 0, 0);

        Console.println(Console.BOLD_WHITE, "\nAllocate your stat points.");
        Console.println(Console.WHITE, "You have " + player.getStatPoints() + " points.");

        while (player.getStatPoints() > 0) {

            Console.println(Console.BOLD_WHITE, "\nRemaining points: " + player.getStatPoints());

            Console.println(Console.WHITE,
                    "STR: " + player.getStrength() +
                    " | VIT: " + player.getVitality() +
                    " | AGI: " + player.getAgility() +
                    " | INT: " + player.getIntelligence());

            Console.println(Console.WHITE, "\nChoose a stat to upgrade:");
            Console.println(Console.WHITE, "[1] Strength");
            Console.println(Console.WHITE, "[2] Vitality");
            Console.println(Console.WHITE, "[3] Agility");
            Console.println(Console.WHITE, "[4] Intelligence");
            Console.println(Console.WHITE, "[5] Finish early");

            int choice = Console.errCheckInt(">>> ", 1, 5);

            if (choice == 5) {
                break;
            }

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
                    player.allocateStat("intelligence", amount);
                    break;
            }
        }

        Console.println(Console.BOLD_PURPLE, "\nYou descend into the Abyss...\n");

        Level level = new Level(player);
        level.start();

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
                    " | INT: " + player.getIntelligence());

            Console.println(Console.WHITE, "\n[1] STR  [2] VIT  [3] AGI  [4] INT  [5] Finish");

            int choice = Console.errCheckInt(">>> ", 1, 5);

            if (choice == 5) break;

            Console.println(Console.WHITE, "How many points?");
            int amount = Console.errCheckInt(">>> ", 1, player.getStatPoints());

            switch (choice) {
                case 1 -> player.allocateStat("strength", amount);
                case 2 -> player.allocateStat("vitality", amount);
                case 3 -> player.allocateStat("agility", amount);
                case 4 -> player.allocateStat("intelligence", amount);
            }
        }
    }
}