package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    private HashMap<Long, TimeEntry> entries = new HashMap<>();
    private long counter = 1;

    public TimeEntry create(TimeEntry timeEntry) {
        timeEntry.setId(counter);
        counter++;

        entries.put(timeEntry.getId(), timeEntry);

        return timeEntry;
    }

    public TimeEntry find(long id) {
        return entries.get(id);
    }

    public List<TimeEntry> list() {
        return new ArrayList<>(entries.values());
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {
        if (entries.get(id) == null) {
            return null;
        }

        timeEntry.setId(id);
        entries.put(timeEntry.getId(), timeEntry);

        return timeEntry;
    }

    public void delete(long id) {
        entries.remove(id);
    }
}
