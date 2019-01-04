package com.rohan.random;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestBooleans {

    @Test
    public void testFalseIsFalse() {
        assertFalse("False should be false", false);
    }


    // Should fail
    @Test
    public void testTrueIsNotFalse() {
        assertFalse("True should be false", true);
    }

    @Test
    public void testTrueIsTrue() {
        assertTrue("True should  be true", true);
    }


    @Test
    public void testTrueIsNotFalse() {
        assertTrue("True should not be false", !true);
    }
}
