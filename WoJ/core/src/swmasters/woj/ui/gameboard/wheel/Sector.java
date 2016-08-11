package swmasters.woj.ui.gameboard.wheel;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

import swmasters.woj.core.Category;

public class Sector extends Widget {

   private OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
   
   private final Model model = new Model();
   private ShaderProgram shader;
  
   private static final Matrix4 modelView = new Matrix4();
   
   private Mesh mesh;
   private Texture texture;
   
 //Position attribute - (x, y) 
   public static final int POSITION_COMPONENTS = 2;

   //Color attribute - (r, g, b, a)
   public static final int COLOR_COMPONENTS = 4;

   //Total number of components for all attributes
   public static final int NUM_COMPONENTS = POSITION_COMPONENTS + COLOR_COMPONENTS;
   
 //The maximum number of triangles our mesh will hold
   public static final int MAX_TRIS = 1;

   //The maximum number of vertices our mesh will hold
   public static final int MAX_VERTS = MAX_TRIS * 3;

   
   protected float[] verts = new float[MAX_VERTS * NUM_COMPONENTS];
   
 //The current index that we are pushing triangles into the array
   protected int idx = 0;
   
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
	
	private TextureRegion textureRegion;
	private PolygonRegion polyRegion;
	private PolygonSprite polySprite;
	private PolygonSpriteBatch polyBatch;
	private float polyAngle = -45f;
	
	void flush() {
	    //if we've already flushed
	    if (idx==0)
	        return;

	    //sends our vertex data to the mesh
	    mesh.setVertices(verts);

	    //no need for depth...
	    Gdx.gl.glDepthMask(false);

	    //enable blending, for alpha
	    Gdx.gl.glEnable(GL20.GL_BLEND);
	    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

	    //number of vertices we need to render
	    int vertexCount = (idx/NUM_COMPONENTS);

	    //update the camera with our Y-up coordiantes
	    camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

	    //start the shader before setting any uniforms
	    shader.begin();

	    //update the projection matrix so our triangles are rendered in 2D
	    shader.setUniformMatrix("u_projTrans", camera.combined);

	    //render the mesh
	    mesh.render(shader, GL20.GL_TRIANGLES, 0, vertexCount);

	    shader.end();

	    //re-enable depth to reset states to their default
	    Gdx.gl.glDepthMask(true);

	    //reset index to zero
	    idx = 0;
	}
	
	void drawTriangle(float x, float y, float width, float height, Color color) {
	    //we don't want to hit any index out of bounds exception...
	    //so we need to flush the batch if we can't store any more verts
	    if (idx==verts.length)
	        flush();

	    //now we push the vertex data into our array
	    //we are assuming (0, 0) is lower left, and Y is up

	    //bottom left vertex
	    verts[idx++] = x;           //Position(x, y) 
	    verts[idx++] = y;
	    verts[idx++] = color.r;     //Color(r, g, b, a)
	    verts[idx++] = color.g;
	    verts[idx++] = color.b;
	    verts[idx++] = color.a;

	    //top left vertex
	    verts[idx++] = x;           //Position(x, y) 
	    verts[idx++] = y + height;
	    verts[idx++] = color.r;     //Color(r, g, b, a)
	    verts[idx++] = color.g;
	    verts[idx++] = color.b;
	    verts[idx++] = color.a;

	    //bottom right vertex
	    verts[idx++] = x + width;    //Position(x, y) 
	    verts[idx++] = y;
	    verts[idx++] = color.r;      //Color(r, g, b, a)
	    verts[idx++] = color.g;
	    verts[idx++] = color.b;
	    verts[idx++] = color.a;
	}
	
	
	private void initMesh() {
	    mesh = new Mesh(true, MAX_VERTS, 0, 
	            new VertexAttribute(Usage.Position, POSITION_COMPONENTS, "a_position"),
	            new VertexAttribute(Usage.ColorUnpacked, COLOR_COMPONENTS, "a_color"),
	            VertexAttribute.TexCoords(0));
	}
	
	private void initShader() {
		  // Setup default shader
	      ShaderProgram.pedantic = false;
	      //System.out.println("GL20.GL_SHADING_LANGUAGE_VERSION is " + Gdx.gl.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION));
	      shader = new ShaderProgram(Gdx.files.internal("assets/shaders/default.vsh"),
	    		                     Gdx.files.internal("assets/shaders/default.fsh"));
	      if (!shader.isCompiled()) {
	    	  System.err.println(shader.getLog());
	    	  return;
	      }    
	}
	
	private void initTexture() {
		// Setup texture
	    texture = new Texture("assets/graphics/wheel/bankrupt.png");
	    texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		textureRegion = new TextureRegion(texture);
	}
	
	private void initSprite() {
		polyRegion = new PolygonRegion(textureRegion,
				new float[] {
						30, 0,
						158, 0,
						168, 768,
						10, 768
				},
				new short[] {
						0, 1, 2,
						0, 2, 3
				});
			
		polySprite = new PolygonSprite(polyRegion);
		polySprite.setOrigin(94, 0);
		polySprite.setSize(128, 768);
		polyBatch = new PolygonSpriteBatch();
		polySprite.rotate(polyAngle);
	}
	
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
    		  0f, 0f, 0,
    		  0f, 100f, 0,
    		  100f, 100f, 0,
    		  100f, 0f, 0 });
      
      mesh.setIndices(new short[] { 0, 1, 2, 3 });
      model.meshes.add(mesh);
      
    initShader();
      
      initTexture();
      initSprite();
      
   }

   /**
    * @brief Construct a Sector of SectorType type
    *
    * @param[in] type
    *    The type of Sector to construct
    */
	public Sector(SectorType type) {
		this.type = type;
		
		initMesh();
	    initShader();
	    initTexture();
	    initSprite();
		//initSector();
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
		initMesh();
		initShader();
		initTexture();
		initSprite();
      //initSector();
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
		
		
		/*
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.BLUE);
		shapeRenderer.circle(100, 100, 100);
		shapeRenderer.end();
		*/
		
		
		/*
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
		//shader.setUniformMatrix("u_modelView", modelView);
		//shader.setUniformMatrix("u_projTrans", camera.combined);
		shader.setUniformMatrix("u_projTrans", batch.getProjectionMatrix());
		shader.end();
	    //texture.bind();*/
		
		
		polyBatch.begin();
		polyBatch.setProjectionMatrix(this.camera.projection);
		polySprite.setBounds(0, 0, this.getWidth(), this.getHeight());
		polySprite.setSize(32, 192);
		polySprite.draw(polyBatch);
		polyBatch.end();
		
		//shader.end();
	    
	    batch.begin();
	    
	  //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    //Gdx.gl.glEnable(GL20.GL_TEXTURE_2D);
	    //mesh.render(shader, GL20.GL_TRIANGLES);
	    
	    
	    //drawTriangle(10, 10, 40, 40, Color.RED);
	    //flush();
	    //shader.begin();
	    //shader.setUniformMatrix("u_projModelView", batch.getProjectionMatrix());
	    //mesh.render(shader, GL20.GL_TRIANGLES);
	    //drawTriangle(10, 10, 40, 40, Color.RED);
	    //flush();
	    //shader.end();
	}
}
