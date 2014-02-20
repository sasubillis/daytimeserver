package assignment;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
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

			// Block the channel while waiting actively for data
			Selector selector = Selector.open();
			tcpserver.register(selector, SelectionKey.OP_ACCEPT);
			udpserver.register(selector, SelectionKey.OP_READ);

			// Create buffer to receive udp inet adderss
			ByteBuffer receiveBuffer = ByteBuffer.allocate(0);

			// infinite loop waiting for connections
			for (;;) {
				try {
					selector.select(); // blocking call
					// Prepare the daytime server response to connected client
					String date = new java.util.Date().toString() + "\r\n";
					ByteBuffer response = encoder.encode(CharBuffer.wrap(date));

				} catch (java.io.IOException e) {
					System.err.println(e);
				}
			}

		} catch (Exception e) {
			System.err.println(e);
			System.exit(1);
		}
	}
}
