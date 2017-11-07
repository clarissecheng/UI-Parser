import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;



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
	
	
	private UIRenderer uiRenderer;

	public UIEventHandlers(UIBuilder uiBuilder){
		this.uiBuilder = uiBuilder;
		this.uiElements = uiBuilder.getUIElements();
		this.root = uiBuilder.getRoot();
		
		uiRenderer = new UIRenderer();
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
			uiBuilder.updatetaJSON("");
		}
	};
	
	EventHandler<ActionEvent> renderUIAction = new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent event) {
			String json = uiRenderer.renderUI(uiElements);
			uiBuilder.updatetaJSON(json);
		}
	};
	
	EventHandler<ActionEvent> saveJSONAction = new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent event) {
			
			 FileChooser fileChooser = new FileChooser();
			  
             //Set extension filter
             FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
             fileChooser.getExtensionFilters().add(extFilter);
             
             //Show save file dialog
             File file = fileChooser.showSaveDialog(uiBuilder.getStage());
             
             if(file != null){
            	 uiRenderer.writeToJSON(file);
             }
		
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setHeaderText("Successfully saved in a JSON file!!");
			alert.show();

		}
	};
	
	EventHandler<ActionEvent> loadJSONAction = new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent event) {
			
			FileChooser fileChooser = new FileChooser();
			//Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
            fileChooser.getExtensionFilters().add(extFilter);
            
            File selectedFile = fileChooser.showOpenDialog(null);

            if(selectedFile != null){
            	JSONArray elements = uiRenderer.readFromJSON(selectedFile);
    			String json = uiRenderer.getJson();
    			
    			for (int i = 0; i < uiElements.size(); i++) 
    				root.getChildren().remove(uiElements.get(i));
    			
    			uiElements.clear();
    			
    			uiBuilder.updatetaJSON(json);
    			
    			loadUI(elements);
    			
    			/*if(uiRenderer.getLabelList() != null){
    				loadUILabels(uiRenderer.getLabelList());
    			}
    			if(uiRenderer.getButtonList() != null){
    				loadUIButtons(uiRenderer.getButtonList());
    			}
    			if(uiRenderer.getTextfieldList() != null){
    				loadUITextFields(uiRenderer.getTextfieldList());
    			}*/
    			
            }
			
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
			
			((Node)(t.getSource())).toFront();
			int first = uiElements.indexOf((Node)(t.getSource()));
			
			Collections.swap(uiElements, first, uiElements.size() - 1);
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
	
	public void loadUI(JSONArray elements){
		if(elements.size() > 0){
			for(int i = 0; i < elements.size(); i++){
				JSONObject object = (JSONObject) elements.get(i);
				if(object.containsKey("label")){
					JSONObject labelJSON = (JSONObject) object.get("label");
					String text = (String) labelJSON.get("text");
					double xpos = (Double) labelJSON.get("x-pos");
					double ypos = (Double) labelJSON.get("y-pos");
					double width = (Double) labelJSON.get("width");
					double height = (Double) labelJSON.get("height");
					
					Label label = new Label(text);
					label.setPrefWidth(width);
					label.setPrefHeight(height);
					label.setTranslateX(xpos);
					label.setTranslateY(ypos);
					label.setCursor(Cursor.HAND);
					label.setOnMousePressed(OnElementPressed);
					label.setOnMouseDragged(OnElementDragged);
					
					uiElements.add(label);
					root.getChildren().add(label);
				}
				
				else if(object.containsKey("button")){
					JSONObject labelJSON = (JSONObject) object.get("button");
					String text = (String) labelJSON.get("text");
					double xpos = (Double) labelJSON.get("x-pos");
					double ypos = (Double) labelJSON.get("y-pos");
					double width = (Double) labelJSON.get("width");
					double height = (Double) labelJSON.get("height");
					
					Button button = new Button(text);
					button.setPrefWidth(width);
					button.setPrefHeight(height);
					button.setTranslateX(xpos);
					button.setTranslateY(ypos);
					button.setCursor(Cursor.HAND);
					button.setOnMousePressed(OnElementPressed);
					button.setOnMouseDragged(OnElementDragged);
					
					uiElements.add(button);
					root.getChildren().add(button);
				}
				
				else if(object.containsKey("textfield")){
					JSONObject labelJSON = (JSONObject) object.get("textfield");
					String text = (String) labelJSON.get("text");
					double xpos = (Double) labelJSON.get("x-pos");
					double ypos = (Double) labelJSON.get("y-pos");
					double width = (Double) labelJSON.get("width");
					double height = (Double) labelJSON.get("height");
					
					TextField textfield = new TextField(text);
					textfield.setPrefWidth(width);
					textfield.setPrefHeight(height);
					textfield.setTranslateX(xpos);
					textfield.setTranslateY(ypos);
					textfield.setCursor(Cursor.HAND);
					textfield.setOnMousePressed(OnElementPressed);
					textfield.setOnMouseDragged(OnElementDragged);
					
					uiElements.add(textfield);
					root.getChildren().add(textfield);
				}
			}
		}
	}
	
	/*public void loadUILabels(JSONArray labelJSONList){

		
		if(labelJSONList.size() > 0){
			for(int i = 0; i < labelJSONList.size(); i++){
				JSONObject object = (JSONObject) labelJSONList.get(i);
				String text = (String) object.get("text");
				double xpos = (Double) object.get("x-pos");
				double ypos = (Double) object.get("y-pos");
				double width = (Double) object.get("width");
				double height = (Double) object.get("height");
				
				Label label = new Label(text);
				label.setPrefWidth(width);
				label.setPrefHeight(height);
				label.setTranslateX(xpos);
				label.setTranslateY(ypos);
				label.setCursor(Cursor.HAND);
				label.setOnMousePressed(OnElementPressed);
				label.setOnMouseDragged(OnElementDragged);
				
				uiElements.add(label);
				root.getChildren().add(label);
			}
		}
		
	}
	
	public void loadUIButtons(JSONArray buttonJSONList){
	
		if(buttonJSONList.size() > 0){
			for(int i = 0; i < buttonJSONList.size(); i++){
				JSONObject object = (JSONObject) buttonJSONList.get(i);
				String text = (String) object.get("text");
				double xpos = (Double) object.get("x-pos");
				double ypos = (Double) object.get("y-pos");
				double width = (Double) object.get("width");
				double height = (Double) object.get("height");
				
				Button button = new Button(text);
				button.setPrefWidth(width);
				button.setPrefHeight(height);
				button.setTranslateX(xpos);
				button.setTranslateY(ypos);
				button.setCursor(Cursor.HAND);
				button.setOnMousePressed(OnElementPressed);
				button.setOnMouseDragged(OnElementDragged);
				
				uiElements.add(button);
				root.getChildren().add(button);
			}
		}

	}
	
	public void loadUITextFields(JSONArray textfieldJSONList){
		
		if(textfieldJSONList.size() > 0){
			for(int i = 0; i < textfieldJSONList.size(); i++){
				JSONObject object = (JSONObject) textfieldJSONList.get(i);
				String text = (String) object.get("text");
				double xpos = (Double) object.get("x-pos");
				double ypos = (Double) object.get("y-pos");
				double width = (Double) object.get("width");
				double height = (Double) object.get("height");
				
				TextField textfield = new TextField(text);
				textfield.setPrefWidth(width);
				textfield.setPrefHeight(height);
				textfield.setTranslateX(xpos);
				textfield.setTranslateY(ypos);
				textfield.setCursor(Cursor.HAND);
				textfield.setOnMousePressed(OnElementPressed);
				textfield.setOnMouseDragged(OnElementDragged);
				
				uiElements.add(textfield);
				root.getChildren().add(textfield);
			}
		}
	}*/
}
