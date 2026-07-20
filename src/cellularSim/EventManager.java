package cellularSim;

import simulation.EventScheduler;

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
class EventManager extends EventScheduler {

    /**
     * Retourne la date courante de la simulation.
     * 
     * @return la date courante (nombre de ticks écoulés depuis le début)
     */
}
