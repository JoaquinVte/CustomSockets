package es.ieslavereda.CustomSockets.sockets;

import java.net.*;

import java.io.*;

public class DHCPManagerMultiServer {

	private static int conexionesActivas = 0;

	public static void main(String[] args) throws IOException {

		int portNumber = 1977;
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
