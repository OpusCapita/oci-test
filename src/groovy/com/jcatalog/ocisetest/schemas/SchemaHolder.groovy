package com.jcatalog.ocisetest.schemas

import com.jcatalog.ocisetest.properties.Properties

interface SchemaHolder {
    Properties getSchema()

    void setSchema(Properties schema)

    void saveSchema() throws Exception

    void deleteSchema()

    void addProperty(String name, String value)

    void setSchemaFile(File path)
}