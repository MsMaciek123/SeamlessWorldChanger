package pl.msmaciek.seamlessWorldChanger;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.title.Title;

import java.time.Duration;

public class Utils {
    public static Title getTitleInstance(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        return Title.title(hexComponent(title), hexComponent(subtitle), Title.Times.times(Duration.ofMillis(fadeIn), Duration.ofMillis(stay), Duration.ofMillis(fadeOut)));
    }

    public static Component hexComponent(String message) {
        return MiniMessage.miniMessage().deserialize(message);
    }

    public static String asTextWithoutColor(Component component) {
        return PlainTextComponentSerializer.plainText().serialize(component);
    }
}
