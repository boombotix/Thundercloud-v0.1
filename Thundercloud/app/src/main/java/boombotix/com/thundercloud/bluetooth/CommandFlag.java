package boombotix.com.thundercloud.bluetooth;

/**
 * Flag byte associated with each command when sending serial data to speaker
 *
 * @author Theo Kanning
 */
public enum CommandFlag {
    //todo add more commands
    //todo see if we need a separate command flag for requests and responses
    GET_SPEAKER_INFO(0x31);

    private byte flag;

    CommandFlag(int flag){
        this.flag = (byte)flag;
    }
}
