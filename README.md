# Bomberman-X

Bomberman-X is a JavaFX-based clone of the classic Bomberman game, supporting multiple players, keyboard controls, and a variety of power-ups and map features. The game is designed for both single and multiplayer experiences, with a focus on fast-paced, strategic gameplay.

## Features

- **Classic Bomberman Gameplay**: Place bombs, destroy blocks, and defeat opponents.
- **Multiple Players**: Supports up to 4 players, each with unique controls.
- **Continuous Movement**: Hold down arrow keys to keep moving in a direction.
- **Movement Throttling**: Movement speed is regulated for smooth gameplay.
- **Power-Ups and Gifts**: Collect power-ups like Bomb Boosters and Bomb Adders to gain advantages.
- **Random Events**: Gifts and special tiles appear randomly during the game.
- **Multiple Maps**: Choose from different maps for varied gameplay.
- **Game Timer**: Each game session is timed, and the winner is determined at the end.
- **Hall of Fame**: View top scores and player achievements.
- **User Selection**: Select and manage players before starting a game.
- **JavaFX UI**: Modern, responsive user interface with custom graphics and animations.

## Gameplay

1. **Start the Game**: Launch the application using `mvn clean javafx:run` or from your IDE (ensure JavaFX is configured).
2. **Main Menu**: Choose to start a new game, view the Hall of Fame, or exit.
3. **Player Selection**: Select up to 4 players from the user list.
4. **Map Selection**: Choose a map for the game.
5. **Game Controls**:
   - **Movement**: Use the arrow keys to move your character. Hold a key to keep moving in that direction.
   - **Bomb Placement**: Press the designated bomb key (e.g., Enter or Command) to place a bomb.
   - **Power-Ups**: Collect gifts and power-ups that appear randomly on the map.
6. **Objective**: Eliminate other players by strategically placing bombs and using power-ups. Avoid getting caught in explosions.
7. **Winning**: The last player alive or the player with the highest score when the timer ends wins the game.

## How to Run

1. **Requirements**:
   - Java 11 or higher
   - Maven
   - JavaFX SDK (if running from IDE, ensure JavaFX libraries are configured)

2. **Run from Terminal**:
   ```sh
   mvn clean javafx:run
   ```

3. **Run from IDE**:
   - Make sure your run configuration includes JavaFX modules (see [JavaFX documentation](https://openjfx.io/openjfx-docs/)).
   - If you see errors about missing JavaFX runtime, add VM options like:
     ```
     --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml
     ```

## Project Structure

- `src/main/java/ir/javageek/` - Main Java source code
- `src/main/resources/assets/` - Game images, maps, and media
- `pom.xml` - Maven build file

## Troubleshooting

- **JavaFX Runtime Errors**: If you get errors about missing JavaFX runtime, ensure you have the JavaFX SDK and your run configuration includes the correct module path and modules.
- **IDE Issues**: Running from the terminal with Maven is the most reliable. For IDE, check your run configuration as above.

## License

This project is for educational and non-commercial use only. All assets are property of their respective owners.

---
Enjoy playing Bomberman-X!

