package domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class LargeNumber {
    private final byte[] digits;
    @Getter
    private final int start;
    @Getter
    private final int size;
    public static final int N = 9;


    public LargeNumber(byte[] digits) {
        this.digits = digits;
        this.start = 0;
        this.size = digits.length;
    }

    public LargeNumber add(LargeNumber second) {
        // Determine the maximum length of the result array
        int maxLength = Math.max(getSize(), second.getSize());
        ByteArrayOutputStream buffer = new ByteArrayOutputStream(maxLength);
        int carry = 0;
        for (int i = 0; i < maxLength || carry != 0; i++) {
            // Get the digits at the current position for both numbers
            int digit1 = (i < getSize()) ? digits[i + start] : 0;
            int digit2 = (i < second.getSize()) ? second.digits[i + second.start] : 0;
            // Calculate the sum of digits and the carry
            int sum = digit1 + digit2 + carry;
            byte value = (byte) (sum % 10);
            buffer.write(value);
            carry = sum / 10; // Update the carry
        }
        return new LargeNumber(buffer.toByteArray());
    }

    public LargeNumber subtract(LargeNumber second) {
        // Determine the maximum length of the result array
        int maxLength = Math.max(getSize(), second.getSize());
        ByteArrayOutputStream buffer = new ByteArrayOutputStream(maxLength);
        int borrow = 0;

        for (int i = 0; i < maxLength; i++) {
            // Get the digits at the current position for both numbers
            int digit1 = (i < getSize()) ? digits[start + i] : 0;
            int digit2 = (i < second.getSize()) ? second.digits[second.start + i] : 0;
            // Calculate the difference with borrow
            int diff = digit1 - digit2 - borrow;
            if (diff < 0) {
                diff += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }

            buffer.write(diff);
        }
        byte[] byteArray = buffer.toByteArray();
        return new LargeNumber(byteArray, 0, byteArray.length);
    }

    public LargeNumber divide(LargeNumber v) {
        BigDecimal first = new BigDecimal(this.toStringValue());
        BigDecimal second = new BigDecimal(v.toStringValue());
        BigDecimal divide = first.divide(second, RoundingMode.FLOOR);
        return LargeNumber.fromUTF_8(divide.toString());
    }

    public LargeNumber remainder(LargeNumber v) {
        BigDecimal first = new BigDecimal(this.toStringValue());
        BigDecimal second = new BigDecimal(v.toStringValue());
        BigDecimal remainder = first.remainder(second);
        return LargeNumber.fromUTF_8(remainder.toString());
    }

    public LargeNumber pow(int i) {
        BigDecimal first = new BigDecimal(this.toStringValue());
        BigDecimal pow = first.pow(i);
        return LargeNumber.fromUTF_8(pow.toString());
    }

    public LargeNumber multiply(LargeNumber second) {
        int n = Math.max(this.getSize(), second.getSize());
        // Base case: If the numbers are small, use regular multiplication
        if (n <= N) {
            long result = this.toLong() * second.toLong();
            byte[] newDigits = fromLong(result);
            return new LargeNumber(newDigits);
        } else if (Math.min(this.getSize(), second.getSize()) == 1) {
            if (size == 1) {
                return simpleMultiply(second, this.digits[start]);
            }
            return simpleMultiply(this, second.digits[start]);
        }
        return karatsubaMultiply(second, n);
    }

    public LargeNumber karatsubaMultiply(LargeNumber second, int n) {
        // Split the numbers into two halves
        int m = n / 2;
        int low = n - m;
        LargeNumber a = splitHigh(m);//a
        LargeNumber b = splitLow(low);//b
        LargeNumber c = second.splitHigh(m);//c
        LargeNumber d = second.splitLow(low);//d
        // Inside the multiply method
        LargeNumber ac = a.multiply(c);//ac
        LargeNumber bd = b.multiply(d);//bd
        LargeNumber abSum = a.add(b);
        LargeNumber cdSum = c.add(d);
        LargeNumber AC_BD_Sum = ac.add(bd);
        LargeNumber sumM = abSum.multiply(cdSum);
        LargeNumber AD_Plus_BC = sumM.subtract(AC_BD_Sum);//ad+bc = (a+b)(c+d)-ac-bd
        // shift and sum
        LargeNumber AC_Powered = ac.shiftLeft(m * 2);//(ac)*10^2*(n/2)
        LargeNumber AC_BC_PoweredSum = AD_Plus_BC.shiftLeft(m);//(ad+bc)*10^(n/2)
        return AC_Powered.add(AC_BC_PoweredSum).add(bd);
    }

    public static LargeNumber simpleMultiply(LargeNumber second, byte digit) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream(second.getSize() + 1);
        int carry = 0;
        for (int i = 0; i < second.size || carry != 0; i++) {
            int sum = carry;
            byte secondValue = i < second.size ? second.digits[second.start + i] : 0;
            sum += digit * secondValue;
            buffer.write((byte) (sum % 10));
            carry = sum / 10;
        }
        return new LargeNumber(buffer.toByteArray());
    }


    // Helper method to convert byte[] to int
    private long toLong() {
        long result = 0;
        int largestDigitIndex = size - 1 + start;
        for (int i = largestDigitIndex; i >= start; --i) {
            long digit = digits[i];
            result = result * 10 + digit;
        }
        return result;
    }

    // Helper method to convert int to byte[]
    private byte[] fromLong(long value) {
        String str = Long.toString(value);
        byte[] bytes = new byte[str.length()];
        for (int i = 0; i < str.length(); i++) {
            int digitIndex = bytes.length - 1 - i;
            bytes[digitIndex] = (byte) (str.charAt(i) - '0');
        }
        return bytes;
    }

    // Helper method to split a LargeNumber into high and low halves
    private LargeNumber splitLow(int m) {
        if (size - m == 0) {
            return empty;
        }
        return new LargeNumber(digits, start, size - m);
    }

    private static final LargeNumber empty = new LargeNumber(new byte[]{});

    private LargeNumber splitHigh(int m) {
        int size1 = size - m;
        int start1 = start + m;
        if (size1 == 0) {
            size1 = 1;
            start1 = start;
        }
        return new LargeNumber(digits, start1, size1);
    }

    // Helper method to shift a LargeNumber to the left by n digits
    private LargeNumber shiftLeft(int n) {
        byte[] resultDigits = new byte[size + n];
        System.arraycopy(digits, start, resultDigits, n, size);
        return new LargeNumber(resultDigits);
    }

    public static LargeNumber fromUTF_8(String source) {
        return fromUTF_8(source.getBytes());
    }

    private static LargeNumber fromUTF_8(byte[] bytes) {
        byte[] digits = new byte[bytes.length];
        IntStream.range(0, bytes.length)
                .forEach(i -> {
                    int digitIndex = bytes.length - 1 - i;
                    digits[digitIndex] = (byte) (bytes[i] - '0');
                });
        return new LargeNumber(digits);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LargeNumber that = (LargeNumber) o;
        return Arrays.equals(digits, that.digits);
    }

    @Override
    public String toString() {
        String collected = toStringValue();
        return "LargeNumber{" +
                "digits=" + collected + '}';
    }

    private String toStringValue() {
        return IntStream.range(0, size)
                .map(i -> size - 1 - i)
                .map(i -> digits[start + i])
                .mapToObj(i -> i + "")
                .collect(Collectors.joining());
    }

    public String toString(String prefix) {
        return prefix + this;
    }
}
