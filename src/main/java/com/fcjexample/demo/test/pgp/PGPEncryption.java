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

import java.io.*;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Iterator;

public class PGPEncryption {
    private static final int BUFFER_SIZE = 1024;

    public static void decryptPGPFile(InputStream encryptedStream, InputStream privateKeyStream,
            char[] passphrase, OutputStream decryptedStream) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        InputStream in = PGPUtil.getDecoderStream(encryptedStream);

        PGPObjectFactory pgpF = new PGPObjectFactory(in);

        PGPEncryptedDataList enc = null;
        // Read the encrypted data
        Object o = pgpF.nextObject();

        // The first object might be a PGP marker packet.
        if (o instanceof PGPEncryptedDataList) {
            enc = (PGPEncryptedDataList) o;
        } else {
            enc = (PGPEncryptedDataList) pgpF.nextObject();
        }

        // Decrypt the data
        // Find the secret key
        Iterator it = enc.getEncryptedDataObjects();
        PGPPrivateKey sKey = null;
        PGPPublicKeyEncryptedData pbe = null;
        PGPSecretKeyRingCollection pgpSec = new PGPSecretKeyRingCollection(
                PGPUtil.getDecoderStream(privateKeyStream));
        while (sKey == null && it.hasNext()) {
            pbe = (PGPPublicKeyEncryptedData) it.next();
            sKey = findSecretKey(pgpSec, pbe.getKeyID(), passphrase);
        }
        if (sKey == null) {
            throw new IllegalArgumentException(
                    "secret key for message not found.");
        }

        InputStream clear = pbe.getDataStream(sKey, "BC");

