package swmasters.woj.ui.gameboard.wheel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

import swmasters.woj.core.Category;
import swmasters.woj.ui.gameboard.wheel.Sector;
import swmasters.woj.ui.gameboard.wheel.Sector.SectorType;

public class Wheel extends Actor {

	private final int MAX_SECTORS = 12;
	private final int MAX_CATEGORY_SECTORS = 6;
	private ArrayList<Sector> sectors;
	private Random randomGenerator = new Random();
	private int currentSectorIndex = 0;
	private Sector sector;
	private final int centerRadius = 50;
	private Texture backgroundTexture;
	
	private void initSectors() {
		
		backgroundTexture = new Texture(Gdx.files.internal("assets/graphics/wheel/background.png"));
		
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
		//Collections.shuffle(sectors);
		
		int offset = 0;
		for (int sectorNumber = 0;
			 sectorNumber < sectors.size();
			 sectorNumber++) {
			Sector sector = sectors.get(sectorNumber);
			sector.setBounds(0, 0, this.getWidth(), this.getHeight());
			if (offset == 1) {
				sector.setRotation(-45f);
			}
			else if (offset == 2) {
				sector.setRotation(-90f);
			}
			offset++;
		}		
	}
	 
	private Sector getNextSector() {
		int nextSectorIndex = currentSectorIndex + randomGenerator.nextInt(MAX_SECTORS*3);
		return sectors.get(nextSectorIndex);
	}
	
	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
	}
	
	public Sector spin() {
		return getNextSector();
	}
	
	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
	}
	
	public Wheel(ArrayList<Category> categories) {
		super();
		loadSectors(categories);
		initSectors();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		batch.draw(backgroundTexture, 0, 0);
		batch.end();
		
		for (int sectorNumber = 0;
		     sectorNumber < this.sectors.size();
			 sectorNumber++) {
			Sector sector = sectors.get(sectorNumber);
			if (sectorNumber == 0) {
				// don't rotate
				sector.rotate(0*360f/(float)MAX_SECTORS);
			}
			else if (sectorNumber == 1) {
				// rotate by 15 degrees
				sector.rotate(1*360f/(float)MAX_SECTORS);
			}
			else if (sectorNumber == 2) {
				sector.rotate(2*360f/(float)MAX_SECTORS);
			}
			sector.setPosition(10, 10);
			//sector.setSize(sector.getWidth() * 1.0f, sector.getHeight() * 1.0f);
			sector.draw(batch, parentAlpha);
		}
		batch.begin();
	}
}
