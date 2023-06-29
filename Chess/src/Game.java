import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import javazoom.jl.player.Player;

public class Game extends JPanel implements MouseListener, ActionListener, KeyListener {
	
	
		// GAME VARIABLES
		//FRAME DIMENSIONS
		static int frameSize =600;
		//TIMER CONTROL VARIABLES
	    int framesPerSecond =10;
		Timer t = new Timer(1000/framesPerSecond,this);
		//CHESS BOARD VARIABLES
		int chessBoardSize=400;
		int chessBoardPadding=100;
		Image chessBoardImage= null;
		String[] chessBoardImageNames;
		int currentChessBoardImageIndex=0;
		int chessBoardSquareSize=chessBoardSize/8;
		//PIECE VARIABLES
		Image piecesImage= null;
		String[] pieceImageNames = null;
		int currentPieceImageIndex=0;
		int piecePadding=chessBoardPadding+5;
		int pieceSize=40;
		String piecePositionKey;
		static Piece[] piece = new Piece[32];
		//HIGHLIGHTED PIECE
		Piece highlightedPiece;
		String highlight;
		//FONT
		Font font = new Font("Segoe Print", Font.BOLD,20);
		//HIGHLIGHTER VARIABLES
		int highlighterPadding = piecePadding+10;
		int highlighterSize = 20;
        Color highlighterMovableColor = new Color(0,255,0,128);
        //MOVABLE LOCATIONS
        ArrayList <MovableLocation> movableLocationList = new ArrayList<MovableLocation>();
        //INGAMEWALLPAPER VARIABLE
        Image inGameWallpaperImage =null;
         //BACKGROUND MUSIC THREAD
        static Thread backgroundMusicThread;
        static boolean loopBackgroundMusic =true;
        static Thread playSoundThread;
        //MUSIC PLAYER VARIABLES
    	static Player playMP3;
    	static FileInputStream fis;
    	static FileInputStream playSoundFis;
    	static Player playSoundMP3;
    	//GAMETIMER VARIABLES
    	int framesElapsed=0;
    	int secondsElapsed=0;
    	int minutesElapsed=0;
    	int playerOneFramesElapsed  = 0;
    	int playerOneSecondsElapsed = 0;
    	int playerOneMinutesElapsed = 0;
    	int playerTwoFramesElapsed  = 0;
    	int playerTwoSecondsElapsed = 0;
    	int playerTwoMinutesElapsed = 0;
    	//SCROLL IMAGE
    	Image scrollImage = null;
    	//SWORDS IMAGE
    	Image activePlayerImage = null;
    	//LOGIC BOARD
    	int logicBoard[][]= new int[8][8];
    	
    	
    	//TURN
    	boolean playerOneTurn=true;

