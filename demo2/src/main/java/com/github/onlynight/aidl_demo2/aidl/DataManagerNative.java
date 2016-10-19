package com.github.onlynight.aidl_demo2.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.github.onlynight.aidl_demo2.Data2;

import java.util.List;

/**
 * Created by lion on 2016/10/12.
 */
public abstract class DataManagerNative extends Binder implements IDataManager2 {

    // Binder描述符，唯一标识符
    private static final String DESCRIPTOR = "com.github.onlynight.aidl_demo2.aidl.IDataManager2";

    // 每个方法对应的ID
    private static final int TRANSACTION_getDataCount = IBinder.FIRST_CALL_TRANSACTION;
    private static final int TRANSACTION_getData = IBinder.FIRST_CALL_TRANSACTION + 1;

    public DataManagerNative() {
        attachInterface(this, DESCRIPTOR);
    }

    /**
     * 将Binder转化为IInterface接口
     *
     * @param binder
     * @return
     */
    public static IDataManager2 asInterface(IBinder binder) {
        if (binder == null) {
            return null;
        }
        //同一进程内直接返回
        IInterface iin = binder.queryLocalInterface(DESCRIPTOR);
        if ((iin != null) && (iin instanceof IDataManager2)) {
            return (IDataManager2) iin;
        }

        //不在同一进程使用代理获取远程服务
        return new Proxy(binder);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    /**
     * 我们查看Binder的源码就可以看出实际上transact方法真正的执行体
     * 是这个onTransact方法。
     *
     * @param code  服务器回掉的方法ID，每一个方法都有一个唯一id，
     *              这样方法回调时可通过id判断回调的方法。
     * @param data  输入的参数，传递给服务端的参数
     * @param reply 输出的参数，服务器返回的数据
     * @param flags 默认传入0
     * @return
     * @throws RemoteException 远端服务器无响应抛出该错误。
     */
    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        switch (code) {
            case TRANSACTION_getDataCount: {
                data.enforceInterface(DESCRIPTOR);
                int _result = this.getDataCount();
                reply.writeNoException();
                reply.writeInt(_result);
                return true;
            }
            case TRANSACTION_getData: {
                data.enforceInterface(DESCRIPTOR);
                List<Data2> _result = this.getData();
                reply.writeNoException();
                reply.writeTypedList(_result);
                return true;
            }
        }
        return super.onTransact(code, data, reply, flags);
    }

    /**
     * 代理类，调用transact方法。
     */
    private static class Proxy implements IDataManager2 {

        private IBinder remote;

        Proxy(IBinder remote) {
            this.remote = remote;
        }

        public String getInterfaceDescriptor() {
            return DESCRIPTOR;
        }

        @Override
        public int getDataCount() throws RemoteException {
            // 输入参数
            Parcel _data = Parcel.obtain();

            //输出参数
            Parcel _reply = Parcel.obtain();
            int _result;
            try {
                _data.writeInterfaceToken(DESCRIPTOR);
                remote.transact(TRANSACTION_getDataCount, _data, _reply, 0);
                _reply.readException();
                _result = _reply.readInt();
            } finally {
                _reply.recycle();
                _data.recycle();
            }
            return _result;
        }

        @Override
        public List<Data2> getData() throws RemoteException {
            Parcel _data = Parcel.obtain();
            Parcel _reply = Parcel.obtain();
            List<Data2> _result;
            try {
                _data.writeInterfaceToken(DESCRIPTOR);
                remote.transact(TRANSACTION_getData, _data, _reply, 0);
                _reply.readException();
                _result = _reply.createTypedArrayList(Data2.CREATOR);
            } finally {
                _reply.recycle();
                _data.recycle();
            }
            return _result;
        }

        @Override
        public IBinder asBinder() {
            return remote;
        }
    }
}
