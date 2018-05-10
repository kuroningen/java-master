package kuroningen.javamaster;

import kuroningen.javamaster.exceptions.PeerException;
import kuroningen.javamaster.peer.Peer;
import kuroningen.javamaster.peer.PeerHandler;

import java.net.*;

/**
 * The main application
 * @author 黒人間 kuroningen@ano.nymous.xyz
 * @since  2018.05.06
 */
public class Application {

    /**
     * Server and client
     */
    private Peer _peer;

    /**
     * Singleton instance
     */
    private static Application instance;

    /**
     * Prevents public instantiation of this class
     * (This class is using singleton pattern)
     */
    private Application() {
        _peer = new Peer();
    }

    /**
     * Returns the singleton instance of this class
     * @return kuroningen.javamaster.Application
     */
    private static Application getInstance() {
        if (instance == null) {
            instance = new Application();
        }
        return instance;
    }

    /**
     * Start of application
     * @param args Command Line Arguments
     */
    public static void main(String[] args) throws UnknownHostException, PeerException, SocketException {
        getInstance()._peer.serve(new ServerHandler(), 12345);
    }

    /**
     * Class responsible for handling the server's actions based on peer's request.
     */
    private static class ServerHandler extends PeerHandler {
        /**
         * Method responsible for handling peer's request
         */
        @Override
        public void handle() {
            waitsFor("HELLO").replies("HI");
            waitsFor("ANNEONG").replies("ANNEONG");
            waitsFor("HELLO").replies("HI");
            waitsFor("ANNEONG").replies("ANNEONG");
            if (waitsFor("SHINE!").ifMet()) {
                closeSocket();
            }
        }
    }
}
