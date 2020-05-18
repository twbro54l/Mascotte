package com.minecraftbot.proxy;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Proxies {


    private List<Proxy> proxies = new ArrayList<>();

    private int index = 0;

    public void init(String fileName, Proxy.Type type) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) file.createNewFile();

        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains(" ")) line = line.replace(" ", "");
            if (line.isEmpty() || line.startsWith("##") || !line.contains(":")) continue;
            String[] ip = line.split(":");
            if (ip.length < 1) return;
            String hostName = ip[0];
            int port = 8080;
            try {
                port = Integer.parseInt(ip[1]);
            } catch (NumberFormatException numberFormatException) {
            }
            proxies.add(new Proxy(type, new InetSocketAddress(hostName, port)));
        }
    }

    public Proxy nextProxy() {
        if (proxies.size() == 0) return Proxy.NO_PROXY;
        if (index >= proxies.size()) index = 0;
        return Objects.requireNonNull(proxies.get(index++));
    }

    public void removeProxy(Proxy proxy) {
        proxies.remove(proxy);
    }

    public int size() {
        return proxies.size();
    }

}
