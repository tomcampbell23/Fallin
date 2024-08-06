package Fallin.engine;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * The GameEngine class represents the main game logic, handling the game map,
 * player movements, and enemy interactions. It supports saving and
 * loading the game state and saving scores to the leaderboard.
 */
public class GameEngine implements Serializable {
    private Cell[][] map;
    private List<Enemy> enemies = new ArrayList<>();
    private Player player;
    private final Leaderboard leaderboard;
    private final int size;
    private int stepLimit, difficulty, collectedTreasures, steps;
    private boolean playing = true, gameWon = false;
    private static final String saveFile = "game.dat";
    private final Random random = new Random();

    /**
     * Creates a square grid for the game board.
     *
     * @param d the difficulty level.
     * @param Size the width and height.
     * @param sLimit the number of steps left in the current game.
     */
    public GameEngine(int d, int Size, int sLimit) {
        map = new Cell[Size][Size];
        size = Size;
        difficulty = d;
        stepLimit = sLimit;
        leaderboard = new Leaderboard();
        initialiseMap();
    }

    /**
     * Moves the player or executes a command based on the given input.
     *
     * @param input the direction or command entered by the player.
     * @return a message indicating the result of the command.
     */
    public String playerInteraction(String input) {
        String result = null;
        switch(input) {
            case "u":
                result = movePlayer(0, 1, "u");
                break;
            case "d":
                result = movePlayer(0, -1, "d");
                break;
            case "l":
                result = movePlayer(-1, 0, "l");
                break;
            case "r":
                result = movePlayer(1, 0, "r");
                break;
            case "help":
                result = this.help();
                break;
            case "save":
                result = this.save();
                break;
            case "load":
                result = this.load();
                break;
            case "leader":
                result = this.displayTopScores();
                break;
            default:
                System.out.println("Invalid input. Please enter u, d, l, r, help, save, load, or leader.");
                break;
        }
        int x = player.getX();
        int y = player.getY();
        map[x][y].setPlayer(true);
        String cellType = map[x][y].getType().getType();

        switch (cellType) {
            case "TREASURE":
                collectedTreasures++;
                map[x][y].setType(new CellTypes.Empty());
                result += " You collected a treasure!";
                break;
            case "MEDICAL_UNIT":
                player.heal(3);
                map[x][y].setType(new CellTypes.Empty());
                result += " You used a medical unit!";
                break;
            case "TRAP":
                player.takeDamage(2);
                map[x][y].setType(new CellTypes.Empty());
                result += " You stepped on a trap!";
                break;
            case "MUTANT":
                Enemy encountered = null;
                // Find the enemy that is at the player's position
                for (Enemy enemy : enemies) {
                    if (enemy.getX() == x && enemy.getY() == y) {
                        player.takeDamage(enemy.getDamage());
                        encountered = enemy;
                    }
                }
                enemies.remove(encountered);
                map[x][y].setType(new CellTypes.Empty());
                result += " You encountered a mutant!";
                break;
            case "EXIT":
                gameWon = true;
                playing = false;
                break;
            default:
                // Do nothing
                break;
        }
        return result;
    }

    /**
     * Moves the enemies randomly on the map.
     *
     * @return a message indicating any encounters with the player.
     */
    public String moveEnemies() {
        Enemy encounter = null;
        for (Enemy enemy : enemies) {
            int action = random.nextInt(5); // Randomly choose action (0-4)

            switch (action) {
                case 0:
                    try {
                        attemptEnemyMove(enemy, 0,  1, "u");
                    } catch (Exception ignore) {}
                    break;
                case 1:
                    try {
                        attemptEnemyMove(enemy, 0, -1, "d");
                    } catch (Exception ignore) {}
                    break;
                case 2:
                    try {
                        attemptEnemyMove(enemy, -1, 0, "l");
                    } catch (Exception ignore) {}
                    break;
                case 3:
                    try {
                        attemptEnemyMove(enemy, 1, 0, "r");
                    } catch (Exception ignore) {}
                    break;
                case 4:
                    // Stay in place
                    break;
            }
            if (enemy.getX() == player.getX() && enemy.getY() == player.getY()) {
                player.takeDamage(enemy.getDamage());
                map[enemy.getX()][enemy.getY()].setType(new CellTypes.Empty());
                encounter = enemy;
            }
        }
        if (encounter != null) {
            enemies.remove(encounter);
            return "You encountered a mutant! ";
        } else {
            return "";
        }
    }

