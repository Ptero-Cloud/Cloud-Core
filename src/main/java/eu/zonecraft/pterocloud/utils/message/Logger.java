package eu.zonecraft.pterocloud.utils.message;

import eu.zonecraft.pterocloud.PteroCloud;
import eu.zonecraft.pterocloud.utils.FileUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    public FileWriter fileWriter;
    private String ansiColorRegex = "\u001B\\[[0-9;]*[mK]";

    public Logger() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
        String formattedDate = simpleDateFormat.format(new Date());

        if (FileUtils.LOG_FILE.exists()) {
            try {
                Files.move(Path.of(FileUtils.LOG_FILE.getPath()), Path.of(FileUtils.LOG_FOLDER.getPath() + "/" + formattedDate + ".log"));
                PteroCloud.getUtils().zipFile(Path.of(FileUtils.LOG_FOLDER.getPath() + "/" + formattedDate + ".log"), "logs/" + formattedDate + ".zip");
                Files.delete(Path.of(FileUtils.LOG_FOLDER.getPath() + "/" + formattedDate + ".log"));

                FileUtils.LOG_FILE.createNewFile();
                this.fileWriter = new FileWriter(FileUtils.LOG_FILE);
            } catch (IOException e) {
                new Throwable("An error occurred while creating the Log file.").printStackTrace();
            }
        } else {
            try {
                FileUtils.LOG_FILE.createNewFile();
                this.fileWriter = new FileWriter(FileUtils.LOG_FILE);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (!FileUtils.LOG_FOLDER.exists()) {
            FileUtils.LOG_FOLDER.mkdirs();
        }
    }

    public void log(MessageType type, String message) {
        try {
            fileWriter.write("[" + type.getPrefix().replaceAll(ansiColorRegex, "") + "] " + message + "\n");
            fileWriter.flush();
        } catch (IOException e) {
        }
    }

    public void log(String message) {
        try {
            fileWriter.write(message.replaceAll(ansiColorRegex, "") + "\n");
            fileWriter.flush();
        } catch (IOException e) {
        }
    }

}
