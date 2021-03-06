package hanabi.server;

import hanabi.Player;
import hanabi.message.IMessage;

public interface IServer {

	/**
	 * sends a message back to a client
	 * 
	 * @param receiver
	 *            client to receive the message
	 * @param msg
	 *            message to send to the client
	 */
	void sendMessage(ClientThread receiver, IMessage msg);

	/**
	 * reads a message sent from a client
	 * 
	 * @param source
	 *            client that sent the message
	 * @param msg
	 *            message to process
	 */
	void readMessage(ClientThread source, IMessage msg);

	/**
	 * adds a certain player to the game
	 */
	void addPlayer(Player player, ClientThread thread);

}
