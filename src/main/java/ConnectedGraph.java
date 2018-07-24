import java.util.*;
/**
 * 这是一个无向连通图的邻接矩阵类
 */
public class ConnectedGraph {

    int size;   //图顶点个数
    HashSet<Integer> pointSet; //图顶点编号
    //int[][] matrix; 一开始是打算邻接矩阵，后来废弃了，还是邻接表合适
    HashMap<Integer, PointNode> pointNodeMap;  //图的邻接矩阵

    public ConnectedGraph(List<MyEdge> myEdgeList) {
        this.pointSet = initPointSet(myEdgeList);
        pointNodeMap = new HashMap<Integer, PointNode>();
        size = pointSet.size();
        Iterator<Integer> integerIterator = pointSet.iterator();
        while (integerIterator.hasNext()){
            int i = integerIterator.next();
            PointNode pointNode = new PointNode(i);
            pointNodeMap.put(i,pointNode);
        }
        for (MyEdge myEdge : myEdgeList) {
            int node = myEdge.getUserIdFirst(); //因为是无向图，所以牵涉到两个头结点
            int anotherNode = myEdge.getUserIdSecond();
            int weight = myEdge.getWeight();
            pointNodeMap.get(node).addPoint(new Point(anotherNode,weight));
            pointNodeMap.get(anotherNode).addPoint(new Point(node,weight));
        }
    }

    /**
     * 这个方法用于统计图内有多少结点
     * @param myEdgeList
     * @return
     */
    private HashSet<Integer> initPointSet(List<MyEdge> myEdgeList){
        HashSet<Integer> integerHashSet = new HashSet<Integer>();
        for(MyEdge MyEdge : myEdgeList){
            integerHashSet.add(MyEdge.getUserIdFirst());
            integerHashSet.add(MyEdge.getUserIdSecond());
        }
        return integerHashSet;
    }

    public void disPlayGraph() {
        StringBuilder stringBuilder = new StringBuilder()
                .append("Graph Node Number:" ).append(size)
                .append("\nGraph nodes and its next points:\n");
        System.out.println(stringBuilder.toString());
        Iterator<Integer> integerIterator = pointSet.iterator();
        while (integerIterator.hasNext()){
            int i = integerIterator.next();
            System.out.println("Head Node:" + i);
            List<Point> pointList = pointNodeMap.get(i).getNextPointList();
            for (Point point : pointList) {
                System.out.println("( " + point.getPointId() + " , " + point.getWeight() + ")");
            }
        }
    }

}
