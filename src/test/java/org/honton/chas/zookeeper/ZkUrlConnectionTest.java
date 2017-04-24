package org.honton.chas.zookeeper;

import com.google.common.io.CharStreams;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.apache.curator.test.TestingServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test zk url handling
 */
public class ZkUrlConnectionTest {

  private TestingServer zkTestServer;
  private CuratorFramework client;
  private String connectString;

  @BeforeClass
  public static void registerUrlHandler() {
    URL.setURLStreamHandlerFactory(new ZkUrlStreamHandlerFactory());
  }

  @Before
  public void startZookeeper() throws Exception {
    zkTestServer = new TestingServer();

    connectString = zkTestServer.getConnectString();
    client = CuratorFrameworkFactory.builder()
        .namespace("zk-testing")
        .connectString(connectString)
        .retryPolicy(new RetryOneTime(2000))
        .build();
    client.start();
    client.create().forPath("/path", "testvalue".getBytes(StandardCharsets.UTF_8));
  }

  @Test
  public void testFetchNode() throws Exception {
    try(InputStream is = new URL("zk://" + connectString + "/zk-testing/path").openStream()) {
      String actual = CharStreams.toString(new InputStreamReader(is, StandardCharsets.UTF_8));
      Assert.assertEquals("testvalue", actual);
    }
  }

  @After
  public void stopZookeeper() throws IOException {
    client.close();
    zkTestServer.stop();
  }
}
