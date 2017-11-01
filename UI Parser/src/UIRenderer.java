import java.util.ArrayList;

import javafx.scene.Node;

public class UIRenderer {
	
	private String json;
	private ArrayList<Node> uiElements;
	
	private UIRenderer(ArrayList<Node> uiElements){
		json = "{\n";
		this.uiElements = uiElements;
	}
	
	/* TO DO*/
	public void renderToJSON(){
		if(uiElements != null){
			for(int i = 0; i < uiElements.size(); i++);{
				String elementType = uiElements.get(0).getClass().getSimpleName();
				renderElement(elementType);
			}
		}

		
		json += "}";
	}
	
	public void renderElement(String elementType){
		switch(elementType){
			case "TextField":
				json += "\t \"textfield\":  [";
				break;
			case "Label": 
				json += "\t \"label\":  [";
				break;
			case "Button":
				json += "\t \"button\":  [";
				break;		
		}
	}
	
	public void renderTextField(){
		
	}
	
	
	
	/* Getters and setters */
	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
	
	
	
}
