package com.nhlstenden.kingdomsquests;

import java.util.Objects;

public abstract class Character
{
    private String name;
    private int attackPower;
    private int defensePower;

    protected Character(String name, int attackPower, int defensePower)
    {
        this.setName(name);
        this.setAttackPower(attackPower);
        this.setDefensePower(defensePower);
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = Objects.requireNonNull(name, "Character name cannot be null");
    }

    public int getAttackPower()
    {
        return this.attackPower;
    }

    public void setAttackPower(int attackPower)
    {
        if (attackPower < 0)
        {
            throw new IllegalArgumentException("Attack power cannot be negative");
        }

        this.attackPower = attackPower;
    }

    public int getDefensePower()
    {
        return this.defensePower;
    }

    public void setDefensePower(int defensePower)
    {
        if (defensePower < 0)
        {
            throw new IllegalArgumentException("Defense power cannot be negative");
        }

        this.defensePower = defensePower;
    }

    public int attack(Character target)
    {
        Objects.requireNonNull(target, "Attack target cannot be null");
        int damage = this.attackPower;
        System.out.println(this.name + " attacks " + target.getName() + " for " + damage + " damage.");

        return damage;
    }

    public int defend(int incomingDamage)
    {
        int damageTaken = Math.max(0, incomingDamage - this.defensePower);
        System.out.println(this.name + " defends and takes " + damageTaken + " damage.");

        return damageTaken;
    }

    public abstract void useSpecialAbility();
}
