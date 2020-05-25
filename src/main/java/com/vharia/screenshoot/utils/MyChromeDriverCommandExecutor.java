package com.vharia.screenshoot.utils;

import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.remote.CommandInfo;
import org.openqa.selenium.remote.http.HttpMethod;
import org.openqa.selenium.remote.service.DriverCommandExecutor;
import org.openqa.selenium.remote.service.DriverService;

/* 
    The Chrome driver has a new endpoint to directly call the DevTools API: 
   /session/:sessionId/chromium/send_command_and_get_result
   
   The Selenium API doesn't implement these commands, so this class adds this new command and is used in MyChromeDriver.java
   */
public class MyChromeDriverCommandExecutor extends DriverCommandExecutor {
    private static final ImmutableMap<String, CommandInfo> CHROME_COMMAND_NAME_TO_URL;

    static {
        CHROME_COMMAND_NAME_TO_URL = ImmutableMap.of("launchApp",
                new CommandInfo("/session/:sessionId/chromium/launch_app", HttpMethod.POST), "sendCommandWithResult",
                new CommandInfo("/session/:sessionId/chromium/send_command_and_get_result", HttpMethod.POST));
    }

    public MyChromeDriverCommandExecutor(DriverService service) {
        super(service, CHROME_COMMAND_NAME_TO_URL);
    }

    
}