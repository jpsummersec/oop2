package com.nhlstenden.kingdomsquests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Player
{
    public static final int EXPERIENCE_POINTS_NEEDED_TO_LEVEL_UP = 200;

    private String name;
    private List<Character> characters;
    private List<Item> inventory;
    private int experiencePoints;
    private int level;

    public Player(String name, int startingExperiencePoints)
    {
        this.setName(name);
        this.setExperiencePoints(startingExperiencePoints);
        this.setLevel(1);
        this.characters = new ArrayList<>();
        this.inventory = new ArrayList<>();
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = Objects.requireNonNull(name, "Player name cannot be null");
    }

    public List<Character> getCharacters()
    {
        return Collections.unmodifiableList(this.characters);
    }

    public void setCharacters(List<Character> characters)
    {
        this.characters = new ArrayList<>(Objects.requireNonNull(characters, "Characters cannot be null"));
    }

    public List<Item> getInventory()
    {
        return Collections.unmodifiableList(this.inventory);
    }

    public void setInventory(List<Item> inventory)
    {
        this.inventory = new ArrayList<>(Objects.requireNonNull(inventory, "Inventory cannot be null"));
    }

    public int getExperiencePoints()
    {
        return this.experiencePoints;
    }

    public void setExperiencePoints(int experiencePoints)
    {
        if (experiencePoints < 0)
        {
            throw new IllegalArgumentException("XP cannot be negative");
        }

        this.experiencePoints = experiencePoints;
    }

    public int getLevel()
    {
        return this.level;
    }

    public void setLevel(int level)
    {
        if (level < 1)
        {
            throw new IllegalArgumentException("Level must be at least 1");
        }

        this.level = level;
    }

    public Character createCharacter(CharacterType type, String characterName)
    {
        Objects.requireNonNull(type, "Character type cannot be null");

        Character character = switch (type)
        {
            case WARRIOR -> new Warrior(characterName, 15, 8, 10);
            case MAGE -> new Mage(characterName, 10, 10, 10);
            case ARCHER -> new Archer(characterName, 12, 7, 2);
        };

        this.characters.add(character);

        return character;
    }

    public List<Quest> viewAvailableQuests(List<Quest> quests)
    {
        Objects.requireNonNull(quests, "Quest list cannot be null");

        return quests.stream()
                .filter(quest -> quest.canBePlayedBy(this))
                .toList();
    }

    public void playQuest(Quest quest, Character selectedCharacter)
    {
        Objects.requireNonNull(quest, "Quest cannot be null");
        Objects.requireNonNull(selectedCharacter, "Selected character cannot be null");

        if (!this.characters.contains(selectedCharacter))
        {
            throw new IllegalArgumentException("The selected character does not belong to this player");
        }

        if (!quest.canBePlayedBy(this))
        {
            throw new IllegalStateException(
                    this.name + " needs " + quest.calculateRequiredExperiencePoints() + " XP to play " + quest.getTitle()
            );
        }

        System.out.println(this.name + " plays " + quest.getTitle()
                + " with " + selectedCharacter.getName()
                + " against " + quest.getOpponent().getName() + ".");
        quest.complete(this);
    }

    public void gainExperiencePoints(int amount)
    {
        if (amount < 0)
        {
            throw new IllegalArgumentException("XP gained cannot be negative");
        }

        this.experiencePoints += amount;
        System.out.println(this.name + " gained " + amount + " XP.");
    }

    public boolean canLevelUp()
    {
        return this.experiencePoints >= Player.EXPERIENCE_POINTS_NEEDED_TO_LEVEL_UP;
    }

    public void levelUp()
    {
        if (!this.canLevelUp())
        {
            throw new IllegalStateException("The player needs 200 XP to level up");
        }

        this.experiencePoints -= Player.EXPERIENCE_POINTS_NEEDED_TO_LEVEL_UP;
        this.level++;
        System.out.println(this.name + " reached level " + this.level + ".");
    }

    public void addItem(Item item)
    {
        this.inventory.add(Objects.requireNonNull(item, "Item cannot be null"));
        System.out.println(this.name + " received " + item.getTitle() + ".");
    }
}
