package boombotix.com.thundercloud.ui.base;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import boombotix.com.thundercloud.model.WifiNetwork;

/**
 * Dialog for showing wifi credentials options to user
 *
 * @author Theo Kanning
 */
public class WifiCredentialsDialog {

    public interface WifiCredentialsDialogListener {
        void onCredentialsConfirmed(WifiNetwork network, String password);
    }

    private Context context;
    private WifiNetwork network;
    private LinearLayout layout;
    private EditText password;
    private WifiCredentialsDialogListener listener;

    public WifiCredentialsDialog( WifiNetwork network, Context context){
        this.context = context;
        this.network = network;
        this.layout = new LinearLayout(context);
        this.layout.setOrientation(LinearLayout.VERTICAL);
        showPassword();
    }

    public void setListener(WifiCredentialsDialogListener listener){
        this.listener = listener;
    }

    private void showPassword(){
        password = new EditText(context);
        layout.addView(password);
    }

    public void show(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(layout);
        builder.setTitle("Dialog Title");
        builder.setPositiveButton("Connect", (dialog, which) -> {
            if (listener != null) {
                listener.onCredentialsConfirmed(network, "PASSWORD");
            }
            Toast.makeText(context, "Connect pressed", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            Toast.makeText(context, "Cancel pressed", Toast.LENGTH_SHORT).show();
        });
        builder.show();
    }


}
