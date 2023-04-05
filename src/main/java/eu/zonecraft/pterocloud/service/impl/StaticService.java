package eu.zonecraft.pterocloud.service.impl;

import com.mattmalec.pterodactyl4j.DataType;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationServer;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import eu.zonecraft.pterocloud.PteroCloud;
import eu.zonecraft.pterocloud.service.ICloudService;
import eu.zonecraft.pterocloud.utils.FileUtils;
import eu.zonecraft.pterocloud.utils.storage.Storage;

import java.io.File;

public class StaticService implements ICloudService {

    String groupName;
    String serviceName;

    ApplicationServer applicationServer;


    public StaticService(String groupName, String serviceName) {
        this.groupName = groupName;
        this.serviceName = serviceName;
    }

    @Override
    public void create() {
        Storage storage = new Storage(FileUtils.CONFIG_FILE);

        applicationServer = PteroCloud.getInstance().pteroApplication.createServer()
                .setName(serviceName)
                .setDescription(serviceName + " from group: " + groupName)
                .setOwner(PteroCloud.getInstance().pteroApplication.retrieveUserById(storage.getArray("pterodactylConfiguration", "ownerID")).execute())
                .setEgg(PteroCloud.getInstance().pteroApplication.retrieveEggById(PteroCloud.getInstance().pteroApplication.retrieveNestById("1").execute(), PteroCloud.getInstance().getGroupManager().isProxy(groupName) ? "5" : "1").execute())
                .setLocation(PteroCloud.getInstance().pteroApplication.retrieveLocationById("1").execute())
                .setMemory(0, DataType.MB)
                .setPort(0)
                .setDisk(0, DataType.MB)
                .execute();

        Storage serviceStorage = new Storage(new File(FileUtils.SERVICE_FOLDER, serviceName + ".json"));
        serviceStorage.set("groupName", groupName);
        serviceStorage.set("serviceName", serviceName);
        serviceStorage.set("identifier", applicationServer.getIdentifier());
        serviceStorage.set("id", applicationServer.getId());

        PteroCloud.getInstance().getServiceManager().CACHED_IDENTIFIER_SERVICES.put(serviceName, applicationServer.getIdentifier());
        PteroCloud.getInstance().getServiceManager().CACHED_ID_SERVICES.put(serviceName, applicationServer.getId());
    }


    @Override
    public void delete() {
        PteroCloud.getInstance().pteroApplication.retrieveServerById(PteroCloud.getInstance().getServiceManager().CACHED_ID_SERVICES.get(serviceName)).execute().getController().delete(true);
        PteroCloud.getInstance().getServiceManager().getServiceConfigFile(serviceName).delete();
    }

    @Override
    public void start() {
        PteroCloud.getInstance().pteroClient.retrieveServerByIdentifier(PteroCloud.getInstance().getServiceManager().CACHED_ID_SERVICES.get(serviceName)).flatMap(ClientServer::start).execute();
    }

    @Override
    public void stop() {
        PteroCloud.getInstance().pteroClient.retrieveServerByIdentifier(PteroCloud.getInstance().getServiceManager().CACHED_ID_SERVICES.get(serviceName)).flatMap(ClientServer::stop).execute();

    }

    @Override
    public void restart() {
        PteroCloud.getInstance().pteroClient.retrieveServerByIdentifier(PteroCloud.getInstance().getServiceManager().CACHED_ID_SERVICES.get(serviceName)).flatMap(ClientServer::restart).execute();
    }

}
