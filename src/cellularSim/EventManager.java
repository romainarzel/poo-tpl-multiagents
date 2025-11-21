package cellularSim;

import java.util.PriorityQueue;

/**
 * Gestionnaire d'événements discrets pour la simulation.
 * 
 * <p>
 * Cette classe implémente un moteur de simulation à événements discrets
 * (discrete-event simulation).
 * Elle maintient une date courante et une file de priorité d'événements à
 * exécuter.
 * </p>
 * 
 * <p>
 * À chaque appel de {@link #next()}, le gestionnaire :
 * <ul>
 * <li>Incrémente la date courante de 1</li>
 * <li>Exécute tous les événements dont la date est antérieure ou égale à la
 * date courante</li>
 * <li>Permet aux événements de programmer de nouveaux événements futurs</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Les événements sont automatiquement ordonnés par date grâce à la
 * {@link PriorityQueue}
 * et à l'implémentation de {@link Comparable} dans {@link Event}.
 * </p>
 * 
 * @see Event
 */
class EventManager {
    private long currentDate = 0;
    private final PriorityQueue<Event> queue = new PriorityQueue<>();

    /**
     * Retourne la date courante de la simulation.
     * 
     * @return la date courante (nombre de ticks écoulés depuis le début)
     */
    public long getCurrentDate() {
        return currentDate;
    }

    /**
     * Ajoute un événement à la file de priorité.
     * 
     * <p>
     * L'événement sera exécuté automatiquement lorsque la date courante
     * atteindra ou dépassera la date de l'événement.
     * </p>
     * 
     * @param e l'événement à ajouter (ignoré si null)
     */
    public void addEvent(Event e) {
        if (e != null)
            queue.add(e);
    }

    /**
     * Avance la simulation d'un pas de temps et exécute les événements dus.
     * 
     * <p>
     * Cette méthode incrémente la date courante puis exécute séquentiellement
     * tous les événements dont la date est inférieure ou égale à la nouvelle date
     * courante.
     * Les événements sont traités dans l'ordre chronologique.
     * </p>
     */
    public void next() {
        currentDate++;
        while (!queue.isEmpty() && queue.peek().getDate() <= currentDate) {
            queue.poll().execute();
        }
    }

    /**
     * Vérifie si la simulation est terminée (plus aucun événement en attente).
     * 
     * @return {@code true} si la file d'événements est vide, {@code false} sinon
     */
    public boolean isFinished() {
        return queue.isEmpty();
    }

    /**
     * Réinitialise le gestionnaire d'événements.
     * 
     * <p>
     * Cette méthode remet la date courante à 0 et vide la file d'événements.
     * Elle est typiquement appelée lors du redémarrage de la simulation.
     * </p>
     */
    public void restart() {
        currentDate = 0;
        queue.clear();
    }
}
