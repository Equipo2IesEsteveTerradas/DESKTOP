package classVRroom.view;

import classVRroom.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class CreateCourseController {
	@FXML
	private Label nomCursLabel;
	@FXML
	private Label descripcioCursLabel;
	@FXML
	private Label errorLabel;
	@FXML
	private TextField nomCursField;
	@FXML
	private TextArea descripcioCursField;
	@FXML
	private Button crearCurs;
	
	private Stage stage;
	
	private App app;
	
	private String json = "{\"title\": \"$TITLE\",\"description\": \"$DESCRIPTION\",\"subscribers\": {\"teachers\": [ 101, 102 ],\"students\": [ 1, 2, 3, 4 ]},\"elements\": [{\"ID\": 1,\"type\": \"HTML\",\"title\": \"Traslado de pacientes\",\"description\": \"Información sobre el traslado de pacientes\",\"order\": 1,\"contents\": \"<h1>Apuntes de traslado de pacientes</h1><p>El traslado ...</p>\"},{\"ID\": 2,\"type\": \"file\",\"title\": \"Primeros auxilios\",\"description\": \"Información sobre primeros auxilios\",\"order\": 2,\"file\": \"file:///media/apuntes.pdf\"}],\"tasks\": [{\"ID\": 3,\"type\": \"file\",\"title\": \"Cambio a postura lateral\",\"description\": \"Inmovilización de pacientes en cama\",\"order\": 1,\"uploads\": [{\"studentID\": 1,\"text\": \"Entrega del ejercicio 1\",\"file\": \"Ejercicio1-lola.pdf\",\"grade\": 8,\"feedback\": \"Buen trabajo\"},{\"studentID\": 2,\"text\": \"Entrega del ejercicio 1\",\"file\": \"Ejercicio1-pepe.pdf\",\"grade\": 6,\"feedback\": \"Buen trabajo\"}]},{\"ID\": 4,\"type\": \"HTML\",\"title\": \"Cambio a postura frontal\",\"description\": \"Inmovilización de pacientes en cama de manera frontal\",\"order\": 2,\"uploads\": [{\"studentID\": 3,\"text\": \"loren ipsum dolo sit amet...\",\"grade\": 5,\"feedback\": \"Hay que mejorar\"},{\"studentID\": 4,\"text\": \"lorem ipsum chiquito de la calzada...\",\"grade\": 3,\"feedback\": \"Hay que mejorar\"}]}],\"vr_tasks\": [{\"ID\": 5,\"title\": \"Movilización hacia el borde de la cama\",\"descripcion\": \"lorem ipsum movilización borde de la cama\",\"VRexID\": 22,\"versionID\": 26,\"pollID\": 1,\"completions\": [{\"studentID\": 1,\"position_data\": { \"data\": \"...to be decided...\" },\"autograde\": {\"passed_items\": 5,\"failed_items\": 3,\"comments\": \"...to be decided...\"},\"grade\": 7,\"feedback\": \"Mala postura lateral, riesgo de esguince\"},{\"studentID\": 2,\"position_data\": { \"data\": \"...to be decided...\" },\"autograde\": {\"passed_items\": 6,\"failed_items\": 2,\"comments\": \"...to be decided...\"},\"grade\": 8,\"feedback\": \"Buena postura lateral, mejora tobillo\"}]},{\"ID\": 6,\"title\": \"Movilización al cabecero de la cama\",\"descripcion\": \"lorem impsum movilización cabecero\",\"VRexID\": 22,\"versionID\": 26,\"pollID\": 1,\"completions\": [{\"studentID\": 1,\"position_data\": { \"data\": \"...to be decided...\" },\"autograde\": {\"passed_items\": 1,\"failed_items\": 7,\"comments\": \"...to be decided...\"},\"grade\": 2,\"feedback\": \"Mala posición lumbares. Pasos incompletos.\"},{\"studentID\": 2,\"position_data\": { \"data\": \"...to be decided...\" },\"autograde\": {\"passed_items\": 6,\"failed_items\": 2,\"comments\": \"...to be decided...\"},\"grade\": 8,\"feedback\": \"Buena postura frontal, mejora tobillo, riesgo de esguince\"}]}]}";
	
	private static boolean isRunning;
	
	public CreateCourseController() {
	}
	
	@FXML
	public void initialize() {
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public void setApp(App app) {
		this.app = app;
	}
	
	public void handleCrearCurs() {
		String title = nomCursField.getText();
		String description = descripcioCursField.getText();
		System.out.println(title);
		if(title.equals("")) {
			errorLabel.setText("Introduce un título");
		}else {
			json = json.replace("$TITLE", title);
			json = json.replace("$DESCRIPTION", description);
			App.getConnection().insertCourse(json);
			App.getCourses();
			stage.close();
			app.showCoursesOverview();
		}
	}
	
	public static boolean isRunning() {
		return isRunning;
	}

	public static void setIsRunning(boolean b) {
		isRunning = b;
		
	}

}
