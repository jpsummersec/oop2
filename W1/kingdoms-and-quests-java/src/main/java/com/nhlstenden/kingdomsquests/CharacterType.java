package com.nhlstenden.kingdomsquests;

public enum CharacterType
{
    WARRIOR("Warrior"),
    MAGE("Mage"),
    ARCHER("Archer");

    private final String displayName;

    CharacterType(String displayName)
    {
        this.displayName = displayName;
    }

    public String getDisplayName()
    {
        return this.displayName;
    }
}
