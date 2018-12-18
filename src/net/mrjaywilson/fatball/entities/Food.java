package net.mrjaywilson.fatball.entities;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Food extends Rectangle {
	private int value;
	
	public Food(double width, double height, int value) {
		
		// The score value
		this.value = 10;

		setWidth(width);
		setHeight(height);
		setFill(Color.LIGHTBLUE);
	}
	
	public void resetPosition() {
		setLayoutX((getParent().getBoundsInParent().getWidth() / 2) - 25);
		setLayoutY((getParent().getBoundsInParent().getHeight() / 2) - 25);
	}
}
