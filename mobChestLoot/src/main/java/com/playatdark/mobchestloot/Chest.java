package com.playatdark.mobchestloot;

import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import java.util.List;


public class Chest {

    private Item entity;
    private Location location;
    private List<ItemStack> contents;
    private Integer entityID;

    private Inventory inventory;

    private Player owner;

    public Chest(Item entitiy, Location location, List<ItemStack> contents, Integer entityID, Inventory inventory, Player owner){

        this.entity = entitiy;
        this.location = location;
        this.contents = contents;
        this.entityID = entityID;
        this.inventory = inventory;
        this.owner = owner;


    }

    //Getters and Setters

    public Item getEntity() {
        return entity;
    }

    public void setEntity(Item entity) {
        this.entity = entity;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<ItemStack> getContents() {
        return contents;
    }

    public void setContents(List<ItemStack> contents) {
        this.contents = contents;
    }

    public Integer getEntityID() {
        return entityID;
    }

    public void setEntityID(Integer entityID) {
        this.entityID = entityID;
    }

    public Inventory getInventory() {
        return inventory;
    }

    // Setter for the Inventory
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Player getOwner() {
        return owner;
    }

    // Setter for the Inventory
    public void setOwner(Player owner) {
        this.owner = owner;
    }

}
