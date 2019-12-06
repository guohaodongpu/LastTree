package com.thundersoft.codecasino;

/**
 * Author: Hadon
 * Date: 2019/12/6 22:17
 * Content:
 */
public class Node {
    int xOfLocation_x;
    int yOfLocation_y;

    int mCurrentDirection;

    int mScore = 0;

    int mDepth = 0;

    int FLAG = 6;

    Node WSon = null;
    Node SSon = null;
    Node ASon = null;
    Node DSon = null;

    Node FaNode;

    Boolean isSafe;

    char[][] map = new char[15][15];

//    static Ghost G1 = null;
//    static Ghost G2 = null;
//    static Ghost G3 = null;

    public void createMap(String str) {
        String string = str.substring(1, 226);
        char[] chars = string.toCharArray();
        int i = 0;
        for (int j = 0; j < 15; j++) {
            for (int k = 0; k < 15; k++) {
                map[j][k] = chars[i];
                if (String.valueOf(chars[i]).equals("s")) {
                    xOfLocation_x = j;
                    yOfLocation_y = k;
                    mCurrentDirection = 1;
                }
                if (String.valueOf(chars[i]).equals("a")) {
                    xOfLocation_x = j;
                    yOfLocation_y = k;
                    mCurrentDirection = 2;
                }
                if (String.valueOf(chars[i]).equals("d")) {
                    xOfLocation_x = j;
                    yOfLocation_y = k;
                    mCurrentDirection = 3;
                }
                if (String.valueOf(chars[i]).equals("w")) {
                    xOfLocation_x = j;
                    yOfLocation_y = k;
                    mCurrentDirection = 0;
                }
                if (String.valueOf(chars[i]).equals("G")) {
//                    setLocationOfGhost(j, k);
                }
                i++;
            }
        }

//        updateMap();
        print();
    }

    public void updateMap() {
        int flagOfEachNode = 0;
        for (int j = 0; j < 15; j++) {
            for (int k = 0; k < 15; k++) {
                flagOfEachNode = 0;
                if (j != xOfLocation_x || k != yOfLocation_y) {
                    if (!(MapTools.isOutOfBounds(j - 1, k) || MapTools.isWall(map[j - 1][k]))) {
                        flagOfEachNode++;
                    }
                    if (!(MapTools.isOutOfBounds(j + 1, k) || MapTools.isWall(map[j + 1][k]))) {
                        flagOfEachNode++;
                    }
                    if (!(MapTools.isOutOfBounds(j, k - 1) || MapTools.isWall(map[j][k - 1]))) {
                        flagOfEachNode++;
                    }
                    if (!(MapTools.isOutOfBounds(j, k + 1) || MapTools.isWall(map[j][k + 1]))) {
                        flagOfEachNode++;
                    }
                    if (flagOfEachNode == 1 && !MapTools.isWall(map[j][k])) {
                        map[j][k] = "9".charAt(0);
                    }
                }

            }
        }
    }

//    public void setLocationOfGhost(int x, int y) {
//        if (G1 == null) {
//            G1 = new Ghost(x, y);
//            System.out.println("x: " + x + " , " + "y: " + y);
//        } else if (G2 == null) {
//            G2 = new Ghost(x, y);
//            System.out.println("x: " + x + " , " + "y: " + y);
//        } else if (G3 == null) {
//            G3 = new Ghost(x, y);
//            System.out.println("x: " + x + " , " + "y: " + y);
//        }
//    }

    public void print() {
        for (int i = 0; i < 15; i++) {
            System.out.println(String.copyValueOf(map[i]));
        }
    }

//    public void print(char[][] matrix) {
//        System.out.println();
//        for (int i = 0; i < 15; i++) {
//            System.out.println(String.copyValueOf(matrix[i]));
//        }
//    }

