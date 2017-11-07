import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UIRenderer {
	
	private String json;
	//private ArrayList<Node> uiElements;
	
	private JSONArray labelList;
	private JSONArray buttonList;
	private JSONArray textfieldList;
	
	private ArrayList<Label> uilabelList;
	private ArrayList<Button> uibuttonList;
	private ArrayList<TextField> uitextfieldList;
	
	private JSONArray elementsList;
	private JSONObject elements;
	
	private Group root;
	
	public UIRenderer(){
		
		labelList = new JSONArray();
		buttonList = new JSONArray();
		textfieldList = new JSONArray();
		elementsList = new JSONArray();
		elements = new JSONObject();
	}
	
	public void writeToJSON(File file){
		
		FileWriter fileWriter = null;
		try {
			file.createNewFile();
			fileWriter = new FileWriter(file);
			
			fileWriter.write(json);
			fileWriter.flush();
			
			System.out.println("WRITE TO JSON SUCCESSFULLY!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public JSONArray readFromJSON(File file){
		
		JSONParser parser = new JSONParser();
		try {

            Object obj = parser.parse(new FileReader(file));

            JSONObject jsonObject = (JSONObject) obj;
            
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
    		JsonParser jp = new JsonParser();
    		JsonElement je = jp.parse(jsonObject.toString());
    		json = gson.toJson(je);
            
            /*buttonList = (JSONArray) jsonObject.get("buttons");
            textfieldList = (JSONArray) jsonObject.get("textfields");
            labelList = (JSONArray) jsonObject.get("labels");*/
    		elementsList = new JSONArray();
    		elementsList = (JSONArray) jsonObject.get("elements");

            System.out.println("READ FROM JSON SUCCESSFULLY!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

		return elementsList;
	}
	
	public String renderUI(ArrayList<Node> uiElements){

		elementsList = new JSONArray();
		elements = new JSONObject();
		
		for(int i = 0; i < uiElements.size(); i++){
			String elementType = uiElements.get(i).getClass().getSimpleName();
			
			switch(elementType){
				case "Label":
					Label label = (Label) uiElements.get(i);
					
					JSONObject labeljson = new JSONObject();
					labeljson.put("text", label.getText());
					labeljson.put("x-pos", label.getTranslateX());
					labeljson.put("y-pos", label.getTranslateY());
					labeljson.put("width", label.getWidth());
					labeljson.put("height", label.getHeight());
					JSONObject elabel = new JSONObject();
					elabel.put("label", labeljson);
					elementsList.add(elabel);
					break;
				case "Button":
					Button button = (Button) uiElements.get(i);
					
					JSONObject buttonjson = new JSONObject();
					buttonjson.put("text", button.getText());
					buttonjson.put("x-pos", button.getTranslateX());
					buttonjson.put("y-pos", button.getTranslateY());
					buttonjson.put("width", button.getWidth());
					buttonjson.put("height", button.getHeight());
					
					JSONObject ebutton = new JSONObject();
					ebutton.put("button", buttonjson);
					elementsList.add(ebutton);
					break;
				case "TextField":
					TextField textfield = (TextField) uiElements.get(i);
					
					JSONObject textfieldjson = new JSONObject();
					textfieldjson.put("text", textfield.getText());
					textfieldjson.put("x-pos", textfield.getTranslateX());
					textfieldjson.put("y-pos", textfield.getTranslateY());
					textfieldjson.put("width", textfield.getWidth());
					textfieldjson.put("height", textfield.getHeight());

					JSONObject etextfield = new JSONObject();
					etextfield.put("textfield", textfieldjson);
					elementsList.add(etextfield);
					break;
			}
			
		}
		
		elements.put("elements", elementsList);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(elements.toJSONString());
		json = gson.toJson(je);
		
		return json;
	}
	
	public void convertToJSON(ArrayList<Node> uiElements){
		
		labelList = new JSONArray();
		buttonList = new JSONArray();
		textfieldList = new JSONArray();
		
		for(int i = 0; i < uiElements.size(); i++){
			String elementType = uiElements.get(i).getClass().getSimpleName();
			
			switch(elementType){
				case "Label":
					Label label = (Label) uiElements.get(i);
					
					JSONObject labeljson = new JSONObject();
					labeljson.put("text", label.getText());
					labeljson.put("x-pos", label.getTranslateX());
					labeljson.put("y-pos", label.getTranslateY());
					labeljson.put("width", label.getWidth());
					labeljson.put("height", label.getHeight());
					labelList.add(labeljson);
					break;
				case "Button":
					Button button = (Button) uiElements.get(i);
					
					JSONObject buttonjson = new JSONObject();
					buttonjson.put("text", button.getText());
					buttonjson.put("x-pos", button.getTranslateX());
					buttonjson.put("y-pos", button.getTranslateY());
					buttonjson.put("width", button.getWidth());
					buttonjson.put("height", button.getHeight());
					buttonList.add(buttonjson);
					break;
				case "TextField":
					TextField textfield = (TextField) uiElements.get(i);
					
					JSONObject textfieldjson = new JSONObject();
					textfieldjson.put("text", textfield.getText());
					textfieldjson.put("x-pos", textfield.getTranslateX());
					textfieldjson.put("y-pos", textfield.getTranslateY());
					textfieldjson.put("width", textfield.getWidth());
					textfieldjson.put("height", textfield.getHeight());
					textfieldList.add(textfieldjson);
					break;
			}
			
		}
	}
	
	
	/* Getters and setters */
	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public JSONArray getLabelList() {
		return labelList;
	}

	public void setLabelList(JSONArray labelList) {
		this.labelList = labelList;
	}

	public JSONArray getButtonList() {
		return buttonList;
	}

	public void setButtonList(JSONArray buttonList) {
		this.buttonList = buttonList;
	}

	public JSONArray getTextfieldList() {
		return textfieldList;
	}

	public void setTextfieldList(JSONArray textfieldList) {
		this.textfieldList = textfieldList;
	}
	
	
	
}
