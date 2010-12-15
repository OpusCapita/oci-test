package com.jcatalog.ocisetest.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


/**
 * Implementation of PropertiesHolder
 *
 * @author Alexander Shulga
 */
public class PropertiesHolderImpl implements PropertiesHolder {
    private File defaultPropertiesFilePath = null;
    private File userPropertiesFilePath = null;
    private Properties properties = null;

    public void loadProperties() throws Exception {
        properties = new Properties();
        if (userPropertiesFilePath.exists()) {
            properties.load(new FileInputStream(userPropertiesFilePath));
        } else {
            properties.load(new FileInputStream(defaultPropertiesFilePath));
        }
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public void addProperty(String name, String value) {
        properties.put(name, value);
    }

    public void saveProperties() throws Exception {
        if (!userPropertiesFilePath.exists()) {
            userPropertiesFilePath.getParentFile().mkdirs();
            userPropertiesFilePath.createNewFile();
        }
        properties.store(new FileOutputStream(userPropertiesFilePath),
            "user configuration");
    }

    public void setDefaultPropertiesFilePath(File path) {
        this.defaultPropertiesFilePath = path;
    }

    public void setUserPropertiesFilePath(File path) {
        this.userPropertiesFilePath = path;
    }
}
