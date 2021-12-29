package io.github.andrew6rant.variantgen;

//import net.devtech.arrp.api.RuntimeResourcePack;
import io.github.andrew6rant.variantgen.craftingtable.CraftingTable;
import io.github.andrew6rant.variantgen.generatednames.Names;
import io.github.andrew6rant.variantgen.generatednames.UBlocks;
import io.github.andrew6rant.variantgen.util.OwoLib;
import io.github.andrew6rant.variantgen.util.ResourceGen;
import io.github.andrew6rant.variantgen.util.ResourceGenerateable;
import io.wispforest.owo.itemgroup.OwoItemGroup;
import io.wispforest.owo.itemgroup.OwoItemSettings;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.loot.JLootTable;
import net.devtech.arrp.json.recipe.*;
import net.devtech.arrp.json.tags.JTag;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class Variantgen implements ModInitializer {
    public static final String ID = "variantgen";
    public static final boolean CLIENT = FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    static RuntimeResourcePack RESOURCEPACK = RuntimeResourcePack.create(ID + ":rrp");

    public static final OwoItemGroup ITEMGROUP = new OwoLib(Variantgen.id("general"));

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

    static void addBlockLootTable(String blockItemName) {
        JLootTable lootTable = JLootTable.loot("block").pool(JLootTable.pool()
                .rolls(1)
                .entry(JLootTable.entry().type("item")
                .name(Variantgen.ID + ":"+ blockItemName))
                .condition(JLootTable.predicate("survives_explosion")));
        RESOURCEPACK.addLootTable(new Identifier(Variantgen.ID + ":blocks/" + blockItemName), lootTable);
    }

    @Override
    public void onInitialize() {
        UBlocks.init();
        AtomicInteger total_position = new AtomicInteger();
        Names.WOOD_NAMES.forEach((mod_id, list) -> {
            if(FabricLoader.getInstance().isModLoaded(mod_id)){
                for (String name : list) {
                    String id = name.replaceAll("\\s+", "_").toLowerCase()+"_crafting_table";
                    ResourceGenerateable.Block CRAFTING_TABLE = new ResourceGenerateable.UniqueFaces(id+"_top", id+"_north", id+"_south", id+"_east", id+"_west", id+"_top", id+"_bottom") {};
                    UBlocks.register(CRAFTING_TABLE, new CraftingTable(FabricBlockSettings.copyOf(Blocks.CRAFTING_TABLE)), id, name+" Crafting Table", 0);
                    addBlockLootTable(id);
                    JRecipe crafting_table_recipe = JRecipe.shaped(JPattern.pattern("XX", "XX"), JKeys.keys().key("X", JIngredient.ingredient().item(mod_id+":"+name.replaceAll("\\s+", "_").toLowerCase()+"_planks")), JResult.result(ID+":"+id));
                    RESOURCEPACK.addRecipe(new Identifier(id), crafting_table_recipe);
                    JTag crafting_table_tag = JTag.tag().add(new Identifier(ID+":"+id));
                    RESOURCEPACK.addTag(new Identifier("c:workstations"), crafting_table_tag); // this doesn't seem to work
                    total_position.getAndIncrement();
                }
            }
        });
        ITEMGROUP.initialize();
        ResourceGen.registerLang("en_us", r -> {});
        RRPCallback.EVENT.register(e -> {
            ResourceGen.init(RESOURCEPACK);
            e.add(RESOURCEPACK);
        });
    }
    public static Identifier id(String path) {
        return new Identifier("variantgen", path);
    }
}
