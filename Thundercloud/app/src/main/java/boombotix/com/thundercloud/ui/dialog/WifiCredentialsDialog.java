package boombotix.com.thundercloud.ui.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.model.WifiCredentials;
import boombotix.com.thundercloud.model.WifiNetwork;

/**
 * Dialog for showing wifi credentials options to user
 *
 * @author Theo Kanning
 */
public class WifiCredentialsDialog {

    //todo add other fields
    //todo add methods to show hide fields based on security type

    public interface WifiCredentialsDialogListener {

        void onCredentialsConfirmed(WifiNetwork network);
    }

    private Context context;

    private WifiNetwork network;

    private View layout;

    private EditText password;

    private WifiCredentialsDialogListener listener;

    AlertDialog.Builder builder;

    public WifiCredentialsDialog(WifiNetwork network, Context context) {
        this.context = context;
        this.network = network;
        initializeDialog();
        createPasswordField();
    }

    private void initializeDialog(){
        createDialogBuilder();
        createDialogLayout();
        setDialogTitle();
    }

    private void createDialogBuilder() {
        builder = new AlertDialog.Builder(context);
    }

    private void createDialogLayout(){
        this.layout = LayoutInflater.from(context)
                .inflate(R.layout.dialog_wifi_credentials, null);
    }

    private void setDialogTitle() {
        String title = context.getString(R.string.wifi_list_prompt_title, network.getSsid());
        builder.setTitle(title);
    }

    public void setListener(WifiCredentialsDialogListener listener) {
        this.listener = listener;
    }

    private void createPasswordField() {
        password = (EditText) layout.findViewById(R.id.password);
    }

    public void show() {
        builder.setView(layout);
        builder.setPositiveButton("Connect", (dialog, which) -> {
            if (listener != null) {
                network.setCredentials(readCredentials());
                listener.onCredentialsConfirmed(network);
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
        });
        builder.show();
    }

    /**
     * Creates a {@link WifiCredentials} object and fills it with all of the data that the user just
     * entered
     *
     * @return object with network credentials
     */
    private WifiCredentials readCredentials() {
        WifiCredentials credentials = new WifiCredentials();
        credentials.setPassword(password.getText().toString());
        //todo create separate method for each field
        return credentials;
    }


}
