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

    private static final String DESCRIPTOR = "com.github.onlynight.aidl_demo2.aidl.IDataManager2";

    private static final int TRANSACTION_getDataCount = IBinder.FIRST_CALL_TRANSACTION;
    private static final int TRANSACTION_getData = IBinder.FIRST_CALL_TRANSACTION + 1;

    public DataManagerNative() {
        attachInterface(this, DESCRIPTOR);
    }

    public static IDataManager2 asInterface(IBinder binder) {
        if (binder == null) {
            return null;
        }
        IInterface iin = binder.queryLocalInterface(DESCRIPTOR);
        if ((iin != null) && (iin instanceof IDataManager2)) {
            return (IDataManager2) iin;
        }

        return new Proxy(binder);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

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

    static class Proxy implements IDataManager2 {

        private IBinder remote;

        Proxy(IBinder remote) {
            this.remote = remote;
        }

        public String getInterfaceDescriptor() {
            return DESCRIPTOR;
        }

        @Override
        public int getDataCount() throws RemoteException {
            Parcel _data = Parcel.obtain();
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
