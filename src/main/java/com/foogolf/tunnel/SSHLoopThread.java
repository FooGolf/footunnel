package com.foogolf.tunnel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SSHLoopThread extends Thread {

	@Autowired AtomicReference<TunnelRequest> currentTunnelRequest;
	@Autowired AtomicReference<Process> currentSshProcess;
    
	@Override
	public void run() {
		
		while (true) {
			if (currentTunnelRequest.get() != null) {
				try {
	                System.out.println("Executing SSH command to create tunnel");
	                String command = String.format("sshpass -p '%s' %s -o StrictHostKeyChecking=no Administrator@%s",
	                		currentTunnelRequest.get().getPassword(), FooboxtunnelApplication.SSHCOMMAND, currentTunnelRequest.get().getCloudPC());
	                System.out.println("Issuing SSH command to create tunnel: " + command);
		
	                ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command);
	                processBuilder.redirectErrorStream(true); // merge stdout and stderr
	                Process sshProcess = processBuilder.start();
		
	               	currentSshProcess.set(sshProcess); // store a reference to the running process so REST API can kill it
	
	                try (BufferedReader reader = new BufferedReader(new InputStreamReader(sshProcess.getInputStream()))) {
	                    String line;
	                    while ((line = reader.readLine()) != null) {
	                        System.out.println("SSH Output: " + line);
	                    }
	                }
		
	                // wait for process to complete, perhaps because it was interrupted by a new incoming REST call
	                int exitCode = sshProcess.waitFor();
	                System.out.println("SSH process exited with code: " + exitCode);
	    
	            } catch (IOException | InterruptedException e) {
	                System.out.println("SSH connection error: " + e.getMessage());
	                
	            } finally {
	                currentSshProcess.set(null);
	            }
			} else
                System.out.println("Null tunnel request");

            try {
            	Thread.sleep(2000);  // don't slam the cloud PC or loop too aggressively
            } catch (InterruptedException e) {
                System.out.println("InterruptedException while waiting to retry SSH connection");
            }
		}
	}
}
