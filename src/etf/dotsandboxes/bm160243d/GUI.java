package etf.dotsandboxes.bm160243d;


import javafx.application.*;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUI extends Application {
	final int HEIGHT = 600;
	final int WIDTH = 800;
	final int DOT_RADIUS = 30;
	final int MARGIN = 20;
	final int LINE_WIDTH = 30;

	int M = 3, N = 3;
	int DEPTH = 3;
	static Game game;
	Label BlueScore, RedScore;
	Platno c;
	Thread t;
	ChoiceBox<String> choiceBox1, choiceBox2;
	Scene s,start; 
	Task<Void> labelUpdater1 , labelUpdater2;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setOnCloseRequest(e -> {
			e.consume();
			game.stop();
			primaryStage.close();
		});
		// game = new HumanVsAIGame(new Board(M, N), M, N);
		//game = new HumanVSMinimax(new Board(M, N), M, N, DEPTH);
		primaryStage.setTitle("Dots and Boxes");
		VBox setup = new VBox(50);
		
		//first row
		Label pl1 = new Label("Player 1: ");
		choiceBox1 = new ChoiceBox<String>();

		choiceBox1.getItems().add("Human");
		choiceBox1.getItems().add("AI_Random");
		choiceBox1.getItems().add("AI_Minimax");
		choiceBox1.setValue("Human");
		
		HBox row1 = new HBox(50);
		row1.getChildren().addAll(pl1, choiceBox1);
		row1.setAlignment(Pos.BASELINE_CENTER);
		//second row
		Label pl2 = new Label("Player 2: ");
		choiceBox2 = new ChoiceBox<String>();

		choiceBox2.getItems().add("Human");
		choiceBox2.getItems().add("AI_Random");
		choiceBox2.getItems().add("AI_Minimax");
		choiceBox2.setValue("AI_Minimax");
		
		HBox row2 = new HBox(50);
		row2.getChildren().addAll(pl2, choiceBox2);
		row2.setAlignment(Pos.BASELINE_CENTER);
		//numbers row
		Label vrste = new Label("Broj vrsta: ");
		TextField brojVrsta = new TextField();
		brojVrsta.setPrefWidth(50);
		brojVrsta.setText("3");
		
		Label kolone = new Label("Broj kolona: ");
		TextField brojKolona = new TextField();
		brojKolona.setPrefWidth(50);
		brojKolona.setText("3");
		
		Label labela = new Label("Dubina stabla: ");
		TextField dubina = new TextField();
		dubina.setPrefWidth(50);
		dubina.setText("3");
		
		HBox numbersRow = new HBox(50);
		numbersRow.getChildren().addAll(vrste, brojVrsta, kolone, brojKolona,  labela, dubina);
		numbersRow.setAlignment(Pos.BASELINE_CENTER);
		//checks row
		String str = "Ucitati tablu iz fajla? ";
		CheckBox fr = new CheckBox(str);
		CheckBox end = new CheckBox("Izvrsiti do kraja?");
		HBox checks = new HBox(50);
		checks.getChildren().addAll(fr, end);
		checks.setAlignment(Pos.BASELINE_CENTER);
		//button row
		
		Button btn1 = new Button("Start");
		//button action
		btn1.setOnAction(e->
		{
			String player1 = (String) choiceBox1.getValue();
			String player2 = choiceBox2.getValue();
			M =Integer.parseInt(brojVrsta.getText());
			N =Integer.parseInt(brojKolona.getText());
			DEPTH =Integer.parseInt(dubina.getText());
			boolean readFromFile = fr.isSelected(), toTheEnd = end.isSelected();
			
			game = createGame(player1, player2, readFromFile, toTheEnd);
			VBox l = new VBox();			
			BlueScore = new Label(Integer.toString(game.getPlayer1().getScore()));
			RedScore = new Label(Integer.toString(game.getPlayer2().getScore()));
			BlueScore.setStyle("-fx-text-fill: BLUE;" + "-fx-font-size:70px; -fx-font-weight:BOLD");
			RedScore.setStyle("-fx-text-fill: RED;" + "-fx-font-size:70px; -fx-font-weight:BOLD");

			Task<Void> labelUpdater1 = new Task<Void>() {

				@Override
				protected Void call() throws Exception {
					while (true) {
						updateMessage(Integer.toString(game.getPlayer1().getScore()));
						Thread.sleep(500);
					}

				}

			};
			Task<Void> labelUpdater2 = new Task<Void>() {

				@Override
				protected Void call() throws Exception {
					while (true) {
						updateMessage(Integer.toString(game.getPlayer2().getScore()));
						Thread.sleep(500);
					}

				}

			};

			BlueScore.textProperty().bind(labelUpdater1.messageProperty());
			RedScore.textProperty().bind(labelUpdater2.messageProperty());
			Label SemiColon = new Label(":");
			SemiColon.setStyle("-fx-text-fill: BLACK;" + "-fx-font-size:70px; -fx-font-weight:BOLD");
			HBox scorebox = new HBox(50, BlueScore, SemiColon, RedScore);
			scorebox.setStyle("-fx-background-color: LIGHTGREY;");
			scorebox.setAlignment(Pos.CENTER);
			scorebox.minHeight(200);
			
			
			

			// Niti
			Thread t2 = new Thread(labelUpdater1);
			t2.setName("Label Updater1");
			t2.setDaemon(true);
			t2.start();
			Thread t3 = new Thread(labelUpdater2);
			t3.setName("Label Updater2");
			t3.setDaemon(true);
			t3.start();
			t = new Thread(game);
			t.start();
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			c = game.getPlatno();
			l.getChildren().addAll(c, scorebox);
			s = new Scene(l, WIDTH, HEIGHT + 100);
			primaryStage.setScene(s);
			primaryStage.show();
		});
			
			
			
		
		HBox buttonRow = new HBox(50);
		buttonRow.getChildren().add(btn1);
		buttonRow.setAlignment(Pos.BASELINE_CENTER);
		
		//final setup scene
		setup.getChildren().addAll(row1, row2,numbersRow,checks, buttonRow);
		
		VBox.setMargin(row1, new Insets(50, 0, 0,0));
		
		start = new Scene(setup, WIDTH, HEIGHT + 100);
		primaryStage.setScene(start);
		primaryStage.show();
		
		
		
		
		
	}


	private Game createGame(String p1, String p2, boolean fromFile, boolean toTheEnd) {
		Player player1=null, player2=null;
		long time=500;
		if (p1 != "Human" && p2 !="Human" && toTheEnd) {
			time=0;
		}
		switch(p1) {
		case "Human":{
			player1 = new HumanPlayer(game, javafx.scene.paint.Color.BLUE,time);
			break;
		}
		case "AI_Random":{
			player1 = new Player_AI_Beginner(game, javafx.scene.paint.Color.BLUE,time);
			break;
		}
		case "AI_Minimax":{
			player1 = new PlayerAIMinimax(javafx.scene.paint.Color.BLUE,game, DEPTH,time);
			break;
		}
		default: break;
		}
		
		switch(p2) {
		case "Human":{
			player2 = new HumanPlayer(null, javafx.scene.paint.Color.RED, time);
			break;
		}
		case "AI_Random":{
			player2 = new Player_AI_Beginner(null, javafx.scene.paint.Color.RED, time);
			break;
		}
		case "AI_Minimax":{
			player2 = new PlayerAIMinimax(javafx.scene.paint.Color.RED,null, DEPTH, time);
			break;
		}
		default: break;
		}
		
		Board b = new Board(M, N);
		
		Game g= new Game(b, M,N,player1, player2, fromFile, time);
		
		g.getPlayer1().setGame(g);
		g.getPlayer2().setGame(g);
		return g;
	}
}
