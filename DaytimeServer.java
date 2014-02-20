package assignment;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

public class DaytimeServer {
	public static void main(String args[]) {
		try {
			CharsetEncoder encoder = Charset.forName("US-ASCII").newEncoder();

			// Read the port number from command line argument
			int port = 13; // default port.
			if (args.length > 0) {
				try {
					port = Integer.parseInt(args[0]);
				} catch (NumberFormatException e) {
					System.err.println("Argument" + " must be an integer");
					System.exit(1);
				}
			}

			SocketAddress localport = new InetSocketAddress(port);
			// TCP Channel
			ServerSocketChannel tcpserver = ServerSocketChannel.open();
			tcpserver.socket().bind(localport);
			tcpserver.configureBlocking(false);

			// UDP Channel
			DatagramChannel udpserver = DatagramChannel.open();
			udpserver.socket().bind(localport);
			udpserver.configureBlocking(false);

		} catch (Exception e) {
			System.err.println(e);
			System.exit(1);
		}
	}
}
