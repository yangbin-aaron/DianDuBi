package cn.com.cunw.diandubiapp.beans;

/**
 * @author YangBin
 * @time 2019/8/9 18:04
 * @des
 * @copyright 湖南新云网科技有限公司
 */
public class Test {
    private String id;
    private String classRoom;

    public String getId() {
        return id;
    }

    public String getClassRoom() {
        return classRoom;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id='" + id + '\'' +
                ", classRoom='" + classRoom + '\'' +
                '}';
    }
}
