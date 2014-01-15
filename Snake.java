

public class Snake{
	private int locx;
	private int locy;
	private int direction= 0;
	public Snake(int d, int x, int y){
		setDirection(d);
		setLocation(x, y);
	}
	public Snake(){
	}
	public void setLocation(int x, int y){
		locx= x;
		locy= y;
	}
	public int getLocationx(){
		return locx;
	}
	public int getLocationy(){
		return locy;
	}
	public void setDirection(int d){
		direction= d;
	}
	public int getDirection(){
		return direction;
	}
}
