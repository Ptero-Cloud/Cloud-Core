package eu.zonecraft.pterocloud.utils.message;

public enum MessageType {

    INFO(Color.BLUE + "INFO" + Color.RESET),
    WARNING(Color.YELLOW + "WARNING"),
    ERROR(Color.RED + "ERROR"),
    INPUT(null);

    private String prefix;

    MessageType(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
