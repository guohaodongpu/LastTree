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

    static int[][] sMapInfo;//二维地图

    static String sToken;
    static int[] sLocationList;
    static int[] sScoreList;

    static public void parseMapSize(String args) {
        String argsArrays[] = args.split("\\s+");
        String temp = argsArrays[2].substring(0, argsArrays[2].length() - 1);
        sMapSize = Integer.valueOf(temp);
        sMapInfo = new int[sMapSize][sMapSize];
        sNum = Integer.valueOf(argsArrays[1]);

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
        for (int i = 0; i < list1.length; i++) {
            System.out.println("list1:" + i + "  = " + list1[i]);
        }

        String[] list2 = list1[1].split("SCORE ");
        for (int i = 0; i < list2.length; i++) {
            System.out.println("list2:" + i + "  = " + list2[i]);
        }

        sMapBase = list1[0].substring(8, list1[0].length() - 2);
        System.out.println("Token: " + sToken + "\n" + " MapBase :" + sMapBase);
        String locationListString = list2[0].substring(3, list2[0].length() - 2);
        String scoreListString = list2[1].substring(3, list2[1].length() - 1);

        sLocationList = stringListToIntList(locationListString);
        System.out.println("LocationList: " + sLocationList);
        sScoreList = stringListToIntList(scoreListString);
        System.out.println("ScoreList: " + sScoreList);
    }

    public static int[] getLocationXY(int location, int mapSize) {
        int[] locationXY = new int[2];
        int locationY = location % mapSize - 1;
        int locationX = location / mapSize;
        locationXY[0] = locationX;
        locationXY[1] = locationY;
        return locationXY;

    }

}
