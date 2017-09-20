package com.jokerfishlib.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.util.Log;
/**
 * 监听Wfif状态广播接收者
 * @author Administrator
 *
 */
public class WifiReceiver extends BroadcastReceiver {

	private WifiListener listener;
	private boolean isConn = true;

	public WifiReceiver(WifiListener listener) {
		this.listener = listener;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (listener != null) {
			wifiStateChanged(intent);
			wifiIsConnected(intent);
			isConnected(context, intent);
		}
	}

	/**
	 * 监听网络是否连接
	 * 
	 * @param context
	 * @param intent
	 */
	private void isConnected(Context context, Intent intent) {
		if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
			ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo gprs = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			Log.i("WifiReceiver", "网络状态改变:" + wifi.isConnected() + " 3g:" + gprs.isConnected());
			if (wifi.isConnected()) {
				listener.connected(manager, wifi);
			} else if (gprs.isConnected()) {
				listener.connected(manager, gprs);
			} else {
				listener.notConnected();
			}
		}
	}

	/**
	 * 监听Wifi网络否连接
	 * 
	 * @param intent
	 */
	private void wifiIsConnected(Intent intent) {
		if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
			Parcelable parcelableExtra = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
			if (null != parcelableExtra) {
				NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
				State state = networkInfo.getState();
				boolean isConnected = (state == State.CONNECTED);
				Log.e("WifiReceiver", "isConnected" + isConnected);
				if (isConnected) {
					Log.i("WifiReceiver", "isConnected" + isConnected);
					listener.wifiConnected(networkInfo);
					isConn = true;

				} else {
					Log.i("WifiReceiver", "isConnected" + isConnected);
					if (isConn) {
						listener.wifiNotConnect(networkInfo);
						isConn = false;
					}
				}
			}
		}
	}

	/**
	 * 监听Wifi启动状态
	 * 
	 * @param intent
	 */
	private void wifiStateChanged(Intent intent) {
		if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {// 这个监听wifi的打开与关闭，与wifi的连接无关
			int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
			switch (wifiState) {
			case WifiManager.WIFI_STATE_DISABLED:
				Log.i("WifiReceiver", "WIFI_STATE_DISABLED----" + wifiState);
				listener.wifiClose();
				break;
			case WifiManager.WIFI_STATE_ENABLED:
				Log.i("WifiReceiver", "WIFI_STATE_ENABLED----" + wifiState);
				listener.wifiOpen();
				break;
			}
		}
	}

}
