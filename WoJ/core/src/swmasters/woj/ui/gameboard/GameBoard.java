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
	private ShapeRenderer renderer = new ShapeRenderer();
	private Game game;
	private Wheel wheel;
	
	public GameBoard(Game game) {
		this.game = game;
	}
	
	public void draw(Batch batch, float parentAlpha) {
		
		/*ArrayList<Category> categories = new ArrayList<Category>();
		wheel = new Wheel(categories);
		wheel.setX(400);
		wheel.setY(400);
		wheel.draw(batch, parentAlpha);*/
		
		Sector sector = new Sector(SectorType.SECTOR_TYPE_BANKRUPT);
		sector.setX(100);
		sector.setY(100);
		sector.draw(batch, 1);
		
		batch.end();
		
		renderer.setProjectionMatrix(batch.getProjectionMatrix());
		renderer.setTransformMatrix(batch.getTransformMatrix());
		renderer.translate(getX(), getY(), 0);
		
		renderer.begin(ShapeType.Filled);
		renderer.setColor(Color.GRAY);
		renderer.rect(0, 0, getWidth(), getHeight());
		renderer.end();
		
		/*
		Sector sector = new Sector(SectorType.SECTOR_TYPE_BANKRUPT);
		sector.draw(batch, parentAlpha);
		*/
		
		batch.begin();
	}
}
