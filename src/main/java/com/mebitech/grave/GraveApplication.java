package com.mebitech.grave;

import com.mebitech.grave.cli.GraveInitializeCommand;
import com.mebitech.grave.conf.GraveConfiguration;
import io.dropwizard.servlets.CacheBustingFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.robe.admin.RobeApplication;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class GraveApplication extends RobeApplication<GraveConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GraveApplication.class);

    public static void main(String[] args) throws Exception {
        GraveApplication application = new GraveApplication();
        application.run(args);
    }

    @Override
    public void initialize(Bootstrap<GraveConfiguration> bootstrap) {
        super.initialize(bootstrap);
        bootstrap.addCommand(new GraveInitializeCommand(this, "init", "Runs Hibernate and initialize required columns"));
    }

    @Override
    protected String[] getHibernateScanPackages() {
        return new String[]{"io.robe.admin", "io.robe.quartz", "com.mebitech"};
    }


    @Override
    public void run(GraveConfiguration configuration, Environment environment) throws Exception {
        super.run(configuration, environment);

        // Enable CORS headers
        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD,PATCH");
        cors.setInitParameter("exposedHeaders", "X-Total-Count");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        environment.servlets().addFilter("CacheBustingFilter", new CacheBustingFilter())
                .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
    }
}
