package swmasters.woj.ui.gameboard;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

import swmasters.woj.core.Category;
import swmasters.woj.core.Game;
import swmasters.woj.ui.gameboard.wheel.Sector.SectorType;
import swmasters.woj.ui.gameboard.wheel.Sector;
import swmasters.woj.ui.gameboard.wheel.Wheel;

public class GameBoard extends WidgetGroup {
	private Game game;
	private Wheel wheel;
	
	private void initWheel() {
		ArrayList<Category> categories = new ArrayList<Category>();
		wheel = new Wheel(categories);
		wheel.setX(400);
		wheel.setY(400);
	}
	
	public GameBoard(Game game) {
		this.game = game;
		initWheel();
	}
	
	public void draw(Batch batch, float parentAlpha) {
		wheel.draw(batch, parentAlpha);
	}
}
