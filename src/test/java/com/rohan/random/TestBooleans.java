package com.rohan.random;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestBooleans {

    @Test
    public void testFalseIsFalse() {
        assertFalse("false should be false", false);
    }


    // Should fail
    @Test
    public void testTrueIsNotFalse() {
        assertFalse("(Intentionally failing tests) true should be false", true);
    }

    @Test
    public void testTrueIsTrue() {
        assertTrue("true should be true", true);
    }


    @Test
    public void testNotFalseIsTrue() {
        assertTrue("Not false should not be true", !false);
    }
}
