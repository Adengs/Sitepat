package com.codelabs.sitepat_customer.imagepicker;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;

public class AppUtils {
    static File getWorkingDirectory(Activity activity) {
    /*File directory =
        new File(Environment.getExternalStorageDirectory(), activity.getApplicationContext().getPackageName());
      if (!directory.exists()) {
          directory.mkdirs();
      }

      MyLog.logE("image : "+directory);*/
        File directory = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

//    File directory = new File(activity.getFilesDir(), "images");

        Log.e("APP UTILS","directory : "+directory);
        return directory;
    }

    static FileUri createImageFile(Activity activity, String prefix) {
        FileUri fileUri = new FileUri();

        File image = null;
        try {
            image = File.createTempFile(prefix + String.valueOf(System.currentTimeMillis()), ".jpg",
                    getWorkingDirectory(activity));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (image != null) {
            fileUri.setFile(image);
            //
//      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fileUri.setImageUrl(FileProvider.getUriForFile(activity,
                    activity.getApplicationContext().getPackageName() + ".provider",
                    image));
//      }

      /*else {
        fileUri.setImageUrl(Uri.parse("file:" + image.getAbsolutePath()));
      }*/
        }
        return fileUri;
    }
}
