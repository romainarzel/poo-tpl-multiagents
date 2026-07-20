package simulation;

/** Base class for events ordered by their simulation date. */
public abstract class ScheduledEvent implements Comparable<ScheduledEvent> {
    private final long date;

    protected ScheduledEvent(long date) {
        this.date = date;
    }

    public long getDate() {
        return date;
    }

    public abstract void execute();

    @Override
    public int compareTo(ScheduledEvent other) {
        return Long.compare(date, other.date);
    }
}
