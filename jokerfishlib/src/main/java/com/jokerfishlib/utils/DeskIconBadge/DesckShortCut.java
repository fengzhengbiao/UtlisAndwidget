package com.jokerfishlib.utils.DeskIconBadge;

import android.content.Context;
import android.util.Log;

import com.jokerfishlib.utils.PhoneUtils;

/**
 * Created by JokerFish on 2017/10/9.
 */

public class DesckShortCut {
    private static DesckShortCut sDesckShortCut = null;
    private static IShortCut iShortCut = null;

    public static DesckShortCut getInstance() {
        if (sDesckShortCut == null) {
            synchronized (DesckShortCut.class) {
                sDesckShortCut = new DesckShortCut();
                String brand = PhoneUtils.getDeviceBrand().toLowerCase();
                Log.i("Brand", "  : " + brand);
                switch (brand) {
                    case "xiaomi":
                        break;
                    case "meizu":
                        break;
                    case "huawei":
                    case "honor":
                        break;
                    case "vivo":
                        break;
                    case "oppo":
                        break;
                    case "samsung":
                    default:
                        break;
                }

            }
        }
        return sDesckShortCut;
    }

    public void showShortCut(Context context, int num) {
        iShortCut.showShortCut(context, num);
    }

}
