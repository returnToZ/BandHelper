package james.com.mag1c_band.Activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import james.com.mag1c_band.R;
import james.com.mag1c_band.Util.Bluetooth;

public class BluetoothActivity extends Activity {
    private BluetoothReceiver receiver;
    private BluetoothAdapter bluetoothAdapter;
    private List<String> devices;
    private List<BluetoothDevice> deviceList;
    private Bluetooth client;
    private final String lockName = "BOLUTEK";
    private String message = "000001";
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

        listView = (ListView) this.findViewById(R.id.list);
        deviceList = new ArrayList<BluetoothDevice>();
        devices = new ArrayList<String>();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothAdapter.startDiscovery();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        receiver = new BluetoothReceiver();
        registerReceiver(receiver, filter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setContentView(R.layout.connect_layout);
                BluetoothDevice device = deviceList.get(position);
                client = new Bluetooth(device, handler);
                try
                {
                    client.connect();
                } catch (Exception e)
                {
                    Log.e("TAG", e.toString());
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case Bluetooth.CONNECT_FAILED:
                    Toast.makeText(BluetoothActivity.this, "连接失败", Toast.LENGTH_LONG).show();
                    try
                    {
                        client.connect();
                    } catch (Exception e)
                    {
                        Log.e("TAG", e.toString());
                    }
                    break;
                case Bluetooth.CONNECT_SUCCESS:
                    Toast.makeText(BluetoothActivity.this, "连接成功", Toast.LENGTH_LONG).show();
                    break;
                case Bluetooth.READ_FAILED:
                    Toast.makeText(BluetoothActivity.this, "读取失败", Toast.LENGTH_LONG).show();
                    break;
                case Bluetooth.WRITE_FAILED:
                    Toast.makeText(BluetoothActivity.this, "写入失败", Toast.LENGTH_LONG).show();
                    break;
                case Bluetooth.DATA:
                    Toast.makeText(BluetoothActivity.this, msg.arg1 + "", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    private class BluetoothReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action))
            {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (isLock(device))
                {
                    devices.add(device.getName());
                }
                deviceList.add(device);
            }
            showDevices();
        }
    }

    private boolean isLock(BluetoothDevice device) {
        boolean isLockName = (device.getName()).equals(lockName);
        boolean isSingleDevice = devices.indexOf(device.getName()) == -1;
        return isLockName && isSingleDevice;
    }

    private void showDevices() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                devices);
        listView.setAdapter(adapter);
    }
}
