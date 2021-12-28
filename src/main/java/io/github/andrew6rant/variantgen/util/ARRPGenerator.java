package io.github.andrew6rant.variantgen.util;

public class ARRPGenerator {
    public static void init() {

    }
    public static void genResources(String namespace, String name){
        namespace = namespace + ":block/" + name;
    }
}
