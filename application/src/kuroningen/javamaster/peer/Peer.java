package kuroningen.javamaster.peer;

import kuroningen.javamaster.exceptions.PeerException;
import kuroningen.javamaster.interfaces.PeerRequestHandler;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Class kuroningen.javamaster.peer.Peer
 *
 * @author 黒人間 kuroningen@ano.nymous.xyz
 * @since 2018.05.09
 */
public class Peer implements Runnable {
    /**
     * Marks true if peer acts as server
     */
    private boolean _isServer = false;

    /**
     * Marks true if peer acts as client
     */
    private boolean _isClient = false;

    /**
     * Server request handler
     */
    private PeerRequestHandler serverRequestHandler;

    /**
     * Client request handler
     */
    private PeerRequestHandler clientRequestHandler;

    /**
     * Uses peer as a server
     * @param serverRequestHandler  The server's request handler responsible for handling requests by connecting clients to this server
     * @param port                  Binds to specific port
     * @throws PeerException  If this peer instance is already a server,
     *                        this will throw a PeerException error.
     */
    public void serve(PeerRequestHandler serverRequestHandler, int port) throws PeerException {
        this.serve(serverRequestHandler, "0.0.0.0", port);
    }

    /**
     * Uses peer as a server
     * @param serverRequestHandler  The server's request handler responsible for handling requests by connecting clients to this server
     * @param host                  Binds to specific host
     * @param port                  Binds to specific port
     * @throws PeerException  If this peer instance is already a server,
     *                        this will throw a PeerException error.
     */
    public void serve(PeerRequestHandler serverRequestHandler, String host, int port) throws PeerException {
        if (this._isServer) {
            throw new PeerException("kuroningen.javamaster.peer.Peer is already a server");
        }
        this.serverRequestHandler = serverRequestHandler;
        this._isServer = true;
        this.serverSocket = new DatagramSocket(port, InetAddress.getByName(host));
        Thread t = new Thread(this);
        t.start();
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
}
