package net.mrjaywilson.fatball.entities;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball extends Circle {
	
	private int lives;
	
	public Ball(double radius) {
		this.setRadius(radius);
		this.setFill(Color.WHITE);
		
		lives = 3;
	}
	
	public void resetPosition(Pane pane) {
		this.setLayoutX(pane.getWidth() / 2);
		this.setLayoutY(100);
	}
	
	public void resetSize(double size) {
		this.setRadius(size);
	}
	
	public void resetLives() {
		lives = 3;
	}
	
	public void addLife() {
		lives += 1;
	}
	
	public void removeLife() {
		lives -= 1;
	}
	
	public int getLives() {
		return this.lives;
	}
}
