package york;

// AKS 2020 York Online Masters
// --------TCPClient-----------
// Socket Communications - TCP
import java.io.*;
import java.net.*;

class TCPClient {
	public static void main(String args[]) throws Exception {
		Socket socket = new Socket("localhost", 9876);
		OutputStream output = socket.getOutputStream();
		PrintWriter writer = new PrintWriter(output, true);
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		String sentence = inFromUser.readLine();
		writer.println(sentence);

		InputStream input = socket.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		String line = reader.readLine();
		System.out.println("FROM SERVER: " + line);
		socket.close();	
	}
}
