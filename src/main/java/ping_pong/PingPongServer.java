package ping_pong;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class PingPongServer {
    private static final int PORT = 8080;
    private List<PingPongHandler> pingPongHandlers = new ArrayList<>();

    public static void main(String[] args) {
        PingPongServer server = new PingPongServer();
        server.listen();
    }

    private void listen() {
        try (ServerSocket socket = new ServerSocket(PORT)) {
            while (true) {
                PingPongHandler client = new PingPongHandler(socket.accept(), new PingPongHandlerListener() {
                    @Override
                    public void handleMessage(String username, String message) {
                        for (PingPongHandler chatClientHandler : pingPongHandlers) {
                            chatClientHandler.send(">> " + username + ": " + message);
                        }
                    }

                    @Override
                    public void handleLogin(String username) {
                        for (PingPongHandler chatClientHandler : pingPongHandlers) {
                            chatClientHandler.send("New user logged in: " + username);
                        }
                    }
                });
                pingPongHandlers.add(client);
                client.start();

                if (client.isInterrupted()) {
                	break;
				}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
