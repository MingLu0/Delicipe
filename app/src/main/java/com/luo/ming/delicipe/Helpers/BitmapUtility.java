package com.luo.ming.delicipe.Helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class BitmapUtility {

    public static byte[] convertBitmapToBytes(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,30,stream);
        return stream.toByteArray();
    }

    public static Bitmap covertBytesToBitmap(byte[] image){
        return BitmapFactory.decodeByteArray(image,0,image.length);
    }
}