	// CLASS CONSTRUCTOR
	public Game() {
		
		//SETING INITIIAL VALUES FOR LOGIC BOARD
			for(int i =0; i<8; i++)
			{
				for(int j=0; j <8; j++)
				{
					logicBoard[i][j]=33;
				}
			}
		//INITIALIZING GAME BOARD TO DEFAULT VALUES
			//INITIALZING UPPER PAWN VALUES ON LOGIC BOARD
			for(int i=0; i <8; i++ )
			{
				logicBoard[i][1]=i;
			}
			//INITIALZING  LOWER PAWN VALUES ON LOGIC BOARD
			for(int i=0; i<8; i++)
			{
				logicBoard[i][6]=16+i;
			}
			//INITIALIZING HIGHER ROOKS
			logicBoard[0][0]=8;
			logicBoard[7][0]=9;
			//INITIALIZING THE LOWER ROOKS
			logicBoard[0][7]=24;
			logicBoard[7][7]=25;
			//INITIALIZING UPPER HORSES
			logicBoard[1][0]=10;
			logicBoard[6][0]=11;
			//INITIALIZING LOWER HORSES
			logicBoard[1][7]=26;
			logicBoard[6][7]=27;
			//INITALIZING UPPER BISHOPS
			logicBoard[2][0]= 12;
			logicBoard[5][0]=13;
			//INITIALIZING LOWER BISHOPS
			logicBoard[2][7]=28;
			logicBoard[5][7]=29;
			//INITIALIZING THE UPPER QUEEN
			logicBoard[3][0]=14;
			//INTIALIZING THE LOWER QUEEN
			logicBoard[3][7]=30;
			//INITIALIZING THE UPPER KING
			logicBoard[4][0]=15;
			//INITIALIZING THE LOWER KING
			logicBoard[4][7]=31;
		    printLogicBoard();
		
		
		//Initializing the black pieces
	    //Initializing the black pawn pieces
		for(int i =0; i <8; i++)
		{
			piece[i]= new Piece("Pawn", i, 1, "Black",5,i,true);
		}
	    //Initializing the black rook pieces
		piece[8]= new Piece("Rook", 0, 0, "Black",2,8,true);
		piece[9]= new Piece("Rook", 7, 0, "Black",2,9,true);
		//Initializing the black horse pieces
		piece[10]= new Piece("Horse", 1, 0, "Black",3,10,true);
		piece[11]= new Piece("Horse", 6, 0, "Black",3,11,true);
	    //Initializing the black bishop pieces
		piece[12]= new Piece("Bishop", 2, 0, "Black",4,12,true);
		piece[13]= new Piece("Bishop", 5, 0, "Black",4,13,true);
		//Initializing the black Queen piece
		piece[14]= new Piece("Queen", 3, 0, "Black",1,14,true);
		//Initializing the black King piece
		piece[15]= new Piece("King", 4, 0, "Black",0,15,true);
		
		//Initializing the white pieces
	    //Initializing the white pawn pieces
		for(int i =16; i <24; i++)
		{
			piece[i]= new Piece("Pawn", i-16, 6, "White",5,i,true);
		}
	    //Initializing the white rook pieces
		piece[24]= new Piece("Rook", 0, 7, "White",2,24,true);
		piece[25]= new Piece("Rook", 7, 7, "White",2,25,true);
		//Initializing the black horse pieces
		piece[26]= new Piece("Horse", 1, 7, "White",3,26,true);
		piece[27]= new Piece("Horse", 6, 7, "White",3,27,true);
	    //Initializing the black bishop pieces
		piece[28]= new Piece("Bishop", 2, 7, "White",4,28,true);
		piece[29]= new Piece("Bishop", 5, 7, "White",4,29,true);
		//Initializing the black Queen piece
		piece[30]= new Piece("Queen", 3, 7, "White",1,30,true);
		//Initializing the black King piece
		piece[31]= new Piece("King", 4, 7, "White",0,31,true);
		
		//RETRIEVING CHESS BOARD IMAGE NAMES FROM FOLDER
		chessBoardImageNames = getChessBoardImageNames();
		
		//RETRIEVING PIECE IMAGES NAMES FROM FOLDER
		pieceImageNames = getPieceImageNames();
		
		//INITIALIZING IMAGES
	    initializeImages();
	    
	    //ADDING THE MOUSELISTENER
        addMouseListener(this);
        
        //ADDING THE KEY LISTENER
        addKeyListener(this);
        setFocusable(true);
  	    setFocusTraversalKeysEnabled(false);
        
        repaint();
        t.start();
	}

	// PAINT METHOD
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		//CONTROLLING THE TIMER VARIABLES
		framesElapsed++;
		if(framesElapsed==framesPerSecond)
		{
			framesElapsed=0;
			secondsElapsed++;
			if(secondsElapsed==60)
			{
				secondsElapsed=0;
				minutesElapsed++;
			}
		}
		
		//DRAWING THE INGAME WALLPAPER
		g2.drawImage(inGameWallpaperImage, 0, 0, frameSize, frameSize, null);
		
