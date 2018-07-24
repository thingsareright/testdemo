import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MyFileUtil {

    /**
     * 这个方法主要用于从指定文件中读取数据，目前已经经过了测试
     * @param filePath
     * @return
     */
    public static List<MyEdge> readEdges(String filePath){
        List<MyEdge> edgeList = new ArrayList<MyEdge>();
        File file = new File(filePath);
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String read = null;

            while ((read = bufferedReader.readLine()) != null) {
                MyEdge edge = new MyEdge();
                //删除多余空格
                String[] strs = read.replaceAll("\\s{1,}", " ").split(" ");
                edge.setUserIdFirst(Integer.parseInt(strs[0]));
                edge.setUserIdSecond(Integer.parseInt(strs[1]));
                edge.setWeight(Integer.parseInt(strs[2]));
                edgeList.add(edge);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("没有发现文件");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return edgeList;
    }
}
