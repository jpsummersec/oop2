package com.nhlstenden.kingdomsquests;

import java.util.Objects;

public class Warrior extends Character
{
    private int temporaryAttackBoost;
    private boolean attackBoostActive;

    public Warrior(String name, int attackPower, int defensePower, int temporaryAttackBoost)
    {
        super(name, attackPower, defensePower);
        this.setTemporaryAttackBoost(temporaryAttackBoost);
    }

    public int getTemporaryAttackBoost()
    {
        return this.temporaryAttackBoost;
    }

    public void setTemporaryAttackBoost(int temporaryAttackBoost)
    {
        if (temporaryAttackBoost < 0)
        {
            throw new IllegalArgumentException("Attack boost cannot be negative");
        }

        this.temporaryAttackBoost = temporaryAttackBoost;
    }

    public boolean isAttackBoostActive()
    {
        return this.attackBoostActive;
    }

    public void setAttackBoostActive(boolean attackBoostActive)
    {
        this.attackBoostActive = attackBoostActive;
    }

    @Override
    public void useSpecialAbility()
    {
        this.attackBoostActive = true;
        System.out.println(this.getName() + " temporarily increases attack power.");
    }

    @Override
    public int attack(Character target)
    {
        Objects.requireNonNull(target, "Attack target cannot be null");
        int damage = this.getAttackPower();

        if (this.attackBoostActive)
        {
            damage += this.temporaryAttackBoost;
            this.attackBoostActive = false;
        }

        System.out.println(this.getName() + " attacks " + target.getName() + " for " + damage + " damage.");

        return damage;
    }
}
