# zookeeper-url
This project supports the 'zk' URL scheme so that you can instantiate a java.net.URL with 
a spec like "zk://host:port/node" and fetch the contents of the znode using
the URL's InputStream. 

### Requirements
* Minimum of Java 7
* Registration of ZkUrlHandler

## Maven Coordinates
To include zookeeper-url in your maven build, use the following fragment in your pom.
```xml
  <build>
    <dependencies>
      <dependency>
        <groupId>org.honton.chas.zookeeper</groupId>
        <artifactId>zookeeper-url</artifactId>
        <version>0.0.1-SNAPSHOT</version>
      </dependency>
      </dependencies>
  </build>
```

### Registration
Before creating a URL with a 'zk' scheme, the ZkUrlHandler must be registered.
This only needs to be done once per jvm instance. 
```java
  ZkUrlHandler.register();
```

## ZK scheme URL Support
Once ZkUrlHandler is registered, URLs with the 'zk' scheme are supported.

```java
    InputStream is = new URL("zk://node1/zk-testing/path").openStream();
```

