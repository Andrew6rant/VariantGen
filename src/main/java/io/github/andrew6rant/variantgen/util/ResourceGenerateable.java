package io.github.andrew6rant.variantgen.util;

import io.github.andrew6rant.variantgen.Variantgen;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.loot.JLootTable;
import net.devtech.arrp.json.models.JModel;
import net.minecraft.util.Identifier;

import static io.github.andrew6rant.variantgen.util.ResourceGen.prefixPath;
import static net.devtech.arrp.json.blockstate.JState.state;
import static net.devtech.arrp.json.blockstate.JState.variant;
import static net.devtech.arrp.json.models.JModel.model;
import static net.devtech.arrp.json.models.JModel.textures;

public interface ResourceGenerateable {
    // thanks to Devan-Kerman for the ARRP example code below

    interface Item extends ResourceGenerateable {
        /**
         * @param item the item about to be processed
         */
        default void init(net.minecraft.item.Item item) {}

        @Override
        default void client(RuntimeResourcePack pack, Identifier id) {
            Identifier path = prefixPath(id, "item");
            pack.addModel(model("item/generated").textures(textures().layer0(path.toString())), path);
        }

        @Override
        default void server(RuntimeResourcePack pack, Identifier id) {

        }
    }

    interface Block extends ResourceGenerateable {
        /**
         * @param block the block about to be processed
         */
        default void init(net.minecraft.block.Block block) {}

        @Override
        default void client(RuntimeResourcePack pack, Identifier id) {
            this.generateBlockModel(pack, id);
            this.generateBlockState(pack, id);
            this.generateItemModel(pack, id);
        }

        @Override
        default void server(RuntimeResourcePack pack, Identifier id) {
            this.generateLootTable(pack, id);
        }

        default void generateItemModel(RuntimeResourcePack pack, Identifier id) {
            pack.addModel(JModel.model(prefixPath(id, "block").toString()), prefixPath(id, "item"));
        }

        default void generateBlockState(RuntimeResourcePack pack, Identifier id) {
            pack.addBlockState(state(variant(JState.model(prefixPath(id, "block").toString()))), id);
        }

        default void generateBlockModel(RuntimeResourcePack pack, Identifier id) {
            Identifier prefix = prefixPath(id, "block");
            pack.addModel(model("block/cube_all").textures(textures().var("all", prefix.toString())), prefix);
        }

        default void generateLootTable(RuntimeResourcePack pack, Identifier id) {
            pack.addLootTable(id,
                    JLootTable.loot("minecraft:block")
                            .pool(JLootTable.pool()
                                    .rolls(1)
                                    .entry(JLootTable.entry()
                                            .type("minecraft:item")
                                            .name(id.toString()))
                                    .condition(JLootTable.condition("minecraft:survives_explosion"))));
        }
    }
    class Piston implements Block {
        private final String base_bottom, base_side, platform_side, platform_top, particle;

        public Piston(String base_bottom, String base_side, String platform_side, String platform_top, String particle) {
            this.base_bottom = Variantgen.ID + ":block/" + base_bottom;
            this.base_side = Variantgen.ID + ":block/" + base_side;
            this.platform_side = Variantgen.ID + ":block/" + platform_side;
            this.platform_top = Variantgen.ID + ":block/" + platform_top;
            this.particle = Variantgen.ID + ":block/" + particle;
        }
        public static Piston of(String base_bottom, String base_side, String platform_side, String platform_top, String particle) {
            return new Piston(base_bottom, base_side, platform_side, platform_top, particle);
        }
        @Override
        public void generateBlockModel(RuntimeResourcePack pack, Identifier id) {
            pack.addModel(model("variantgen:block/template_piston").textures(textures()
                    .var("base_bottom", this.base_bottom)
                    .var("base_side", this.base_side)
                    .var("platform_side", this.platform_side)
                    .var("platform_top", this.platform_top)
                    .var("particle", this.particle)
            ), prefixPath(id, "block"));
        }
        @Override
        public void generateBlockState(RuntimeResourcePack pack, Identifier id) {
            String model = prefixPath(id, "block").toString();
            pack.addBlockState(state(variant()
                    .put("extended=false,facing=down", JState.model(model).x(90))
                    .put("extended=false,facing=east", JState.model(model).y(90))
                    .put("extended=false,facing=north", JState.model(model))
                    .put("extended=false,facing=south", JState.model(model).y(180))
                    .put("extended=false,facing=up", JState.model(model).x(270))
                    .put("extended=false,facing=west", JState.model(model).y(270))
                    .put("extended=true,facing=down", JState.model(model).x(90))
                    .put("extended=true,facing=east", JState.model(model).y(90))
                    .put("extended=true,facing=north", JState.model(model))
                    .put("extended=true,facing=south", JState.model(model).y(180))
                    .put("extended=true,facing=up", JState.model(model).x(270))
                    .put("extended=true,facing=west", JState.model(model).y(270))
                    ), id);
        }
    }
    class UniqueFaces implements Block {
        private final String particle, north, south, east, west, up, down;

