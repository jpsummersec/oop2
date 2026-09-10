package com.nhlstenden.kingdomsquests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SpecialQuest extends Quest
{
    private List<Item> itemRewards;

    public SpecialQuest(
            String title,
            int experiencePointReward,
            int difficulty,
            Character opponent,
            List<Item> itemRewards
    )
    {
        super(title, experiencePointReward, difficulty, opponent);
        this.setItemRewards(itemRewards);
    }

    public List<Item> getItemRewards()
    {
        return Collections.unmodifiableList(this.itemRewards);
    }

    public void setItemRewards(List<Item> itemRewards)
    {
        this.itemRewards = new ArrayList<>(Objects.requireNonNull(itemRewards, "Item rewards cannot be null"));
    }

    @Override
    public void giveRewards(Player player)
    {
        super.giveRewards(player);
        for (Item item : this.itemRewards)
        {
            player.addItem(item);
        }
    }

    public void addItemReward(Item item)
    {
        this.itemRewards.add(Objects.requireNonNull(item, "Item reward cannot be null"));
    }
}
