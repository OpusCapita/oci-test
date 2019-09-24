package com.jcatalog.ocisetest.schemas

import com.jcatalog.ocisetest.properties.Properties
import grails.util.Holders

class SchemaHolderFactory {
    private File baseDir = Holders.grailsApplication.mainContext.getResource('WEB-INF/conf/punchout').getFile()
    private Map schemaHolders

    void init() throws Exception {
        File[] files = new File(baseDir, "default").listFiles()
        File[] userFiles = new File(baseDir, "user").listFiles()
        schemaHolders = [:]

        for (int i = 0; i < files.length; i++) {
            SchemaHolder schemaHolder = new SchemaHolderImpl(files[i])
            schemaHolders[files[i].getName().substring(0,
                    files[i].getName().indexOf("."))] = schemaHolder
        }

        for (int i = 0; i < userFiles.length; i++) {
            SchemaHolder schemaHolder = new SchemaHolderImpl(userFiles[i])
            schemaHolders[userFiles[i].getName().substring(0,
                    userFiles[i].getName().indexOf("."))] = schemaHolder
        }
    }

    String duplicateSchema(String schemaName) throws Exception {
        def schemaHolder = schemaHolders.get(schemaName) as SchemaHolder
        def newSchemaHolder = new SchemaHolderImpl()
        def propertiesCopy = new Properties()
        propertiesCopy.putAll(schemaHolder.getSchema())
        newSchemaHolder.setSchema(propertiesCopy)
        String newSchemaName = "userSchema" + new Date().getTime()
        newSchemaHolder.setSchemaFile(new File(baseDir,
                "user//" + newSchemaName + ".properties"))
        newSchemaHolder.saveSchema()
        schemaHolders[newSchemaName] = newSchemaHolder
        return newSchemaName
    }

    void deleteSchema(String schemaName) {
        (schemaHolders.get(schemaName) as SchemaHolder).deleteSchema()
        schemaHolders.remove(schemaName)
    }

    void saveSchema(String schemaName) throws Exception {
        SchemaHolder schemaHolder = schemaHolders.get(schemaName) as SchemaHolder
        schemaHolder.saveSchema()
    }
}
