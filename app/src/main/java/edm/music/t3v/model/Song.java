package edm.music.t3v.model;

/**
 * Created by Vuong_IT on 20/11/2017.
 */

public class Song {

    private String name;

    private String link;

    private String view;

    private String poster;

    public Song(String name, String link, String view, String poster) {
        this.name = name;
        this.link = link;
        this.view = view;
        this.poster = poster;
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

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
