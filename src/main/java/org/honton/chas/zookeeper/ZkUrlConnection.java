package org.honton.chas.zookeeper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

/**
 * Simple URLConnection to ZooKeeper
 */
public class ZkUrlConnection extends URLConnection {

  private static final int SESSION_TIMEOUT = (int) TimeUnit.MINUTES.toMillis(5);

  private ZooKeeper zooKeeper;

  /**
   * Constructs a URL connection to the specified URL. A connection to
   * the object referenced by the URL is not created.
   *
   * @param url the specified URL.
   */
  protected ZkUrlConnection(URL url) {
    super(url);
  }

  /**
   * Opens communications to the ZooKeeper referenced by this
   * URL, if such a connection has not already been established.
  */
  @Override
  public void connect() throws IOException {
    synchronized (this) {
      if( !connected) {
        connected = true;
        zooKeeper = new  ZooKeeper(getConnectString(), SESSION_TIMEOUT, null);
      }
    }
  }

  private String getConnectString() {
    return url.getHost() + ':' + (url.getPort()<0 ?url.getDefaultPort() :url.getPort());
  }

  /**
   * Returns an input stream that reads from ZooKeeper.
   *
   * @return an InputStream that reads from ZooKeeper.
   * @throws IOException if an I/O error occurs while
   *               creating the input stream.
   */
  @Override
  public InputStream getInputStream() throws IOException {
    connect();
    byte[] data = new byte[0];
    try {
      data = zooKeeper.getData(url.getPath(), false, null);
    } catch (KeeperException e) {
      throw new IOException(e);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      InterruptedIOException iie = new InterruptedIOException();
      iie.initCause(e);
      throw iie;
    }
    return new ByteArrayInputStream(data);
  }
}
