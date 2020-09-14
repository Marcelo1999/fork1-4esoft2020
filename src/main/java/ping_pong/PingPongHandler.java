package ping_pong;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

public class PingPongHandler extends Thread {
	private Socket socket;
	private String username;
	private PingPongHandlerListener listener;
	private PrintWriter output;
	private Logger logger = Logger.getLogger(PingPongHandler.class.getName());
			
	public PingPongHandler(Socket socket, PingPongHandlerListener listener) {
		logger.info("New user connecting...");
		this.socket = socket;
		this.listener = listener;
	}

	//"command<<<>>>body"
	//"login<<<>>>fulana.de.almeida"
	//"message<<<>>>Olá pessoal, boa tarde!"
	public void run() {
		try  {
			output = new PrintWriter(socket.getOutputStream());
			Scanner input = new Scanner(socket.getInputStream());			
			while (true) {
				final String message = input.nextLine();
				final String[] messageArray = message.split("<<<>>>");
				logger.info("New message: [" + message + "]");
				final String command = messageArray[0];
				final String body = messageArray[1];
				handleMessage(command, body);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleMessage(String command, String body) {
		logger.info("Handling new message: command: " + command + ", body: " + body);
		if (command.equalsIgnoreCase("login")) {
			this.username = body;
			listener.handleLogin(body);
		} else if (command.equalsIgnoreCase("message")) {
			listener.handleMessage(username, body);
		}		
	}

	public void send(String message) {
		try {
			output.println(message);
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
