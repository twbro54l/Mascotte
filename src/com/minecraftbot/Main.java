package com.minecraftbot;

import com.minecraftbot.option.Options;

public class Main {

    public static void main(String[] args) {
        Launcher launcher = new Launcher(Options.Builder.of(args));
        launcher.launch();
    }
}