        public UniqueFaces(String particle, String north, String south, String east, String west, String up, String down) {
            this.particle = Variantgen.ID + ":block/" + particle;
            this.north = Variantgen.ID + ":block/" + north;
            this.south = Variantgen.ID + ":block/" + south;
            this.east = Variantgen.ID + ":block/" + east;
            this.west = Variantgen.ID + ":block/" + west;
            this.up = Variantgen.ID + ":block/" + up;
            this.down = Variantgen.ID + ":block/" + down;
        }
        public static UniqueFaces of(String particle, String north, String south, String east, String west, String up, String down) {
            return new UniqueFaces(particle, north, south, east, west, up, down);
        }
        @Override
        public void generateBlockModel(RuntimeResourcePack pack, Identifier id) {
            pack.addModel(model("minecraft:block/cube").textures(textures()
                    .var("particle", this.particle)
                    .var("north", this.north)
                    .var("south", this.south)
                    .var("east", this.east)
                    .var("west", this.west)
                    .var("up", this.up)
                    .var("down", this.down)
            ), prefixPath(id, "block"));
        }
    }

    class Facing implements Block {
        private final String top, front, side, bottom;

        public Facing(String top, String front, String side, String bottom) {
            this.top = Variantgen.ID + ":block/" + top;
            this.front = Variantgen.ID + ":block/" + front;
            this.side = Variantgen.ID + ":block/" + side;
            this.bottom = Variantgen.ID + ":block/" + bottom;
        }

        public static Facing of(String top, String front, String side, String bottom) {
            return new Facing(top, front, side, bottom);
        }

        @Override
        public void generateBlockState(RuntimeResourcePack pack, Identifier id) {
            String model = prefixPath(id, "block")
                    .toString();
            pack.addBlockState(state(variant().put("facing",
                            "east",
                            JState.model(model)
                                    .y(90))
                    .put("facing",
                            "west",
                            JState.model(model)
                                    .y(270))
                    .put("facing", "north", JState.model(model))
                    .put("facing",
                            "south",
                            JState.model(model)
                                    .y(180))), id);
        }

        @Override
        public void generateBlockModel(RuntimeResourcePack pack, Identifier id) {
            pack.addModel(model("minecraft:block/orientable").textures(textures().var("top", this.top)
                    .var("front", this.front)
                    .var("side", this.side)
                    .var("bottom", this.bottom)), prefixPath(id, "block"));
        }
    }

    class FurnaceLike extends Facing {
        private final String top_on, front_on, side_on, bottom_on;

        public FurnaceLike(String top, String front, String side, String bottom, String top_on, String front_on, String side_on, String bottom_on) {
            super(top, front, side, bottom);
            this.top_on = Variantgen.ID + ":block/" + top_on;
            this.front_on = Variantgen.ID + ":block/" + front_on;
            this.side_on = Variantgen.ID + ":block/" + side_on;
            this.bottom_on = Variantgen.ID + ":block/" + bottom_on;
        }


        @Override
        public void generateBlockState(RuntimeResourcePack pack, Identifier id) {
            String model = prefixPath(id, "block")
                    .toString();
            String on = model + "_on";
            pack.addBlockState(state(variant().put("facing=east,lit=false",
                            JState.model(model)
                                    .y(90))
                    .put("facing=west,lit=false",
                            JState.model(model)
                                    .y(270))
                    .put("facing=north,lit=false", JState.model(model))
                    .put("facing=south,lit=false",
                            JState.model(model)
                                    .y(180))
                    .put("facing=east,lit=true",
                            JState.model(on)
                                    .y(90))
                    .put("facing=west,lit=true",
                            JState.model(on)
                                    .y(270))
                    .put("facing=north,lit=true", JState.model(on))
                    .put("facing=south,lit=true",
                            JState.model(on)
                                    .y(180))), id);
        }

        @Override
        public void generateBlockModel(RuntimeResourcePack pack, Identifier id) {
            super.generateBlockModel(pack, id);
            pack.addModel(model("minecraft:block/orientable").textures(textures().var("top", this.top_on)
                            .var("front", this.front_on)
                            .var("side", this.side_on)
                            .var("bottom", this.bottom_on)),
                    new Identifier(id.getNamespace(), "block/" + id.getPath() + "_on"));
        }
    }


    void client(RuntimeResourcePack pack, Identifier id);

    void server(RuntimeResourcePack pack, Identifier id);
}
