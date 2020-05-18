package com.minecraftbot;

import com.minecraftbot.flood.FloodRunner;
import com.minecraftbot.option.Options;
import com.minecraftbot.proxy.Proxies;

import java.io.IOException;
import java.net.Proxy;

public class Launcher {

    private String version = "1.0";
    private Options options;

    public Launcher(Options options) {
        this.options = options;
    }

    public void launch() {
        if (!options.isOption("host")) {
            System.out.println("No \"host\" option provided!");
            System.exit(1);
            return;
        }
        String proxiesType = options.getOption("proxiesType", "http");
        String proxiesFile = options.getOption("proxiesFile", "proxies.txt");
        Proxies proxies = new Proxies();
        try {
            switch (proxiesType) {
                case "socks":
                    proxies.init(proxiesFile, Proxy.Type.SOCKS);
                    break;
                case "http":
                    proxies.init(proxiesFile, Proxy.Type.HTTP);
                    break;
            }
        } catch (IOException exception) {
            System.out.println("Couldn't init proxies instance!");
            System.exit(1);
            return;
        }
        System.out.println("Enabled MASCOTTE powered by NetSeek & _JetFuel\nProxies amount: " + proxies.size());
        new FloodRunner(options, proxies).run();
    }

}
