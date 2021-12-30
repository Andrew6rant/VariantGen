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
            entry("minecraft", Arrays.asList("Cobblestone", "Cobbled Deepslate", "Blackstone")), // Mossy Cobblestone maybe
            entry("blockus", List.of("Legacy Cobblestone")) // Legacy Mossy Cobblestone maybe
    );
}
