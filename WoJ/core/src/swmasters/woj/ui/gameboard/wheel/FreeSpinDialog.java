package swmasters.woj.ui.gameboard.wheel;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import swmasters.woj.core.Player;

public class FreeSpinDialog extends WidgetGroup {
	private Dialog dialog;
	boolean result = false;
	
	/**
	 * Construct a Free Spin Dialog widget
	 * 
	 * @param questionInput
	 */
	public FreeSpinDialog(final Player player){
		Skin skinDialog = new Skin(Gdx.files.internal("Assets/skins/default/uiskin.json"));
		
		// Create a default font
		BitmapFont font = new BitmapFont();
		
		// Create a label style for the question label
		LabelStyle labelStyle = new LabelStyle(font, Color.WHITE);
		Label useTokenLabel = new Label("Use a free spin token?", labelStyle);
		useTokenLabel.setFontScale(1.2f);
		useTokenLabel.setAlignment(Align.center);
		useTokenLabel.setWrap(true);
		labelStyle.fontColor = Color.WHITE;
		
		dialog = new Dialog("Warning", skinDialog, "dialog"){
			@Override
			public void result(Object obj){
				if(obj.equals(true)){
					result = true;
				}
				dialog.hide();
				dialog.cancel();
				dialog.remove();
			}
			
			@Override
			public float getPrefWidth(){
				// dialog width
				return 250f;
			}
			@Override
			public float getPrefHeight(){
				// dialog height
				return 100f;
			}
		};
		dialog.setScale(2, 2);
		dialog.getContentTable().add(useTokenLabel);
		dialog.button("Yes", true);
		dialog.button("No", false);
	}
	
	/**
	 * Show the dialog on the main game screen
	 * 
	 * @param stage
	 */
	public void show(Stage stage){
		// Show the dialog box on the main game window
		dialog.show(stage);
		dialog.setName("questionPanelDialog");
		stage.addActor(dialog);
		stage.setKeyboardFocus(dialog);
	}
	
	/**
	 * Return the saved variable from player's choice to use free token or not
	 * 
	 * @return choice to use free spin token or not
	 */
	public boolean returnResult(){
	      System.out.println(result);
		return result;
	}
}
