package eu.zonecraft.pterocloud.utils;

import eu.zonecraft.pterocloud.PteroCloud;
import eu.zonecraft.pterocloud.utils.message.MessageType;
import eu.zonecraft.pterocloud.utils.message.MessageUtils;
import lombok.Setter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Utils {

    @Setter
    public boolean animationFinished = false;
    @Setter
    public boolean animationSuccess;
    @Setter
    public boolean isScreenRunning = false;

    public boolean isNumber(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void clearConsole() {
        if (getOS().equals("windows")) {
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                PteroCloud.getLogger().log("(Clearing console...)");
            } catch (Exception e) {
                MessageUtils.sendMessage(MessageType.ERROR, "An error occurred while clearing the console.");
                MessageUtils.sendMessage(MessageType.ERROR, "Error: " + e.getMessage());
            }
        } else {
            try {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
                PteroCloud.getLogger().log("(Clearing console...)");
            } catch (Exception e) {
                MessageUtils.sendMessage(MessageType.ERROR, "An error occurred while clearing the console.");
                MessageUtils.sendMessage(MessageType.ERROR, "Error: " + e.getMessage());
            }
        }
    }

    public void finishAnimation(boolean success) {
        animationFinished = true;
        this.animationSuccess = success;
        try {
            MessageUtils.animationThread.join();
        } catch (InterruptedException e) {
            MessageUtils.sendMessage(MessageType.ERROR, "An error occurred while joining the Thread.");
            MessageUtils.sendMessage(MessageType.ERROR, "Error: " + e.getMessage());
        }
        MessageUtils.sendMessage(" ");
        animationFinished = false;
    }

    public void zipFile(Path source, String zipFileName) throws IOException {

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFileName)); FileInputStream fis = new FileInputStream(source.toFile())) {

            ZipEntry zipEntry = new ZipEntry(source.getFileName().toString());
            zos.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
            zos.closeEntry();
        }

    }

    public String getOS() {
        return System.getProperty("os.name").toLowerCase();
    }
}
