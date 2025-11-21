package SwarmSim;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Classe de base représentant un élément mobile de simulation avec propriétés
 * physiques.
 * 
 * <p>
 * Cette classe étend {@link Point2D.Double} pour hériter de la position (x, y)
 * et ajoute
 * des attributs pour la vélocité, la direction et l'accélération. Elle sert de
 * classe
 * de base pour les boids et autres agents mobiles de la simulation.
 * </p>
 * 
 * <p>
 * Attributs :\n *
 * <ul>
 * <li><b>velocity</b> : vitesse de déplacement (pixels par pas de temps)</li>
 * <li><b>direction</b> : orientation en radians (0 = haut, sens horaire)</li>
 * <li><b>acceleration</b> : accélération (non utilisée dans l'implémentation
 * actuelle)</li>
 * </ul>
 * </p>
 * 
 * @see Boids
 * @see Point2D.Double
 */
public class Element extends Point2D.Double {

    public double velocity;
    public double direction;
    public double acceleration;

    /**
     * Crée un élément avec tous les attributs spécifiés.
     * 
     * @param x            position initiale en x
     * @param y            position initiale en y
     * @param velocity     vitesse initiale
     * @param acceleration accélération initiale
     * @param direction    direction initiale en radians
     */
    public Element(double x, double y, double velocity, double acceleration, double direction) {
        this.direction = direction;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.x = x;
        this.y = y;
    }

    /**
     * Crée un élément avec des valeurs par défaut (position et attributs à 0).
     */
    public Element() {
    }

    /**
     * Retourne la vitesse de cet élément.
     * 
     * @return la vitesse courante
     */
    public double getVelocity() {
        return velocity;
    }

    /**
     * Retourne la direction de cet élément.
     * 
     * @return la direction en radians
     */
    public double getDirection() {
        return direction;
    }
}
