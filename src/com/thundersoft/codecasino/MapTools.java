package com.thundersoft.codecasino;

/**
 * Author: Hadon
 * Date: 2019/12/6 22:18
 * Content:
 */
public class MapTools {

    public static boolean isWall(char ch) {
        return ch == "9".charAt(0);
    }

    public static boolean isGhost(char ch) {
        return ch == "G".charAt(0);
    }

    public static boolean isOutOfBounds(int x) {
        if (x > 14 || x < 0) {
            return true;
        }
        return false;
    }

    public static boolean isOutOfBounds(int x, int y) {
        if (x < 0 || x > 14 || y < 0 || y > 14) {
            return true;
        }
        return false;
    }

    public static int charToInt(char ch) {
        return Integer.parseInt(String.valueOf(ch));
    }
}
