package swmasters.woj;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
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
import com.badlogic.gdx.scenes.scene2d.Actor;
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
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import swmasters.woj.core.Category;
import swmasters.woj.core.Game;
import swmasters.woj.core.Player;
import swmasters.woj.ui.gameboard.GameBoard;
import swmasters.woj.ui.gameboard.GameStage;
import swmasters.woj.ui.gameboard.questionboard.QuestionBoard;
import swmasters.woj.ui.gameboard.wheel.Wheel;

/**
 * @brief Main application class
 */
public class WoJGame extends ApplicationAdapter {

   /* constants */
   static final int WORLD_WIDTH = 1920;
   static final int WORLD_HEIGHT = 1080;

   private GameStage gameStage;
   private GameBoard gameBoard;
   private OrthographicCamera camera;

   /**
    * @brief Create and set game stage
    */
   @Override
   public void create () {
	   
	   /* Setup camera annd viewport */
	  float aspectRatio = (float)Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth();
	  camera = new OrthographicCamera(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
	  camera.translate(camera.viewportWidth/2, camera.viewportHeight/2);
	  camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
	  gameStage = new GameStage(new FitViewport(WORLD_WIDTH * aspectRatio, WORLD_HEIGHT, camera));
      Gdx.input.setInputProcessor(gameStage);
      
      /* add the wheel */
      /*ArrayList<Category> categories = new ArrayList<Category>();
      Wheel wheel = new Wheel(categories);
      wheel.setPosition(Gdx.graphics.getWidth()/2 - wheel.getWidth()/2, Gdx.graphics.getHeight()/2 - wheel.getHeight());
      wheel.setBounds(wheel.getX(), wheel.getY(), wheel.getWidth(), wheel.getHeight());
      gameStage.addActor(wheel);
      */
      
      /*      
      class MyActor extends Actor {
    	  
    	  
          private Sprite sprite;
              
          public MyActor() {
        	  setBounds(getX(), getY(), getWidth(), getHeight());
        	  
        	  sprite = new Sprite(new Texture(Gdx.files.internal("assets/graphics/wheel/loseaturn.png")));
        	  sprite.setPosition(0, 0);
          }
          
          @Override
          public void setSize(float width, float height) {
        	  sprite.setSize(width, height);
          }
          
    	  @Override
    	  public void draw(Batch batch, float parentAlpha) {
    		  sprite.draw(batch);
    		  super.draw(batch, parentAlpha);
    	  }
      }

      MyActor myActor = new MyActor();
      //myActor.setBounds(myActor.getX(), myActor.getY(), myActor.getWidth(), myActor.getHeight());
      myActor.setPosition(0, 0);
      myActor.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
      gameStage.addActor(myActor);
      */

      /*Player player1 = new Player("Alice");
      Player player2 = new Player("Bob");
      gameBoard = new GameBoard(new Game(player1, player2));
      gameBoard.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
      gameBoard.setColor(Color.BLUE);
      gameBoard.setPosition(0, 0);
      gameStage.addActor(gameBoard);*/
      
      ArrayList<Category> categories = new ArrayList<Category>();
      Wheel wheel = new Wheel(categories);
      wheel.setPosition(gameStage.getWidth()/2 - wheel.getWidth()/2, gameStage.getHeight()/2 - wheel.getHeight()/2);
      gameStage.addActor(wheel);
   }

   /**
    * @brief The main draw loop
    */
   @Override
   public void render () {
	  Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
      //camera.update();
      gameStage.act(Gdx.graphics.getDeltaTime());
      gameStage.draw();
   }
   
   /**
    * @brief Handle resize event
    */
   @Override
    public void resize(int width, int height) {
       gameStage.getViewport().update(width, height, true);
    }
   
   /**
    * @brief Destroy stage and other UI components
    */
   @Override
   public void dispose () {
      gameStage.dispose();
   }
}
