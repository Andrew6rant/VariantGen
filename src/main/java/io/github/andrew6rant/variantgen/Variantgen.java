package io.github.andrew6rant.variantgen;

//import net.devtech.arrp.api.RuntimeResourcePack;
import io.github.andrew6rant.variantgen.craftingtable.CraftingTable;
import io.github.andrew6rant.variantgen.generatednames.WoodNames;
import io.github.andrew6rant.variantgen.util.OwoLib;
import io.wispforest.owo.itemgroup.Icon;
import io.wispforest.owo.itemgroup.OwoItemGroup;
import io.wispforest.owo.itemgroup.OwoItemSettings;
import io.wispforest.owo.itemgroup.gui.ItemGroupButton;
import io.wispforest.owo.itemgroup.gui.ItemGroupTab;
import io.wispforest.owo.registration.RegistryHelper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Variantgen implements ModInitializer {

    public static final OwoItemGroup ITEMGROUP = new OwoLib(Variantgen.id("general"));

    public static void registerBlock(String path, Block block, int group) {
        Registry.register(Registry.BLOCK, new Identifier("variantgen", path), block);
        Registry.register(Registry.ITEM, new Identifier("variantgen", path), new BlockItem(block, new OwoItemSettings().group(Variantgen.ITEMGROUP).tab(group)));
    }

    List<CraftingTable> vanilla_crafting_tables = new ArrayList<>();
    List<CraftingTable> blockus_crafting_tables = new ArrayList<>();

    public Variantgen(){
        for(int i = 0; i < WoodNames.minecraft.size(); i++) {
            vanilla_crafting_tables.add(new CraftingTable(FabricBlockSettings.copyOf(Blocks.CRAFTING_TABLE)));
        }
        if(FabricLoader.getInstance().isModLoaded("blockus")){
            for(int i = 0; i < WoodNames.blockus.size(); i++){
                blockus_crafting_tables.add(new CraftingTable(FabricBlockSettings.copyOf(Blocks.CRAFTING_TABLE)));
            }
        }
    }
    @Override
    public void onInitialize() {
        for(int i = 0; i < WoodNames.minecraft.size(); i++) {
            registerBlock(WoodNames.minecraft.get(i)+"_crafting_table", vanilla_crafting_tables.get(i), 0);
        }

        if(FabricLoader.getInstance().isModLoaded("blockus")){
            for(int i = 0; i < WoodNames.blockus.size(); i++) {
                registerBlock(WoodNames.blockus.get(i)+"_crafting_table", blockus_crafting_tables.get(i), 1);
            }
        }
        ITEMGROUP.initialize();
    }
    public static Identifier id(String path) {
        return new Identifier("variantgen", path);
    }
}
