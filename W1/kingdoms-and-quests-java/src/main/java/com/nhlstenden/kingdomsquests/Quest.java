package com.nhlstenden.kingdomsquests;

import java.util.Objects;

public class Quest
{
    private String title;
    private int experiencePointReward;
    private int difficulty;
    private Character opponent;

    public Quest(String title, int experiencePointReward, int difficulty, Character opponent)
    {
        this.setTitle(title);
        this.setExperiencePointReward(experiencePointReward);
        this.setDifficulty(difficulty);
        this.setOpponent(opponent);
    }

    public String getTitle()
    {
        return this.title;
    }

    public void setTitle(String title)
    {
        this.title = Objects.requireNonNull(title, "Quest title cannot be null");
    }

    public int getExperiencePointReward()
    {
        return this.experiencePointReward;
    }

    public void setExperiencePointReward(int experiencePointReward)
    {
        if (experiencePointReward < 0)
        {
            throw new IllegalArgumentException("XP reward cannot be negative");
        }

        this.experiencePointReward = experiencePointReward;
    }

    public int getDifficulty()
    {
        return this.difficulty;
    }

    public void setDifficulty(int difficulty)
    {
        if (difficulty < 0)
        {
            throw new IllegalArgumentException("Difficulty cannot be negative");
        }

        this.difficulty = difficulty;
    }

    public Character getOpponent()
    {
        return this.opponent;
    }

    public void setOpponent(Character opponent)
    {
        this.opponent = Objects.requireNonNull(opponent, "Quest opponent cannot be null");
    }

    public int calculateRequiredExperiencePoints()
    {
        return this.difficulty * 10;
    }

    public boolean canBePlayedBy(Player player)
    {
        Objects.requireNonNull(player, "Player cannot be null");

        return player.getExperiencePoints() >= this.calculateRequiredExperiencePoints();
    }

    public void complete(Player player)
    {
        this.giveRewards(player);
    }

    public void giveRewards(Player player)
    {
        Objects.requireNonNull(player, "Player cannot be null");
        player.gainExperiencePoints(this.experiencePointReward);
    }
}
