package edm.music.t3v.model;

/**
 * Created by Vuong_IT on 21/11/2017.
 */

public class Mp3Link {

    private String src;

    private String type;


    public Mp3Link(String src, String type) {
        this.src = src;
        this.type = type;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
