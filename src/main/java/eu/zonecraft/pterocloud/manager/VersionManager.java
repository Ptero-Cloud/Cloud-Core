package eu.zonecraft.pterocloud.manager;

import eu.zonecraft.pterocloud.utils.message.MessageType;
import eu.zonecraft.pterocloud.utils.message.MessageUtils;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import java.io.IOException;
import java.io.InputStream;

public class VersionManager {

    public String getVersion() {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = null;
        try (InputStream in = getClass().getResourceAsStream("/pom.xml")) {
            model = reader.read(in);
        } catch (IOException | XmlPullParserException e) {
            MessageUtils.sendMessage(MessageType.ERROR, "An error occurred while reading the version.");
            MessageUtils.sendMessage(MessageType.ERROR, "Error: " + e.getMessage());
        }
        return model.getVersion();
    }

    public void checkForUpdates() {
        //
    }

}
