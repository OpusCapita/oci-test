package com.jcatalog.ocisetest.schemas

import com.jcatalog.ocisetest.properties.Properties

class SchemaHolderImpl implements SchemaHolder {
    private File schemaFile = null
    private Properties schema = null

    SchemaHolderImpl(File schemaFile) throws Exception {
        this.schemaFile = schemaFile
        schema = new Properties()
        schema.load(new FileInputStream(schemaFile))
    }

    @Override
    Properties getSchema() {
        return schema
    }

    @Override
    void setSchema(Properties schema) {
        this.schema = schema
    }

    @Override
    void saveSchema() throws Exception {
        if (!schemaFile.exists()) {
            schemaFile.createNewFile()
        }
        schema.store(new FileOutputStream(schemaFile), "punchout schema")
    }

    @Override
    void deleteSchema() {
        schemaFile.delete()
    }

    @Override
    void addProperty(String name, String value) {
        schema[name] = value
    }

    @Override
    void setSchemaFile(File path) {
        this.schemaFile = path
    }
}
