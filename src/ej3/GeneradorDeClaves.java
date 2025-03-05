package ej3;

import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.*;
import javax.crypto.Cipher;

public class GeneradorDeClaves {

    /**
     * Genera un par de claves RSA (pública y privada) y las guarda en archivos.
     * La clave privada se guarda en un archivo con extensión ".priv" y la clave pública en un archivo con extensión ".pub".
     *
     * @param longitudClaves Longitud de la clave RSA (por ejemplo, 2048).
     * @throws Exception Si ocurre algún error durante la generación de las claves.
     */
    public static void generarClaves(int longitudClaves) throws Exception {
        // Crear el generador de claves RSA
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(longitudClaves);

        // Generar el par de claves (pública y privada)
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // Obtener la clave privada y la clave pública
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        // Guardar la clave privada en un archivo
        guardarClavePrivada(privateKey, "clave.priv");

        // Guardar la clave pública en un archivo
        guardarClavePublica(publicKey, "clave.pub");

        System.out.println("Las claves RSA se han generado y guardado.");
    }

    /**
     * Guarda la clave privada en un archivo en formato PKCS#8.
     *
     * @param privateKey La clave privada RSA.
     * @param archivo El nombre del archivo donde se guardará la clave privada.
     * @throws IOException Si ocurre un error al guardar la clave.
     */
    private static void guardarClavePrivada(RSAPrivateKey privateKey, String archivo) throws IOException {
        // Codificar la clave privada en formato PKCS#8
        byte[] keyBytes = privateKey.getEncoded();
        try (FileOutputStream fos = new FileOutputStream(archivo)) {
            fos.write(keyBytes);
        }
    }

    /**
     * Guarda la clave pública en un archivo en formato X.509.
     *
     * @param publicKey La clave pública RSA.
     * @param archivo El nombre del archivo donde se guardará la clave pública.
     * @throws IOException Si ocurre un error al guardar la clave.
     */
    private static void guardarClavePublica(RSAPublicKey publicKey, String archivo) throws IOException {
        // Codificar la clave pública en formato X.509
        byte[] keyBytes = publicKey.getEncoded();
        try (FileOutputStream fos = new FileOutputStream(archivo)) {
            fos.write(keyBytes);
        }
    }

    public static void main(String[] args) {
        try {
            // Longitud de la clave RSA (2048 bits)
            int longitudClaves = 2048;

            // Generar las claves y guardarlas
            generarClaves(longitudClaves);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
