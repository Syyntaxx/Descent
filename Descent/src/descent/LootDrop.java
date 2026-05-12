package descent;

import descent.item.*;

public class LootDrop {

    private Item item;
    private int amount;

    public LootDrop(Item item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    public Item getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }
}