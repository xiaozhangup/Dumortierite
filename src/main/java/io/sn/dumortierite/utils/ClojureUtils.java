package io.sn.dumortierite.utils;

import clojure.lang.Cons;

import java.util.List;

@SuppressWarnings("unused")
public class ClojureUtils {

    private ClojureUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String[] listToArr(List<String> lore) {
        return lore.toArray(new String[0]);
    }

    @SuppressWarnings("rawtypes")
    public static List consToList(Cons cons) {
        return cons.subList(0, cons.count());
    }

}
