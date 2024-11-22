package services;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.SongVO;

public class ClientSongHandler {

    private ObjectInputStream inputStream;
    private ArrayList<SongVO> dataList;
    private Socket socket;

    public ClientSongHandler(Socket socket) {
        this.socket = socket;
        try {
            inputStream = new ObjectInputStream(this.socket.getInputStream());

        } catch (IOException e) {
            System.out.println("Problemas creando un nuevo input" + e.getMessage());
        }
    }

    public ArrayList<SongVO> readSongList() {
        try {
            dataList = (ArrayList<SongVO>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ClientSongHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dataList;
    }

    public void setDataList(ArrayList<SongVO> dataList) {
        this.dataList = dataList;
    }

    public ArrayList<SongVO> getDataList() {
        return dataList;
    }

}
