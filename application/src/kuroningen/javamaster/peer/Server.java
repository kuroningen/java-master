package kuroningen.javamaster.peer;

import java.io.IOException;
import java.net.*;

/**
 * Class Server
 *
 * @author 黒人間 kuroningen@ano.nymous.xyz
 * @since 2018.05.09
 */
class Server implements Runnable {
    /**
     * Peer instance
     */
    private Peer _peer;

    /**
     * Server request handler
     */
    private PeerHandler _peerRequestHandler;

    /**
     * Binding address
     */
    private InetAddress _host;

    /**
     * Binding port
     */
    private int _port;

    /**
     * UDP Socket
     */
    private DatagramSocket _socket;

    /**
     * Received packet from client
     */
    private DatagramPacket _packet;

    /**
     * Creates new instance of server
     * @param peerRequestHandler     Server request handler
     * @param host                   Host where this server will bind to
     * @param port                   Port where this server will bind to
     * @throws UnknownHostException  If host is invalid, then this error shall be thrown.
     */
    Server(PeerHandler peerRequestHandler, String host, int port) throws UnknownHostException {
        _peerRequestHandler = peerRequestHandler;
        _host = InetAddress.getByName(host);
        _port = port;
    }

    /**
     * Starts the thread of this instance
     * @throws SocketException  Throws SocketException if something went wrong
     */
    void serve() throws SocketException {
        createSocket(_port, _host);
        (new Thread(this)).start();
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        byte[] buffer = new byte[4096];
        DatagramPacket packet = createPacket(buffer);
        do {
            try {
                packet = waitsRequestFromPeer(packet);
            } catch (SocketException e) {
                break; // Socket is closed
            }
            _peerRequestHandler.setSocket(_socket).setReceiverPacket(_packet).handle();
        } while (true);
        System.out.println("The server has been closed.");
    }

    /**
     * Waits request from peer
     * @param packet  Packet object that is used to receive packet from peer
     * @return  Returns packet from client if there is no error
     * @throws SocketException  Throws SocketException if something went wrong
     */
    private DatagramPacket waitsRequestFromPeer(DatagramPacket packet) throws SocketException {
        do {
            try {
                _socket.receive(packet);
                return packet;
            } catch (SocketException e) {
                throw e;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (true);
    }

    /**
     * Creates packet instance that will receive packet from peer
     * @param buffer  Fixed-length buffer that determines how big the packet is expected to be received
     * @return  Returns packet instance
     */
    private DatagramPacket createPacket(byte[] buffer) {
        return new DatagramPacket(buffer, buffer.length);
    }

    /**
     * Creates the socket
     * @param port  Port where to bind this connection
     * @param host  Host where to bind this connection.
     * @throws SocketException  Throws SocketException if something went wrong
     */
    private void createSocket(int port, InetAddress host) throws SocketException {
        _socket = new DatagramSocket(port, host);
    }
}
