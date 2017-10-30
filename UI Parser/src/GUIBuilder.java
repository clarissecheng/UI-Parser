
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class GUIBuilder extends Application {

	private double nX, nY;
	private double transX, transY;

	private Label lblComponents;
	private Button btnLabel;
	private Button bthTextfield;
	private Button btnButton;
	private Button btnRender;
	private Button btnClearAll;
	
	private ArrayList<Node> nodeList = new ArrayList<Node>();
	private Group root = new Group();
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		
		Text txtComponents = new Text ("- Components -");
		txtComponents.setFont(Font.font(18));
		txtComponents.setTranslateX(20);
		txtComponents.setTranslateY(40);
		
		VBox vBox = new VBox();
		vBox.setPrefWidth(125);

		bthTextfield = new Button("Text Field");
		bthTextfield.setTranslateX(20);
		bthTextfield.setTranslateY(60);
		bthTextfield.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TextField textField = new TextField("New Textfield");
				textField.setTranslateX(600);
				textField.setTranslateY(350);
				textField.setCursor(Cursor.HAND);
				textField.setOnMousePressed(OnNodePressed);
				textField.setOnMouseDragged(OnNodeDragged);
				
				nodeList.add(textField);
				root.getChildren().add(textField);
			}
		});
		
		btnButton = new Button("Button");
		btnButton.setTranslateX(20);
		btnButton.setTranslateY(100);
		btnButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Button button = new Button("New Button");
				button.setTranslateX(600);
				button.setTranslateY(350);
				button.setCursor(Cursor.HAND);
				button.setOnMousePressed(OnNodePressed);
				button.setOnMouseDragged(OnNodeDragged);
				
				nodeList.add(button);
				root.getChildren().add(button);
			}
		});
		
		btnLabel = new Button("Label");
		btnLabel.setTranslateX(20);
		btnLabel.setTranslateY(140);
		btnLabel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Label label = new Label("New Label");
				label.setTranslateX(600);
				label.setTranslateY(350);
				label.setCursor(Cursor.HAND);
				label.setOnMousePressed(OnNodePressed);
				label.setOnMouseDragged(OnNodeDragged);
				
				nodeList.add(label);
				root.getChildren().add(label);
			}
		});

		btnRender = new Button("Render UI");
		btnRender.setTranslateX(20);
		btnRender.setTranslateY(225);
	
		btnClearAll = new Button("Clear UI");
		btnClearAll.setTranslateX(20);
		btnClearAll.setTranslateY(265);
		btnClearAll.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				for (int i = 0; i < nodeList.size(); i++) 
					root.getChildren().remove(nodeList.get(i));
				
				nodeList.clear();
			}
		});
		
		/*Set buttons to same width*/
		btnLabel.setMinWidth(vBox.getPrefWidth());
		bthTextfield.setMinWidth(vBox.getPrefWidth());
		btnButton.setMinWidth(vBox.getPrefWidth());
		btnClearAll.setMinWidth(vBox.getPrefWidth());
		btnRender.setMinWidth(vBox.getPrefWidth());
		
		vBox.getChildren().addAll(btnLabel, bthTextfield, btnButton, btnClearAll, btnRender);
		
		root.getChildren().addAll(txtComponents, btnLabel, bthTextfield, btnButton, btnRender,
				btnClearAll);

		stage.setResizable(false);
		
		stage.setScene(new Scene(root, 1200, 800, Color.GHOSTWHITE));
		stage.setTitle("UI Builder");
		stage.show();
	}

	EventHandler<MouseEvent> OnNodePressed = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent t) {
			nX = t.getSceneX();
			nY = t.getSceneY();
			transX = (((Node) t.getSource())).getTranslateX();
			transY = ((Node) (t.getSource())).getTranslateY();
		}
	};

	EventHandler<MouseEvent> OnNodeDragged = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent t) {
			double x = t.getSceneX() - nX;
			double y = t.getSceneY() - nY;
			double newTranslateX = transX + x;
			double newTranslateY = transY + y;

			((Node) (t.getSource())).setTranslateX(newTranslateX);
			((Node) (t.getSource())).setTranslateY(newTranslateY);
		}
	};

}