package swmasters.woj.model;

import java.util.ArrayList;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class Category {
	private String name;
	private ArrayList<Question> questions;
		
	public Category() {
		
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void write(Json json) {
		json.writeValue(name);
	}
	
	public void read(Json json, JsonValue jsonMap) {
		name = jsonMap.child().name();
	}
	
	public Category(JsonValue jsonCategory) {
	}
}
