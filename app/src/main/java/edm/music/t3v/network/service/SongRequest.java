package edm.music.t3v.network.service;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vuong_IT on 20/11/2017.
 */

public class SongRequest {

    @SerializedName("url")
    private String url;

    @SerializedName("page")
    private int page;

    public SongRequest(String url) {
        this.url = url;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
