package com.moraes.undertow.js.templates.undertow.js.templates;

import io.undertow.js.templates.Template;
import io.undertow.js.templates.TemplateProvider;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;
import java.util.Map;

public class ThymeleafTemplateProvider implements TemplateProvider {

    private final Locale locale = Locale.getDefault();
    private final TemplateEngine engine = new TemplateEngine();

    public String name() {
        return "thymeleaf";
    }

    public void init(Map<String, String> properties) {

    }

    public Template compile(String templateName, String templateContents) {
        return data -> {
            final Context context = new Context(locale, (Map<String, Object>) data);
            return engine.process(templateContents, context);
        };
    }

    public void close() {

    }
}
