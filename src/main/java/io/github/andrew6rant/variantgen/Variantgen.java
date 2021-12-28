package io.github.andrew6rant.variantgen;

//import net.devtech.arrp.api.RuntimeResourcePack;
import io.github.andrew6rant.variantgen.craftingtable.CraftingTable;
import io.github.andrew6rant.variantgen.generatednames.Names;
import io.github.andrew6rant.variantgen.util.OwoLib;
import io.wispforest.owo.itemgroup.OwoItemGroup;
import io.wispforest.owo.itemgroup.OwoItemSettings;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class Variantgen implements ModInitializer {

    public static final OwoItemGroup ITEMGROUP = new OwoLib(Variantgen.id("general"));

    public static void registerBlock(String path, Block block, int group) {
        Registry.register(Registry.BLOCK, new Identifier("variantgen", path), block);
        Registry.register(Registry.ITEM, new Identifier("variantgen", path), new BlockItem(block, new OwoItemSettings().group(Variantgen.ITEMGROUP).tab(group)));
    }
    List<CraftingTable> new_crafting_tables = new ArrayList<>();

    public Variantgen(){
        Names.WOOD_NAMES.forEach((mod_id, list) -> {
            if(FabricLoader.getInstance().isModLoaded(mod_id)){
                list.forEach((wood_type)-> {
                    new_crafting_tables.add(new CraftingTable(FabricBlockSettings.copyOf(Blocks.CRAFTING_TABLE)));
                });
            }
        });
    }
    @Override
    public void onInitialize() {
        AtomicInteger total_position = new AtomicInteger();
        Names.WOOD_NAMES.forEach((mod_id, list) -> {
            if(FabricLoader.getInstance().isModLoaded(mod_id)){
                for (String name : list) {
                    registerBlock(name+"_crafting_table", new_crafting_tables.get(total_position.get()), 0);
                    total_position.getAndIncrement();
                }
            }
        });
        ITEMGROUP.initialize();
    }
    public static Identifier id(String path) {
        return new Identifier("variantgen", path);
    }
}
