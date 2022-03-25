package classVRroom;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import classVRroom.model.Course;
import classVRroom.view.CoursesOverviewController;
import classVRroom.view.CreateCourseController;
import classVRroom.view.DeleteCourseController;
import connection.ConnectionDB;

public class App extends Application{
	private Stage primaryStage;
	private BorderPane coursesOverview;
	private static ArrayList<Course> courseList;
	private static ConnectionDB connection;
	
	public App() throws IOException {
		setConnection(new ConnectionDB());
		getCourses();
	}
	
	public static void getCourses() {
		courseList = new ArrayList<Course>();
		courseList = getConnection().getAllCourses();
		for (Course course : courseList) {
			System.out.println(course.getTitle());
			System.out.println(course.getDescription());
		}
	}

	public static ArrayList<Course> getCourseList() {
		return courseList;
	}



	public void setCourseList(ArrayList<Course> courseList) {
		this.courseList = courseList;
	}


	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("classVRroom");
		
		showCoursesOverview();
	}
	
	public void showCoursesOverview(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("view/CoursesOverview.fxml"));
			coursesOverview = (BorderPane) loader.load();
			
			Scene scene = new Scene(coursesOverview);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			CoursesOverviewController controller = loader.getController();
			controller.setApp(this);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public Stage showCreateCourse() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("view/CreateCourse.fxml"));
			AnchorPane createCourse = (AnchorPane) loader.load();
			
			Stage createCourseStage = new Stage();
			createCourseStage.setTitle("Crear curs");
			createCourseStage.initModality(Modality.WINDOW_MODAL);
			createCourseStage.initOwner(primaryStage);
			Scene scene = new Scene(createCourse);
			createCourseStage.setScene(scene);
			
			CreateCourseController controller = loader.getController();
			controller.setStage(createCourseStage);
			controller.setApp(this);
			
			createCourseStage.show();
			
			return createCourseStage;
		}catch(IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public void showDeleteCourse() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("view/DeleteCourse.fxml"));
			BorderPane deleteCourse = (BorderPane) loader.load();
			
			Stage deleteCourseStage = new Stage();
			deleteCourseStage.setTitle("Eliminar curs");
			deleteCourseStage.initModality(Modality.WINDOW_MODAL);
			deleteCourseStage.initOwner(primaryStage);
			Scene scene = new Scene(deleteCourse);
			deleteCourseStage.setScene(scene);
			
			
			DeleteCourseController controller = loader.getController();
			controller.setStage(deleteCourseStage);
			controller.setApp(this);
			
			deleteCourseStage.show();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public static void main(String[] args) throws IOException{
		setConnection(new ConnectionDB());
		launch(args);
	}


	public static ConnectionDB getConnection() {
		return connection;
	}


	public static void setConnection(ConnectionDB connection) {
		App.connection = connection;
	}
	

}
