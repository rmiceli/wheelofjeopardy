package swmasters.woj.ui.gameboard.wheel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

import swmasters.woj.core.Category;
import swmasters.woj.ui.gameboard.wheel.Sector;
import swmasters.woj.ui.gameboard.wheel.Sector.SectorType;

public class Wheel extends WidgetGroup {

	private ShapeRenderer renderer;
	private final int MAX_SECTORS = 12;
	private final int MAX_CATEGORY_SECTORS = 6;
	private ArrayList<Sector> sectors;
	private Random randomGenerator = new Random();
	private int currentSectorIndex = 0;
	private Sector sector;
	private final int centerRadius = 50;
	
	private void initSectors() {
		sector = new Sector(SectorType.SECTOR_TYPE_BANKRUPT);
		sector.setX(centerRadius / 2);
		sector.setY(centerRadius / 2);
	}
	
	private void loadSectors(ArrayList<Category> categories) {
		sectors = new ArrayList<Sector>();
		
		/* add non-category sectors */
		sectors.add(new Sector(SectorType.SECTOR_TYPE_BANKRUPT));
		sectors.add(new Sector(SectorType.SECTOR_TYPE_FREE_TURN));
		sectors.add(new Sector(SectorType.SECTOR_TYPE_LOSE_TURN));
		sectors.add(new Sector(SectorType.SECTOR_TYPE_OPPONENTS_CHOICE));
		sectors.add(new Sector(SectorType.SECTOR_TYPE_PLAYERS_CHOICE));
		sectors.add(new Sector(SectorType.SECTOR_TYPE_SPIN_AGAIN));

		/* add category sectors */
		for (int sectorNumber = 0;
			 sectorNumber < this.MAX_CATEGORY_SECTORS && sectorNumber < categories.size();
			 sectorNumber++) {
			sectors.add(new Sector(categories.get(sectorNumber)));
		}
		
		/* randomize the order of the sectors */
		Collections.shuffle(sectors);
	}
	
	private Sector getNextSector() {
		int nextSectorIndex = currentSectorIndex + randomGenerator.nextInt(MAX_SECTORS*3);
		return sectors.get(nextSectorIndex);
	}
	
	public Sector spin() {
		return getNextSector();
	}
	
	public Wheel(ArrayList<Category> categories) {
		loadSectors(categories);
		initSectors();
		renderer = new ShapeRenderer();
	}
	
	public void draw(Batch batch, float parentAlpha) {
		batch.end();
		//this.getStage().getViewport();
		/*
		renderer.setProjectionMatrix(this.getStage().getViewport().getCamera().projection);	
		renderer.begin(ShapeType.Filled);
		renderer.setColor(Color.GREEN);
		renderer.circle(0, 0, 75);
		renderer.end();
		*/
		
		batch.begin();
		//batch.draw(sector, 0, 0, 100, 100);
		sector.setBounds(0, 0, this.getWidth(), this.getHeight());
		sector.draw(batch, 1);
	}
}
