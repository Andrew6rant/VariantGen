package io.github.andrew6rant.variantgen.generatednames;

import io.github.andrew6rant.variantgen.Variantgen;
import io.github.andrew6rant.variantgen.util.ResourceGen;
import io.github.andrew6rant.variantgen.util.ResourceGenerateable;
import io.wispforest.owo.itemgroup.OwoItemSettings;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static io.github.andrew6rant.variantgen.Variantgen.ID;
import static net.devtech.arrp.api.RuntimeResourcePack.id;
import static net.devtech.arrp.json.recipe.JPattern.pattern;
import static net.minecraft.advancement.criterion.ConsumeItemCriterion.Conditions.item;

public interface UBlocks {
    // thanks to Devan-Kerman for the ARRP example code below
    ResourceGenerateable.Block DEFAULT = new ResourceGenerateable.Block() {};

    static <T extends Block> T register(ResourceGenerateable.Block resource, T block, String id, String en_us, int group) {
        Identifier identifier = new Identifier(ID, id);
        resource.init(block);
        ResourceGen.registerClient(r -> resource.client(r, identifier));
        ResourceGen.registerServer(r -> resource.server(r, identifier));
        ResourceGen.registerLang("en_us", l -> l.block(identifier, en_us));
        Registry.register(Registry.ITEM, identifier, new BlockItem(block, new OwoItemSettings().group(Variantgen.ITEMGROUP).tab(group)));
        return Registry.register(Registry.BLOCK, identifier, block);
    }

    static void init() {}
}
