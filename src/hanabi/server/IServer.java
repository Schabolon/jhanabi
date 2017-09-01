package hanabi.server;

import hanabi.IMessage;

public interface IServer {

	public void sendMessage(IMessage msg);
	
	public void readMessage(IMessage msg);
	
}
