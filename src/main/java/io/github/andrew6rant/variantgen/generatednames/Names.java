package io.github.andrew6rant.variantgen.generatednames;

import com.mojang.datafixers.util.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class Names {
    public static final Map<String, List<String>> WOOD_NAMES = Map.ofEntries(
            entry("minecraft", Arrays.asList("oak", "spruce", "birch", "jungle", "acacia", "dark_oak", "crimson", "warped")),
            entry("blockus", Arrays.asList("bamboo", "charred", "white_oak", "legacy"))
    );
    
}
