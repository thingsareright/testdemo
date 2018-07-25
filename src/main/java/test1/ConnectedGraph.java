package test1;

import java.util.*;

/**
 * 这是一个无向连通图的邻接矩阵类
 */
public class ConnectedGraph {

    int size;   //图顶点个数
    HashSet<Integer> pointSet; //图顶点编号
    //int[][] matrix; 一开始是打算邻接矩阵，后来废弃了，还是邻接表合适
    HashMap<Integer, PointNode> pointNodeMap;  //图的邻接矩阵
    List<MyEdge> pointEdgeList; //存储构造图的原始信息


    public ConnectedGraph(List<MyEdge> myEdgeList) {
        this.pointSet = initPointSet(myEdgeList);
        pointNodeMap = new HashMap<Integer, PointNode>();
        size = pointSet.size();
        pointEdgeList = myEdgeList;
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
        System.out.println("Totally edge number: " + pointEdgeList.size());
    }

    /**
     * 从某一结点对图进行广度优先遍历
     * @param pointId   //点的唯一标识
     * @return  //返回的是一个点标识集合
     */
    public HashSet<Integer> myBFS(Integer pointId){
        List<MyEdge> myEdges = new ArrayList<MyEdge>();
        //先找出此结点能遍历的所有结点
        Queue<Integer> pointQueue = new LinkedList<Integer>();
        HashSet<Integer> already = new HashSet<Integer>();  //已访问的元素
        pointQueue.offer(pointId);
        while (0 != pointQueue.size()){
            Integer one = pointQueue.poll();
                List<Point> pointList = pointNodeMap.get(one).getNextPointList();
                already.add(one);
                Iterator<Point> pointIterator = pointList.listIterator();
                while (pointIterator.hasNext()){
                    Point point = pointIterator.next();
                    if (already.contains(point.getPointId())){
                        continue;
                    } else {
                        pointQueue.offer(point.getPointId());
                    }
                }

        }
        return already;
    }

    /**
     * 从这个图中根据某结点，分离出一个连通子图
     */
    public ConnectedGraph separateGraph(Integer pointId){
        List<MyEdge> newEdgeList = new ArrayList<MyEdge>();
        HashSet<Integer> newGraphPointSet = myBFS(pointId);
        Iterator<MyEdge> myEdgeIterator = pointEdgeList.listIterator();
        MyEdge myEdge = null;
        while (myEdgeIterator.hasNext()) {
            myEdge = myEdgeIterator.next();
            if (newGraphPointSet.contains(myEdge.getUserIdFirst()) || newGraphPointSet.contains(myEdge.getUserIdSecond())){
                //原始信息有用了吧，以空间换时间
                newEdgeList.add(myEdge);
            }
        }

        return new ConnectedGraph(newEdgeList);
    }

    public List<ConnectedGraph> separateGraphs(){
        HashSet<Integer> allPointSet = new HashSet<Integer>();
        Iterator<Integer> integerIterator = pointSet.iterator();    //深复制一下，省的改变原数据
        while (integerIterator.hasNext()){
            allPointSet.add(integerIterator.next());
        }

        List<ConnectedGraph> connectedGraphs = new ArrayList<ConnectedGraph>();

        while (!allPointSet.isEmpty()){
            integerIterator = allPointSet.iterator();
            Integer pointId = integerIterator.next();   //找个顶点，分个图
            ConnectedGraph connectedGraph = separateGraph(pointId);
            connectedGraphs.add(connectedGraph);
            allPointSet.removeAll(connectedGraph.pointSet); //别忘了把顶点去掉，不然就死循环了
        }
        return connectedGraphs;
    }

    /**
     * 利用自身边的构造信息构造最小生成树
     * 注意，此时必须是一个连通图
     * @return
     */
    public List<MyEdge> constructMST(){
        List<MyEdge> myMSTEdgeList = new ArrayList<MyEdge>();
        ArrayList<HashSet<Integer>> pointAlreadyHashSets = new ArrayList<HashSet<Integer>>();
        //这是表示生成树中已经加了的点
        Iterator<Integer> integerIterator = pointSet.iterator();
        while (integerIterator.hasNext()){
            HashSet<Integer> integers = new HashSet<Integer>();
            integers.add(integerIterator.next());
            pointAlreadyHashSets.add(integers);
        }
        //按照权值对边集合重排序，注意此时会更改原有数据
        Collections.sort(pointEdgeList, new Comparator<MyEdge>() {
            public int compare(MyEdge o1, MyEdge o2) {
                int i = o1.getWeight() - o2.getWeight();
                if(i > 0)
                    return 1;
                else if(i < 0)
                    return -1;
                return 0;
            }
        });

        int edgeNum = pointSet.size() - 1;  //边数为结点数减1
        Iterator<MyEdge> myEdgeIterator = pointEdgeList.listIterator();
        while (myEdgeIterator.hasNext() && edgeNum > 0){
            MyEdge myEdge = myEdgeIterator.next();
            int pointOne = myEdge.getUserIdFirst();
            int pointTwo = myEdge.getUserIdSecond();
            int count1 = -1, count2 = -2;
            for(int i=0; i<pointAlreadyHashSets.size(); i++){
                HashSet<Integer> set = pointAlreadyHashSets.get(i);
                if(set.contains(pointOne)){
                    count1 = i;
                }
                if (set.contains(pointTwo)){
                    count2 = i;
                }
            }
            if (count1 < 0 || count2 < 0){System.err.println("结点不在树中");}
            else if (count1 != count2){
                HashSet set1 = pointAlreadyHashSets.get(count2);
                HashSet set2 = pointAlreadyHashSets.get(count1);
                if(count1 > count2){
                    pointAlreadyHashSets.remove(count1);
                    pointAlreadyHashSets.remove(count2);
                } else {
                    pointAlreadyHashSets.remove(count2);
                    pointAlreadyHashSets.remove(count1);
                }
                set1.addAll(set2);
                pointAlreadyHashSets.add(set1);
                myMSTEdgeList.add(myEdge);
                edgeNum --;
            }
        }

        return myMSTEdgeList;
    }



    /**
     * 向外界输出边权重之和
     * @return
     */
    public int totalWeight(){
        int totalWeight = 0;

        for (MyEdge myEdge:
             pointEdgeList) {
            totalWeight += myEdge.getWeight();
        }

        return totalWeight;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
