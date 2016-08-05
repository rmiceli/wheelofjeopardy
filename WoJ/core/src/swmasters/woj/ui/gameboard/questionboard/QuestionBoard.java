package swmasters.woj.ui.gameboard.questionboard;

import java.util.ArrayList;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import swmasters.woj.core.Category;
import swmasters.woj.core.Question;

public class QuestionBoard extends WidgetGroup {

	private ArrayList<Category> categories;
	private ArrayList<Question> questions;
	
	private final String FILE_NAME_CATEGORIES = "assets/data/categories.json";
	private ShapeRenderer renderer = new ShapeRenderer();
	
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
	
	private void loadCategories() {
		/*
		FileHandle categoriesFile = new FileHandle(FILE_NAME_CATEGORIES);
		JsonValue categoriesRoot = new JsonReader().parse(categoriesFile);
		Category category = new Category(categoriesRoot);
		*/
	}
	
	private void loadQuestions() {
		
	}
	
	public QuestionBoard() {
		loadCategories();
		loadQuestions();
		
		setWidth(400);
		setHeight(300);
		setX(100);
		setY(100);
		
	}
	
}
