package com.rohan.routing;

import org.junit.Test;

import static org.junit.Assert.assertFalse;


public class TestRandomStuff {

    @Test
    public void testFalseIsFalse() {
        assertFalse("false should be false", false);
    }


    // Should fail
    @Test
    public void testTrueIsNotFalse() {
        assertFalse("(Intentionally failing test) true should be false", true);
    }
    
    @Test
    public void runtimeExceptionTest() {
        throw new RuntimeException();   
    }
}
