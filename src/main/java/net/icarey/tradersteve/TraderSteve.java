package net.icarey.tradersteve;


import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.util.logging.Logger;

public final class TraderSteve extends JavaPlugin {
    public final Logger logger = Logger.getLogger("Minecraft");
    public static TraderSteve plugin;
    private File settingsFile = new File(this.getDataFolder(), "config.yml");
    private FileConfiguration settings;
    public String Prefix;
    public static Economy econ = null;

    public TraderSteve(){

    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        if (this.setupPlug()) {
            this.logger.info(String.format("[%s] TraderSteve's has been enabled Version: %s", this.getDescription().getName(), this.getDescription().getVersion()));
            this.loadMethod();
            if (!this.setupEconomy()) {
                this.logger.severe(String.format("[%s] - Disabled due to no Vault or Economy plugin!", this.getDescription().getName()));
                Bukkit.getPluginManager().disablePlugin(this);
                return;
            }

            this.registerConfig();
        } else {
            this.getLogger().severe("Failed to load TraderSteve's!");
            this.getLogger().severe("Your server version is not compatible with this plugin!");
            this.getLogger().severe("Usable Versions: 1.13.1");
            Bukkit.getPluginManager().disablePlugin(this);
        }

    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private void registerConfig() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
    }

    private void loadMethod() {
        //REGISTER COMMANDS HERE
        this.Prefix = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("Prefix"));

    }

    private boolean setupPlug() {
        String version;
        try {
            version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        } catch (ArrayIndexOutOfBoundsException var3) {
            return false;
        }
        this.getLogger().info("Your server is running version " + version);
        if (version.equals("v1_13_R2")){
            //REGISTER LISTENERS HERE
        }

        return version.equals("v1_13_R2");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.logger.info("TraderSteve's for 1.13.1 has been disabled correctly!");
    }
}
