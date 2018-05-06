import java.io.IOException;
import java.net.*;
import java.util.Arrays;

/**
 * UDP Server
 * @author 黒人間 kuroningen@ano.nymous.xyz
 * @since  2018.05.06
 */
public class Server implements Runnable {

    /**
     * The UDP Socket
     */
    private DatagramSocket socket;

    /**
     * Received packet from client
     */
    private DatagramPacket receivedPacket;

    /**
     * Wait for string condition
     */
    private String waitForString;

    /**
     * Server Handler
     */
    private ClientRequestHandler clientRequestHandler;

    /**
     * Starts the server. This method should not be called directly.
     * This method is intended to be called by a thread (Multi Threading)
     * Use start instead.
     */
    @Override
    public void run() {
        do {
            try {
                waitForClient();
            } catch (SocketException e) {
                break; // Socket is closed
            }
            clientRequestHandler.handle(this);
        } while (true);
        System.out.println("The server has been closed.");
    }

    /**
     * Starts the server
     * @param port Port where to bind this connection
     * @param host Host where to bind this connection.
     */
    public void start(ClientRequestHandler clientRequestHandler, int port, String host) throws SocketException, UnknownHostException {
        this.clientRequestHandler = clientRequestHandler;
        createSocket(port, host);
        Thread t = new Thread(this);
        t.start();
    }

    /**
     * Starts the server. Uses 0.0.0.0 as IP address by default
     * @param port Port where to bind this connection
     */
    public void start(ClientRequestHandler clientRequestHandler, int port) throws SocketException, UnknownHostException {
        start(clientRequestHandler, port, "0.0.0.0");
    }

    /**
     * Creates the socket
     * @param port Port where to bind this connection
     * @param host Host where to bind this connection.
     * @throws UnknownHostException
     */
    private void createSocket(int port, String host) throws UnknownHostException, SocketException {
        socket = new DatagramSocket(port, InetAddress.getByName(host));
    }

    /**
     * Creates a packet from byte buffer
     * @param buffer
     * @return
     */
    private DatagramPacket createPacket(byte[] buffer) {
        return new DatagramPacket(buffer, buffer.length);
    }

    /**
     * Creates a packet from buffer and existing packet
     * @param buffer
     * @param packet
     * @return
     */
    private DatagramPacket createPacket(byte[] buffer, DatagramPacket packet) {
        return new DatagramPacket(buffer, buffer.length, packet.getAddress(), packet.getPort());
    }

    /**
     * Wait first for client (before doing anything else)
     */
    private void waitForClient() throws SocketException {
        byte[] buffer = new byte[4096];
        receivedPacket = createPacket(buffer);
        do {
            try {
                socket.receive(receivedPacket);
                return;
            } catch (SocketException e) {
                throw e;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (true);
    }

    /**
     * Waits for [condition] and executes appropriate action
     * @param condition  Condition needed to execute certain command, or response to client
     * @return Returns this instance
     */
    public Server waitsFor(String condition) {
        waitForString = condition;
        return this;
    }

    /**
     * If given condition is met, then returns true
     */
    public boolean ifMet() {
        System.out.println("IF-MET: " + new String(receivedPacket.getData()).trim());
        return waitForString.equalsIgnoreCase(new String(receivedPacket.getData()).trim());
    }

    /**
     * Replies to server when the given condition string is sent by the client
     * @param serverResponse  Response by the server
     * @return Returns TRUE if condition is met. If condition is not met, then it will return false
     */
    public boolean replies(String serverResponse) {
        if (!ifMet()) {
            return false;
        }
        try {
            socket.send(createPacket(serverResponse.getBytes(), receivedPacket));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Closes the socket server
     */
    public void die() {
        socket.close();
    }
}
