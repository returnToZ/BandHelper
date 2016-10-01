package james.com.mag1c_band.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Utils {
    /**
     * 检测当的网络（WLAN、3G/2G）状态
     *
     * @param context Context
     * @return true 表示网络可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected())
            {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

    public static String isChartDataExists(Context context) {
        File fileDir = new File(context.getFilesDir(), "data");
        //File fileDir = new File(f, "data.txt");
        FileInputStream is;
        try
        {
            is = new FileInputStream(fileDir);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] array = new byte[1024];
            int len = -1;
            while ((len = is.read(array)) != -1)
            {
                bos.write(array, 0, len);
            }
            bos.close();
            is.close();
            String temp;
            temp = bos.toString().replaceAll("76 81 ","");
            temp = temp.replaceAll("0D 0A ","");
            temp = temp.replaceAll(" ","");
            Log.d("data",temp);
            return temp;
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
