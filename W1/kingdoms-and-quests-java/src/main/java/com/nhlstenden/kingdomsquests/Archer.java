package com.nhlstenden.kingdomsquests;

import java.util.Objects;

public class Archer extends Character
{
    private int damageMultiplier;
    private boolean doubleDamageActive;

    public Archer(String name, int attackPower, int defensePower, int damageMultiplier)
    {
        super(name, attackPower, defensePower);
        this.setDamageMultiplier(damageMultiplier);
    }

    public int getDamageMultiplier()
    {
        return this.damageMultiplier;
    }

    public void setDamageMultiplier(int damageMultiplier)
    {
        if (damageMultiplier < 1)
        {
            throw new IllegalArgumentException("Damage multiplier must be at least 1");
        }

        this.damageMultiplier = damageMultiplier;
    }

    public boolean isDoubleDamageActive()
    {
        return this.doubleDamageActive;
    }

    public void setDoubleDamageActive(boolean doubleDamageActive)
    {
        this.doubleDamageActive = doubleDamageActive;
    }

    @Override
    public void useSpecialAbility()
    {
        this.doubleDamageActive = true;
        System.out.println(this.getName() + " prepares to deal double damage.");
    }

    @Override
    public int attack(Character target)
    {
        Objects.requireNonNull(target, "Attack target cannot be null");
        int damage = this.getAttackPower();

        if (this.doubleDamageActive)
        {
            damage *= this.damageMultiplier;
            this.doubleDamageActive = false;
        }

        System.out.println(this.getName() + " attacks " + target.getName() + " for " + damage + " damage.");

        return damage;
    }
}
