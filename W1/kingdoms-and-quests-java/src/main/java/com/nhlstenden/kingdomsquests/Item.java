package com.nhlstenden.kingdomsquests;

import java.util.Objects;

public class Item
{
    private String title;

    public Item(String title)
    {
        this.setTitle(title);
    }

    public String getTitle()
    {
        return this.title;
    }

    public void setTitle(String title)
    {
        this.title = Objects.requireNonNull(title, "Item title cannot be null");
    }

    @Override
    public String toString()
    {
        return this.title;
    }
}
