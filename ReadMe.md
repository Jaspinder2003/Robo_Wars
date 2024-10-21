# Robot Wars Simulation Map Editor

**Note**: If you are looking for the Robo Wars project that I mentioned on my resume where I worked in a team, please refer to the **Floor Occupancy Tracker**. This project is a different one whose name I accidentally added.

## Project Overview

This project is a **JavaFX-based Map Editor** for the **Robot Wars Simulation** game. It allows users to create, edit, load, and save maps that are used in a robot battle simulation. The project exposes students to **event-driven**, **object-oriented programming** with a graphical user interface built using **JavaFX** and **SceneBuilder 2.0**.

## Features

- **Map Editing**: Users can create and edit a map where robots battle in a grid-based layout.
- **Save and Load Maps**: Save maps to text files and load them back for editing.
- **GUI Integration**: Includes a graphical interface built using JavaFX and SceneBuilder.
- **Menu Bar Operations**: Load, save, and quit options via the menu bar.
- **Entity Management**: Add or delete entities such as Predacons, Maximals, and walls from the map.
- **Entity Information**: View details of specific entities in the world (e.g., health, position, and weapon type).

## Technologies Used

- **Java 21**
- **JavaFX** for the GUI
- **SceneBuilder 2.0** for designing the interface
- **Git** for version control

## File Structure

- `Main.java`, `MainController.java`, `Main.fxml`: Handles the GUI interface and interactions.
- `Reader.java`, `Writer.java`: For reading from and writing to map files.
- `Entity.java`, `Robot.java`, `Maximal.java`, `PredaCon.java`: Classes for managing game entities.
- `World.java`: Manages the grid where entities are placed.

## Setup & Running the Project

1. **Install JavaFX SDK**: Download the JavaFX SDK from [here](https://gluonhq.com/products/javafx/) and configure it in your IDE.
   
2. **Running the Project**: You can run the project via your IDE. Here is an example command to run the project using the command line:
    ```bash
    java --module-path "path_to_javafx_sdk/lib" --add-modules javafx.controls,javafx.fxml rw.app.Main
    ```
   Replace `"path_to_javafx_sdk/lib"` with the actual path to your JavaFX SDK installation.

3. **Building a JAR File**: Once the project is complete, you can build a `.jar` file. Here is an example command to run the JAR:
    ```bash
    java --module-path "path_to_javafx_sdk/lib" --add-modules javafx.controls,javafx.fxml -jar CPSC233W24A3.jar
    ```

4. **Using the Map Editor**:
    - **Create a Map**: Specify the number of rows and columns for the grid and start placing entities (robots and walls) using the GUI.
    - **Save and Load Maps**: Use the menu bar to save your current map or load a previously saved map for further editing.
    - **View Entity Details**: Click on any entity in the grid to see details such as its type, health, and weapon.

## Error Handling and Future Deployment

- The project is currently handling a few UI-based errors related to input validation and map rendering.
- The deployment will happen once the issues are resolved.

## Submission

1. **Main.java**, **MainController.java**, **Main.fxml**: These files manage the core GUI functionality.
2. **Reader.java**, **Writer.java**: For loading and saving map files.
3. **CPSC233W24A3.jar**: A packaged JAR file of the project.
4. **GitLab Repository**: Ensure that your GitLab repository is private, and invite your TA as a Developer.

## Author

- **Jaspinder Singh Maan**
