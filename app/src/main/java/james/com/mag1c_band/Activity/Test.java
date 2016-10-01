package james.com.mag1c_band.Activity;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class Test extends Activity {
    public static final int REQUEST_ENABLE_BT = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        openBluetooth(this);
    }
    public void openBluetooth(Context context){
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if  (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            Toast.makeText(context,"Bluetooth not supported !!!",Toast.LENGTH_SHORT).show();
        }
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            /*
            常量REQUEST_ENABLE_BT是本地定义的整型（需要大于0），
            当系统通过onActivityResult() 返回至你的应用程序时，将作为requestCode的参数。
            如果成功开启了蓝牙，
            你的Activity将收到RESULT_OK作为resultCode。
            如果蓝牙不能成功开启（例如用户选择“取消”），
            则resultCode为RESULT_CANCELED。
             */
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }
}
