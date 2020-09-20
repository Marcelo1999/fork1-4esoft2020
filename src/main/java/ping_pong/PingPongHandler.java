package ping_pong;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

public class PingPongHandler extends Thread {

    private final Socket socket;
    private final Logger logger = Logger.getLogger(PingPongHandler.class.getName());
    private PrintWriter output;

    public PingPongHandler(Socket socket) {

        logger.info("Cliente conectado.");
        this.socket = socket;
    }

    public void run() {

        try {
            output = new PrintWriter(socket.getOutputStream());
            Scanner input = new Scanner(socket.getInputStream());
            String mensagem = "";

            while (!mensagem.equalsIgnoreCase("end")) {
                mensagem = input.nextLine();
                logger.info(String.format("Nova mensagem: %s", mensagem));
                handleMensagem(mensagem);
            }

            if (mensagem.equalsIgnoreCase("end")) {
                encaminharMensagem("Finalizando conexão.");
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleMensagem(String comando) throws IOException {

        logger.info(String.format("Processando novo comando: %s", comando));

        if (comando.equalsIgnoreCase("ping")) {
            encaminharMensagem("pong");
        }

        if (comando.equalsIgnoreCase("end")) {
            encaminharMensagem("Finalizando conexão.");
            socket.close();
        } else {
            encaminharMensagem("Comando desconhecido.");
        }
    }

    public void encaminharMensagem(String mensagem) {

        try {
            output.println(mensagem);
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}