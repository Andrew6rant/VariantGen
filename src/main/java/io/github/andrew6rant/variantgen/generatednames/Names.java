package io.github.andrew6rant.variantgen.generatednames;

import com.mojang.datafixers.util.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class Names {
    public static final Map<String, List<String>> WOOD_NAMES = Map.ofEntries(
            entry("minecraft", Arrays.asList("Oak", "Spruce", "Birch", "Jungle", "Acacia", "Dark Oak", "Crimson", "Warped")),
            entry("blockus", Arrays.asList("Bamboo", "Charred", "White Oak", "Legacy"))
    );
    public static final Map<String, List<String>> STONE_NAMES = Map.ofEntries(
            entry("minecraft", Arrays.asList("Stone", "Sandstone", "Red Sandstone", "Deepslate", "Andesite", "Diorite", "Granite", "Tuff", "Netherrack", "Calcite")),
            entry("blockus", Arrays.asList("Limestone", "Marble", "Bluestone", "Soul Sandstone"))
    );
    public static final Map<String, List<String>> COBBLED_STONE_NAMES = Map.ofEntries(
            entry("minecraft", List.of("Cobblestone")), // Mossy Cobblestone maybe, , "Cobbled Deepslate", "Blackstone" temp removal
            entry("blockus", List.of("Legacy Cobblestone")), // Legacy Mossy Cobblestone maybe
            entry("consistency_plus", Arrays.asList("Cobbled Andesite", "Cobbled Calcite", "Cobbled Diorite", "Cobbled Granite", "Cobbled Tuff"))
            // "Cobbled Blackstone", "Cobbled Dripstone", "Cobbled End Stone", "Cobbled Netherrack" maybe
            // cobbled_black_terracotta, cobbled_blue_terracotta, cobbled_brown_terracotta, cobbled_crimson_wart, cobbled_cyan_terracotta, cobbled_gray_terracotta, cobbled_green_terracotta,
            // cobbled_light_blue_terracotta, cobbled_light_gray_terracotta, cobbled_lime_terracotta, cobbled_magenta_terracotta, cobbled_orange_terracotta, cobbled_pink_terracotta,
            // cobbled_purple_terracotta, cobbled_red_terracotta, cobbled_terracotta, cobbled_warped_wart, cobbled_white_terracotta, cobbled_yellow_terracotta maaaybe
    );
}