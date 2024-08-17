package net.swordnetwork.sworddata.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

public class Util {

    public static UUID getUUID(String name) {

        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            URLConnection urlConnection = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            StringBuilder res = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                res.append(line);
            }
            bufferedReader.close();

            if (!res.toString().startsWith("{")) {
                return null;
            }

            String uuid = res.substring(res.indexOf(": \"") + 3, res.indexOf("\", "));
            String dashes = uuid.substring(0, 8) + "-";
            dashes += uuid.substring(8, 12) + "-";
            dashes += uuid.substring(12, 16) + "-";
            dashes += uuid.substring(16, 20) + "-" + uuid.substring(20);

            return UUID.fromString(dashes);
        } catch (IOException ignored) {}
        return null;
    }
}
