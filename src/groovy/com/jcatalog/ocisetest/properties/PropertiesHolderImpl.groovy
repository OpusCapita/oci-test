package com.jcatalog.ocisetest.properties

/**
 * Implementation of PropertiesHolder
 */
class PropertiesHolderImpl implements PropertiesHolder {
    private File defaultPropertiesFilePath = null
    private File userPropertiesFilePath = null
    private Properties properties = null


    @Override
    void loadProperties() throws Exception {
        properties = new Properties()
        userPropertiesFilePath.exists() ?
                properties.load(new FileInputStream(userPropertiesFilePath)) :
                properties.load(new FileInputStream(defaultPropertiesFilePath))
    }

    @Override
    Properties getProperties() {
        return properties
    }

    @Override
    void setProperties(Properties properties) {
        this.properties = properties
    }

    @Override
    void addProperty(String name, String value) {
        properties[name] = value
    }

    @Override
    void saveProperties() throws Exception {
        if (!userPropertiesFilePath.exists()) {
            userPropertiesFilePath.getParentFile().mkdirs()
            userPropertiesFilePath.createNewFile()
        }
        properties.store(new FileOutputStream(userPropertiesFilePath),
                'user configuration')
    }

    @Override
    void setDefaultPropertiesFilePath(File path) {
        this.defaultPropertiesFilePath = path
    }

    @Override
    void setUserPropertiesFilePath(File path) {
        this.userPropertiesFilePath = path
    }
}
