package net.mrjaywilson.fatball.main;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import net.mrjaywilson.fatball.entities.Ball;
import net.mrjaywilson.fatball.entities.Enemy;
import net.mrjaywilson.fatball.entities.Food;
import net.mrjaywilson.fatball.ui.HudLabel;

public class FatBall extends Application {
	int totalScore = 0;
	Boolean isRunning = false;

	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	HudLabel score;
	HudLabel enemyCounter;
	HudLabel lives;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage fatBall) throws Exception { 
		Pane root = new Pane();
		root.setStyle("-fx-background: black");
		Scene scene = new Scene(root, 500, 500);
		
		// Fatball
		Ball ball = new Ball(10f);
		root.getChildren().add(ball);
		
		// Good Square
		Food food = new Food(20f, 20f, 100);
		
		// Add Food to scene
		root.getChildren().add(food);
		
		// Set location of food
		food.setLayoutX(scene.getWidth() / 2);
		food.setLayoutY(scene.getHeight() / 2);

		// Labels for HUD
		score = new HudLabel(root, "SCORE\n" + totalScore, 14, 50, scene.getHeight() - 48);
		enemyCounter = new HudLabel(root, "Enemy Count\n" + enemies.size(), 14, 150, scene.getHeight() - 48);
		lives = new HudLabel(root, "Lives (1 free every 1,000 points!)\n" + ball.getLives(), 14, 300, scene.getHeight() - 48);

		HudLabel gameOver = new HudLabel(root, "GAME OVER!\nClick to restart.", 24, scene.getWidth() / 2, scene.getHeight() / 2);
		gameOver.setVisible(false);
		gameOver.setMinHeight(100);
		gameOver.setMinWidth(400);
		
		gameOver.setTextAlignment(TextAlignment.CENTER);

		gameOver.setLayoutX(root.getWidth() / 2 - 75);
		gameOver.setLayoutY(root.getHeight() / 2 - 50);

		HudLabel welcomeToFatball = new HudLabel(root, "WELCOME TO FAT BALL!\n\nCLICK TO PLAY", 24, scene.getWidth() / 2, scene.getHeight() / 2);
		welcomeToFatball.setVisible(true);
		welcomeToFatball.setMinHeight(100);
		welcomeToFatball.setMinWidth(400);
		
		welcomeToFatball.setTextAlignment(TextAlignment.CENTER);

		welcomeToFatball.setLayoutX((root.getWidth() / 2) - 125);
		welcomeToFatball.setLayoutY((root.getHeight() / 2) - 50);

		
		AnimationTimer animator = new AnimationTimer(){

			@Override
			public void handle(long now) {


				if (isRunning == false) {
					ball.resetLives();
					ball.resetSize(10);
					
					food.resetPosition();
					
					isRunning = true;
				}

				// When game starts or restarts
				welcomeToFatball.setVisible(false);
				
				// Keep the ball inside the window, even if resized
				if ((ball.getLayoutX() + ball.getRadius()) > scene.getWidth()) {
					ball.setLayoutX(scene.getWidth() - ball.getRadius());
				} 
				
				if (ball.getLayoutX() - ball.getRadius() < 0) {
					ball.setLayoutX(0 + ball.getRadius());
				}
				
				if ((ball.getLayoutY() + ball.getRadius()) > scene.getHeight()) {
					ball.setLayoutY(scene.getHeight() - ball.getRadius());
				}

				if (ball.getLayoutY() - ball.getRadius() < 0) {
					ball.setLayoutY(0 + ball.getRadius());
				}
				
				if (ball.getBoundsInParent().intersects(food.getBoundsInParent())) {

					// Update Score
					totalScore += 25;
					
					if (totalScore % 1000 == 0) {
						ball.addLife();
						lives.setText("Lives (1 free every 1,000 points!)\n" + ball.getLives());
					}
					
					score.setText("SCORE\n" + totalScore);
					
					// Move food to another location
					food.relocate(
							ThreadLocalRandom.current().nextDouble(0, scene.getWidth() - food.getWidth()),
							ThreadLocalRandom.current().nextDouble(0, scene.getHeight() - food.getHeight()));
					
					enemies.add(new Enemy(10f, 10f, 25));
					root.getChildren().add(enemies.get(enemies.size() - 1));

					enemyCounter.setText("Enemy Count\n" + enemies.size());				

					for (Enemy enemy : enemies) {
						enemy.setEnemyRandomLocation(
								0, 
								0, 
								scene.getWidth(), 
								scene.getHeight(), 
								ball.getBoundsInParent());
						
					}

					// Increase ball size
					ball.setRadius(ball.getRadius() + .125);
				}
				
				for (Enemy enemy : enemies) {
					if (ball.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
						
						ball.removeLife();
						lives.setText("Lives (1 free every 1,000 points!)\n" + ball.getLives());
						
						if (ball.getLives() < 0) {
							ball.setFill(Color.RED);
							lives.setText("Lives (1 free every 1,000 points!)\n" + (ball.getLives() + 1));
							isRunning = false;
							
							gameOver.setVisible(true);
							
							this.stop();
						}
						
						enemy.setEnemyRandomLocation(
								0, 
								0, 
								scene.getWidth(), 
								scene.getHeight(), 
								ball.getBoundsInParent());
					}
				}
			}
		};
		
		root.addEventFilter(MouseEvent.ANY, e-> {
			// Make the ball follow the mouse, and ensure mouse is at about center
			// of the ball.
			if (MouseEvent.MOUSE_MOVED != null) {
				ball.relocate(e.getX() - (ball.getRadius() / 2), e.getY() - (ball.getRadius() / 2));
			}
			
			if (e.isPrimaryButtonDown() == true && isRunning == false) {
				root.getChildren().removeAll(enemies);
				enemies.clear();
				
				gameOver.setVisible(false);
				
				ball.resetLives();
				ball.setFill(Color.WHITE);

				totalScore = 0;
				
				animator.start();
			}
		});
		
		//animator.start();

		scene.setCursor(Cursor.NONE);
		scene.setFill(Color.BLACK);
		fatBall = new Stage();
		fatBall.setTitle("Fat Ball - Jay Wilson");
		fatBall.setScene(scene);
		fatBall.show();
	}
}
