package org.github.lion.aidl_demo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textData;

    /**
     * data manager service 的远程引用
     */
    private IDataManager dataManagerService = null;

    /**
     * 创建Service Connection用于监听service链接与断开链接
     */
    private ServiceConnection dataServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //这里使用asInterface将IBinder转换为IDataManager
            dataManagerService = IDataManager.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            dataManagerService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textData = (TextView) findViewById(R.id.textData);

        bindService(new Intent(this, DataManagerService.class), dataServiceConnection,
                Context.BIND_AUTO_CREATE);
    }

    public void callService(View view) {
        try {
            System.out.println(dataManagerService.getDataTypeCount());
            
            StringBuilder sb = new StringBuilder();
            for (Data data : dataManagerService.getData()) {
                System.out.println(data.toString());
                sb.append(data.toString()).append("\n");
            }
            textData.setText(sb.toString());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(dataManagerService.getUrlContent("http://www.baidu.com"));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
