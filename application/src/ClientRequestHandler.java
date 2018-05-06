
/**
 * Abstract responsible for handling the client's request
 *
 * @author 黒人間 kuroningen@ano.nymous.xyz
 * @since 2018.05.06
 */
interface ClientRequestHandler {

    /**
     * Method responsible for handling client's request
     * @param server Server to handle
     */
    void handle(Server server);
}
