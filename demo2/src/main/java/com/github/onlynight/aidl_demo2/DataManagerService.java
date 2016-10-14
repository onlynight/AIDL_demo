package com.github.onlynight.aidl_demo2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.github.onlynight.aidl_demo2.aidl.DataManagerNative;

import java.util.ArrayList;
import java.util.List;

public class DataManagerService extends Service {

    private static List<Data2> data = new ArrayList<>();

    static {
        Data2 data1 = new Data2();
        data1.setId(1);
        data1.setContent("data1");
        data.add(data1);
        Data2 data2 = new Data2();
        data2.setId(2);
        data2.setContent("data2");
        data.add(data2);
        Data2 data3 = new Data2();
        data3.setId(3);
        data3.setContent("data3");
        data.add(data3);
        Data2 data4 = new Data2();
        data4.setId(4);
        data4.setContent("data4");
        data.add(data4);
        Data2 data5 = new Data2();
        data5.setId(5);
        data5.setContent("data5");
        data.add(data5);
    }

    private static DataManagerNative binder = new DataManagerNative() {

        @Override
        public int getDataCount() throws RemoteException {
            return data.size();
        }

        @Override
        public List<Data2> getData() throws RemoteException {
            return data;
        }
    };

    public DataManagerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
