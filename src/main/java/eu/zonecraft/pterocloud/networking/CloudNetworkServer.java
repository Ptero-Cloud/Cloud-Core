package eu.zonecraft.pterocloud.networking;

import eu.zonecraft.pterocloud.utils.FileUtils;
import eu.zonecraft.pterocloud.utils.storage.Storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class CloudNetworkServer {
    
    Storage storage;
    int port;
    String serviceName;
    String serviceHost;
    int servicePort;
    ServerSocket serverSocket;
    Socket clientSocket;
    Thread thread;
    BufferedReader reader;
    PrintWriter writer;
    String AUTH_KEY = storage.getArray("apiConfiguration", "authKey");

    public CloudNetworkServer(int port, String serviceHost, int servicePort, String serviceName) {
        storage = new Storage(FileUtils.CONFIG_FILE);
        this.port = port;
        this.serviceName = serviceName;
        this.serviceHost = serviceHost;
        this.servicePort = servicePort;
    }


    public void start() {
        thread = new Thread(() -> {
            try {
                serverSocket = new ServerSocket(this.port);
                while (true) {
                    //Waiting for Connection
                    clientSocket = serverSocket.accept();

                    reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    writer = new PrintWriter(clientSocket.getOutputStream(), true);

                    String key = reader.readLine();

                    if (checkCredentials(key)) {
                        // Logged in
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public boolean checkCredentials(String key) {
        return AUTH_KEY.equals(key);
    }

}
