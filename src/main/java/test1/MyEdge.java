package test1;

/**
 * 我们以边为主导
 */

public class MyEdge {
    private int userIdFirst;
    private int userIdSecond;
    private int weight;

    public MyEdge(int userIdFirst, int userIdSecond, int weight) {
        this.userIdFirst = userIdFirst;
        this.userIdSecond = userIdSecond;
        this.weight = weight;
    }

    public MyEdge() {
    }

    public int getUserIdFirst() {
        return userIdFirst;
    }

    public void setUserIdFirst(int userIdFirst) {
        this.userIdFirst = userIdFirst;
    }

    public int getUserIdSecond() {
        return userIdSecond;
    }

    public void setUserIdSecond(int userIdSecond) {
        this.userIdSecond = userIdSecond;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * 方便打印和调试输出
     * @return
     */
    @Override
    public String toString() {
        return "MyEdge{" +
                "userIdFirst=" + userIdFirst +
                ", userIdSecond=" + userIdSecond +
                ", weight=" + weight +
                '}';
    }
}
