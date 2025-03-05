# Secutity_PSP

Este repositorio contiene varios ejemplos de código relacionados con la seguridad en Java, incluyendo generación de hashes, cifrado de archivos, generación y verificación de firmas digitales, así como la implementación de un cliente y servidor SSL.

## Contenido

- [`src/ej1/FileHashGenerator.java`](#filehashgenerator)
- [`src/ej2/FileEncryptor.java`](#fileencryptor)
- [`src/ej3/FirmChecker.java`](#firmchecker)
- [`src/ej3/FirmGenerator.java`](#firmgenerator)
- [`src/ej3/GeneradorDeClaves.java`](#generadordeclaves)
- [`src/ej4/Client.java`](#client)
- [`src/ej4/Server.java`](#server)

## FileHashGenerator

La clase `FileHashGenerator` permite generar hashes MD5 y SHA-256 de un archivo seleccionado mediante un JFileChooser.

### Funcionamiento

1. El usuario selecciona un archivo utilizando un JFileChooser.
2. Se generan los hashes MD5 y SHA-256 del archivo seleccionado.
3. Los resultados se imprimen en la consola.

```java
public class FileHashGenerator {
    public static void main(String[] args) {
        // Código para seleccionar el archivo y generar los hashes
    }

    public static String getFileHash(File file, String algorithm) throws Exception {
        // Código para generar el hash del archivo
    }
}
```

## FileEncryptor

La clase `FileEncryptor` permite cifrar y descifrar archivos utilizando el algoritmo AES.

### Funcionamiento

1. El usuario selecciona un archivo y proporciona una contraseña.
2. El usuario elige si desea cifrar o descifrar el archivo.
3. Dependiendo de la elección, se cifra o descifra el archivo utilizando AES y la contraseña proporcionada.

```java
public class FileEncryptor {
    public static void main(String[] args) {
        // Código para seleccionar el archivo, solicitar la contraseña y cifrar/descifrar el archivo
    }

    public static void encryptFile(File inputFile, File outputFile, String password) throws Exception {
        // Código para cifrar el archivo
    }

    public static void decryptFile(File inputFile, File outputFile, String password) throws Exception {
        // Código para descifrar el archivo
    }

    private static SecretKeySpec generateKey(String password) {
        // Código para generar la clave AES a partir de la contraseña
    }

    private static void processFile(Cipher cipher, File inputFile, File outputFile) throws Exception {
        // Código para procesar el archivo (cifrar/descifrar)
    }
}
```

## FirmChecker

La clase `FirmChecker` verifica la firma de un archivo usando una clave pública.

### Funcionamiento

1. El usuario proporciona la ruta del archivo original y la ruta de la clave pública.
2. Se lee el contenido del archivo y su firma.
3. Se verifica la firma utilizando la clave pública y se muestra el resultado.

```java
public class FirmChecker {
    public static void main(String[] args) throws Exception {
        // Código para verificar la firma del archivo
    }

    public static void verificarFirma(String archivoRuta, String clavePublica) throws Exception {
        // Código para verificar la firma del archivo
    }
}
```

## FirmGenerator

La clase `FirmGenerator` genera una firma para un archivo utilizando SHA-256 y una clave privada.

### Funcionamiento

1. El usuario proporciona la ruta del archivo y la ruta de la clave privada.
2. Se lee el contenido del archivo y se crea un hash utilizando SHA-256.
3. Se firma el hash con la clave privada y se guarda la firma en un archivo.

```java
public class FirmGenerator {
    public static void main(String[] args) throws Exception {
        // Código para generar la firma del archivo
    }

    public static void generarFirma(String archivoRuta, String clavePrivada) throws Exception {
        // Código para generar la firma del archivo
    }
}
```

## GeneradorDeClaves

La clase `GeneradorDeClaves` genera un par de claves RSA (pública y privada) y las guarda en archivos.

### Funcionamiento

1. Se genera un par de claves RSA (pública y privada).
2. La clave privada se guarda en un archivo con extensión ".priv" y la clave pública en un archivo con extensión ".pub".

```java
public class GeneradorDeClaves {
    public static void main(String[] args) {
        // Código para generar y guardar las claves RSA
    }

    public static void generarClaves(int longitudClaves) throws Exception {
        // Código para generar las claves RSA
    }
}
```

## Client

La clase `Client` implementa un cliente SSL que se conecta a un servidor SSL, envía un mensaje y recibe una respuesta del servidor de manera segura.

### Funcionamiento

1. Se carga el truststore con las claves del cliente.
2. Se configura el contexto SSL y se crea un socket SSL.
3. Se conecta al servidor y se comunica de manera segura.

```java
public class Client {
    public static void main(String[] args) {
        // Código para conectar al servidor SSL y comunicarse de manera segura
    }
}
```

## Server

La clase `Server` implementa un servidor SSL básico que escucha en el puerto 4444 y acepta conexiones de clientes, procesando mensajes que recibe y enviándolos de vuelta.

### Funcionamiento

1. Se carga el keystore con las claves del servidor.
2. Se configura el contexto SSL y se crea un socket de servidor SSL.
3. Se acepta la conexión de un cliente y se comunica de manera segura.

```java
public class Server {
    public static void main(String[] args) {
        // Código para aceptar conexiones de clientes y comunicarse de manera segura
    }
}
```