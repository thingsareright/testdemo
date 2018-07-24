import java.util.HashSet;
import java.util.List;

/**
 * 这是一个无向连通图的邻接矩阵类
 */

public class ConnectedGraph {

    int size;   //图顶点个数
    HashSet<Integer> pointSet; //图顶点编号
    int[][] matrix; //图关系矩阵

    public ConnectedGraph(List<MyEdge> myEdgeList) {
        this.pointSet = sizeOfPoint(myEdgeList);
        size = pointSet.size();
        matrix = new int[size][size];
        for (MyEdge MyEdge : myEdgeList) {
            matrix[MyEdge.getUserIdFirst()][MyEdge.getUserIdSecond()] = MyEdge.getWeight();
        }
    }

    /**
     * 这个方法用于统计图内有多少结点
     * @param myEdgeList
     * @return
     */
    private HashSet<Integer> sizeOfPoint(List<MyEdge> myEdgeList){
        HashSet<Integer> integerHashSet = new HashSet<Integer>();
        for(MyEdge MyEdge : myEdgeList){
            integerHashSet.add(MyEdge.getUserIdFirst());
            integerHashSet.add(MyEdge.getUserIdSecond());
        }
        return integerHashSet;
    }


}
