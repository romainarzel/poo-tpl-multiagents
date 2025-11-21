package cellularSim;

/**
 * Événement abstrait planifié à une date spécifique de simulation.
 * 
 * <p>
 * Cette classe fait partie d'un système de gestion d'événements discrets
 * (discrete-event simulation).
 * Chaque événement possède une date d'exécution et définit son comportement
 * spécifique
 * via la méthode abstraite {@link #execute()}.
 * </p>
 * 
 * <p>
 * Les événements sont comparables par leur date d'exécution, ce qui permet de
 * les
 * organiser automatiquement dans une file de priorité au sein de
 * {@link EventManager}.
 * </p>
 * 
 * @see EventManager
 * @see Comparable
 */
abstract class Event implements Comparable<Event> {
    private final long date;

    /**
     * Crée un nouvel événement programmé à la date spécifiée.
     * 
     * @param date la date de simulation à laquelle l'événement doit être exécuté
     */
    public Event(long date) {
        this.date = date;
    }

    /**
     * Retourne la date de simulation de cet événement.
     * 
     * @return la date d'exécution planifiée
     */
    public long getDate() {
        return date;
    }

    /**
     * Exécute l'action associée à cet événement.
     * 
     * <p>
     * Cette méthode abstraite doit être implémentée par les sous-classes pour
     * définir
     * le comportement spécifique de l'événement (par exemple, calculer la prochaine
     * génération d'automate cellulaire, mettre à jour l'affichage, etc.).
     * </p>
     */
    public abstract void execute();

    /**
     * Compare cet événement à un autre événement selon leur date d'exécution.
     * 
     * <p>
     * Cette méthode permet d'ordonner les événements par ordre chronologique dans
     * une structure de données comme une {@link java.util.PriorityQueue}.
     * </p>
     * 
     * @param other l'événement à comparer
     * @return un entier négatif, zéro, ou positif si cet événement est antérieur,
     *         simultané, ou postérieur à l'événement spécifié
     */
    @Override
    public int compareTo(Event other) {
        return Long.compare(this.date, other.date);
    }
}
