package com.app.tddt4iots.config;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
public class SocketIOConfig {

    @Value("${socket-server.port}")
    private Integer port;

    public SocketIOConfig() throws UnknownHostException {
    }

    @Bean
    public SocketIOServer socketIOServer() throws UnknownHostException {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(Inet4Address.getLocalHost().getHostAddress());
        //config.setHostname("0.0.0.0");
        config.setPort(port);
        return new
                SocketIOServer(config);
    }

}
