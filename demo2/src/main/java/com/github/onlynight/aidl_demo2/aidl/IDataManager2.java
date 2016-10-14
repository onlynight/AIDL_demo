package com.github.onlynight.aidl_demo2.aidl;

import android.os.IInterface;
import android.os.RemoteException;

import com.github.onlynight.aidl_demo2.Data2;

import java.util.List;

/**
 * Created by lion on 2016/10/12.
 */
public interface IDataManager2 extends IInterface {

    int getDataCount() throws RemoteException;

    List<Data2> getData() throws RemoteException;
}
