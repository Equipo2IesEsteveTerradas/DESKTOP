package classVRroom.view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import classVRroom.App;
import classVRroom.model.Course;
import classVRroom.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CoursesOverviewController {
	@FXML
	private Label classVRroomLabel;
	@FXML
	private ListView<Course> courseList;
	@FXML
	private ListView<User> teachersList;
	@FXML
	private ListView<User> alumsList;
	@FXML
	private ListView<User> usersList;
	@FXML
	private Button crearCursButton;
	@FXML
	private Button eliminarCursButton;
	@FXML
	private Button addUserToProfessors;
	@FXML
	private Button removeUserFromProfessors;
	@FXML
	private Button addUserToAlumnes;
	@FXML
	private Button removeUserFromAlumnes;
	@FXML
	private TextField nomCurs;
	@FXML
	private TextArea descripcioCurs;
	
	private ObservableList<Course> listCourses;
	private ObservableList<User> listTeachers;
	private ObservableList<User> listAlums;
	private ObservableList<User> listUsers;
	
	private Course selectedCourse;
	private User selectedUser;
	
	
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
		courseList.setStyle("-fx-font-size: 14px;-fx-alignment: center;");
		
		teachersList.setCellFactory(cellData -> new ListCell<User>() {
			public void updateItem(User teacher, boolean empty) {
                super.updateItem(teacher, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else if (teacher != null) {
                    setText(teacher.getName());
                } else {
                    setText("null");
                    setGraphic(null);
                }
            }
		});
		
		alumsList.setCellFactory(cellData -> new ListCell<User>() {
			public void updateItem(User alum, boolean empty) {
                super.updateItem(alum, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else if (alum != null) {
                    setText(alum.getName());
                } else {
                    setText("null");
                    setGraphic(null);
                }
            }
		});
		
		usersList.setCellFactory(cellData -> new ListCell<User>() {
			public void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else if (user != null) {
                    setText(user.getName());
                } else {
                    setText("null");
                    setGraphic(null);
                }
            }
		});
		
		nomCurs.setEditable(false);
		descripcioCurs.setEditable(false);
		
		addUserToAlumnes.setStyle("-fx-background-color: #23c44a; ");
		addUserToProfessors.setStyle("-fx-background-color: #23c44a; ");
		removeUserFromAlumnes.setStyle("-fx-background-color: #c43f23; ");
		removeUserFromProfessors.setStyle("-fx-background-color: #c43f23; ");
		
		addUserToProfessors.setDisable(true);
		removeUserFromProfessors.setDisable(true);
		addUserToAlumnes.setDisable(true);
		removeUserFromAlumnes.setDisable(true);
		
	}
	
	@FXML
	public void updateCourses() {
		ArrayList<Course> courses = new ArrayList<Course>();
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
	}
	
	@FXML
	public void handleEliminarCurs() {
		app.showDeleteCourse();
		//courseList.setItems((ObservableList<Course>) app.getCourseList());
	}
	
	@FXML
	public void showDetails() {
		Course course = courseList.getSelectionModel().getSelectedItem();
		this.selectedCourse = course;
		
		nomCurs.setText(course.getTitle());
		descripcioCurs.setText(course.getDescription());
		
		showTeachers(course);
		
		showAlums(course);
		
		showUsers(course);
		
		addUserToProfessors.setDisable(true);
		removeUserFromProfessors.setDisable(true);
		addUserToAlumnes.setDisable(true);
		removeUserFromAlumnes.setDisable(true);
	}
	
	public void showUsers(Course course) {
		ArrayList<User> users = App.getConnection().getUsersRestantes(course);
		listUsers = FXCollections.observableArrayList();
		for (User user : users) {
			listUsers.add(user);
		}
		usersList.setItems(listUsers);
	}
	
	public void showTeachers(Course course) {
		ArrayList<User> teachers = App.getConnection().getProfessorsFromCourse(course);
		listTeachers = FXCollections.observableArrayList();
		for (User teacher : teachers) {
			listTeachers.add(teacher);
		}
		teachersList.setItems(listTeachers);
	}
	
	public void showAlums(Course course) {
		ArrayList<User> alums = App.getConnection().getAlumnesFromCourse(course);
		listAlums = FXCollections.observableArrayList();
		for (User alum : alums) {
			listAlums.add(alum);
		}
		alumsList.setItems(listAlums);
	}
	
	@FXML
	public void getSelectedUserFromUsers() {
		User user = usersList.getSelectionModel().getSelectedItem();
		this.selectedUser = user;
		if(selectedUser == null) {
			addUserToProfessors.setDisable(true);
			removeUserFromProfessors.setDisable(true);
			addUserToAlumnes.setDisable(true);
			removeUserFromAlumnes.setDisable(true);
		}else {
			addUserToProfessors.setDisable(false);
			removeUserFromProfessors.setDisable(true);
			addUserToAlumnes.setDisable(false);
			removeUserFromAlumnes.setDisable(true);
		}
	}
	
	@FXML
	public void getSelectedUserFromProfessors() {
		User user = teachersList.getSelectionModel().getSelectedItem();
		this.selectedUser = user;
		if(selectedUser == null) {
			addUserToProfessors.setDisable(true);
			removeUserFromProfessors.setDisable(true);
			addUserToAlumnes.setDisable(true);
			removeUserFromAlumnes.setDisable(true);
		}else {
			addUserToProfessors.setDisable(true);
			removeUserFromProfessors.setDisable(false);
			addUserToAlumnes.setDisable(true);
			removeUserFromAlumnes.setDisable(true);
		}
	}
	
	@FXML
	public void getSelectedUserFromAlumnes() {
		User user = alumsList.getSelectionModel().getSelectedItem();
		this.selectedUser = user;
		if(selectedUser == null) {
			addUserToProfessors.setDisable(true);
			removeUserFromProfessors.setDisable(true);
			addUserToAlumnes.setDisable(true);
			removeUserFromAlumnes.setDisable(true);
		}else {
			addUserToProfessors.setDisable(true);
			removeUserFromProfessors.setDisable(true);
			addUserToAlumnes.setDisable(true);
			removeUserFromAlumnes.setDisable(false);
		}
	}
	
	@FXML
	public void insertNewTeacher() {
		App.getConnection().insertTeacher(selectedCourse, selectedUser);
		showUsers(selectedCourse);
		showTeachers(selectedCourse);
	}
	
	@FXML
	public void insertNewAlum() {
		App.getConnection().insertAlum(selectedCourse, selectedUser);
		showUsers(selectedCourse);
		showAlums(selectedCourse);
	}
	
	@FXML
	public void removeTeacherFromCourse() {
		App.getConnection().removeTeacher(selectedCourse, selectedUser);
		showUsers(selectedCourse);
		showTeachers(selectedCourse);
	}
	
	@FXML
	public void removeAlumFromCourse() {
		App.getConnection().removeAlum(selectedCourse, selectedUser);
		showUsers(selectedCourse);
		showAlums(selectedCourse);
	}

}
