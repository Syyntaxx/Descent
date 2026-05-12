package descent;

import java.util.List;
import descent.move.*;

public class Combat {

	private Player player;
	private Enemy enemy;

	public Combat(Player player, Enemy enemy) {
		this.player = player;
		this.enemy = enemy;
	}

	public boolean startBattle() {
		boolean result = false;
		Console.println(Console.BOLD_RED, "You encounter a " + enemy.getName() + "!");
		Console.sleep(1000);

		while (!enemy.isDefeated() && player.getHealth() > 0) {
			display();
			playerTurn();

			if (enemy.isDefeated()) {
				victory();
				result = true;
				break;
			}
			enemyTurn();

			if (player.getHealth() == 0) {
				defeat();
				result = false;
				
			}

		}
		return result;
	}

	private void display() {
		System.out.println(Console.WHITE + "================================================" + Console.RESET);

		System.out.printf("%-24s | HP: %3d/%-3d | Energy: %s%2d%s/%-2d %n",
				Console.BOLD_WHITE + player.getName() + Console.RESET, player.getHealth(), player.getMaxHealth(),
				Console.BOLD_YELLOW, player.getEnergy(), Console.RESET, // Colored energy
				player.getMaxEnergy());

		System.out.printf("%-24s | HP: %3d/%-3d %n", Console.BOLD_RED + enemy.getName() + Console.RESET,
				enemy.getHealth(), enemy.getMaxHealth());

		System.out.println(Console.WHITE + "================================================" + Console.RESET);
	}

	private void playerTurn() {
		List<Move> moves = player.getMoves();

		Console.println(Console.BOLD_WHITE, "\n================ YOUR TURN ================\n");

		// Display moves
		for (int i = 0; i < moves.size(); i++) {

			Move m = moves.get(i);
			if (!player.hasEnergy(m.getEnergyCost())) {
				String energy = String.format("[%d]", m.getEnergyCost());

				System.out.printf("%s%-3s%s %-18s %s%-6s%s | %s%s%s%n", Console.BOLD_WHITE, (i + 1) + ".",
						Console.RESET, m.getName(), Console.GREY, energy, Console.RESET, Console.ITALIC,
						m.getDescription(), Console.RESET);
			} else {
				String energy = String.format("[%d]", m.getEnergyCost());

				System.out.printf("%s%-3s%s %-18s %s%-6s%s | %s%s%s%n", Console.BOLD_WHITE, (i + 1) + ".",
						Console.RESET, m.getName(), Console.YELLOW, energy, Console.RESET, Console.ITALIC,
						m.getDescription(), Console.RESET);

			}
			
		}
		
		System.out.printf("%s%-3s%s %-18s%n", Console.BOLD_WHITE,
		        (moves.size() + 1) + ".", Console.RESET, "Use item", Console.RESET);

		System.out.println("\n--------------------------------------------");

		int choice = Console.errCheckInt(">>> ", 1, moves.size() + 1);
		
		if (choice == moves.size() + 1) {
		    boolean used = player.getInventory().useConsumableInCombat(player);
		    
		    if (!used) {
		            playerTurn();
		            return;
		        }
		        
		
		} else {
			Move selectedMove = moves.get(choice - 1);
			String result = selectedMove.execute(player, enemy);
			Console.println(Console.CYAN, "\nYou used " + selectedMove.getName() + "...\n");
			Console.sleep(500);
			Console.println(Console.BOLD_CYAN, result);
		}
	}

	private void enemyTurn() {
		int damage = Math.max(1, enemy.getAttack());

		if (enemy instanceof Boss) {
			Boss boss = (Boss) enemy;
			
			if (boss.isEnraged()) {
				damage = (int) (damage * 1.5);
			}
			
			if (boss.shouldUseAbility()) {
				Console.sleep(500);
				boss.useSpecialAbility(player);
			}
			
		}
		Console.sleep(1000);
		Console.println(Console.RED, "\n" + enemy.getName() + " attacks!");
		Console.sleep(500);
		player.takeDamage(damage);
		Console.println(Console.BOLD_RED, "You took " + damage + " damage!\n");
		Console.sleep(1000);

	}

	private void victory() {
		Console.println(Console.BOLD_YELLOW, "\nVictory! " + enemy.getName() + " has been defeated.");
		Console.sleep(1000);
		player.gainExperience(enemy.getExpReward());
		Console.println(Console.CYAN, "You gained " + enemy.getExpReward() + " experience!");
		player.addEnergy();
	}

	private void defeat() {
		Console.println(Console.BOLD_RED, "\n[ DEFEATED ]");
		Console.sleep(1000);
		Console.println(Console.RED, "You collapsed in battle...");

		if (player.getLivesRemaining() == 0) {
			Console.println(Console.BOLD_RED, "GAME OVER: You have no lives remaining.");
			// IMPORTANT: player is fully dead here, add game over system
		} else {
			Console.println(Console.YELLOW, "You lost a life! Lives remaining: " + player.getLivesRemaining());
			Console.sleep(1000);
			Console.println(Console.YELLOW, "You retreat to safety to recover...");
			player.heal();
		}
	}

}