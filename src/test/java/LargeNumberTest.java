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
    void subtract() {
        LargeNumber expect = LargeNumber.fromUTF_8("59800894206659805286227479415488782373291316884370");
        LargeNumber actual = second.subtract(first);
        System.out.println(second.toString("second: "));
        System.out.println(first.toString("first:  "));
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

    @Test
    void multiplyEasy() {
        LargeNumber first = LargeNumber.fromUTF_8("146123");
        LargeNumber second = LargeNumber.fromUTF_8("352120");
        LargeNumber expect = LargeNumber.fromUTF_8("51452830760");
        LargeNumber actual = first.multiply(second);
        System.out.println(first.toString("first:  "));
        System.out.println(second.toString("second: "));
        System.out.println(actual.toString("actual: "));
        System.out.println(expect.toString("expect: "));
        assertEquals(expect, actual);
    }

    @Test
    void multiplyEasy2() {
        LargeNumber first = LargeNumber.fromUTF_8("146");
        LargeNumber second = LargeNumber.fromUTF_8("352");
        LargeNumber expect = LargeNumber.fromUTF_8("51392");
        LargeNumber actual = first.multiply(second);
        System.out.println(first.toString("first:  "));
        System.out.println(second.toString("second: "));
        System.out.println(actual.toString("actual: "));
        System.out.println(expect.toString("expect: "));
        assertEquals(expect, actual);
    }

    @Test
    void simpleMultiplyTest() {
        LargeNumber second = LargeNumber.fromUTF_8("9");
        LargeNumber expect = LargeNumber.fromUTF_8("209604322259484103989687520984148269644713603862879");
        LargeNumber actual = first.multiply(second);
        System.out.println(first.toString("first:  "));
        System.out.println(second.toString("second: "));
        System.out.println(actual.toString("actual: "));
        System.out.println(expect.toString("expect: "));
        assertEquals(expect, actual);
    }
}