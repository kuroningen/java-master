package kuroningen.javamaster;

import kuroningen.javamaster.interfaces.ClientRequestHandler;

import java.net.*;

/**
 * The main application
 * @author 黒人間 kuroningen@ano.nymous.xyz
 * @since  2018.05.06
 */
public class Application {

    /**
     * Our Server
     */
    private Server server;

    /**
     * Test client
     */
    private TestClient testClient;

    /**
     * Singleton instance
     */
    private static Application instance;

    /**
     * Prevents public instantiation of this class
     * (This class is using singleton pattern)
     */
    private Application() {
        server = new Server();
        testClient = new TestClient();
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
    public static void main(String[] args) throws SocketException, UnknownHostException {
        getInstance().server.start(new RequestHandler(), 12345);
        getInstance().testClient.start(12345);
    }

    private static class RequestHandler implements ClientRequestHandler {

        /**
         * Method responsible for handling client's request
         * @param server Server to handle
         */
        @Override
        public void handle(Server server) {
            server.waitsFor("HELLO").replies("HI");
            server.waitsFor("ANNEONG").replies("ANNEONG");
            if (server.waitsFor("SHINE!").ifMet()) {
                server.die();
            }
        }
    }
}
