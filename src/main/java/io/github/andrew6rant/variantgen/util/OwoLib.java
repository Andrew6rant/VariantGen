package io.github.andrew6rant.variantgen.util;

import io.wispforest.owo.itemgroup.Icon;
import io.wispforest.owo.itemgroup.OwoItemGroup;
import io.wispforest.owo.itemgroup.gui.ItemGroupTab;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class OwoLib extends OwoItemGroup {
    public OwoLib(Identifier id) {
        super(id);
    }
    @Override
    protected void setup() {
        this.addTab(Icon.of(Blocks.CRAFTING_TABLE), "crafting_tables", ItemGroupTab.EMPTY); // group 0
        this.addTab(Icon.of(Blocks.PISTON), "pistons", ItemGroupTab.EMPTY); // group 1
        this.addTab(Icon.of(Blocks.FURNACE), "furnaces", ItemGroupTab.EMPTY); // group 2
        //this.addButton(ItemGroupButton.github("https://github.com/"));
    }
    @Override
    public ItemStack createIcon() {
        return new ItemStack(Blocks.CRAFTING_TABLE);
    }
}
