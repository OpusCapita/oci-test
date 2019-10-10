package com.opuscapita.ocisetest.properties

import org.apache.commons.io.IOUtils

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

        def is

        // load default properties
        try {
            is = new FileInputStream(defaultPropertiesFilePath)
            properties.load(is)
        } finally {
            if (is) {
                IOUtils.closeQuietly(is)
            }
            is = null
        }

        if (userPropertiesFilePath.exists()) {
            // load user properties (override default)
            try {
                is = new FileInputStream(userPropertiesFilePath)
                properties.load(is)
            } finally {
                if (is) {
                    IOUtils.closeQuietly(is)
                }
                is = null
            }
        }
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
