package es.ieslavereda.CustomSockets.sockets;

import java.net.*;

import java.io.*;

/**
 * Gestor de peticiones multihilo para el lado del servidor
 * 
 * @author joaalsai
 *
 */
public class DHCPManagerMultiServer {

	private static int conexionesActivas = 0;

	/**
	 * Ejecutable para el servidor. Si no se pasa parametro de puerto, este empezara
	 * a escuchar por el puerto 1977
	 * 
	 * @param args Opcional. Puerto por el que desamos que escuche el ServerSocket 
	 * @throws IOException Normalmente el puerto ya esta en uso
	 */
	public static void main(String[] args) throws IOException {

		int portNumber = 1977;

		try {
			if (args.length > 0)
				portNumber = Integer.parseInt(args[0]);

		} catch (Exception e) {
			System.out.println("Parametro no valido, se intenta iniciar por el puerto " + portNumber);
			portNumber = 1977;
		}

		boolean listening = true;
		System.out.println("Socket iniciado en el puerto " + portNumber);

		try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
			while (listening) {
				new DHCPManagerMultiServerThread(serverSocket.accept()).start();
				System.out.println("Conexiones iniciada nº: " + (++conexionesActivas));
			}
		} catch (IOException e) {
			System.err.println("No se puede escuchar por el puerto " + portNumber);
			System.exit(-1);
		}
	}
}
