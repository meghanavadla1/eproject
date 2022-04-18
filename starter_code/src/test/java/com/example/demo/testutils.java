package com.example.demo;

import java.lang.reflect.Field;

public class testutils {
//target
// feildname
// toinject
    private static Object target;
    private static String fieldName;
    private static Object toInject;

    //constructor
    public static void injectObjects(Object target, String fieldName, Object toInject) {
        testutils.target = target;
        testutils.fieldName = fieldName;
        testutils.toInject = toInject;

        boolean wasPrivate;
        wasPrivate = false;

        try {
            //try statement allows you to define a block of code to be tested for errors while it is being executed.

            // then The catch statement allows you to define a block of code to be executed, if an error occurs in the try block.
            Field f;
            f = target.getClass().getDeclaredField(fieldName);
            if (f.isAccessible()) {
            } else {
                f.setAccessible(true);
                wasPrivate = true;
            }
            f.set(target, toInject);
            //if to specify a block of code to be executed, if a specified condition is true condition

            //Use else to specify a block of code to be executed, if the same condition is false condition
            if (wasPrivate) {
                f.setAccessible(false);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
