package DialogueAlerte;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class DialogueAlerteSql {
	
	private Alert alert = new Alert(AlertType.ERROR);
	private StringWriter sw = new StringWriter();
	private PrintWriter pw = new PrintWriter(sw);
	private Label label = new Label("L'exception était:");
	private GridPane expContent = new GridPane();
	
	public DialogueAlerteSql(SQLException e, String Title, String HeaderText, String ContentText) {	
	alert.setTitle(Title);
	alert.setHeaderText(HeaderText);
	alert.setContentText(ContentText);
	e.printStackTrace(pw);
	String exceptionText = sw.toString();
	TextArea textArea = new TextArea(exceptionText);
	textArea.setEditable(false);
	textArea.setWrapText(true);
	textArea.setMaxWidth(Double.MAX_VALUE);
	textArea.setMaxHeight(Double.MAX_VALUE);
	GridPane.setVgrow(textArea, Priority.ALWAYS);
	GridPane.setHgrow(textArea, Priority.ALWAYS);
	expContent.setMaxWidth(Double.MAX_VALUE);
	expContent.add(label, 0, 0);
	expContent.add(textArea, 0, 1);
	alert.getDialogPane().setExpandableContent(expContent);
	alert.showAndWait();
	}
}
