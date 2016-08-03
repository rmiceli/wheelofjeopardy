package swmasters.woj;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType.GlyphSlot;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType.Library;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;

public class WoJGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Sprite backgroundSprite;
	private OrthographicCamera camera;
	private BitmapFont fontTitle;
	private BitmapFont fontMenuItem;
	private FreeTypeFontGenerator generator;
	private FreeTypeFontParameter parameter;
	static final int WORLD_WIDTH = 1920;
    static final int WORLD_HEIGHT = 1080;
    static final float worldGridWidth = 1920;
    static final float worldGridHeight = 1080;
	
	@Override
	public void create () {
		float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight(); 
		camera = new OrthographicCamera(worldGridWidth, worldGridHeight * (h / w));
		camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
		batch = new SpriteBatch();
		
		generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fonts/Arial Rounded Bold.ttf"));
		parameter = new FreeTypeFontParameter();
		parameter.size = 120;
		parameter.color = new Color(1f,1f,1f,0.6f);
		parameter.borderWidth = 0;
		parameter.shadowColor = Color.BLACK;
		parameter.shadowOffsetX = 1;
		parameter.shadowOffsetY = 1;
		fontTitle = generator.generateFont(parameter);
		fontTitle.setUseIntegerPositions(false);
		parameter.size = 30;
		parameter.kerning = true;
		parameter.mono = false;
		fontMenuItem = generator.generateFont(parameter);
		
		backgroundSprite = new Sprite(new Texture("assets/graphics/background.png"));
		backgroundSprite.setSize(150, 150);
		backgroundSprite.setBounds(0, 0, 150, 150);
		
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();
	}

	@Override
	public void render () {
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glBlendFunc(WORLD_WIDTH, WORLD_HEIGHT);
		
		batch.begin();
		
		// render background tiles
		for (int xPos = 0; xPos < worldGridWidth; xPos+=backgroundSprite.getWidth()) {
			for (int yPos = 0; yPos < worldGridHeight; yPos+=backgroundSprite.getHeight()) {
				backgroundSprite.setPosition(xPos, yPos);
				backgroundSprite.draw(batch);
			}
		}
		
		String titleString = new String("Wheel of Jeopardy");
		fontTitle.setUseIntegerPositions(false);
		fontTitle.draw(batch, titleString, fontTitle.getSpaceWidth()*titleString.length()/2, fontTitle.getCapHeight()*titleString.length()/2);
		fontMenuItem.draw(batch, "New Game", fontTitle.getSpaceWidth()*titleString.length()/2, fontTitle.getCapHeight()*titleString.length()/2 - fontTitle.getCapHeight() - 10);
		batch.end();		
	}
	
	@Override
    public void resize(int width, int height) {
        camera.viewportWidth = worldGridWidth;
        camera.viewportHeight = worldGridWidth * height/width;
        camera.update();
    }
	
	@Override
	public void dispose () {
		batch.dispose();
		//img.dispose();
		generator.dispose(); // don't forget to dispose to avoid memory leaks!
	}
}
