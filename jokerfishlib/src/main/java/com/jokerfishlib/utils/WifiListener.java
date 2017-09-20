package com.jokerfishlib.utils;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public abstract class WifiListener {

	/**
	 * Wifi打开,调用此方法
	 */
	public abstract void wifiOpen();
	/**
	 * Wifi关闭调用此方法
	 */
	public abstract void wifiClose();
	/**
	 * Wifi连接到网络,调用此方法
	 * @param networkInfo 网络信息连接
	 */
	public abstract void wifiConnected(NetworkInfo networkInfo);
	/**
	 * Wifi没有连接到网络,调用此方法
	 * @param networkInfo 网络信息连接
	 */
	public abstract void wifiNotConnect(NetworkInfo networkInfo);
	/** 
	 * 网络连接,调用此方法
	 * @param manager	连接管理	
	 * @param networkInfo	当前连接到网络的网络信息
	 */
	public void connected(ConnectivityManager manager, NetworkInfo networkInfo){}
	/**
	 * 断开网络,调用此方法
	 */
	public void notConnected(){}
	
}
