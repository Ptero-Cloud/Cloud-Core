package eu.zonecraft.pterocloud.groups;

import eu.zonecraft.pterocloud.utils.FileUtils;
import eu.zonecraft.pterocloud.utils.GroupType;
import eu.zonecraft.pterocloud.utils.storage.Storage;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GroupManager {

    @Getter
    List<String> groups = new ArrayList<>();

    public GroupManager() {
        if (!FileUtils.GROUPS_FOLDER.exists()) {
            FileUtils.GROUPS_FOLDER.mkdirs();
        }
        File[] groupFiles = FileUtils.GROUPS_FOLDER.listFiles();
        for (File groupFile : groupFiles) {
            groups.add(groupFile.getName().replaceAll(".json", ""));
        }
    }

    public void createGroup(String groupName, int memory, int maxPlayers, int minOnline, int maxOnline, boolean isStatic, boolean isAutoStart, GroupType type) {
        Storage storage = new Storage(new File(FileUtils.GROUPS_FOLDER, groupName + ".json"));
        storage.set("name", groupName);
        storage.set("memory", memory + "");
        storage.set("maxPlayers", maxPlayers + "");
        storage.set("minOnline", minOnline + "");
        storage.set("maxOnline", maxOnline + "");
        storage.set("static", isStatic + "");
        storage.set("groupType", type + "");
        storage.set("isAutoStart", isAutoStart + "");

    }
    public String get(String groupName, String path) {
        Storage storage = new Storage(new File(FileUtils.GROUPS_FOLDER, groupName + ".json"));
        return storage.get(path);
    }

    public String listRegisteredGroups() {
        if (!groups.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String group : groups) {
                stringBuilder.append(group).append(", ");
                if (groups.indexOf(group) == groups.size() - 1) {
                    stringBuilder.delete(stringBuilder.lastIndexOf(","), stringBuilder.lastIndexOf(",") + 1);
                }
            }
            return stringBuilder.toString();
        }
        return "No groups found!";
    }

    public boolean groupExists(String groupName) {
        return groups.contains(groupName);
    }

    public void deleteGroup(String groupName) {
        //Todo: Stop all Services from Group
        groups.remove(groupName);
        getGroupConfigFile(groupName).delete();
    }

    public File getGroupConfigFile(String groupName) {
        return new File(FileUtils.GROUPS_FOLDER, groupName + ".json");
    }

    public boolean isGroupStatic(String groupName) {
        return Boolean.parseBoolean(get(groupName, "static"));
    }

    public boolean isProxy(String groupName) {
        Storage storage = new Storage(new File(FileUtils.GROUPS_FOLDER, groupName + ".json"));
        return Boolean.parseBoolean(storage.get("groupType"));
    }
}
