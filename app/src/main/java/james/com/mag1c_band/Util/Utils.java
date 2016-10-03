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
    public static boolean isReceiver = false;
    public static boolean isConnect = false;
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

    public static byte[] isChartDataExists(Context context) {
        File fileDir = new File(context.getFilesDir(), "data");
        FileInputStream is;
        try
        {
            is = new FileInputStream(fileDir);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] array = new byte[1024];
            int len;
            while ((len = is.read(array)) != -1)
            {
                bos.write(array, 0, len);
            }
            bos.close();
            Log.d("readOrNot",bos.toString());
            is.close();
            //String temp;
            /*
            temp = bos.toString().replaceAll("76 81 ","");
            temp = temp.replaceAll("0D 0A ","");
            temp = temp.replaceAll(" ","");
            Log.d("data",temp);
            Log.d("dataLength",String.valueOf(temp.length()));
            */
            return bos.toByteArray();
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
    public static String bytesToHexString(byte[] bytes) {
        String result = "";
        for (int i = 0; i < bytes.length; i++) {
            String hexString = Integer.toHexString(bytes[i] & 0xFF);
            if (hexString.length() == 1) {
                hexString = '0' + hexString;
            }
            result += hexString.toUpperCase();
        }
        return result;
    }
    public static int SquareRoot(int data)
    {
        int num = data;
        int num2 = 0;
        int num3;
        for (num3 = 1073741824; num3 > num; num3 >>= 2)
        {
        }
        while (num3 != 0)
        {
            if (num >= num2 + num3)
            {
                num -= num2 + num3;
                num2 += 2 * num3;
            }
            num2 >>= 1;
            num3 >>= 2;
        }
        return num2;
    }

}
