package com.thundersoft.codecasino;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Hadon
 * Date: 2019/12/6 22:21
 * Content:
 */
public class Ghost {

    int xOfLocation;
    int yOfLocation;

    int xInDistance;
    int yInDistance;

    List<NextLocation> list = new ArrayList<>();

    public void calculateDistance(int x, int y){
        xInDistance=Math.abs(xOfLocation-x);
        yInDistance=Math.abs(yOfLocation-y);
    }

    public Ghost(int x, int y){
        xOfLocation=x;
        yOfLocation=y;

        calculateList();
        if(list.size()==0){
            System.out.println("Ghost list size is 0");
        }else {
            System.out.println("Ghost list size is "+list.size());
        }
    }

    public int getDistance(int x, int y){
        calculateDistance(x,y);
        return xInDistance+yInDistance;
    }

    public int getDistance(NextLocation n){
        calculateDistance(n.xNext,n.yNext);
        return xInDistance+yInDistance;
    }

    public void calculateList(){
        if(xOfLocation-1>=0){
            list.add(new NextLocation(xOfLocation-1,yOfLocation));
        }
        if(xOfLocation+1<=14){
            list.add(new NextLocation(xOfLocation+1,yOfLocation));
        }
        if(yOfLocation-1>=0){
            list.add(new NextLocation(xOfLocation,yOfLocation-1));
        }
        if(yOfLocation+1<=14){
            list.add(new NextLocation(xOfLocation,yOfLocation+1));
        }
    }

    public List getGhostList(){
        return list;
    }

    class NextLocation{
        int xNext;
        int yNext;
        public NextLocation(int x,int y){
            xNext=x;
            yNext=y;
        }
    }
}
