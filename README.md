# BotBase

## Maven Repository

**Repository**:

```
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```

**Dependency (type latest version in for version)**

```
<!-- BotBase / CodeBase -->
<dependency>
  <groupId>com.github.NegativeKB</groupId>
  <artifactId>BotBase</artifactId>
  <version>VERSION</version>
</dependency>
```

[![](https://jitpack.io/v/NegativeKB/BotBase.svg)](https://jitpack.io/#NegativeKB/BotBase)

## Building the Bot
From my experience, the Bot does not build correctly with all the dependancies until you add the following to your pom.xml:
```
    <build>
        <plugins>
            <plugin>
                <!-- Build an executable JAR -->
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <version>2.4</version>
                <configuration>
                    <archive>
                        <manifest>
                            <mainClass>MAIN CLASS</mainClass>
                        </manifest>
                    </archive>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>3.2.4</version>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                        <configuration>
                            <createDependencyReducedPom>false</createDependencyReducedPom>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```

## Code Examples
**Bot Class**
```JAVA
public class BotClassExample extends Bot {

    public BotClassExample() {
        super("ID HERE", "TOKEN HERE", "!", "help", Activity.watching("your mother"));

        setInstance(this);

        // Other stuff here
    }
}
```

**Main Class**
```JAVA
public class MainClass {

    public static void main(String[] args) {
        new BotClassExample();
    }

}
```
