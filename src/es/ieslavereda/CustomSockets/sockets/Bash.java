package es.ieslavereda.CustomSockets.sockets;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 * Esta clase gestiona la ejecucion de comandos en consola 
 * @author 		<a href="mailto:joaalsai@ieslavereda.es">Joaquin Vicente Alonso Saiz</a>
 * @version 	1.0 28/02/2019
 * @see         ProcessBuilder 
 * @see 		Runtime
 */
public class Bash {

	private String[] command = { "/bin/bash", "-c", "" };
	private String streamSalida = "";
	private String streamError = "";

	/**
	 * Este metodo ejecuta un parametro en el sistema pasado por parametro mediante {@link Runtime}
	 * @param	comando	Comando a ejecutar p.e. "ls -l"
	 * @return 			Devuelve si ha habido error
	 */
	public boolean ejecutarComandoSistema(String comando) {

		command[2] = comando;

		return ejecutar();
	}
	
	/**
	 * Este metodo ejecuta un parametro en el sistema pasado por parametro
	 * @param commands Lista de comandos 
	 * @return        Devuelve si ha habido error
	 */

	public boolean ejecutarComandoSistema(List<String> commands) {
		ProcessBuilder pb = null;

		try {
			// Run macro on target
			pb = new ProcessBuilder(commands);
			pb.directory(new File("/home/joaalsai"));
			pb.redirectErrorStream(true);
			Process process = pb.start();

			
			
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null, previous = null;
			while ((line = br.readLine()) != null)
				if (!line.equals(previous)) {
					previous = line;
					
					streamSalida+=line+"\n";
				}

			//Check result
	        if (process.waitFor() == 0) {
	            return true;
	        }
			// Abnormal termination: Log command parameters and output and throw
			// ExecutionException
			System.err.println(commands);
			

		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return false;
	}

	/**
	 * Metodo que devuelve el flujo de salida (stdout) del ultimo comando ejecutado
	 * @return 	String con la salida del ultimo comando ejecutado
	 */
	public String getStreamSalida() {
		return streamSalida;
	}

	/**
	 * Metodo que devuelve el flujo de salida de error (stderr) del ultimo comando ejecutado
	 * @return 	String con la salida de error del ultimo comando ejecutado
	 */
	public String getStreamError() {
		return streamError;
	}

	/**
	 * Metodo que devuelve los diferentes campos del comando almacenada
	 * @return 	String con los campos del comando almacenado
	 */
	public String getCommand() {
		return "Command[0]: " + this.command[0] + "\n" + "Command[1]:" + this.command[1] + "\n" + "Command[2]:"
				+ this.command[2] + "\n";
	}

//	public void setCommand(String[] command) {
//		this.command = command;
//	}

	private boolean ejecutar() {

		BufferedReader input = null;
		boolean error = false;
		String line, salida = "";

		try {
			Process pb = Runtime.getRuntime().exec(command);

			input = new BufferedReader(new InputStreamReader(pb.getErrorStream()));

			while ((line = input.readLine()) != null) {
				salida = salida + line + "\n";
				error = true;
			}
			System.out.print(salida);
			this.streamError = salida;

			input = new BufferedReader(new InputStreamReader(pb.getInputStream()));

			salida = "";
			while ((line = input.readLine()) != null) {
				salida = salida + line + "\n";
			}

			this.streamSalida = salida;

		} catch (IOException ioe) {
			System.out.println(command[2]);
			ioe.printStackTrace();
			error = true;
		} finally {
			if (input != null)
				try {
					input.close();
				} catch (Exception ignore) {
				}
		}

		return !error;
	}
	/**
	 * Metodo que devuelve los diferentes campos del comando almacenado asi como 
	 * las salidas stdout y stderr del ultimo comando ejecutado
	 * @return 	String con la situacion de todos los campos del objeto
	 */
	@Override
	public String toString() {
		return "command=" + Arrays.toString(command) + "\nstreamSalida=" + streamSalida + "\nstreamError=" + streamError
				+ "\n";
	}

}
