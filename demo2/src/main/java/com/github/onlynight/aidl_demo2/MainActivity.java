package com.github.onlynight.aidl_demo2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.github.onlynight.aidl_demo2.aidl.DataManagerNative;
import com.github.onlynight.aidl_demo2.aidl.IDataManager2;

public class MainActivity extends AppCompatActivity {

    private TextView textData;
    private IDataManager2 dataManagerService;
    private ServiceConnection dataServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            dataManagerService = DataManagerNative.asInterface(service);
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(dataManagerService.getDataCount());

                    StringBuilder sb = new StringBuilder();
                    for (Data2 data : dataManagerService.getData()) {
                        System.out.println(data.toString());
                        sb.append(data.toString()).append("\n");
                    }

                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = sb.toString();
                    handler.sendMessage(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                String content = (String) msg.obj;
                textData.setText(content);
            }
            return false;
        }
    });
}
