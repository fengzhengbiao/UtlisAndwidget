package com.jokerfishlib.utils;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.jokerfishlib.bean.ContactInfo;
import com.jokerfishlib.bean.MessageInfo;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by JokerFish on 2017/7/13.
 */

public class PhoneUtils {
    private static final String TAG = PhoneUtils.class.getSimpleName();
    /**
     * 得到手机通讯录联系人信息
     **/
    public static List<ContactInfo> getPhoneContacts(Context sContext) {
        List<ContactInfo> contacts = new ArrayList<>();
        ContentResolver resolver = sContext.getContentResolver();
        // 获取手机联系人
        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        if (phoneCursor == null) {
            return contacts;
        }
        while (phoneCursor.moveToNext()) {
            String string = phoneCursor.getString(0);
            Log.i(TAG, "getPhoneContacts: " + string);
            String name = phoneCursor.getString(phoneCursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            //读取通讯录的号码
            String number = phoneCursor.getString(phoneCursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String timesContacted = phoneCursor.getString(phoneCursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.TIMES_CONTACTED));
            Log.i(TAG, "姓名" + name + "---电话：" + number + "--联系次数" + timesContacted);
            contacts.add(new ContactInfo(name, number, Integer.parseInt(timesContacted)));
        }
        phoneCursor.close();
        contacts.add(getLocal(sContext));
        return contacts;
    }

    /**
     * 获取本机手机号码
     *
     * @param context
     * @return
     */
    public static ContactInfo getLocal(Context context) {
        ContactInfo info = null;
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String tel = tm.getLine1Number();
            info = new ContactInfo("local", TextUtils.isEmpty(tel) ? "0000" : tel, 0);
        } catch (Exception e) {
            e.printStackTrace();
            info = new ContactInfo("local", "00000000", 0);
        }
        return info;
    }

    /**
     * 获取用户最近1000条通话记录
     *
     * @param context
     * @return
     */
    public static List<com.jokerfishlib.bean.CallLog> getCallHistoryList(Context context) {
        ContentResolver cr = context.getContentResolver();
        Cursor cs;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            return new ArrayList<>();
        }
        cs = cr.query(CallLog.Calls.CONTENT_URI, //系统方式获取通讯录存储地址
                new String[]{
                        CallLog.Calls.CACHED_NAME,  //姓名
                        CallLog.Calls.NUMBER,    //号码
                        CallLog.Calls.TYPE,  //呼入/呼出(2)/未接
                        CallLog.Calls.DATE,  //拨打时间
                        CallLog.Calls.DURATION   //通话时长
                }, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
        List<com.jokerfishlib.bean.CallLog> callLogs = new ArrayList<>();
        int i = 0;
        if (cs != null && cs.getCount() > 0) {
            for (cs.moveToFirst(); !cs.isAfterLast() & i < 1000; cs.moveToNext()) {
                com.jokerfishlib.bean.CallLog callOne = new com.jokerfishlib.bean.CallLog();
                String callName = cs.getString(0);
                callOne.setName(callName);
                String callNumber = cs.getString(1);
                callOne.setNumber(callNumber);
                //通话类型
                int callType = Integer.parseInt(cs.getString(2));
                callOne.setType(callType);
                //拨打时间
                long callDate = Long.parseLong(cs.getString(3));
                callOne.setDate(callDate);
                //通话时长
                long callDuration = Long.parseLong(cs.getString(4));
                callOne.setDuration(callDuration);
                callLogs.add(callOne);
                Log.i(TAG, "name:" + callOne.getName() + "callNum:" + callNumber + "callType:" + callOne.getType());
                i++;
            }
        }

        return callLogs;
    }

    /**
     * 获取用户短信信息
     *
     * @param context
     * @return
     */
    public static List<MessageInfo> getSmsInfos(Context context) {
        List<MessageInfo> messageInfos = new ArrayList<>();
        final String SMS_URI_INBOX = "content://sms/";// 收信箱
        try {
            ContentResolver cr = context.getContentResolver();
            String[] projection = new String[]{"_id", "address", "person", "body", "date", "type", "read"};
            Uri uri = Uri.parse(SMS_URI_INBOX);
            Cursor cursor = cr.query(uri, projection, null, null, "date desc");
            while (cursor.moveToNext()) {
                MessageInfo messageInfo = new MessageInfo();
                // -----------------------信息----------------
                int nameColumn = cursor.getColumnIndex("person");// 联系人姓名列表序号
                int phoneNumberColumn = cursor.getColumnIndex("address");// 手机号
                int smsbodyColumn = cursor.getColumnIndex("body");// 短信内容
                int dateColumn = cursor.getColumnIndex("date");// 日期
                int typeColumn = cursor.getColumnIndex("type");// 收发类型 1表示接受 2表示发送
                int readColumn = cursor.getColumnIndex("read");// 是否已读 0未读 1已读
                String nameId = cursor.getString(nameColumn);
                String phoneNumber = cursor.getString(phoneNumberColumn);
                String smsbody = cursor.getString(smsbodyColumn);
                // --------------------------匹配联系人名字--------------------------
                Uri personUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, phoneNumber);
                Cursor localCursor = cr.query(personUri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup.PHOTO_ID, ContactsContract.PhoneLookup._ID}, null, null, null);
                if (localCursor.getCount() != 0) {
                    localCursor.moveToFirst();
                    String name = localCursor.getString(localCursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
                    messageInfo.setName(name);
                } else {
                    messageInfo.setName(phoneNumber);
                }
                localCursor.close();
                messageInfo.setPhoneNumb(phoneNumber);
                messageInfo.setSmsContent(smsbody);
                messageInfo.setDate(Long.parseLong(cursor.getString(dateColumn)));
                messageInfo.setType(Integer.parseInt(cursor.getString(typeColumn)));
                messageInfo.setRead(Integer.parseInt(cursor.getString(readColumn)));
                messageInfos.add(messageInfo);
                Log.i(TAG, "name:" + messageInfo.getName() + "msgBody:" + messageInfo.getSmsContent());
            }
            cursor.close();

        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return messageInfos;
    }


    public static Location getLocation(Context context) {
        String locationProvider = null;
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //获取所有可用的位置提供器
        List<String> providers = locationManager.getProviders(true);
        Location location = null;
        //获取Location
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        if (providers.contains(LocationManager.NETWORK_PROVIDER) && location == null) {
            //如果是Network
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        } else {
            Log.i(TAG, "没有可用的位置提供器");
        }
        return location;
    }

    /**
     * 获取设备的IMEI号
     *
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        try {
            return TelephonyMgr.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取安卓ID
     *
     * @param context
     * @return
     */
    public static String getAndroidId(Context context) {
        try {
            return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取设备的WIFI的MAC地址
     *
     * @param context
     * @return
     */
    public static String getWifiMAC(Context context) {
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        try {
            return wm.getConnectionInfo().getMacAddress();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取蓝牙的MAC地址
     *
     * @param context
     * @return
     */
    public static String getBlueToothMAC(Context context) {
        BluetoothAdapter m_BluetoothAdapter = null; // Local Bluetooth adapter
        m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        try {
            return m_BluetoothAdapter.getAddress();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取当前应用的版本名
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前应用的版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
