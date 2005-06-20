package com.jcatalog.ocisetest.schemas;

import com.jcatalog.ocisetest.properties.Properties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * DOCUMENT ME!
 *
 * @author Alexander Shulga
 */
public class ItemStorageManager {
    public ItemStorage createStorage(SchemaHolder schemaHolder) {
        ItemStorage itemStorage = new ItemStorage();
        itemStorage.setSchemaHolder(schemaHolder);
        itemStorage.getItems().add(schemaHolder.getSchema());
        return itemStorage;
    }

    public void addItem(ItemStorage itemStorage) {
        itemStorage.getItems().add(itemStorage.getSchemaHolder().getSchema());
    }

    public void copyItem(ItemStorage itemStorage, int n) {
        Properties schemaProperties = new Properties();
        schemaProperties.putAll((Properties) itemStorage.getItems().get(n));
        itemStorage.getItems().add(schemaProperties);
    }

    public void deleteItem(ItemStorage itemStorage, int n) {
        itemStorage.getItems().remove(n);
    }

    public List getIndexedItems(ItemStorage itemStorage) {
        List items = itemStorage.getItems();
        List indexedItems = new ArrayList();
        int counter = 1;
        for (Iterator iterator = items.iterator(); iterator.hasNext();) {
            Properties properties = (Properties) iterator.next();
            Properties indexedProperties = new Properties();
            Set keys = properties.keySet();
            for (Iterator iter = keys.iterator(); iter.hasNext();) {
                String key = (String) iter.next();
                Object value = properties.getProperty(key);
                String transformedKey = key.replaceFirst("n", Integer.toString(counter));
                indexedProperties.put(transformedKey, value);
            }
            counter++;
            indexedItems.add(indexedProperties);
        }
        return indexedItems;
    }
}
