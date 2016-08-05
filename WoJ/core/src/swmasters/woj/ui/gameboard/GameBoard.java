package swmasters.woj.ui.gameboard;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

import swmasters.woj.model.Game;

public class GameBoard extends WidgetGroup {
	private ShapeRenderer renderer = new ShapeRenderer();
	private Game game;
	
	public GameBoard(Game game) {
		this.game = game;
	}
	
	public void draw(Batch batch, float parentAlpha) {
		batch.end();
		
		renderer.setProjectionMatrix(batch.getProjectionMatrix());
		renderer.setTransformMatrix(batch.getTransformMatrix());
		renderer.translate(getX(), getY(), 0);
		
		renderer.begin(ShapeType.Filled);
		renderer.setColor(Color.GRAY);
		renderer.rect(0, 0, getWidth(), getHeight());
		renderer.end();
		
		batch.begin();
	}
}
