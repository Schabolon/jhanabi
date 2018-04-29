package hanabi.client;

import java.net.Socket;

import hanabi.message.IMessage;



public interface IClient {

	public void sendMessage(IMessage msg);
	
	public void readMessage(IMessage msg);
	
	public boolean connect();
	
	public void listenToServer();
	
	public boolean disconnect();
	
	public Socket getSocket();
	
}
