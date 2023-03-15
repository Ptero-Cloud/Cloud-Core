package eu.zonecraft.pterocloud.service;

import eu.zonecraft.pterocloud.PteroCloud;
import eu.zonecraft.pterocloud.service.impl.StaticService;
import eu.zonecraft.pterocloud.service.impl.TempService;
import eu.zonecraft.pterocloud.utils.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ServiceManager {

    public ServiceManager() {
        if (!FileUtils.SERVICE_FOLDER.exists()) {
            FileUtils.SERVICE_FOLDER.mkdirs();
        }
    }

    //  Service, Group
    public Map<String, String> CACHED_GROUP_SERVICES = new HashMap<>();

    //  Service, Identifier
    public Map<String, String> CACHED_IDENTIFIER_SERVICES = new HashMap<>();

    //  Service, Identifier
    public Map<String, String> CACHED_ID_SERVICES = new HashMap<>();


    public void startService(String groupName) {
        String serviceName = groupName + "-" + (CACHED_GROUP_SERVICES.size() + 1);
        if (PteroCloud.getInstance().getGroupManager().isGroupStatic(groupName)) {
            new StaticService(groupName, serviceName);
        } else {
            new TempService(groupName, serviceName);
        }
    }

    public void stopService(String serviceName) {
        CACHED_GROUP_SERVICES.remove(serviceName);
        if (PteroCloud.getInstance().getGroupManager().isGroupStatic(getGroupNameFromServiceName(serviceName))) {
            new StaticService(getGroupNameFromServiceName(serviceName), serviceName).restart();
        } else {
            CACHED_IDENTIFIER_SERVICES.remove(serviceName);
            CACHED_ID_SERVICES.remove(serviceName);
            new TempService(getGroupNameFromServiceName(serviceName), serviceName).stop();
            new TempService(getGroupNameFromServiceName(serviceName), serviceName).delete();
        }
    }

    public boolean serviceExists(String serviceName) {
        return CACHED_GROUP_SERVICES.containsKey(serviceName);
    }

    public String getGroupNameFromServiceName(String serviceName) {
        for (Map.Entry<String, String> entry : CACHED_GROUP_SERVICES.entrySet()) {
            if (entry.getValue().equals(serviceName)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public String getServiceFromGroup(String groupName) {
        for (Map.Entry<String, String> entry : CACHED_GROUP_SERVICES.entrySet()) {
            if (entry.getValue().equals(groupName)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public File getServiceConfigFile(String serviceName) {
        return new File(FileUtils.SERVICE_FOLDER, serviceName + ".json");
    }

    public void stopAllServicesFromGroup(String groupName) {
        
    }

}
