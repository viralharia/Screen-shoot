package com.vharia.screenshoot.services;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.vharia.screenshoot.exceptions.ScreenShotServiceException;
import com.vharia.screenshoot.utils.MyChromeDriver;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.ScreenshotException;
import org.springframework.stereotype.Service;

import io.github.bonigarcia.wdm.WebDriverManager;

@Service
public class ScreenshotService {

    public ScreenshotService() {

        WebDriverManager.chromedriver().setup();
    }

    public byte[] getScreenshot(final String url) {
        System.out.println("url - " + url);

        final MyChromeDriver driver = new MyChromeDriver(getChromeOptions());

        try {
            // Navigate to the specified url
            driver.get(url);

            final byte[] bytes = takeScreenshot(driver);

            return bytes;

        } catch (final Exception e) {
            throw new ScreenShotServiceException("error in taking screenshot", e);
        } finally {
            driver.quit();
        }
    }

    private ChromeOptions getChromeOptions() {
        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("start-maximized");

        return chromeOptions;
    }

    public byte[] takeScreenshot(final MyChromeDriver driver) throws Exception {

        // getting the actual content's width and height
        final Object contentSize = send(driver, "Page.getLayoutMetrics", new HashMap<>());
        final Long contentWidth = jsonValue(contentSize, "contentSize.width", Long.class);
        final Long contentHeight = jsonValue(contentSize, "contentSize.height", Long.class);

        // setting the device's width and height to the actual content's width & height
        send(driver, "Emulation.setDeviceMetricsOverride",
                ImmutableMap.of("width", contentWidth, "height", contentHeight, "deviceScaleFactor", Long.valueOf(1),
                        "mobile", Boolean.FALSE, "fitWindow", Boolean.FALSE));

        send(driver, "Emulation.setVisibleSize", ImmutableMap.of("width", contentWidth, "height", contentHeight));

        // sending the command to take screenshot - returns : Base64-encoded image data.
        final Object base64EncodedImageData = send(driver, "Page.captureScreenshot",
                ImmutableMap.of("format", "png", "fromSurface", Boolean.TRUE));

        final String imageData = jsonValue(base64EncodedImageData, "data", String.class);
        final byte[] bytes = Base64.getDecoder().decode(imageData);

        return bytes;

    }

    // This method creates the Command object and sends it to Chrome
    private Object send(final MyChromeDriver driver, final String cmd, final Map<String, Object> params)
            throws IOException {
        final Map<String, Object> exe = ImmutableMap.of("cmd", cmd, "params", params);
        final Command xc = new Command(driver.getSessionId(), "sendCommandWithResult", exe);
        final Response response = driver.getCommandExecutor().execute(xc);

        final Object value = response.getValue();
        if (response.getStatus() == null || response.getStatus().intValue() != 0) {
            System.out.println("Command - " + cmd + "failed - " + value);
            // throw new MyChromeDriverException("Command '" + cmd + "' failed: " + value);
        }
        if (null == value) {
            // throw new MyChromeDriverException("Null response value to command '" + cmd +
            // "'");
            System.out.println("Null response value to command - " + cmd);
        }

        return value;
    }

    static private <T> T jsonValue(final Object map, final String path, final Class<T> type) {
        final String[] segs = path.split("\\.");
        Object current = map;
        for (final String name : segs) {
            final Map<String, Object> cm = (Map<String, Object>) current;
            final Object o = cm.get(name);
            if (null == o)
                return null;
            current = o;
        }
        return (T) current;
    }

}