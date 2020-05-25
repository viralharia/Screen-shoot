package com.vharia.screenshoot.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.InputStream;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.compress.utils.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ScreenshotControllerUnitTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void testNoURL() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/screenshot/v1")).andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
    }

    @Test
    public void testInvalidURL() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/screenshot/v1?abc")).andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
    }

    @Test
    public void testScreenshot() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/screenshot/v1?url=http://wikipedia.org"))
                .andExpect(status().is(HttpStatus.OK.value())).andReturn();

        byte[] testResponseImage = mvcResult.getResponse().getContentAsByteArray();

        ClassPathResource resource = new ClassPathResource("wikipedia.png");
        InputStream in = resource.getInputStream();
        byte[] testImage = IOUtils.toByteArray(in);

        boolean isSame = Arrays.equals(testImage, testResponseImage);
        /*
         * System.out.println("isSame- " + isSame);
         * System.out.println(testResponseImage.length + "-" + testImage.length);
         */
        Assert.assertTrue(isSame);

    }

    @Test
    public void testScreenshotFail() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/screenshot/v1?url=http://www.google.com"))
                .andExpect(status().is(HttpStatus.OK.value())).andReturn();

        byte[] testResponseImage = mvcResult.getResponse().getContentAsByteArray();

        ClassPathResource resource = new ClassPathResource("wikipedia.png");
        InputStream in = resource.getInputStream();
        byte[] testImage = IOUtils.toByteArray(in);

        boolean isSame = Arrays.equals(testImage, testResponseImage);
        /*
         * System.out.println("isSame- " + isSame);
         * System.out.println(testResponseImage.length + "-" + testImage.length);
         */
        Assert.assertFalse(isSame);

    }

}