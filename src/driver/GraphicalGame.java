package driver;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;

import board.Board;
import board.Player;
import cards.Card;
import cards.CardType;
import cards.EdibleItem;
import cards.Mushroom;

public class GraphicalGame extends Application{
    private static Player p1;
    private static Player p2;
    private static Boolean p1plays;
    private static Player currentPlayer;

    private static Text p1Score;
    private static Text p2Score;
    private static Text p1Sticks;
    private static Text p2Sticks;

    private static HBox gameBoard;
    private static HBox decayD;
    private static HBox fD;
    private static HBox p1HD;
    private static HBox p1DD;
    private static HBox p2HD;
    private static HBox p2DD;
    private static Text turn;
    private static VBox cardInfo;

    private static TextField mName_field;
    private static TextField mAmount_field;

    private static String mToSell_name;
    private static int mToSell_amount;

    private static int currentAction = 9;

    private static int imgW = 98 - 15;
    private static int imgH = 164 - 30;

    private static int widthW = 1500;
    private static int heightW = 900;
    private static int spacing = 2;

    private static ArrayList<Integer> forestSel = new ArrayList<>();
    private static ArrayList<Integer> sh = new ArrayList<>();


    @Override
    public void start(Stage stage) throws Exception {
        p1plays=true;
		
		Board.initialisePiles();
		Board.setUpCards();
		Board.getForestCardsPile().shufflePile();
		
		//Populate forest	
		for (int i=0; i<8;i++) {			
			Board.getForest().add(Board.getForestCardsPile().drawCard());		
		}
		//Initialise players and populate player hands
		p1  = new Player(); currentPlayer=p1; p2 = new Player();
		p1.addCardtoHand(Board.getForestCardsPile().drawCard());p1.addCardtoHand(Board.getForestCardsPile().drawCard());p1.addCardtoHand(Board.getForestCardsPile().drawCard());
		p2.addCardtoHand(Board.getForestCardsPile().drawCard());p2.addCardtoHand(Board.getForestCardsPile().drawCard());p2.addCardtoHand(Board.getForestCardsPile().drawCard());	
		
		//Display board
        displayBoard(stage);

    }

    public static void displayBoard(Stage stage){
        String [] actions = {"Take 1 card from forest",
                             "Take all cards from decay pile",
                             "Cook 3 or > identical mushrooms",
                             "Put down 1 pan"};
        turn = new Text("Select action P1!");
        turn.setFont(new Font(40));
        turn.setFill(Color.BLACK);

        HBox root = new HBox( 2 * spacing );
        root.setBackground(Background.EMPTY);
        root.setAlignment(Pos.CENTER);

        BackgroundImage myBI= new BackgroundImage(new Image("file:img/backgroundImg.jpg",1500,900,false,true),
        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
          BackgroundSize.DEFAULT);

        VBox right_panel = new VBox(0);
        right_panel.getStyleClass().add("color-palette");
        right_panel.setBackground(new Background(myBI));
        right_panel.setPrefWidth(widthW * 4/5 - spacing);
        right_panel.setAlignment(Pos.CENTER);

        gameBoard = new HBox();
        gameBoard.getStyleClass().add("color-palette");
        gameBoard.setBackground(new Background(new BackgroundFill(Color.BISQUE, CornerRadii.EMPTY, Insets.EMPTY)));
        gameBoard.setPrefWidth(widthW * 4/5);
        gameBoard.setAlignment(Pos.CENTER);

        decayD = new HBox();
        decayD.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
        decayD.setPrefWidth(widthW * 3/5);
        decayD.setAlignment(Pos.CENTER);

        p1HD = new HBox(spacing);
        p1HD.getStyleClass().add("color-palette");
        p1HD.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1);");
        p1HD.setPrefWidth(widthW * 4/5 - spacing);
        p1HD.setAlignment(Pos.CENTER);

        p1DD = new HBox(spacing);
        p1DD.getStyleClass().add("color-palette");
        p1DD.setStyle("-fx-background-color: rgba(250, 139, 55, 0.1);");
        p1DD.setPrefWidth(widthW * 4/5 - spacing);
        p1DD.setAlignment(Pos.CENTER);

        fD = new HBox();
        fD.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
        fD.setPrefWidth(widthW * 2/5);
        fD.setAlignment(Pos.CENTER);

