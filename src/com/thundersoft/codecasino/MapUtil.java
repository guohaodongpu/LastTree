package com.thundersoft.codecasino;

/**
 * Author: Hadon
 * Date: 2019/12/6 15:01
 * Content:
 */
public class MapUtil {
    static int sMapSize = -1;
    static int sNum = -1;  //在LocationList、ScoreList的下标
    static String sMapBase;//初始一维地图

    static char[][] sMapInfo;//二维地图

    static String sToken;
    static int[] sLocationList;
    static int[] sScoreList;

    static int sCurrentDirction;
    static int x;
    static int y;

    static int mNumOfGhostAndBullet;
    static int mNumOfPlayers;


    static int[] sDirctions = new int[4];


    static public void parseMapSize(String args) {
        String argsArrays[] = args.split("\\s+");
        String temp = argsArrays[2].substring(0, argsArrays[2].length() - 1);
        sMapSize = Integer.valueOf(temp);
        sMapInfo = new char[sMapSize][sMapSize];
        if (argsArrays[1].equals("#")) {
            sNum = 10;
        } else {
            sNum = Integer.valueOf(argsArrays[1]);
        }


        System.out.println("MapSize: " + sMapSize + " | " + "Num :" + sNum);

    }

    public static int[] stringListToIntList(String list) {
        String[] stringList = list.split(" ");
        int[] intList = new int[stringList.length];
        for (int i = 0; i < stringList.length; i++) {
            intList[i] = Integer.valueOf(stringList[i]);
        }
        return intList;

    }

    static public void parseMapInfo(String args) {

        sToken = args.substring(5, 7);

        String[] list1 = args.split("LOCATION ");
//        for (int i = 0; i < list1.length; i++) {
//            System.out.println("list1:" + i + "  = " + list1[i]);
//        }

        String[] list2 = list1[1].split("SCORE ");
//        for (int i = 0; i < list2.length; i++) {
//            System.out.println("list2:" + i + "  = " + list2[i]);
//        }

        sMapBase = list1[0].substring(8, list1[0].length() - 2);
        System.out.println("Token: " + sToken + "\n" + " MapBase :" + sMapBase);
        String locationListString = list2[0].substring(3, list2[0].length() - 2);
        String scoreListString = list2[1].substring(3, list2[1].length() - 1);

        sLocationList = stringListToIntList(locationListString);
        System.out.println("LocationList: " + locationListString);
        sScoreList = stringListToIntList(scoreListString);
        System.out.println("ScoreList: " + scoreListString);

        createMap(sMapBase);

        getLocationXY(sLocationList[sNum], sMapSize);
        getCurrentDirection();

        calculate();
    }

    public static int[] getLocationXY(int location, int mapSize) {
        int[] locationXY = new int[2];
        int locationY = location % mapSize - 1;
        int locationX = location / mapSize;
        locationXY[0] = locationX;
        locationXY[1] = locationY;
        x = locationX;
        y = locationY;
        return locationXY;
    }

    public static void createMap(String str) {
        String string = str;
        char[] chars = string.toCharArray();
        int i = 0;
        for (int j = 0; j < 15; j++) {
            for (int k = 0; k < 15; k++) {
                sMapInfo[j][k] = chars[i];
                i++;
            }
        }
        print();
    }

    public static void print() {
        for (int i = 0; i < 15; i++) {
            System.out.println(String.copyValueOf(sMapInfo[i]));
        }
    }

    public static String getCommand() {

        int temp = getPreciseCommand();
        //xx 第一位 1,2 -> 不发射，发射  |  第二位 0,1,2,3 -> 上，下，左，右
        switch (temp) {
            case 10: {
                return "[" + sToken + "w ]";
            }
            case 11: {
                return "[" + sToken + "s ]";
            }
            case 12: {
                return "[" + sToken + "a ]";
            }
            case 13: {
                return "[" + sToken + "d ]";
            }
            case 14: {
                return "[" + sToken + "  ]";
            }
            case 20: {
                return "[" + sToken + "wv]";
            }
            case 21: {
                return "[" + sToken + "sv]";
            }
            case 22: {
                return "[" + sToken + "av]";
            }
            case 23: {
                return "[" + sToken + "dv]";
            }
            case 24: {
                return "[" + sToken + " v]";
            }
        }
        return null;
    }