    public char[][] copyMap() {
        char[][] mapC = new char[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                mapC[i][j] = map[i][j];
            }
        }
        return mapC;
    }

    public void createWNode() {

        WSon = new Node();
        WSon.FLAG = 0;
        WSon.mCurrentDirection = 0;
        WSon.map = copyMap();
        WSon.yOfLocation_y = yOfLocation_y;
        WSon.FaNode = this;
        WSon.mDepth = WSon.FaNode.mDepth + 1;
        if (WSon.mCurrentDirection == mCurrentDirection) {
            if (xOfLocation_x > 0 && !MapTools.isWall(WSon.map[xOfLocation_x - 1][yOfLocation_y])
                    && !MapTools.isGhost(WSon.map[xOfLocation_x - 1][yOfLocation_y])) {
                int scoreTemp = MapTools.charToInt(WSon.map[xOfLocation_x - 1][yOfLocation_y]);
                WSon.mScore = WSon.FaNode.mScore + scoreTemp;
                WSon.map[xOfLocation_x][WSon.yOfLocation_y] = "0".charAt(0);
                WSon.xOfLocation_x = xOfLocation_x - 1;
                WSon.map[WSon.xOfLocation_x][WSon.yOfLocation_y] = "w".charAt(0);
            } else if (xOfLocation_x > 0 && (MapTools.isWall(WSon.map[xOfLocation_x - 1][yOfLocation_y])
                    || MapTools.isGhost(WSon.map[xOfLocation_x - 1][yOfLocation_y]))) {
                WSon.xOfLocation_x = xOfLocation_x;
                WSon.map[WSon.xOfLocation_x][WSon.yOfLocation_y] = "w".charAt(0);
                WSon.mScore = WSon.FaNode.mScore;
            } else {
                WSon.xOfLocation_x = 0;
                WSon.map[WSon.xOfLocation_x][WSon.yOfLocation_y] = "w".charAt(0);
                WSon.mScore = WSon.FaNode.mScore;
            }
        } else {
            WSon.xOfLocation_x = xOfLocation_x;
            WSon.map[WSon.xOfLocation_x][WSon.yOfLocation_y] = "w".charAt(0);
            WSon.mScore = WSon.FaNode.mScore;
        }


        WSon.isSafe = isSafe(WSon);
    }

    public void createSNode() {
        SSon = new Node();
        SSon.FLAG = 1;
        SSon.mCurrentDirection = 1;
        SSon.map = copyMap();
        SSon.yOfLocation_y = yOfLocation_y;
        SSon.FaNode = this;
        SSon.mDepth = SSon.FaNode.mDepth + 1;
        if (SSon.mCurrentDirection == mCurrentDirection) {
            if (xOfLocation_x < 14 && !MapTools.isWall(SSon.map[xOfLocation_x + 1][yOfLocation_y])
                    && !MapTools.isGhost(SSon.map[xOfLocation_x + 1][yOfLocation_y])) {

                int scoreTemp = MapTools.charToInt(SSon.map[xOfLocation_x + 1][yOfLocation_y]);
                SSon.mScore = SSon.FaNode.mScore + scoreTemp;
                SSon.map[xOfLocation_x][SSon.yOfLocation_y] = "0".charAt(0);
                SSon.xOfLocation_x = xOfLocation_x + 1;
                SSon.map[SSon.xOfLocation_x][SSon.yOfLocation_y] = "s".charAt(0);
            } else if (xOfLocation_x < 14 && (MapTools.isWall(SSon.map[xOfLocation_x + 1][yOfLocation_y])
                    || MapTools.isGhost(SSon.map[xOfLocation_x + 1][yOfLocation_y]))) {

                SSon.xOfLocation_x = xOfLocation_x;
                SSon.map[SSon.xOfLocation_x][SSon.yOfLocation_y] = "s".charAt(0);
                SSon.mScore = SSon.FaNode.mScore;
            } else {
                SSon.xOfLocation_x = 14;
                SSon.map[SSon.xOfLocation_x][SSon.yOfLocation_y] = "s".charAt(0);
                SSon.mScore = SSon.FaNode.mScore;
            }
        } else {
            SSon.xOfLocation_x = xOfLocation_x;
            SSon.map[SSon.xOfLocation_x][SSon.yOfLocation_y] = "s".charAt(0);
            SSon.mScore = SSon.FaNode.mScore;
        }

        SSon.isSafe = isSafe(SSon);
    }

    public void createANode() {
        ASon = new Node();
        ASon.FLAG = 2;
        ASon.mCurrentDirection = 2;
        ASon.map = copyMap();
        ASon.xOfLocation_x = xOfLocation_x;
        ASon.FaNode = this;
        ASon.mDepth = ASon.FaNode.mDepth + 1;
        if (ASon.mCurrentDirection == mCurrentDirection) {
            if (yOfLocation_y > 0 && !MapTools.isWall(ASon.map[xOfLocation_x][yOfLocation_y - 1])
                    && !MapTools.isGhost(ASon.map[xOfLocation_x][yOfLocation_y - 1])) {

                int scoreTemp = MapTools.charToInt(ASon.map[xOfLocation_x][yOfLocation_y - 1]);
                ASon.mScore = ASon.FaNode.mScore + scoreTemp;
                ASon.map[ASon.xOfLocation_x][yOfLocation_y] = "0".charAt(0);
                ASon.yOfLocation_y = yOfLocation_y - 1;
                ASon.map[ASon.xOfLocation_x][ASon.yOfLocation_y] = "a".charAt(0);
            } else if (yOfLocation_y > 0 && (MapTools.isWall(ASon.map[xOfLocation_x][yOfLocation_y - 1])
                    || MapTools.isGhost(ASon.map[xOfLocation_x][yOfLocation_y - 1]))) {

                ASon.yOfLocation_y = yOfLocation_y;
                ASon.map[ASon.xOfLocation_x][ASon.yOfLocation_y] = "a".charAt(0);
                ASon.mScore = ASon.FaNode.mScore;
            } else {
                ASon.yOfLocation_y = 0;
                ASon.map[ASon.xOfLocation_x][ASon.yOfLocation_y] = "a".charAt(0);
                ASon.mScore = ASon.FaNode.mScore;
            }
        } else {
            ASon.yOfLocation_y = yOfLocation_y;
            ASon.map[ASon.xOfLocation_x][ASon.yOfLocation_y] = "a".charAt(0);
            ASon.mScore = ASon.FaNode.mScore;
        }

        ASon.isSafe = isSafe(ASon);
    }

    public void createDNode() {
        DSon = new Node();
        DSon.FLAG = 3;
        DSon.mCurrentDirection = 3;
        DSon.map = copyMap();
        DSon.xOfLocation_x = xOfLocation_x;
        DSon.FaNode = this;
        DSon.mDepth = DSon.FaNode.mDepth + 1;
        if (DSon.mCurrentDirection == mCurrentDirection) {
            if (yOfLocation_y < 14 && !MapTools.isWall(DSon.map[xOfLocation_x][yOfLocation_y + 1])
                    && !MapTools.isGhost(DSon.map[xOfLocation_x][yOfLocation_y + 1])) {
                int scoreTemp = MapTools.charToInt(DSon.map[xOfLocation_x][yOfLocation_y + 1]);
                DSon.mScore = DSon.FaNode.mScore + scoreTemp;
                DSon.map[DSon.xOfLocation_x][yOfLocation_y] = "0".charAt(0);
                DSon.yOfLocation_y = yOfLocation_y + 1;
                DSon.map[DSon.xOfLocation_x][DSon.yOfLocation_y] = "d".charAt(0);
            } else if (yOfLocation_y < 14 && (MapTools.isWall(DSon.map[xOfLocation_x][yOfLocation_y + 1])
                    || MapTools.isGhost(DSon.map[xOfLocation_x][yOfLocation_y + 1]))) {
                DSon.yOfLocation_y = yOfLocation_y;
                DSon.map[DSon.xOfLocation_x][DSon.yOfLocation_y] = "d".charAt(0);
                DSon.mScore = DSon.FaNode.mScore;
            } else {
                DSon.yOfLocation_y = 14;
                DSon.map[DSon.xOfLocation_x][DSon.yOfLocation_y] = "d".charAt(0);
                DSon.mScore = DSon.FaNode.mScore;
            }
        } else {
            DSon.yOfLocation_y = yOfLocation_y;
            DSon.map[DSon.xOfLocation_x][DSon.yOfLocation_y] = "d".charAt(0);
            DSon.mScore = DSon.FaNode.mScore;
        }

        DSon.isSafe = isSafe(DSon);
    }

    public boolean isSafe(Node node) {

//        int distance = 5;
//        if (node.mDepth == 2) {
//            distance = 1;
//        } else if (node.mDepth == 3) {
////            distance=2;
//        }
//
//        if (node.mDepth <= 1) {
//            return true;
//        }
//
//        if (G1.getDistance(node.xOfLocation_x, node.yOfLocation_y) <= distance) {
//            return false;
//        }
//        if (G2.getDistance(node.xOfLocation_x, node.yOfLocation_y) <= distance) {
//            return false;
//        }
//        if (G3.getDistance(node.xOfLocation_x, node.yOfLocation_y) <= distance) {
//            return false;
//        }
        return true;
    }

    public boolean isSafe(Node node, int distance) {

//        if (G1.getDistance(node.xOfLocation_x, node.yOfLocation_y) <= distance) {
//            return false;
//        }
//        if (G2.getDistance(node.xOfLocation_x, node.yOfLocation_y) <= distance) {
//            return false;
//        }
//        if (G3.getDistance(node.xOfLocation_x, node.yOfLocation_y) <= distance) {
//            return false;
//        }
        return true;
    }
}
