package com.example.omc.forumguru;

/**
 * Created by OM c on 15-01-2016.
 */
public class Item {
    private String title;
    private String description;

    public Item(String title, String description) {
        super();
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


