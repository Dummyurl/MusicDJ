package edm.music.t3v.model;

/**
 * Created by Vuong_IT on 19/11/2017.
 */

public class Kind {

    private String name;

    private String link;

    public Kind(String name, String link) {
        this.name = name;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
