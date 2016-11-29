package com.comandulli.lib.socks;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * A SocksConnection represents a connection between a client and server.
 * <p>
 * If told to {@link #receive(SocksResponse)} , it will instantiate a SocksServer {@see com.comandulli.lib.socks.SocksServer} that will listen
 * for data in a port.
 * <p>
 * If told to {@link #transmit(String, SocksTransmission)} , it will instantiate a SocksClient {@see com.comandulli.lib.socks.SocksClient} that will transmit
 * data to a host in a port.
 *
 * @author <a href="mailto:caioa.comandulli@gmail.com">Caio Comandulli</a>
 * @since 1.0
 */
public class SocksConnection {
    /**
     * Port to be used.
     */
    private final int port;

    /**
     * Instantiates a connection with a port.
     *
     * @param port the port
     */
    public SocksConnection(int port) {
        this.port = port;
    }

    /**
     * Tell this connection to be at the receiving end.
     *
     * @param callback response callback {@see com.comandulli.lib.socks.SocksResponse}
     */
    public void receive(SocksResponse callback) {
        new SocksServer().start(port, callback);
    }

    /**
     * Tell this connection to be at the transmitting end.
     *
     * @param host     to be transmitted at
     * @param callback transmission callback {@see com.comandulli.lib.socks.SocksTransmission}
     */
    public void transmit(String host, SocksTransmission callback) {
        new SocksClient().start(host, port, callback);
    }

    /**
     * Utility method that finds this device's IP address.
     *
     * @param useIPv4 if it is IPv4
     * @return the found IP address
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface anInterface : interfaces) {
                List<InetAddress> addresses = Collections.list(anInterface.getInetAddresses());
                for (InetAddress address : addresses) {
                    if (!address.isLoopbackAddress()) {
                        String addressString = address.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(addressString);
                        boolean isIPv4 = addressString.indexOf(':') < 0;

                        if (useIPv4) {
                            if (isIPv4) {
                                return addressString;
                            }
                        } else {
                            if (!isIPv4) {
                                int delim = addressString.indexOf('%'); // drop ip6 zone suffix
                                return delim < 0 ? addressString.toUpperCase() : addressString.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ignored) {
        } // for now eat exceptions
        return "";
    }
}
