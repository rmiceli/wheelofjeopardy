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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Scaling;
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

   //private SpriteBatch batch;
   //private Sprite backgroundSprite;
   //private OrthographicCamera camera;
   //private BitmapFont fontTitle;
   //private BitmapFont fontMenuItem;
   //private FreeTypeFontGenerator generator;
   //private FreeTypeFontParameter parameter;
   private Stage stage;
   //private QuestionBoard questionBoard;
   //private Window window;
   private GameBoard gameBoard;
   //private FitViewport viewport;

   /**
    * @brief Create and set game stage
    */
   @Override
   public void create () {
      stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));
      Gdx.input.setInputProcessor(stage);
      Player player1 = new Player("Alice");
      Player player2 = new Player("Bob");
      gameBoard = new GameBoard(new Game(player1, player2));
      gameBoard.setFillParent(true);
      gameBoard.setSize(stage.getWidth(), stage.getHeight());
      gameBoard.setBounds(0, 0, stage.getWidth(), stage.getHeight());
      gameBoard.setColor(Color.BLUE);
      gameBoard.setX(0);
      gameBoard.setY(0);
      //gameBoard.setBounds(0, 0, WORLD_WIDTH - 25, WORLD_HEIGHT - 25);
      gameBoard.setSize(stage.getWidth(), stage.getHeight());
      stage.addActor(gameBoard);
   }

   /**
    * @brief The main draw loop
    */
   @Override
   public void render () {
	  Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
      stage.act(Gdx.graphics.getDeltaTime());
      stage.draw();

      /*
      ShapeRenderer shapeRenderer;
      shapeRenderer = new ShapeRenderer();
      shapeRenderer.setAutoShapeType(true);
	  shapeRenderer.setProjectionMatrix(stage.getViewport().getCamera().projection);
      //shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
	  shapeRenderer.begin(ShapeType.Filled);
	  shapeRenderer.setColor(Color.CYAN);
	  shapeRenderer.circle(0, 0, 10);
	  shapeRenderer.end();
	  */
   }
   
   /**
    * @brief Handle resize event
    */
   @Override
    public void resize(int width, int height) {
       stage.getViewport().update(width, height, true);
    }
   
   /**
    * @brief Destroy stage and other UI components
    */
   @Override
   public void dispose () {
      stage.dispose();
   }
}
