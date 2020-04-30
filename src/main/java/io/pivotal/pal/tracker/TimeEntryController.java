package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("time-entries")
@RestController
public class TimeEntryController {
    private final DistributionSummary distributionSummary;
    private TimeEntryRepository timeEntryRepository;
    private Counter counter;

    public TimeEntryController(TimeEntryRepository timeEntryRepository, MeterRegistry meterRegistry) {
        this.timeEntryRepository = timeEntryRepository;
        this.distributionSummary = meterRegistry.summary("timeEntry.summary");
        this.counter = meterRegistry.counter("timeEntry.actionCounter");
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry createdTimeEntry = timeEntryRepository.create(timeEntryToCreate);

        distributionSummary.record(timeEntryRepository.list().size());
        counter.increment();
        return new ResponseEntity(createdTimeEntry, HttpStatus.CREATED);
    }

    @GetMapping("{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {
        TimeEntry foundTimeEntry = timeEntryRepository.find(timeEntryId);

        if (foundTimeEntry == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        counter.increment();
        return new ResponseEntity(foundTimeEntry, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> timeEntries = timeEntryRepository.list();

        counter.increment();
        return new ResponseEntity(timeEntries, HttpStatus.OK);
    }

    @PutMapping("{timeEntryId}")
    public ResponseEntity update(@PathVariable long timeEntryId, @RequestBody TimeEntry expected) {
        TimeEntry updatedTimeEntry = timeEntryRepository.update(timeEntryId, expected);

        if (updatedTimeEntry == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        counter.increment();
        return new ResponseEntity(updatedTimeEntry, HttpStatus.OK);
    }

    @DeleteMapping("{timeEntryId}")
    public ResponseEntity delete(@PathVariable long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);

        counter.increment();
        distributionSummary.record(timeEntryRepository.list().size());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