    /**
     * Checks the state of the game, including win/loss conditions and
     * saves the score to the leaderboard.
     *
     * @return a message indicating the game state or null if the game is still ongoing.
     */
    public String checkState() {
        if (player.getHealth() <= 0) {
            int finalScore = -1;
            LocalDate date = LocalDate.now();
            leaderboard.addScore(finalScore, date);
            return "Game over! You died.";
        } else if (steps >= stepLimit) {
            int finalScore = -1;
            LocalDate date = LocalDate.now();
            leaderboard.addScore(finalScore, date);
            return "Game over! You ran out of stamina.";
        }

        if (!playing) {
            if (gameWon) {
                int finalScore = getScore();
                LocalDate date = LocalDate.now();
                int rank = leaderboard.addScore(finalScore, date).getRank();
                if (rank <= leaderboard.getScoresToSave()) {
                    return "You won the game and have placed #" + rank + " on the leaderboard" +
                            " with a final score of " + finalScore + ", congratulations!";
                } else {
                    return "You won the game with a final score of " + finalScore + ".";
                }
            } else {
                int finalScore = -1;
                LocalDate date = LocalDate.now();
                leaderboard.addScore(finalScore, date);
                return "Game over! You lost the game.";
            }
        } else {
            return null;
        }
    }

    /**
     * Saves the current game state to a file.
     *
     * @return a message indicating the success or failure of the save operation.
     */
    public String save() {
        try {
            ObjectOutputStream output = new ObjectOutputStream(new BufferedOutputStream(
                    new FileOutputStream(saveFile)));
            try {
                output.writeObject(this);
            } catch (Throwable e) {
                try {
                    output.close();
                } catch (Throwable e2) {
                    e.addSuppressed(e2);
                }
                throw e;
            }
            output.close();
            return "Successfully saved the game.";
        } catch (IOException e3) {
            return "IO Error has occurred: " + e3.getMessage();
        }
    }

