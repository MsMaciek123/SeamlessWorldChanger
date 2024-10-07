package pl.msmaciek.seamlessWorldChanger.configs;

import de.exlll.configlib.ConfigLib;
import de.exlll.configlib.YamlConfigurationProperties;
import de.exlll.configlib.YamlConfigurationStore;
import lombok.Getter;
import net.kyori.adventure.title.Title;
import pl.msmaciek.seamlessWorldChanger.SeamlessWorldChanger;

import java.io.File;

public class ConfigLoader {
    private YamlConfigurationStore<MainConfig> mainConfigStore;
    @Getter private MainConfig mainConfig;

    public void loadConfigs() {
        YamlConfigurationProperties properties = ConfigLib.BUKKIT_DEFAULT_PROPERTIES
            .toBuilder()
            .addSerializer(Title.class, new TitleSerializer())
            .build();

        mainConfigStore = new YamlConfigurationStore<>(MainConfig.class, properties);
        this.mainConfig = mainConfigStore.update(new File(SeamlessWorldChanger.getInstance().getDataFolder(), "config.yml").toPath());
    }
}
