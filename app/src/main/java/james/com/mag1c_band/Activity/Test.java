package james.com.mag1c_band.Activity;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import james.com.mag1c_band.R;

public class Test extends Activity {
    public static final int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter mBluetoothAdapter;
    private List<String> mArrayAdapter;
    TextView pairs;
    final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView
                mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        pairs = (TextView) findViewById(R.id.pairs);
        mArrayAdapter = new ArrayList<>();
        openBluetooth(this);
        show();
        setBroadcatReceive();
        search();
    }

    private void search() {
        Intent discoverableIntent = new
                Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);
    }

    public void openBluetooth(Context context){
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
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
    private void show(){
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        String temp = "";
// If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                temp += device.getName();
                temp += device.getAddress();
            }
        }
        if (pairedDevices.size() == 0){
            pairs.setText("null");
        }else {
            pairs.setText(temp);
        }
    }
    private void setBroadcatReceive(){
        // Register the BroadcastReceiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
    }
    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

}