    public static int getPreciseCommand() {

        if (mNumOfPlayers > 0) {
            for (int i = 0; i < 4; i++) {
                if (sDirctions[i] == 2) {
                    if (sCurrentDirction == i) {
                        return 24;
                    } else {
                        return 20;
                    }
                }
            }
        } else if (mNumOfGhostAndBullet > 1) {
            return getCommandForCurrentDriction(1);
        } else if (mNumOfGhostAndBullet == 1) {
            for (int i = 0; i < 4; i++) {
                if (sDirctions[i] == 1) {
                    if (sCurrentDirction == i) {
                        return 24;
                    } else {
                        return 20;
                    }
                }
            }
        }
        Tree tree = new Tree();
        tree.start();
        return tree.getCommand();

    }

    public static int getCurrentDirection() {
        char ch = sMapInfo[x][y];
        if (ch == "w".charAt(0)) {
            return 0;
        }
        if (ch == "s".charAt(0)) {
            return 1;
        }
        if (ch == "a".charAt(0)) {
            return 2;
        }
        if (ch == "d".charAt(0)) {
            return 3;
        }
        return -1;
    }

    public static void calculate() {
        if (!MapTools.isOutOfBounds(x - 1, y) && !MapTools.isWall(sMapInfo[x - 1][y])) {
            if (MapTools.isGhost(sMapInfo[x - 1][y]) || sMapInfo[x - 1][y] == "v".charAt(0)) {
                mNumOfGhostAndBullet++;
                sDirctions[0] = 1;
            } else if (MapTools.isPerson(sMapInfo[x - 1][y])) {
                mNumOfPlayers++;
                sDirctions[0] = 2;
            }
        }
        if (!MapTools.isOutOfBounds(x + 1, y) && !MapTools.isWall(sMapInfo[x + 1][y])) {
            if (MapTools.isGhost(sMapInfo[x + 1][y]) || sMapInfo[x + 1][y] == "^".charAt(0)) {
                mNumOfGhostAndBullet++;
                sDirctions[1] = 1;
            } else if (MapTools.isPerson(sMapInfo[x + 1][y])) {
                mNumOfPlayers++;
                sDirctions[1] = 2;
            }
        }
        if (!MapTools.isOutOfBounds(x, y - 1) && !MapTools.isWall(sMapInfo[x][y - 1])) {
            if (MapTools.isGhost(sMapInfo[x][y - 1]) || sMapInfo[x][y - 1] == ">".charAt(0)) {
                mNumOfGhostAndBullet++;
                sDirctions[2] = 1;
            } else if (MapTools.isPerson(sMapInfo[x][y - 1])) {
                mNumOfPlayers++;
                sDirctions[2] = 2;
            }
        }
        if (!MapTools.isOutOfBounds(x, y + 1) && !MapTools.isWall(sMapInfo[x][y + 1])) {
            if (MapTools.isGhost(sMapInfo[x][y + 1]) || sMapInfo[x][y + 1] == "<".charAt(0)) {
                mNumOfGhostAndBullet++;
                sDirctions[3] = 1;
            } else if (MapTools.isPerson(sMapInfo[x][y + 1])) {
                mNumOfPlayers++;
                sDirctions[3] = 2;
            }
        }
    }

    public static int getCommandForCurrentDriction(int a) {
        if (a == 1) {
            return Integer.valueOf("2" + sCurrentDirction);
        } else {
            return Integer.valueOf("1" + sCurrentDirction);
        }
    }


    public static boolean isHighestScore() {
        if (sScoreList.length > 0) {
            int myScore = sScoreList[sNum];
            int highestScore = 0;
            for (int i = 0; i < sScoreList.length; i++) {
                if (sScoreList[i] > highestScore) {
                    highestScore = sScoreList[i];
                }
            }
            if (myScore == highestScore) {
                return true;
            }
        }
        return false;
    }

}
