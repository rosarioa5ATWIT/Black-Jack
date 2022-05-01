package application;
	
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.Parent;


public class Main extends Application {
	private Deck deck = new Deck();
    private Hand house, player;
    private Text message = new Text();

    private SimpleBooleanProperty playable = new SimpleBooleanProperty(false);

    private HBox houseCards = new HBox(20);
    private HBox playerCards = new HBox(20);

    private Parent createContent() {
        house = new Hand(houseCards.getChildren());
        player = new Hand(playerCards.getChildren());

        Pane root = new Pane();
        root.setPrefSize(600, 500);
        
  
        Rectangle greenRec = new Rectangle(600, 390);
        greenRec.setLayoutX(0);
        greenRec.setLayoutY(0);
        greenRec.setFill(Color.DARKOLIVEGREEN);
        Rectangle blueRec = new Rectangle(600, 100);
        blueRec.setLayoutX(0);
        blueRec.setLayoutY(390);
        blueRec.setFill(Color.DARKBLUE);

        
        Font font = Font.font("Courier New", FontWeight.BOLD, 20);
        Font fontT=Font.font("Times New Roman", 20);

        VBox leftVBox = new VBox(20);
        leftVBox.setAlignment(Pos.CENTER);
        leftVBox.setPadding(new Insets(35,10,10,10));

        Text dealerScore = new Text("House: ");
        dealerScore.setLayoutX(0);
        dealerScore.setLayoutY(30);
        dealerScore.setFont(fontT);
        Text playerScore = new Text("Player: ");
        playerScore.setLayoutX(0);
        playerScore.setLayoutY(200);
        playerScore.setFont(fontT);
        
        
        leftVBox.getChildren().addAll(houseCards, message, playerCards);


       

        Button Play = new Button("New game");
        Play.setLayoutX(10);
        Play.setLayoutY(400);
        Play.setFont(font);
        
        Button Hit = new Button("HIT");
        Hit.setLayoutX(300);
        Hit.setLayoutY(400);
        Hit.setFont(font);

        Button Stand = new Button("STAND");
        Stand.setLayoutX(400);
        Stand.setLayoutY(400);
        Stand.setFont(font);


        



        
        root.getChildren().addAll(greenRec,blueRec,leftVBox,Play,Hit,playerScore, dealerScore,Stand);


        Play.disableProperty().bind(playable);
        Hit.disableProperty().bind(playable.not());
        Stand.disableProperty().bind(playable.not());

        playerScore.textProperty().bind(new SimpleStringProperty("Player: ").concat(player.valueProperty().asString()));
        dealerScore.textProperty().bind(new SimpleStringProperty("Dealer: ").concat(house.valueProperty().asString()));

        player.valueProperty().addListener((obs, old, newValue) -> {
            if (newValue.intValue() >= 21) {
                endGame();
            }
        });

        house.valueProperty().addListener((obs, old, newValue) -> {
            if (newValue.intValue() >= 21) {
                endGame();
            }
        });

        // INIT BUTTONS

        Play.setOnAction(event -> {
            startNewGame();
        });

        Hit.setOnAction(event -> {
            player.takeCard(deck.drawCard());
        });

        Stand.setOnAction(event -> {
            while (house.valueProperty().get() < 17) {
                house.takeCard(deck.drawCard());
            }

            endGame();
        });

        return root;
    }

    private void startNewGame() {
        
    	playable.set(true);
        message.setText("");



        deck.refill();

        house.reset();
        player.reset();

        house.takeCard(deck.drawCard());
        house.takeCard(deck.drawCard());
        player.takeCard(deck.drawCard());
        player.takeCard(deck.drawCard());
    }

    private void endGame() {
        playable.set(false);

        int houseValue = house.valueProperty().get();
        int playerValue = player.valueProperty().get();
        String winner = "Exceptional case: d: " + houseValue + " p: " + playerValue;


        if (houseValue == 21 || playerValue > 21 || houseValue == playerValue
                || (houseValue < 21 && houseValue > playerValue)) {
            winner = "House";
        }
        else if (playerValue == 21 || houseValue > 21 || playerValue > houseValue) {
            winner = "Player";
        }

        message.setText(winner + " won!");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    	//background
    	BackgroundFill background_fill = new BackgroundFill(Color.DARKOLIVEGREEN, CornerRadii.EMPTY, Insets.EMPTY);
		Background background = new Background(background_fill);
		//
		
		//fonts
		Font font = Font.font("Courier New", FontWeight.BOLD, 36);
		//
		Pane root = new Pane();
		root.setBackground(background);

		Image PLAYINGC = new Image("playing cards.png");
		ImageView PLAYINGCC = new ImageView(PLAYINGC);
		PLAYINGCC.setFitHeight(400); 
		PLAYINGCC.setFitWidth(400);
		PLAYINGCC.setLayoutX(30);
		PLAYINGCC.setLayoutY(50);
		root.getChildren().add(PLAYINGCC);
		
		Image image = new Image("playbutton.png");
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(50); 
		imageView.setFitWidth(50);
		
		Button btn = new Button("Play",imageView);
		btn.setFont(font);
		btn.setLayoutX(350);
		btn.setLayoutY(250);
		root.getChildren().add(btn);
		Text t = new Text (100, 200, "Black Jack");
		t.setFont(Font.font ("Times New Roman ",FontWeight.BOLD, 40));
		t.setFill(Color.RED);
		root.getChildren().add(t);
		
		Scene start = new Scene(root, 600, 500);
		//

		primaryStage.setScene(start);
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
		public void handle(ActionEvent event) {
				primaryStage.setScene(new Scene(createContent()));

			}
			});
		
		primaryStage.setWidth(600);
		primaryStage.setHeight(500);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Black Jack");
		
    	
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
