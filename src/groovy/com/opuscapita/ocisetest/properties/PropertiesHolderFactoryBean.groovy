package com.opuscapita.ocisetest.properties

import grails.util.Holders
import org.springframework.beans.factory.FactoryBean

/**
 * Factory for dynamic creation of the map of PropertyHolders
 * depending on the existing configuration file
 */
class PropertiesHolderFactoryBean implements FactoryBean {
    private File baseDir = Holders.grailsApplication.mainContext.getResource('WEB-INF/conf/opc').getFile()

    @Override
    Object getObject() throws Exception {
        File[] files = new File(baseDir, "default").listFiles()
        Map propertiesHolders = new HashMap()

        for (int i = 0; i < files.length; i++) {
            PropertiesHolder propertiesHolder = new PropertiesHolderImpl()
            propertiesHolder.setDefaultPropertiesFilePath(files[i])
            propertiesHolder.setUserPropertiesFilePath(new File(baseDir,
                    "user//" + files[i].getName()))
            propertiesHolders.put(files[i].getName().substring(0,
                    files[i].getName().indexOf(".")), propertiesHolder)
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
