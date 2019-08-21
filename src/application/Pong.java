package application;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Pong extends Application {

	Rectangle player, player2;
	Circle ball;
	Line line;
	AnimationTimer timer;
	private final int WIDTH = 1000, HEIGTH = 400;
	private int speedX = 3, speedY = 3, dv = speedX, dy = speedY;
	int playerScore = 0;
	int player2Score = 0;

	@Override
	public void start(Stage pongStage) {
		try {
			Pane root = new BorderPane();
			root.setPrefSize(WIDTH, HEIGTH);

			line = new Line(WIDTH / 2, 0, WIDTH / 2, HEIGTH);
			line.setStroke(Color.WHITE);

			player2 = new Rectangle(10, 80, Color.WHITE);
			player2.setLayoutX(WIDTH - 10);
			player2.setLayoutY(HEIGTH / 2 - 40);

			player = new Rectangle(10, 80, Color.WHITE);
			player.setLayoutX(0);
			player.setLayoutY(HEIGTH / 2 - 40);

			ball = new Circle(5, Color.WHITE);
			ball.setLayoutX(WIDTH / 2);
			ball.setLayoutY(HEIGTH / 2);

			root.getChildren().addAll(line, player, player2, ball);
			Scene scene = new Scene(root, WIDTH, HEIGTH, Color.BLACK);

			timer = new AnimationTimer() {
				@Override
				public void handle(long arg0) {
					speedX = 3;
					speedY = 3;
					double x = ball.getLayoutX(), y = ball.getLayoutY();

					if (x <= 10 && y > player.getLayoutY() && y < player.getLayoutY() + 80) {
						dv = speedX;
					}

					if (x >= WIDTH - 12.5 && y > player2.getLayoutY() && y < player2.getLayoutY() + 80) {
						speedX = speedX + 1;
						dv = -speedX;
					}

					if (y <= 0) {
						dy = speedY;
					}
					if (y >= HEIGTH - 5) {
						dy = -speedY;
					}

					ball.setLayoutX(ball.getLayoutX() + dv);
					ball.setLayoutY(ball.getLayoutY() + dy);

					//automating player 2 for testing
					if (x < WIDTH / 2 || player2.getLayoutY() > y) {
						player2.setLayoutY(player2.getLayoutY() - 5);
					}
					if (x < WIDTH / 2 || player2.getLayoutY() + 80 < y) {
						player2.setLayoutY(player2.getLayoutY() + 5);
					}

					if (x <= 0) {
						player2Score++;
						endGame();
					}
					if (x >= WIDTH) {
						playerScore++;
						endGame();
					}
				}

			};

			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			pongStage.setTitle("pong");
			pongStage.setScene(scene);
			pongStage.show();
			pongStage.getScene().setOnKeyPressed(event -> {
				if (event.getCode() == KeyCode.UP) {
					if (player.getLayoutY() > 0) {
						player.setLayoutY(player.getLayoutY() - 30);
						}
					}
				if (event.getCode() == KeyCode.DOWN) {
					if (player.getLayoutY() < (HEIGTH-80)) {
						player.setLayoutY(player.getLayoutY() + 30);
					}
				}
				if (event.getCode() == KeyCode.SPACE) {
					timer.start();
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void endGame() {
		try {
			ball.setLayoutY(HEIGTH / 2);
			ball.setLayoutX(WIDTH / 2);
			speedX = 0;
			speedY = 0;
			timer.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);

	}
}