		//DRAWING THE CHESSBOARD AND ASSIGNING PIECE IMAGE VARIABLES
		int renderedPiecesImageHeight=piecesImage.getHeight(null);
		int renderedPiecesImageWidth= piecesImage.getWidth(null);
		g2.drawImage(chessBoardImage, 100, 100, 400, 400, null);
		
		
		//DRAWING THE SCROLLS
		g2.drawImage(scrollImage, 50, 25, 500, 75, null);
		g2.drawImage(scrollImage, 50, 500, 500, 75, null);
		
		//HIGHLIGHTING ALL MOVABLE LOCATIONS OF THE SELECTED PIECE
		g2.setColor(highlighterMovableColor);
		if(highlightedPiece!=null)
		{
		   for(int i =0; i<movableLocationList.size(); i++)
		   {
			   g2.fillOval(highlighterPadding+movableLocationList.get(i).movableLocationX*chessBoardSquareSize,
					   highlighterPadding+movableLocationList.get(i).movableLocationY*chessBoardSquareSize
					   ,highlighterSize, highlighterSize);
		   }
		}
		
		
		for(int i =0; i < 32; i++)
		{
			
			int tempPiecePosition=1;
			if(piece[i].type.equals("King"))
			{
			  tempPiecePosition = Character.getNumericValue(piecePositionKey.charAt(0));
			}
			else if(piece[i].type.equals("Queen"))
			{
			  tempPiecePosition = Character.getNumericValue(piecePositionKey.charAt(1));
			}
			else if(piece[i].type.equals("Rook"))
			{
			  tempPiecePosition = Character.getNumericValue(piecePositionKey.charAt(2));
			}
			else if(piece[i].type.equals("Horse"))
			{
			  tempPiecePosition = Character.getNumericValue(piecePositionKey.charAt(3));
			}
			else if(piece[i].type.equals("Bishop"))
			{
			  tempPiecePosition = Character.getNumericValue(piecePositionKey.charAt(4));
			}
			else if(piece[i].type.equals("Pawn"))
			{
			  tempPiecePosition = Character.getNumericValue(piecePositionKey.charAt(5));
			}
			if(piece[i].alive)
			{
			int tempMultiplier = piece[i].color.equals("Black")?0:1;
			g2.drawImage(piecesImage,piecePadding+piece[i].locationX*chessBoardSquareSize,piecePadding+piece[i].locationY*chessBoardSquareSize ,
					piecePadding+piece[i].locationX*chessBoardSquareSize+pieceSize, piecePadding+piece[i].locationY*chessBoardSquareSize+pieceSize,
					((tempPiecePosition-1)*renderedPiecesImageWidth/6),(renderedPiecesImageHeight/2)*tempMultiplier, 
					((tempPiecePosition)*renderedPiecesImageWidth/6), (renderedPiecesImageHeight/2)*(tempMultiplier+1), null);
			}
		}
		
		
		
		//DRAWING NAMES ON SCROLLS
		g2.setColor(Color.black);
		g2.setFont(font);
		g2.drawString("Player One", 150,  70);
		g2.drawString("Player Two", 150, 545);
		
		
		//DRAWING PLAYER TIMERS
		if(playerOneTurn)
		{
			playerOneFramesElapsed++;
			if(playerOneFramesElapsed==framesPerSecond)
			{
				playerOneFramesElapsed=0;
				playerOneSecondsElapsed++;
				if(playerOneSecondsElapsed == 59)
				{
					playerOneMinutesElapsed ++;
				}
			}
		}
		else
		{
			playerTwoFramesElapsed++;
			if(playerTwoFramesElapsed==framesPerSecond)
			{
				playerTwoFramesElapsed=0;
				playerTwoSecondsElapsed++;
				if(playerTwoSecondsElapsed == 59)
				{
					playerTwoMinutesElapsed ++;
				}
			}
		}
		String formattedSecondsPlayerOne = String.format("%02d", 59-playerOneSecondsElapsed);
		String formattedMinutesPlayerOne = String.format("%02d", 4-playerOneMinutesElapsed);
		String formattedSecondsPlayerTwo = String.format("%02d", 59-playerTwoSecondsElapsed);
		String formattedMinutesPlayerTwo = String.format("%02d", 4-playerTwoMinutesElapsed);
		g2.drawString(formattedMinutesPlayerOne+" : "+formattedSecondsPlayerOne, 350,  70);
		g2.drawString(formattedMinutesPlayerTwo+" : "+formattedSecondsPlayerTwo, 350, 545);
		
		
		//DRAWING THE ACTIVE PLAYER ICON
		g2.drawImage(activePlayerImage, 275, playerOneTurn?50:525, 25, 25, null);
		
