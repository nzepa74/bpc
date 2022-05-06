package com.spring.project.development.auth;

/**
 * Created By zepaG on 6/15/2021.
 */

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
public class MySiteMeshFilter extends ConfigurableSiteMeshFilter {
    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        builder.addDecoratorPath("/*", "/layout/layout.jsp")
                .addDecoratorPath("/login", "/layout/login-layout.jsp")
                .addDecoratorPath("/forgotPassword", "/layout/login-layout.jsp")
                .addDecoratorPath("/resetPassword", "/layout/login-layout.jsp");
    }
}

