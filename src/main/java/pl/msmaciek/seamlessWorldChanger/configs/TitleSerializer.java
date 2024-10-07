package pl.msmaciek.seamlessWorldChanger.configs;

import de.exlll.configlib.Serializer;
import net.kyori.adventure.title.Title;
import pl.msmaciek.seamlessWorldChanger.Utils;

import java.time.Duration;
import java.util.LinkedHashMap;

public final class TitleSerializer implements Serializer<Title, LinkedHashMap<String, Object>> {

    @Override
    public LinkedHashMap<String, Object> serialize(Title element) {
        LinkedHashMap<String, Object> hashMap = new LinkedHashMap<>();
        hashMap.put("title", Utils.asTextWithoutColor(element.title()));
        hashMap.put("subtitle", Utils.asTextWithoutColor(element.subtitle()));
        hashMap.put("fadeIn", element.times().fadeIn().toMillis());
        hashMap.put("stay", element.times().stay().toMillis());
        hashMap.put("fadeOut", element.times().fadeOut().toMillis());
        return hashMap;
    }

    @Override
    public Title deserialize(LinkedHashMap<String, Object> stringObjectHashMap) {
        return Title.title(
            Utils.hexComponent((String) stringObjectHashMap.get("title")),
            Utils.hexComponent((String) stringObjectHashMap.get("subtitle")),
            Title.Times.times(
                Duration.ofMillis((int) stringObjectHashMap.get("fadeIn")),
                Duration.ofMillis((int) stringObjectHashMap.get("stay")),
                Duration.ofMillis((int) stringObjectHashMap.get("fadeOut"))
            ));
    }
}