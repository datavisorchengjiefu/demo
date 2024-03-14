///*************************************************************************
// *
// * Copyright (c) 2016, DATAVISOR, INC.
// * All rights reserved.
// * __________________
// *
// * NOTICE:  All information contained herein is, and remains
// * the property of DataVisor, Inc.
// * The intellectual and technical concepts contained
// * herein are proprietary to DataVisor, Inc. and
// * may be covered by U.S. and Foreign Patents,
// * patents in process, and are protected by trade secret or copyright law.
// * Dissemination of this information or reproduction of this material
// * is strictly forbidden unless prior written permission is obtained
// * from DataVisor, Inc.
// */
//
//package com.fcjexample.demo.test.pgp;
//
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.io.OutputStream;
//
//public class Main {
//    public static void main(String[] args) throws Exception {
//        String encryptedFileName = "/Users/chengjiefu/research/work/develop/FP-3571/fcjtest/data01.csv.gpg";
//        String privateKeyFileName = "/Users/chengjiefu/research/work/develop/FP-3571/fcjtest/private.pgp";
//        String decryptedFileName = "/Users/chengjiefu/research/work/develop/FP-3571/fcjtest/decrypt01";
//        //        char[] passphrase = "12345678".toCharArray();
//        String passphrase = "12345678";
//        PgpDecryptionUtil pgpDecryptionUtil = new PgpDecryptionUtil(privateKeyFileName, passphrase);
//
//        InputStream inputStream = new FileInputStream(encryptedFileName);
//        OutputStream outputStream = new FileOutputStream(decryptedFileName);
//        pgpDecryptionUtil.decrypt(inputStream, outputStream);
//
//        System.out.println("succeed! ");
//
//    }
//}
