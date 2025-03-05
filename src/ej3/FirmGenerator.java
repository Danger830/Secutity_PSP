package ej3;

import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

public class FirmGenerator {

    /**
     * Genera una firma para un archivo utilizando SHA-256 y la clave privada.
     * La firma se guarda en un archivo con el mismo nombre que el original, añadiendo ".firma" al final.
     *
     * @param archivoRuta Ruta al archivo que se va a firmar.
     * @param clavePrivada Ruta al archivo que contiene la clave privada (formato PKCS#8).
     * @throws Exception Si ocurre algún error durante la generación de la firma.
     */
    public static void generarFirma(String archivoRuta, String clavePrivada) throws Exception {
        // Lee el contenido del archivo
        File archivo = new File(archivoRuta);
        byte[] contenido = leerArchivo(archivo);

        // Crea el hash del archivo usando SHA-256
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        byte[] hash = sha256.digest(contenido);

        // Carga la clave privada desde el archivo
        RSAPrivateKey privateKey = cargarClavePrivada(clavePrivada);

        // Firma el hash con la clave privada
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(hash);
        byte[] firma = signature.sign();

        // Guarda la firma en un archivo con el nombre del archivo original + ".firma"
        String firmaRuta = archivoRuta + ".firma";
        guardarArchivo(firmaRuta, firma);
        System.out.println("Firma generada y guardada en: " + firmaRuta);
    }

    /**
     * Lee un archivo y devuelve su contenido en un arreglo de bytes.
     *
     * @param archivo El archivo a leer.
     * @return El contenido del archivo en un arreglo de bytes.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    private static byte[] leerArchivo(File archivo) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (FileInputStream fis = new FileInputStream(archivo)) {
            byte[] buffer = new byte[1024];
            int bytesLeidos;
            while ((bytesLeidos = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesLeidos);
            }
        }
        return bos.toByteArray();
    }

    /**
     * Carga la clave privada desde un archivo en formato PKCS#8.
     *
     * @param ruta La ruta al archivo de clave privada.
     * @return La clave privada cargada.
     * @throws Exception Si ocurre un error al cargar la clave privada.
     */
    private static RSAPrivateKey cargarClavePrivada(String ruta) throws Exception {
        byte[] claveBytes = leerArchivo(new File(ruta));
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(claveBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }

    /**
     * Guarda los bytes en un archivo.
     *
     * @param ruta La ruta donde se guardará el archivo.
     * @param datos Los datos que se guardarán en el archivo.
     * @throws IOException Si ocurre un error al guardar el archivo.
     */
    private static void guardarArchivo(String ruta, byte[] datos) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(ruta)) {
            fos.write(datos);
        }
    }

    public static void main(String[] args) throws Exception {
        // Argumentos del programa
        if (args.length != 2) {
            System.out.println("Uso: java FirmGenerator <archivo> <clave privada>");
            return;
        }
        String archivoRuta = args[0];
        String clavePrivada = args[1];

        // Generar la firma
        generarFirma(archivoRuta, clavePrivada);
    }
}
