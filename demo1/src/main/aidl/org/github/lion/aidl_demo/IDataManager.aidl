// IDataManager.aidl
package org.github.lion.aidl_demo;

import org.github.lion.aidl_demo.Data;

// Declare any non-default types here with import statements

interface IDataManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,double aDouble, String aString);

    int getDataTypeCount();

    List<Data> getData();

    String getUrlContent(String url);
}
