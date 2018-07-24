import java.util.*;

/**
 * 这个主要用来实现邻接表的头节点
 */
public class PointNode {
    private int pointId;    //结点的标识
    private List<Point> nextPointList;    //指向的结点的hash表

    public PointNode(int pointId) {
        this.pointId = pointId;
        nextPointList = new ArrayList<Point>();
    }



    public int getPointId() {
        return pointId;
    }

    public void setPointId(int pointId) {
        this.pointId = pointId;
    }

    public boolean addPoint(Point b){
        nextPointList.add(b);
        return true;
    }

    public boolean removePoint(Point b){
        Iterator<Point> it = nextPointList.iterator();
        while (it.hasNext()){
            Point point = it.next();
            if(point.getPointId() == b.getPointId() &&
                    point.getWeight() == b.getPointId())
                it.remove();
        }
        return true;
    }

    public List<Point> getNextPointList() {
        return nextPointList;
    }

    public void setNextPointList(List<Point> nextPointList) {
        this.nextPointList = nextPointList;
    }
}
