package connection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
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
import com.mongodb.client.model.Updates;

import static com.mongodb.client.model.Filters.eq;

import classVRroom.model.Course;
import classVRroom.model.User;

public class ConnectionDB {
	private String databaseURL;
	private com.mongodb.client.MongoClient mongo;
	private DB db;
	private MongoDatabase database;
	private MongoCollection<Document> coursesCollection;
	private MongoCollection<Document> usersCollection;
	
	public ConnectionDB() throws IOException {
		loadDatabaseURL();
		mongo = MongoClients.create(databaseURL);
		database = mongo.getDatabase("classVRroom");
		coursesCollection = database.getCollection("courses");
		usersCollection = database.getCollection("users");
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
	
	public ArrayList<User> getProfessorsFromCourse(Course course){
		ArrayList<User> teachersList = new ArrayList<User>();
		Document document = coursesCollection.find(eq("_id", course.getId())).first();
	    
		Document subscribers = (Document) document.get("subscribers");
		ArrayList<Integer> teachers = (ArrayList<Integer>) subscribers.get("teachers");
		
		for (int i = 0; i < teachers.size(); i++) {
			Document teacher = usersCollection.find(eq("id",teachers.get(i))).first();
			teachersList.add(new User(teacher.getInteger("id"),teacher.getString("name")));
		}
		return teachersList;
	}
	
	public ArrayList<User> getAlumnesFromCourse(Course course){
		ArrayList<User> alumsList = new ArrayList<User>();
		Document document = coursesCollection.find(eq("_id", course.getId())).first();
	    
		Document subscribers = (Document) document.get("subscribers");
		ArrayList<Integer> alums = (ArrayList<Integer>) subscribers.get("students");
		
		for (int i = 0; i < alums.size(); i++) {
			Document alum = usersCollection.find(eq("id",alums.get(i))).first();
			alumsList.add(new User(alum.getInteger("id"),alum.getString("name")));
		}
		return alumsList;
	}
	
	public ArrayList<User> getUsersRestantes(Course course){
		ArrayList<User> users = new ArrayList<User>();
		Document document = coursesCollection.find(eq("_id", course.getId())).first();
		
		Document subscribers = (Document) document.get("subscribers");
		ArrayList<Integer> teachers = (ArrayList<Integer>) subscribers.get("teachers");
		ArrayList<Integer> alums = (ArrayList<Integer>) subscribers.get("students");
		
		MongoCursor<Document> cursor = usersCollection.find().iterator();
		try {
		    while (cursor.hasNext()) {
		    	Document user = cursor.next();
		    	if(!teachers.contains(user.getInteger("id")) & !alums.contains(user.getInteger("id"))) {
		    		users.add(new User(user.getInteger("id"),user.getString("name")));
		    	}
		    }
		} finally {
		    cursor.close();
		}
		
		return users;
	}
	
	public void insertTeacher(Course course, User user) {
		Bson filter = Filters.eq("_id", course.getId());
		Bson update = Updates.push("subscribers.teachers",user.getId());
		coursesCollection.findOneAndUpdate(filter, update);
	}
	
	public void insertAlum(Course course, User user) {
		Bson filter = Filters.eq("_id", course.getId());
		Bson update = Updates.push("subscribers.students",user.getId());
		coursesCollection.findOneAndUpdate(filter, update);
	}
	
	public void removeTeacher(Course course, User user) {
		Bson filter = Filters.eq("_id", course.getId());
		Bson update = Updates.pull("subscribers.teachers",user.getId());
		coursesCollection.findOneAndUpdate(filter, update);
	}
	
	public void removeAlum(Course course, User user) {
		Bson filter = Filters.eq("_id", course.getId());
		Bson update = Updates.pull("subscribers.students",user.getId());
		coursesCollection.findOneAndUpdate(filter, update);
	}
}
