package com.jcatalog.ocisetest.schemas;

import com.jcatalog.ocisetest.properties.Properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


/**
 * DOCUMENT ME!
 *
 * @author Alexander Shulga
 */
public class SchemaHolderImpl implements SchemaHolder {
    private File schemaFile = null;
    private Properties schema = null;

    public SchemaHolderImpl(File schemaFile) throws Exception {
        this.schemaFile = schemaFile;
        schema = new Properties();
        schema.load(new FileInputStream(schemaFile));
    }

    public SchemaHolderImpl() {}

    public Properties getSchema() {
        return schema;
    }

    public void setSchema(Properties schema) {
        this.schema = schema;
    }

    public void addProperty(String name, String value) {
        schema.put(name, value);
    }

    public void saveSchema() throws Exception {
        if (!schemaFile.exists()) {
            schemaFile.createNewFile();
        }
        schema.store(new FileOutputStream(schemaFile), "punchout schema");
    }

    public void deleteSchema() {
        schemaFile.delete();
    }

    public void setSchemaFile(File schemaFilePath) {
        this.schemaFile = schemaFilePath;
    }
}
