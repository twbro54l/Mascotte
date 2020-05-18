package com.minecraftbot.option;

import java.util.HashMap;
import java.util.Map;

public class Options {

    private Map<String, Object> options = new HashMap<>();

    public boolean isOption(String id) {
        return options.containsKey(id);
    }

    public <T> T getOption(String id, T defaultValue) {
        return (T) options.getOrDefault(id, defaultValue);
    }

    public Map<String, Object> getOptions() {
        return options;
    }

    public static class Builder {
        public static Options of(String[] args) {
            Options options = new Options();
            for (String arg : args) {
                String[] part = arg.split("=");
                if (part.length > 1) {
                    Object value = part[1];
                    try {
                        value = Integer.parseInt((String) value);
                    } catch (Exception exception) {
                    }

                    if ((value instanceof String && value.equals("true")) || value.equals("false")) {
                        try {
                            value = Boolean.parseBoolean((String) value);
                        } catch (Exception exception) {
                        }
                    }
                    options.options.put(part[0].replace("=", ""), value);
                }
            }
            return options;
        }
    }

}
