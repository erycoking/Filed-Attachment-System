package com.egerton.bugs.components;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PdfContent {

    private String templateName;
    private Map<String, Object> variables;

    public PdfContent() {
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }
}
