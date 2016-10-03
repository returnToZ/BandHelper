package james.com.mag1c_band.Util;


import android.util.Log;

public class KalmanAlgorithm {
    private double X = 0;
    private byte[] array;
    private int index = 0;
    private final static double MeasureNoise_Q = 10f;
    private final static double MeasureNoise_R = 0.01f;
    private double anyLast;
    private double pAnyLast;
    private double yLast;
    private double zLast;
    private double xLast;
    private double pLastX;
    private double pLastY;
    private double pLastZ;
    private double calcY;//专门用于计算步数

    public KalmanAlgorithm(byte[] array) {
        this.array = array;
    }

    private double[] processData() {//拿到X、Y、Z轴的数据
        double[] result = new double[3];
        String temp = null;
        double num2;
        double num3;
        double num4;
        try
        {
            num2 = (double)((int)array[index + 2] + (int)array[index + 3] * 256);
            Log.d("originalX", String.valueOf(num2));
            result[0] = num2;
            if (num2 > 32767f)
            {
                num2 -= 65536f;
                result[0] = num2;
                //Log.d("originalResult0", String.valueOf(result[0]));
            }
            num3 = (double)((int)array[index + 4] + (int)array[index + 5] * 256);
            Log.d("originalY", String.valueOf(num3));
            result[1] = num3;
            if (num3 > 32767f)
            {
                num3 -= 65536f;
                result[1] = num3;
                //Log.d("originalResult1", String.valueOf(result[1]));
            }
            num4 = (double)((int)array[index + 6] + (int)array[index + 7] * 256);
            Log.d("originalZ", String.valueOf(num4));
            result[2] = num4;
            if (num4 > 32767f)
            {
                num4 -= 65536f;
                result[2] = num4;
                //Log.d("originalResult2", String.valueOf(result[2]));
            }
            index += 16;
            /*
            result[0] = (double)Integer.parseInt(array.substring(index, index + 4), 16);
            result[1] = (double)Integer.parseInt(array.substring(index + 4, index + 8), 16);
            result[2] = (double)Integer.parseInt(array.substring(index + 8, index + 12), 16);
            Log.d("originalX", array.substring(index, index + 4));
            Log.d("originalY", array.substring(index + 4, index + 8));
            Log.d("originalZ", array.substring(index + 8, index + 12));
            */
        } catch (IndexOutOfBoundsException e)
        {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    public double getFinalData() {
        double[] data = processData();
        Log.d("FinalData0",String.valueOf(data[0]));
        try
        {
            double tempX = kalmanFilterX(data[0]);
            double tempY = kalmanFilterY(data[1]);
            calcY = tempY;
            double tempZ = kalmanFilterZ(data[2]);
            double result = Utils.SquareRoot((int) (tempX * tempX + tempY * tempY + tempZ * tempZ));
            Log.d("FinalX",String.valueOf(data[0]));
            Log.d("FinalY",String.valueOf(tempY));
            Log.d("FinalZ",String.valueOf(tempZ));
            result = kalmanFilterAny(result);
            return result;
        }catch (NullPointerException e){
            return -9999999f;
        }
    }

    private double kalmanFilterAny(double resAny)
    {
        double num = this.anyLast;
        num = this.anyLast;
        double num2 = this.pAnyLast + MeasureNoise_Q;
        double num3 = num2 / (num2 + MeasureNoise_R);
        double result = num + num3 * (resAny - num);
        this.pAnyLast = (1f - num3) * num2;
        this.anyLast = result;
        return result;
    }

    private double kalmanFilterZ(double resZ)
    {
        double num = this.zLast;
        num = this.zLast;
        double num2 = this.pLastZ + MeasureNoise_Q;
        double num3 = num2 / (num2 + MeasureNoise_R);
        double result = num + num3 * (resZ - num);
        this.pLastZ = (1f - num3) * num2;
        this.zLast = result;
        return result;
    }

    private double kalmanFilterX(double resX)
    {
        double num = this.xLast;
        num = this.xLast;
        double num2 = this.pLastX + MeasureNoise_Q;
        double num3 = num2 / (num2 + MeasureNoise_R);
        double result = num + num3 * (resX - num);
        this.pLastX = (1f - num3) * num2;
        this.xLast = result;
        return result;
    }

    private double kalmanFilterY(double resY)
    {
        double num = this.yLast;
        num = this.yLast;
        double num2 = this.pLastY + MeasureNoise_Q;
        double num3 = num2 / (num2 + MeasureNoise_R);
        double result = num + num3 * (resY - num);
        this.pLastY = (1f - num3) * num2;
        this.yLast = result;
        return result;
    }

    public double getXLast() {
        return xLast;
    }

    public double getYLast() {
        return yLast;
    }

    public double getCalcY() {
        return calcY;
    }

    /*
    public double Kalman() {
        double array = processData();
        Log.d("Xdata",String .valueOf(array));
        double xMid, xNow, pMid, pNow, kg;
        xMid = lastX;
        pMid = X + MeasureNoise_Q;  //p_mid=p(k|k-1),p_last=p(k-1|k-1),Q=噪声
        kg = pMid / (pMid + MeasureNoise_R);    //kg为kalman filter，R为噪声
        xNow = xMid + kg * (array - xMid);   //估计出的最优值
        pNow = (1 - kg) * pMid; //最优值对应的covariance
        X = pNow;   //更新covariance值
        lastX = xNow;   //更新系统状态值
        Log.d("xNow",String.valueOf(xNow));
        return xNow;
    }
    */
}
