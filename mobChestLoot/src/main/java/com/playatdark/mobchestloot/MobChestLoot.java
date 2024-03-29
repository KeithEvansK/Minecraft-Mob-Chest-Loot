package com.playatdark.mobchestloot;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.Bukkit;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;;
import org.bukkit.scheduler.BukkitRunnable;


import java.util.ArrayList;
import java.util.List;

public final class MobChestLoot extends JavaPlugin implements  Listener {

    private Location chestLocation;
    public Item chestEntity;

    private Inventory chestGUI;

    private List<Chest> chests = new ArrayList<>();



    @Override
    public void onEnable() {
        // Plugin startup logic

        getLogger().info("Plugin Start:");

        getServer().getPluginManager().registerEvents(this, this);


        new BukkitRunnable() {
            @Override
            public void run() {



                for (Chest chest : chests) {

                    if (chest.getEntity() != null && !chest.getEntity().isDead()) {

                        chest.setLocation(chest.getEntity().getLocation());
                    } else {

                        chests.remove(chest);

                    }
                }




                if (chestEntity != null && !chestEntity.isDead()) {

                    chestLocation = chestEntity.getLocation();

                }
            }
        }.runTaskTimer(this, 0L, 20L); // Update every second (20 ticks)


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    @EventHandler
    public void onMobDeath(EntityDeathEvent event){

        if(!event.getDrops().isEmpty()){

            List<ItemStack> cancelledItems = new ArrayList<>(event.getDrops());

            getLogger().info("Chest Dropped Contents " + cancelledItems);

            event.getDrops().clear();



            Location dropLocation = event.getEntity().getLocation();


            // The physical drop chest item on the ground.
            ItemStack chestItem = new ItemStack(Material.CHEST);

            // Drop the chest.
            chestEntity = dropLocation.getWorld().dropItemNaturally(dropLocation, chestItem);

            chestEntity.setPickupDelay(Integer.MAX_VALUE);
            //chestEntity.setGlowing(true);





            getLogger().info("Chest Dropped." + chestEntity.getEntityId());


            chestGUI = Bukkit.createInventory(null, 27, "Loot Inventory");





            for (ItemStack item : cancelledItems) {

                chestGUI.addItem(item);


            }




            Chest chest = new Chest(chestEntity, dropLocation, cancelledItems, chestEntity.getEntityId(), chestGUI, event.getEntity().getKiller());

            /////////////// combine chests
            List<Chest> removeChest = new ArrayList<>();
            for (Chest searchchest : chests) {

                if (chest.getLocation().distance(searchchest.getLocation()) < 1.0) {
                    if (chest.getOwner() == searchchest.getOwner()) {//if (chest.getOwner().equals(searchchest.getOwner())) { /////////// Replace .equals with == and retest. Doesn't work well with null
                        for (ItemStack item : searchchest.getInventory().getContents()) {
                            if (item != null) {
                                chest.getInventory().addItem(item);
                            }
                        }

                        removeChest.add(searchchest);
                        searchchest.getEntity().remove();
                    }

                }

            }

            if(removeChest.size() > 0){
                chests.remove(removeChest.getFirst());
            }
            /////// End combine chests

            chests.add(chest);
            Bukkit.getServer().broadcastMessage("Chest Saved");


        }

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        // Check if the action is a right-click
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getHand() == EquipmentSlot.HAND) {


            

            for (Chest chest : chests) {

                if (chest.getLocation() != null) {

                    if (event.getPlayer().getLocation().distance(chest.getLocation()) < 2.0) {
                        Bukkit.getServer().broadcastMessage("Chest Belongs to: " + chest.getOwner());
                        if (chest.getOwner() == event.getPlayer() || chest.getOwner() == null) {

                            event.getPlayer().openInventory(chest.getInventory());

                            Bukkit.getServer().broadcastMessage("Chest Loaded: " + chest.getEntityID() + "Index:" + chests.indexOf(chest));
                            break;

                        }

                    }

                }



            }

        }
    }


    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        // Check if the closed inventory is the special chest GUI
        if (event.getView().getTitle().equals("Loot Inventory")) {


                              



        for (Chest chest : chests) {
            if (chest.getInventory().equals(event.getInventory())) {
                //chest.getContents().clear(); // Clear the contents of the Chest object
                chest.getEntity().remove();
                chests.remove(chest);
                Bukkit.getServer().broadcastMessage("Chest Clear Check");
                break;
            }
        }





            getLogger().info("Chest Removed."+ chestEntity.getEntityId());



        }
    }

}