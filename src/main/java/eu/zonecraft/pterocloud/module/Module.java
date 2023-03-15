package eu.zonecraft.pterocloud.module;

public abstract class Module {
    protected ModuleProperty property;

    public abstract void onEnable();
    public abstract void onDisable();

    public ModuleProperty getProperty() {
        return property;
    }
    public void setProperty(ModuleProperty property) {
        this.property = property;
    }
}