package kuroningen.javamaster.interfaces;

import kuroningen.javamaster.peer.Peer;

/**
 * Abstract responsible for handling the client's request
 *
 * @author 黒人間 kuroningen@ano.nymous.xyz
 * @since 2018.05.06
 */
public interface PeerRequestHandler {

    /**
     * Method responsible for handling client's request
     * @param peer Peer to handle
     */
    void handle(Peer peer);
}
