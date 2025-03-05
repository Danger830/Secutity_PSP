package ej4;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.KeyStore;

/**
 * La clase {@code Client} implementa un cliente SSL que se conecta a un servidor SSL,
 * envía un mensaje y recibe una respuesta del servidor de manera segura.
 */
public class Client {
    public static void main(String[] args) {
        try {
            // Cargar el truststore con las claves del cliente (certificados de confianza)
            KeyStore trustStore = KeyStore.getInstance("JKS");
            trustStore.load(new FileInputStream("client.truststore"), "password".toCharArray());

            // Configurar la fábrica de administradores de confianza (Trust Manager Factory)
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
            trustManagerFactory.init(trustStore);

            // Configurar el contexto SSL utilizando los administradores de confianza
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

            // Crear el socket SSL y conectarse al servidor en localhost:4444
            SSLSocketFactory socketFactory = sslContext.getSocketFactory();
            SSLSocket socket = (SSLSocket) socketFactory.createSocket("localhost", 4444);

            // Crear flujos de entrada y salida para la comunicación con el servidor
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            // Solicitar al usuario que ingrese un mensaje
            System.out.println("Escribe un mensaje para enviar al servidor:");
            String message = userInput.readLine();

            // Enviar el mensaje al servidor
            output.println(message);

            // Leer la respuesta del servidor y mostrarla
            String response = input.readLine();
            System.out.println("Respuesta del servidor: " + response);

            // Cerrar el socket después de la comunicación
            socket.close();
        } catch (Exception e) {
            // Manejo de excepciones e impresión del error
            e.printStackTrace();
        }
    }
}
