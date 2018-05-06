import java.io.*;
import java.net.*;

public class Server implements Runnable {

    /**
     * Port where this server is going to be bound
     */
    private int port;

    /**
     * IP Address where this server is going to be bound (as string)
     */
    private String strIp;

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
        String clientSentence;
        String capitalizedSentence;
        try {
            // IP Address where this server is going to be bound
            InetAddress ip = InetAddress.getByName(this.strIp);
            // Number of connections queued (predefined)
            int backlog = 50;
            ServerSocket welcomeSocket = new ServerSocket(this.port, backlog, ip);
            while (true) {
                Socket connectionSocket = welcomeSocket.accept();
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                clientSentence = inFromClient.readLine();
                System.out.println("Received: " + clientSentence);
                capitalizedSentence = clientSentence.toUpperCase() + '\n';
                outToClient.writeBytes(capitalizedSentence);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }


    /**
     * Starts the server
     * @param port Port where to bind this connection
     * @param ip   IP where to bind this connection.
     */
    public void start(int port, String ip)
    {
        this.port = port;
        this.strIp = ip;
        Thread t = new Thread(this);
        t.start();
    }

    /**
     * Starts the server. Uses 0.0.0.0 as IP address by default
     * @param port Port where to bind this connection
     */
    public void start(int port)
    {
        this.start(port, "0.0.0.0");
    }
}
