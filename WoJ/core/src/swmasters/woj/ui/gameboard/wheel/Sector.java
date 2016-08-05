package swmasters.woj.ui.gameboard.wheel;

import swmasters.woj.core.Category;

public class Sector {
	public enum SectorType {
		SECTOR_TYPE_BANKRUPT,
		SECTOR_TYPE_CATEGORY,
		SECTOR_TYPE_FREE_TURN,
		SECTOR_TYPE_LOSE_TURN,
		SECTOR_TYPE_OPPONENTS_CHOICE,
		SECTOR_TYPE_PLAYERS_CHOICE,
		SECTOR_TYPE_SPIN_AGAIN		
	}
	
	private SectorType type;
	private Category category;
	
	public Category getCategory() {
		
		if (this.type != SectorType.SECTOR_TYPE_CATEGORY) {
			Throwable t = new Throwable("Can't get category for non-Category sector");
			t.printStackTrace();
			System.exit(1);
		}
		
		return category;
	}
	
	public SectorType getType() {
		return this.type;
	}
	
	public Sector(SectorType type) {
		this.type = type;
	}
	
	public Sector(Category category) {
		this.type = SectorType.SECTOR_TYPE_CATEGORY;
		this.category = category;
	}
}
