package com.comandulli.lib.socks;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build.VERSION;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;


/**
 * A SocksServer listens to a port a receives data.
 *
 * @author <a href="mailto:caioa.comandulli@gmail.com">Caio Comandulli</a>
 * @since 1.0
 */
public class SocksServer {
    /**
     * If this server is active.
     */
    private boolean active = true;

    /**
     * Tell this client to start receiving data at a specific port.
     *
     * @param port     to be listened
     * @param callback response callback
     */
    @SuppressLint("NewApi")
    public void start(final int port, final SocksResponse callback) {
        AsyncTask<Void, Void, Void> async = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                byte[] buffer = new byte[4096];
                DatagramPacket dataPacket = new DatagramPacket(buffer, buffer.length);
                DatagramSocket dataSocket = null;
                try {
                    dataSocket = new DatagramSocket(port);
                    while (active) {
                        dataSocket.receive(dataPacket);
                        callback.onResponse(ByteBuffer.wrap(buffer));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (dataSocket != null) {
                        dataSocket.close();
                    }
                }
                return null;
            }
        };
        if (VERSION.SDK_INT >= 11) {
            async.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            async.execute();
        }
    }

    /**
     * Stop this server from receiving data.
     */
    public void stop() {
        active = false;
    }
}
