package com.jcatalog.ocisetest.schemas

class ItemStorage {
    List items = []
    private SchemaHolder schemaHolder = null

    SchemaHolder getSchemaHolder() {
        return schemaHolder
    }

    void setSchemaHolder(SchemaHolder schemaHolder) {
        this.schemaHolder = schemaHolder
    }
}
