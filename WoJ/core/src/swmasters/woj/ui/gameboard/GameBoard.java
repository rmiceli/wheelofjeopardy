package swmasters.woj.ui.gameboard;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

import swmasters.woj.core.Category;
import swmasters.woj.core.Game;
import swmasters.woj.ui.gameboard.wheel.Sector.SectorType;
import swmasters.woj.ui.gameboard.wheel.Sector;
import swmasters.woj.ui.gameboard.wheel.Wheel;

public class GameBoard extends Actor {
	private Game game;
	private Wheel wheel;
		
	private void initWheel() {
		ArrayList<Category> categories = new ArrayList<Category>();
		wheel = new Wheel(categories);
		wheel.setBounds(wheel.getX(), wheel.getY(), wheel.getWidth(), wheel.getHeight());
		wheel.setPosition(10, 500);
	    //this.addActor(wheel);
	}
	
	public GameBoard(Game game) {
		super();
		this.game = game;
		initWheel();
	}
		
	@Override
	public void draw(Batch batch, float parentAlpha) {
		//super.draw(batch, parentAlpha);
		//ShapeRenderer shapeRenderer;
	      
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

		//wheel.draw(batch, parentAlpha);
		//this.act(Gdx.graphics.getDeltaTime());
		//this.draw(batch, parentAlpha);
		wheel.draw(batch, parentAlpha);
		super.draw(batch, parentAlpha);
	}
}
