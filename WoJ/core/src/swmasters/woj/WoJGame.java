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

import swmasters.woj.model.Category;
import swmasters.woj.model.Game;
import swmasters.woj.model.Player;
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

   /**
    * @brief Create and set game stage
    */
   @Override
   public void create () {
      stage = new Stage(new FitViewport(1920, 1080));
      Gdx.input.setInputProcessor(stage);
      Player player1 = new Player("Alice");
      Player player2 = new Player("Bob");
      gameBoard = new GameBoard(new Game(player1, player2));
      gameBoard.setFillParent(true);
      stage.addActor(gameBoard);
      
      
      
      /*
      window = new Window("window", skin);
      window.setX(500);
      window.setY(500);
      window.setWidth(300);
      window.setHeight(300);
      stage.addActor(window);
      */
      
      //batch = new SpriteBatch();
      /*
      float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight(); 
      camera = new OrthographicCamera(worldGridWidth, worldGridHeight * (h / w));
      camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
      
      

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
         */
   }

   /**
    * @brief The main draw loop
    */
   @Override
   public void render () {
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
      stage.act(Gdx.graphics.getDeltaTime());
      stage.draw();
      
      //batch.begin();
      
      /*
      // render background tiles
      for (int xPos = 0; xPos < worldGridWidth; xPos+=backgroundSprite.getWidth()) {
         for (int yPos = 0; yPos < worldGridHeight; yPos+=backgroundSprite.getHeight()) {
            backgroundSprite.setPosition(xPos, yPos);
            backgroundSprite.draw(batch);
         }
      }
      */
      
      /*
      String titleString = new String("Wheel of Jeopardy");
      fontTitle.setUseIntegerPositions(false);
      fontTitle.draw(batch, titleString, fontTitle.getSpaceWidth()*titleString.length()/2, fontTitle.getCapHeight()*titleString.length()/2);
      fontMenuItem.draw(batch, "New Game", fontTitle.getSpaceWidth()*titleString.length()/2, fontTitle.getCapHeight()*titleString.length()/2 - fontTitle.getCapHeight() - 10);
      */
      
   
      /*batch.end();*/
      
      /*
      Json json = new Json();
      Category category = new Category();
      category.setName("Sports");
      category.setAnotherField("bagel");
      String text = json.prettyPrint(category);
      //category.write(json);
      //System.out.println(json.toJson(category, Category.class));
      System.out.println(text);
      */
   }
   
   /**
    * @brief Handle resize event
    */
   @Override
    public void resize(int width, int height) {
      stage.getViewport().update(width,  height, true);
    }
   
   /**
    * @brief Destroy stage and other UI components
    */
   @Override
   public void dispose () {
      stage.dispose();
      //batch.dispose();
      //img.dispose();
      //generator.dispose(); // don't forget to dispose to avoid memory leaks!
   }
}
