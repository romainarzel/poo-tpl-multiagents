package simulation;

import java.util.PriorityQueue;

/** Shared discrete-event scheduler used by every simulation. */
public class EventScheduler {
    private long currentDate;
    private final PriorityQueue<ScheduledEvent> queue = new PriorityQueue<>();

    public long getCurrentDate() {
        return currentDate;
    }

    public void addEvent(ScheduledEvent event) {
        if (event != null) {
            queue.add(event);
        }
    }

    public void next() {
        currentDate++;
        while (!queue.isEmpty() && queue.peek().getDate() <= currentDate) {
            queue.poll().execute();
        }
    }

    public boolean isFinished() {
        return queue.isEmpty();
    }

    public void restart() {
        currentDate = 0;
        queue.clear();
    }
}
