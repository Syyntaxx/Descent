package descent;

import descent.item.*;

import java.util.List;

import java.util.ArrayList;

public class Inventory {

	private List<Item> items;
	//private List<Equipment> equipped = new ArrayList<>();

	public Inventory() {
		this.items = new ArrayList<>();
	}

	public void addItem(Item item) {
		items.add(item);
		Console.println(Console.YELLOW, item.getName() + " added to inventory.");
	}

	public void addItem(Item item, int amount) {
		int i = amount;
		while (i > 0) {
			items.add(item);
			i--;

		}
		Console.println(Console.YELLOW, item.getName() + " [x" + amount + "] added to inventory.");

	}

	public void removeItem(Item item) {
		items.remove(item);
		Console.println(Console.YELLOW, item.getName() + " removed from inventory.");

	}

	public boolean useItem(int index, Player player) {
		if (index < 0 || index >= items.size()) {
			Console.println(Console.RED, "Invalid item");
			return false;
		}

		Item item = items.get(index);

		if (!(item instanceof Consumable)) {
			Console.println(Console.YELLOW, "This item is not a consumable.");
			return false;
		}

		item.use(player);
		items.remove(index);
		return true;
	}

	public boolean toggleEquip(int index, Player player) {
		if (index < 0 || index >= items.size()) {
			Console.println(Console.YELLOW, "Invalid item");
			return false;
		}

		Item item = items.get(index);

		if (!(item instanceof Equipment)) {
			Console.println(Console.RED, "That item cannot be equipped.");
			return false;
		}

		player.toggleEquip((Equipment) item);
		return true;
	}

	
	public String getStatString(Equipment eq) {
	    String stats = "";

	    if (eq.getStrengthBonus() != 0)
	        stats += " +" + eq.getStrengthBonus() + " STR";
	    if (eq.getAgilityBonus() != 0)
	        stats += " +" + eq.getAgilityBonus() + " AGI";
	    if (eq.getVitalityBonus() != 0)
	        stats += " +" + eq.getVitalityBonus() + " VIT";
	    if (eq.getLuckBonus() != 0)
	        stats += " +" + eq.getLuckBonus() + " LCK";

	    return stats.isEmpty() ? "" : " (" + stats.trim() + ")";
	}

	public void displayInventory(Player player) {
		
	    if (items.isEmpty()) {
	        Console.println(Console.YELLOW, "Your inventory is empty.");
	        return;
	    }

	    Console.println(Console.BOLD_WHITE, "\n==== INVENTORY ====");
	    
	    Console.println(Console.BOLD_WHITE, "--EQUIPPED--");
	    
	    Console.println(Console.WHITE,
	    	    "Head:  " + (player.getHead()  != null ? player.getHead().getName()  : "None"));
	    	Console.println(Console.WHITE,
	    	    "Torso: " + (player.getTorso() != null ? player.getTorso().getName() : "None"));
	    	Console.println(Console.WHITE,
	    	    "Boots: " + (player.getBoots() != null ? player.getBoots().getName() +
	    	    		getStatString(player.getBoots()) : "None"));
	  
	    List<Item> displayItems = getDisplayItems();
	    
	    System.out.println();
	    for (int i = 0; i < displayItems.size(); i++) {
	        Item current = displayItems.get(i);

	        int count = getNumberOfItems(current); 
	        String rarityColour = getRarityColour(current.getRarity());

	        String displayName = current.getName();
	       
	      
	        
	        if (count > 1) {
	            displayName += " x" + count;
	        }

	        System.out.printf(
	            "%s%-3s%s %-20s %s%-12s%s | %s%s%s%n",
	            Console.BOLD_WHITE, (i + 1) + ".", Console.RESET,
	            displayName,
	            rarityColour, "[" + current.getRarity() + "]", Console.RESET,
	            Console.ITALIC,
	            current.getDescription(),
	            Console.RESET
	        );
	    }

	    System.out.println(Console.WHITE + "===================" + Console.RESET);
	}
	// skipping duplicates in display
	public List<Item> getDisplayItems() {
	    List<Item> displayItems = new ArrayList<>();
	    List<String> countedNames = new ArrayList<>();

	    for (Item current : items) {
	        String name = current.getName().toLowerCase();

	        if (!countedNames.contains(name)) {
	            countedNames.add(name);
	            displayItems.add(current);
	        }
	    }

	    return displayItems;
	}

	//rarity color
	private String getRarityColour(Item.Rarity rarity) {
		switch (rarity) {
		case CONSUMABLE:	return Console.WHITE;
		case COMMON:    	return Console.WHITE;
		case UNCOMMON:  	return Console.GREEN;
		case RARE:      	return Console.BLUE;
		case LEGENDARY: 	return Console.BOLD_YELLOW;
		default:        	return Console.RESET;

		}
	}

	
	public int getNumberOfItems(Item item) {
		int i = 0;
		for (Item n : items) {
			if (n.getName().equalsIgnoreCase(item.getName())) {
				i++;
			}
		}
		return i;
	}

	public int getItemIndex(String name) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getName().equalsIgnoreCase(name)) {
				return i;
			}
		}
		return -1; // not found
	}
	
	//combat inventory, only able to use consumables
	public boolean useConsumableInCombat(Player player) {
	    List<Item> displayItems = getDisplayItems();
	    List<Item> consumables = new ArrayList<>();

	    for (Item item : displayItems) {
	        if (item instanceof Consumable) {
	            consumables.add(item);
	        }
	    }

	    if (consumables.isEmpty()) {
	        Console.println(Console.YELLOW, "You have no consumables.");
	        return false;
	    }

	    Console.println(Console.BOLD_WHITE, "\n=== CONSUMABLES ===");

	    for (int i = 0; i < consumables.size(); i++) {
	        Item item = consumables.get(i);
	        int count = getNumberOfItems(item);

	        String name = item.getName();
	        if (count > 1) {
	            name += " x" + count;
	        }

	        System.out.printf("%d. %s - %s%s%s%n",
	            i + 1,
	            name,
	            Console.ITALIC,
	            item.getDescription(),
	            Console.RESET
	        );
	    }

	    System.out.println("0. Cancel");

	    int choice = Console.errCheckInt(">>> ", 0, consumables.size());

	    if (choice == 0) return false;

	    Item selected = consumables.get(choice - 1);

	    int realIndex = getItemIndex(selected.getName());

	    selected.use(player);
	    items.remove(realIndex);

	    return true;
	}
	
	

	public void manageInventory(Player player) {
		while (true) {
			Inventory inv = player.getInventory();
	
			if (inv.isEmpty()) {
				Console.println(Console.YELLOW, "Inventory is empty.");
				return;
			}
			
			List <Item> displayItems = inv.getDisplayItems();
	
			inv.displayInventory(player);
			System.out.println(Console.BOLD_WHITE + "0." + Console.RESET + "  [Exit inventory]");
			int choice = Console.errCheckInt(">>> ", 0, displayItems.size());
	
			if (choice == 0) {
				Console.println(Console.YELLOW, "Closed inventory.");
				return;
			}
			
			int index = choice - 1;
			
			
			Item item = displayItems.get(index);
			
			int realIndex = inv.getItemIndex(item.getName());
	
			if (item instanceof Consumable) {
				inv.useItem(realIndex, player);
				
			} else if (item instanceof Equipment) {
				inv.toggleEquip(realIndex, player);
			}
		}
	}

	public List<Item> getItems() { return items; }
	public boolean isEmpty() { return items.isEmpty(); }
	public int getSize() { return items.size(); }

}