package net.mrjaywilson.fatball.entities;

import java.util.concurrent.ThreadLocalRandom;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Enemy extends Rectangle {
	
	private int value;
	
	public Enemy(double width, double height, int value) {
		
		// Damage Value
		this.value = value;
		
		setWidth(width);
		setHeight(height);
		setFill(Color.LIGHTCORAL);
	}
	
	public void setEnemyRandomLocation(
			double startX, 
			double startY, 
			double sceneWidth, 
			double sceneHeight,
			Bounds ballBounds) {
		
		double x = ThreadLocalRandom.current().nextDouble(0, sceneWidth - getWidth());
		double y = ThreadLocalRandom.current().nextDouble(0, sceneHeight - getHeight());
		
		setLayoutX(x);
		setLayoutY(y);
		
		// don't let move into circle
		while (getBoundsInParent().intersects(ballBounds)) {
			x = (ThreadLocalRandom.current().nextDouble(0, sceneWidth - getWidth()));
			y = (ThreadLocalRandom.current().nextDouble(0, sceneHeight - getHeight()));
		
			setLayoutX(x);
			setLayoutY(y);
		}
		
	}
}
