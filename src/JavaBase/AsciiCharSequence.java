package JavaBase;

import java.util.Arrays;

public class AsciiCharSequence implements java.lang.CharSequence {
    byte[] arrByte;

    public AsciiCharSequence(byte[] arrByte) {
        this.arrByte=arrByte.clone();
    }

    @Override
    public String toString() {
        return new String(arrByte);
    }

    @Override
    public int length() {
        return arrByte.length;
    }

    @Override
    public char charAt(int index) {
        return (char) arrByte[index];
    }

    @Override
    public AsciiCharSequence subSequence(int start, int end) {
        return  new AsciiCharSequence(Arrays.copyOfRange(arrByte, start, end));
    }
}
