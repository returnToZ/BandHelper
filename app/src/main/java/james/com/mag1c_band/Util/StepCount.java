package james.com.mag1c_band.Util;

import android.util.Log;

public class StepCount {
    
    private double step_value = 750f;
    private double data_time = 0;
    
    /*
    以下数据是给更复杂的calcStep用的
     */
    private double data_high_peak = 0;
    private double data_low_peak = 0;
    private double data_high_trough = 0;
    private double data_low_trough = 0;
    private double step_num = 0;
    private double err_num = 0;
    
    /*
    以下数据是给简化版calcStep用的
     */
    private double data_high = 0;
    private double data_low = 0;

    /**
     * 使用Y轴数据计算步数
     * @param Y     Y轴数据 - 滤波处理后
     * @return  0表示计步不成功 1表示计步成功
     */
    public int calcStep(double Y)
    {
        if (Y > this.data_high)
        {
            this.data_high = Y;
            this.data_low = Y;
            return 0;
        }
        this.data_time += 1f;
        if (Y < this.data_low)
        {
            this.data_low = Y;
            return 0;
        }
        Log.d("testCalcY",String.valueOf(Y));
        Log.d("testCalcHigh",String.valueOf(data_high));
        Log.d("testCalcLow",String.valueOf(data_low));
        if (this.data_high - Y > this.step_value)
        {
            if (this.data_time > 1f & this.data_time < 200f)
            {
                return 1;
            }
            else
            {
                this.err_num++;
            }
            this.data_time = 0f;
            this.data_high = Y;
            this.data_low = Y;
        }
        return 0;
    }
    /*
    public double calcStep(double X, double Y, double Z, double temp) {
        if (X > data_high_peak){
            data_high_peak = X;
            data_low_peak = X;
        }else {
            data_time += 1;
            if (X > data_low_peak){
                data_low_peak = X;
            }else {
                if (data_high_peak - X > step_value){
                    data_low_peak = X;
                    data_high_peak = X;
                    if (data_time > 1 && data_time < 200){
                        step_num += 1;
                    }else {
                        err_num += 1;
                    }
                }else {
                    data_time = 0;
                }
            }
        }
        if (X < data_low_trough){
            data_high_trough = X;
            data_low_trough = X;
        }else {
            data_time += 1;
            if (X > data_high_trough){
                data_high_trough = X;
            }else {
                if (X - data_low_trough > step_value){
                    data_low_trough = X;
                    data_high_trough = X;
                    if (data_time > 1 && data_time < 200){
                        step_num += 1;
                    }else {
                        err_num += 1;
                    }
                }else {
                    data_time = 0;
                }
            }
        }
        Log.d("stepNum",String.valueOf(step_num));
        return step_num;
    }
    */
}
