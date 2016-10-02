package james.com.mag1c_band.Util;

import android.util.Log;

public class KalmanAlgorithm {
    private double X = 0;
    private double lastX = 0;
    private String data;
    private int index = 0;
    private final static double MeasureNoise_Q = 0.01;
    private final static double MeasureNoise_R = 10;

    public KalmanAlgorithm(String data) {
        this.data = data;
    }

    public int processData(){//拿到X轴的数据
        String temp = null;
        index += 16;
        try
        {
            temp = data.substring(index,index+4);
            Log.d("originalString",temp);
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return Integer.parseInt(temp,16);
    }
    public double Kalman() {
        double data = processData();
        Log.d("Xdata",String .valueOf(data));
        double xMid, xNow, pMid, pNow, kg;
        xMid = lastX;
        pMid = X + MeasureNoise_Q;  //p_mid=p(k|k-1),p_last=p(k-1|k-1),Q=噪声
        kg = pMid / (pMid + MeasureNoise_R);    //kg为kalman filter，R为噪声
        xNow = xMid + kg * (data - xMid);   //估计出的最优值
        pNow = (1 - kg) * pMid; //最优值对应的covariance
        X = pNow;   //更新covariance值
        lastX = xNow;   //更新系统状态值
        Log.d("xNow",String.valueOf(xNow));
        return xNow;
    }
}
