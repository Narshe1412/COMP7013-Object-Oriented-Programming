package application;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * Creates an analog watch with stop and pause functionality
 * 
 * @author Manuel Colorado
 *
 */
public class Main extends Application {
	/** Creates local variables for tracking the time and the clock itself */
	private Timeline timeline;
	private Timeline stopwatch;
	private ClockPane clock;
	private Label lblCurrentTime;
	private long stopwatchElapsedTime;
	private long stopwatchPausedTime;

	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) {
		/** Resets the stopwatch timer */
		stopwatchPausedTime = 0;

		/** Creates a grid for placement the START, STOP button */
		GridPane buttonGrid = new GridPane();
		buttonGrid.setAlignment(Pos.CENTER);
		buttonGrid.setHgap(10);
		buttonGrid.setVgap(10);

		/** Creates the set of buttons for the application */
		Button startButton = new Button("Start");
		Button pauseButton = new Button("Pause");
		Button stopButton = new Button("Stop");

		/** Adds the start button to the grid */
		buttonGrid.add(startButton, 0, 0);
		GridPane.setHalignment(startButton, HPos.CENTER);

		/**
		 * Creates the onClick event to start the stopwatch, swaps the Pause and Start
		 * button, enables the Stop button
		 */
		startButton.setOnAction(event -> {
			startStopWatch();
			startButton.setVisible(false);
			stopButton.setDisable(false);
			pauseButton.setVisible(true);
			pauseButton.requestFocus();
		});

		/** Adds the pause button to the grid */
		buttonGrid.add(pauseButton, 0, 0);
		GridPane.setHalignment(pauseButton, HPos.CENTER);
		/** Sets the pause button as invisible until the stopwatch */
		pauseButton.setVisible(false);

		/**
		 * Creates the onClick event to pause the stopwatch, swaps the Pause and Start
		 * button
		 */
		pauseButton.setOnAction(event -> {
			pauseStopWatch();
			pauseButton.setVisible(false);
			startButton.setVisible(true);
			startButton.requestFocus();
		});

		/** Add the stop button to the grid */
		GridPane.setHalignment(stopButton, HPos.CENTER);
		buttonGrid.add(stopButton, 1, 0);
		/** Sets the stop button as disabled while the stopwatch is not running */
		stopButton.setDisable(true);

		/**
		 * Creates the onClick event to stop the stopwatch, disables the stop button,
		 * makes sure the start button is the one visible
		 */
		stopButton.setOnAction(event -> {
			stopStopWatch();
			stopButton.setDisable(true);
			pauseButton.setVisible(false);
			startButton.setVisible(true);
		});

		// Create a clock and a label based in the current time
		clock = new ClockPane();
		String timeString = clock.getTimeString();
		lblCurrentTime = new Label(timeString);

		// Creates a timer that executes every 500ms to set up the current time in the
		// clock
		timeline = new Timeline(new KeyFrame(Duration.millis(500), event -> {
			clock.setCurrentTime();
			lblCurrentTime.setText(clock.getTimeString());
		}));
		/** Starts the timer and runs indefinitely */
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();

		// Place buttons, clock and label in border pane
		BorderPane pane = new BorderPane();
		pane.setTop(buttonGrid);
		BorderPane.setAlignment(buttonGrid, Pos.CENTER);
		pane.setCenter(clock);
		pane.setBottom(lblCurrentTime);
		BorderPane.setAlignment(lblCurrentTime, Pos.TOP_CENTER);

		// Create a scene and place it in the stage
		Scene scene = new Scene(pane, 250, 250);
		primaryStage.setTitle("Stopwatch"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
	}

	/**
	 * Starts the stopwatch in the system
	 */
	private void startStopWatch() {
		/** Stops the clock timer*/
		timeline.stop();
		/** Grabs the current time to calculate the elapsed time*/
		long startTime = System.currentTimeMillis();
		/** Creates a new timer that runs every 500ms and calculates the elapsed time since the start of the stopwatch*/
		stopwatch = new Timeline(new KeyFrame(Duration.millis(500), event -> {
			/** Tracks the elapsed time for the stopwatch */
			stopwatchElapsedTime = System.currentTimeMillis() - startTime;
			/** Sets up the clock with the elapsed time, and the recorded elapsed time since the paused time was clicked*/
			clock.setTime(stopwatchElapsedTime + stopwatchPausedTime);
			lblCurrentTime.setText(clock.getTimeString());
		}));
		/** Sets the timer to execute indefinitely */
		stopwatch.setCycleCount(Animation.INDEFINITE);
		stopwatch.play();
	}

	/** 
	 * Pauses the timer that tracks the stopwatch
	 * */
	private void pauseStopWatch() {
		stopwatch.stop();
		/** Stores the current elapsed time so it can be added to future runs*/
		stopwatchPausedTime += stopwatchElapsedTime;
	}

	/**
	 * Stops the stopwatch timer and starts the normal timer for the regular clock
	 */
	private void stopStopWatch() {
		stopwatch.stop();
		stopwatchPausedTime = 0;
		timeline.play();
	}

	/**
	 * The main method is only needed for the IDE with limited JavaFX support. Not
	 * needed for running from the command line.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
