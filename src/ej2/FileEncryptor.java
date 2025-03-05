package ej2;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;

/**
 * Clase para cifrar y descifrar archivos utilizando AES.
 */
public class FileEncryptor {
    private static final String ALGORITHM = "AES"; // Algoritmo de cifrado utilizado
    private static final int KEY_SIZE = 16; // Tamaño de la clave en bytes (128 bits)

    public static void main(String[] args) {
        // Crear un selector de archivos para que el usuario elija un archivo
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);

        // Verificar si el usuario ha seleccionado un archivo
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile(); // Obtener el archivo seleccionado
            String password = JOptionPane.showInputDialog("Ingrese la contraseña:"); // Solicitar la contraseña

            if (password != null && !password.isEmpty()) {
                int option = JOptionPane.showConfirmDialog(null, "¿Desea descifrar el archivo?", "Seleccionar acción", JOptionPane.YES_NO_OPTION);

                try {
                    if (option == JOptionPane.YES_OPTION) {
                        // Si el usuario elige descifrar
                        File decryptedFile = new File(selectedFile.getAbsolutePath().replace(".enc", ""));
                        decryptFile(selectedFile, decryptedFile, password); // Descifrar el archivo
                        System.out.println("Archivo descifrado creado: " + decryptedFile.getName());
                    } else {
                        // Si el usuario elige cifrar
                        File encryptedFile = new File(selectedFile.getAbsolutePath() + ".enc"); // Nombre del archivo cifrado
                        encryptFile(selectedFile, encryptedFile, password); // Cifrar el archivo

                        // Eliminar el archivo original tras el cifrado
                        if (selectedFile.delete()) {
                            System.out.println("Archivo original eliminado: " + selectedFile.getName());
                        }
                        System.out.println("Archivo cifrado creado: " + encryptedFile.getName());
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Cifra un archivo usando AES y una contraseña.
     *
     * @param inputFile  Archivo de entrada.
     * @param outputFile Archivo cifrado de salida.
     * @param password   Contraseña para cifrar.
     * @throws Exception Si ocurre un error durante el cifrado.
     */
    public static void encryptFile(File inputFile, File outputFile, String password) throws Exception {
        SecretKeySpec key = generateKey(password); // Generar la clave a partir de la contraseña
        Cipher cipher = Cipher.getInstance(ALGORITHM); // Obtener una instancia del cifrador AES
        cipher.init(Cipher.ENCRYPT_MODE, key); // Configurar el cifrador en modo cifrado
        processFile(cipher, inputFile, outputFile); // Procesar el archivo para cifrarlo
    }

    /**
     * Descifra un archivo usando AES y una contraseña.
     *
     * @param inputFile  Archivo cifrado de entrada.
     * @param outputFile Archivo de salida descifrado.
     * @param password   Contraseña utilizada para el cifrado.
     * @throws Exception Si ocurre un error durante el descifrado.
     */
    public static void decryptFile(File inputFile, File outputFile, String password) throws Exception {
        SecretKeySpec key = generateKey(password); // Generar la clave a partir de la contraseña
        Cipher cipher = Cipher.getInstance(ALGORITHM); // Obtener una instancia del cifrador AES
        cipher.init(Cipher.DECRYPT_MODE, key); // Configurar el cifrador en modo descifrado
        processFile(cipher, inputFile, outputFile); // Procesar el archivo para descifrarlo
    }

    /**
     * Genera una clave AES a partir de una contraseña.
     * La clave se genera tomando los primeros 16 bytes de la contraseña.
     *
     * @param password Contraseña de entrada.
     * @return Clave secreta AES.
     */
    private static SecretKeySpec generateKey(String password) {
        byte[] keyBytes = Arrays.copyOf(password.getBytes(), KEY_SIZE); // Asegurar que la clave tenga 16 bytes
        return new SecretKeySpec(keyBytes, ALGORITHM); // Crear la clave secreta AES
    }

    /**
     * Procesa un archivo para cifrarlo o descifrarlo.
     * Lee el archivo en bloques de 1024 bytes y los procesa con el cifrador.
     *
     * @param cipher     Instancia del cifrador AES.
     * @param inputFile  Archivo de entrada.
     * @param outputFile Archivo de salida.
     * @throws Exception Si ocurre un error durante el procesamiento.
     */
    private static void processFile(Cipher cipher, File inputFile, File outputFile) throws Exception {
        try (FileInputStream fis = new FileInputStream(inputFile); // Leer el archivo de entrada
             FileOutputStream fos = new FileOutputStream(outputFile); // Escribir el archivo de salida
             CipherOutputStream cos = new CipherOutputStream(fos, cipher)) { // Aplicar el cifrado/descifrado

            byte[] buffer = new byte[1024]; // Buffer de lectura de 1024 bytes
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) { // Leer hasta el final del archivo
                cos.write(buffer, 0, bytesRead); // Escribir los datos procesados en el archivo de salida
            }
        }
    }
}
