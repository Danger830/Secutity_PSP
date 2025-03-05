package ej1;
import java.io.*;
import java.security.*;
import javax.swing.*;

/**
 * Clase para generar hashes MD5 y SHA-256 de un archivo seleccionado mediante JFileChooser.
 */
public class FileHashGenerator {

    public static void main(String[] args) {
        // Crear un selector de archivos para que el usuario elija un archivo
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);

        // Verificar si el usuario ha seleccionado un archivo
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                // Generar los hashes MD5 y SHA-256 del archivo seleccionado
                String md5Hash = getFileHash(selectedFile, "MD5");
                String sha256Hash = getFileHash(selectedFile, "SHA-256");

                // Imprimir los resultados en la consola
                System.out.println("MD5: " + md5Hash);
                System.out.println("SHA-256: " + sha256Hash);

                // Verificar si ambos hashes se generaron correctamente
                if (md5Hash != null && sha256Hash != null) {
                    System.out.println("Hashes generados correctamente.");
                } else {
                    System.out.println("Error en la generación de hashes.");
                }
            } catch (Exception e) {
                // Capturar y mostrar cualquier error que ocurra durante la generación del hash
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Genera el hash de un archivo usando el algoritmo especificado.
     *
     * @param file      Archivo del cual se generará el hash.
     * @param algorithm Algoritmo de hash a utilizar (MD5 o SHA-256).
     * @return Hash del archivo en formato hexadecimal.
     * @throws Exception Si ocurre un error en la lectura del archivo o en la generación del hash.
     */
    public static String getFileHash(File file, String algorithm) throws Exception {
        // Crear una instancia de MessageDigest con el algoritmo especificado
        MessageDigest digest = MessageDigest.getInstance(algorithm);

        // Abrir el archivo y leerlo en bloques de 1024 bytes para evitar consumir demasiada memoria
        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            byte[] buffer = new byte[1024]; // Buffer de lectura
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead); // Actualizar el hash con los datos leídos
            }
        }

        // Obtener el resultado del hash en bytes
        byte[] hashBytes = digest.digest();

        // Convertir los bytes del hash a formato hexadecimal
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b)); // Formatear cada byte como un par de caracteres hexadecimales
        }

        return hexString.toString(); // Retornar el hash en formato hexadecimal
    }
}