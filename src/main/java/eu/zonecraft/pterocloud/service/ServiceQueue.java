package eu.zonecraft.pterocloud.service;

import eu.zonecraft.pterocloud.PteroCloud;

public class ServiceQueue {

    String groupName;

    public ServiceQueue(String groupName) {
        this.groupName = groupName;
    }

    public void startServices() {
        for(int i = 0; i < Integer.parseInt(PteroCloud.getInstance().getGroupManager().get(groupName, "minOnline")); i++) {
            PteroCloud.getInstance().getServiceManager().startService(groupName);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
