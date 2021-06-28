package dev.negativekb.api.util.iface;

import java.io.IOException;

public interface JSONDataManager {

    void save() throws IOException;

    void load() throws IOException;
}
