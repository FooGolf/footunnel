package com.foogolf.tunnel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SSHLoopRunner implements CommandLineRunner {

    private final SSHLoopThread sshLoopThread;

//    @Autowired
    public SSHLoopRunner(SSHLoopThread sshLoopThread) {
        this.sshLoopThread = sshLoopThread;
    }

    @Override
    public void run(String... args) throws Exception {
        sshLoopThread.start();
    }
}
