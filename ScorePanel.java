
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel{
	private JLabel label;
	public ScorePanel(int lines){
		label= new JLabel("Score: "+lines);
		super.add(label);
	}
	public void refresh(int score){
		super.remove(label);
		label= new JLabel("Score: "+score);
		super.add(label);
		super.validate();
	}
}