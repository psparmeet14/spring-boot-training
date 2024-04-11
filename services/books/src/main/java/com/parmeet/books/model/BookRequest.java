package com.parmeet.books.model;

import jakarta.annotation.Nonnull;

public class BookRequest {
    // add three properties id, name, sortIndex and  generate getters and setters
    private String id;
    @Nonnull
    private String name;
    @Nonnull
    private int sortIndex;

    public BookRequest(String id, String name, int sortIndex) {
        this.id = id;
        this.name = name;
        this.sortIndex = sortIndex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(int sortIndex) {
        this.sortIndex = sortIndex;
    }

}
