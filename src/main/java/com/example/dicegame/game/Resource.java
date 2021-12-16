package com.example.dicegame.game;

public class Resource {
    private boolean blueResource;

    public boolean isBlueResource() {
        return blueResource;
    }

    public void setBlueResource(boolean blueResource) {
        this.blueResource = blueResource;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "blueResource=" + blueResource +
                "}\n";
    }
}
