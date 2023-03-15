package eu.zonecraft.pterocloud.module;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;
import java.util.zip.ZipException;

public class ModuleLoader {
    private static ModuleProperty loadModuleProperties(File file) throws ZipException, IOException {
        URL url = file.toURI().toURL();
        String jarURL = "jar:" + url +"!/module.properties";

        InputStream input;
        URL inputURL = new URL(jarURL);
        JarURLConnection conn = (JarURLConnection)inputURL.openConnection();
        input = conn.getInputStream();

        Properties property = new Properties();

        property.load(input);

        String main = property.getProperty("module.main");

        String name = property.getProperty("module.name");
        String description = property.getProperty("module.description");
        double version = Double.parseDouble(property.getProperty("module.version"));


        return new ModuleProperty(main, name, description, version);
    }

    public static Module loadModule(File file) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        if(!file.exists()) {
            return null;
        }

        ModuleProperty property = loadModuleProperties(file);

        URL url = file.toURI().toURL();
        String jarURL = "jar:" + url + "!/";
        URL urls[] = {new URL(jarURL)};
        URLClassLoader ucl = new URLClassLoader(urls);
        Module module = (Module) Class.forName(property.getMain(), true, ucl).getDeclaredConstructor().newInstance();
        module.setProperty(property);

        return module;
    }
}