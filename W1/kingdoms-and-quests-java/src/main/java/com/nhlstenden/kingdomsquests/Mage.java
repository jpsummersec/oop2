package com.nhlstenden.kingdomsquests;

public class Mage extends Character
{
    private int defenseBoost;
    private boolean defenseBoostActive;

    public Mage(String name, int attackPower, int defensePower, int defenseBoost)
    {
        super(name, attackPower, defensePower);
        this.setDefenseBoost(defenseBoost);
    }

    public int getDefenseBoost()
    {
        return this.defenseBoost;
    }

    public void setDefenseBoost(int defenseBoost)
    {
        if (defenseBoost < 0)
        {
            throw new IllegalArgumentException("Defense boost cannot be negative");
        }

        this.defenseBoost = defenseBoost;
    }

    public boolean isDefenseBoostActive()
    {
        return this.defenseBoostActive;
    }

    public void setDefenseBoostActive(boolean defenseBoostActive)
    {
        this.defenseBoostActive = defenseBoostActive;
    }

    @Override
    public void useSpecialAbility()
    {
        this.defenseBoostActive = true;
        System.out.println(this.getName() + " increases defence for the next attack.");
    }

    @Override
    public int defend(int incomingDamage)
    {
        int totalDefense = this.getDefensePower();

        if (this.defenseBoostActive)
        {
            totalDefense += this.defenseBoost;
            this.defenseBoostActive = false;
        }

        int damageTaken = Math.max(0, incomingDamage - totalDefense);
        System.out.println(this.getName() + " defends and takes " + damageTaken + " damage.");

        return damageTaken;
    }
}
