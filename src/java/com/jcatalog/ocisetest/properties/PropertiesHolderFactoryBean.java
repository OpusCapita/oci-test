package com.jcatalog.ocisetest.properties;

import org.springframework.beans.factory.FactoryBean;

import java.io.File;

import java.util.HashMap;
import java.util.Map;


/**
 * Factory for dinamic creation of the map of PropertyHolders
 * depending on the existing configuration files 
 *
 * @author Alexander Shulga
 */
public class PropertiesHolderFactoryBean implements FactoryBean {
    private File baseDir;

    public void setBaseDir(File baseDir) {
        this.baseDir = baseDir;
    }

    public Object getObject() throws Exception {
        File[] files = new File(baseDir, "default").listFiles();
        Map propertiesHolders = new HashMap();

        for (int i = 0; i < files.length; i++) {
            PropertiesHolder propertiesHolder = new PropertiesHolderImpl();
            propertiesHolder.setDefaultPropertiesFilePath(files[i]);
            propertiesHolder.setUserPropertiesFilePath(new File(baseDir,
                    "user//" + files[i].getName()));
            propertiesHolders.put(files[i].getName().substring(0,
                    files[i].getName().indexOf(".")), propertiesHolder);
        }
        return propertiesHolders;
    }

    public Class getObjectType() {
        return Map.class;
    }

    public boolean isSingleton() {
        return false;
    }
}
