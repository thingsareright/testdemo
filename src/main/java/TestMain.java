
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 这个类主要用于输出
 */

public class TestMain {
    public static void main(String[] args){
        List<MyEdge> edgeList = new ArrayList<MyEdge>();
        edgeList = MyFileUtil.readEdges("G://Facebook_data.txt");
        /*for (MyEdge myEdge: edgeList
             ) {
            System.out.println(myEdge);
        }*/

        //第一步，根据边集制造总的不要求连通的图
        ConnectedGraph connectedGraph = new ConnectedGraph(edgeList);
        //connectedGraph.disPlayGraph();
        //第二步，选出连通子图集中的最大连通子图
        List<ConnectedGraph> connectedGraphs = connectedGraph.separateGraphs();
        int max = 0;
        ConnectedGraph maxConnectedGraph = null;
        Iterator<ConnectedGraph> connectedGraphIterator = connectedGraphs.iterator();
        ConnectedGraph connectedGraphIter = null;
        while (connectedGraphIterator.hasNext()){
            connectedGraphIter = connectedGraphIterator.next();

            if (null == maxConnectedGraph || maxConnectedGraph.size > max){
                maxConnectedGraph = connectedGraphIterator.next();
                max = maxConnectedGraph.size;   //别忘了这一步，不然一直就是最后一个
            }
        }
        maxConnectedGraph.disPlayGraph();

        //第三步，构造最小生成树并输出
        System.out.println(edgeList.size());
    }
}
