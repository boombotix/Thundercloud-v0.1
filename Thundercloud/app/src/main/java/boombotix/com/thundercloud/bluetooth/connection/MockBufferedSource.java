package boombotix.com.thundercloud.bluetooth.connection;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Random;

import boombotix.com.thundercloud.model.constants.BluetoothConstants;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Options;
import okio.Sink;
import okio.Timeout;

/**
 * BufferedSource implimentation that we can use until the spec is final. This can be changed to
 * return whatever we thing the speaker should be sending us back after we send it the wireless password
 *
 * Created by kriedema on 6/27/16.
 */
class MockBufferedSource implements BufferedSource {

    @Override
    public Buffer buffer() {
        return null;
    }

    @Override
    public boolean exhausted() throws IOException {
        return false;
    }

    @Override
    public void require(long byteCount) throws IOException {

    }

    @Override
    public boolean request(long byteCount) throws IOException {
        return false;
    }

    @Override
    public byte readByte() throws IOException {
        int random = new Random(System.currentTimeMillis()).nextInt(100);

        if(random < 33) {
            return BluetoothConstants.SUCCESS;
        } else if (random > 33 && random < 66) {
            return BluetoothConstants.ERROR;
        }

        return BluetoothConstants.TIMEOUT;
    }

    @Override
    public short readShort() throws IOException {
        return 0;
    }

    @Override
    public short readShortLe() throws IOException {
        return 0;
    }

    @Override
    public int readInt() throws IOException {
        return 0;
    }

    @Override
    public int readIntLe() throws IOException {
        return 0;
    }

    @Override
    public long readLong() throws IOException {
        return 0;
    }

    @Override
    public long readLongLe() throws IOException {
        return 0;
    }

    @Override
    public long readDecimalLong() throws IOException {
        return 0;
    }

    @Override
    public long readHexadecimalUnsignedLong() throws IOException {
        return 0;
    }

    @Override
    public void skip(long byteCount) throws IOException {

    }

    @Override
    public ByteString readByteString() throws IOException {
        return null;
    }

    @Override
    public ByteString readByteString(long byteCount) throws IOException {
        return null;
    }

    @Override
    public int select(Options options) throws IOException {
        return 0;
    }

    @Override
    public byte[] readByteArray() throws IOException {
        return new byte[0];
    }

    @Override
    public byte[] readByteArray(long byteCount) throws IOException {
        return new byte[0];
    }

    @Override
    public int read(byte[] sink) throws IOException {
        return 0;
    }

    @Override
    public void readFully(byte[] sink) throws IOException {

    }

    @Override
    public int read(byte[] sink, int offset, int byteCount) throws IOException {
        return 0;
    }

    @Override
    public void readFully(Buffer sink, long byteCount) throws IOException {

    }

    @Override
    public long readAll(Sink sink) throws IOException {
        return 0;
    }

    @Override
    public String readUtf8() throws IOException {
        return null;
    }

    @Override
    public String readUtf8(long byteCount) throws IOException {
        return null;
    }

    @Override
    public String readUtf8Line() throws IOException {
        return null;
    }

    @Override
    public String readUtf8LineStrict() throws IOException {
        return null;
    }

    @Override
    public int readUtf8CodePoint() throws IOException {
        return 0;
    }

    @Override
    public String readString(Charset charset) throws IOException {
        return null;
    }

    @Override
    public String readString(long byteCount, Charset charset) throws IOException {
        return null;
    }

    @Override
    public long indexOf(byte b) throws IOException {
        return 0;
    }

    @Override
    public long indexOf(byte b, long fromIndex) throws IOException {
        return 0;
    }

    @Override
    public long indexOf(ByteString bytes) throws IOException {
        return 0;
    }

    @Override
    public long indexOf(ByteString bytes, long fromIndex) throws IOException {
        return 0;
    }

    @Override
    public long indexOfElement(ByteString targetBytes) throws IOException {
        return 0;
    }

    @Override
    public long indexOfElement(ByteString targetBytes, long fromIndex) throws IOException {
        return 0;
    }

    @Override
    public InputStream inputStream() {
        return null;
    }

    @Override
    public long read(Buffer sink, long byteCount) throws IOException {
        return 0;
    }

    @Override
    public Timeout timeout() {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}
