import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.ByteArrayOutputStream;
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
        for (int i = start; i < maxLength || carry != 0; i++) {
            // Get the digits at the current position for both numbers
            int digit1 = (i < getSize()) ? digits[i] : 0;
            int digit2 = (i < second.getSize()) ? second.digits[i] : 0;
            // Calculate the sum of digits and the carry
            int sum = digit1 + digit2 + carry;
            byte value = (byte) (sum % 10);
            buffer.write(value);
            carry = sum / 10; // Update the carry
        }
        return new LargeNumber(buffer.toByteArray());
    }

    public LargeNumber multiply(LargeNumber second) {
        int n = Math.max(this.getSize(), second.getSize());
        // Base case: If the numbers are small, use regular multiplication
        if (n <= 9) {
            long result = this.toLong() * second.toLong();
            byte[] newDigits = fromLong(result);
            return new LargeNumber(newDigits);
        }
        // Split the numbers into two halves
        int m = (n + 1) / 2;
        LargeNumber low1 = splitLow(m);
        LargeNumber high1 = splitHigh(m);
        LargeNumber low2 = second.splitLow(m);
        LargeNumber high2 = second.splitHigh(m);
        // Inside the multiply method
        LargeNumber z0 = low1.multiply(low2);
        LargeNumber z1 = (low1.add(high1)).multiply(low2.add(high2));
        LargeNumber z2 = high1.multiply(high2);
        return z2.shiftLeft(2 * m).add(z1).add(z0.shiftLeft(m));
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
    private LargeNumber splitHigh(int m) {
        return new LargeNumber(digits, start, size - m);
    }

    private LargeNumber splitLow(int m) {
        return new LargeNumber(digits, m, size - m);
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
        String collected = IntStream.range(0, size)
                .map(i -> size - 1 - i)
                .map(i -> digits[start + i])
                .mapToObj(i -> i + "")
                .collect(Collectors.joining());
        return "LargeNumber{" +
                "digits=" + collected + '}';
    }

    public String toString(String prefix) {
        return prefix + this;
    }
}
