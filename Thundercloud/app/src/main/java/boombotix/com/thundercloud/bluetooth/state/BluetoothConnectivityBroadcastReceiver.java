package boombotix.com.thundercloud.bluetooth.state;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import timber.log.Timber;

/**
 * BroadcastReciver that handles changes in bluetooth state or connectivity.
 *
 * Created by kriedema on 6/14/16.
 */
public class BluetoothConnectivityBroadcastReceiver extends BroadcastReceiver {
    private final int COULDNT_GET_STATE_FROM_EXTRAS = -1;

    @Override
    public void onReceive(Context context, Intent intent) {

        logState(intent);

        int state = getState(intent);

        actOnState(state);
    }

    private void logState(Intent intent){
        Timber.d("Intent action " + intent.getAction());

        Timber.d("Previous Connection State " + intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_CONNECTION_STATE, COULDNT_GET_STATE_FROM_EXTRAS));
        Timber.d("Current Connection State " + intent.getIntExtra(BluetoothAdapter.EXTRA_CONNECTION_STATE, COULDNT_GET_STATE_FROM_EXTRAS));
        Timber.d("Device " + intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE));

        Timber.d("Previous State " + intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_STATE, COULDNT_GET_STATE_FROM_EXTRAS));
        Timber.d("Current State " + intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, COULDNT_GET_STATE_FROM_EXTRAS));

        Bundle bundle = intent.getExtras();

        for (String key : bundle.keySet()) {
            Object value = bundle.get(key);
            if(value == null) continue;
            Timber.v(String.format("%s %s (%s)", key, value.toString(), value.getClass().getName()));
        }
    }

    private int getState(Intent intent){
        int state;

        state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, COULDNT_GET_STATE_FROM_EXTRAS);

        if(state == COULDNT_GET_STATE_FROM_EXTRAS){
            state = intent.getIntExtra(BluetoothAdapter.EXTRA_CONNECTION_STATE, COULDNT_GET_STATE_FROM_EXTRAS);
        }

        return state;
    }

    private void actOnState(int bluetoothState){
        switch (bluetoothState) {
            case BluetoothAdapter.STATE_CONNECTED:
                Timber.d("STATE_CONNECTED");
                break;
            case BluetoothAdapter.STATE_CONNECTING:
                Timber.d("STATE_CONNECTING");
                break;
            case BluetoothAdapter.STATE_DISCONNECTED:
                Timber.d("STATE_DISCONNECTED");
                break;
            case BluetoothAdapter.STATE_DISCONNECTING:
                Timber.d("STATE_DISCONNECTING");
                break;
            case BluetoothAdapter.STATE_ON:
                Timber.d("STATE_ON");
                break;
            case BluetoothAdapter.STATE_TURNING_ON:
                Timber.d("STATE_TURNING_ON");
                break;
            case BluetoothAdapter.STATE_OFF:
                Timber.d("STATE_OFF");
                break;
            case BluetoothAdapter.STATE_TURNING_OFF:
                Timber.d("STATE_TURNING_OFF");
                break;
            case COULDNT_GET_STATE_FROM_EXTRAS:
                Timber.d("Failed to get state from extras");
                break;
            default:
                Timber.d("We don't handle this bluetooth state: " + bluetoothState);
                break;
        }
    }
}
