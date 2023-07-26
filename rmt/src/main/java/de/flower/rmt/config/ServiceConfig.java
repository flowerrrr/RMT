package de.flower.rmt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = {
        "de.flower.common.service",
        "de.flower.rmt.service"
})
public class ServiceConfig {

    @Value("${mail.smtp.host}")
    private String host;

    @Value("${mail.smtp.username}")
    private String username;

    @Value("${mail.smtp.password}")
    private String password;

    @Value("${mail.default.sender}")
    private String from;

    @Value("${mail.default.replyto}")
    private String replyTo;

    @Value("${app.title}")
    private String appTitle;

    @Value("${admin.address}")
    private String adminAddress;

    @Value("${app.url}")
    private String appUrl;

    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(587);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.transport.protocol", "smtp");
        // required for using authentication. simply setting username is not sufficient.
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.debug", "false");
        javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");

        mailSender.setJavaMailProperties(javaMailProperties);

        return mailSender;
    }

    /**
     * this is a template message that we can pre-load with default state.
     */
    @Bean
    public SimpleMailMessage templateMessage() {
        SimpleMailMessage templateMessage = new SimpleMailMessage();
        templateMessage.setFrom(from);
        templateMessage.setReplyTo(replyTo);

        return templateMessage;
    }

    /**
     * velocity engine for rendering emails, sms.
     */
    @Bean
    public VelocityEngineFactoryBean velocityEngine() {
        VelocityEngineFactoryBean velocityEngine = new VelocityEngineFactoryBean();
        velocityEngine.setResourceLoaderPath("classpath:/de/flower/rmt/service/mail/templates");
        // needed for testing with classpath templates that reside in different file locations but same package.
        velocityEngine.setPreferFileSystemAccess(false);

        Properties velocityProperties = new Properties();
        // specify the file encoding used in our templates.
        velocityProperties.put("input.encoding", "UTF-8");
        // raise exception when referencing null values - helps debugging.
        velocityProperties.put("runtime.references.strict", "true");
        velocityEngine.setVelocityProperties(velocityProperties);

        return velocityEngine;
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/de/flower/rmt/ui/app/RMTApplication");
        // only rarely used so reloading bundle should not impact performance
        messageSource.setCacheSeconds(10);

        return messageSource;
    }

    @Bean
    public MessageSourceAccessor messageSourceAccessor() {
        return new MessageSourceAccessor(messageSource());
    }

    /**
     * defaults for template service.
     */
    @Bean
    public Map<String, String> templateDefaults() {
        Map<String, String> templateDefaults = new HashMap<>();
        templateDefaults.put("appTitle", appTitle);
        templateDefaults.put("adminAddress", adminAddress);
        templateDefaults.put("appUrl", appUrl);
        return templateDefaults;
    }
}
