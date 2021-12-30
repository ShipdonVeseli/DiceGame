package com.example.dicegame.game;

import java.util.Objects;

public class Resource {
    private boolean blueResource;

    public Resource() {
    }

    public Resource(boolean blueResource) {
        this.blueResource = blueResource;
    }

    public boolean isBlueResource() {
        return blueResource;
    }

    public void setBlueResource(boolean blueResource) {
        this.blueResource = blueResource;
    }

    public String convertToJSON(){
        return "{"+"\"blueResource\": "+blueResource+"}";
    }

    @Override
    public String toString() {
        return "Resource{" +
                "blueResource=" + blueResource +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return blueResource == resource.blueResource;
    }

    @Override
    public int hashCode() {
        return Objects.hash(blueResource);
    }
}
