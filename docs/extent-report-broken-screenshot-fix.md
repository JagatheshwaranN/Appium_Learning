# Extent Reports: Broken Screenshot Image Issue & Fix

## The Problem

Screenshots were being captured and saved correctly to disk (via `driver.getScreenshotAs(OutputType.FILE)`), but they appeared **broken** inside the generated Extent HTML report.

### Root Cause

`extentTest.addScreenCaptureFromPath(path, name)` writes whatever string you pass **directly** into the report's `<img src="...">` tag. That path is resolved **relative to the location of the report HTML file itself** — not relative to your project root (`user.dir`) and not as an absolute OS path.

**Original (broken) code:**

```java
public String takeScreenshot(AppiumDriver driver, String screenshotName) {
    File sourceScreenFile = driver.getScreenshotAs(OutputType.FILE);
    String destinationScreenPath = System.getProperty("user.dir") + "//report//screenshot//" + screenshotName + ".png";
    FileUtils.copyFile(sourceScreenFile, new File(destinationScreenPath));
    return destinationScreenPath; // <-- absolute path, wrong for the report's img src
}
```

Reporter was initialized as:

```java
String path = System.getProperty("user.dir") + "//report//extent-report.html";
ExtentSparkReporter reporter = new ExtentSparkReporter(path);
```

Since `extent-report.html` lives in `.../Appium_Learning/report/`, returning a path like `report//screenshot//xxx.png` caused the browser to resolve it relative to that folder, producing a doubled path:

```
.../report/report/screenshot/xxx.png   <-- doesn't exist, so image shows broken
```

The actual file was correctly saved at `.../report/screenshot/xxx.png` — just not at the path the `<img>` tag pointed to.

---

## Fix Option 1 — Return a Relative Path (Applied Fix)

Write the file using an absolute path (safe for `FileUtils.copyFile`), but **return** a path relative to the report HTML's own folder.

```java
public String takeScreenshot(AppiumDriver driver, String screenshotName) {
    File sourceScreenFile = driver.getScreenshotAs(OutputType.FILE);

    // Physical write path — absolute, relative to project root (user.dir)
    String destinationScreenPath = System.getProperty("user.dir") + File.separator
            + "report" + File.separator + "screenshot" + File.separator + screenshotName + ".png";

    try {
        FileUtils.copyFile(sourceScreenFile, new File(destinationScreenPath));
    } catch (IOException e) {
        throw new RuntimeException(e);
    }

    // Path returned to Extent — relative to extent-report.html's own directory
    return "screenshot" + File.separator + screenshotName + ".png";
}
```

**Why it works:**
- `FileUtils.copyFile` needs the full absolute path to place the file correctly on disk.
- The string passed to `addScreenCaptureFromPath()` needs to be relative to wherever `extent-report.html` sits — since both `extent-report.html` and the `screenshot` folder live under `report/`, the relative path is just `screenshot/xxx.png`.

**Pros:** Lightweight HTML report (image files stay external, report file size stays small).
**Cons:** Report and screenshot folder must stay together and be viewed from the same relative location — won't render correctly if the report HTML is moved/opened independently of the `screenshot` folder, or opened from a different machine without copying both.

---

## Fix Option 2 — Embed Screenshot as Base64

Skip the file-path problem entirely by embedding the screenshot directly into the HTML report as a Base64 string.

```java
public void onTestFailure(ITestResult result) {
    extentTest.fail(result.getThrowable());
    try {
        driver = (AppiumDriver) result.getTestClass().getRealClass()
                .getField("driver").get(result.getInstance());

        String base64Screenshot = driver.getScreenshotAs(OutputType.BASE64);
        extentTest.fail("Screenshot",
                MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());

    } catch (IllegalAccessException | NoSuchFieldException e) {
        throw new RuntimeException(e);
    }
}
```

You can still keep a separate `takeScreenshot()` method that saves a copy to disk for archival purposes — it just isn't what gets embedded in the report.

**Pros:** No path resolution issues at all. Report is fully self-contained — works even if moved to another machine or shared as a single HTML file (e.g., attaching to an email or CI artifact).
**Cons:** Increases the report's HTML file size, especially with many screenshots.

---

## Recommendation

- Use **Base64 (Option 2)** for CI/CD pipelines or when the report needs to be portable/self-contained across machines.
- Use **relative path (Option 1)** if you want smaller report files and always keep the `report` folder intact together with `extent-report.html`.
