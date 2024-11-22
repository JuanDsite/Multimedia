package model;

import java.io.Serializable;

public class SongVO implements Serializable {

    private final int songId;
    private final String songName;
    private final String url;

    public SongVO(int songId, String songName, String url) {
        this.songId = songId;
        this.songName = songName;
        this.url = url;
    }

    public int getId() {
        return songId;
    }

    public String getSongName() {
        return songName;
    }
    
    public String getUrl (){
        return url;
    }

}
