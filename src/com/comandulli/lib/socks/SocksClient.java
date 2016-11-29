package com.comandulli.lib.socks;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build.VERSION;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.nio.ByteBuffer;

/**
 * A SocksClient connects to a host in a specific port and starts sending data.
 *
 * @author <a href="mailto:caioa.comandulli@gmail.com">Caio Comandulli</a>
 * @since 1.0
 */
public class SocksClient {
    /**
     * If this client is active.
     */
    private boolean active = true;

    /**
     * Tell this client to start sending data to an specific host and port.
     *
     * @param host     that will receive data
     * @param port     to be used
     * @param callback transmission callback
     */
    @SuppressLint("NewApi")
    public void start(final String host, final int port, final SocksTransmission callback) {
        AsyncTask<Void, Void, Void> async = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                DatagramSocket dataSocket = null;
                try {
                    dataSocket = new DatagramSocket();
                    while (active) {
                        ByteBuffer buffer = callback.onTransmission();
                        byte[] array = buffer.array();
                        DatagramPacket dataPacket = new DatagramPacket(array, array.length, Inet4Address.getByName(host), port);
                        dataSocket.setBroadcast(true);
                        dataSocket.send(dataPacket);
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

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
            }
        };

        if (VERSION.SDK_INT >= 11) {
            async.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            async.execute();
        }
    }

    /**
     * Stop this client from sending data.
     */
    public void stop() {
        active = false;
    }
}
