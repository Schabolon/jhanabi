package hanabi.client.v2;

import java.io.IOException;
import java.io.ObjectInputStream;

import hanabi.message.IMessage;

public class ClientListener2 extends Thread {

	ObjectInputStream objectInputStream;
	Client2 client;
	boolean listenToServer = true;

	public ClientListener2(Client2 client) {
		this.client = client;
		objectInputStream = getObjectInputStream();
	}

	public void terminate() {
		listenToServer = false;
	}

	@Override
	public void run() {
		System.err.println("Started listening ...");
		while (listenToServer && !client.getSocket().isClosed()) {
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
			client.readMessage(msg);
		}
		try {
			objectInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private ObjectInputStream getObjectInputStream() {
		ObjectInputStream objectInputStream = null;
		try {
			objectInputStream = new ObjectInputStream(client.getSocket().getInputStream());
		} catch (IOException e) {
			System.out.println("Couldn't get ObjectInputStream");
			e.printStackTrace();
		}
		return objectInputStream;
	}
}
