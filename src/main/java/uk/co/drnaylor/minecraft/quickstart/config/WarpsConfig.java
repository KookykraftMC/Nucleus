package uk.co.drnaylor.minecraft.quickstart.config;

import com.google.common.collect.Maps;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.SimpleConfigurationNode;
import ninja.leaping.configurate.gson.GsonConfigurationLoader;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import uk.co.drnaylor.minecraft.quickstart.api.exceptions.NoSuchWorldException;
import uk.co.drnaylor.minecraft.quickstart.api.service.QuickStartWarpService;
import uk.co.drnaylor.minecraft.quickstart.config.serialisers.LocationNode;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

public class WarpsConfig extends AbstractConfig<ConfigurationNode, GsonConfigurationLoader> implements QuickStartWarpService {

    private final Map<String, LocationNode> warpNodes = Maps.newHashMap();

    public WarpsConfig(Path file) throws IOException {
        super(file);
    }

    @Override
    public void load() throws IOException {
        super.load();

        warpNodes.clear();
        node.getChildrenMap().forEach((k, v) -> {
            warpNodes.put(k.toString().toLowerCase(), new LocationNode(v));
        });
    }

    @Override
    public void save() throws IOException {
        node = SimpleConfigurationNode.root();
        warpNodes.forEach((k, v) -> v.populateNode(node.getNode(k.toLowerCase())));
        super.save();
    }

    @Override
    protected GsonConfigurationLoader getLoader(Path file) {
        return GsonConfigurationLoader.builder().setPath(file).build();
    }

    @Override
    protected ConfigurationNode getDefaults() {
        return SimpleConfigurationNode.root();
    }

    @Override
    public Optional<Location<World>> getWarp(String warpName) {
        LocationNode ln = warpNodes.get(warpName.toLowerCase());

        try {
            if (ln != null) {
                return Optional.of(ln.getLocation());
            }
        } catch (NoSuchWorldException ex) {
            // Yeah... we know
        }

        return Optional.empty();
    }

    @Override
    public boolean removeWarp(String warpName) {
        return warpNodes.remove(warpName.toLowerCase()) != null;
    }

    @Override
    public boolean setWarp(String warpName, Location<World> location) {
        String warp = warpName.toLowerCase();
        if (getWarp(warp).isPresent()) {
            return false;
        }

        warpNodes.put(warp, new LocationNode(location));
        return true;
    }
}
