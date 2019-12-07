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

    public static boolean isPerson(char ch) {
        if (ch == "w".charAt(0) || ch == "s".charAt(0) || ch == "a".charAt(0) || ch == "d".charAt(0)) {
            return true;
        }
        return false;
    }

    public static boolean isBullet(char ch) {
        if (ch == "v".charAt(0) || ch == "^".charAt(0) || ch == ">".charAt(0) || ch == "<".charAt(0)) {
            return true;
        }
        return false;
    }

    //player x,y ; bullet x1, y1;
    public static boolean isSafeBullet(int x, int y, int x1, int y1, char ch) {
        if (ch == "v".charAt(0)) {
            if (x - x1 == 1) {
                return false;
            }
        }
        if (ch == "^".charAt(0)) {

            if (x1 - x == 1) {
                return false;
            }
        }
        if (ch == ">".charAt(0)) {
            if (y - y1 == 1) {
                return false;
            }
        }
        if (ch == "<".charAt(0)) {
            if (y1 - y == 1) {
                return false;
            }
        }
        return true;
    }


    public static boolean isOutOfBounds(int x) {
        if (x > MapUtil.sMapSize - 1 || x < 0) {
            return true;
        }
        return false;
    }

    public static boolean isOutOfBounds(int x, int y) {
        if (x < 0 || x > MapUtil.sMapSize - 1 || y < 0 || y > MapUtil.sMapSize) {
            return true;
        }
        return false;
    }

    public static int charToInt(char ch) {
        return Integer.parseInt(String.valueOf(ch));
    }
}
