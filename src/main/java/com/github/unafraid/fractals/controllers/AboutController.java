package com.github.unafraid.fractals.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Created by UnAfraid on 7.6.2015 г..
 */
public class AboutController
{

	@FXML
	public void onOkButton(ActionEvent event)
	{
		((Button) event.getSource()).getScene().getWindow().hide();
	}
}