		//DRAWING THE TIMER
		String formattedSeconds = String.format("%02d", secondsElapsed);
		String formattedMinutes = String.format("%02d", minutesElapsed);
		//g2.drawString(formattedMinutes+" : "+formattedSeconds, 240, 50);
	}

	// MAIN METHOD
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Game game = new Game();
		frame.add(game);
		frame.setSize(frameSize, frameSize+25);
		frame.setTitle("Chess");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		backgroundMusicThread = new Thread(new Runnable() {
		    public void run()
		    {
   		         	 try{
   		         	 do {
					    fis = new FileInputStream(getClass().getResource("Music//emotionalBackground.mp3").getPath());
					    playMP3 = new Player(fis);
					    playMP3.play();
					    }while(loopBackgroundMusic);
					    }catch(Exception e){
					    	System.out.println(e);
					    }
		    }
		    });  
	 backgroundMusicThread.start();
	}

	// MOUSE LISTENER EVENTS
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		int mouseLocationX= (e.getX()-chessBoardPadding)/chessBoardSquareSize;
		int mouseLocationY= (e.getY()-chessBoardPadding)/chessBoardSquareSize;
		
				
		//Making a piece selection
		for(int i =0; i <32; i++)
		{
			if(piece[i].locationX==mouseLocationX && piece[i].locationY==mouseLocationY && piece[i].alive)
			{
				boolean selectable = true;
				for(int x =0; x<movableLocationList.size(); x++)
				{
					System.out.print("ENTERED HERE FOR CHECKS");
					if(mouseLocationX==movableLocationList.get(x).movableLocationX && mouseLocationY == movableLocationList.get(x).movableLocationY)
					{
						selectable = false;
						piece[i].alive=false;
					}
				}
				if(selectable && piece[i].alive)
				{
				playSound();
				highlightedPiece = piece[i];
				movableLocationList=new ArrayList<MovableLocation>();
				getMovableLocationList();
				}
				break;
			}
		}
		
		//When a piece has been selected
		if(highlightedPiece!=null)
			{
				for(int i=0; i <movableLocationList.size(); i++)
				{
					if(movableLocationList.get(i).movableLocationX==mouseLocationX && 
									movableLocationList.get(i).movableLocationY == mouseLocationY )
					{
							logicBoard[highlightedPiece.locationX][highlightedPiece.locationY]=33;
							logicBoard[mouseLocationX][mouseLocationY]=highlightedPiece.ID;
							highlightedPiece.locationX=mouseLocationX;
							highlightedPiece.locationY=mouseLocationY;
							highlightedPiece=new Piece();
							movableLocationList=new ArrayList<MovableLocation>();
							printLogicBoard();
							playerOneTurn=!playerOneTurn;
							playSound();
					}
				}
			}

		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

//ACTION LISTENER METHOD
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
	}
	
