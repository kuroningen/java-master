package kuroningen.javamaster.peer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Abstract responsible for handling the client's request
 *
 * @author 黒人間 kuroningen@ano.nymous.xyz
 * @since 2018.05.06
 */
public abstract class PeerHandler {
    /**
     * Condition string
     */
    private String _conditionString;

    /**
     * Packet variable where peer data is stored when they request something
     */
    private DatagramPacket _packet;

    /**
     * Socket that we are handling
     */
    private DatagramSocket _socket;

    /**
     * Method responsible for handling peer's request
     */
    public abstract void handle();

    /**
     * Sets socket for this instance
     * @param socket  Socket that we are going to handle
     * @return  Returns this instance
     */
    PeerHandler setSocket(DatagramSocket socket)
    {
        _socket = socket;
        return this;
    }

    /**
     * Sets packet receiver
     * @param packet  Packet that receives data from peer
     * @return  Returns this class instance
     */
    PeerHandler setReceiverPacket(DatagramPacket packet) {
        _packet = packet;
        return this;
    }

    /**
     * Closes socket if requested
     */
    protected void closeSocket() {
        _socket.close();
    }

    /**
     * Checks whether the condition string matches with the packet sent by peer to this server
     * @return  Returns true if condition string matches with the received packet
     */
    public boolean ifMet() {
        return _conditionString.equalsIgnoreCase(new String(_packet.getData()).trim());
    }

    /**
     * Waits for [condition] and executes appropriate action
     * @param condition  Condition needed to execute certain command, or response to client
     * @return Returns this instance
     */
    protected PeerHandler waitsFor(String condition) {
        _conditionString = condition;
        return this;
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
            _socket.send(createPacket(serverResponse.getBytes(), _packet));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Creates packet to be sent to peer
     * @param buffer  Byte buffer that contains the data to be sent to the peer
     * @param packet  Packet that contains peer information (address and port)
     * @return  Returns packet that is to be sent through UDP Socket
     */
    private DatagramPacket createPacket(byte[] buffer, DatagramPacket packet) {
        return new DatagramPacket(buffer, buffer.length, packet.getAddress(), packet.getPort());
    }
}
