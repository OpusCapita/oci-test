package com.jcatalog.ocisetest.properties

import org.springframework.beans.factory.FactoryBean

/**
 * Factory for dynamic creation of the map of PropertyHolders
 * depending on the existing configuration file
 */
class PropertiesHolderFactoryBean implements FactoryBean {
    private File baseDir

    void setBaseDir(File baseDir) {
        this.baseDir = baseDir
    }

    @Override
    Object getObject() throws Exception {
        File[] files = new File(baseDir, 'default').listFiles()
        def propertiesHolders = [:]

        for (int i = 0; i < files.length; i++) {
            def propsHolder = new PropertiesHolderImpl()
            propsHolder.setDefaultPropertiesFilePath(files[i])
            propsHolder.setUserPropertiesFilePath(new File(baseDir,
                    'user//' + files[i].getName()))
            def fileName = files[i].getName()
            propertiesHolders[fileName[0, fileName.indexOf('.')]] = propsHolder
        }
        return propertiesHolders
    }

    @Override
    Class<?> getObjectType() {
        return Map.class
    }

    @Override
    boolean isSingleton() {
        return false
    }
}
