package com.jcatalog.ocisetest.schemas;

import java.util.ArrayList;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author Alexander Shulga
 */
public class ItemStorage {
    private List items = new ArrayList();
    private SchemaHolder schemaHolder = null;

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }

    public SchemaHolder getSchemaHolder() {
        return schemaHolder;
    }

    public void setSchemaHolder(SchemaHolder schemaHolder) {
        this.schemaHolder = schemaHolder;
    }
}
