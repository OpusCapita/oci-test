package com.jcatalog.ocisetest.properties;

import java.io.File;


/**
 * Interface containing methods for Properties manipulation 
 *
 * @author Alexander Shulga
 */
public interface PropertiesHolder {
    public void loadProperties() throws Exception;

    public Properties getProperties();

    public void setProperties(Properties properties);

    public void addProperty(String name, String value);

    public void saveProperties() throws Exception;

    public void setDefaultPropertiesFilePath(File path);

    public void setUserPropertiesFilePath(File path);

    public void setFunction(String function);

    public String getFunction();
}
