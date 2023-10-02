import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LargeNumberTest {
    private final LargeNumber first = LargeNumber.fromUTF_8("23289369139942678221076391220460918849412622651431");
    private final LargeNumber second = LargeNumber.fromUTF_8("83090263346602483507303870635949701222703939535801");

    @Test
    void add() {
        LargeNumber expect = LargeNumber.fromUTF_8("106379632486545161728380261856410620072116562187232");
        LargeNumber actual = first.add(second);
        System.out.println(first.toString("first:  "));
        System.out.println(second.toString("second: "));
        System.out.println(actual.toString("actual: "));
        System.out.println(expect.toString("expect: "));
        assertEquals(expect, actual);
    }

    @Test
    void multiply() {
        LargeNumber expect = LargeNumber.fromUTF_8("1935119815014074121536103348056448444039083549670411378348797303795444254411091353359440315968381231");
        LargeNumber actual = first.multiply(second);
        System.out.println(first.toString("first:  "));
        System.out.println(second.toString("second: "));
        System.out.println(actual.toString("actual: "));
        System.out.println(expect.toString("expect: "));
        assertEquals(expect, actual);
    }
    @Test
    void multiplySimple() {
        LargeNumber first = LargeNumber.fromUTF_8("1000000000");
        LargeNumber second = LargeNumber.fromUTF_8("1000000000");
        LargeNumber expect = LargeNumber.fromUTF_8("1000000000000000000");
        LargeNumber actual = first.multiply(second);
        System.out.println(first.toString("first:  "));
        System.out.println(second.toString("second: "));
        System.out.println(actual.toString("actual: "));
        System.out.println(expect.toString("expect: "));
        assertEquals(expect, actual);
    }
}