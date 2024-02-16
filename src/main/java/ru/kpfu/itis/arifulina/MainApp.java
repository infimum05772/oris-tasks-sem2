package ru.kpfu.itis.arifulina;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.io.IOException;

public class MainApp {
    private static final String CONTEXT_PATH = "";
    private static final String APP_BASE = ".";
    private static final int PORT = port();
    private static final String TOMCAT_TEMP_DIRECTORY_NAME = "tomcat";

    public static void main(String[] args) throws LifecycleException {
        startTomcat();
    }

    private static void startTomcat() throws LifecycleException {
        Tomcat tomcat = new Tomcat();

        tomcat.getConnector();
        tomcat.setBaseDir(tempDirectory());
        tomcat.setPort(PORT);
        tomcat.getHost().setAppBase(APP_BASE);
        tomcat.addWebapp(CONTEXT_PATH, APP_BASE);

        tomcat.start();
        tomcat.getServer().await();
    }

    private static int port() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 8080;
    }

    private static String tempDirectory() {
        try {
            File tempDirectory = File.createTempFile(TOMCAT_TEMP_DIRECTORY_NAME, "." + PORT);
            if (tempDirectory.delete()) tempDirectory.mkdir();
            tempDirectory.deleteOnExit();
            return tempDirectory.getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}