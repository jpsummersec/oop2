package com.nhlstenden.kingdomsquests;

import java.util.List;

public class Main
{
    public static void main(String[] arguments)
    {
        Player player = new Player("Ayla", 100);

        Character warrior = player.createCharacter(CharacterType.WARRIOR, "Bran");
        Character mage = player.createCharacter(CharacterType.MAGE, "Mira");
        Character archer = player.createCharacter(CharacterType.ARCHER, "Rowan");

        Character goblin = new Warrior("Goblin", 8, 2, 4);
        Character dragon = new Mage("Dragon", 20, 12, 8);

        Quest goblinPatrol = new Quest("Goblin Patrol", 80, 2, goblin);
        SpecialQuest dragonRuins = new SpecialQuest(
                "Dragon Ruins",
                140,
                10,
                dragon,
                List.of(new Item("Flame Sword"), new Item("Dragon Scale"))
        );

        List<Quest> questList = List.of(goblinPatrol, dragonRuins);

        System.out.println("Available quests:");
        for (Quest quest : player.viewAvailableQuests(questList))
        {
            System.out.println("- " + quest.getTitle()
                    + " (requires " + quest.calculateRequiredExperiencePoints() + " XP)");
        }

        warrior.useSpecialAbility();
        warrior.attack(goblin);

        mage.useSpecialAbility();
        mage.defend(25);

        archer.useSpecialAbility();
        archer.attack(goblin);

        player.playQuest(goblinPatrol, warrior);
        player.playQuest(dragonRuins, warrior);

        if (player.canLevelUp())
        {
            player.levelUp();
        }

        Main.require(player.getLevel() == 2, "Player should reach level 2");
        Main.require(player.getExperiencePoints() == 120, "Player should have 120 XP after levelling");
        Main.require(player.getInventory().size() == 2, "Special quest should award two items");

        System.out.println("Final level: " + player.getLevel());
        System.out.println("Remaining XP: " + player.getExperiencePoints());
        System.out.println("Inventory: " + player.getInventory());
        System.out.println("All checks passed.");
    }

    private static void require(boolean condition, String message)
    {
        if (!condition)
        {
            throw new IllegalStateException(message);
        }
    }
}
