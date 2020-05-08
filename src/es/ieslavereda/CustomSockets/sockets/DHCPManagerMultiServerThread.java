package es.ieslavereda.CustomSockets.sockets;

import java.net.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.io.*;

public class DHCPManagerMultiServerThread extends Thread {
	private Socket socket = null;

	public DHCPManagerMultiServerThread(Socket socket) {
		super("KKMultiServerThread");
		this.socket = socket;
	}

	public void run() {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-uuuu H:m:s");
		String instante = LocalDateTime.now().format(formatter);
		
		System.out.println(instante + " -> Conexion realizada desde la IP " + socket.getRemoteSocketAddress().toString());
		try (

				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

		) {

			MensajeSocket mensajeSalida, mensajeRecibido;
			GestionMensajes gm = new GestionMensajes();

			while ((mensajeRecibido = (MensajeSocket) ois.readObject()) != null) {

				System.out.println("Recibido el mensaje: " + mensajeRecibido);

				if (mensajeRecibido.getTipoMensaje() == MensajeSocket.ACEPTADO_CIERRE) {
					break;
				}
				mensajeSalida = gm.processInput(mensajeRecibido);
				int tipo = mensajeSalida.getTipoMensaje();
				boolean demasidoLargo = (tipo == MensajeSocket.ENVIO_DHCP_CONF || tipo == MensajeSocket.ENVIO_JOURNALCTL);
				System.out.println("Enviando el mensaje: "
						+ ((demasidoLargo) ? "Demasiados datos para mostrar .... datos omitidos" : mensajeSalida));

				oos.writeObject(mensajeSalida);

				if (mensajeSalida.getTipoMensaje() == MensajeSocket.ACEPTADO_CIERRE) {

					break;
				}
			}
			System.out.print("Cerrando conexion para la ip " + socket.getRemoteSocketAddress().toString() + " ...");
			socket.close();
			System.out.println("OK!");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
