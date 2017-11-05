package com.moraes.undertow.js.templates;

import io.undertow.js.UndertowJS;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;
import io.undertow.testutils.DefaultServer;
import io.undertow.testutils.HttpClientUtils;
import io.undertow.testutils.TestHttpClient;
import io.undertow.util.StatusCodes;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.script.ScriptException;
import java.io.IOException;

@RunWith(DefaultServer.class)
public class ThymeleafTemplateTestCase {

    @BeforeClass
    public static void setup() throws ScriptException, IOException {

        final ClassPathResourceManager res =
                new ClassPathResourceManager(ThymeleafTemplateTestCase.class.getClassLoader());

        UndertowJS js = UndertowJS.builder()
                .addResources(res, "thymeleaf.js")
                .setResourceManager(res)
                .build();

        js.start();

        DefaultServer.setRootHandler(js.getHandler(new ResourceHandler(res,
                exchange -> exchange.getResponseSender().send("Default Response"))));
    }

    @Test
    public void testSimpleThymeleafTemplate() throws IOException {
        final TestHttpClient client = new TestHttpClient();
        try {
            HttpGet get = new HttpGet(DefaultServer.getDefaultServerURL() + "/testTemplate");
            HttpResponse result = client.execute(get);
            Assert.assertEquals(StatusCodes.OK, result.getStatusLine().getStatusCode());
            Assert.assertEquals("Template Data: Some Data", HttpClientUtils.readResponse(result));
            Assert.assertEquals("text/html; charset=UTF-8", result.getFirstHeader("Content-Type").getValue());
        } finally {
            client.getConnectionManager().shutdown();
        }
    }


}
