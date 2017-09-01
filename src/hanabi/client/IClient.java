package hanabi.client;

import java.net.Socket;

import hanabi.IMessage;

public interface IClient {

	public void sendMessage(IMessage msg);
	
	public void readMessage(IMessage msg);
	
	public boolean connect(Socket socket);
	
	public boolean disconnect();
}
