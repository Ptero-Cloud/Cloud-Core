package eu.zonecraft.pterocloud.service.impl;

import eu.zonecraft.pterocloud.service.ICloudService;

public class TempService implements ICloudService {

    String groupName;
    String serviceName;

    public TempService(String groupName, String serviceName) {
        this.groupName = groupName;
        this.serviceName = serviceName;
    }

    @Override
    public void create() {

    }

    @Override
    public void delete() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void restart() {

    }

}
