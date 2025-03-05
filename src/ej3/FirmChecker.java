package ej3;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.Signature;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

public class FirmChecker {

    /**
     * Verifica la firma de un archivo usando la clave pública.
     * La firma se debe encontrar en un archivo con el mismo nombre que el archivo original,
     * pero con la extensión ".firma".
     *
     * @param archivoRuta  Ruta al archivo original.
     * @param clavePublica Ruta al archivo que contiene la clave pública (formato X.509).
     * @throws Exception Si ocurre algún error durante la verificación.
     */
    public static void verificarFirma(String archivoRuta, String clavePublica) throws Exception {
        // Lee el contenido del archivo y la firma
        File archivo = new File(archivoRuta);
        byte[] contenido = leerArchivo(archivo);
        byte[] firma = leerArchivo(new File(archivoRuta + ".firma"));

        // Crea el hash del archivo original usando SHA-256
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        byte[] hash = sha256.digest(contenido);

        // Carga la clave pública desde el archivo
        RSAPublicKey publicKey = cargarClavePublica(clavePublica);

        // Verifica la firma usando la clave pública
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(hash);
        boolean esValida = signature.verify(firma);

        // Muestra el resultado de la verificación
        if (esValida) {
            System.out.println("La firma es válida.");
        } else {
            System.out.println("La firma no es válida.");
        }
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
     * Carga la clave pública desde un archivo en formato X.509.
     *
     * @param ruta La ruta al archivo de clave pública.
     * @return La clave pública cargada.
     * @throws Exception Si ocurre un error al cargar la clave pública.
     */
    private static RSAPublicKey cargarClavePublica(String ruta) throws Exception {
        byte[] claveBytes = leerArchivo(new File(ruta));
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(claveBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    public static void main(String[] args) throws Exception {
        // Argumentos del programa
        if (args.length != 2) {
            System.out.println("Uso: java FirmChecker <archivo> <clave publica>");
            return;
        }
        String archivoRuta = args[0];
        String clavePublica = args[1];

        // Verificar la firma
        verificarFirma(archivoRuta, clavePublica);
    }
}
