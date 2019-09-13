package com.jcatalog.ocisetest.schemas

import com.jcatalog.ocisetest.properties.Properties

class SchemaHolderFactory {
    private File baseDir
    private Map schemaHolders

    void setBaseDir(File baseDir) {
        this.baseDir = baseDir
    }

    File getBaseDir() {
        return baseDir
    }

    Map getSchemaHolders() {
        return schemaHolders
    }

    void init() throws Exception {
        def files = new File(baseDir, "default").listFiles()
        def userFiles = new File(baseDir, "user").listFiles()
        schemaHolders = [:]

        for (int i = 0; i < files.length; i++) {
            def schemaHolder = new SchemaHolderImpl(files[i])
            def fileName = files[i].getName()
            schemaHolders[fileName[0, fileName.indexOf('.')]] = schemaHolder
        }

        for (int i = 0; i < userFiles.length; i++) {
            def schemaHolder = new SchemaHolderImpl(files[i])
            def fileName = files[i].getName()
            schemaHolders[fileName[0, fileName.indexOf('.')]] = schemaHolder
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
