package com.example.scrollingbar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Administrator on 2018/1/4 0004.
 */

public class BitmapUtil {

    private static Bitmap ratio(String url, int pixelW, int pixelH){

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        BitmapFactory.decodeFile(url, options);

        int oringnalW = options.outWidth;
        int oringalH = options.outHeight;

        options.inSampleSize = getSampleSize(oringnalW, oringalH, pixelW, pixelH);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(url, options);
    }

    private static int getSampleSize(int oringnalW,
                                     int oringalH, int pixelW, int pixelH) {

        int sampleSize = 1;
        if (oringnalW > oringalH && oringnalW > pixelW){
            sampleSize = oringnalW/ pixelW;
        }else if(oringnalW < oringalH && oringalH > pixelH){
            sampleSize = oringalH / pixelH;
        }

        if (sampleSize <= 0){
            sampleSize = 1;
        }

        return sampleSize;
    }
}
