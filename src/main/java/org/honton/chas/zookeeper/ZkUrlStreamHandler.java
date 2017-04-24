package org.honton.chas.zookeeper;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * Factory for ZkUrlConnection(s)
 */
public class ZkUrlStreamHandler extends URLStreamHandler {

  /**
   * Obtain the default ZooKeeper port
   * @return the default ZooKeeper port
   */
  @Override
  protected int getDefaultPort() {
    return 2181;
  }

  /**
   * Opens a connection to the ZooKeeper node
   *
   * @param zkUrl   the ZooKeeper URL.
   * @return a ZkUrlConnection
   */
  @Override
  protected URLConnection openConnection(URL zkUrl) {
    return new ZkUrlConnection(zkUrl);
  }
}
