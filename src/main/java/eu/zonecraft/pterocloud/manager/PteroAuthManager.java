package eu.zonecraft.pterocloud.manager;

import com.mattmalec.pterodactyl4j.PteroBuilder;
import eu.zonecraft.pterocloud.PteroCloud;
import eu.zonecraft.pterocloud.utils.FileUtils;
import eu.zonecraft.pterocloud.utils.message.Color;
import eu.zonecraft.pterocloud.utils.message.MessageType;
import eu.zonecraft.pterocloud.utils.message.MessageUtils;
import eu.zonecraft.pterocloud.utils.storage.Storage;

public class PteroAuthManager {

    private String host;
    private String clientKey;
    private String applicationKey;

    public PteroAuthManager(String host, String clientKey, String applicationKey) {
        this.host = host;
        this.clientKey = clientKey;
        this.applicationKey = applicationKey;

        MessageUtils.sendAnimatedMessage(MessageType.INFO, "Trying to connect to Pterodactyl Panel...");
        try {
            PteroCloud.getInstance().pteroApplication = PteroBuilder.createApplication(host, this.applicationKey);
            PteroCloud.getInstance().pteroClient = PteroBuilder.createClient(host, this.clientKey);
            PteroCloud.getInstance().pteroApplication.retrieveServers().execute();
            PteroCloud.getInstance().pteroClient.retrieveServers().execute();
        }catch (Exception exception) {
            PteroCloud.getUtils().finishAnimation(false);
            Storage storage = new Storage(FileUtils.CONFIG_FILE);

            MessageUtils.sendMessage(MessageType.ERROR, "An error occurred while connecting to Pterodactyl. Please check your credentials.");
            MessageUtils.sendMessage(MessageType.ERROR, "Error: " + exception.getMessage());

            MessageUtils.sendMessage(Color.RED + "----------------------------------------------------");
            exception.printStackTrace();
            MessageUtils.sendMessage(Color.RED + "Host: " + host);
            MessageUtils.sendMessage(Color.RED + "Client Key: " + clientKey);
            MessageUtils.sendMessage(Color.RED + "Application Key: " + applicationKey);
            MessageUtils.sendMessage(Color.RED + "Host: " + storage.getArray("pterodactyl", "host"));
            MessageUtils.sendMessage(Color.RED + "Client API: " + storage.getArray("pterodactyl", "clientAPI"));
            MessageUtils.sendMessage(Color.RED + "Application API: " + storage.getArray("pterodactyl", "applicationAPI"));
            MessageUtils.sendMessage(Color.RED + "----------------------------------------------------");

            System.exit(1);
        }
        PteroCloud.getUtils().finishAnimation(true);
    }

    public String getHost() {
        return host;
    }

    public String getClientKey() {
        return clientKey;
    }

    public String getApplicationKey() {
        return applicationKey;
    }

}
