package me.ujun.absolutevanish.commands;

public class CommandUtil {
    public static Boolean toBooleanOrNull(String input) {
        if (input == null) return null;

        if (input.equalsIgnoreCase("true")) {
            return true;
        } else if (input.equalsIgnoreCase("false")) {
            return false;
        } else {
            return null;
        }
    }
}
