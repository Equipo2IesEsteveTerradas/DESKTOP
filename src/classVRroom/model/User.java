package classVRroom.model;

import org.bson.types.ObjectId;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
	private int id;
	private StringProperty name;
	
	public User() {
	}

	public User(int id, String name) {
		super();
		this.id = id;
		this.name = new SimpleStringProperty(name);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name.get();
	}
	
	public StringProperty nameProperty() {
		return name;
	}
	
	public void setName(StringProperty name) {
		this.name = name;
	}
}
