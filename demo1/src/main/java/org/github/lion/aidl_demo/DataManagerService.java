package org.github.lion.aidl_demo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class DataManagerService extends Service {

    private static final List<Data> data = new ArrayList<>();

    static {
        Data data1 = new Data();
        data1.setId(1);
        data1.setContent("data1");
        data.add(data1);
        Data data2 = new Data();
        data2.setId(2);
        data2.setContent("data2");
        data.add(data2);
        Data data3 = new Data();
        data3.setId(3);
        data3.setContent("data3");
        data.add(data3);
        Data data4 = new Data();
        data4.setId(4);
        data4.setContent("data4");
        data.add(data4);
        Data data5 = new Data();
        data5.setId(5);
        data5.setContent("data5");
        data.add(data5);
    }

    private static final IDataManager.Stub mBinder = new IDataManager.Stub() {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
        }

        @Override
        public int getDataTypeCount() throws RemoteException {
            return data.size();
        }

        @Override
        public List<Data> getData() throws RemoteException {
            return data;
        }

        @Override
        public String getUrlContent(String url) throws RemoteException {
            try {
                URL u = new URL(url);
                URLConnection connection = u.openConnection();
                InputStream is = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line;
                String result = "";
                while ((line = reader.readLine()) != null) {
                    result += line;
                    System.out.println(line);
                }

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    };

    public DataManagerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
