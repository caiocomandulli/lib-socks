package com.comandulli.lib.socks;

import java.nio.ByteBuffer;

/**
 * The interface for a SocksServer to receive a ByteBuffer from a SocksClient.
 *
 * @author <a href="mailto:caioa.comandulli@gmail.com">Caio Comandulli</a>
 * @since 1.0
 */
public interface SocksResponse {
    /**
     * Implement this function at a SocksServer to receive a ByteBuffer from a SocksClient.
     *
     * @param buffer received
     */
    void onResponse(ByteBuffer buffer);

}
