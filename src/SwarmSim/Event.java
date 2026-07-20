package SwarmSim;

import simulation.ScheduledEvent;

/**
 * Abstract event scheduled at a specific simulation date.
 */
abstract class Event extends ScheduledEvent {

    public Event(long date) {
        super(date);
    }

    public abstract void execute();

}