        // Write decrypted data to output stream
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = clear.read(buffer)) != -1) {
            decryptedStream.write(buffer, 0, bytesRead);
        }
        decryptedStream.close();
        clear.close();
        //        } else {
        //            throw new PGPException("PGP Encrypted Data not found in the input stream");
        //        }
    }

    public static InputStream decryptPGPFile(InputStream encryptedStream,
            InputStream privateKeyStream,
            char[] passphrase) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        InputStream in = PGPUtil.getDecoderStream(encryptedStream);

        PGPObjectFactory pgpF = new PGPObjectFactory(in);

        PGPEncryptedDataList enc = null;
        // Read the encrypted data
        Object o = pgpF.nextObject();

        // The first object might be a PGP marker packet.
        if (o instanceof PGPEncryptedDataList) {
            enc = (PGPEncryptedDataList) o;
        } else {
            enc = (PGPEncryptedDataList) pgpF.nextObject();
        }

        // Decrypt the data
        // Find the secret key
        Iterator it = enc.getEncryptedDataObjects();
        PGPPrivateKey sKey = null;
        PGPPublicKeyEncryptedData pbe = null;
        PGPSecretKeyRingCollection pgpSec = new PGPSecretKeyRingCollection(
                PGPUtil.getDecoderStream(privateKeyStream));
        while (sKey == null && it.hasNext()) {
            pbe = (PGPPublicKeyEncryptedData) it.next();
            sKey = findSecretKey(pgpSec, pbe.getKeyID(), passphrase);
        }
        if (sKey == null) {
            throw new IllegalArgumentException(
                    "secret key for message not found.");
        }

        InputStream clearDataStream = pbe.getDataStream(sKey, "BC");

        //        PGPObjectFactory pgpFact = new PGPObjectFactory(clearDataStream);
        //        PGPCompressedData cData = (PGPCompressedData) pgpFact.nextObject();
        //        pgpFact = new PGPObjectFactory(cData.getDataStream());
        //        PGPLiteralData ld = (PGPLiteralData) pgpFact.nextObject();
        //        InputStream unc = ld.getInputStream();
        //        return unc;

        PGPObjectFactory plainFact = new PGPObjectFactory(clearDataStream);
        Object message = plainFact.nextObject();

        // Decrypt messages
        if (message instanceof PGPCompressedData) {
            PGPCompressedData compressedData = (PGPCompressedData) message;
            PGPObjectFactory pgpFact = new PGPObjectFactory(compressedData.getDataStream());
            message = pgpFact.nextObject();
        }
        InputStream decryptedStream;
        if (message instanceof PGPLiteralData) {
            PGPLiteralData literalData = (PGPLiteralData) message;
            decryptedStream = literalData.getDataStream();
        } else if (message instanceof PGPOnePassSignatureList) {
            //            throw new PGPException(
            //                    "Encrypted message contains a signed message - not literal data.");

            message = plainFact.nextObject();// for len's example:T24TransactionSFTPTestSample.csv.pgp
            if (message instanceof PGPLiteralData) {
                PGPLiteralData literalData = (PGPLiteralData) message;
                decryptedStream = literalData.getDataStream();
            } else {
                throw new PGPException(
                        "Encrypted message contains a signed message - not literal data.");
            }

        } else {
            throw new PGPException("Message is not a simple encrypted file - type unknown.");
        }

        return decryptedStream;
    }

    private static PGPPrivateKey findSecretKey(PGPSecretKeyRingCollection secKeyRing, long keyID,
            char[] password)
            throws PGPException, NoSuchProviderException {
        PGPSecretKey pgpSecKey = secKeyRing.getSecretKey(keyID);
        if (pgpSecKey == null) {
            return null;
        }
        return pgpSecKey.extractPrivateKey(password, "BC");
    }

    /**
     * 这是第一种，直接outputStream
     * This function decrypts data contained in inputFile and output to outputFile
     *
     * @param inputFile
     * @param outputFile
     * @param keyFile
     * @param password
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    //    public static void decryptFile(String inputFile, String outputFile, String keyFile,
    //            char[] password) throws Exception {
    //        InputStream inputStream = new FileInputStream(inputFile);
    //        InputStream keyStream = new FileInputStream(keyFile);
    //        OutputStream decryptedStream = decryptStream(inputStream, keyStream, password, outputFile);
    //        decryptedStream.close();
    //        keyStream.close();
    //        inputStream.close();
    //    }

    // 这是第二种，先得到InputStream
    public static void decryptFile(String inputFile, String outputFile, String keyFile,
            char[] password) throws Exception {
        InputStream inputStream = new FileInputStream(inputFile);
        InputStream keyStream = new FileInputStream(keyFile);
        InputStream decryptedStream = decryptPGPFile(inputStream, keyStream, password);
        OutputStream outputStream = new FileOutputStream(outputFile);
        streamFlow(decryptedStream, outputStream);
        decryptedStream.close();
        //        outputStream.close();
        keyStream.close();
        inputStream.close();
    }

    /**
     * Pipe data from inputStream to outputStream
     *
     * @param inputStream
     * @param outputStream
     * @throws IOException
     */
    public static void streamFlow(InputStream inputStream, OutputStream outputStream)
            throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
    }

    /**
     * This is the core decryption function that
     * 1. takes an InputStream inputStream containing encrypted data
     * 2. decrypt this InputStream with pgp decryption method with private key and password
     * 3. return another InputStream containing clear data
     *
     * @param inputStream input data stream containing encrypted data
     * @param keyStream   private/secret key stream
     * @param password    password associated to this key stream
     * @return data stream containing clear data
     * @throws IOException
     * @throws PGPException
     * @throws NoSuchProviderException
     */
    public static OutputStream decryptStream(InputStream inputStream,
            InputStream keyStream, char[] password, String outputFile)
            throws IOException, PGPException, NoSuchProviderException {
        Security.addProvider(new BouncyCastleProvider());
        inputStream = PGPUtil.getDecoderStream(inputStream);
        PGPObjectFactory pgpObjFactory = new PGPObjectFactory(inputStream);
        PGPEncryptedDataList encryptedDataList;
        Object obj = pgpObjFactory.nextObject();

        // the first object might be a PGP marker packet.
        if (obj instanceof PGPEncryptedDataList) {
            encryptedDataList = (PGPEncryptedDataList) obj;
        } else {
            encryptedDataList = (PGPEncryptedDataList) pgpObjFactory.nextObject();
        }

        // find the secret key
        Iterator<PGPPublicKeyEncryptedData> encrypteddataIter = encryptedDataList
                .getEncryptedDataObjects();
        PGPPrivateKey secKey = null;
        PGPPublicKeyEncryptedData encryptedData = null;
        while (secKey == null && encrypteddataIter.hasNext()) {
            encryptedData = encrypteddataIter.next();
            secKey = findSecretKey(keyStream, encryptedData.getKeyID(), password);
        }
        if (secKey == null) {
            throw new IllegalArgumentException("Secret key for message not found.");
        }
        InputStream clearDataStream = encryptedData.getDataStream(secKey, "BC");
        PGPObjectFactory plainFact = new PGPObjectFactory(clearDataStream);
        Object message = plainFact.nextObject();

        // Decrypt messages
        if (message instanceof PGPCompressedData) {
            PGPCompressedData compressedData = (PGPCompressedData) message;
            PGPObjectFactory pgpFact = new PGPObjectFactory(compressedData.getDataStream());
            message = pgpFact.nextObject();
        }
        InputStream decryptedStream;
        if (message instanceof PGPLiteralData) {
            PGPLiteralData literalData = (PGPLiteralData) message;
            decryptedStream = literalData.getDataStream();
        } else if (message instanceof PGPOnePassSignatureList) {
            throw new PGPException(
                    "Encrypted message contains a signed message - not literal data.");
        } else {
            throw new PGPException("Message is not a simple encrypted file - type unknown.");
        }

        // Write decrypted data to output stream
        OutputStream outputStream = new FileOutputStream(outputFile);
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = decryptedStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        return outputStream;
    }

    /**
     * Load a secret keyring collection from secKeyStream and find the secret key corresponding to
     * keyID
     * If keyID is not found in the keyring collection, return null
     *
     * @param secKeyStream input stream of the secret key file
     * @param keyID        keyID associated to secret key
     * @param password     passphrase to decrypt secret key with.
     * @return
     * @throws IOException
     * @throws PGPException
     * @throws NoSuchProviderException
     */
    private static PGPPrivateKey findSecretKey(InputStream secKeyStream, long keyID,
            char[] password)
            throws IOException, PGPException, NoSuchProviderException {
        PGPSecretKeyRingCollection secKeyRing = new PGPSecretKeyRingCollection(
                org.bouncycastle.openpgp.PGPUtil.getDecoderStream(secKeyStream));
        return findSecretKey(secKeyRing, keyID, password);
    }

    public static void main(String[] args) {

        try {
            //            String encryptedFileName = "/Users/chengjiefu/research/work/develop/FP-3571/tower/rawlog.20240304_010102.csv.pgp";
            //            String privateKeyFileName = "/Users/chengjiefu/research/work/develop/FP-3571/tower/towerfedcu.key";
            //            String decryptedFileName = "/Users/chengjiefu/research/work/develop/FP-3571/tower/decrypt07";
            //            String decryptedFileName = "/Users/chengjiefu/research/work/develop/FP-3571/tower/decrypt04.pgp";

            String encryptedFileName = "/Users/chengjiefu/research/work/develop/FP-3571/len/T24TransactionSFTPTestSample.csv.pgp";
            String privateKeyFileName = "/Users/chengjiefu/research/work/develop/FP-3571/len/eqbank.key";
            String decryptedFileName = "/Users/chengjiefu/research/work/develop/FP-3571/len/decrypt01";
            char[] passphrase = "datavisor".toCharArray();

            //            String encryptedFileName = "/Users/chengjiefu/research/work/develop/FP-3571/fcjtest/data01.csv.gpg";
            //            String privateKeyFileName = "/Users/chengjiefu/research/work/develop/FP-3571/fcjtest/private.pgp";
            //            String decryptedFileName = "/Users/chengjiefu/research/work/develop/FP-3571/fcjtest/decrypt01";
            //            char[] passphrase = "12345678".toCharArray();

            //            extracted(encryptedFileName, privateKeyFileName, decryptedFileName);
            decryptFile(encryptedFileName, decryptedFileName, privateKeyFileName, passphrase);

            System.out.println("Decryption Successful!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // wrong
    private static void extracted(String encryptedFile, String privateKeyFile, String decryptedFile,
            char[] passphrase)
            throws Exception {
        FileInputStream encryptedFileStream = new FileInputStream(encryptedFile);
        FileInputStream privateKeyStream = new FileInputStream(privateKeyFile);
        FileOutputStream decryptedFileStream = new FileOutputStream(decryptedFile);

        decryptPGPFile(encryptedFileStream, privateKeyStream, passphrase, decryptedFileStream);

        encryptedFileStream.close();
        privateKeyStream.close();
        decryptedFileStream.close();
    }
}

