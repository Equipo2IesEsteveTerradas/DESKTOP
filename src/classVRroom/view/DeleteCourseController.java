package classVRroom.view;

import java.util.ArrayList;

import classVRroom.App;
import classVRroom.model.Course;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class DeleteCourseController {
	@FXML
	private Label mainLabel;
	@FXML
	private ListView<Course> courseList;
	@FXML
	private Button eliminarCurs;
	
	private Stage stage;
	
	private App app;
	
	private ObservableList<Course> listCourses;
	
	public DeleteCourseController() {
	}
	
	@FXML
	public void initialize() {
		ArrayList<Course> courses = new ArrayList<Course>();
		courses = App.getCourseList();
		listCourses = FXCollections.observableArrayList();
		for (Course course : courses) {
			listCourses.add(course);
			//System.out.println(course.getTitle());
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

	public Stage getStage() {
		return stage;
	}
	
	public void setApp(App app) {
		this.app = app;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public void handleEliminarCurs() {
		Course course = courseList.getSelectionModel().getSelectedItem();
		if(course != null) {
			App.getConnection().deleteCourse(course.getId());
			App.getCourses();
			stage.close();
			app.showCoursesOverview();
		}
	}

}
