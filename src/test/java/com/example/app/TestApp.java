package com.example.app;

import io.fluxcapacitor.javaclient.configuration.ApplicationProperties;
import io.fluxcapacitor.proxy.ProxyServer;
import io.fluxcapacitor.testserver.TestServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.ServerSocket;

@SpringBootApplication
@Slf4j
public class TestApp {
    public static void main(String[] args) {
        // start Flux Test Server
        System.setProperty("FLUX_PORT", ApplicationProperties.getProperty("FLUX_PORT", "8888"));
        int fluxPort = ApplicationProperties.getIntegerProperty("FLUX_PORT");
        System.setProperty("FLUX_BASE_URL", "ws://localhost:" + fluxPort);
        if (availablePort(fluxPort)) {
            TestServer.main(new String[0]);
        }

        // start Flux Proxy
        System.setProperty("PROXY_PORT", ApplicationProperties.getProperty("PROXY_PORT", "8080"));
        int proxyPort = ApplicationProperties.getIntegerProperty("PROXY_PORT");
        if (availablePort(proxyPort)) {
            ProxyServer.main(new String[0]);
        }

        // start application
        System.setProperty("FLUX_APPLICATION_NAME", "Example");
        App.main(args);
    }

    static boolean availablePort(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setReuseAddress(true);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}

