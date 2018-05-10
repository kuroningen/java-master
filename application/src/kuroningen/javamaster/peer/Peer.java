package kuroningen.javamaster.peer;

import kuroningen.javamaster.exceptions.PeerException;

import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Class kuroningen.javamaster.peer.Peer
 *
 * @author 黒人間 kuroningen@ano.nymous.xyz
 * @since 2018.05.09
 */
public class Peer {
    /**
     * Server instance
     */
    private Server _server;

    /**
     * Client instance
     */
    private Client _client;

    /**
     * Uses peer as a server
     * @param serverRequestHandler  The server's request handler responsible for handling requests by connecting clients to this server
     * @param port                  Binds to specific port
     * @throws PeerException  If this peer instance is already a server,
     *                        this will throw a PeerException error.
     */
    public void serve(PeerHandler serverRequestHandler, int port) throws PeerException, UnknownHostException, SocketException {
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
    public void serve(PeerHandler serverRequestHandler, String host, int port) throws PeerException, UnknownHostException, SocketException {
        if (_server != null) {
            throw new PeerException("kuroningen.javamaster.peer.Peer is already a server");
        }
        _server = new Server(serverRequestHandler, host, port);
        _server.serve();
    }

}
