# Socks - Socket Datagram Communication in Android

Socks allow you to send byte data to a receiving end.
This allows very fast and continuous chunks of data to arrive at the receiver.

## Usage

### Transmitting Data

We transmit data by instantiating a `SocksConnection` and invoking `transmit(String, SocksTransmission)`.

````java
new SocksConnection(port).transmit(host, new SocksTransmission() {
    @Override
    public ByteBuffer requestTransmission() {
        return myByteBuffer;
    }
});
````

We choose a port and a host, then we define a `SocksTransmission`, 
the return of the implementation of `requestTransmission` will be
the data transmitted to the receiving end.

### Receiving Data

We receive data by instantiating a `SocksConnection` and invoking `receive(SocksResponse)`.

````java
new SocksConnection(port).receive(new SocksResponse() {
    @Override
    public void onResponse(final ByteBuffer myByteBuffer) {
        // handle myByteBuffer
    }
});
````

We choose a port to listen to, then we define a `SocksResponse`,
every time we receive the `ByteBuffer` sent by the other end 
we will invoke the implementation of `onResponse`.

### Handling the ByteBuffer

We instantiate a `ByteBuffer` by allocating memory with `allocate(int)`.
The parameter being the number of bytes allocated.

````java
ByteBuffer.allocate(9).put(a).putFloat(b).putFloat(c);
````

Then we put data up to our byte limit.
In the example we use `put(int)` to put an integer.

````java
int a = value.get();
float b = value.getFloat();
float c = value.getFloat();
````

When receiving a `ByteBuffer` we use the get methods to pull data.
Every get moves our buffer forward until we reach the buffer's limit.
In the example we use `get(int)` to pull an integer.

## Install Library

__Step 1.__ Get this code and compile it

__Step 2.__ Define a dependency within your project. For that, access to Properties > Android > Library and click on add and select the library

##  License

MIT License. See the file LICENSE.md with the full license text.

## Compatibility

This Library is valid for Android systems from version Android 4.4 (android:minSdkVersion="19" android:targetSdkVersion="19").