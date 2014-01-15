
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GamePanel extends JPanel{

	public GamePanel(final boolean[][] board, final ImageIcon[] images, final Snake[] snake){
		Runnable makeGamePanel= new Runnable(){
			public void run(){
				GamePanel.this.setLayout(new GridBagLayout());
				GamePanel.this.setBackground(Color.gray);
				for(Snake s:snake){
					board[s.getLocationx()][s.getLocationy()]= true;
				}
				GridBagConstraints gbc= new GridBagConstraints();
				gbc.anchor= GridBagConstraints.NORTHWEST;
				for(int i= 0; i<board.length; i++){
					gbc.gridx= i;
					for(int j= 0; j<board[0].length; j++){
						gbc.gridy= j;
						if(board[i][j]){
							GamePanel.this.add(new JLabel(images[1]), gbc, i*board.length+j);	
						}
						else{
							GamePanel.this.add(new JLabel(images[0]), gbc, i*board.length+j);	
						}
					}
				}
			}
		};
		try{
			SwingUtilities.invokeAndWait(makeGamePanel);
		}catch(Exception e){}
	}
	public void Refresh(final boolean[][] board, final ImageIcon[] images, final Snake[] snake, final int[] loc, final Snake extra, final MainFrame mf){
		Runnable refreshGamePanel= new Runnable(){
			public void run(){
				GridBagConstraints gbc= new GridBagConstraints();
				gbc.anchor= GridBagConstraints.NORTHWEST;
				gbc.gridx= loc[0];//old x of last snake cell
				gbc.gridy= loc[1];//old y of last snake cell
				GamePanel.this.remove(loc[0]*board.length+loc[1]);//remove last block of snake by index
				GamePanel.this.add(new JLabel(images[0]), gbc, loc[0]*board.length+loc[1]);//repaint last block of snake by index
				gbc.gridx= snake[0].getLocationx();
				gbc.gridy= snake[0].getLocationy();
				GamePanel.this.remove(snake[0].getLocationx()*board.length+snake[0].getLocationy());//remove next block by index
				GamePanel.this.add(new JLabel(images[1]), gbc, snake[0].getLocationx()*board.length+snake[0].getLocationy());//repaint next block as head of snake
				gbc.gridx= extra.getLocationx();
				gbc.gridy= extra.getLocationy();
				GamePanel.this.remove(extra.getLocationx()*board.length+extra.getLocationy());//remove extra block by index
				GamePanel.this.add(new JLabel(images[1]), gbc, extra.getLocationx()*board.length+extra.getLocationy());//repaint extra block by index
				GamePanel.this.validate();
			}
		};
		try{
			SwingUtilities.invokeAndWait(refreshGamePanel);
		}catch(Exception e){}
	}
}
