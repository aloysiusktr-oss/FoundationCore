package registry;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Registry<T extends Identifiable> {

    private final Map<String, T> entries = new HashMap<>();

    public void register(T entry) {
        entries.put(entry.getId(), entry);
    }

    public T get(String id) {
        return entries.get(id);
    }

    public boolean exists(String id) {
        return entries.containsKey(id);
    }

    public Collection<T> all() {
        return entries.values();
    }

    public int size() {
        return entries.size();
    }
}