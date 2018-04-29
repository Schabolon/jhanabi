package hanabi.ai;

import java.io.IOException;
import java.io.ObjectInputStream;

import hanabi.message.IMessage;

public class BotListener extends Thread {

	private Bot bot;
	private boolean listenToServer = true;
	private ObjectInputStream objectInputStream;

	public BotListener(Bot bot) {
		this.bot = bot;
		objectInputStream = getObjectInputStream();
	}

	@Override
	public void run() {
		while (listenToServer && !bot.getSocket().isClosed()) {
			Object object = null;
			try {
				object = objectInputStream.readObject();
			} catch (ClassNotFoundException e) {
				System.out.println("The class received from the server was not found");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("An error occured while listening to the server");
				e.printStackTrace();
			}
			IMessage msg = (IMessage) object;
			bot.readMessage(msg);
		}
	}

	private ObjectInputStream getObjectInputStream() {
		ObjectInputStream objectInputStream = null;
		try {
			objectInputStream = new ObjectInputStream(bot.getSocket().getInputStream());
		} catch (IOException e) {
			System.out.println("Couldn't get ObjectInputStream");
			e.printStackTrace();
		}
		return objectInputStream;
	}

	public void terminate() {
		listenToServer = false;
	}

}
