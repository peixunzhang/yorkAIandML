// ASK 2020 York Online Masters
// ---------TCPServer ---------
//  Socket communications TCP
package york;

import java.io.*;
import java.net.*;

class TCPServer {
	public static void main(String args[]) throws Exception {
		ServerSocket serverSocket = new ServerSocket(9876);
		System.out.println("Server Running");

		try {
			while (true) {
				Socket socket = serverSocket.accept();
				InputStream input = socket.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(input));
				String sentence = reader.readLine();
				System.out.println("RECEIVED: " + sentence);
				OutputStream output = socket.getOutputStream();
				PrintWriter writer = new PrintWriter(output, true);
				String capitalizedSentence = sentence.toUpperCase();
				writer.println(capitalizedSentence);
				socket.close();
			}
		} finally {
			serverSocket.close();
		}
	}
}
