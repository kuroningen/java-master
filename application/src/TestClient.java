import java.io.*;
import java.net.*;

/**
 * Test Client
 * @author 黒人間 kuroningen@ano.nymous.xyz
 * @since  2018.05.06
 */
public class TestClient implements Runnable {

    /**
     * Port where this client will be connecting to
     */
    private int port;

    /**
     * Host/IP Address where this client will connect to
     */
    private InetAddress host;

    /**
     * UDP Socket
     */
    private DatagramSocket socket;

    /**
     * Starts the client. This method should not be called directly.
     * This method is intended to be called by a thread (Multi Threading)
     * Use start instead.
     */
    @Override
    public void run() {
        sendToServer("HELLO");
        sendToServer("ANNEONG");
        sendToServer("SHINE!");
    }

    /**
     * Starts the server
     * @param port Port where to bind this connection
     * @param host Host where to bind this connection.
     */
    public void start(int port, String host) throws UnknownHostException, SocketException {
        this.port = port;
        this.host = InetAddress.getByName(host);
        this.socket = new DatagramSocket();
        Thread t = new Thread(this);
        t.start();
    }

    /**
     * Starts the server. Uses 0.0.0.0 as IP address by default
     * @param port Port where to bind this connection
     */
    public void start(int port) throws SocketException, UnknownHostException {
        this.start(port, "localhost");
    }

    /**
     * Sends the following request to server
     * @param request
     */
    private void sendToServer(String request)
    {
        DatagramPacket packet = new DatagramPacket(request.getBytes(), request.getBytes().length, this.host, this.port);
        try {
            socket.send(packet);
            System.out.printf("Client: %s\n", request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        packet = new DatagramPacket(new byte[4096], 4096);
        try {
            socket.receive(packet);
            String response = new String(packet.getData(), 0, packet.getLength());
            System.out.printf("Server: %s\n", response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the connection between server and this client
     */
    private void die()
    {
        socket.close();
    }
}
