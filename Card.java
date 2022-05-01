package application;


import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Card  extends Parent{
	private static final int CARD_WIDTH = 100;
    private static final int CARD_HEIGHT = 140;

    enum Suit {
    	heart, diamond, club, spade;
    	
    	final Image image;
    	
    	Suit(){
    		
    		this.image = new Image( ((name()).concat(".png")),85,85,true,false);
    		;
    		

    	}
    	
    }
    

    enum Rank {
        two(2), three(3), four(4), five(5), six(6), seven(7), eight(8), nine(9), ten(10),
        Jack(10), Queen(10), King(10), Ace(11);

        final int value;
        Rank(int value) {
            this.value = value;
            
        }

        String displayName() {
            return ordinal() < 9 ? String.valueOf(value) : name().substring(0, 1);
        }
    }

    public final Suit suit;
    public final Rank rank;
    public final int value;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
        this.value = rank.value;

       
   
      
        
        Font fontC= Font.font("Courier New", FontWeight.BOLD, 20);
        


        
        Rectangle cardShape = new Rectangle(CARD_WIDTH, CARD_HEIGHT);
        cardShape.setArcWidth(10);
        cardShape.setArcHeight(10);
        cardShape.setFill(Color.WHITE);

        Text rankTop = new Text(rank.displayName());
        rankTop.setFont(fontC);
        rankTop.setX(CARD_WIDTH - rankTop.getLayoutBounds().getWidth() - 10);
        rankTop.setY(rankTop.getLayoutBounds().getHeight());

        Text rankBottom = new Text(rankTop.getText());
        rankBottom.setFont(fontC);
        rankBottom.setX(10);
        rankBottom.setY(CARD_HEIGHT - 10);
	        	
        ImageView cardv= new ImageView(suit.image);
	    cardv.setRotate(180);
	    cardv.setX(CARD_WIDTH - 50);
	    cardv.setY(CARD_HEIGHT - 100);

        getChildren().addAll(cardShape,new ImageView(suit.image), rankTop, rankBottom);
    }

    @Override
    public String toString() {
        return rank.toString() + " of " + suit.toString();
    }
}
