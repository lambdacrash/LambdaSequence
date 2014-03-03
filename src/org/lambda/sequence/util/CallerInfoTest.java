package org.lambda.sequence.util;

import org.junit.*;

/**
 * Created by brice on 01/03/2014.
 */
public class CallerInfoTest {
    @Test
    public void testGetCallDate() throws Exception {
        if ((CallerInfo.getCallDate() != System.currentTimeMillis())) {
            throw new AssertionError();
        }
     }

    @Test
    public void testGetCallerClassName() throws Exception {
        final String className = CallerInfo.getCallerClassName();
        System.out.println(className);

        if (className == null || className.length() == 0) {
            throw new AssertionError();
        }
    }

    @Test
    public void testGetCallerName() throws Exception {
        final String callerName = CallerInfo.getCallerName();
        System.out.println(callerName);

        if (callerName == null || callerName.length() == 0) {
            throw new AssertionError();
        }
    }

    @Test
    public void testForFun() throws Exception {
    // Fuck

        if (true) {
            throw new AssertionError();
        }
    }
}
