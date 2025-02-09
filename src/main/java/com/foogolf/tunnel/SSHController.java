package com.foogolf.tunnel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@CrossOrigin(origins = {"https://dev.foogolf.com", "https://foogolf.com"})
public class SSHController {

	@Autowired AtomicReference<TunnelRequest> currentTunnelRequest;
	@Autowired AtomicReference<Process> currentSshProcess;

	// API to launch a SSH tunnel command, automatically retrying on failure. If one is already underway it is interrupted. 
	@PostMapping("/doSSH")
	public String startSshTunnel(@RequestBody TunnelRequest request) {

		int numSSHCommands = countActiveSshProcesses();
		System.out.println("# running ssh commands: " + numSSHCommands);

		currentTunnelRequest.set(request);  // tell the loop thread about the new connect details

		// kill the loop thread's existing ssh process if any
		Process existingProcess = currentSshProcess.get();
		if (existingProcess != null && existingProcess.isAlive()) {
			System.out.println("Existing SSH process is alive - destroying");
			existingProcess.destroy();
			try {
				if (!existingProcess.waitFor(10, java.util.concurrent.TimeUnit.SECONDS)) {
					System.out.println("Forcing SSH process termination...");
					existingProcess.destroyForcibly();
				}
				if (existingProcess.isAlive()) {
					System.out.println("Fatal error - existing ssh process couldn't be killed");
				}

			} catch (InterruptedException e) {
				System.out.println("Error - InterruptedException while waiting for SSH process termination: " + e.getMessage());

			} finally {
				currentSshProcess.set(null);
			}
		}
		return "SSH process requested for cloudPC: " + request.getCloudPC();
	}

	private int countActiveSshProcesses() {
		try {
			Process process = new ProcessBuilder("bash", "-c", "pgrep -fc '" + FooboxtunnelApplication.SSHCOMMAND + "'").start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String count = reader.readLine();
			return count != null ? Integer.parseInt(count) : 0;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}
}
