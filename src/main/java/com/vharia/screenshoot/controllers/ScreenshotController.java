package com.vharia.screenshoot.controllers;

import java.io.IOException;

import com.vharia.screenshoot.services.ScreenshotService;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/screenshot/v1")
public class ScreenshotController {

    private final ScreenshotService screenshotService;

    public ScreenshotController(ScreenshotService screenshotService) {
        this.screenshotService = screenshotService;
    }

    @GetMapping(produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getScreenshot(@RequestParam("url") String url) throws IOException {

        byte[] bytes = screenshotService.getScreenshot(url);

        /*
         * ClassPathResource imgFile = new ClassPathResource("spacex.jpg"); byte[] bytes
         * = StreamUtils.copyToByteArray(imgFile.getInputStream());
         */

        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytes);

    }
}