        p2HD = new HBox(spacing);
        p2HD.getStyleClass().add("color-palette");
        p2HD.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1);");
        p2HD.setPrefWidth(widthW * 4/5 - spacing);
        p2HD.setAlignment(Pos.CENTER);


        p2DD = new HBox(spacing);
        p2DD.getStyleClass().add("color-palette");
        p2DD.setStyle("-fx-background-color: rgba(250, 139, 55, 0.1);");
        p2DD.setPrefWidth(widthW * 4/5 - spacing);
        p2DD.setAlignment(Pos.CENTER);

        gameBoard.getChildren().addAll(fD, decayD);
        right_panel.getChildren().addAll(p1HD, p1DD, gameBoard, p2DD, p2HD);

        VBox left_panel = new VBox(2 * spacing);
        left_panel.getStyleClass().add("color-palette");
        left_panel.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
        left_panel.setPrefWidth(widthW /5 - spacing);
        left_panel.setAlignment(Pos.CENTER);

        VBox pStats = new VBox();
        pStats.getStyleClass().add("color-palette");
        pStats.setBackground(new Background(new BackgroundFill(Color.WHEAT, CornerRadii.EMPTY, Insets.EMPTY)));
        pStats.setPrefWidth(widthW /5 - spacing);
        renderPlayerScore(pStats);

        VBox actionBox = renderActionButtons(actions);

        cardInfo = new VBox();
        cardInfo.getStyleClass().add("color-palette");
        cardInfo.setBackground(new Background(new BackgroundFill(Color.BISQUE, CornerRadii.EMPTY, Insets.EMPTY)));
        cardInfo.setPrefWidth(imgW);
        cardInfo.setPrefHeight(imgH + 80);
        cardInfo.setAlignment(Pos.CENTER);

        left_panel.getChildren().addAll(turn, pStats, actionBox, cardInfo);

        root.getChildren().addAll(left_panel, right_panel);

        //display images/cards P1
        renderPlayerHand(p1, p1HD, p1DD, 1);
        renderPlayerHand(p2, p2HD, p2DD, 2);

        //display images/cards forest
        renderForest(fD);
        renderDecayPile(decayD);

        Scene scene = new Scene(root, widthW, heightW, Color.BLACK);
        stage.setScene(scene);
        stage.setTitle("FUNGI GAME - by Popovici Radu-Stefan");

        stage.show();
    }

    public static void renderDecayPile(HBox dp){
        for(int i = 0; i < Board.getDecayPile().size(); i++){
            String path = "";
            String cardName = "";
            cardName = Board.getDecayPile().get(i).getName();
            if (Board.getDecayPile().get(i).getType() == CardType.NIGHTMUSHROOM){
                path = "file:img/" + cardName + "-modified.jpg";
            } else{
                path = "file:img/" + cardName + ".jpg";
            }
            Image image = new Image(path, imgW, imgH, true, false);
            ImageView imageView = new ImageView(image);
            StackPane cardImg = new StackPane(imageView);
            
            cardImg.setStyle("-fx-border-style: solid inside;"
                        + "-fx-border-width: 2;" +
                        "-fx-border-color: black;");
            dp.getChildren().add(cardImg);

        }
    }

    public static void renderPlayerHand(Player p, HBox hBox, HBox dBox, int n){
        for(int i = 0; i < p.getHand().size(); i++){
            int index = i;
            String path = "";
            String cardName = p.getHand().getElementAt(i).getName();
            if (p.getHand().getElementAt(i).getType() == CardType.NIGHTMUSHROOM){
                path = "file:img/" + cardName + "-modified.jpg";
            } else{
                path = "file:img/" + cardName + ".jpg";
            }
            Image image = new Image(path, imgW, imgH, true, false);
            ImageView imageView = new ImageView(image);
            StackPane cardImg = new StackPane(imageView);

            Image imageInfo = new Image(path, imgW, imgH, true, false);
            ImageView imageViewInfo = new ImageView(imageInfo);
            StackPane cardImgInfo = new StackPane(imageViewInfo);

            String stickValueInfo = "";
            String flavValueInfo = "";
            if (p.getHand().getElementAt(i).getType() == CardType.PAN ||
            p.getHand().getElementAt(i).getType() == CardType.BASKET){
                stickValueInfo = "0";
                flavValueInfo = "0";
            } else if (p.getHand().getElementAt(i).getType() == CardType.NIGHTMUSHROOM ||
                p.getHand().getElementAt(i).getType() == CardType.DAYMUSHROOM){
                stickValueInfo = ((Mushroom) p.getHand().getElementAt(i)).getSticksPerMushroom() + "";
                flavValueInfo = ((Mushroom) p.getHand().getElementAt(i)).getFlavourPoints() + "";
            } else if (p.getHand().getElementAt(i).getType() == CardType.CIDER ||
                        p.getHand().getElementAt(i).getType() == CardType.BUTTER){
                stickValueInfo = "0";
                flavValueInfo = ((EdibleItem) p.getHand().getElementAt(i)).getFlavourPoints() + "";
            }

            String stickValueInfoFinal = stickValueInfo;
            String flavValueInfoFinal = flavValueInfo;
            
            cardImg.setStyle("-fx-border-style: solid inside;"
                        + "-fx-border-width: 2;" +
                        "-fx-border-color: black;");
            hBox.getChildren().add(cardImg);

            cardImg.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                Boolean selected = false;
                Boolean lastSelected = false;
                Text infoStick = new Text("Stick value: " + stickValueInfoFinal);
                Text infoFlav = new Text("Flav value: " + flavValueInfoFinal);

                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (p1plays == true && n != 1 ||
                        p1plays == false && n != 2){
                        return;
                    }

                    if (selected == false){
                        selected = true;
                        cardImg.setStyle("-fx-border-style: solid inside;"
                        + "-fx-border-width: 2;" +
                        "-fx-border-color: red;");
                        sh.add(index);
                    } else {
                        selected = false;
                        cardImg.setStyle("-fx-border-style: solid inside;"
                        + "-fx-border-width: 2;" +
                        "-fx-border-color: black;");
                        sh.remove(Integer.valueOf(index));
                    }

                    if (lastSelected == false ){
                        cardInfo.getChildren().clear();
                        lastSelected = true;
                        cardInfo.getChildren().addAll(cardImgInfo, infoStick, infoFlav);
                    } else{
                        lastSelected = false;
                        cardInfo.getChildren().clear();
                    }
                }
            });
        }

        ArrayList<Integer> sd = new ArrayList<>();
        for(int i = 0; i < p.getDisplay().size(); i++){
            int index = i;
            String path = "";
            String cardName = p.getDisplay().getElementAt(i).getName();
            path = "file:img/" + cardName + ".jpg";
            Image image = new Image(path, imgW, imgH, true, false);
            ImageView imageView = new ImageView(image);
            StackPane cardImg = new StackPane(imageView);

            cardImg.setStyle("-fx-border-style: solid inside;"
                        + "-fx-border-width: 2;" +
                        "-fx-border-color: black;");
            dBox.getChildren().add(cardImg);

            cardImg.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                Boolean selected = false;
                
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (p1plays == true && n != 1 ||
                        p1plays == false && n != 2){
                        return;
                    }

                    if (selected == false){
                        selected = true;
                        cardImg.setStyle("-fx-border-style: solid inside;"
                        + "-fx-border-width: 2;" +
                        "-fx-border-color: orange;");
                        sd.add(index);
                    } else {
                        selected = false;
                        cardImg.setStyle("-fx-border-style: solid inside;"
                        + "-fx-border-width: 2;" +
                        "-fx-border-color: black;");
                        sd.remove(Integer.valueOf(index));
                    }
                }
            });
        }
    }

    public static void renderForest(HBox fD){
        for(int i = 0; i < Board.getForest().size(); i++){
            int index = i;
            String path = "";
            String cardName = "";
            cardName = Board.getForest().getElementAt(i).getName();
            if (Board.getForest().getElementAt(i).getType() == CardType.NIGHTMUSHROOM){
                path = "file:img/" + cardName + "-modified.jpg";
            } else{
                path = "file:img/" + cardName + ".jpg";
            }
            Image image = new Image(path, imgW, imgH, true, false);
            ImageView imageView = new ImageView(image);
            StackPane cardImg = new StackPane(imageView);

            Image imageInfo = new Image(path, imgW, imgH, true, false);
            ImageView imageViewInfo = new ImageView(imageInfo);
            StackPane cardImgInfo = new StackPane(imageViewInfo);

            String stickValueInfo = "";
            String flavValueInfo = "";
            if (Board.getForest().getElementAt(i).getType() == CardType.NIGHTMUSHROOM ||
                Board.getForest().getElementAt(i).getType() == CardType.DAYMUSHROOM){
                stickValueInfo = ((Mushroom) Board.getForest().getElementAt(i)).getSticksPerMushroom() + "";
                flavValueInfo = ((Mushroom) Board.getForest().getElementAt(i)).getFlavourPoints() + "";
            } else if (Board.getForest().getElementAt(i).getType() == CardType.CIDER ||
            Board.getForest().getElementAt(i).getType() == CardType.BUTTER){
            stickValueInfo = "0";
            flavValueInfo = ((EdibleItem) Board.getForest().getElementAt(i)).getFlavourPoints() + "";
            } else if (Board.getForest().getElementAt(i).getType() == CardType.PAN ||
                Board.getForest().getElementAt(i).getType() == CardType.BASKET){
                stickValueInfo = "0";
                flavValueInfo = "0";
                }
            String stickValueInfoFinal = stickValueInfo;
            String flavValueInfoFinal = flavValueInfo;
            
            cardImg.setStyle("-fx-border-style: solid inside;"
                        + "-fx-border-width: 2;" +
                        "-fx-border-color: black;");
            fD.getChildren().add(cardImg);

            cardImg.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                Boolean selected = false;
                Boolean lastSelected = false;
                Text infoStick = new Text("Stick value: " + stickValueInfoFinal);
                Text infoFlav = new Text("Flav value: " + flavValueInfoFinal);

                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (selected == false){
                        if(forestSel.size() == 1 && currentAction == 0 || currentAction > 5 || currentAction == 1){
                            return;
                        }
                        selected = true;
                        cardImg.setStyle("-fx-border-style: solid inside;"
                        + "-fx-border-width: 2;" +
                        "-fx-border-color: orange;");
                        forestSel.add(index);
                    } else {
                        selected = false;
                        cardImg.setStyle("-fx-border-style: solid inside;"
                        + "-fx-border-width: 2;" +
                        "-fx-border-color: black;");
                        forestSel.remove(Integer.valueOf(index));
                    }
                    if (lastSelected == false ){
                        cardInfo.getChildren().clear();
                        lastSelected = true;
                        cardInfo.getChildren().addAll(cardImgInfo, infoStick, infoFlav);
                    } else{
                        lastSelected = false;
                        cardInfo.getChildren().clear();
                    }
                }
            });
        }
    }

    public static void renderPlayerScore(VBox box){
        p1Score = new Text("Player 1 Score: " + Integer.toString(p1.getScore()));
        p1Score.setFont(new Font(30));
        p1Score.setTextAlignment(TextAlignment.JUSTIFY);
        p1Sticks = new Text("Player 1 Sticks: " + Integer.toString(p1.getStickNumber()));
        p1Sticks.setFont(new Font(30));
        p1Sticks.setTextAlignment(TextAlignment.JUSTIFY);

        p2Score = new Text("Player 2 Score: " + Integer.toString(p1.getScore()));
        p2Score.setFont(new Font(30));
        p2Score.setTextAlignment(TextAlignment.JUSTIFY);
        p2Sticks = new Text("Player 2 Sticks: " + Integer.toString(p1.getStickNumber()));
        p2Sticks.setFont(new Font(30));
        p2Sticks.setTextAlignment(TextAlignment.JUSTIFY);
        box.getChildren().addAll(p1Score, p1Sticks, p2Score, p2Sticks);
    }

    public static VBox renderActionButtons(String[] actions){
        VBox actionBox = new VBox();
        actionBox.getStyleClass().add("color-palette");
        actionBox.setBackground(new Background(new BackgroundFill(Color.WHEAT, CornerRadii.EMPTY, Insets.EMPTY)));
        actionBox.setPrefWidth(widthW /5 - spacing);
        actionBox.setAlignment(Pos.CENTER);

        for (int i=0; i < actions.length; i++){
            int index = i;
            String actionName = actions[i];
            Button button = new Button(actions[i]);
            button.setStyle("-fx-padding: 8 15 15 15;" +
            "-fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;" +
            "-fx-background-radius: 8;" +
            "-fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%), #9d4024, #d86e3a, radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c);" +
            "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 1.1em;");
            actionBox.getChildren().add(button);

            button.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent mouseEvent) {
                    currentAction = index;
                    performAction(index);
                }
            });
        }

        Text sellM_txt = new Text("Sell 2 or > identical mushrooms");
        HBox sellMBox = new HBox();
        mName_field = new TextField();
        mName_field.setPromptText("Name");
        mAmount_field = new TextField();
        mAmount_field.setPromptText("Amount");
        Button submitSellM = new Button("GO");
        submitSellM.setStyle("-fx-padding: 4 7 7 7;" +
            "-fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;" +
            "-fx-background-radius: 8;" +
            "-fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%), #9d4024, #d86e3a, radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c);" +
            "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 0.9em;");
        submitSellM.setMinWidth(60);
        sellMBox.getChildren().addAll(mName_field, mAmount_field, submitSellM);
        actionBox.getChildren().addAll(sellM_txt, sellMBox);

        submitSellM.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mName_field.getText().isEmpty() || mAmount_field.getText().isEmpty()){
                    turn.setText("This is not the appropriate format.");
                    turn.setFont(new Font(20));
                    turn.setFill(Color.RED);
                    return;
                }
                mToSell_name = mName_field.getText();
                try{
                    mToSell_amount = Integer.parseInt(mAmount_field.getText());
                } catch(NumberFormatException e) {
                    mToSell_amount = 1000;
                    turn.setText("This is not the appropriate format.");
                    turn.setFont(new Font(20));
                    turn.setFill(Color.RED);
                }
                currentAction = 4;
                performAction(4);
            }
        });

        return actionBox;
    }

    public static void refreshWindow(){
        fD.getChildren().clear();
        p1HD.getChildren().clear();
        p1DD.getChildren().clear();
        p2HD.getChildren().clear();
        p2DD.getChildren().clear();
        decayD.getChildren().clear();

        renderForest(fD);
        renderDecayPile(decayD);
        renderPlayerHand(p1, p1HD, p1DD, 1);
        renderPlayerHand(p2, p2HD, p2DD, 2);
    }

    public static void performAction(int currentAction){
        try{
            if (Board.getForest().size()>0){
                boolean succesfullMove = false;
                switch(currentAction) {
                    case 0:
                        if (forestSel.size() == 1){
                            if (currentPlayer.takeCardFromTheForest(Board.getForest().size() - forestSel.get(0))) {
                                if (Board.getForestCardsPile().pileSize()>0) {
                                    Board.getForest().add(Board.getForestCardsPile().drawCard());
                                }
                                succesfullMove=true;
                                forestSel.clear();
                            }
                        }
                        break;
                    case 1:
                        if (currentPlayer.takeFromDecay()) {
                            succesfullMove=true;	
                        }
                        break;
                    case 2:
                        ArrayList<Card> cookingmushrooms = new ArrayList<Card>();
                        for (int k=0;k<sh.size();k++) {
                            cookingmushrooms.add(currentPlayer.getHand().getElementAt(sh.get(k)));
                        }
                        if (currentPlayer.cookMushrooms(cookingmushrooms)) {
                            succesfullMove=true;
                            sh.clear();
                        }
                        break;
                    case 3:
                        if (currentPlayer.putPanDown()) {
                            succesfullMove=true;
                        }
                        break;
                    case 4:
                        if (currentPlayer.sellMushrooms(mToSell_name, mToSell_amount)) {
                            succesfullMove=true;
                            mAmount_field.clear();
                            mName_field.clear();
                        }
                        break;
                    default:
                        break;
                }

                if (succesfullMove) {
                    if (Board.getForest().size()>0) {
                        Board.updateDecayPile();
                    }
                    if (Board.getForestCardsPile().pileSize()>0) {
                        Board.getForest().add(Board.getForestCardsPile().drawCard());
                    }
                    refreshWindow();
                    updatePlayerScore();
                    p1plays=!p1plays;
                    if (p1plays)
                        currentPlayer=p1;
                    else
                        currentPlayer=p2;
                }

                if(p1plays == true){
                    turn.setText("It is P1's turn");
                    turn.setFont(new Font(40));
                    turn.setFill(Color.BLACK);
                }else{
                    turn.setText("It is P2's turn");
                    turn.setFont(new Font(40));
                    turn.setFill(Color.BLACK);
                }
            }
        }catch(NumberFormatException e) {
            turn.setText("This is not the appropriate format.");
            turn.setFont(new Font(20));
            turn.setFill(Color.RED);
        }

        if (Board.getForest().size() <= 0){
            if (p1.getScore()>p2.getScore()) {
                turn.setText("Player 1 wins");
                turn.setFill(Color.RED);
            }
            else if (p2.getScore()>p1.getScore()) {
                turn.setText("Player 2 wins");
                turn.setFill(Color.RED);
            }
            else {
                turn.setText("There was a tie");
                turn.setFill(Color.RED);
            }
        }
    }

    public static void updatePlayerScore(){
        p1Score.setText("Player 1 Score: " + Integer.toString(p1.getScore()));
        p1Sticks.setText("Player 1 Sticks: " + Integer.toString(p1.getStickNumber()));
        p2Score.setText("Player 2 Score: " + Integer.toString(p2.getScore()));
        p2Sticks.setText("Player 2 Sticks: " + Integer.toString(p2.getStickNumber()));
    }

    public static void main(String[] args){
        launch(args);
    }
}
