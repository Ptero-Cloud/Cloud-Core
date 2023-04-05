package eu.zonecraft.pterocloud.utils.message;

import eu.zonecraft.pterocloud.PteroCloud;

import java.util.Date;

public class MessageUtils {

    public static Thread animationThread;

    public static void sendMessage(String message) {
        System.out.println(message + Color.RESET);
        PteroCloud.getLogger().log(message);
    }

    public static void sendMessage(MessageType type, String message) {
        if(type.equals(MessageType.INPUT)) {
            System.out.print(message + Color.RESET);
            return;
        }
        System.out.println(type.getPrefix() + ": " + message + Color.RESET);
        PteroCloud.getLogger().log(type, message);
    }

    public static void sendMessage(MessageType type, boolean time, String message) {
        if (time) {
            Date date = new Date();
            message = Color.GRAY + "[" + Color.WHITE + date.getHours() + Color.GRAY + ":" + Color.WHITE + date.getMinutes() + Color.GRAY + ":" + Color.WHITE + date.getSeconds() + Color.GRAY + "] " + message;
        }
        if(type.equals(MessageType.INPUT)) {
            System.out.print(message + Color.RESET);
            return;
        }
        System.out.println(type.getPrefix() + ": " + message + Color.RESET);
        PteroCloud.getLogger().log(type, message);
    }

    public static void sendMessage(boolean time, String message) {
        if (time) {
            Date date = new Date();
            message = Color.GRAY + "[" + Color.WHITE + date.getHours() + Color.GRAY + ":" + Color.WHITE + date.getMinutes() + Color.GRAY + ":" + Color.WHITE + date.getSeconds() + Color.GRAY + "] " + message;
        }
        System.out.println(message + Color.RESET);
        PteroCloud.getLogger().log(message);
    }


    public static void sendAnimatedMessage(String message) {
        animationThread = new Thread(() -> {
            String[] animationFrames = {"⠋", "⠙", "⠹", "⠸", "⠼", "⠴", "⠦", "⠧", "⠇", "⠏"};
            int currentFrame = 0;
            while (!PteroCloud.getUtils().animationFinished) {
                System.out.print("\r" + message + Color.RESET + " " + animationFrames[currentFrame]);
                currentFrame = (currentFrame + 1) % animationFrames.length;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {}
            }

            if(PteroCloud.getUtils().animationSuccess) {
                System.out.print("\r" + message + Color.GRAY + " [" + Color.GREEN + " ✓ " + Color.GRAY + "]" + Color.RESET);
                PteroCloud.getLogger().log(message + " [ ✓ ]");
            }else{
                System.out.print("\r" + Color.RED + MessageType.ERROR + ": " + Color.RESET + message + Color.GRAY + " [" + Color.RED + " ✗ " + Color.GRAY + "]" + Color.RESET);
                PteroCloud.getLogger().log(MessageType.ERROR, message + " [ ✗ ]");
            }
        });

        animationThread.start();


    }

    public static void sendAnimatedMessage(MessageType type, String message) {
        animationThread = new Thread(() -> {
            String[] animationFrames = {"⠋", "⠙", "⠹", "⠸", "⠼", "⠴", "⠦", "⠧", "⠇", "⠏"};
            int currentFrame = 0;
            while (!PteroCloud.getUtils().animationFinished) {
                System.out.print("\r" + type.getPrefix() + ": " + message + Color.RESET + " " + animationFrames[currentFrame]);
                currentFrame = (currentFrame + 1) % animationFrames.length;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {}
            }

            if(PteroCloud.getUtils().animationSuccess) {
                System.out.print("\r" + type.getPrefix() + ": " + message + Color.RESET + " [" + Color.GREEN + " ✓ " + Color.RESET + "]" + Color.RESET);
                PteroCloud.getLogger().log(type, message + " [ ✓ ]");
            }else{
                System.out.print("\r" + Color.RED + MessageType.ERROR + ": " + Color.RESET + message + Color.RESET + " [" + Color.RED + " ✗ " + Color.RESET + "]" + Color.RESET);
                PteroCloud.getLogger().log(type, message + " [ ✗ ]");
            }
        });

        animationThread.start();

    }

}
