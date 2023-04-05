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
    private String clientAPI;
    private String applicationAPI;

    public PteroAuthManager(String host, String clientKey, String applicationKey) {
        this.host = host;
        this.clientAPI = clientKey;
        this.applicationAPI = applicationKey;

        MessageUtils.sendAnimatedMessage(MessageType.INFO, "Trying to connect to Pterodactyl Panel...");
        try {
            PteroCloud.getInstance().pteroApplication = PteroBuilder.createApplication(host, applicationKey);
            PteroCloud.getInstance().pteroClient = PteroBuilder.createClient(host, clientKey);
            PteroCloud.getInstance().pteroApplication.retrieveServers().execute();
            PteroCloud.getInstance().pteroClient.retrieveServers().execute();
        }catch (Exception exception) {
            PteroCloud.getUtils().finishAnimation(false);

            MessageUtils.sendMessage(MessageType.ERROR, "An error occurred while connecting to Pterodactyl. Please check your credentials.");
            MessageUtils.sendMessage(MessageType.ERROR, "Error: " + exception.getMessage());

            MessageUtils.sendMessage(Color.RED + "----------------------------------------------------");
            exception.printStackTrace();
            MessageUtils.sendMessage(Color.RED + "Host: " + host);
            MessageUtils.sendMessage(Color.RED + "Client Key: " + clientKey);
            MessageUtils.sendMessage(Color.RED + "Application Key: " + applicationKey);
            MessageUtils.sendMessage(Color.RED + "----------------------------------------------------");

            System.exit(1);
        }
        PteroCloud.getUtils().finishAnimation(true);
    }

    public String getHost() {
        return host;
    }

    public String getClientAPI() {
        return clientAPI;
    }

    public String getApplicationAPI() {
        return applicationAPI;
    }

}
