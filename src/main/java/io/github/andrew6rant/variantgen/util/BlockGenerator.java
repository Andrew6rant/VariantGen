package io.github.andrew6rant.variantgen.util;

import io.github.andrew6rant.variantgen.Variantgen;
import io.github.andrew6rant.variantgen.craftingtable.CraftingTable;
import io.wispforest.owo.itemgroup.OwoItemSettings;
import net.devtech.arrp.json.loot.JLootTable;
import net.devtech.arrp.json.recipe.*;
import net.devtech.arrp.json.tags.JTag;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static net.devtech.arrp.api.RuntimeResourcePack.id;
import static net.devtech.arrp.json.recipe.JPattern.pattern;

public interface BlockGenerator {

    static void genCraftingTables(String mod_id, String name) {
        String formatted_name = name.replaceAll("\\s+", "_").toLowerCase();
        String id = formatted_name+"_crafting_table";
        ResourceGenerateable.Block CRAFTING_TABLE = new ResourceGenerateable.UniqueFaces(id+"_top", id+"_north", id+"_south", id+"_east", id+"_west", id+"_top", id+"_bottom") {};
        BlockGenerator.register(CRAFTING_TABLE, new CraftingTable(FabricBlockSettings.copyOf(Blocks.CRAFTING_TABLE)), id, name+" Crafting Table", 0);
        addBlockLootTable(id);
        JRecipe crafting_table_recipe = JRecipe.shaped(JPattern.pattern(
                "XX",
                "XX"),
                JKeys.keys().key("X", JIngredient.ingredient().item(mod_id+":"+formatted_name+"_planks")),
                JResult.result(Variantgen.ID+":"+id));
        Variantgen.RESOURCEPACK.addRecipe(new Identifier(id), crafting_table_recipe);
        Variantgen.crafting_table_tags.add(new Identifier(Variantgen.ID+":"+id));
    }

    static void genPistons(String mod_id_wood, String mod_id_cobb, String name_wood, String name_cobb) {
        String formatted_name_wood = name_wood.replaceAll("\\s+", "_").toLowerCase();
        String formatted_name_cobb = name_cobb.replaceAll("\\s+", "_").toLowerCase();
        String name_wood_cobb = name_wood + " " + name_cobb;
        String formatted_name_wood_cobb = name_wood_cobb.replaceAll("\\s+", "_").toLowerCase();
        String id = formatted_name_wood_cobb+"_piston";
        String id_sticky = formatted_name_wood_cobb+"_sticky_piston";

        ResourceGenerateable.Piston PISTON = new ResourceGenerateable.Piston(formatted_name_cobb+"_piston_base_bottom", formatted_name_cobb+"_piston_base_side", formatted_name_wood+"_piston_platform_side", formatted_name_wood+"_piston_platform_top", formatted_name_cobb+"_piston_base_bottom") {};
        BlockGenerator.register(PISTON, new PistonBlock(false, FabricBlockSettings.copyOf(Blocks.PISTON)), id, name_wood_cobb+" Piston", 1);
        addBlockLootTable(id);
        //addBlockLootTable(id_sticky);
        JRecipe piston_recipe = JRecipe.shaped(JPattern.pattern(
                        "XXX",
                        "CIC",
                        "CRC"),
                JKeys.keys()
                        .key("X", JIngredient.ingredient().item(mod_id_wood+":"+name_wood.replaceAll("\\s+", "_").toLowerCase()+"_planks"))
                        .key("C", JIngredient.ingredient().item(mod_id_cobb+":"+name_cobb.replaceAll("\\s+", "_").toLowerCase()))
                        .key("I", JIngredient.ingredient().item("minecraft:iron_ingot")) //.tag("c:iron_ingots"))
                        .key("R", JIngredient.ingredient().item("minecraft:redstone")), //.tag("c:redstone_dusts"))
                JResult.result(Variantgen.ID+":"+id));
        Variantgen.RESOURCEPACK.addRecipe(new Identifier(id), piston_recipe);
        //Variantgen.RESOURCEPACK.addRecipe(new Identifier(id_sticky), sticky_piston_recipe);
        Variantgen.piston_tags.add(new Identifier(Variantgen.ID+":"+id));
    }

    static void initTag(String path, JTag jtag){
        Variantgen.RESOURCEPACK.addTag(new Identifier(path), jtag);
    }

    static void addBlockLootTable(String blockItemName) {
        JLootTable lootTable = JLootTable.loot("block").pool(JLootTable.pool()
                .rolls(1)
                .entry(JLootTable.entry().type("item")
                        .name(Variantgen.ID + ":"+ blockItemName))
                .condition(JLootTable.predicate("survives_explosion")));
        Variantgen.RESOURCEPACK.addLootTable(new Identifier(Variantgen.ID + ":blocks/" + blockItemName), lootTable);
    }

    static <T extends Block> T register(ResourceGenerateable.Block resource, T block, String id, String en_us, int group) {
        Identifier identifier = new Identifier(Variantgen.ID, id);
        resource.init(block);
        ResourceGen.registerClient(r -> resource.client(r, identifier));
        ResourceGen.registerServer(r -> resource.server(r, identifier));
        ResourceGen.registerLang("en_us", l -> l.block(identifier, en_us));
        Registry.register(Registry.ITEM, identifier, new BlockItem(block, new OwoItemSettings().group(Variantgen.ITEMGROUP).tab(group)));
        return Registry.register(Registry.BLOCK, identifier, block);
    }

    static void init() {}
}
