package classVRroom.model;

import org.bson.types.ObjectId;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Course {
	private ObjectId id;
	private StringProperty title;
	private StringProperty description;
	
	//Constructors
	public Course() {
	}
	
	public Course(ObjectId id, String title, String description) {
		this.id = id;
		this.title = new SimpleStringProperty(title);
		this.description = new SimpleStringProperty(description);
	}
	
	//Getters and Setters
	public String getTitle() {
		return title.get();
	}
	public void setTitle(StringProperty title) {
		this.title = title;
	}

	public String getDescription() {
		return description.get();
	}

	public void setDescription(StringProperty description) {
		this.description = description;
	}
	
	public StringProperty titleProperty() {
		return title;
	}
	
	public StringProperty descripcionProperty() {
		return description;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}
	
	
	
}
