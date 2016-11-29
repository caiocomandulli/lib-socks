package com.comandulli.lib.socks;

import java.nio.ByteBuffer;

/**
 * The interface for a SocksClient to send a ByteBuffer to a SocksServer.
 *
 * @author <a href="mailto:caioa.comandulli@gmail.com">Caio Comandulli</a>
 * @since 1.0
 */
public interface SocksTransmission {
    /**
     * Implement this function at a SocksClient to send a ByteBuffer to a SocksServer.
     *
     * @return ByteBuffer to be sent.
     */
    ByteBuffer onTransmission();

}
