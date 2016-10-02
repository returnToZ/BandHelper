package james.com.mag1c_band.Util;

public class StepCount {
    private double data_high_peak = 0;
    private double data_low_peak = 0;
    private double data_high_trough = 0;
    private double data_low_trough = 0;
    private double step_value = 400;
    private double data_time = 0;
    private double step_num = 0;
    private double err_num = 0;

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
        return step_num;
    }
}
