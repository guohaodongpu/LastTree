package com.thundersoft.codecasino;

/**
 * Author: Hadon
 * Date: 2019/12/6 15:01
 * Content:
 */
public class MapUtil {
    static int sMapSize = -1;
    static int sNum = -1;
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

    static public void parseMapInfo(String args) {

        String sToken = args.substring(5, 7);

        String argsArrays[]= args.split("LOCATION");

        sMapBase = argsArrays[1].substring(0, argsArrays[1].length() - 10);
        System.out.println("Token: " + sToken + "\n" + " MapBase :" + sMapBase);

        String locationListArrays[] = argsArrays[2].split("\\s+");
        for (int i = 2; i < locationListArrays.length; i++) {
            if (i == locationListArrays.length -1) {
                sLocationList[i -2] = Integer.valueOf(locationListArrays[i].substring(0, locationListArrays[i].length() - 1));
            } else {
                sLocationList[i - 2] = Integer.valueOf(locationListArrays[i]);
            }
        }


//        String argsArrays[] = args.split("\\[");//"["分割
//        System.out.println("argsArrays: " + argsArrays[0] + "\n" + " argsArrays :" + argsArrays);
//        String tempArrays_1[] = argsArrays[0].split("\\s+");//空格分隔第一个 MAP
//        sToken = tempArrays_1[1];
//        sMapBase = tempArrays_1[2].substring(0, argsArrays[2].length() - 1);
//        System.out.println("Token: " + sToken + "\n" + " MapBase :" + sMapBase);
//
//
//        String tempArrays_2[] = argsArrays[1].split("\\s+");//空格分隔第二个 LOCATION
//        for (int i = 2; i < tempArrays_2.length; i++) {
//            if (i == tempArrays_2.length -1) {
//                sLocationList[i -2] = Integer.valueOf(tempArrays_2[i].substring(0, tempArrays_2[i].length() - 1));
//            } else {
//                sLocationList[i - 2] = Integer.valueOf(tempArrays_2[i]);
//            }
//        }
//        System.out.println("LocationList: " + sLocationList);
//
//        String tempArrays_3[] = argsArrays[2].split("\\s+");//空格分隔第三个 SCORE
//        for (int i = 2; i < tempArrays_3.length; i++) {
//            if (i == tempArrays_3.length -1) {
//                sScoreList[i -2] = Integer.valueOf(tempArrays_3[i].substring(0, tempArrays_3[i].length() - 1));
//            } else {
//                sScoreList[i - 2] = Integer.valueOf(tempArrays_3[i]);
//            }
//        }
//        System.out.println("ScoreList: " + sScoreList);

    }


}