    /**
     * Loads a previously saved game state from a file.
     *
     * @return a message indicating the success or failure of the load operation.
     */
    public String load() {
        try {
            ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(
                    new FileInputStream(saveFile)));
            try {
                GameEngine loaded = (GameEngine)input.readObject();
                this.player = loaded.player;
                this.enemies = loaded.enemies;
                this.map = loaded.map;
                this.gameWon = loaded.gameWon;
                this.playing = loaded.playing;
                this.steps = loaded.steps;
                this.stepLimit = loaded.stepLimit;
                this.difficulty = loaded.difficulty;
                this.collectedTreasures = loaded.collectedTreasures;
            } catch (Throwable e) {
                try {
                    input.close();
                } catch (Throwable e2) {
                    e.addSuppressed(e2);
                }
                throw e;
            }
            input.close();
            return "Successfully loaded the game.";
        } catch (FileNotFoundException e3) {
            return "No save file found";
        } catch (ClassNotFoundException | IOException e4) {
            return "IO Error has occurred, save file may not be compatible: "+ e4.getMessage();
        }
    }

    /**
     * Displays the top scores from the leaderboard.
     *
     * @return a string representation of the top scores.
     */
    public String displayTopScores() {
        String result = "";
        for (Leaderboard.ScoreEntry entry : leaderboard.getTopScores()) {
            result += "#" + entry.getRank() + " Score: " + entry.getScore() + " " + entry.getDate() + "\n";
        }
        return result;
    }

    /**
     * The size of the current game.
     *
     * @return the size of the game map, this is both the width and height.
     */
    public int getSize() {
        return size;
    }

    /**
     * The map of the current game.
     *
     * @return the map, which is a 2d array.
     */
    public Cell[][] getMap() {
        return map;
    }

    /**
     * Provides help information for the game.
     *
     * @return a string containing help information.
     */
    public String help() {
        return "You need to reach the exit at the top right corner of the map with the highest score." +
                " Avoid traps and mutants while keeping within the step limit using the movement controls." +
                " You can save and load your progress at any time during the game.";
    }

    /**
     * Gets the difficulty level of the game.
     *
     * @return the difficulty level.
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Gets the player class instance in the current game.
     *
     * @return the player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Calculates the current score of the game.
     *
     * @return the score based on collected treasures and remaining steps.
     */
    public int getScore() {
        return 20 * collectedTreasures + (stepLimit - steps);
    }

    /**
     * Gets the number of treasures collected by the player.
     *
     * @return the number of collected treasures.
     */
    public int getCollectedTreasures() {
        return collectedTreasures;
    }

    /**
     * Gets the step limit for the game.
     *
     * @return the step limit.
     */
    public int getStepLimit() {
        return stepLimit;
    }

    /**
     * Gets the number of steps taken by the player.
     *
     * @return the number of steps.
     */
    public int getSteps() {
        return steps;
    }

    /**
     * Plays a text-based game
     */
    public static void main(String[] args) {
        GameEngine engine = new GameEngine(5, 10, 100);
        System.out.printf("The size of map is %d * %d, the difficulty is %d, and the step limit is %d.\n",
                engine.getSize(), engine.getSize(), engine.getDifficulty(), engine.getStepLimit());
        engine.textGameLoop();
    }

    /**
     * Moves the player in the specified direction by updating their position.
     * The player will only move if the new position is within bounds.
     *
     * @param dx The change in the x-coordinate (horizontal movement).
     * @param dy The change in the y-coordinate (vertical movement).
     * @param direction The direction of the movement as a string ("u", "d", "l", "r").
     * @return A message indicating the result of the movement, either successful or unsuccessful.
     */
    private String movePlayer(int dx, int dy, String direction) {
        int newX = player.getX() + dx;
        int newY = player.getY() + dy;
        if (newX >= 0 && newX < size && newY >= 0 && newY < size) {
            steps++;
            map[player.getX()][player.getY()].setPlayer(false);
            return player.move(direction);
        } else {
            switch (direction) {
                case "u":
                    return "up was unsuccessful. ";
                case "d":
                    return "down was unsuccessful. ";
                case "r":
                    return "right was unsuccessful. ";
                case "l":
                    return "left was unsuccessful. ";
                default:
                    return "was unsuccessful. ";
            }
        }
    }

    /**
     * Attempts to move an enemy to a new position on the map in the specified direction.
     * The enemy will only move if the new position is within bounds and empty.
     *
     * @param enemy The enemy to be moved.
     * @param dx The change in the x-coordinate (horizontal movement).
     * @param dy The change in the y-coordinate (vertical movement).
     * @param direction The direction of the movement as a string ("u", "d", "l", "r").
     */
    private void attemptEnemyMove(Enemy enemy, int dx, int dy, String direction) {
        int newX = enemy.getX() + dx;
        int newY = enemy.getY() + dy;
        if (newX >= 0 && newX < size && newY >= 0 && newY < size && map[newX][newY].getType() instanceof CellTypes.Empty) {
            map[enemy.getX()][enemy.getY()].setType(new CellTypes.Empty());
            enemy.move(direction);
            map[enemy.getX()][enemy.getY()].setType(new CellTypes.Mutant());
        }
    }

    /**
     * Populates the game map with different cell types.
     */
    private void initialiseMap() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j] = new Cell(i, j);
            }
        }

        // Place the entrance and exit cell types
        map[0][0].setType(new CellTypes.Entrance());
        map[size - 1][size - 1].setType(new CellTypes.Exit());

        // Initialise the player at the entrance
        map[0][0].setPlayer(true);
        player = new Player(0, 0);

        // Randomly place treasures, medical units, traps, and mutants
        placeRandomElements(new CellTypes.Treasure(), 8);
        placeRandomElements(new CellTypes.MedicalUnit(), 2);
        placeRandomElements(new CellTypes.Trap(), 5);
        placeRandomElements(new CellTypes.Mutant(), difficulty);
    }

    /**
     * Places a specified number of elements randomly on the map.
     *
     * @param type the type of cell to place.
     * @param count the number of cells to place.
     */
    private void placeRandomElements(CellTypes type, int count) {
        int placed = 0;

        while (placed < count) {
            int x = random.nextInt(size);
            int y = random.nextInt(size);

            if (map[x][y].getType() instanceof CellTypes.Empty) {
                map[x][y].setType(type);
                placed++;
                if (type instanceof CellTypes.Mutant) {
                    Enemy enemy = new Enemy(x, y);
                    enemies.add(enemy);
                }
            }
        }
    }

    /**
     * The main game loop for the text-based game.
     */
    private void textGameLoop() {
        while (playing) {

            // Display the game state
            displayGameState();

            // Get player input for movement/command.
            System.out.println("Enter a command (u = Up, d = Down, l = Left, r = Right, save = Save current game, load = Load previously saved game, leader = Show leaderboard):");
            Scanner in = new Scanner(System.in);
            String input = in.nextLine().trim().toLowerCase();

            if (!input.isEmpty()) {
                String out = playerInteraction(input);

                System.out.println(out);
                if ((out.contains("up") || out.contains("down") || out.contains("right") || out.contains("left"))
                        && !out.contains("unsuccessful")) {
                    // Update the mutants' positions
                    System.out.println(moveEnemies());
                }
            }

            String checkState = checkState();
            if (checkState != null) {
                displayGameState();
                System.out.println(checkState);
                playing = false;
            }
        }
    }

    /**
     * Displays the current game state.
     */
    private void displayGameState() {
        System.out.println("Current game state:");
        for (int j = size - 1; j >= 0; j--) {
            for (int i = 0; i < size; i++) {
                if (map[i][j].getPlayer()) {
                    System.out.print("PLAY ");
                } else {
                    System.out.print(map[i][j].getType().getString() + " ");
                }
            }
            System.out.println();
        }

        System.out.println("Score: " + getScore());
        System.out.println("Player health: " + player.getHealth());
        System.out.println("Treasures collected: " + collectedTreasures);
        System.out.println("Steps: " + steps);
        System.out.println("Step Limit: " + stepLimit);
    }
}