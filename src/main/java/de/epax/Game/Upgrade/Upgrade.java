package de.epax.Game.Upgrade;

public class Upgrade {
    private String name;
    private long cost;
    private int addsPerClickIncrease;
    private int level;

    public Upgrade(String name, long cost, int addsPerClickIncrease, int level) {
        this.name = name;
        this.cost = cost;
        this.addsPerClickIncrease = addsPerClickIncrease;
        this.level = level;
    }

    public Upgrade(String name, long cost, int addsPerClickIncrease) {
        this(name, cost, addsPerClickIncrease, 0);
    }

    public String getName() {
        return name;
    }

    public long getCost() {
        return cost;
    }

    public int getAddsPerClickIncrease() {
        return addsPerClickIncrease * level;
    }

    public int getLevel() {
        return level;
    }

    public boolean canBuy(long clicks) {
        return clicks >= cost;
    }


    public long buy(long clicks) {
        if (canBuy(clicks)) {
            clicks -= cost;
            level++;
            cost = (long)(cost * 1.5);
        }

        return clicks;
    }


    public void reduceCost(long amount) {
        cost -= amount;
        if (cost < 0) {
            cost = 1;
        }

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public void setAddsPerClickIncrease(int addsPerClickIncrease) {
        this.addsPerClickIncrease = addsPerClickIncrease;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