//KEYLISTENER METHODS
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_RIGHT)
		{
			currentChessBoardImageIndex = currentChessBoardImageIndex+1 == chessBoardImageNames.length? 0:currentChessBoardImageIndex+1;
			initializeImages();
		}
		else if(code == KeyEvent.VK_LEFT)
		{
			currentChessBoardImageIndex = currentChessBoardImageIndex-1 < 0? chessBoardImageNames.length-1:currentChessBoardImageIndex-1;
			initializeImages();
		}
		else if(code == KeyEvent.VK_UP)
		{
			currentPieceImageIndex = currentPieceImageIndex-1 < 0? pieceImageNames.length-1:currentPieceImageIndex-1;
			initializeImages();
		}
		else if(code == KeyEvent.VK_DOWN)
		{
			currentPieceImageIndex = currentPieceImageIndex+1 == pieceImageNames.length? 0:currentPieceImageIndex+1;
			initializeImages();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
//INITIALIZNG IMAGES
	public void initializeImages() 
	{
		piecePositionKey = pieceImageNames[currentPieceImageIndex].substring(pieceImageNames[currentPieceImageIndex].indexOf('-')+1,pieceImageNames[currentPieceImageIndex].indexOf('.'));
		System.out.println("Piece position key "+ piecePositionKey);
		try
		{
		System.out.print(currentChessBoardImageIndex);
		chessBoardImage = ImageIO.read(new File(getClass().getResource("ChessBoardImages/"+chessBoardImageNames[currentChessBoardImageIndex]).getPath()));
		piecesImage = ImageIO.read(new File(getClass().getResource("PieceImages/"+pieceImageNames[currentPieceImageIndex]).getPath()));
		inGameWallpaperImage= ImageIO.read(new File(getClass().getResource("DuringGameWallpaper/castle.jpg").getPath()));
		scrollImage = ImageIO.read(new File(getClass().getResource("MiscImages/scroll.png").getPath()));
		activePlayerImage = ImageIO.read(new File(getClass().getResource("MiscImages/crossSwords.png").getPath()));
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	
//Get movable locations
	public void getMovableLocationList()
	{
		if(highlightedPiece.type=="Pawn")
		{
			int tempMultiplier = highlightedPiece.color.equals("Black")?1:-1;
			boolean pawnMovableSingle = true;
			for(int i =0; i <32; i++)
			{
					if( (piece[i].locationX==highlightedPiece.locationX) &&
						(piece[i].locationY==highlightedPiece.locationY+(tempMultiplier*1)) && piece[i].alive )
						{
						  pawnMovableSingle=false;
						  break;
						}
			}
			if(pawnMovableSingle)
			{
				movableLocationList.add(new MovableLocation(highlightedPiece.locationX, highlightedPiece.locationY+(1*tempMultiplier)));
				if((highlightedPiece.locationY==1&& tempMultiplier==1)||(highlightedPiece.locationY==6 && tempMultiplier==-1))
				{
				boolean pawnMovableDouble = true;
				for(int i =0; i <32; i++)
				{
						if( (piece[i].locationX==highlightedPiece.locationX) &&
							(piece[i].locationY==highlightedPiece.locationY+(tempMultiplier*2)) && piece[i].alive )
							{
							  pawnMovableDouble=false;
							  break;
							}
				}
				if(pawnMovableDouble)
				{
					movableLocationList.add(new MovableLocation(highlightedPiece.locationX, highlightedPiece.locationY+(2*tempMultiplier)));
				}
				}
			}
			//PAWN ATTACKING ANOTHER TEAMS PIECE
			if(
					(highlightedPiece.locationX<7) &&
					(logicBoard[highlightedPiece.locationX+1][highlightedPiece.locationY+tempMultiplier]
					>=16+(tempMultiplier==-1?-16:0) && 
					logicBoard[highlightedPiece.locationX+1][highlightedPiece.locationY+tempMultiplier] 
							<32+(tempMultiplier==-1?-16:0)) )
			{
				movableLocationList.add(new MovableLocation(highlightedPiece.locationX+1, highlightedPiece.locationY+tempMultiplier));
			}
			if(		(highlightedPiece.locationX>0) &&
					(logicBoard[highlightedPiece.locationX-1][highlightedPiece.locationY+tempMultiplier]
					>=16+(tempMultiplier==-1?-16:0) &&
					logicBoard[highlightedPiece.locationX-1][highlightedPiece.locationY+tempMultiplier]
							<32+(tempMultiplier==-1?-16:0)))
			{
				movableLocationList.add(new MovableLocation(highlightedPiece.locationX-1, highlightedPiece.locationY+tempMultiplier));
			}
		}
		
		else if(highlightedPiece.type=="Horse")
		{
			System.out.print("Entered Here");
			int X = highlightedPiece.locationX;
			int Y = highlightedPiece.locationY;
			int tempMultiplier = highlightedPiece.color.equals("Black")?1:-1;
			if(isInBounds(X+2,Y+1) && !isFriendlyPiecePresent(tempMultiplier, X+2, Y+1))
			{
				movableLocationList.add(new MovableLocation(X+2,Y+1));
			}
			if(isInBounds(X+2,Y-1) && !isFriendlyPiecePresent(tempMultiplier, X+2, Y-1))
			{
				movableLocationList.add(new MovableLocation(X+2,Y-1));
			}
			if(isInBounds(X-2,Y+1) && !isFriendlyPiecePresent(tempMultiplier, X-2, Y+1))
			{
				movableLocationList.add(new MovableLocation(X-2,Y+1));
			}
			if(isInBounds(X-2,Y-1) && !isFriendlyPiecePresent(tempMultiplier, X-2, Y-1))
			{
				movableLocationList.add(new MovableLocation(X-2,Y-1));
			}
			if(isInBounds(X+1,Y+2) && !isFriendlyPiecePresent(tempMultiplier, X+1, Y+2))
			{
				movableLocationList.add(new MovableLocation(X+1,Y+2));
			}
			if(isInBounds(X+1,Y-2) && !isFriendlyPiecePresent(tempMultiplier, X+1, Y-2))
			{
				movableLocationList.add(new MovableLocation(X+1,Y-2));
			}
			if(isInBounds(X-1,Y+2) && !isFriendlyPiecePresent(tempMultiplier, X-1, Y+2))
			{
				movableLocationList.add(new MovableLocation(X-1,Y+2));
			}
			if(isInBounds(X-1,Y-2) && !isFriendlyPiecePresent(tempMultiplier, X-1, Y-2))
			{
				movableLocationList.add(new MovableLocation(X-1,Y-2));
			}
		}
		
		else if(highlightedPiece.type=="Bishop")
		{
			int X = highlightedPiece.locationX;
			int Y = highlightedPiece.locationY;
			int tempMultiplier = highlightedPiece.color.equals("Black")?1:-1;
			getDirectionalMovements(tempMultiplier,1,1,X,Y);
			getDirectionalMovements(tempMultiplier,1,-1,X,Y);
			getDirectionalMovements(tempMultiplier,-1,1,X,Y);
			getDirectionalMovements(tempMultiplier,-1,-1,X,Y);
			
		}
		else if(highlightedPiece.type=="Rook")
		{
			int X = highlightedPiece.locationX;
			int Y = highlightedPiece.locationY;
			int tempMultiplier = highlightedPiece.color.equals("Black")?1:-1;
			getDirectionalMovements(tempMultiplier,1,0,X,Y);
			getDirectionalMovements(tempMultiplier,-1,0,X,Y);
			getDirectionalMovements(tempMultiplier,0,1,X,Y);
			getDirectionalMovements(tempMultiplier,0,-1,X,Y);
		}
		else if(highlightedPiece.type=="Queen")
		{
			int X = highlightedPiece.locationX;
			int Y = highlightedPiece.locationY;
			int tempMultiplier = highlightedPiece.color.equals("Black")?1:-1;
			getDirectionalMovements(tempMultiplier,1,0,X,Y);
			getDirectionalMovements(tempMultiplier,-1,0,X,Y);
			getDirectionalMovements(tempMultiplier,0,1,X,Y);
			getDirectionalMovements(tempMultiplier,0,-1,X,Y);
			getDirectionalMovements(tempMultiplier,1,1,X,Y);
			getDirectionalMovements(tempMultiplier,1,-1,X,Y);
			getDirectionalMovements(tempMultiplier,-1,1,X,Y);
			getDirectionalMovements(tempMultiplier,-1,-1,X,Y);
		}
		else if(highlightedPiece.type=="King")
		{
			int X = highlightedPiece.locationX;
			int Y = highlightedPiece.locationY;
			int tempMultiplier = highlightedPiece.color.equals("Black")?1:-1;
			getKingsMovement(tempMultiplier,X,Y+1);
			getKingsMovement(tempMultiplier,X,Y-1);
			getKingsMovement(tempMultiplier,X-1,Y);
			getKingsMovement(tempMultiplier,X+1,Y);
			getKingsMovement(tempMultiplier,X+1,Y+1);
			getKingsMovement(tempMultiplier,X+1,Y-1);
			getKingsMovement(tempMultiplier,X-1,Y+1);
			getKingsMovement(tempMultiplier,X-1,Y-1);
		}
	}
	
//Get ChessBoard Image Names
	public String[] getChessBoardImageNames()
	{
		File folder = new File(getClass().getResource("ChessBoardImages").getPath());
		File[] listOfFiles = folder.listFiles();
		String [] fileNames = new String[listOfFiles.length];

	    for (int i = 0; i < listOfFiles.length; i++) {
	       fileNames[i]= listOfFiles[i].getName();
	    }
	    System.out.println(Arrays.toString(fileNames));
	    return fileNames;
	}
	
	
//Get Piece Image Names
	public String[] getPieceImageNames()
	{
		File folder = new File(getClass().getResource("PieceImages").getPath());
		File[] listOfFiles = folder.listFiles();
		String [] fileNames = new String[listOfFiles.length];

	    for (int i = 0; i < listOfFiles.length; i++) {
	       fileNames[i]= listOfFiles[i].getName();
	    }
	    System.out.println(Arrays.toString(fileNames));
	    return fileNames;
	}
	
//Print the Logic Board
	public void printLogicBoard()
	{
		System.out.println();
		for(int i =0; i <8; i++)
		{
			for(int j=0; j <8; j++)
			{
				System.out.printf("%02d",logicBoard[j][i]);
				System.out.print("  ");
			}
			System.out.println();
		}
	}
	
//To check if movable location is inBounds
	public boolean isInBounds(int locationX, int locationY)
	{
		if(locationX<0 || locationY <0 || locationX >7 || locationY >7)
		{
			return false;
		}
		return true; 
	}

//To check if friendly piece is present in that location
	public boolean isFriendlyPiecePresent( int multiplier, int locationX, int locationY)
	{
		if(logicBoard[locationX][locationY]>= (multiplier==1?0:16) && logicBoard[locationX][locationY]<(multiplier==1?16:32))
		{
			return true;
		}
		return false;
	}
//Returns all possible directional movements of a piece in a particular direction
	public void getDirectionalMovements(int multiplier, int XMultiplier, int YMultiplier, int X, int Y)
	{
		int i =1;
		while (true)
		{
			if(isInBounds(X+i*XMultiplier,Y+i*YMultiplier))
			{
				if(logicBoard[X+i*XMultiplier][Y+i*YMultiplier]==33)
				{
					movableLocationList.add(new MovableLocation(X+i*XMultiplier,Y+i*YMultiplier));
					i++;
				}
				else if(!isFriendlyPiecePresent(multiplier,X+i*XMultiplier, Y+i*YMultiplier))
				{
					movableLocationList.add(new MovableLocation(X+i*XMultiplier,Y+i*YMultiplier));
					break;
				}
				else
				{
					break;
				}
			}
			else 
			{
				break;
			}
		}
	}
	
//getKingsMove
	public void getKingsMovement(int multiplier ,int X, int Y)
	{
		if(isInBounds(X,Y))
		{
			if(!isFriendlyPiecePresent(multiplier,X,Y))
			{
			movableLocationList.add(new MovableLocation(X,Y));
			}
		}
	}
//PlaySound
	public void playSound()
	{
		playSoundThread = new Thread(new Runnable() {
		    public void run()
		    {
   		         	 try{
					    playSoundFis = new FileInputStream(getClass().getResource("Music//WoodClick.mp3").getPath());
					    playSoundMP3 = new Player(playSoundFis);
					    playSoundMP3.play();
					    }catch(Exception e){
					    	System.out.println(e);
					    }
		    }
		    });  
	 playSoundThread.start();
	}
}
