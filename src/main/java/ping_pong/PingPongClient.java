package ping_pong;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Logger;

public class PingPongClient {

    private static final int PORT = 8030;
    private static final String SERVER_ADDRESS = "localhost";
    private final Logger logger = Logger.getLogger(PingPongClient.class.getName());

    public static void main(String[] args) throws Exception {

        final PingPongClient client = new PingPongClient();
        client.executar();
    }

    private void executar() throws Exception {

        int contador = 0;
        List<Long> temposList = new ArrayList<>();

        while (contador < 1000) {
            String comando = "ping";

            Long inicio = System.currentTimeMillis();

            handleComunicacao(comando);

            Long termino = System.currentTimeMillis();

            logger.info(String.format("Início: %s. Término: %s.", inicio, termino));
            logger.info(String.format("Média: %s.", termino - inicio));
            temposList.add(termino - inicio);
            contador++;
        }

        handleComunicacao("end");

        Collections.sort(temposList);

        Long menorTempo = temposList.get(0);
        Long maiorTempo = temposList.get(temposList.size() - 1);

        logger.info(String.format("Menor tempo: %s ms", menorTempo));
        logger.info(String.format("Maior tempo: %s ms", maiorTempo));

        double media = temposList.stream().mapToDouble(Long::doubleValue).average().orElse(0);

        if (media == 0) {
            logger.info("Não foi possível extrair a média.");
        }

        logger.info(String.format("Média: %s ms", media));
    }

    private String handleComunicacao(String comando) throws IOException {

        Socket connection = new Socket(SERVER_ADDRESS, PORT);
        Scanner serverInput = new Scanner(connection.getInputStream());
        PrintWriter serverOutput = new PrintWriter(connection.getOutputStream());

        serverOutput.println(comando);
        serverOutput.flush();

        String response = serverInput.nextLine();

        if (Objects.isNull(response)) {
            String vazio = "Vázio.";
            logger.info(vazio);
            connection.close();
            return vazio;
        }

        logger.info(String.format("Retorno: %s", response));
        connection.close();
        return response;
    }
}
