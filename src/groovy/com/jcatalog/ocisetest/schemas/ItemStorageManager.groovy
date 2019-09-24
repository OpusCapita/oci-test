package com.jcatalog.ocisetest.schemas

import com.jcatalog.ocisetest.properties.Properties

class ItemStorageManager {
    ItemStorage createStorage(SchemaHolder schemaHolder) {
        def itemStorage = new ItemStorage()
        itemStorage.setSchemaHolder(schemaHolder)
        itemStorage.items.add(schemaHolder.getSchema())
        return itemStorage
    }

    void addItem(ItemStorage itemStorage) {
        itemStorage.items.add(itemStorage.getSchemaHolder().getSchema())
    }

    void copyItem(ItemStorage itemStorage, int n) {
        def schemaProperties = new Properties()
        schemaProperties.putAll(itemStorage.getItems().get(n) as Properties)
        itemStorage.items.add(schemaProperties)
    }

    void deleteItem(ItemStorage itemStorage, int n) {
        itemStorage.items.remove(n)
    }

    List getIndexedItems(ItemStorage itemStorage) {
        List items = itemStorage.items
        List indexedItems = []
        int counter = 1
        items.each {
            def properties = it as Properties
            def indexedProperties = new Properties()
            Set keys = properties.keySet()
            keys.each {
                String key = it as String
                def value = properties.getProperty(key)
                String transformedKey = key.replaceFirst("n", counter as String)
                indexedProperties[transformedKey] = value
            }
            counter++
            indexedItems.add(indexedProperties)
        }
        return indexedItems
    }
}
