package WarOfMage;

import java.io.IOException;
import java.net.Socket;

public abstract class Net {
	public Socket socket;
	public Boolean isServer;
	public String port = "0";

	public Net() {

	}

	public abstract void TCPDeliver(String input) throws IOException;

	public abstract void UDPDeliver(String input) throws IOException;

	public abstract void Receive();

	public abstract void stopServerSocket();

	public abstract void startBuildUDP();
}
