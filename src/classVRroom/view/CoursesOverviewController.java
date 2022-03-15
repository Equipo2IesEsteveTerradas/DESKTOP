package classVRroom.view;

import java.util.ArrayList;

import classVRroom.App;
import classVRroom.model.Course;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class CoursesOverviewController {
	@FXML
	private Label classVRroomLabel;
	@FXML
	private ListView<Course> courseList;
	@FXML
	private Button crearCursButton;
	@FXML
	private Button eliminarCursButton;
	
	private ObservableList<Course> listCourses;
	
	
	private App app;
	
	public CoursesOverviewController() {
	}
	
	@FXML
	private void initialize() {
		courseList.setCellFactory(cellData -> new ListCell<Course>() {
			public void updateItem(Course course, boolean empty) {
                super.updateItem(course, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else if (course != null) {
                    setText(course.getTitle());
                } else {
                    setText("null");
                    setGraphic(null);
                }
            }
		});
		courseList.setStyle("-fx-font-size: 24px;-fx-alignment: center;");
		
	}
	
	@FXML
	public void updateCourses() {
		ArrayList<Course> courses = new ArrayList<Course>();
		this.app = app;
		courses = app.getCourseList();
		listCourses = FXCollections.observableArrayList();
		for (Course course : courses) {
			listCourses.add(course);
		}
		courseList.setItems(listCourses);
		courseList.setCellFactory(cellData -> new ListCell<Course>() {
			public void updateItem(Course course, boolean empty) {
                super.updateItem(course, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else if (course != null) {
                    setText(course.getTitle());
                } else {
                    setText("null");
                    setGraphic(null);
                }
            }
		});
	}
	
	public void setApp(App app) {
		ArrayList<Course> courses = new ArrayList<Course>();
		this.app = app;
		courses = app.getCourseList();
		listCourses = FXCollections.observableArrayList();
		for (Course course : courses) {
			listCourses.add(course);
		}
		courseList.setItems(listCourses);
	}
	
	@FXML
	public void handleCrearCurs() throws InterruptedException {
		CreateCourseController.setIsRunning(true);
		Stage childStage = app.showCreateCourse();
		childStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    public void handle(WindowEvent we) {
		        updateCourses();
		    }
		});
	}
	
	@FXML
	public void handleEliminarCurs() {
		app.showDeleteCourse();
		//courseList.setItems((ObservableList<Course>) app.getCourseList());
	}

}
