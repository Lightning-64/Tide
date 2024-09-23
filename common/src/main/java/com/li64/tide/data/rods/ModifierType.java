package com.li64.tide.data.rods;

public enum ModifierType {

    BOBBER("bobber"), LINE("line"), HOOK("hook");

    private final String id;

    ModifierType(String id) {
        this.id = id;
    }

    public String getID() {
        return this.id;
    }
}
