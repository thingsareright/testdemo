
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类主要用于输出
 */

public class TestMain {
    public static void main(String[] args){
        List<MyEdge> edgeList = new ArrayList<MyEdge>();
        edgeList = MyFileUtil.readEdges("G://Facebook_data.txt");
        for (MyEdge myEdge: edgeList
             ) {
            System.out.println(myEdge);
        }

        //第一步，根据边集制造总的不要求连通的图
        //第二步，选出最大连通子图
        //第三步，构造最小生成树并输出
        System.out.println("*****************");
        System.out.println(edgeList.size());
    }
}
