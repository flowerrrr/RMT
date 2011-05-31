package de.flower.wicketstuff.gae;

import org.apache.wicket.util.listener.ChangeListenerSet;
import org.apache.wicket.util.listener.IChangeListener;
import org.apache.wicket.util.time.Duration;
import org.apache.wicket.util.time.Time;
import org.apache.wicket.util.watch.IModifiable;
import org.apache.wicket.util.watch.IModificationWatcher;

import java.util.*;

public class StaticModificationWatcher implements IModificationWatcher {

    private static final class Entry {
        // The most recent lastModificationTime polled on the object
        Time lastModifiedTime;

        // The set of listeners to call when the modifiable changes
        final ChangeListenerSet listeners = new ChangeListenerSet();

        // The modifiable thing
        IModifiable modifiable;
    }

    private Map<IModifiable, Entry> listeners;

    /**
     * Call this method regularly
     */
    public void runCheck() {
        // Use a separate list to avoid concurrent modification exceptions (notifying
        // the listener might cause a call to the add -method of this class)
        Collection<ChangeListenerSet> toBeNotified = new
                ArrayList<ChangeListenerSet>();
        for (Entry entry : listeners.values()) {
            if (entry.modifiable.lastModifiedTime().equals(entry.lastModifiedTime) == false) {
                toBeNotified.add(entry.listeners);
                // entry.listeners.notifyListeners();
            }
            entry.lastModifiedTime = entry.modifiable.lastModifiedTime();
        }
        for (ChangeListenerSet changeListenerSet : toBeNotified) {
            changeListenerSet.notifyListeners();
        }
    }

    public StaticModificationWatcher() {
        listeners = Collections.synchronizedMap(
                new HashMap<IModifiable, Entry>());
    }

    @Override
    public boolean add(IModifiable modifiable, IChangeListener listener) {
        boolean contains = listeners.containsKey(modifiable);
        if (contains == false) {
            Entry entry = new Entry();
            entry.modifiable = modifiable;
            entry.lastModifiedTime = modifiable.lastModifiedTime();
            listeners.put(modifiable, entry);
        }
        listeners.get(modifiable).listeners.add(listener);

        return contains;
    }

    @Override
    public void destroy() {
        // nothing to do

    }

    @Override
    public Set<IModifiable> getEntries() {
        return listeners.keySet();
    }

    @Override
    public IModifiable remove(IModifiable modifiable) {
        listeners.remove(modifiable);

        return modifiable;
    }

    @Override
    public void start(Duration duration) {
        // ignored
    }
}
