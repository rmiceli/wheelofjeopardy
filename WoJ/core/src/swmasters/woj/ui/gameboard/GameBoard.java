package swmasters.woj.ui.gameboard;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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
		//wheel.setOriginX(this.getWidth() / 2 - wheel.getWidth() / 2);
		//wheel.setOriginY(this.getHeight() / 2 - wheel.getHeight() / 2);
		wheel.setX(0);
		wheel.setY(0);
		wheel.setSize(500, 500);
	}
	
	public GameBoard(Game game) {
		this.game = game;
		initWheel();
	}
	
	public void draw(Batch batch, float parentAlpha) {
		ShapeRenderer shapeRenderer;
	      
		/*
		shapeRenderer = new ShapeRenderer();
		   shapeRenderer.setAutoShapeType(true);
		   //shapeRenderer.setProjectionMatrix(stage.getViewport().getCamera().projection);
		   shapeRenderer.setProjectionMatrix(this.getStage().getViewport().getCamera().projection);
		   shapeRenderer.begin(ShapeType.Filled);
		   shapeRenderer.setColor(Color.YELLOW);
		   shapeRenderer.circle(0, 0, 50);
		   shapeRenderer.end();
		   */
		/*
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(this.getColor());
		shapeRenderer.rect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		shapeRenderer.end();
		*/
		
		//wheel.setX(1000);
		wheel.setBounds(0, 0, wheel.getWidth(), wheel.getHeight());
	    this.getStage().addActor(wheel);
		//wheel.draw(batch, parentAlpha);
	}
}
