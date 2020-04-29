package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    private HashMap<Long, TimeEntry> entries = new HashMap<>();
    private long counter = 1;

    public TimeEntry create(TimeEntry timeEntry) {
        TimeEntry createdTimeEntry = new TimeEntry(counter, timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());
        counter++;

        entries.put(createdTimeEntry.getId(), createdTimeEntry);

        return createdTimeEntry;
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

        TimeEntry updatedTimeEntry = new TimeEntry(id, timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());
        entries.put(updatedTimeEntry.getId(), updatedTimeEntry);

        return updatedTimeEntry;
    }

    public void delete(long id) {
        entries.remove(id);
    }
}
