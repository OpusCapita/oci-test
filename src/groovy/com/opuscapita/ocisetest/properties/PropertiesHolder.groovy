package com.opuscapita.ocisetest.properties

/**
 * Interface containing methods for Properties manipulation
 */
interface PropertiesHolder {
    void loadProperties() throws Exception

    Properties getProperties()

    void setProperties(Properties properties)

    void addProperty(String name, String value)

    void saveProperties() throws Exception

    void setDefaultPropertiesFilePath(File path)

    void setUserPropertiesFilePath(File path)
}
