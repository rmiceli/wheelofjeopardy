package swmasters.woj.ui.gameboard.wheel;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

import swmasters.woj.core.Category;

public class Sector extends Widget {

   private PerspectiveCamera camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
   
   private final Model model = new Model();
   private ShaderProgram shader;
  
   private static final Matrix4 modelView = new Matrix4();
   
   private Mesh mesh;
   private Texture texture;
   
   /**
    * @brief Enumeration for the sector types
    */
	public enum SectorType {
		SECTOR_TYPE_BANKRUPT,
		SECTOR_TYPE_CATEGORY,
		SECTOR_TYPE_FREE_TURN,
		SECTOR_TYPE_LOSE_TURN,
		SECTOR_TYPE_OPPONENTS_CHOICE,
		SECTOR_TYPE_PLAYERS_CHOICE,
		SECTOR_TYPE_SPIN_AGAIN
	}
	
	private SectorType type;      /**< Sector type */
	private Category category;    /**< Category for this sector (only valid for
                                      (SectorType == SECTOR_TYPE_CATEGORY)
                                      instances */
	
   private void initSector() {
	   
	  // Setup bounding box
	  BoundingBox boundingBox = new BoundingBox();
	  model.calculateBoundingBox(boundingBox);
	  Vector3 center = new Vector3();
	  center = boundingBox.getCenter(center);
		
	  // Setup camera
	  camera.position.set(boundingBox.max.x, boundingBox.max.y, boundingBox.max.z).mul(
          new Matrix3(new float[] {
			   2, 0, 0,
			   0, 2, 0,
			   0, 0, 2 }));
	  camera.lookAt(center.x, center.y, center.z);
	  camera.up.set(0, 0, 1);
	  camera.normalizeUp();
	  camera.near = 1f;
	  camera.far = new Vector3(camera.position).dst2(center) * 2f;
	  camera.update();

	  // Create mesh
      mesh = new Mesh(true, 4, 4,
    		  new VertexAttribute(Usage.Position, 3, "a_position"),
    		  new VertexAttribute(Usage.ColorPacked, 4, "a_color"),
    		  VertexAttribute.TexCoords(0));
      
      mesh.setVertices(new float[] { 
    		  -0.5f, -0.5f, 0,
    		  0.5f, -0.5f, 0,
    		  0.5f, 0.5f, 0,
    		  -0.5f, 0.5f, 0 });
      
      mesh.setIndices(new short[] { 0, 1, 2, 3 });
      model.meshes.add(mesh);
      
      // Setup default shader
      ShaderProgram.pedantic = false;
      //System.out.println("GL20.GL_SHADING_LANGUAGE_VERSION is " + Gdx.gl.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION));
      shader = new ShaderProgram(Gdx.files.internal("assets/shaders/default.vsh"),
    		                     Gdx.files.internal("assets/shaders/default.fsh"));
      if (!shader.isCompiled()) {
    	  System.err.println(shader.getLog());
    	  return;
      }    
      
      // Setup texture
      texture = new Texture("assets/graphics/wheel/bankrupt.png");
      
   }

   /**
    * @brief Construct a Sector of SectorType type
    *
    * @param[in] type
    *    The type of Sector to construct
    */
	public Sector(SectorType type) {
		this.type = type;
		initSector();
	}
	
   /**
    * @brief Construct a sector of type SECTOR_TYPE_CATEGORY with the specified
    * category.
    *
    * @param[in] category
    *    The category to assign to the Sector
    */
	public Sector(Category category) {
		this.type = SectorType.SECTOR_TYPE_CATEGORY;
		this.category = category;

      initSector();
	}

   /**
    * @brief Get the category this Sector selects
    * @returns Category when SectorType == SECTOR_TYPE_CATEGORY. Throws otherwise.
    */
	public Category getCategory() {
		
		if (this.type != SectorType.SECTOR_TYPE_CATEGORY) {
			Throwable t = new Throwable("Can't get category for non-Category sector");
			t.printStackTrace();
			System.exit(1);
		}
		
		return category;
	}
	
   /**
    * @brief Get the SectorType for this Sector
    */
	public SectorType getType() {
		return this.type;
	}

   /**
    * @brief Render a Sector
    *
    * @param[in] batch
    *    The batch in which to render the Sector
    * @param[in] parentAlpha
    *    
    */
	public void draw(Batch batch, float parentAlpha) {
		batch.end();
		Gdx.gl.glViewport(0, 0, (int)this.getWidth(), (int)this.getHeight());

		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		Gdx.gl.glDepthMask(true);
		Gdx.gl.glDepthFunc(GL20.GL_LEQUAL);
		Gdx.gl.glDepthRangef(0f, 1f);

		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
		Vector3 light = camera.position;
		shader.begin();
		shader.setUniformf("u_lightPosition",  light.x, light.y, light.z);
		shader.setUniformMatrix("u_modelView", modelView);
		shader.setUniformMatrix("u_projTrans", camera.combined);
	    texture.bind();
	    
	    mesh.render(shader, GL20.GL_TRIANGLES);
	    
	    batch.begin();	    
	}	
}
