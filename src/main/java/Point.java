/**
 * 这是一个结点旗下结点的元素，与头结点不同
 */
public class Point {
    private int pointId;    //结点的标识
    private int weight;

    public Point() {
    }

    public Point(int pointId) {
        this.pointId = pointId;
        this.weight = 0;
    }

    public Point(int pointId, int weight) {
        this.pointId = pointId;
        this.weight = weight;
    }

    public int getPointId() {
        return pointId;
    }

    public void setPointId(int pointId) {
        this.pointId = pointId;
    }


    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
