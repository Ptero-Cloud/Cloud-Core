package eu.zonecraft.pterocloud.utils.storage;


import com.eclipsesource.json.*;
import eu.zonecraft.pterocloud.utils.message.MessageType;
import eu.zonecraft.pterocloud.utils.message.MessageUtils;

import java.io.*;

public class Storage {

    private File file;
    private JsonObject jsonObject;

    public Storage(File file) {
        this.file = file;
        this.jsonObject = new JsonObject();
    }

    public Storage setArray(String path, String key, String value) {
        try {
            Reader reader = new FileReader(file);

            if (reader.ready()) {
                JsonValue jsonValue = Json.parse(reader);
                jsonObject = jsonValue.asObject();
            } else {
                jsonObject = new JsonObject();
            }

            JsonObject currentObject = jsonObject;
            String[] segments = path.split("\\.");
            for (int i = 0; i < segments.length - 1; i++) {
                String segment = segments[i];
                if (currentObject.get(segment) == null || currentObject.get(segment).isNull()) {
                    currentObject.add(segment, new JsonObject());
                }
                currentObject = currentObject.get(segment).asObject();
            }

            JsonObject innerObject;
            if (currentObject.get(segments[segments.length - 1]) == null || currentObject.get(segments[segments.length - 1]).isNull()) {
                innerObject = new JsonObject();
                currentObject.add(segments[segments.length - 1], innerObject);
            } else {
                innerObject = currentObject.get(segments[segments.length - 1]).asObject();
            }

            innerObject.add(key, value);

            Writer writer = new FileWriter(file);
            writer.write(jsonObject.toString(WriterConfig.PRETTY_PRINT));
            writer.flush();
            writer.close();

        } catch (IOException | ParseException e) {
            MessageUtils.sendMessage(MessageType.ERROR, "An error occurred while parsing the JSON file.");
            MessageUtils.sendMessage(MessageType.ERROR, "Error: " + e.getMessage());
        }

        return this;
    }


    public String getArray(String path, String key) {
        try {
            Reader reader = new FileReader(file);

            JsonObject currentObject = Json.parse(reader).asObject();

            String[] segments = path.split("\\.");
            for (int i = 0; i < segments.length - 1; i++) {
                currentObject = currentObject.get(segments[i]).asObject();
            }

            JsonObject innerObject = currentObject.get(segments[segments.length - 1]).asObject();
            return innerObject.get(key).asString();
        } catch (IOException | NullPointerException | UnsupportedOperationException | ParseException e) {
            MessageUtils.sendMessage(MessageType.ERROR, "An error occurred while parsing the JSON file.");
            MessageUtils.sendMessage(MessageType.ERROR, "Error: " + e.getMessage());
            return null;
        }
    }


    public Storage set(String path, String value) {
        try {
            Writer writer = new FileWriter(file);

            jsonObject.set(path, value);

            String output = jsonObject.toString(WriterConfig.PRETTY_PRINT);
            writer.write(output);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            MessageUtils.sendMessage(MessageType.ERROR, "An error occurred while parsing the JSON file.");
            MessageUtils.sendMessage(MessageType.ERROR, "Error: " + e.getMessage());
        }

        return this;
    }

    public Storage delete(String path) {
        try {
            Writer writer = new FileWriter(file);

            jsonObject.remove(path);

            String output = jsonObject.toString(WriterConfig.PRETTY_PRINT);
            writer.write(output);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            MessageUtils.sendMessage(MessageType.ERROR, "An error occurred while parsing the JSON file.");
            MessageUtils.sendMessage(MessageType.ERROR, "Error: " + e.getMessage());
        }

        return this;
    }

    public String get(String path) {
        try {
            Reader reader = new FileReader(file);

            JsonObject object = Json.parse(reader).asObject();
            return object.get(path).asString();
        } catch (IOException e) {
            MessageUtils.sendMessage(MessageType.ERROR, "An error occurred while parsing the JSON file.");
            MessageUtils.sendMessage(MessageType.ERROR, "Error: " + e.getMessage());
            return null;
        }
    }

}