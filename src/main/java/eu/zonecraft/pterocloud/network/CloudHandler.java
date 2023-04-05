package eu.zonecraft.pterocloud.network;

public class CloudHandler {

    /*public CloudHandler() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Storage storage = new Storage(FileUtils.CONFIG_FILE);
                        ServerSocket serverSocket = new ServerSocket(Integer.parseInt(storage.getArray("cloudAPIConfiguration", "port")));
                        Socket server = serverSocket.accept();
                        System.out.println("Verbunden mit: " + server.getRemoteSocketAddress());

                        // Hier erfolgt die Authentifizierung
                        String key = "1234";
                        BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
                        String receivedKey = in.readLine();

                        if(receivedKey.equals(key)) {
                            System.out.println("Authentifizierung erfolgreich.");
                            // Hier können Sie Code hinzufügen, um Daten mit dem Client auszutauschen.
                        } else {
                            System.out.println("Authentifizierung fehlgeschlagen.");
                        }

                        server.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        });
        thread.start();
    }*/

}
