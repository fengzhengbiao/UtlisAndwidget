package com.jokerfishlib.utils;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Wifi管理工具类
 * 
 * @author Administrator
 *
 */
public class WifiUtils {

	private Context context;
	private WifiReceiver wifiReceiver;
	// 定义WifiManager对象
	private WifiManager mWifiManager;
	// 定义WifiInfo对象
	private WifiInfo mWifiInfo;

	public WifiUtils(Context context) {
		this.context = context;
		// 取得WifiManager对象
		mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		// 取得WifiInfo对象
		mWifiInfo = mWifiManager.getConnectionInfo();
	}

	/**
	 * 注册Wifi监听广播
	 * 
	 * @param listener
	 */
	public void register(WifiListener listener) {
		if (wifiReceiver == null) {
			wifiReceiver = new WifiReceiver(listener);
			IntentFilter intentFilter = new IntentFilter();
			intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
			intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
			intentFilter.addAction("android.net.wifi.STATE_CHANGE");
			context.registerReceiver(wifiReceiver, intentFilter);
		}
	}

	/**
	 * 取消注册wifi监听广播
	 */
	public void Unregister() {
		if (wifiReceiver != null) {
			context.unregisterReceiver(wifiReceiver);
		}
	}

	/**
	 * 打开WIFI
	 */
	public void OpenWifi() {
		if (!mWifiManager.isWifiEnabled()) {
			mWifiManager.setWifiEnabled(true);
		}
	}

	/**
	 * 关闭WIFI
	 */
	public void CloseWifi() {
		if (mWifiManager.isWifiEnabled()) {
			mWifiManager.setWifiEnabled(false);
		}
	}

	/**
	 * 得到MAC地址
	 * 
	 * @return
	 */
	public String GetMacAddress() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.getMacAddress();
	}

	/**
	 * 得到接入点的BSSID
	 * 
	 * @return
	 */
	public String GetBSSID() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.getBSSID();
	}

	/**
	 * 得到IP地址
	 * 
	 * @return
	 */
	public int GetIPAddress() {
		return (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();
	}

	/**
	 * 得到连接的ID
	 * 
	 * @return
	 */
	public int GetNetworkId() {
		return (mWifiInfo == null) ? 0 : mWifiInfo.getNetworkId();
	}

	/**
	 * 得到WifiInfo的所有信息包
	 * 
	 * @return
	 */
	public String GetWifiInfo() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.toString();
	}

}
