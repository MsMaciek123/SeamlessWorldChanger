package pl.msmaciek.seamlessWorldChanger.configs;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import net.kyori.adventure.title.Title;
import pl.msmaciek.seamlessWorldChanger.Utils;

@Configuration
public class MainConfig {
    @Comment({"This plugin requires 1.19.x or above!",
            "Title shown when changing worlds"})
    public boolean showTitle = true;

    public Title shownTitle = Utils.getTitleInstance(
        "Changing worlds", "Please wait...", 1000, 500, 1000
    );

    @Comment({"", "Amount of ticks to delay world change",
            "Set to -1 to disable",
            "WARNING - This option may cause incompatibility issues with other plugins!"})
    public int worldChangeDelay = -1;
}
