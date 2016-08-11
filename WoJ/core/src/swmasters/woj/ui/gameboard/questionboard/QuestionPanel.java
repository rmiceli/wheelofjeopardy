package swmasters.woj.ui.gameboard.questionboard;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.utils.Align;

import swmasters.woj.core.Question;

public class QuestionPanel extends WidgetGroup {
	private String question;
	private Dialog dialog;
	
	/**
	 * Construct a Question Panel widget
	 * 
	 * @param questionInput
	 */
	public QuestionPanel(final Question questionInput){
	//public QuestionPanel(String questionInput){
		question = questionInput.getQuestion();
		// Create a default font
		BitmapFont font = new BitmapFont();
		
		// Create a label style for the question label
		LabelStyle labelStyle = new LabelStyle(font, Color.WHITE);
		Label questionLabel = new Label(question, labelStyle);
		questionLabel.setAlignment(Align.center);
		labelStyle.fontColor = Color.WHITE;
		
		// Create a tile skin and texture for the submit button
		Skin tileSkin = new Skin();
		Texture tex = new Texture(Gdx.files.internal("Assets/graphics/background.png"));
		tex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		tileSkin.add("white", tex);
		tileSkin.add("default", new BitmapFont());
		
		// Create a text button style for the submit button
		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.up = tileSkin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.down = tileSkin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.checked = tileSkin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.over = tileSkin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = font;
		textButtonStyle.font.getData().setScale(3, 3);
		textButtonStyle.fontColor = Color.WHITE;
		tileSkin.add("default", textButtonStyle);
		
		// Create a new text button for the submit button
		TextButton submit = new TextButton("Submit", tileSkin);
		// Create a button table to hold the submit button
		float btnSize = 150f;
		Table buttonTable = new Table();
	    Table contentsTable = new Table();
	    contentsTable.setWidth(500);
	    // Add the submit button to the button table
	    buttonTable.add(submit).width(btnSize+100).height(btnSize);
	    
	    // Create a text field style for player's answer text field
		TextFieldStyle style = new TextFieldStyle();
		style.fontColor = Color.WHITE;
		style.font = new BitmapFont();
		style.font.getData().scale(2);
		
		// Create the text field for the player's answer
		final TextField answerBox = new TextField("", style);
	    answerBox.setMessageText("Your answer");
	    
	    // Add the question label and the answer text field to the contents table
	    contentsTable.add(questionLabel);
	    contentsTable.row();
	    contentsTable.add(answerBox).expand().fill();
	    
	    // Create the question dialog box
		Skin skinDialog = new Skin(Gdx.files.internal("Assets/skins/default/uiskin.json"));
		dialog = new Dialog("", skinDialog) {
			@Override
			public float getPrefWidth(){
				// dialog width
				return 700f;
			}
			@Override
			public float getPrefHeight(){
				// dialog height
				return 400f;
			}
		};
		dialog.setModal(true);
		dialog.setMovable(false);
		dialog.setResizable(false);
	    
		// Add the content table and button table to the dialog box
	    dialog.getContentTable().add(contentsTable).center();
		dialog.getButtonTable().add(buttonTable).center().padBottom(60f);	
		dialog.setName("questionPanelDialog");
		
		// Listen for user clicking the submit button
		submit.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				// From here, should return the answer to the method calling it
				// The method calling it should call the answer checking method with the box text as an argument
				System.out.println(answerBox.getText());
				questionInput.verifyAnswer(question);
				
				dialog.hide();
				dialog.cancel();
				dialog.remove();
				return true;
			}
		});
	}
	
	/**
	 * Show the dialog on the main game screen
	 * 
	 * @param stage
	 */
	public void show(Stage stage){
		/** TODO Figure out how to show the dialog on the screen without needing to pass stage variable **/
		// Show the dialog box on the main game window
		dialog.show(stage);
		dialog.setName("questionPanelDialog");
		stage.addActor(dialog);
		stage.setKeyboardFocus(dialog);
	}
}
