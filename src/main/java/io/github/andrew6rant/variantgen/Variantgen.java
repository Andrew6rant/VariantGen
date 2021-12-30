package io.github.andrew6rant.variantgen;

import io.github.andrew6rant.variantgen.craftingtable.CraftingTable;
import io.github.andrew6rant.variantgen.generatednames.Names;
import io.github.andrew6rant.variantgen.util.BlockGenerator;
import io.github.andrew6rant.variantgen.util.OwoLib;
import io.github.andrew6rant.variantgen.util.ResourceGen;
import io.wispforest.owo.itemgroup.OwoItemGroup;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.tags.JTag;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.*;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class Variantgen implements ModInitializer {
    public static final String ID = "variantgen";
    public static final boolean CLIENT = FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    public static RuntimeResourcePack RESOURCEPACK = RuntimeResourcePack.create(ID + ":rrp");
    public static JTag crafting_table_tags = JTag.tag();
    public static JTag piston_tags = JTag.tag();

    public static final OwoItemGroup ITEMGROUP = new OwoLib(Variantgen.id("general"));

    public static final PistonBlock EXAMPLE_BLOCK = new PistonBlock(true, FabricBlockSettings.copyOf(Blocks.PISTON));

    List<CraftingTable> new_crafting_tables = new ArrayList<>();
    List<PistonBlock> new_pistons = new ArrayList<>();

    public Variantgen(){
        Names.WOOD_NAMES.forEach((mod_id, list) -> {
            if(FabricLoader.getInstance().isModLoaded(mod_id)){
                list.forEach((wood_type)-> {
                    new_crafting_tables.add(new CraftingTable(FabricBlockSettings.copyOf(Blocks.CRAFTING_TABLE)));
                    new_pistons.add(new PistonBlock(false, FabricBlockSettings.copyOf(Blocks.PISTON)));
                    //new_pistons.add(new PistonBlock(true, FabricBlockSettings.copyOf(Blocks.PISTON)));
                });
            }
        });
    }


    @Override
    public void onInitialize() {
        BlockGenerator.init();
        //AtomicInteger total_position = new AtomicInteger();

        Names.WOOD_NAMES.forEach((mod_id_wood, list_wood) -> {
            if(FabricLoader.getInstance().isModLoaded(mod_id_wood)){
                for (String name_wood : list_wood) {
                    BlockGenerator.genCraftingTables(mod_id_wood, name_wood);
                    //total_position.getAndIncrement();
                    Names.COBBLED_STONE_NAMES.forEach((mod_id_cobb, list_cobb)-> {
                        for (String name_cobb : list_cobb) {
                            BlockGenerator.genPistons(mod_id_wood, mod_id_cobb, name_wood, name_cobb);
                        }
                    });
                }


            }
        });
        BlockGenerator.initTag("c:items/workbench", crafting_table_tags);
        BlockGenerator.initTag("blocks/mineable/axe", crafting_table_tags);
        BlockGenerator.initTag("blocks/mineable/pickaxe", piston_tags);
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
