package Fallin.engine;

import java.io.Serializable;

/**
 * The Person class represents a character in the game with a position, health, and damage attributes.
 * It provides methods to move the character, get and set its attributes, and handle health-related actions.
 */
public class Person implements Serializable {
    private int xposition;
    private int yposition;
    private int health = 10;
    private int damage = 1;
    private int maxHealth = 10;

    /**
     * Constructs a new Person with the specified initial position.
     *
     * @param x the initial x-coordinate of the person.
     * @param y the initial y-coordinate of the person.
     */
    public Person(int x, int y) {
        this.xposition = x;
        this.yposition = y;
    }

    /**
     * Moves the person in the direction specified by 1.
     *
     * @param direction the direction to move the person.
     * @return a message indicating the move was successful or invalid.
     */
    public String move(String direction) {
        switch (direction) {
            case "u":
                this.yposition++;
                return "up was successful.";
            case "d":
                this.yposition--;
                return "down was successful.";
            case "l":
                this.xposition--;
                return "left was successful.";
            case "r":
                this.xposition++;
                return "right was successful.";
            default:
                return "invalid direction.";
        }
    }

    /**
     * Gets the current x-coordinate of the person.
     *
     * @return the x-coordinate of the person.
     */
    public int getX() {
        return this.xposition;
    }

    /**
     * Gets the current y-coordinate of the person.
     *
     * @return the y-coordinate of the person.
     */
    public int getY() {
        return this.yposition;
    }

    /**
     * Gets the current health of the person.
     *
     * @return the health of the person.
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * Gets the max health of the person.
     *
     * @return the max health of the person.
     */
    public int getMaxHealth() {
        return this.maxHealth;
    }

    /**
     * Gets the damage value of the person.
     *
     * @return the damage value of the person.
     */
    public int getDamage() {
        return this.damage;
    }

    /**
     * Heals the person by the specified amount, increasing the health up to their max health.
     *
     * @param amount the amount to heal.
     */
    public void heal(int amount) {
        health = Math.min(maxHealth, health + amount);
    }

    /**
     * Decreases the health of the person by the specified amount.
     *
     * @param amount the amount of damage to be taken.
     */
    public void takeDamage(int amount) {
        health -= amount;
    }

    /**
     * Protected setter for health, allows subclasses to modify health.
     *
     * @param health the new health value.
     */
    protected void setHealth(int health) {
        this.health = health;
    }

    /**
     * Protected setter for max health, allows subclasses to modify max health.
     *
     * @param health the new max health value.
     */
    protected void setMaxHealth(int health) {
        this.maxHealth = health;
    }

    /**
     * Protected setter for damage, allows subclasses to modify damage.
     *
     * @param damage the new damage value.
     */
    protected void setDamage(int damage) {
        this.damage = damage;
    }
}