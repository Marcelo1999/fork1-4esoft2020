package ping_pong;

public interface PingPongHandlerListener {
	
	void handleLogin(String username);
	void handleMessage(String username, String message);

}
