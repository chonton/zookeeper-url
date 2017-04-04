package org.honton.chas.zookeeper;

import java.net.URL;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A URLStreamHandlerFactory that allows registration of multiple schemes
 */
public class UrlStreamHandlerRegistry implements URLStreamHandlerFactory {

  private static final UrlStreamHandlerRegistry SINGLETON =  new UrlStreamHandlerRegistry();

  /**
   * Get the singleton instance of the registry/factory.
   * @return The singleton UrlStreamHandlerRegistry
   */
  public static UrlStreamHandlerRegistry getInstance() {
    return SINGLETON;
  }

  private final Map<String, URLStreamHandler> streamHandlers = new ConcurrentHashMap<>();

  /*
   * register self with URL and make constructor private to prevent multiple instances
   */
  private UrlStreamHandlerRegistry() {
    URL.setURLStreamHandlerFactory(this);
  }

  /**
   * Register a URLStreamHandler for a scheme
   * @param scheme The scheme to register
   * @param streamHandler The handler instance to register (must be thread-safe)
   * @return The prior handler registered for the scheme
   */
  public URLStreamHandler registerURLStreamHandler(String scheme, URLStreamHandler streamHandler) {
    return streamHandlers.put(scheme, streamHandler);
  }

  @Override
  public URLStreamHandler createURLStreamHandler(String scheme) {
    return streamHandlers.get(scheme);
  }
}
