package test2;

import com.sun.corba.se.spi.legacy.connection.Connection;
import test1.ConnectedGraph;
import test1.MyEdge;
import util.MyFileUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class ShortestPathFloyd {

    /*邻接矩阵*/
    private int[][] matrix;
    /*表示两点间无边*/
    private int MAX_WEIGHT = Integer.MAX_VALUE;
    /*路径矩阵*/
    private int[][] pathMatrix;
    /*前驱表*/
    private int[][] preTable;

    /**
     * 建图
     */
    private void createGraph(){
        List<MyEdge> myEdges = MyFileUtil.readEdges("G://Facebook_data.txt");
        ConnectedGraph connectedGraph = new ConnectedGraph(myEdges);
        int index = connectedGraph.getSize();//我们下标从0开始
        matrix = new int[1000][1000];

        initMatrix(1000);
        Iterator<MyEdge> myEdgeIterator = myEdges.listIterator();
        MyEdge myEdge = null;
        while (myEdgeIterator.hasNext()){
            myEdge = myEdgeIterator.next();
            int x = myEdge.getUserIdFirst();
            int y = myEdge.getUserIdSecond();
            matrix[x][y] = myEdge.getWeight();
            matrix[y][x] = myEdge.getWeight();
        }
    }

    private void initMatrix(int index){
        for(int i=0; i<index; i++){
            for(int j=0; j<index; j++){
                matrix[i][j] = MAX_WEIGHT;
            }
        }
    }

    //算法
    public void floyd(){
        //路径矩阵，表示顶点到顶点的最短路径权值之和，初始化时就是邻接矩阵
        pathMatrix = new int[matrix.length][matrix.length];
        //前驱矩阵，P[m][n]为m到n的最短路径的前驱结点，如果是直连则为n
        preTable = new int[matrix.length][matrix.length];
        //初始化
        for (int i=0; i<matrix.length; i++){
            for (int j=0; j<matrix.length; j++){
                pathMatrix[i][j] = matrix[i][j];
                preTable[i][j] = j;
            }
        }
        //循环，中间经过顶点
        for(int i=0; i<pathMatrix.length; i++){
            //循环所有路径
            for(int m=0; m<pathMatrix.length; m++){
                for(int n=0; n<pathMatrix.length; n++){
                    int mn = pathMatrix[m][n];
                    int mi = pathMatrix[m][i];
                    int in = pathMatrix[i][n];
                    int addedPath = (mi == Integer.MAX_VALUE || in == Integer.MAX_VALUE)?Integer.MAX_VALUE:(mi + in);

                    if (addedPath < mn){
                        pathMatrix[m][n] = addedPath;
                        preTable[m][n] = preTable[m][i];
                    }
                }
            }
        }

    }

    /**
     * 返回最短路径顶点的有序集合
     * @return
     */
    public ArrayList<Integer> getShortPath(int start, int end){
        ArrayList<Integer> integers = new ArrayList<Integer>();
        integers.add(start);
        int k = preTable[start][end];
        while (k != end){
            integers.add(k);
            k = preTable[k][end];
        }
        integers.add(end);
        return integers;
    }

    public void disPlay(){
        for (int i=0; i<matrix.length;i++){
            for(int j=0; j<matrix.length;j++){
                System.out.print(preTable[i][j] + "  ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args){
        ShortestPathFloyd shortestPathFloyd = new ShortestPathFloyd();
        shortestPathFloyd.createGraph();
        shortestPathFloyd.floyd();
        Scanner sc = new Scanner(System.in);

        int flag = 1;
        while (0 != flag){
            System.out.println("请输入起始点：");
            int start = sc.nextInt();
            System.out.println("请输入终止点：");
            int end = sc.nextInt();

            System.out.println("最短路径顶点序列：");
            //shortestPathFloyd.disPlay();
            ArrayList<Integer> integers = shortestPathFloyd.getShortPath(start, end);
            if (null == integers){
                System.out.println("此两点不连通！");
                return;
            }
            Iterator<Integer> integerIterator = integers.listIterator();

            while (integerIterator.hasNext()){
                System.out.println(integerIterator.next());
            }
            System.out.println("路径权值：" + shortestPathFloyd.pathMatrix[start][end]);

            flag = sc.nextInt();
        }

    }
}
