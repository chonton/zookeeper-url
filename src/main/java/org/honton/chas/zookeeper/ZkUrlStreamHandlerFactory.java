package org.honton.chas.zookeeper;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

/**
 * Factory for ZkUrlStreamHandler
 */
public class ZkUrlStreamHandlerFactory implements URLStreamHandlerFactory {

  @Override
  public URLStreamHandler createURLStreamHandler(String protocol) {
    return protocol.equals("zk") ? new ZkUrlStreamHandler() : null;
  }
}
