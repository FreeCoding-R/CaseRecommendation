package freecoding.vo;


import java.util.List;

/**
 * Created by zhujing on 2017/7/18.
 */
public class Case {
    private String name;
    private String id;
    private List<Law> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public List<Law> getList() {
        return list;
    }

    public void setList(List<Law> list) {
        this.list = list;
    }

    public void setId(String id) {
        this.id = id;

    }
}
