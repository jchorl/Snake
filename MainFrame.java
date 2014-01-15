
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainFrame extends JFrame{
	private GamePanel gp;
	private JPanel mainPanel;
	private ScorePanel sp= new ScorePanel(0);
	private ImageIcon[] images= setImages();
	private boolean[][] board= new boolean[25][25];
	private Snake[] snake= new Snake[4];
	private Snake extraSnake;
	private Timer t= new Timer();
	private int score= 0;
	private JFrame jf= this;
	private boolean extra= false;
	private boolean endAtWall;
	public MainFrame(){
		JTextField widthField= new JTextField();
		widthField.setColumns(2);
		JTextField heightField= new JTextField();
		heightField.setColumns(2);
		JCheckBox boundaryCheck= new JCheckBox();
		Object[] dataIn= {"Width", widthField, "Height", heightField, "Boundaries?", boundaryCheck};
		JOptionPane op= new JOptionPane(dataIn,JOptionPane.QUESTION_MESSAGE,JOptionPane.DEFAULT_OPTION,null,null);
		JDialog dialog= op.createDialog(op, "Settings");
		dialog.setVisible(true);
		endAtWall= boundaryCheck.isSelected();
		int width= Integer.parseInt(widthField.getText());
		int height= Integer.parseInt(heightField.getText());
		board= new boolean[width][height];
		super.setSize(width*15+80,height*15+40);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		start();
		placeExtra();
		gp= new GamePanel(board, images, snake);
		mainPanel= new JPanel(new GridBagLayout());
		GridBagConstraints mc= new GridBagConstraints();
		mc.anchor= GridBagConstraints.NORTHWEST;
		mc.gridx= 0;
		mc.gridy= 0;
		mainPanel.add(gp, mc);
		mc.gridx= 1;
		sp= new ScorePanel(score);
		mainPanel.add(sp, mc);
		super.add(mainPanel);
		super.setVisible(true);
		this.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_RIGHT){
					snake[0].setDirection(4);
				}
				else if(e.getKeyCode()==KeyEvent.VK_LEFT){
					snake[0].setDirection(3);
				}
				else if(e.getKeyCode()==KeyEvent.VK_DOWN){
					snake[0].setDirection(1);
				}
				else if(e.getKeyCode()==KeyEvent.VK_UP){
					snake[0].setDirection(2);
				}
			}
			public void keyReleased(KeyEvent e){
			}
			public void keyTyped(KeyEvent e){
			}
		});
		t.schedule(new TimerTask(){
			public void run(){
				int[] loc= {snake[snake.length-1].getLocationx(), snake[snake.length-1].getLocationy()};//old coordinates of last snake cell
				for(int i= snake.length-1; i>0; i--){
					snake[i]= new Snake(snake[i-1].getDirection(), snake[i-1].getLocationx(), snake[i-1].getLocationy());
				}//makes each cell of the snake equal to the one ahead so that the very first cell is empty
				if(endAtWall){
					switch(snake[1].getDirection()){//assigns the first cell of the snake
					case 1: snake[0]= new Snake(snake[1].getDirection(), snake[1].getLocationx(), snake[1].getLocationy()+1);
					break;
					case 2: snake[0]= new Snake(snake[1].getDirection(), snake[1].getLocationx(), snake[1].getLocationy()-1);
					break;
					case 3: snake[0]= new Snake(snake[1].getDirection(), snake[1].getLocationx()-1, snake[1].getLocationy());
					break;
					case 4: snake[0]= new Snake(snake[1].getDirection(), snake[1].getLocationx()+1, snake[1].getLocationy());
					break;
					}
				}
				else{
					switch(snake[1].getDirection()){//assigns the first cell of the snake
					case 1: 
						if(snake[1].getLocationy()+1==board[0].length){
							snake[0]= new Snake(snake[1].getDirection(), snake[1].getLocationx(), 0);
						}
						else{
							snake[0]= new Snake(snake[1].getDirection(), snake[1].getLocationx(), snake[1].getLocationy()+1);
						}
						break;
					case 2: 
						if(snake[1].getLocationy()-1==-1){
							snake[0]= new Snake(snake[1].getDirection(), snake[1].getLocationx(), board[0].length-1);
						}
						else{
							snake[0]= new Snake(snake[1].getDirection(), snake[1].getLocationx(), snake[1].getLocationy()-1);
						}
						break;
					case 3: 
						if(snake[1].getLocationx()-1==-1){
							snake[0]= new Snake(snake[1].getDirection(), board.length-1, snake[1].getLocationy());
						}
						else{
							snake[0]= new Snake(snake[1].getDirection(), snake[1].getLocationx()-1, snake[1].getLocationy());
						}
						break;
					case 4:
						if(snake[1].getLocationx()+1==board.length){
							snake[0]= new Snake(snake[1].getDirection(), 0, snake[1].getLocationy());
						}
						else{
							snake[0]= new Snake(snake[1].getDirection(), snake[1].getLocationx()+1, snake[1].getLocationy());
						}
						break;
					}
				}
				if(snake[0].getLocationx()==extraSnake.getLocationx()&&snake[0].getLocationy()==extraSnake.getLocationy()){//if the head hits the extra
					Snake[] temp= new Snake[snake.length];
					for(int i= 0; i<snake.length; i++){
						temp[i]= new Snake(snake[i].getDirection(), snake[i].getLocationx(), snake[i].getLocationy());
					}
					snake= new Snake[snake.length+1];
					for(int i= 0; i<temp.length; i++){
						snake[i]= new Snake(temp[i].getDirection(), temp[i].getLocationx(), temp[i].getLocationy());
					}//copied snake array back to snake with 1 more space for the extra on the tail
					switch(snake[snake.length-2].getDirection()){
					case 1: snake[snake.length-1]= new Snake(snake[snake.length-2].getDirection(), snake[snake.length-2].getLocationx(), snake[snake.length-2].getLocationy()-1);
					break;
					case 2: snake[snake.length-1]= new Snake(snake[snake.length-2].getDirection(), snake[snake.length-2].getLocationx(), snake[snake.length-2].getLocationy()+1);
					break;
					case 3: snake[snake.length-1]= new Snake(snake[snake.length-2].getDirection(), snake[snake.length-2].getLocationx()+1, snake[snake.length-2].getLocationy());
					break;
					case 4: snake[snake.length-1]= new Snake(snake[snake.length-2].getDirection(), snake[snake.length-2].getLocationx()-1, snake[snake.length-2].getLocationy());
					break;
					}
					extra= false;
					if(snake[snake.length-1].getLocationx()==-1){
						snake[snake.length-1].setLocation(board.length-1, snake[snake.length-1].getLocationy());
					}
					if(snake[snake.length-1].getLocationy()==-1){
						snake[snake.length-1].setLocation(snake[snake.length-1].getLocationx(), board[0].length-1);
					}					
					board[snake[snake.length-1].getLocationx()][snake[snake.length-1].getLocationy()]= false;
					score++;
					sp.refresh(score);
					placeExtra();
				}
				boolean gameOver= false;
				for(int i= 1; i<snake.length; i++){
					if(snake[0].getLocationx()==snake[i].getLocationx()&&snake[0].getLocationy()==snake[i].getLocationy()){
						endGame();
						gameOver= true;
					}
				}
				if(endAtWall){
					if(snake[0].getLocationx()==board.length||snake[0].getLocationx()==-1||snake[0].getLocationy()==-1||snake[0].getLocationy()==board[0].length){
						endGame();
						gameOver= true;
					}
				}
				if(!gameOver){//if the game isnt over, refresh
					gp.Refresh(board, images, snake, loc, extraSnake, MainFrame.this);
				}
			}
		}, 1000, 100);
	}
	public void start(){
		int xstart= (int)(Math.random()*(board.length-8)+4);
		int ystart= (int)(Math.random()*(board[0].length-8)+4);
		int direction= (int)(Math.random()*4+1);
		snake[0]= new Snake(direction, xstart, ystart);
		if(direction==1){
			snake[1]= new Snake(direction, xstart, ystart-1);
			snake[2]= new Snake(direction, xstart, ystart-2);
			snake[3]= new Snake(direction, xstart, ystart-3);
		}
		else if(direction==2){
			snake[1]= new Snake(direction, xstart, ystart+1);
			snake[2]= new Snake(direction, xstart, ystart+2);
			snake[3]= new Snake(direction, xstart, ystart+3);
		}
		else if(direction==3){
			snake[1]= new Snake(direction, xstart+1, ystart);
			snake[2]= new Snake(direction, xstart+2, ystart);
			snake[3]= new Snake(direction, xstart+3, ystart);
		}
		else if(direction==4){
			snake[1]= new Snake(direction, xstart-1, ystart);
			snake[2]= new Snake(direction, xstart-2, ystart);
			snake[3]= new Snake(direction, xstart-3, ystart);
		}
	}
	public ImageIcon[] setImages(){
		ImageIcon[] images= new ImageIcon[2];
		URL[] url= new URL[2];
		url[0]= getClass().getResource("0.jpg");
		url[1]= getClass().getResource("1.jpg");
		try{
			for(int i= 0; i<2; i++){
				images[i]= new ImageIcon(ImageIO.read(url[i]));
			}
		}catch(Exception e){}
		return images;
	}
	public void endGame(){
		t.cancel();
	}
	public void placeExtra(){
		if(!extra){
			int x= (int)(Math.random()*board.length);
			int y= (int)(Math.random()*board[0].length);
			boolean partOfSnake= false;
			do{
				for(Snake s:snake){
					if(s.getLocationx()==x&&s.getLocationy()==y){
						partOfSnake= true;
						break;
					}
					else{
						partOfSnake= false;
					}
				}
				if(partOfSnake){
					x= (int)(Math.random()*board.length);
					y= (int)(Math.random()*board[0].length);
				}
			}while(partOfSnake);//makes sure that the extra isnt placed on a part of the snake
			board[x][y]= true;
			extraSnake= new Snake(1, x, y);
			extra= true;
		}
	}
	public static void main(String[] args){
		MainFrame mf= new MainFrame();
	}
}