package splendor.model;

public class GraphicInfo {
	private int x;
	private int y;
	private int width;
	private int height;
	
	public GraphicInfo(int x, int y,int width,int height) {
		if(x < 0 || y < 0 || width < 0 || height < 0) throw new IllegalArgumentException();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	public GraphicInfo() {
		this(0,0,0,0);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	@Override
	public String toString() {
		return "[info : x="+x+" ,y="+y+"]";
		
	}
	
}
