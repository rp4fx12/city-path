package com.rohan.routing;

import org.junit.Test;

import static org.junit.Assert.assertFalse;


public class TestRandomStuff {

    @Test
    public void testFalseIsFalse() {
        assertFalse("False should be false", false);
    }


    // Should fail
    @Test
    public void testTrueIsNotFalse() {
        assertFalse("True should be false", true);
    }
}
