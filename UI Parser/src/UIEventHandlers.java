import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;



public class UIEventHandlers {
	
	private double nX, nY;
	private double transX, transY;
	private TextField selectedTextField;
	private Button selectedButton;
	private Label selectedLabel;
	
	private UIBuilder uiBuilder;
	private ArrayList<Node> uiElements;
	private Group root;
	private String selectedElementType;

	public UIEventHandlers(UIBuilder uiBuilder){
		this.uiBuilder = uiBuilder;
		this.uiElements = uiBuilder.getUIElements();
		this.root = uiBuilder.getRoot();
	}
	
	EventHandler<ActionEvent> newTextFieldAction = new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent event) {
			TextField textField = new TextField("New Textfield");
			textField.setTranslateX(600);
			textField.setTranslateY(350);
			textField.setCursor(Cursor.HAND);
			textField.setOnMousePressed(OnElementPressed);
			textField.setOnMouseDragged(OnElementDragged);

			uiElements.add(textField);
			root.getChildren().add(textField);
		}
	};
	
	EventHandler<ActionEvent> newButtonAction = new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent event) {
			Button button = new Button("New Button");
			button.setTranslateX(600);
			button.setTranslateY(350);
			button.setCursor(Cursor.HAND);
			button.setOnMousePressed(OnElementPressed);
			button.setOnMouseDragged(OnElementDragged);
			
			uiElements.add(button);
			root.getChildren().add(button);
		}
		

	};
	
	EventHandler<ActionEvent> newLabelAction = new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent event) {
			Label label = new Label("New Label");
			label.setTranslateX(600);
			label.setTranslateY(350);
			label.setCursor(Cursor.HAND);
			label.setOnMousePressed(OnElementPressed);
			label.setOnMouseDragged(OnElementDragged);
			
			uiElements.add(label);
			root.getChildren().add(label);
		}
	};
	

	
	EventHandler<ActionEvent> modifyElement = new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent event) {
			String text = uiBuilder.getTfText().getText();
			double width = Double.parseDouble(uiBuilder.getTfWidth().getText());
			double height = Double.parseDouble(uiBuilder.getTfHeight().getText());
			switch(selectedElementType){
				case "TextField":
					selectedTextField.setText(text);
					selectedTextField.setPrefWidth(width);
					selectedTextField.setPrefHeight(height);
					break;
				case "Button":
					selectedButton.setText(text);
					selectedButton.setPrefWidth(width);
					selectedButton.setPrefHeight(height);
					break;
				case "Label": 
					selectedLabel.setText(text);
					selectedLabel.setPrefWidth(width);
					selectedLabel.setPrefHeight(height);
					break;
			}
		}
	};

	EventHandler<ActionEvent> clearAllAction = new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent event) {
			for (int i = 0; i < uiElements.size(); i++) 
				root.getChildren().remove(uiElements.get(i));
			
			uiElements.clear();
		}
	};

	EventHandler<MouseEvent> OnElementPressed = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent t) {
			updateSelectedElement((Node) t.getSource());
			nX = t.getSceneX();
			nY = t.getSceneY();
			transX = (((Node) t.getSource())).getTranslateX();
			transY = ((Node) (t.getSource())).getTranslateY();
		}
	};
	
	public void updateSelectedElement(Node element){
		String elementType = element.getClass().getSimpleName();
		selectedElementType = elementType;
		
		switch(elementType){
			case "TextField":
				selectedTextField = (TextField) element;
				displayAttr(selectedTextField.getText(), selectedTextField.getWidth(), selectedTextField.getHeight());
				break;
			case "Button":
				selectedButton = (Button) element;
				displayAttr(selectedButton.getText(), selectedButton.getWidth(), selectedButton.getHeight());
				break;
			case "Label":
				selectedLabel = (Label) element;
				displayAttr(selectedLabel.getText(), selectedLabel.getWidth(), selectedLabel.getHeight());
				break;
		}
	}
	
	
	public void displayAttr(String text, double width, double height){
		uiBuilder.getTfText().setText(text);
		uiBuilder.getTfWidth().setText(width + "");
		uiBuilder.getTfHeight().setText(height + "");
	}
	
	
	EventHandler<MouseEvent> OnElementDragged = new EventHandler<MouseEvent>() {
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
