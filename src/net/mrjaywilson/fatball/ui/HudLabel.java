package net.mrjaywilson.fatball.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class HudLabel extends Label {
	public HudLabel(Pane root, String text, int fontSize, double locationX, double locationY) {
		setPrefHeight(48);
		setPrefWidth(250);
		setFont(Font.font("Century Gothic", fontSize));

		setText(text);
		root.getChildren().add(this);
		setLayoutX(locationX);
		setLayoutY(locationY);
	}
}
