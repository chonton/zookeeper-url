package org.honton.chas.zookeeper;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * Factory for ZkUrlConnection(s)
 */
public class ZkUrlHandler extends URLStreamHandler {

  private static ZkUrlHandler SINGLETON;

  /**
   * Register a singleton instance with the jvm
   */
  public static void register() {
    synchronized (ZkUrlHandler.class) {
      if (SINGLETON == null) {
        SINGLETON = new ZkUrlHandler();
        UrlStreamHandlerRegistry.getInstance().registerURLStreamHandler("zk", SINGLETON);
      }
    }
  }

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
