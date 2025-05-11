package application;
	


import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

import game.engine.Battle;
import game.engine.BattlePhase;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.titans.AbnormalTitan;
import game.engine.titans.ArmoredTitan;
import game.engine.titans.ColossalTitan;
import game.engine.titans.PureTitan;
import game.engine.titans.Titan;
import game.engine.weapons.PiercingCannon;
import game.engine.weapons.SniperCannon;
import game.engine.weapons.VolleySpreadCannon;
import game.engine.weapons.WallTrap;
import game.engine.weapons.Weapon;
import game.engine.weapons.WeaponRegistry;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.paint.Color;

public class Main extends Application implements EventHandler<ActionEvent>{

		private Battle battleEasy;
		private Battle battleHard;
		
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane rootMenue = (AnchorPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			AnchorPane rootEasy = (AnchorPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			AnchorPane rootHard = (AnchorPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));

			Scene sceneMenue = new Scene(rootMenue,1000,600);
			Scene sceneEasy = new Scene(rootEasy,1000,600);
			Scene sceneHard = new Scene(rootHard,1000,600);
			
			sceneMenue.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setResizable(false);
			primaryStage.setTitle("Attack On Titan");
			/*Image icon = new Image("Icon.png");
			primaryStage.getIcons().add(icon);
			Image backgroundImage = new Image("BackgroundImage.png");
			BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
			BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, 
			                                                 BackgroundRepeat.NO_REPEAT, 
			                                                 BackgroundPosition.DEFAULT, 
			                                                 backgroundSize);
			rootMenue.setBackground(new Background(background));
			*/
			VBox Modes = new VBox();
			Button Easy = new Button("Easy");
			Button Hard = new Button("Hard");
			
			Easy.setPrefSize(130, 70);
			
			Hard.setPrefSize(130, 70);
		
			Modes.getChildren().addAll(Easy, Hard);

			Modes.setLayoutX(610);
		    Modes.setLayoutY(270);
		    Modes.setSpacing(20);
	        rootMenue.getChildren().add(Modes);
	      
			
            Easy.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					primaryStage.setScene(sceneEasy);
				}
			});
            Hard.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					primaryStage.setScene(sceneHard);
				}
			});
            
            //Instructions button
            Button instructionbutton = new Button("Open Instructions");
            instructionbutton.setStyle("-fx-font-size: 20px;");
            instructionbutton.setOnAction(new EventHandler<ActionEvent>() {
            	public void handle(ActionEvent event) {
            		Stage instructionsStage = new Stage();
            		instructionsStage.initOwner(primaryStage);
            		instructionsStage.setTitle("Instructions");
            		Label instructionsLabel = new Label("Attack on Titan is a tower defense type game.\r\n"
            				+ "There are two game modes (EASY and HARD).\r\n"
            				+ "\r\n"
            				+ "EASY mode: 3 lanes and 250 resources per lane\r\n"
            				+ "HARD mode: 5 lanes and 125 resources per lane");
            		StackPane instructionsPane = new StackPane(instructionsLabel);
            		Scene instructionsScene = new Scene(instructionsPane, 300, 200);
            		instructionsStage.setScene(instructionsScene);
            		instructionsStage.show();
                }
            });
            rootMenue.getChildren().add(instructionbutton);
            
            //create easy battle and extract 3 lanes
            
            try {
        		battleEasy = new Battle(1, 0, 400, 3, 250);
        	}
        	catch (IOException e){
        		
        	}
        	ArrayList<Lane> temp = battleEasy.getOriginalLanes();
        	Lane laneOne = temp.remove(0);
        	Lane laneTwo = temp.remove(0);
        	Lane laneThree = temp.remove(0);
			
			primaryStage.setScene(sceneMenue);
			primaryStage.show();
			
			//Easy Scene
			Pane lane1 = new Pane();
			Pane lane2 = new Pane();
			Pane lane3 = new Pane();
			
			lane1.setLayoutX(0);
			lane1.setMinSize(275, 400);
			lane1.setMaxSize(275, 400);
			
			lane2.setLayoutX(275);
			lane2.setMinSize(275, 400);
			lane2.setMaxSize(275, 400);
			
			lane3.setLayoutX(550);
			lane3.setMinSize(275, 400);
			lane3.setMaxSize(275, 400);
	
			rootEasy.getChildren().addAll(lane1, lane2, lane3);
			
			Label labelD1 = new Label("Danger: " + laneOne.getDangerLevel());
            Label labelD2 = new Label("Danger: " + laneTwo.getDangerLevel());
            Label labelD3 = new Label("Danger: " + laneThree.getDangerLevel());
            Label labelH1 = new Label("Health: " + laneOne.getLaneWall().getCurrentHealth());
            Label labelH2 = new Label("Health: " + laneTwo.getLaneWall().getCurrentHealth());
            Label labelH3 = new Label("Health: " + laneThree.getLaneWall().getCurrentHealth());
            Label labelW1 = new Label("Wall 1");
            Label labelW2 = new Label("Wall 2");
            Label labelW3 = new Label("Wall 3");
            VBox wall1 = new VBox(labelW1, labelD1, labelH1);
            VBox wall2 = new VBox(labelW2, labelD2, labelH2);
            VBox wall3 = new VBox(labelW3, labelD3, labelH3);
            wall1.setMinSize(275, 100);
            wall1.setMaxSize(275, 100);
            wall2.setMinSize(275, 100);
            wall2.setMaxSize(275, 100);
            wall3.setMinSize(275, 100);
            wall3.setMaxSize(275, 100);
            HBox allWalls = new HBox(wall1, wall2, wall3);
            allWalls.setMinSize(825, 100);
            allWalls.setMaxSize(825, 100);
            allWalls.setLayoutY(550);
            allWalls.setLayoutX(0);
            rootEasy.getChildren().add(allWalls);
            TilePane WS1 = new TilePane();
            TilePane WS2 = new TilePane();
            TilePane WS3 = new TilePane();
            WS1.setMaxSize(275,100);
            WS1.setMinSize(275,100);
            WS2.setMaxSize(275,100);
            WS2.setMinSize(275,100);
            WS3.setMaxSize(275,100);
            WS3.setMinSize(275,100);
            HBox allWeapons = new HBox(WS1,WS2,WS3);
            allWeapons.setMaxSize(825,100);
            allWeapons.setMinSize(825,100);
            allWeapons.setLayoutY(450);
            allWeapons.setLayoutX(0);
            rootEasy.getChildren().add(allWeapons);


            //weapon Registry
            WeaponRegistry WR1 = battleEasy.getWeaponFactory().getWeaponShop().get(1);
            WeaponRegistry WR2 = battleEasy.getWeaponFactory().getWeaponShop().get(2);
            WeaponRegistry WR3 = battleEasy.getWeaponFactory().getWeaponShop().get(3);
            WeaponRegistry WR4 = battleEasy.getWeaponFactory().getWeaponShop().get(4);
            
            String s1 ="Name: " + WR1.getName() + "\n" + "Type: Piercing Cannon\nRepresentation: PC" + "\n" + "Price: " + WR1.getPrice() + " | "+ "Damage: " + WR1.getDamage();
            String s2 ="Name: " + WR2.getName() + "\n" + "Type: Sniper Cannon\nRepresentation: SC" + "\n" + "Price: " + WR2.getPrice() + " | " + "Damage: " + WR2.getDamage();
            String s3 ="Name: " + WR3.getName() + "\n" + "Type: Volley Spread Cannon\nRepresentation: VSC" + "\n" + "Price: " + WR3.getPrice() + " | " + "Damage: " + WR3.getDamage()+ "\n" + "Range: " + WR3.getMinRange() + "-" + WR3.getMaxRange();
            String s4 ="Name: " + WR4.getName() + "\n" + "Type: Wall Trap\nRepresentation: WT" + "\n" + "Price: " + WR4.getPrice() + " | " + "Damage: " + WR4.getDamage();
            
            Button PassT = new Button("Pass Turn");
            PassT.setLayoutX(825);
            PassT.setPrefSize(175,100);
            Button w1 = new Button(s1);
            w1.setLayoutX(825);
            w1.setLayoutY(100);
            w1.setPrefSize(175,75);
            Button w2 = new Button(s2);
            w2.setLayoutX(825);
            w2.setLayoutY(175);
            w2.setPrefSize(175,75);
            Button w3 = new Button(s3);
            w3.setLayoutX(825);
            w3.setLayoutY(250);
            w3.setPrefSize(175,75);
            Button w4 = new Button(s4);
            w4.setLayoutX(825);
            w4.setLayoutY(325);
            w4.setPrefSize(175,75);
            rootEasy.getChildren().addAll(w1,w2,w3,w4,PassT);
            
            Label score = new Label("Score: "+battleEasy.getScore());
            Label turn = new Label("Turn: "+battleEasy.getNumberOfTurns());
            Label phase = new Label("Phase: "+battleEasy.getBattlePhase());
            Label resources = new Label("Resources: "+battleEasy.getResourcesGathered());
            Label nLanes = new Label("Active Lanes: "+ 3);
            Button exit = new Button("Exit to main menu");
            
            score.setLayoutX(825);
            score.setLayoutY(405);
            turn.setLayoutX(825);
            turn.setLayoutY(430);
            phase.setLayoutX(825);
            phase.setLayoutY(455);
            resources.setLayoutX(825);
            resources.setLayoutY(480);
            nLanes.setLayoutX(825);
            nLanes.setLayoutY(505);
            exit.setLayoutX(825);
            exit.setLayoutY(525);
            exit.setPrefSize(175, 75);
            rootEasy.getChildren().addAll(score, turn, phase, resources, exit, nLanes);
            
            //Buttons' Functions
            
            exit.setOnAction(new EventHandler<ActionEvent>() {
            	public void handle(ActionEvent event) {
            		Alert B = new Alert(AlertType.CONFIRMATION);
        			B.setTitle("Exit");
        			B.setHeaderText("Exit and return to main menu?");
        			B.setContentText("Are you sure you want to exit?\nYour progress will be lost.");
        			B.showAndWait();
        			start(primaryStage);
                }
            });
         
            PassT.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					battleEasy.passTurn();
					updateWeaponsTitansEasy(laneOne, laneTwo, laneThree, WS1, WS2, WS3, lane1, lane2, lane3);
					updateWallsEasy(laneOne, laneTwo, laneThree, labelD1, labelD2, labelD3, labelH1, labelH2, labelH3, nLanes);
					updateStatus(score, turn, phase, resources, battleEasy, primaryStage);
				}
			});
            Button b1 = new Button("Lane 1");
    		Button b2 = new Button("Lane 2");
    		Button b3 = new Button("Lane 3");
    		b1.setPrefSize(100, 50);
    		b2.setPrefSize(100, 50);
    		b3.setPrefSize(100, 50);
    		HBox chooseLaneBox = new HBox(b1,b2,b3);
    		chooseLaneBox.setPrefSize(300, 50);
    		Scene chooseLaneScene = new Scene(chooseLaneBox, 300, 50);
            w1.setOnAction(new EventHandler<ActionEvent>() {
            	public void handle(ActionEvent event) {
            		Stage chooseLaneStage = new Stage();
            		chooseLaneStage.initOwner(primaryStage);
            		chooseLaneStage.setTitle("Choose Lane");
            	
            		b1.setOnAction(new EventHandler<ActionEvent>() {
                     	public void handle(ActionEvent event) {
                     		try {
								battleEasy.purchaseWeapon(1, laneOne);
								updateWeaponsTitansEasy(laneOne, laneTwo, laneThree, WS1, WS2, WS3, lane1, lane2, lane3);
								updateWallsEasy(laneOne, laneTwo, laneThree, labelD1, labelD2, labelD3, labelH1, labelH2, labelH3, nLanes);
								updateStatus(score, turn, phase, resources, battleEasy, primaryStage);
							} catch (InsufficientResourcesException e) {
								Alert A = new Alert(AlertType.ERROR);
								A.setTitle("Error!");
								A.setContentText("You don't have enough resources");
								A.showAndWait();
							} catch (InvalidLaneException e) {
								Alert B = new Alert(AlertType.ERROR);
								B.setTitle("Error!");
								B.setContentText("Invalid  Lane");
								B.showAndWait();
							}
                     		chooseLaneStage.close();
                     	}
                     });
            		b2.setOnAction(new EventHandler<ActionEvent>() {
                     	public void handle(ActionEvent event) {
                     		try {
								battleEasy.purchaseWeapon(1, laneTwo);
								updateWeaponsTitansEasy(laneOne, laneTwo, laneThree, WS1, WS2, WS3, lane1, lane2, lane3);
								updateWallsEasy(laneOne, laneTwo, laneThree, labelD1, labelD2, labelD3, labelH1, labelH2, labelH3, nLanes);
								updateStatus(score, turn, phase, resources, battleEasy, primaryStage);
							} catch (InsufficientResourcesException e) {
								Alert A = new Alert(AlertType.ERROR);
								A.setTitle("Error!");
								A.setContentText("You don't have enough resources");
								A.showAndWait();
							} catch (InvalidLaneException e) {
								Alert B = new Alert(AlertType.ERROR);
								B.setTitle("Error!");
								B.setContentText("Invalid  Lane");
								B.showAndWait();
							}
                     		chooseLaneStage.close();
                     	}
                     });
            		b3.setOnAction(new EventHandler<ActionEvent>() {
                     	public void handle(ActionEvent event) {
                     		try {
								battleEasy.purchaseWeapon(1, laneThree);
								updateWeaponsTitansEasy(laneOne, laneTwo, laneThree, WS1, WS2, WS3, lane1, lane2, lane3);
								updateWallsEasy(laneOne, laneTwo, laneThree, labelD1, labelD2, labelD3, labelH1, labelH2, labelH3, nLanes);
								updateStatus(score, turn, phase, resources, battleEasy, primaryStage);
							} catch (InsufficientResourcesException e) {
								Alert A = new Alert(AlertType.ERROR);
								A.setTitle("Error!");
								A.setContentText("You don't have enough resources");
								A.showAndWait();
							} catch (InvalidLaneException e) {
								Alert B = new Alert(AlertType.ERROR);
								B.setTitle("Error!");
								B.setContentText("Invalid  Lane");
								B.showAndWait();
							}
                     		chooseLaneStage.close();
                     	}
                     });
            		chooseLaneStage.setScene(chooseLaneScene);
            		chooseLaneStage.show();
            	}

            });
            w2.setOnAction(new EventHandler<ActionEvent>() {
            	public void handle(ActionEvent event) {
            		Stage chooseLaneStage = new Stage();
            		chooseLaneStage.initOwner(primaryStage);
            		chooseLaneStage.setTitle("Choose Lane");
            		b1.setOnAction(new EventHandler<ActionEvent>() {
                     	public void handle(ActionEvent event) {
                     		try {
								battleEasy.purchaseWeapon(2, laneOne);
								updateWeaponsTitansEasy(laneOne, laneTwo, laneThree, WS1, WS2, WS3, lane1, lane2, lane3);
								updateWallsEasy(laneOne, laneTwo, laneThree, labelD1, labelD2, labelD3, labelH1, labelH2, labelH3, nLanes);
								updateStatus(score, turn, phase, resources, battleEasy, primaryStage);
							} catch (InsufficientResourcesException e) {
								Alert A = new Alert(AlertType.ERROR);
								A.setTitle("Error!");
								A.setContentText("You don't have enough resources");
								A.showAndWait();
							} catch (InvalidLaneException e) {
								Alert B = new Alert(AlertType.ERROR);
								B.setTitle("Error!");
								B.setContentText("Invalid  Lane");
								B.showAndWait();
							}
                     		chooseLaneStage.close();
                     	}
                     });
            		b2.setOnAction(new EventHandler<ActionEvent>() {
                     	public void handle(ActionEvent event) {
                     		try {
								battleEasy.purchaseWeapon(2, laneTwo);
								updateWeaponsTitansEasy(laneOne, laneTwo, laneThree, WS1, WS2, WS3, lane1, lane2, lane3);
								updateWallsEasy(laneOne, laneTwo, laneThree, labelD1, labelD2, labelD3, labelH1, labelH2, labelH3, nLanes);
								updateStatus(score, turn, phase, resources, battleEasy, primaryStage);
							} catch (InsufficientResourcesException e) {
								Alert A = new Alert(AlertType.ERROR);
								A.setTitle("Error!");
								A.setContentText("You don't have enough resources");
								A.showAndWait();
							} catch (InvalidLaneException e) {
								Alert B = new Alert(AlertType.ERROR);
								B.setTitle("Error!");
								B.setContentText("Invalid  Lane");
								B.showAndWait();
							}
                     		chooseLaneStage.close();
                     	}
                     });
            		b3.setOnAction(new EventHandler<ActionEvent>() {
                     	public void handle(ActionEvent event) {
                     		try {
								battleEasy.purchaseWeapon(2, laneThree);
								updateWeaponsTitansEasy(laneOne, laneTwo, laneThree, WS1, WS2, WS3, lane1, lane2, lane3);
								updateWallsEasy(laneOne, laneTwo, laneThree, labelD1, labelD2, labelD3, labelH1, labelH2, labelH3, nLanes);
								updateStatus(score, turn, phase, resources, battleEasy, primaryStage);
							} catch (InsufficientResourcesException e) {
								Alert A = new Alert(AlertType.ERROR);
								A.setTitle("Error!");
								A.setContentText("You don't have enough resources");
								A.showAndWait();
							} catch (InvalidLaneException e) {
								Alert B = new Alert(AlertType.ERROR);
								B.setTitle("Error!");
								B.setContentText("Invalid  Lane");
								B.showAndWait();
							}
                     		chooseLaneStage.close();
                     	}
                     });
            		chooseLaneStage.setScene(chooseLaneScene);
            		chooseLaneStage.show();
            	}
            });
            w3.setOnAction(new EventHandler<ActionEvent>() {
            	public void handle(ActionEvent event) {
            		Stage chooseLaneStage = new Stage();
            		chooseLaneStage.initOwner(primaryStage);
            		chooseLaneStage.setTitle("Choose Lane");
            		b1.setOnAction(new EventHandler<ActionEvent>() {
                     	public void handle(ActionEvent event) {
                     		try {
								battleEasy.purchaseWeapon(3, laneOne);
								updateWeaponsTitansEasy(laneOne, laneTwo, laneThree, WS1, WS2, WS3, lane1, lane2, lane3);
								updateWallsEasy(laneOne, laneTwo, laneThree, labelD1, labelD2, labelD3, labelH1, labelH2, labelH3, nLanes);
								updateStatus(score, turn, phase, resources, battleEasy, primaryStage);
							} catch (InsufficientResourcesException e) {
								Alert A = new Alert(AlertType.ERROR);
								A.setTitle("Error!");
								A.setContentText("You don't have enough resources");
								A.showAndWait();
							} catch (InvalidLaneException e) {
								Alert B = new Alert(AlertType.ERROR);
								B.setTitle("Error!");
								B.setContentText("Invalid  Lane");
								B.showAndWait();
							}
                     		chooseLaneStage.close();
                     	}
                     });
            		b2.setOnAction(new EventHandler<ActionEvent>() {
                     	public void handle(ActionEvent event) {
                     		try {
								battleEasy.purchaseWeapon(3, laneTwo);
								updateWeaponsTitansEasy(laneOne, laneTwo, laneThree, WS1, WS2, WS3, lane1, lane2, lane3);
								updateWallsEasy(laneOne, laneTwo, laneThree, labelD1, labelD2, labelD3, labelH1, labelH2, labelH3, nLanes);
								updateStatus(score, turn, phase, resources, battleEasy, primaryStage);
							} catch (InsufficientResourcesException e) {
								Alert A = new Alert(AlertType.ERROR);
								A.setTitle("Error!");
								A.setContentText("You don't have enough resources");
								A.showAndWait();
							} catch (InvalidLaneException e) {
								Alert B = new Alert(AlertType.ERROR);
								B.setTitle("Error!");
								B.setContentText("Invalid  Lane");
								B.showAndWait();
							}
                     		chooseLaneStage.close();
                     	}
                     });
            		b3.setOnAction(new EventHandler<ActionEvent>() {
                     	public void handle(ActionEvent event) {
                     		try {
								battleEasy.purchaseWeapon(3, laneThree);
								updateWeaponsTitansEasy(laneOne, laneTwo, laneThree, WS1, WS2, WS3, lane1, lane2, lane3);
								updateWallsEasy(laneOne, laneTwo, laneThree, labelD1, labelD2, labelD3, labelH1, labelH2, labelH3, nLanes);
								updateStatus(score, turn, phase, resources, battleEasy, primaryStage);
							} catch (InsufficientResourcesException e) {
								Alert A = new Alert(AlertType.ERROR);
								A.setTitle("Error!");
								A.setContentText("You don't have enough resources");
								A.showAndWait();
							} catch (InvalidLaneException e) {
								Alert B = new Alert(AlertType.ERROR);
								B.setTitle("Error!");
								B.setContentText("Invalid  Lane");
								B.showAndWait();
							}
                     		chooseLaneStage.close();
                     	}
                     });
            		chooseLaneStage.setScene(chooseLaneScene);
            		chooseLaneStage.show();
            	}
            });
            w4.setOnAction(new EventHandler<ActionEvent>() {
            	public void handle(ActionEvent event) {
            		Stage chooseLaneStage = new Stage();
            		chooseLaneStage.initOwner(primaryStage);
            		chooseLaneStage.setTitle("Choose Lane");
            		b1.setOnAction(new EventHandler<ActionEvent>() {
                     	public void handle(ActionEvent event) {
                     		try {
								battleEasy.purchaseWeapon(4, laneOne);
								updateWeaponsTitansEasy(laneOne, laneTwo, laneThree, WS1, WS2, WS3, lane1, lane2, lane3);
								updateWallsEasy(laneOne, laneTwo, laneThree, labelD1, labelD2, labelD3, labelH1, labelH2, labelH3, nLanes);
								updateStatus(score, turn, phase, resources, battleEasy, primaryStage);
							} catch (InsufficientResourcesException e) {
								Alert A = new Alert(AlertType.ERROR);
								A.setTitle("Error!");
								A.setContentText("You don't have enough resources");
								A.showAndWait();
							} catch (InvalidLaneException e) {
								Alert B = new Alert(AlertType.ERROR);
								B.setTitle("Error!");
								B.setContentText("Invalid  Lane");
								B.showAndWait();
							}
                     		chooseLaneStage.close();
                     	}
                     });
            		b2.setOnAction(new EventHandler<ActionEvent>() {
                     	public void handle(ActionEvent event) {
                     		try {
								battleEasy.purchaseWeapon(4, laneTwo);
								updateWeaponsTitansEasy(laneOne, laneTwo, laneThree, WS1, WS2, WS3, lane1, lane2, lane3);
								updateWallsEasy(laneOne, laneTwo, laneThree, labelD1, labelD2, labelD3, labelH1, labelH2, labelH3, nLanes);
								updateStatus(score, turn, phase, resources, battleEasy, primaryStage);
							} catch (InsufficientResourcesException e) {
								Alert A = new Alert(AlertType.ERROR);
								A.setTitle("Error!");
								A.setContentText("You don't have enough resources");
								A.showAndWait();
							} catch (InvalidLaneException e) {
								Alert B = new Alert(AlertType.ERROR);
								B.setTitle("Error!");
								B.setContentText("Invalid  Lane");
								B.showAndWait();
							}
                     		chooseLaneStage.close();
                     	}
                     });
            		b3.setOnAction(new EventHandler<ActionEvent>() {
                     	public void handle(ActionEvent event) {
                     		try {
								battleEasy.purchaseWeapon(4, laneThree);
								updateWeaponsTitansEasy(laneOne, laneTwo, laneThree, WS1, WS2, WS3, lane1, lane2, lane3);
								updateWallsEasy(laneOne, laneTwo, laneThree, labelD1, labelD2, labelD3, labelH1, labelH2, labelH3, nLanes);
								updateStatus(score, turn, phase, resources, battleEasy, primaryStage);
							} catch (InsufficientResourcesException e) {
								Alert A = new Alert(AlertType.ERROR);
								A.setTitle("Error!");
								A.setContentText("You don't have enough resources");
								A.showAndWait();
							} catch (InvalidLaneException e) {
								Alert B = new Alert(AlertType.ERROR);
								B.setTitle("Error!");
								B.setContentText("Invalid  Lane");
								B.showAndWait();
							}
                     		chooseLaneStage.close();
                     	}
                     });
            		chooseLaneStage.setScene(chooseLaneScene);
            		chooseLaneStage.show();
            	}
            });
            
            
        	///////////////////////////////////////////////////////////////////////////////////////
    		//Scene HARD
    		
    		try {
    			battleHard = new Battle(1, 0, 400, 5, 125);
        	}
        	catch (IOException e){
        		
        	}
    		ArrayList<Lane> tempH = battleHard.getOriginalLanes();
        	Lane laneOneH = tempH.remove(0);
        	Lane laneTwoH = tempH.remove(0);
        	Lane laneThreeH = tempH.remove(0);
        	Lane laneFourH = tempH.remove(0);
        	Lane laneFiveH = tempH.remove(0);
        	
        	Pane lane1H = new Pane();
    		Pane lane2H = new Pane();
    		Pane lane3H = new Pane();
    		Pane lane4H = new Pane();
    		Pane lane5H = new Pane();
    		
    		lane1H.setLayoutX(0);
    		lane1H.setMinSize(165, 400);
    		lane1H.setMaxSize(165, 400);
    		
    		lane2H.setLayoutX(165);
    		lane2H.setMinSize(165, 400);
    		lane2H.setMaxSize(165, 400);
    		
    		lane3H.setLayoutX(330);
    		lane3H.setMinSize(165, 400);
    		lane3H.setMaxSize(165, 400);
    		
    		lane4H.setLayoutX(495);
    		lane4H.setMinSize(165, 400);
    		lane4H.setMaxSize(165, 400);
    		
    		lane5H.setLayoutX(660);
    		lane5H.setMinSize(165, 400);
    		lane5H.setMaxSize(165, 400);
    		
    		rootHard.getChildren().addAll(lane1H, lane2H, lane3H, lane4H, lane5H);
    		
    		Label labelD1H = new Label("Danger: " + laneOneH.getDangerLevel());
            Label labelD2H = new Label("Danger: " + laneTwoH.getDangerLevel());
            Label labelD3H = new Label("Danger: " + laneThreeH.getDangerLevel());
            Label labelD4H = new Label("Danger: " + laneFourH.getDangerLevel());
            Label labelD5H = new Label("Danger: " + laneFiveH.getDangerLevel());
            
            Label labelH1H = new Label("Health: " + laneOneH.getLaneWall().getCurrentHealth());
            Label labelH2H = new Label("Health: " + laneTwoH.getLaneWall().getCurrentHealth());
            Label labelH3H = new Label("Health: " + laneThreeH.getLaneWall().getCurrentHealth());
            Label labelH4H = new Label("Health: " + laneFourH.getLaneWall().getCurrentHealth());
            Label labelH5H = new Label("Health: " + laneFiveH.getLaneWall().getCurrentHealth());      
            
            Label labelW1H = new Label("Wall 1");
            Label labelW2H = new Label("Wall 2");
            Label labelW3H = new Label("Wall 3");
            Label labelW4H = new Label("Wall 4");
            Label labelW5H = new Label("Wall 5");
            
            
            VBox wall1H = new VBox(labelW1H, labelD1H, labelH1H);
            VBox wall2H = new VBox(labelW2H, labelD2H, labelH2H);
            VBox wall3H = new VBox(labelW3H, labelD3H, labelH3H);
            VBox wall4H = new VBox(labelW4H, labelD4H, labelH4H);
            VBox wall5H = new VBox(labelW5H, labelD5H, labelH5H);
            
            wall1H.setMinSize(165, 100);
            wall1H.setMaxSize(165, 100);
            wall2H.setMinSize(165, 100);
            wall2H.setMaxSize(165, 100);
            wall3H.setMinSize(165, 100);
            wall3H.setMaxSize(165, 100);
            wall4H.setMinSize(165, 100);
            wall4H.setMaxSize(165, 100);
            wall5H.setMinSize(165, 100);
            wall5H.setMaxSize(165, 100);
            
            HBox allWallsH = new HBox(wall1H, wall2H, wall3H, wall4H, wall5H);
            allWallsH.setMinSize(825, 100);
            allWallsH.setMaxSize(825, 100);
            allWallsH.setLayoutY(550);
            allWallsH.setLayoutX(0);
            rootHard.getChildren().add(allWallsH);
            TilePane WS1H = new TilePane();
            TilePane WS2H = new TilePane();
            TilePane WS3H = new TilePane();
            TilePane WS4H = new TilePane();
            TilePane WS5H = new TilePane();
            WS1H.setMaxSize(165,100);
            WS1H.setMinSize(165,100);
            WS2H.setMaxSize(165,100);
            WS2H.setMinSize(165,100);
            WS3H.setMaxSize(165,100);
            WS3H.setMinSize(165,100);
            WS4H.setMaxSize(165,100);
            WS4H.setMinSize(165,100);
            WS5H.setMaxSize(165,100);
            WS5H.setMinSize(165,100);
            HBox allWeaponsH = new HBox(WS1H,WS2H,WS3H,WS4H,WS5H);
            allWeaponsH.setMaxSize(825,100);
            allWeaponsH.setMinSize(825,100);
            allWeaponsH.setLayoutY(450);
            allWeaponsH.setLayoutX(0);
            rootHard.getChildren().add(allWeaponsH);
            
          //weapon Registry Hard
            WeaponRegistry WR1H = battleHard.getWeaponFactory().getWeaponShop().get(1);
            WeaponRegistry WR2H = battleHard.getWeaponFactory().getWeaponShop().get(2);
            WeaponRegistry WR3H = battleHard.getWeaponFactory().getWeaponShop().get(3);
            WeaponRegistry WR4H = battleHard.getWeaponFactory().getWeaponShop().get(4);
            
            String s1H ="Name: " + WR1H.getName() + "\n" + "Type: Piercing Cannon\nRepresentation: PC" + "\n" + "Price: " + WR1H.getPrice() + " | "+ "Damage: " + WR1H.getDamage();
            String s2H ="Name: " + WR2H.getName() + "\n" + "Type: Sniper Cannon\nRepresentation: SC" + "\n" + "Price: " + WR2H.getPrice() + " | " + "Damage: " + WR2H.getDamage();
            String s3H ="Name: " + WR3H.getName() + "\n" + "Type: Volley Spread Cannon\nRepresentation: VSC" + "\n" + "Price: " + WR3H.getPrice() + " | " + "Damage: " + WR3H.getDamage()+ "\n" + "Range: " + WR3H.getMinRange() + "-" + WR3H.getMaxRange();
            String s4H ="Name: " + WR4H.getName() + "\n" + "Type: Wall Trap\nRepresentation: WT" + "\n" + "Price: " + WR4H.getPrice() + " | " + "Damage: " + WR4H.getDamage();
            
            Button PassTH = new Button("Pass Turn");
            PassTH.setLayoutX(825);
            PassTH.setPrefSize(175,100);
            Button w1H = new Button(s1H);
            w1H.setLayoutX(825);
            w1H.setLayoutY(100);
            w1H.setPrefSize(175,75);
            Button w2H = new Button(s2H);
            w2H.setLayoutX(825);
            w2H.setLayoutY(175);
            w2H.setPrefSize(175,75);
            Button w3H = new Button(s3H);
            w3H.setLayoutX(825);
            w3H.setLayoutY(250);
            w3H.setPrefSize(175,75);
            Button w4H = new Button(s4H);
            w4H.setLayoutX(825);
            w4H.setLayoutY(325);
            w4H.setPrefSize(175,75);
            rootHard.getChildren().addAll(w1H,w2H,w3H,w4H,PassTH);
            
            Label scoreH = new Label("Score: "+battleHard.getScore());
            Label turnH = new Label("Turn: "+battleHard.getNumberOfTurns());
            Label phaseH = new Label("Phase: "+battleHard.getBattlePhase());
            Label resourcesH = new Label("Resources: "+battleHard.getResourcesGathered());
            Label nLanesH = new Label("Active Lanes: "+ 5);
            Button exitH = new Button("Exit to main menu");
            
            scoreH.setLayoutX(825);
            scoreH.setLayoutY(405);
            turnH.setLayoutX(825);
            turnH.setLayoutY(430);
            phaseH.setLayoutX(825);
            phaseH.setLayoutY(455);
            resourcesH.setLayoutX(825);
            resourcesH.setLayoutY(480);
            nLanesH.setLayoutX(825);
            nLanesH.setLayoutY(505);
            exitH.setLayoutX(825);
            exitH.setLayoutY(525);
            exitH.setPrefSize(175, 75);
            rootHard.getChildren().addAll(scoreH, turnH, phaseH, resourcesH, exitH, nLanesH);
            
            //Buttons' Functions
            
            exitH.setOnAction(new EventHandler<ActionEvent>() {
            	public void handle(ActionEvent event) {
            		Alert B = new Alert(AlertType.CONFIRMATION);
        			B.setTitle("Exit");
        			B.setHeaderText("Exit and return to main menu?");
        			B.setContentText("Are you sure you want to exit?\nYour progress will be lost.");
        			B.showAndWait();
        			start(primaryStage);
                }
            });
         
            PassTH.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					battleHard.passTurn();
					updateWeaponsTitansHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, WS1H, WS2H, WS3H, WS4H, WS5H, lane1H, lane2H, lane3H, lane4H, lane5H);
					updateWallsHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, labelD1H, labelD2H, labelD3H, labelD4H, labelD5H, labelH1H, labelH2H, labelH3H, labelH4H, labelH5H, nLanesH);
					updateStatus(scoreH, turnH, phaseH, resourcesH, battleHard, primaryStage);
				}
			});
            Button b1H = new Button("Lane 1");
    		Button b2H = new Button("Lane 2");
    		Button b3H = new Button("Lane 3");
    		Button b4H = new Button("Lane 4");
    		Button b5H = new Button("Lane 5");
    		
    		b1H.setPrefSize(100, 50);
    		b2H.setPrefSize(100, 50);
    		b3H.setPrefSize(100, 50);
    		b4H.setPrefSize(100, 50);
    		b5H.setPrefSize(100, 50);
    		
    		HBox chooseLaneBoxH = new HBox(b1H,b2H,b3H,b4H,b5H);
    		chooseLaneBoxH.setPrefSize(500, 50);
    		Scene chooseLaneSceneH = new Scene(chooseLaneBoxH, 500, 50);
            w1H.setOnAction(new EventHandler<ActionEvent>() {
            	public void handle(ActionEvent event) {
            		Stage chooseLaneStageH = new Stage();
            		chooseLaneStageH.initOwner(primaryStage);
            		chooseLaneStageH.setTitle("Choose Lane");
            	
            		b1H.setOnAction(new EventHandler<ActionEvent>() {
                     	public void handle(ActionEvent event) {
                     		try {
								battleHard.purchaseWeapon(1, laneOneH);
								updateWeaponsTitansHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, WS1H, WS2H, WS3H, WS4H, WS5H, lane1H, lane2H, lane3H, lane4H, lane5H);
								updateWallsHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, labelD1H, labelD2H, labelD3H, labelD4H, labelD5H, labelH1H, labelH2H, labelH3H, labelH4H, labelH5H, nLanesH);
								updateStatus(scoreH, turnH, phaseH, resourcesH, battleHard, primaryStage);
							} catch (InsufficientResourcesException e) {
								Alert A = new Alert(AlertType.ERROR);
								A.setTitle("Error!");
								A.setContentText("You don't have enough resources");
								A.showAndWait();
							} catch (InvalidLaneException e) {
								Alert B = new Alert(AlertType.ERROR);
								B.setTitle("Error!");
								B.setContentText("Invalid  Lane");
								B.showAndWait();
							}
                     		chooseLaneStageH.close();
                     	}
                     });
            		b2H.setOnAction(new EventHandler<ActionEvent>() {
                     	public void handle(ActionEvent event) {
                     		try {
								battleHard.purchaseWeapon(1, laneTwoH);
								updateWeaponsTitansHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, WS1H, WS2H, WS3H, WS4H, WS5H, lane1H, lane2H, lane3H, lane4H, lane5H);
								updateWallsHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, labelD1H, labelD2H, labelD3H, labelD4H, labelD5H, labelH1H, labelH2H, labelH3H, labelH4H, labelH5H, nLanesH);
								updateStatus(scoreH, turnH, phaseH, resourcesH, battleHard, primaryStage);
							} catch (InsufficientResourcesException e) {
								Alert A = new Alert(AlertType.ERROR);
								A.setTitle("Error!");
								A.setContentText("You don't have enough resources");
								A.showAndWait();
							} catch (InvalidLaneException e) {
								Alert B = new Alert(AlertType.ERROR);
								B.setTitle("Error!");
								B.setContentText("Invalid  Lane");
								B.showAndWait();
							}
                     		chooseLaneStageH.close();
                     	}
                     });
            		b3H.setOnAction(new EventHandler<ActionEvent>() {
                     	public void handle(ActionEvent event) {
                     		try {
								battleHard.purchaseWeapon(1, laneThreeH);
								updateWeaponsTitansHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, WS1H, WS2H, WS3H, WS4H, WS5H, lane1H, lane2H, lane3H, lane4H, lane5H);
								updateWallsHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, labelD1H, labelD2H, labelD3H, labelD4H, labelD5H, labelH1H, labelH2H, labelH3H, labelH4H, labelH5H, nLanesH);
								updateStatus(scoreH, turnH, phaseH, resourcesH, battleHard, primaryStage);
							} catch (InsufficientResourcesException e) {
								Alert A = new Alert(AlertType.ERROR);
								A.setTitle("Error!");
								A.setContentText("You don't have enough resources");
								A.showAndWait();
							} catch (InvalidLaneException e) {
								Alert B = new Alert(AlertType.ERROR);
								B.setTitle("Error!");
								B.setContentText("Invalid  Lane");
								B.showAndWait();
							}
                     		chooseLaneStageH.close();
                     	}
                     });
            		b4H.setOnAction(new EventHandler<ActionEvent>() {
                     	public void handle(ActionEvent event) {
                     		try {
								battleHard.purchaseWeapon(1, laneFourH);
								updateWeaponsTitansHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, WS1H, WS2H, WS3H, WS4H, WS5H, lane1H, lane2H, lane3H, lane4H, lane5H);
								updateWallsHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, labelD1H, labelD2H, labelD3H, labelD4H, labelD5H, labelH1H, labelH2H, labelH3H, labelH4H, labelH5H, nLanesH);
								updateStatus(scoreH, turnH, phaseH, resourcesH, battleHard, primaryStage);
							} catch (InsufficientResourcesException e) {
								Alert A = new Alert(AlertType.ERROR);
								A.setTitle("Error!");
								A.setContentText("You don't have enough resources");
								A.showAndWait();
							} catch (InvalidLaneException e) {
								Alert B = new Alert(AlertType.ERROR);
								B.setTitle("Error!");
								B.setContentText("Invalid  Lane");
								B.showAndWait();
							}
                     		chooseLaneStageH.close();
                     	}
                     });
            		
            		b5H.setOnAction(new EventHandler<ActionEvent>() {
                     	public void handle(ActionEvent event) {
                     		try {
								battleHard.purchaseWeapon(1, laneFiveH);
								updateWeaponsTitansHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, WS1H, WS2H, WS3H, WS4H, WS5H, lane1H, lane2H, lane3H, lane4H, lane5H);
								updateWallsHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, labelD1H, labelD2H, labelD3H, labelD4H, labelD5H, labelH1H, labelH2H, labelH3H, labelH4H, labelH5H, nLanesH);
								updateStatus(scoreH, turnH, phaseH, resourcesH, battleHard, primaryStage);
							} catch (InsufficientResourcesException e) {
								Alert A = new Alert(AlertType.ERROR);
								A.setTitle("Error!");
								A.setContentText("You don't have enough resources");
								A.showAndWait();
							} catch (InvalidLaneException e) {
								Alert B = new Alert(AlertType.ERROR);
								B.setTitle("Error!");
								B.setContentText("Invalid  Lane");
								B.showAndWait();
							}
                     		chooseLaneStageH.close();
                     	}
                     });
            		chooseLaneStageH.setScene(chooseLaneSceneH);
            		chooseLaneStageH.show();
            	}

            });
            w2H.setOnAction(new EventHandler<ActionEvent>() {
            	public void handle(ActionEvent event) {
            		Stage chooseLaneStageH = new Stage();
            		chooseLaneStageH.initOwner(primaryStage);
            		chooseLaneStageH.setTitle("Choose Lane");
            	
            		b1H.setOnAction(new EventHandler<ActionEvent>() {
                     	public void handle(ActionEvent event) {
                     		try {
								battleHard.purchaseWeapon(2, laneOneH);
								updateWeaponsTitansHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, WS1H, WS2H, WS3H, WS4H, WS5H, lane1H, lane2H, lane3H, lane4H, lane5H);
								updateWallsHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, labelD1H, labelD2H, labelD3H, labelD4H, labelD5H, labelH1H, labelH2H, labelH3H, labelH4H, labelH5H, nLanesH);
								updateStatus(scoreH, turnH, phaseH, resourcesH, battleHard, primaryStage);
							} catch (InsufficientResourcesException e) {
								Alert A = new Alert(AlertType.ERROR);
								A.setTitle("Error!");
								A.setContentText("You don't have enough resources");
								A.showAndWait();
							} catch (InvalidLaneException e) {
								Alert B = new Alert(AlertType.ERROR);
								B.setTitle("Error!");
								B.setContentText("Invalid  Lane");
								B.showAndWait();
							}
                     		chooseLaneStageH.close();
                     	}
                     });
            		b2H.setOnAction(new EventHandler<ActionEvent>() {
                     	public void handle(ActionEvent event) {
                     		try {
								battleHard.purchaseWeapon(2, laneTwoH);
								updateWeaponsTitansHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, WS1H, WS2H, WS3H, WS4H, WS5H, lane1H, lane2H, lane3H, lane4H, lane5H);
								updateWallsHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, labelD1H, labelD2H, labelD3H, labelD4H, labelD5H, labelH1H, labelH2H, labelH3H, labelH4H, labelH5H, nLanesH);
								updateStatus(scoreH, turnH, phaseH, resourcesH, battleHard, primaryStage);
							} catch (InsufficientResourcesException e) {
								Alert A = new Alert(AlertType.ERROR);
								A.setTitle("Error!");
								A.setContentText("You don't have enough resources");
								A.showAndWait();
							} catch (InvalidLaneException e) {
								Alert B = new Alert(AlertType.ERROR);
								B.setTitle("Error!");
								B.setContentText("Invalid  Lane");
								B.showAndWait();
							}
                     		chooseLaneStageH.close();
                     	}
                     });
            		b3H.setOnAction(new EventHandler<ActionEvent>() {
                     	public void handle(ActionEvent event) {
                     		try {
								battleHard.purchaseWeapon(2, laneThreeH);
								updateWeaponsTitansHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, WS1H, WS2H, WS3H, WS4H, WS5H, lane1H, lane2H, lane3H, lane4H, lane5H);
								updateWallsHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, labelD1H, labelD2H, labelD3H, labelD4H, labelD5H, labelH1H, labelH2H, labelH3H, labelH4H, labelH5H, nLanesH);
								updateStatus(scoreH, turnH, phaseH, resourcesH, battleHard, primaryStage);
							} catch (InsufficientResourcesException e) {
								Alert A = new Alert(AlertType.ERROR);
								A.setTitle("Error!");
								A.setContentText("You don't have enough resources");
								A.showAndWait();
							} catch (InvalidLaneException e) {
								Alert B = new Alert(AlertType.ERROR);
								B.setTitle("Error!");
								B.setContentText("Invalid  Lane");
								B.showAndWait();
							}
                     		chooseLaneStageH.close();
                     	}
                     });
            		b4H.setOnAction(new EventHandler<ActionEvent>() {
                     	public void handle(ActionEvent event) {
                     		try {
								battleHard.purchaseWeapon(2, laneFourH);
								updateWeaponsTitansHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, WS1H, WS2H, WS3H, WS4H, WS5H, lane1H, lane2H, lane3H, lane4H, lane5H);
								updateWallsHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, labelD1H, labelD2H, labelD3H, labelD4H, labelD5H, labelH1H, labelH2H, labelH3H, labelH4H, labelH5H, nLanesH);
								updateStatus(scoreH, turnH, phaseH, resourcesH, battleHard, primaryStage);
							} catch (InsufficientResourcesException e) {
								Alert A = new Alert(AlertType.ERROR);
								A.setTitle("Error!");
								A.setContentText("You don't have enough resources");
								A.showAndWait();
							} catch (InvalidLaneException e) {
								Alert B = new Alert(AlertType.ERROR);
								B.setTitle("Error!");
								B.setContentText("Invalid  Lane");
								B.showAndWait();
							}
                     		chooseLaneStageH.close();
                     	}
                     });
            		
            		b5H.setOnAction(new EventHandler<ActionEvent>() {
                     	public void handle(ActionEvent event) {
                     		try {
								battleHard.purchaseWeapon(2, laneFiveH);
								updateWeaponsTitansHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, WS1H, WS2H, WS3H, WS4H, WS5H, lane1H, lane2H, lane3H, lane4H, lane5H);
								updateWallsHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, labelD1H, labelD2H, labelD3H, labelD4H, labelD5H, labelH1H, labelH2H, labelH3H, labelH4H, labelH5H, nLanesH);
								updateStatus(scoreH, turnH, phaseH, resourcesH, battleHard, primaryStage);
							} catch (InsufficientResourcesException e) {
								Alert A = new Alert(AlertType.ERROR);
								A.setTitle("Error!");
								A.setContentText("You don't have enough resources");
								A.showAndWait();
							} catch (InvalidLaneException e) {
								Alert B = new Alert(AlertType.ERROR);
								B.setTitle("Error!");
								B.setContentText("Invalid  Lane");
								B.showAndWait();
							}
                     		chooseLaneStageH.close();
                     	}
                     });
            		chooseLaneStageH.setScene(chooseLaneSceneH);
            		chooseLaneStageH.show();
            	}

            });
            
            w3H.setOnAction(new EventHandler<ActionEvent>() {
            	public void handle(ActionEvent event) {
            		Stage chooseLaneStageH = new Stage();
            		chooseLaneStageH.initOwner(primaryStage);
            		chooseLaneStageH.setTitle("Choose Lane");
            	
            		b1H.setOnAction(new EventHandler<ActionEvent>() {
                     	public void handle(ActionEvent event) {
                     		try {
								battleHard.purchaseWeapon(3, laneOneH);
								updateWeaponsTitansHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, WS1H, WS2H, WS3H, WS4H, WS5H, lane1H, lane2H, lane3H, lane4H, lane5H);
								updateWallsHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, labelD1H, labelD2H, labelD3H, labelD4H, labelD5H, labelH1H, labelH2H, labelH3H, labelH4H, labelH5H, nLanesH);
								updateStatus(scoreH, turnH, phaseH, resourcesH, battleHard, primaryStage);
							} catch (InsufficientResourcesException e) {
								Alert A = new Alert(AlertType.ERROR);
								A.setTitle("Error!");
								A.setContentText("You don't have enough resources");
								A.showAndWait();
							} catch (InvalidLaneException e) {
								Alert B = new Alert(AlertType.ERROR);
								B.setTitle("Error!");
								B.setContentText("Invalid  Lane");
								B.showAndWait();
							}
                     		chooseLaneStageH.close();
                     	}
                     });
            		b2H.setOnAction(new EventHandler<ActionEvent>() {
                     	public void handle(ActionEvent event) {
                     		try {
								battleHard.purchaseWeapon(3, laneTwoH);
								updateWeaponsTitansHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, WS1H, WS2H, WS3H, WS4H, WS5H, lane1H, lane2H, lane3H, lane4H, lane5H);
								updateWallsHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, labelD1H, labelD2H, labelD3H, labelD4H, labelD5H, labelH1H, labelH2H, labelH3H, labelH4H, labelH5H, nLanesH);
								updateStatus(scoreH, turnH, phaseH, resourcesH, battleHard, primaryStage);
							} catch (InsufficientResourcesException e) {
								Alert A = new Alert(AlertType.ERROR);
								A.setTitle("Error!");
								A.setContentText("You don't have enough resources");
								A.showAndWait();
							} catch (InvalidLaneException e) {
								Alert B = new Alert(AlertType.ERROR);
								B.setTitle("Error!");
								B.setContentText("Invalid  Lane");
								B.showAndWait();
							}
                     		chooseLaneStageH.close();
                     	}
                     });
            		b3H.setOnAction(new EventHandler<ActionEvent>() {
                     	public void handle(ActionEvent event) {
                     		try {
								battleHard.purchaseWeapon(3, laneThreeH);
								updateWeaponsTitansHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, WS1H, WS2H, WS3H, WS4H, WS5H, lane1H, lane2H, lane3H, lane4H, lane5H);
								updateWallsHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, labelD1H, labelD2H, labelD3H, labelD4H, labelD5H, labelH1H, labelH2H, labelH3H, labelH4H, labelH5H, nLanesH);
								updateStatus(scoreH, turnH, phaseH, resourcesH, battleHard, primaryStage);
							} catch (InsufficientResourcesException e) {
								Alert A = new Alert(AlertType.ERROR);
								A.setTitle("Error!");
								A.setContentText("You don't have enough resources");
								A.showAndWait();
							} catch (InvalidLaneException e) {
								Alert B = new Alert(AlertType.ERROR);
								B.setTitle("Error!");
								B.setContentText("Invalid  Lane");
								B.showAndWait();
							}
                     		chooseLaneStageH.close();
                     	}
                     });
            		b4H.setOnAction(new EventHandler<ActionEvent>() {
                     	public void handle(ActionEvent event) {
                     		try {
								battleHard.purchaseWeapon(3, laneFourH);
								updateWeaponsTitansHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, WS1H, WS2H, WS3H, WS4H, WS5H, lane1H, lane2H, lane3H, lane4H, lane5H);
								updateWallsHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, labelD1H, labelD2H, labelD3H, labelD4H, labelD5H, labelH1H, labelH2H, labelH3H, labelH4H, labelH5H, nLanesH);
								updateStatus(scoreH, turnH, phaseH, resourcesH, battleHard, primaryStage);
							} catch (InsufficientResourcesException e) {
								Alert A = new Alert(AlertType.ERROR);
								A.setTitle("Error!");
								A.setContentText("You don't have enough resources");
								A.showAndWait();
							} catch (InvalidLaneException e) {
								Alert B = new Alert(AlertType.ERROR);
								B.setTitle("Error!");
								B.setContentText("Invalid  Lane");
								B.showAndWait();
							}
                     		chooseLaneStageH.close();
                     	}
                     });
            		
            		b5H.setOnAction(new EventHandler<ActionEvent>() {
                     	public void handle(ActionEvent event) {
                     		try {
								battleHard.purchaseWeapon(3, laneFiveH);
								updateWeaponsTitansHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, WS1H, WS2H, WS3H, WS4H, WS5H, lane1H, lane2H, lane3H, lane4H, lane5H);
								updateWallsHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, labelD1H, labelD2H, labelD3H, labelD4H, labelD5H, labelH1H, labelH2H, labelH3H, labelH4H, labelH5H, nLanesH);
								updateStatus(scoreH, turnH, phaseH, resourcesH, battleHard, primaryStage);
							} catch (InsufficientResourcesException e) {
								Alert A = new Alert(AlertType.ERROR);
								A.setTitle("Error!");
								A.setContentText("You don't have enough resources");
								A.showAndWait();
							} catch (InvalidLaneException e) {
								Alert B = new Alert(AlertType.ERROR);
								B.setTitle("Error!");
								B.setContentText("Invalid  Lane");
								B.showAndWait();
							}
                     		chooseLaneStageH.close();
                     	}
                     });
            		chooseLaneStageH.setScene(chooseLaneSceneH);
            		chooseLaneStageH.show();
            	}

            });

            w4H.setOnAction(new EventHandler<ActionEvent>() {
            	public void handle(ActionEvent event) {
            		Stage chooseLaneStageH = new Stage();
            		chooseLaneStageH.initOwner(primaryStage);
            		chooseLaneStageH.setTitle("Choose Lane");
            	
            		b1H.setOnAction(new EventHandler<ActionEvent>() {
                     	public void handle(ActionEvent event) {
                     		try {
								battleHard.purchaseWeapon(4, laneOneH);
								updateWeaponsTitansHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, WS1H, WS2H, WS3H, WS4H, WS5H, lane1H, lane2H, lane3H, lane4H, lane5H);
								updateWallsHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, labelD1H, labelD2H, labelD3H, labelD4H, labelD5H, labelH1H, labelH2H, labelH3H, labelH4H, labelH5H, nLanesH);
								updateStatus(scoreH, turnH, phaseH, resourcesH, battleHard, primaryStage);
							} catch (InsufficientResourcesException e) {
								Alert A = new Alert(AlertType.ERROR);
								A.setTitle("Error!");
								A.setContentText("You don't have enough resources");
								A.showAndWait();
							} catch (InvalidLaneException e) {
								Alert B = new Alert(AlertType.ERROR);
								B.setTitle("Error!");
								B.setContentText("Invalid  Lane");
								B.showAndWait();
							}
                     		chooseLaneStageH.close();
                     	}
                     });
            		b2H.setOnAction(new EventHandler<ActionEvent>() {
                     	public void handle(ActionEvent event) {
                     		try {
								battleHard.purchaseWeapon(4, laneTwoH);
								updateWeaponsTitansHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, WS1H, WS2H, WS3H, WS4H, WS5H, lane1H, lane2H, lane3H, lane4H, lane5H);
								updateWallsHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, labelD1H, labelD2H, labelD3H, labelD4H, labelD5H, labelH1H, labelH2H, labelH3H, labelH4H, labelH5H, nLanesH);
								updateStatus(scoreH, turnH, phaseH, resourcesH, battleHard, primaryStage);
							} catch (InsufficientResourcesException e) {
								Alert A = new Alert(AlertType.ERROR);
								A.setTitle("Error!");
								A.setContentText("You don't have enough resources");
								A.showAndWait();
							} catch (InvalidLaneException e) {
								Alert B = new Alert(AlertType.ERROR);
								B.setTitle("Error!");
								B.setContentText("Invalid  Lane");
								B.showAndWait();
							}
                     		chooseLaneStageH.close();
                     	}
                     });
            		b3H.setOnAction(new EventHandler<ActionEvent>() {
                     	public void handle(ActionEvent event) {
                     		try {
								battleHard.purchaseWeapon(4, laneThreeH);
								updateWeaponsTitansHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, WS1H, WS2H, WS3H, WS4H, WS5H, lane1H, lane2H, lane3H, lane4H, lane5H);
								updateWallsHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, labelD1H, labelD2H, labelD3H, labelD4H, labelD5H, labelH1H, labelH2H, labelH3H, labelH4H, labelH5H, nLanesH);
								updateStatus(scoreH, turnH, phaseH, resourcesH, battleHard, primaryStage);
							} catch (InsufficientResourcesException e) {
								Alert A = new Alert(AlertType.ERROR);
								A.setTitle("Error!");
								A.setContentText("You don't have enough resources");
								A.showAndWait();
							} catch (InvalidLaneException e) {
								Alert B = new Alert(AlertType.ERROR);
								B.setTitle("Error!");
								B.setContentText("Invalid  Lane");
								B.showAndWait();
							}
                     		chooseLaneStageH.close();
                     	}
                     });
            		b4H.setOnAction(new EventHandler<ActionEvent>() {
                     	public void handle(ActionEvent event) {
                     		try {
								battleHard.purchaseWeapon(4, laneFourH);
								updateWeaponsTitansHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, WS1H, WS2H, WS3H, WS4H, WS5H, lane1H, lane2H, lane3H, lane4H, lane5H);
								updateWallsHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, labelD1H, labelD2H, labelD3H, labelD4H, labelD5H, labelH1H, labelH2H, labelH3H, labelH4H, labelH5H, nLanesH);
								updateStatus(scoreH, turnH, phaseH, resourcesH, battleHard, primaryStage);
							} catch (InsufficientResourcesException e) {
								Alert A = new Alert(AlertType.ERROR);
								A.setTitle("Error!");
								A.setContentText("You don't have enough resources");
								A.showAndWait();
							} catch (InvalidLaneException e) {
								Alert B = new Alert(AlertType.ERROR);
								B.setTitle("Error!");
								B.setContentText("Invalid  Lane");
								B.showAndWait();
							}
                     		chooseLaneStageH.close();
                     	}
                     });
            		
            		b5H.setOnAction(new EventHandler<ActionEvent>() {
                     	public void handle(ActionEvent event) {
                     		try {
								battleHard.purchaseWeapon(4, laneFiveH);
								updateWeaponsTitansHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, WS1H, WS2H, WS3H, WS4H, WS5H, lane1H, lane2H, lane3H, lane4H, lane5H);
								updateWallsHard(laneOneH, laneTwoH, laneThreeH, laneFourH, laneFiveH, labelD1H, labelD2H, labelD3H, labelD4H, labelD5H, labelH1H, labelH2H, labelH3H, labelH4H, labelH5H, nLanesH);
								updateStatus(scoreH, turnH, phaseH, resourcesH, battleHard, primaryStage);
							} catch (InsufficientResourcesException e) {
								Alert A = new Alert(AlertType.ERROR);
								A.setTitle("Error!");
								A.setContentText("You don't have enough resources");
								A.showAndWait();
							} catch (InvalidLaneException e) {
								Alert B = new Alert(AlertType.ERROR);
								B.setTitle("Error!");
								B.setContentText("Invalid  Lane");
								B.showAndWait();
							}
                     		chooseLaneStageH.close();
                     	}
                     });
            		chooseLaneStageH.setScene(chooseLaneSceneH);
            		chooseLaneStageH.show();
            	}

            });
            
          
            
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateStatus(Label score, Label turn, Label phase, Label resources, Battle battle, Stage ps) {
		score.setText("Score: "+battle.getScore());
		turn.setText("Turn: "+battle.getNumberOfTurns());
		phase.setText("Phase: "+battle.getBattlePhase());
		resources.setText("Resources: "+battle.getResourcesGathered());
		
		if(battle.isGameOver()) {
			Alert A = new Alert(AlertType.ERROR);
			A.setTitle("Game Over!");
			A.setHeaderText("You lose, Try again!\nScore: " + battle.getScore());
			A.setContentText("The game will reset.");
			A.showAndWait();
			start(ps);
		}
    }
	
	public void updateWallsEasy(Lane laneOne, Lane laneTwo, Lane laneThree, Label labelD1, Label labelD2, Label labelD3, Label labelH1, Label labelH2, Label labelH3, Label nLanes) {
		labelD1.setText("Danger: " + laneOne.getDangerLevel());
        labelD2.setText("Danger: " + laneTwo.getDangerLevel());
        labelD3.setText("Danger: " + laneThree.getDangerLevel());
        
        labelH1.setText("Health: " + laneOne.getLaneWall().getCurrentHealth());
        labelH2.setText("Health: " + laneTwo.getLaneWall().getCurrentHealth());
        labelH3.setText("Health: " + laneThree.getLaneWall().getCurrentHealth());
        
        
        int temp = 0;
        if (!laneOne.isLaneLost()) temp++;
        if (!laneTwo.isLaneLost()) temp++;
        if (!laneThree.isLaneLost()) temp++;
        nLanes.setText("Active Lanes: " + temp);
	}
	
	public void updateWallsHard(Lane laneOneH, Lane laneTwoH, Lane laneThreeH, Lane laneFourH, Lane laneFiveH, Label labelD1H, Label labelD2H, Label labelD3H, Label labelD4H, Label labelD5H, Label labelH1H, Label labelH2H, Label labelH3H, Label labelH4H, Label labelH5H, Label nLanesH) {
		labelD1H.setText("Danger: " + laneOneH.getDangerLevel());
        labelD2H.setText("Danger: " + laneTwoH.getDangerLevel());
        labelD3H.setText("Danger: " + laneThreeH.getDangerLevel());
        labelD4H.setText("Danger: " + laneFourH.getDangerLevel());
        labelD5H.setText("Danger: " + laneFiveH.getDangerLevel());
        
        labelH1H.setText("Health: " + laneOneH.getLaneWall().getCurrentHealth());
        labelH2H.setText("Health: " + laneTwoH.getLaneWall().getCurrentHealth());
        labelH3H.setText("Health: " + laneThreeH.getLaneWall().getCurrentHealth());
        labelH4H.setText("Health: " + laneFourH.getLaneWall().getCurrentHealth());
        labelH5H.setText("Health: " + laneFiveH.getLaneWall().getCurrentHealth());   
        
        
        int temp = 0;
        if (!laneOneH.isLaneLost()) temp++;
        if (!laneTwoH.isLaneLost()) temp++;
        if (!laneThreeH.isLaneLost()) temp++;
        if (!laneFourH.isLaneLost()) temp++;
        if (!laneFiveH.isLaneLost()) temp++;
        nLanesH.setText("Active Lanes: " + temp);
	}
	
	public void updateWeaponsTitansEasy(Lane laneOne, Lane laneTwo, Lane laneThree, TilePane WS1, TilePane WS2, TilePane WS3, Pane lane1, Pane lane2, Pane lane3) {
		WS1.getChildren().clear();
		WS2.getChildren().clear();
		WS3.getChildren().clear();
		if (!laneOne.isLaneLost()) {
			for (int i=0;i<laneOne.getWeapons().size();i++) {
				Weapon weapon = laneOne.getWeapons().get(i);
				String s = "";
				if (weapon instanceof PiercingCannon) {s="| PC ";}
				if (weapon instanceof SniperCannon) {s="| SC ";}
				if (weapon instanceof VolleySpreadCannon) {s="| VSC ";}
				if (weapon instanceof WallTrap) {s="| WT ";}
				Label l = new Label(s);
				WS1.getChildren().add(l);
			}
		}
		if (!laneTwo.isLaneLost()) {
			for (int i=0;i<laneTwo.getWeapons().size();i++) {
				Weapon weapon = laneTwo.getWeapons().get(i);
				String s = "";
				if (weapon instanceof PiercingCannon) {s="| PC ";}
				if (weapon instanceof SniperCannon) {s="| SC ";}
				if (weapon instanceof VolleySpreadCannon) {s="| VSC ";}
				if (weapon instanceof WallTrap) {s="| WT ";}
				Label l = new Label(s);
				WS2.getChildren().add(l);
			}
		}
		if (!laneThree.isLaneLost()) {
			for (int i=0;i<laneThree.getWeapons().size();i++) {
				Weapon weapon = laneThree.getWeapons().get(i);
				String s = "";
				if (weapon instanceof PiercingCannon) {s="| PC ";}
				if (weapon instanceof SniperCannon) {s="| SC ";}
				if (weapon instanceof VolleySpreadCannon) {s="| VSC ";}
				if (weapon instanceof WallTrap) {s="| WT ";}
				Label l = new Label(s);
				WS3.getChildren().add(l);
			}
		}
		
		
		
		lane1.getChildren().clear();
		lane2.getChildren().clear();
		lane3.getChildren().clear();
		if (!laneOne.isLaneLost()) {
			 for (Titan titan : laneOne.getTitans()) {
				String s = "";
				if (titan instanceof AbnormalTitan) {s="Abnormal\n" + titan.getCurrentHealth();}
				if (titan instanceof ArmoredTitan) {s="Armored\n" + titan.getCurrentHealth();}
				if (titan instanceof ColossalTitan) {s="Colossal\n" + titan.getCurrentHealth();}
				if (titan instanceof PureTitan) {s="Pure\n" + titan.getCurrentHealth();}
				Button titanPane = new Button(s);
				Random random = new Random();
				titanPane.setLayoutX(random.nextInt(190));
				titanPane.setLayoutY(400-titan.getDistance());
				lane1.getChildren().add(titanPane);
			}
		}
		else {
			lane1.setStyle("-fx-background-color: red;");
		}
		if (!laneTwo.isLaneLost()) {
			 for (Titan titan : laneTwo.getTitans()) {
				String s = "";
				if (titan instanceof AbnormalTitan) {s="Abnormal\n" + titan.getCurrentHealth();}
				if (titan instanceof ArmoredTitan) {s="Armored\n" + titan.getCurrentHealth();}
				if (titan instanceof ColossalTitan) {s="Colossal\n" + titan.getCurrentHealth();}
				if (titan instanceof PureTitan) {s="Pure\n" + titan.getCurrentHealth();}
				Button titanPane = new Button(s);
				Random random = new Random();
				titanPane.setLayoutX(random.nextInt(190));
				titanPane.setLayoutY(400-titan.getDistance());
				lane2.getChildren().add(titanPane);
			}
		}
		else {
			lane2.setStyle("-fx-background-color: red;");
		}
		if (!laneThree.isLaneLost()) {
			 for (Titan titan : laneThree.getTitans()) {
				String s = "";
				if (titan instanceof AbnormalTitan) {s="Abnormal\n" + titan.getCurrentHealth();}
				if (titan instanceof ArmoredTitan) {s="Armored\n" + titan.getCurrentHealth();}
				if (titan instanceof ColossalTitan) {s="Colossal\n" + titan.getCurrentHealth();}
				if (titan instanceof PureTitan) {s="Pure\n" + titan.getCurrentHealth();}
				Button titanPane = new Button(s);
				Random random = new Random();
				titanPane.setLayoutX(random.nextInt(190));
				titanPane.setLayoutY(400-titan.getDistance());
				lane3.getChildren().add(titanPane);
			}
		}
		else {
			lane3.setStyle("-fx-background-color: red;");
		}
	}
	
	public void updateWeaponsTitansHard(Lane laneOneH, Lane laneTwoH, Lane laneThreeH, Lane laneFourH, Lane laneFiveH, TilePane WS1H, TilePane WS2H, TilePane WS3H, TilePane WS4H, TilePane WS5H, Pane lane1H, Pane lane2H, Pane lane3H, Pane lane4H, Pane lane5H) {
		WS1H.getChildren().clear();
		WS2H.getChildren().clear();
		WS3H.getChildren().clear();
		WS4H.getChildren().clear();
		WS5H.getChildren().clear();
		if (!laneOneH.isLaneLost()) {
			for (int i=0;i<laneOneH.getWeapons().size();i++) {
				Weapon weapon = laneOneH.getWeapons().get(i);
				String s = "";
				if (weapon instanceof PiercingCannon) {s="| PC ";}
				if (weapon instanceof SniperCannon) {s="| SC ";}
				if (weapon instanceof VolleySpreadCannon) {s="| VSC ";}
				if (weapon instanceof WallTrap) {s="| WT ";}
				Label l = new Label(s);
				WS1H.getChildren().add(l);
			}
		}
		if (!laneTwoH.isLaneLost()) {
			for (int i=0;i<laneTwoH.getWeapons().size();i++) {
				Weapon weapon = laneTwoH.getWeapons().get(i);
				String s = "";
				if (weapon instanceof PiercingCannon) {s="| PC ";}
				if (weapon instanceof SniperCannon) {s="| SC ";}
				if (weapon instanceof VolleySpreadCannon) {s="| VSC ";}
				if (weapon instanceof WallTrap) {s="| WT ";}
				Label l = new Label(s);
				WS2H.getChildren().add(l);
			}
		}
		if (!laneThreeH.isLaneLost()) {
			for (int i=0;i<laneThreeH.getWeapons().size();i++) {
				Weapon weapon = laneThreeH.getWeapons().get(i);
				String s = "";
				if (weapon instanceof PiercingCannon) {s="| PC ";}
				if (weapon instanceof SniperCannon) {s="| SC ";}
				if (weapon instanceof VolleySpreadCannon) {s="| VSC ";}
				if (weapon instanceof WallTrap) {s="| WT ";}
				Label l = new Label(s);
				WS3H.getChildren().add(l);
			}
			
		}
		if (!laneFourH.isLaneLost()) {
			for (int i=0;i<laneFourH.getWeapons().size();i++) {
				Weapon weapon = laneFourH.getWeapons().get(i);
				String s = "";
				if (weapon instanceof PiercingCannon) {s="| PC ";}
				if (weapon instanceof SniperCannon) {s="| SC ";}
				if (weapon instanceof VolleySpreadCannon) {s="| VSC ";}
				if (weapon instanceof WallTrap) {s="| WT ";}
				Label l = new Label(s);
				WS4H.getChildren().add(l);
			}
			
		}
		if (!laneFiveH.isLaneLost()) {
			for (int i=0;i<laneFiveH.getWeapons().size();i++) {
				Weapon weapon = laneFiveH.getWeapons().get(i);
				String s = "";
				if (weapon instanceof PiercingCannon) {s="| PC ";}
				if (weapon instanceof SniperCannon) {s="| SC ";}
				if (weapon instanceof VolleySpreadCannon) {s="| VSC ";}
				if (weapon instanceof WallTrap) {s="| WT ";}
				Label l = new Label(s);
				WS5H.getChildren().add(l);
			}
			
		}

		lane1H.getChildren().clear();
		lane2H.getChildren().clear();
		lane3H.getChildren().clear();
		lane4H.getChildren().clear();
		lane5H.getChildren().clear();
		if (!laneOneH.isLaneLost()) {
			 for (Titan titan : laneOneH.getTitans()) {
				String s = "";
				if (titan instanceof AbnormalTitan) {s="Abnormal\n" + titan.getCurrentHealth();}
				if (titan instanceof ArmoredTitan) {s="Armored\n" + titan.getCurrentHealth();}
				if (titan instanceof ColossalTitan) {s="Colossal\n" + titan.getCurrentHealth();}
				if (titan instanceof PureTitan) {s="Pure\n" + titan.getCurrentHealth();}
				Button titanPane = new Button(s);
				Random random = new Random();
				titanPane.setLayoutX(random.nextInt(80));
				titanPane.setLayoutY(400-titan.getDistance());
				lane1H.getChildren().add(titanPane);
			}
		}
		else {
			lane1H.setStyle("-fx-background-color: red;");
		}
		if (!laneTwoH.isLaneLost()) {
			 for (Titan titan : laneTwoH.getTitans()) {
				String s = "";
				if (titan instanceof AbnormalTitan) {s="Abnormal\n" + titan.getCurrentHealth();}
				if (titan instanceof ArmoredTitan) {s="Armored\n" + titan.getCurrentHealth();}
				if (titan instanceof ColossalTitan) {s="Colossal\n" + titan.getCurrentHealth();}
				if (titan instanceof PureTitan) {s="Pure\n" + titan.getCurrentHealth();}
				Button titanPane = new Button(s);
				Random random = new Random();
				titanPane.setLayoutX(random.nextInt(80));
				titanPane.setLayoutY(400-titan.getDistance());
				lane2H.getChildren().add(titanPane);
			}
		}
		else {
			lane2H.setStyle("-fx-background-color: red;");
		}
		if (!laneThreeH.isLaneLost()) {
			 for (Titan titan : laneThreeH.getTitans()) {
				String s = "";
				if (titan instanceof AbnormalTitan) {s="Abnormal\n" + titan.getCurrentHealth();}
				if (titan instanceof ArmoredTitan) {s="Armored\n" + titan.getCurrentHealth();}
				if (titan instanceof ColossalTitan) {s="Colossal\n" + titan.getCurrentHealth();}
				if (titan instanceof PureTitan) {s="Pure\n" + titan.getCurrentHealth();}
				Button titanPane = new Button(s);
				Random random = new Random();
				titanPane.setLayoutX(random.nextInt(80));
				titanPane.setLayoutY(400-titan.getDistance());
				lane3H.getChildren().add(titanPane);
			}
		}
		else {
			lane3H.setStyle("-fx-background-color: red;");
		}
		if (!laneFourH.isLaneLost()) {
			 for (Titan titan : laneFourH.getTitans()) {
				String s = "";
				if (titan instanceof AbnormalTitan) {s="Abnormal\n" + titan.getCurrentHealth();}
				if (titan instanceof ArmoredTitan) {s="Armored\n" + titan.getCurrentHealth();}
				if (titan instanceof ColossalTitan) {s="Colossal\n" + titan.getCurrentHealth();}
				if (titan instanceof PureTitan) {s="Pure\n" + titan.getCurrentHealth();}
				Button titanPane = new Button(s);
				Random random = new Random();
				titanPane.setLayoutX(random.nextInt(80));
				titanPane.setLayoutY(400-titan.getDistance());
				lane4H.getChildren().add(titanPane);
			}
		}
		else {
			lane4H.setStyle("-fx-background-color: red;");
		}
		if (!laneFiveH.isLaneLost()) {
			 for (Titan titan : laneFiveH.getTitans()) {
				String s = "";
				if (titan instanceof AbnormalTitan) {s="Abnormal\n" + titan.getCurrentHealth();}
				if (titan instanceof ArmoredTitan) {s="Armored\n" + titan.getCurrentHealth();}
				if (titan instanceof ColossalTitan) {s="Colossal\n" + titan.getCurrentHealth();}
				if (titan instanceof PureTitan) {s="Pure\n" + titan.getCurrentHealth();}
				Button titanPane = new Button(s);
				Random random = new Random();
				titanPane.setLayoutX(random.nextInt(80));
				titanPane.setLayoutY(400-titan.getDistance());
				lane5H.getChildren().add(titanPane);
			}
		}
		else {
			lane5H.setStyle("-fx-background-color: red;");
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
