package com.minecraftbot.util;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Vars {

    public static void writeString(DataOutputStream out, String string, Charset charset) throws IOException {
        byte[] bytes = string.getBytes(charset);
        writeVarInt(out, bytes.length);
        out.write(bytes);
    }

    public static void writeVarInt(DataOutputStream out, int value) throws IOException {
        while (true) {
            if ((value & 0xFFFFFF80) == 0) {
                out.writeByte(value);
                return;
            }
            out.writeByte(value & 0x7F | 0x80);
            value >>>= 7;
        }
    }

    public static int readVarInt(DataInputStream in) throws IOException {
        int i = 0;
        int j = 0;
        while (true) {
            byte k = in.readByte();
            i |= (k & Byte.MAX_VALUE) << j++ * 7;
            if (j <= 5) {
                if ((k & 0x80) != 128)
                    return i;
                continue;
            }
            throw new RuntimeException("VarInt too big");
        }
    }

    public static byte[] handshake(String host, int port, int state) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        DataOutputStream handshake = new DataOutputStream(buffer);

        handshake.writeByte(0);
        //handshake.write(2147483647);
        //handshake.write(-2147483648);

        writeVarInt(handshake, 578);
        writeString(handshake, host, StandardCharsets.UTF_8);

        handshake.writeShort(port);

        writeVarInt(handshake, state);
        return buffer.toByteArray();
    }

    public static byte[] status() throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        DataOutputStream status = new DataOutputStream(buffer);

        // status size ??
        // status.writeByte(1);
        // or
        // status.writeByte(254);
        // legacy ??
        // status.writeByte(254);
        status.writeByte(0);
        //status.write(2147483647);
        //status.write(-2147483648);

        // ping ??
        // size : status.writeByte(9);
        // status.writeByte(1);
        // status.writeByte(System.currentTimeMillis());
        // will it work ??
        // increase numbers maybe ??
        // status.writeByte(254);
        return buffer.toByteArray();
    }


    public static byte[] login(String name) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        DataOutputStream login = new DataOutputStream(buffer);

        // size
        // login.writeByte(1);
        // or
        // login.writeByte(254);
        login.writeByte(0);
        writeString(login, name, StandardCharsets.UTF_8);
        return buffer.toByteArray();
    }

    public static byte[] chat(String message) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        DataOutputStream chat = new DataOutputStream(buffer);

        // size ??
        // chat.writeByte(254);
        chat.writeByte(3);
        writeString(chat, message, StandardCharsets.UTF_8);
        // add something else will it work ??
        // chat.writeByte(254);
        return buffer.toByteArray();
    }

}
