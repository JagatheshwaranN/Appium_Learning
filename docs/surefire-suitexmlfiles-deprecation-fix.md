# Maven Surefire: `suiteXmlFiles` Ignored on Version 3.6.0-M1 (All Tests Run Instead of TestNG Suite)

## The Problem

After correctly moving the `maven-surefire-plugin` declaration from `<pluginManagement>` to `<plugins>` inside each Maven profile (`Regression`, `Smoke`), running:

```
mvn test -PRegression
```

still ran **every test class in the project**, instead of only the tests listed in the configured TestNG suite file (`suite/testNG.xml`).

### pom.xml (profile configuration)

```xml
<profile>
    <id>Regression</id>
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.6.0-M1</version>
                <configuration>
                    <suiteXmlFiles>
                        <suiteXmlFile>suite/testNG.xml</suiteXmlFile>
                    </suiteXmlFiles>
                </configuration>
            </plugin>
        </plugins>
    </build>
</profile>
```

## Root Cause

This was **not** a `pluginManagement`/`plugins` merge issue — that fix was already correct. The real cause was the **Surefire plugin version**: `3.6.0-M1`.

Confirmed directly from the official Apache Maven Surefire plugin documentation (published for version 3.6.0-M1):

- `suiteXmlFiles` is officially marked:
  > **Deprecated.** Not supported after 3.6.0, please use `groups` or JUnit suite support.

- From the plugin's Usage page:
  > Since Surefire 3.6.0, all tests run via the JUnit Platform. The appropriate engine is automatically selected based on your dependencies — `junit:junit` (4.12+) runs via the Vintage Engine, `junit-jupiter-engine` runs via the Jupiter Engine, and `org.testng:testng` (6.14.3+) runs via the TestNG JUnit Platform Engine. Since the test framework dependency is required to compile the test classes anyway, no additional configuration is required.

**In short:** Starting with Surefire 3.6.0, TestNG execution moved to the JUnit Platform TestNG engine, and the classic `suiteXmlFiles` parameter is no longer honored. Surefire doesn't fail the build when it sees this now-unsupported config — it silently ignores it and falls back to default test auto-discovery, which picks up and runs *every* test class in the project. That's exactly the symptom observed.

## Fix Options

### Option 1 — Downgrade to a Surefire version that still supports `suiteXmlFiles` (Applied Fix)

Any version prior to the 3.6.0 line still honors `suiteXmlFiles` normally. **Version 3.3.0** was confirmed working:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.3.0</version>
    <configuration>
        <suiteXmlFiles>
            <suiteXmlFile>suite/testNG.xml</suiteXmlFile>
        </suiteXmlFiles>
    </configuration>
</plugin>
```

Apply this version change to both the `Regression` and `Smoke` profiles.

**Pros:** Minimal change — one version bump per profile, no changes to test classes or annotations needed.
**Cons:** Pins the project to an older Surefire release; `suiteXmlFiles`-based TestNG suite XML support will need to be migrated away from eventually, since it's deprecated going forward.

### Option 2 — Migrate off `suiteXmlFiles` to `groups` (future-proof, more work)

For long-term compatibility with Surefire 3.6.0+, move suite selection to TestNG groups instead of suite XML files:

1. Annotate test classes/methods with the relevant group:
   ```java
   @Test(groups = {"regression"})
   public class PlaceOrderTest { ... }
   ```
   ```java
   @Test(groups = {"smoke"})
   public class ToastMessageTest { ... }
   ```

2. Reference the group in the surefire configuration instead of a suite XML file:
   ```xml
   <configuration>
       <groups>regression</groups>
   </configuration>
   ```

**Pros:** Compatible with current and future Surefire versions (3.6.0+); no version pinning required.
**Cons:** Requires annotating every relevant test class/method with the correct group — a larger refactor across the test suite.

## How to Find Valid Surefire Plugin Versions

- **Maven Central (authoritative, always current):**
  `https://central.sonatype.com/artifact/org.apache.maven.plugins/maven-surefire-plugin/versions`
- **mvnrepository.com (easier to browse):**
  `https://mvnrepository.com/artifact/org.apache.maven.plugins/maven-surefire-plugin`
- **From the command line:**
  ```
  mvn versions:display-plugin-updates
  ```

## Recommendation

- Use **Option 1** (version pin to `3.3.0`) for now — it's the immediate, low-risk fix and matches the confirmed working version.
- Consider **Option 2** (migrate to `groups`) as a planned follow-up if the project intends to upgrade to Surefire 3.6.0+ later, since `suiteXmlFiles` support is being phased out going forward.
