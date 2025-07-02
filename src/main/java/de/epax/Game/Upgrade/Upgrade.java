package de.epax.Game.Upgrade;

public class Upgrade {
    private String name;
    private double cost;
    private double baseAddsPerClickIncrease; // Base increase per level
    private int level;

    public Upgrade(String name, double cost, double baseAddsPerClickIncrease, int level) {
        this.name = name;
        this.cost = cost;
        this.baseAddsPerClickIncrease = baseAddsPerClickIncrease;
        this.level = level;
    }

    public Upgrade(String name, double cost, int baseAddsPerClickIncrease) {
        this(name, cost, baseAddsPerClickIncrease, 0);
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public double getAddsPerClickIncrease() {
        return baseAddsPerClickIncrease * level;
    }

    public int getLevel() {
        return level;
    }

    public boolean canBuy(double clicks) {
        return clicks >= cost;
    }

    public double buy(double clicks) {
        if (!canBuy(clicks)) return clicks;
        clicks -= cost;
        level++;
        cost *= 1.5; // Increase cost exponentially
        return clicks;
    }

    public void reduceCost(double amount) {
        cost = Math.max(1, cost - amount);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setBaseAddsPerClickIncrease(double baseAddsPerClickIncrease) {
        this.baseAddsPerClickIncrease = baseAddsPerClickIncrease;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}