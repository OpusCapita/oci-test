package com.jcatalog.ocisetest.schemas;

import com.jcatalog.ocisetest.properties.Properties;

import java.io.File;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * DOCUMENT ME!
 *
 * @author Alexander Shulga
 */
public class SchemaHolderFactory {
    private File baseDir;
    Map schemaHolders;

    public void setBaseDir(File baseDir) {
        this.baseDir = baseDir;
    }

    public File getBaseDir() {
        return baseDir;
    }

    public Map getSchemaHolders() {
        return schemaHolders;
    }

    public void init() throws Exception {
        File[] files = new File(baseDir, "default").listFiles();
        File[] userFiles = new File(baseDir, "user").listFiles();
        schemaHolders = new LinkedHashMap();

        for (int i = 0; i < files.length; i++) {
            SchemaHolder schemaHolder = new SchemaHolderImpl(files[i]);
            schemaHolders.put(files[i].getName().substring(0,
                    files[i].getName().indexOf(".")), schemaHolder);
        }
        files = null;

        for (int i = 0; i < userFiles.length; i++) {
            SchemaHolder schemaHolder = new SchemaHolderImpl(userFiles[i]);
            schemaHolders.put(userFiles[i].getName().substring(0,
                    userFiles[i].getName().indexOf(".")), schemaHolder);
        }
        userFiles = null;
    }

    public String duplicateSchema(String schemaName) throws Exception {
        SchemaHolder schemaHolder = (SchemaHolder) schemaHolders.get(schemaName);
        SchemaHolder newSchemaHolder = new SchemaHolderImpl();
        Properties propertiesCopy = new Properties();
        propertiesCopy.putAll(schemaHolder.getSchema());
        newSchemaHolder.setSchema(propertiesCopy);
        String newSchemaName = "userSchema" + new Date().getTime();
        newSchemaHolder.setSchemaFile(new File(baseDir,
                "user//" + newSchemaName + ".properties"));
        newSchemaHolder.saveSchema();
        schemaHolders.put(newSchemaName, newSchemaHolder);
        return newSchemaName;
    }

    public void deleteSchema(String schemaName) {
        ((SchemaHolder) schemaHolders.get(schemaName)).deleteSchema();
        schemaHolders.remove(schemaName);
    }

    public void saveSchema(String schemaName) throws Exception {
        SchemaHolder schemaHolder = (SchemaHolder) schemaHolders.get(schemaName);
        schemaHolder.saveSchema();
    }
}
