package boombotix.com.thundercloud.dependencyinjection.module;

import javax.inject.Singleton;

import boombotix.com.thundercloud.bluetooth.BluetoothCommandReceiver;
import boombotix.com.thundercloud.bluetooth.BluetoothCommandSender;
import boombotix.com.thundercloud.bluetooth.BluetoothConnection;
import boombotix.com.thundercloud.bluetooth.BluetoothMessageWrapper;
import dagger.Module;
import dagger.Provides;

@Module
public class BluetoothModule {

    @Provides
    @Singleton
    BluetoothConnection provideBluetoothConnection() {
        return new BluetoothConnection();
    }

    @Singleton
    @Provides
    BluetoothCommandSender provideBluetoothCommandSender() {
        return new BluetoothCommandSender();
    }

    @Singleton
    @Provides
    BluetoothCommandReceiver provideBluetoothCommandReciever() {
        return new BluetoothCommandReceiver();
    }

    @Singleton
    @Provides
    BluetoothMessageWrapper provideBluetoothMessageWrapper() {
        return new BluetoothMessageWrapper();
    }
}
