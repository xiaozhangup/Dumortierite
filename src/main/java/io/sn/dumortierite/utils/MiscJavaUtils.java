package io.sn.dumortierite.utils;

public class MiscJavaUtils {

    public static String explainTier(int tier, String before) {
        return switch (tier) {
            case 1 -> before + "<dark_gray>Mk.<color:#ffff55>" + RomanUtils.intToRoman(1);
            case 2 -> before + "<dark_gray>Mk.<color:#aaffaa>" + RomanUtils.intToRoman(2);
            case 3 -> before + "<dark_gray>Mk.<color:#55ffff>" + RomanUtils.intToRoman(3);
            case 4 -> before + "<dark_gray>Mk.<color:#aaaaff>" + RomanUtils.intToRoman(4);
            case 5 -> before + "<dark_gray>Mk.<color:#ff55ff>" + RomanUtils.intToRoman(5);
            default -> before + "<dark_gray>Mk.<color:#ff5555>" + RomanUtils.intToRoman(tier);
        };
    }

}
