package freecoding.vo;

/**
 * Created by zhujing on 2017/7/17.
 */
public class Law implements Comparable{

    private String name;

    private String detail;

    private int num;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return name+" 第"+detail+"条";
    }


    @Override
    public int compareTo(Object o) {
        Law l=(Law)o;
        if(this.toString().compareTo(l.toString())<0){
            return 1;

        }
        return -1;
    }
}
