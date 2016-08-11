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
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType.GlyphSlot;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType.Library;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.badlogic.gdx.utils.viewport.FitViewport;

import swmasters.woj.core.Category;
import swmasters.woj.core.Game;
import swmasters.woj.core.Player;
import swmasters.woj.ui.gameboard.GameBoard;
import swmasters.woj.ui.gameboard.questionboard.QuestionBoard;

/**
 * @brief Main application class
 */
public class WoJGame extends ApplicationAdapter {

   /* constants */
   static final int WORLD_WIDTH = 1920;
   static final int WORLD_HEIGHT = 1080;
   static final float worldGridWidth = 1920;
   static final float worldGridHeight = 1080;

   private SpriteBatch batch;
   private Sprite backgroundSprite;
   private OrthographicCamera camera;
   private BitmapFont fontTitle;
   private BitmapFont fontMenuItem;
   private FreeTypeFontGenerator generator;
   private FreeTypeFontParameter parameter;
   private Stage stage;
   private QuestionBoard questionBoard;
   private Window window;
   private GameBoard gameBoard;
   private FitViewport viewport;

   /**
    * @brief Create and set game stage
    */
   @Override
   public void create () {
	   /*
      stage = new Stage(new FitViewport(1920, 1080));
      Gdx.input.setInputProcessor(stage);
      */
	   camera = new OrthographicCamera();
	   viewport = new FitViewport(1920, 1080, camera);
      Player player1 = new Player("Alice");
      Player player2 = new Player("Bob");
      gameBoard = new GameBoard(new Game(player1, player2));
      gameBoard.setFillParent(true);
      gameBoard.setX(0);
      gameBoard.setY(0);
      gameBoard.setColor(Color.RED);
      //stage.addActor(gameBoard);
   }

   /**
    * @brief The main draw loop
    */
   @Override
   public void render () {
	  camera.update();
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
      SpriteBatch batch = new SpriteBatch();
      batch.begin();
      gameBoard.draw(batch, 1);
      batch.end();
      
      //stage.act(Gdx.graphics.getDeltaTime());
      //stage.draw();
   }
   
   /**
    * @brief Handle resize event
    */
   @Override
    public void resize(int width, int height) {
      //stage.getViewport().update(width,  height, true);
	   viewport.update(width, height);
    }
   
   /**
    * @brief Destroy stage and other UI components
    */
   @Override
   public void dispose () {
      //stage.dispose();
   }
}
