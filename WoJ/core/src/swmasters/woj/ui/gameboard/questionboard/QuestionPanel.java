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
		question = questionInput.getQuestion();
		// Create a default font
		BitmapFont font = new BitmapFont();
		
		// Create a label style for the question label
		LabelStyle labelStyle = new LabelStyle(font, Color.WHITE);
		Label questionLabel = new Label(question, labelStyle);
		questionLabel.setFontScale(1.2f);
		questionLabel.setAlignment(Align.center);
		questionLabel.setWrap(true);
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
		textButtonStyle.font.getData().setScale(1f);
		textButtonStyle.fontColor = Color.WHITE;
		tileSkin.add("default", textButtonStyle);
		
		// Create a new text button for the submit button
	    TextButton submit = new TextButton("Submit", tileSkin);
		// Create a button table to hold the submit button
		float btnSize = 50f;
		Table buttonTable = new Table();
	    Table contentsTable = new Table();
	    contentsTable.setWidth(500);
	    // Add the submit button to the button table
	    buttonTable.add(submit).width(btnSize+100).height(btnSize);
	    
	    // Create a text field style for player's answer text field
		TextFieldStyle style = new TextFieldStyle();
		style.fontColor = Color.LIGHT_GRAY;
		style.font = new BitmapFont();
		style.font.getData().scale(.3f);
		
		// Create the text field for the player's answer
	    final TextField answerBox = new TextField("", style);
	    answerBox.setMessageText("Your answer");
	    
	    // Add the question label and the answer text field to the contents table
	    Table questionTable = new Table();
	    Table answerTable = new Table();
	    answerTable.setWidth(500);
	    
	    questionTable.add(questionLabel).row();
	    answerTable.add(answerBox).row();

	    contentsTable.add(questionTable).width(500).height(80).row();
	    contentsTable.add(answerTable).width(500).row();
	    
	    // Create the question dialog box
		Skin skinDialog = new Skin(Gdx.files.internal("Assets/skins/default/uiskin.json"));
		dialog = new Dialog("Question for " + questionInput.getpointValue() + " points", skinDialog) {
			@Override
			public float getPrefWidth(){
				// dialog width
				return 400f;
			}
			@Override
			public float getPrefHeight(){
				// dialog height
				return 250f;
			}
		};
		dialog.setModal(true);
		dialog.setMovable(true);
		dialog.setResizable(false);
		dialog.setScale(2, 2);
	    
		// Add the content table and button table to the dialog box
	    dialog.getContentTable().add(contentsTable).center();
		dialog.getButtonTable().add(buttonTable).center().padBottom(10f);	
		dialog.setName("questionPanelDialog");
		dialog.pack();
		
		// Listen for user clicking the submit button
		submit.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				// Verify the answer given by the user
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