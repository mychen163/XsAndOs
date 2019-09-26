
/*
 * ************Design********************
 * 1. one inner class was used to represent each  cell ;
 * 2. Combine all of the Cell objects to a GridPane(3x3);
 * 3. Starting from 'X' player, click the mouse then draw an 'X' and set the cell token to 'X'.
 *    Then switch the turn to 'O' player.
 *    After a click of mouse, draw an 'O' and set the cell token to 'O. Then switch the turn to 'X' player.
 *    Do these two steps alternatively.
 * 4. After each click, check if there is one row , or one column ,or a diagonal line has the same token.
 *    If so, then the current player wins and game over.
 * 5. If there is no one wins, but the grid is full, it is a draw.
 *
 */
import javafx.application.Application;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Ellipse;

public class XsAndOs extends Application {

    private boolean gameOver = false;  //flag
    private char whoseTurn = 'X'; // 'X' or 'O' but 'X' starts
    private Cell[][] board =  new Cell[3][3];  //the board for playing
    private Label statusLabel = new Label("X's turn to play");  //let user know status of game

    @Override
    public void start(Stage primaryStage) {
        GridPane pane = new GridPane();
        //Use nested loop to create a Cell object for each location (row/column) in the board
        //use pane.add(object, column, row) to add that Cell object to the GridPane
        //each Cell object is accessed by row and column

        for(int row=0;row<3;row++)
            for (int col=0;col<3;col++) {
                board[row][col] = new Cell(); //create each new Cell object
                pane.add(board[row][col], col, row); // add the Cell object to the GridPane
            }

        BorderPane borderPane = new BorderPane(); //Pane Border
        borderPane.setCenter(pane);
        borderPane.setBottom(statusLabel); //set a label on the bottom

        Scene scene = new Scene(borderPane, 300, 300); //create scene object
        primaryStage.setTitle("XsAndOs"); //scene tile
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //check the board to see whether it  is full
    public boolean isFull() {
        for(int row=0;row<3;row++){
            for (int col=0;col<3;col++){
                //if there is a blank cell, the board is not full
                if (board[row][col].getToken()==' ')
                {
                    return false;
                }
            }
        }
        return true;  //otherwise the board is full.
    }

    //Check the board to see if there is a winner
    public boolean hasWon(char tkn) {

        //check every row to see if there is one row has the same token
        for(int row=0;row<3;row++) {
            if (board[row][0].getToken()==tkn && board[row][1].getToken()==tkn && board[row][2].getToken()==tkn)
                return true;
        }

        //check every column to see if there is one column has the same token
        for (int col=0;col<3;col++) {
            if (board[0][col].getToken() == tkn && board[1][col].getToken() == tkn && board[2][col].getToken() == tkn)
                return true;}

        // check to see whether there is a diagonal row has the same token
        if (board[0][0].getToken()==tkn &&board[1][1].getToken()==tkn&& board[2][2].getToken()==tkn||
                board[0][2].getToken()==tkn &&board[1][1].getToken()==tkn&& board[2][0].getToken()==tkn
        ){
            return true;
        }

        return false; //otherwise no win
    }

    //HERE IS INNER CLASS REPRESENTING ONE CELL IN BOARD
    //The inner class has access to all of the outer classes data/methods
    public class Cell extends Pane {

        private char token = ' ';   // one of blank, X, or O

        public Cell() {
            setStyle("-fx-border-color: black");   //cell border style
            setPrefSize(100, 100); // cell size
            setOnMouseClicked(e -> handleMouseClick());  // click mouse
        }

        public char getToken() {
            return token;  //return the value of token
        }

        public void drawX() {
            double w = getWidth();
            double h = getHeight();
            //Create two crossing lines for 'X'
            Line line1= new Line(0,0,w,h);
            Line line2= new Line(0,h,w,0);
            line1.setStroke(Color.BLUE);
            line2.setStroke(Color.BLUE);
            getChildren().addAll(line1, line2);
        }

        public void drawO() {
            double w = getWidth();
            double h = getHeight();
            //draw an 'O' on the cell
            Ellipse ellipse =new Ellipse(0.5*w,0.5*h,0.5*w,0.5*h);
            ellipse.setFill(null);
            ellipse.setStroke(Color.RED);
            getChildren().add(ellipse);
        }

        public void setToken(char c) {
            //Draw an X or O based on value of argument c
            if (c=='X')
                this.drawX();
            if (c=='O')
                this.drawO();
            //Update token value to argument c
            token=c;
        }

        private void handleMouseClick() {
            String s = "";
            if (!gameOver) {

                // if the token is not blank, it can not be clicked again
                if (this.token !=' ' ) {
                    s= "Click again.This cell has been taken."; //reminder the user to click again
                    statusLabel.setText(s);  //display at the bottom
                    return;
                }


                //if it is 'X' turn, draw X and update the token value to 'X', and change turn to 'O'
                if (whoseTurn == 'X'){
                    this.setToken('X');
                    whoseTurn = 'O';
                    s="O's turn to play";  //change the label message
                }

                //if it is 'O' turn, draw 'O' and update the token value to 'O', and change turn to 'X'
                else {
                    this.setToken('O');
                    whoseTurn = 'X';
                    s="X's turn to play";//change the label message
                }

                // check whether the current player wins
                if (hasWon(this.getToken())){
                    gameOver = true;  // if winning , then game over
                    s = token + " won! The game is over."; //and display the result on the bottom label
                }
                //check whether it is full
                else if (isFull()){
                    gameOver = true;  //if so, game over
                    s = "It's a draw. The game is over.";  //the result of draw
                }
                statusLabel.setText(s);}  //display the results on the bottom label

            // if the game is over, you can not click any more
            else return;

        }
    }

    public static void main(String[] args) {
        launch(args);
    } //main
}



