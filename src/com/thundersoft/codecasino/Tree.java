package com.thundersoft.codecasino;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Hadon
 * Date: 2019/12/6 22:17
 * Content:
 */
public class Tree {
    Node root = null;

    Node mCurrentTreeNode = null;

    // 大于8后，未在0.2s内算完
    private static int HMAX = 8;

    //储存所有的叶子节点
    List<Node> listNodes = new ArrayList<>();

    List<Integer> path = new ArrayList<>();

    static boolean FLAG_SAFE_OR_NOT = false;

    static int SAFE_DISTANCE = 8;

    public Tree() {
    }

    public void start() {
        try {
            createRoot();
            createSonNodes();
            findPaths();
            selectPathFromPaths();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setRootMap(char[][] map) {
        root.map = map;
    }

    public void initRoot() {
        root.map = MapUtil.sMapInfo;
        root.xOfLocation_x = MapUtil.x;
        root.yOfLocation_y = MapUtil.y;
        root.mCurrentDirection = MapUtil.sCurrentDirection;
    }

    public void createRoot() {
        root = new Node();
//        root.createMap(string);
        initRoot();
        root.FaNode = null;
        root.mScore = 0;//ke shan
        root.mDepth = 0;//ke shan
        mCurrentTreeNode = root;
        root.FLAG = 5;
        FLAG_SAFE_OR_NOT = root.isSafe(root, SAFE_DISTANCE);
    }

    int number = 0;

    public void createSonNodes() {

        mCurrentTreeNode.createWNode();
        number++;
        if (mCurrentTreeNode.WSon.mDepth < HMAX) {
            mCurrentTreeNode = mCurrentTreeNode.WSon;
            createSonNodes();
        }

        mCurrentTreeNode.createSNode();
        number++;
        if (mCurrentTreeNode.SSon.mDepth < HMAX) {
            mCurrentTreeNode = mCurrentTreeNode.SSon;
            createSonNodes();
        }

        mCurrentTreeNode.createANode();
        number++;
        if (mCurrentTreeNode.ASon.mDepth < HMAX) {
            mCurrentTreeNode = mCurrentTreeNode.ASon;
            createSonNodes();
        }

        mCurrentTreeNode.createDNode();
        number++;
        if (mCurrentTreeNode.DSon.mDepth < HMAX) {
            mCurrentTreeNode = mCurrentTreeNode.DSon;
            createSonNodes();
        }

        if (mCurrentTreeNode.FLAG == 5) {
            return;
        } else {
            mCurrentTreeNode = mCurrentTreeNode.FaNode;
        }
    }

    List<Node> listOfSafeNode = new ArrayList<>();

    public void findPaths() {
        System.out.println("find paths");
        findInSubTree();
        System.out.println("listNodes.size : " + listNodes.size());
        Node node;
        for (int i = 0; i < listNodes.size(); i++) {
            node = listNodes.get(i);

            while (node.FLAG != 5) {

                if (node.mDepth == 1 && node.isSafe) {
                    listOfSafeNode.add(listNodes.get(i));
                } else if (node.mDepth == 2 && !node.isSafe) {
                    break;
                }

                if (node.FaNode != null) {
                    node = node.FaNode;
                }
            }
        }
        System.out.println("listOfSafeNode.size : " + listOfSafeNode.size());
    }

    public void selectPathFromPaths() {
        int max = -1;
        int flag = 111111;

        for (int i = 0; i < listOfSafeNode.size(); i++) {
            if (max < listOfSafeNode.get(i).mScore) {
                flag = i;
                max = listOfSafeNode.get(i).mScore;
            } else if (max == listOfSafeNode.get(i).mScore) {
                if (listOfSafeNode.get(i).mScore == listOfSafeNode.get(i).FaNode.mScore) {
                    max = listOfSafeNode.get(i).mScore;
                    flag = i;
                }
            }
        }

        if (flag != 111111) {
            Node node = listOfSafeNode.get(flag);
            path.clear();
            System.out.println(" max score = " + node.mScore);
            while (node.FLAG != 5) {
                path.add(node.FLAG);
                node = node.FaNode;
            }
            printPath();
        } else {
            System.out.println("No Path !!!");
        }
    }

    public int[] createCommands() {
        int[] commands = new int[2];
        commands[0] = path.get(path.size() - 1);
        commands[1] = path.get(path.size() - 2);
        return commands;
    }

    public void printPath() {
        System.out.print("Path : ");
        for (int i = path.size() - 1; i >= 0; i--) {
            System.out.print(" " + path.get(i) + " ");
        }
    }

    public void findInSubTree() {

        if (mCurrentTreeNode.WSon != null) {
            mCurrentTreeNode = mCurrentTreeNode.WSon;
            findInSubTree();
        } else {
            listNodes.add(mCurrentTreeNode);
        }

        if (mCurrentTreeNode.SSon != null) {
            mCurrentTreeNode = mCurrentTreeNode.SSon;
            findInSubTree();
        }

        if (mCurrentTreeNode.ASon != null) {
            mCurrentTreeNode = mCurrentTreeNode.ASon;
            findInSubTree();
        }

        if (mCurrentTreeNode.DSon != null) {
            mCurrentTreeNode = mCurrentTreeNode.DSon;
            findInSubTree();
        }
        if (mCurrentTreeNode.FLAG != 5) {
            mCurrentTreeNode = mCurrentTreeNode.FaNode;
        } else {
            return;
        }
    }

    public String getNextCommand(int command) {

        switch (command) {
            case 0:
                // xOfLocation_x=xOfLocation_x-1;

                return "[w]";
            case 1:
                // xOfLocation_x=xOfLocation_x+1;

                return "[s]";
            case 2:
                // yOfLocation_y=yOfLocation_y-1;

                return "[a]";
            case 3:
                // yOfLocation_y=yOfLocation_y+1;

                return "[d]";
            default:
        }
        return "[w]";
    }

    public void resetGhost() {
//        root.G1 = null;
//        root.G2 = null;
//        root.G3 = null;
    }

    public int getCommand() {
        try {
            return path.get(0);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return 14;
        }
    }
}
