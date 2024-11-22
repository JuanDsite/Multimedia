package model;

public class SongUserVO {
    
    private final int idUser;
    private final int idSong;
    
    public SongUserVO(int idUser, int idSong){
        this.idSong = idSong;
        this.idUser = idUser;
        
    }

    public int getIdUser() {
        return idUser;
    }

    public int getIdSong() {
        return idSong;
    }
}