import java.net.*;
import java.io.*; 

/**
 * Threaded Echo Server, pre-allocation schema.
 * @author Ian F. Darwin.
 */
public class EchoServerThreaded2 {

	public static final int PORT = 7;

	public static final int NUM_THREADS = 4;

	/** Main method, to start the servers. */
	public static void main(String[] av)
	{
		new EchoServerThreaded2(PORT, NUM_THREADS);
	}

	/** Constructor */
	public EchoServerThreaded2(int port, int numThreads)
	{
		ServerSocket servSock;
		Socket clientSocket;

		try {
			servSock = new ServerSocket(PORT);
		
		} catch(IOException e) {
			/* Crash the server if IO fails. Something bad has happened */
			System.err.println("Could not create ServerSocket " + e);
			System.exit(1);
			return;	/*NOTREACHED*/
		}
		for (int i=0; i<numThreads; i++) {
			new Thread(new Handler(servSock, i)).start();
		}
	}

	/** A Thread subclass to handle one client conversation. */
	class Handler extends Thread {
		ServerSocket servSock;
		int threadNumber;

		/** Construct a Handler. */
		Handler(ServerSocket s, int i) {
			super();
			servSock = s;
			threadNumber = i;
		}

		/** Like Thread.getName(), which is alas final */
		public String myName() {
			return "Thread " + threadNumber;
		}

		public void run() 
		{
			/* Wait for a connection */
			while (true){
				try {
					System.out.println( myName() + " waiting");
					Socket clientSocket = servSock.accept();
					System.out.println(myName() + " starting, IP=" + 
						clientSocket.getInetAddress());
					DataInputStream is = new DataInputStream(
						clientSocket.getInputStream());
					PrintStream os = new PrintStream(
						clientSocket.getOutputStream(), true);
					String line;
					while ((line = is.readLine()) != null) {
						// System.out.println(">> " + line);
						os.print(line + "\r\n");
						os.flush();
					}
					System.out.println(myName() + " ENDED ");
					clientSocket.close();
				} catch (IOException ex) {
					System.out.println(myName() + ": IO Error on socket " + ex);
					return;
				}
			}
		}
	}
}
