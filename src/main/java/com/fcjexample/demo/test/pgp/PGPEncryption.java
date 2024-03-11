/*************************************************************************
 *
 * Copyright (c) 2016, DATAVISOR, INC.
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

package com.fcjexample.demo.test.pgp;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.*;
import org.bouncycastle.openpgp.jcajce.JcaPGPObjectFactory;
import org.bouncycastle.openpgp.operator.PublicKeyDataDecryptorFactory;
import org.bouncycastle.openpgp.operator.jcajce.JcaKeyFingerprintCalculator;
import org.bouncycastle.openpgp.operator.jcajce.JcePBESecretKeyDecryptorBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePublicKeyDataDecryptorFactoryBuilder;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Security;

public class PGPEncryption {

    public static void decryptPGPFile(InputStream encryptedStream, InputStream privateKeyStream,
            char[] passphrase, OutputStream decryptedStream) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        // Create PGP object factory
        PGPObjectFactory pgpF = new JcaPGPObjectFactory(PGPUtil.getDecoderStream(encryptedStream));

        // Read the encrypted data
        Object o = pgpF.nextObject();

        // Check if it's a PGP encrypted data object
        if (o instanceof org.bouncycastle.openpgp.PGPEncryptedDataList) {
            org.bouncycastle.openpgp.PGPEncryptedDataList encDataList = (org.bouncycastle.openpgp.PGPEncryptedDataList) o;
            org.bouncycastle.openpgp.PGPPublicKeyEncryptedData encData = (org.bouncycastle.openpgp.PGPPublicKeyEncryptedData) encDataList.get(
                    0);

            // Get the secret key
            PGPSecretKeyRing pgpSec = new PGPSecretKeyRing(
                    PGPUtil.getDecoderStream(privateKeyStream), new JcaKeyFingerprintCalculator());
            PGPSecretKey secretKey = pgpSec.getSecretKey(encData.getKeyID());

            //            PGPSecretKey secretKey = null;

            // Decrypt the data
            PublicKeyDataDecryptorFactory dataDecryptorFactory = new JcePublicKeyDataDecryptorFactoryBuilder().setProvider(
                            "BC")
                    .build(secretKey.extractPrivateKey(
                            new JcePBESecretKeyDecryptorBuilder().setProvider("BC")
                                    .build(passphrase)));

            InputStream clear = encData.getDataStream(dataDecryptorFactory);

            // Write decrypted data to output stream
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = clear.read(buffer)) != -1) {
                decryptedStream.write(buffer, 0, bytesRead);
            }
            decryptedStream.close();
            clear.close();
        } else {
            throw new PGPException("PGP Encrypted Data not found in the input stream");
        }
    }

    public static void main(String[] args) {
        try {
            //            FileInputStream encryptedFileStream = new FileInputStream("encrypted.pgp");
            FileInputStream encryptedFileStream = new FileInputStream(
                    "/Users/chengjiefu/research/work/develop/FP-3571/demo/data03.csv.gpg");
            //            FileInputStream privateKeyStream = new FileInputStream("private.key");
            FileInputStream privateKeyStream = new FileInputStream(
                    "/Users/chengjiefu/.gnupg/private-keys-v1.d/FF2952A93FF9384B1951AA39958656AEE8B86650.key");
            FileOutputStream decryptedFileStream = new FileOutputStream(
                    "/Users/chengjiefu/research/work/develop/FP-3571/demo/decrypted.txt");

            char[] passphrase = "fcjvae1234".toCharArray();

            decryptPGPFile(encryptedFileStream, privateKeyStream, passphrase, decryptedFileStream);

            System.out.println("Decryption Successful!");

            encryptedFileStream.close();
            privateKeyStream.close();
            decryptedFileStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

