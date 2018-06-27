package bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.sample.locationupdates.R;

import org.w3c.dom.Text;

import java.util.Set;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

public class BluetoothActivity extends Activity {
    private Button btnStartBluetooth;
    private TextView txtTextView;
    BluetoothAdapter mBluetoothAdapter;
    int REQUEST_ENABLE_BT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                // verifica aparelhos conectados
                Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
                // If there are paired devices
                if (pairedDevices.size() > 0) {
                    // Loop through paired devices
                    for (BluetoothDevice device : pairedDevices) {
                        // Add the name and address to an array adapter to show in a ListView
                        txtTextView.append(device.getName() + "\n" + device.getAddress());
                        //mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                    }
                }
            } else {
                // n faz nada
            }
        }
    }
}