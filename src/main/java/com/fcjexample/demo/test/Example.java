/*************************************************************************
 *
 * Copyright (c) 2020, DATAVISOR, INC.
 * All rights reserved.
 * __________________
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of DataVisor, Inc.
 * The intellectual and technical concepts contained
 * herein are proprietary to DataVisor, Inc. and
 * may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from DataVisor, Inc.
 */

package com.fcjexample.demo.test;

public class Example {
    public static void main(String args[]) {
        try {
            //            int arr[] = new int[7];
            //            arr[4] = 30 / 0;
            //            System.out.println("Last Statement of try block");

            int arr[] = new int[7];
            arr[10] = 10 / 5;
            System.out.println("Last Statement of try block");
        } catch (ArithmeticException e) {
            System.out.println("You should not divide a number by zero");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Accessing array elements outside of the limit");
        } catch (Exception e) {
            System.out.println("Some Other Exception");
        }
        System.out.println("Out of the try-catch block");
    }

    // 子类要在上面
    //    public static void main(String args[]) {
    //        try {
    //            int arr[] = new int[7];
    //            arr[10] = 10 / 5;
    //            System.out.println("Last Statement of try block");
    //        } catch (Exception e) {
    //            System.out.println("Some Other Exception");
    //        } catch (ArithmeticException e) {
    //            System.out.println("You should not divide a number by zero");
    //        } catch (ArrayIndexOutOfBoundsException e) {
    //            System.out.println("Accessing array elements outside of the limit");
    //        }
    //        System.out.println("Out of the try-catch block");
    //    }
}
