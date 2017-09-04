package hanabi.server;

import hanabi.IMessage;
import hanabi.Player;

public interface IServer {

	void sendMessage(ClientThread reciever, IMessage msg);

	void readMessage(ClientThread source, IMessage msg);

	void addPlayer(Player player, ClientThread thread);

}
