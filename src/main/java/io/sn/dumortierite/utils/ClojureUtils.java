package io.sn.dumortierite.utils;

import java.util.List;

@SuppressWarnings("unused")
public class ClojureUtils {

    private ClojureUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String[] listToArr(List<String> lore) {
        return lore.toArray(new String[0]);
    }

}
