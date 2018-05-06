import java.io.*;
import java.net.*;

/**
 * The main application
 * @author 黒人間 kuroningen@ano.nymous.xyz
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
     * @return Application
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
    public static void main(String[] args) {
        getInstance().server.start(12345);
        getInstance().testClient.start(12345);
    }
}
