package ej4;

import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;

/**
 * La clase {@code Server} implementa un servidor SSL básico que escucha en el puerto 4444 y
 * acepta conexiones de clientes, procesando mensajes que recibe y enviándolos de vuelta.
 */
public class Server {
    public static void main(String[] args) {
        try {
            // Cargar el keystore con las claves del servidor
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream("server.keystore"), "password".toCharArray());

            // Configurar la fábrica de administradores de claves (Key Manager Factory)
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(keyStore, "password".toCharArray());

            // Configurar el contexto SSL para establecer una comunicación segura
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

            // Crear una fábrica de sockets de servidor SSL y establecer el puerto en el que escuchará
            SSLServerSocketFactory serverSocketFactory = sslContext.getServerSocketFactory();
            SSLServerSocket serverSocket = (SSLServerSocket) serverSocketFactory.createServerSocket(4444);

            System.out.println("Servidor iniciado, esperando conexiones...");

            // Aceptar la conexión entrante de un cliente
            SSLSocket clientSocket = (SSLSocket) serverSocket.accept();

            // Crear flujos de entrada y salida para la comunicación con el cliente
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

            String message;
            // Leer los mensajes enviados por el cliente y devolverlos
            while ((message = input.readLine()) != null) {
                System.out.println("Recibido del cliente: " + message);
                output.println(message); // Enviar el mensaje de vuelta al cliente
            }

            // Cerrar el socket del cliente después de finalizar la comunicación
            clientSocket.close();
        } catch (Exception e) {
            // Manejo de excepciones y despliegue del error
            e.printStackTrace();
        }
    }
}
