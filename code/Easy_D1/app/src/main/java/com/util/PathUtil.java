package com.util;
import com.bean.*;

import java.util.List;

/**
 * Created by Vorringer on 2016/7/8.
 */
public class PathUtil {
    private Position start;
    private List<Position> dests;
    public PathUtil(Position start,List<Position>dests)
    {
        this.start=start;
        this.dests=dests;
    }
    public List<Position> getShortestPath()
    {
        return null;
    }
}
