package com.prototype;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class TestProperties {

    @org.junit.Before
    public void setUp() throws Exception {
    }

    @org.junit.Test
    public void setGet() throws IOException, ClassNotFoundException {

        Properties prop = new Properties();

        String string = "hosts=localhost:9500, localhost:9500";

        InputStream stream = new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));

        prop.load(stream);

        System.out.println(prop.getProperty("hosts"));


    }
}