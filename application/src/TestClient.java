import java.io.*;
import java.net.*;

public class TestClient implements Runnable {

    /**
     * Port where this client will be connecting to
     */
    private int port;

    /**
     * Host/IP Address where this client will connect to
     */
    private String host;

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see     java.lang.Thread#run()
     */
    @Override
    public void run() {
        try {
            String sentence;
            String modifiedSentence;
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            Socket clientSocket = new Socket(this.host, this.port);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            sentence = inFromUser.readLine();
            outToServer.writeBytes(sentence + '\n');
            modifiedSentence = inFromServer.readLine();
            System.out.println("FROM SERVER: " + modifiedSentence);
            clientSocket.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    /**
     * Starts the server
     * @param port Port where to bind this connection
     * @param host Host where to bind this connection.
     */
    void start(int port, String host)
    {
        this.port = port;
        this.host = host;
        Thread t = new Thread(this);
        t.start();
    }

    /**
     * Starts the server. Uses 0.0.0.0 as IP address by default
     * @param port Port where to bind this connection
     */
    void start(int port)
    {
        this.start(port, "0.0.0.0");
    }
}
