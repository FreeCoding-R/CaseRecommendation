package freecoding.entity;

/**
 * Created by loick on 15/07/2017.
 */
public class JDocument {

    private String info;

    private String character;

    public JDocument(String info, String character){
        this.info = info;
        this.character = character;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
