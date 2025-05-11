# Attack on Titan Tower Defense Game

A tower defense game inspired by the popular anime series "Attack on Titan", built with Java and JavaFX. Defend your walls against approaching titans by strategically placing ODM-equipped soldiers, artillery, and other defenses.

## Features

- **Multiple Character Classes**: Deploy different types of soldiers (Scouts, Garrison, Military Police) each with unique abilities
- **Titan Variety**: Face different types of titans with varying speeds, health, and special abilities
- **Interactive Map System**: Multiple maps with different terrain features affecting gameplay
- **Upgrade System**: Improve your defenses over time as you progress
- **Special Abilities**: Activate ultimate abilities like Eren's titan transformation or Levi's special attack
- **Resource Management**: Balance your resources between building defenses and upgrading units
- **Wave-based Progression**: Increasingly difficult waves of titans to test your strategy

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 11 or newer
- JavaFX SDK
- Git (optional, for cloning the repository)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Sirye8/aot-td-game.git
2. Navigate to the project directory:
   ```bash
   cd aot-td-game
3. Compile and run the application:
   ```bash
   # If using Maven:
   mvn javafx:run

   # If using Gradle:
   gradle run

   # Or using raw Java commands:
   javac -d out --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml src/**/*.java
   java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml -cp out com.aottpgame.Main
