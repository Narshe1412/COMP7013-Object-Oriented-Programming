package Scenes;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.paint.Paint;

public class MyScene extends Scene{

	public MyScene(Parent root, Paint fill) {
		super(root, fill);
		// TODO Auto-generated constructor stub
	}

	public MyScene(Parent root, double width, double height, Paint fill) {
		super(root, width, height, fill);
		// TODO Auto-generated constructor stub
	}


	public MyScene(Parent root, double width, double height, boolean depthBuffer, SceneAntialiasing antiAliasing) {
		super(root, width, height, depthBuffer, antiAliasing);
		// TODO Auto-generated constructor stub
	}
	public MyScene(Parent root, double width, double height, boolean depthBuffer) {
		super(root, width, height, depthBuffer);
		// TODO Auto-generated constructor stub
	}

	public MyScene(Parent root, double width, double height) {
		super(root, width, height);
		// TODO Auto-generated constructor stub
	}
	
	public MyScene(Parent arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
}
