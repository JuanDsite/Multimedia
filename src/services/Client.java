package services;

import controller.LoginManager;
import controller.SelectorManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.SongVO;

public class Client {

    private final String host = null;
    private int port;
    private String ip;
    Socket socket = null;
    BufferedReader input = null;
    PrintWriter output = null;
    private final LoginManager loginManager;
    private final SelectorManager selectorManager;
    private ClientSongHandler songHandler;
    private ArrayList<SongVO> arraySong;
    private ArrayList<SongVO> downloadedArray;

    public Client() {
        this.loginManager = new LoginManager(this);
        this.selectorManager = new SelectorManager(this);

    }

//------------------------------------------------------------------------------------------------------------------------------\\  
    public boolean iniciate() {
        if (!connect()) {
            return false;
        }
        return !(!getOutputStream() || !getInputStream());
    }

    private boolean connect() {
        try {
            socket = new Socket(host, port);
        } catch (UnknownHostException exc) {
            return false;
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private boolean getOutputStream() {
        try {
            output = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException exc) {
            return false;
        }
        return true;
    }

    private boolean getInputStream() {
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            return false;
        }
        return true;
    }

// ------------------------------------------------------------------------------------------------------------------------------ \\  
    public boolean credentials() {
        output.println(loginManager.getLogin().getUserField().getText());
        output.println(new String(loginManager.getLogin().getPasswordField().getPassword()));
        // Espera respuesta de validación del servidor
        String response = read();
        return "VALID".equals(response);
    }

    public void protocol() {
        songHandler = new ClientSongHandler(socket);
        arraySong = new ArrayList<>();
        arraySong = songHandler.readSongList();
        selectorManager.showSongs();
        selectorManager.manageQuery();
    }

    public ArrayList<SongVO> getArraySong() {
        return arraySong;
    }

// _________________________________
    public void songDownloading(String songName) {
        output.println("download request");
        output.println(songName);
        output.flush();

        String responseDownload = read();

        switch (responseDownload) {
            case "User with no due balance" ->
                selectorManager.showDownloadMessage();
            case "song has already been downloaded" ->
                selectorManager.showSongAlreadyDownloaded();
            default -> {
                double balanceDue = Double.parseDouble(responseDownload);
                selectorManager.showDueMessage(balanceDue);
            }
        }
    }

    public void payment() {
        output.println("payment request");
        output.flush();

        String responsePay = read();

        if (responsePay.equals("processing payment")) {
            selectorManager.showPaymentMessage();

        } else {
            double balancePayment = Double.parseDouble(responsePay);
            selectorManager.showDueMessage(balancePayment);
        }
    }

    public void songRequest() {
        downloadedArray = new ArrayList<>();
        songHandler.setDataList(downloadedArray);
        output.println("show songs");
        output.flush();
        downloadedArray = songHandler.readSongList();
        selectorManager.receiveDownloadedSongs(downloadedArray);
    }

    public void endOfSession() {
        output.println("log out");
        output.flush();

        String response = read(); // recibe el balance de cuanto debe
        selectorManager.finishSession(response);
    }
// _________________________________

// ------------------------------------------------------------------------------------------------------------------------------ \\  
    public void setPort(int port) {
        this.port = port;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public SelectorManager getSelectorManager() {
        return selectorManager;
    }

    @SuppressWarnings("empty-statement")
    private String read() {
        String temp = null;
        try {
            while ((temp = input.readLine()) == null)
                ;
        } catch (IOException exc) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, exc);
        }
        return temp;
    }

    public boolean finish() {
        try {
            input.close();
            output.close();
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (IOException exc) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, exc);
            return false;
        }

        return true;
    }

// -------------------------------------------------------------------------- \\  
    public static void main(String[] args) {
        Client client = new Client();
        client.iniciate();
    }
}
