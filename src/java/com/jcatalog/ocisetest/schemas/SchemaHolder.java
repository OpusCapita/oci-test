package com.jcatalog.ocisetest.schemas;

import com.jcatalog.ocisetest.properties.Properties;

import java.io.File;


/**
 * DOCUMENT ME!
 *
 * @author Alexander Shulga
 */
public interface SchemaHolder {
    public Properties getSchema();

    public void setSchema(Properties schema);

    public void saveSchema() throws Exception;

    public void deleteSchema();

    public void addProperty(String name, String value);

    public void setSchemaFile(File path);
}
