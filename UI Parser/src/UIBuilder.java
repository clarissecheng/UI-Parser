import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class UIBuilder extends Application {

	private Button btnLabel, bthTextfield, btnButton,
				   btnModifyElement,
				   btnRender, btnClearAll;
		
	private Text txtWidth, txtHeight, txtText;
	private TextField tfText, tfWidth, tfHeight;
	
	private ArrayList<Node> uiElements;
	private Group root;
	
	private UIEventHandlers uiController;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		/* init */
		uiElements = new ArrayList<Node>();
		root = new Group();
		uiController = new UIEventHandlers(this);
		
		Text txtComponents = new Text ("- Components -");
		txtComponents.setFont(Font.font(18));
		txtComponents.setTranslateX(20);
		txtComponents.setTranslateY(40);
		
		VBox vBox = new VBox();
		vBox.setPrefWidth(125);
		
		VBox tfVBox = new VBox();
		tfVBox.setPrefWidth(70);
		
		/* UI Builder Components */
		bthTextfield = new Button("Text Field");
		bthTextfield.setTranslateX(20);
		bthTextfield.setTranslateY(60);
		bthTextfield.setOnAction(uiController.newTextFieldAction);
		
		btnButton = new Button("Button");
		btnButton.setTranslateX(20);
		btnButton.setTranslateY(100);
		btnButton.setOnAction(uiController.newButtonAction);
		
		btnLabel = new Button("Label");
		btnLabel.setTranslateX(20);
		btnLabel.setTranslateY(140);
		btnLabel.setOnAction(uiController.newLabelAction);		

		Text txtAttributes = new Text ("- Attributes -");
		txtAttributes.setFont(Font.font(18));
		txtAttributes.setTranslateX(20);
		txtAttributes.setTranslateY(225);
		
		txtWidth = new Text ("Width: ");
		txtWidth.setFont(Font.font(14));
		txtWidth.setTranslateX(25);
		txtWidth.setTranslateY(300);	
		
		txtHeight = new Text ("Height: ");
		txtHeight.setFont(Font.font(14));
		txtHeight.setTranslateX(25);
		txtHeight.setTranslateY(340);	
		
		txtText = new Text ("Text: ");
		txtText.setFont(Font.font(14));
		txtText.setTranslateX(25);
		txtText.setTranslateY(260);	
		
		tfText = new TextField();
		tfText.setTranslateX(80);
		tfText.setTranslateY(240);
		
		tfWidth = new TextField();
		tfWidth.setTranslateX(80);
		tfWidth.setTranslateY(280);
		
		tfHeight = new TextField();
		tfHeight.setTranslateX(80);
		tfHeight.setTranslateY(320);
		
		btnModifyElement = new Button("Modify Element");
		btnModifyElement.setTranslateX(20);
		btnModifyElement.setTranslateY(365);
		btnModifyElement.setOnAction(uiController.modifyElement);
		
		btnRender = new Button("Render UI");
		btnRender.setTranslateX(20);
		btnRender.setTranslateY(440);
	
		btnClearAll = new Button("Clear UI");
		btnClearAll.setTranslateX(20);
		btnClearAll.setTranslateY(480);
		btnClearAll.setOnAction(uiController.clearAllAction);
		
		/*Set buttons to same width*/
		btnLabel.setMinWidth(vBox.getPrefWidth());
		bthTextfield.setMinWidth(vBox.getPrefWidth());
		btnButton.setMinWidth(vBox.getPrefWidth());
		tfText.setPrefWidth(tfVBox.getPrefWidth());
		tfWidth.setPrefWidth(tfVBox.getPrefWidth());
		tfHeight.setPrefWidth(tfVBox.getPrefWidth());
		btnClearAll.setMinWidth(vBox.getPrefWidth());
		btnModifyElement.setMinWidth(vBox.getPrefWidth());
		btnRender.setMinWidth(vBox.getPrefWidth());
		
		vBox.getChildren().addAll(btnLabel, bthTextfield, btnButton, btnModifyElement, btnClearAll, btnRender);
		
		tfVBox.getChildren().addAll(tfText, tfWidth, tfHeight);
		
		root.getChildren().addAll(txtComponents, btnLabel, bthTextfield, btnButton, 
				txtAttributes, tfText,tfWidth, tfHeight, btnModifyElement,
				btnRender, btnClearAll, txtText, txtWidth, txtHeight);

		stage.setResizable(false);
		
		stage.setScene(new Scene(root, 1366, 768, Color.GHOSTWHITE));
		stage.setTitle("UI Builder");
		stage.show();
	}
	
	/* Getters & Setters*/
	public ArrayList<Node> getUIElements(){
		return uiElements;
	}
	
	public Group getRoot(){
		return root;
	}
	
	public TextField getTfText() {
		return tfText;
	}


	public TextField getTfWidth() {
		return tfWidth;
	}

	public TextField getTfHeight() {
		return tfHeight;
	}

	
}