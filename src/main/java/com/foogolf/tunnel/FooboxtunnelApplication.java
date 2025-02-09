package com.foogolf.tunnel;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class FooboxtunnelApplication {

    public final static String SSHCOMMAND = "ssh -R 7575:127.0.0.1:7575"; // ssh command to set up a tunnel for bluetooth to happen
	
	@Bean
    public AtomicReference<TunnelRequest> getTurrentTunnelRequest() {
		return new AtomicReference<>();
	}

	@Bean
    public AtomicReference<Process> getCurrentSshProcess() {
		return new AtomicReference<>();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(FooboxtunnelApplication.class, args);
	}
}
