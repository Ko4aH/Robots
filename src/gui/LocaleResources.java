package gui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class LocaleResources {
    private static HashMap<String, String> resources;
    private static Locale currentLocale;
    private static final String resourcePath = "resources/locales";
    private static final String currentLocalePath = String.format(
            "%s/Robots/currentLocale.cfg",
            System.getProperty("user.home"));

    public static void init() {
        if (Files.exists(Path.of(currentLocalePath))) {
            try {
                var content = Files.readString(Paths.get(currentLocalePath), StandardCharsets.UTF_8);
                var gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .create();
                Type type = new TypeToken<Locale>(){}.getType();
                Locale locale = gson.fromJson(content, type);
                initWithLocale(locale);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else initWithLocale(Locale.getDefault());
    }

    public static void initWithLocale(Locale locale) {
/*        if (resources == null || !locale.equals(resources.getLocale()))
               resources = ResourceBundle
                    .getBundle("strings", locale);*/

        if (resources == null || !locale.equals(currentLocale)) {
            var cwd = Paths.get(".").toAbsolutePath().normalize();
            var filename = cwd.resolve(resourcePath);
            switch (locale.getLanguage()) {
                case "ru", "en":
                    filename = filename.resolve(String.format("text_%s.json", locale.getLanguage()));
                    break;
                default:
                    filename = filename.resolve("text.json");
            }
            loadResourcesFrom(filename);
            currentLocale = locale;
            saveLocale(currentLocale);
        }
    }

    public static HashMap<String, String> getResources() {
        return resources;
    }

    public static Locale getCurrentLocale() {
        return currentLocale;
    }

    private static void loadResourcesFrom(Path filename) {
        try {
            var content = Files.readString(filename);
            var gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            Type type = new TypeToken<HashMap<String, String>>(){}.getType();
            resources = gson.fromJson(content, type);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveLocale(Locale locale) {
        if (!Files.exists(Paths.get(currentLocalePath))) {
            try {
                Files.createDirectories(Paths.get(currentLocalePath).getParent());
                Files.createFile(Paths.get(currentLocalePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        var gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        try {
            Files.writeString(
                    Paths.get(currentLocalePath),
                    gson.toJson(locale),
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}