package connection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import classVRroom.model.Course;

public class ConnectionDB {
	private String databaseURL;
	private com.mongodb.client.MongoClient mongo;
	private DB db;
	private MongoDatabase database;
	private MongoCollection<Document> coursesCollection;
	public ConnectionDB() throws IOException {
		loadDatabaseURL();
		mongo = MongoClients.create(databaseURL);
		database = mongo.getDatabase("classVRroom");
		coursesCollection = database.getCollection("courses");
		
	}
	public ConnectionDB(String databaseURL) {
		this.databaseURL = databaseURL;
	}
	
	public void loadDatabaseURL() throws IOException {
		  File file = new File("C:/Users/alum-01/Desktop/DATABASE_URL.txt");
		  BufferedReader br = new BufferedReader(new FileReader(file));
		  databaseURL = br.readLine();
	}
	
	public ArrayList<Course> getAllCourses(){
		ArrayList<Course> courses = new ArrayList<Course>();
		MongoCursor<Document> cursor = coursesCollection.find().iterator();
		try {
		    while (cursor.hasNext()) {
		    	Document course = cursor.next();
		    	String title = (String) course.get("title");
		    	String description = (String) course.get("description");
		    	ObjectId id = (ObjectId) course.get("_id");
		    	courses.add(new Course(id, title, description));
		    }
		} finally {
		    cursor.close();
		}
		
		return courses;
	}
	
	public boolean checkCourseTitle(String title) {
		return false;
	}
	
	public void insertCourse(String json) {
		Document dbObject = Document.parse(json);
		coursesCollection.insertOne(dbObject);
	}
	
	public void deleteCourse(ObjectId id) {
		coursesCollection.deleteOne(Filters.eq("_id",id));
	}
}
