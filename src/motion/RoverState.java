package motion;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import searchproblem.*;


public class RoverState extends State implements Cloneable{
	private int x, y, height;
	private BitmapTerrain terrain;
	public enum RoverOperator{
		N,S,E,W,NE,NW,SE,SW
	};
	
	public RoverState(int startx, int starty, BitmapTerrain t){
		x = startx;
		y = starty;
		terrain = t;
		height = t.getHeight(startx, starty);
	}
	
	private static final int[] PRIMES = 
	    {
	        31, 421, 4951, 25163, 430517, 4904077, 24827059, 424194271, 2147483647
	    };
	
	public List<Arc> successorFunction() {
		List<Arc> l = new ArrayList<Arc>();
		for (RoverOperator op: RoverOperator.values())
			if (applicableOperator(op))
				l.add(successorState(op));
		return l;
	}
	
	public boolean applicableOperator(Object action){
		RoverOperator op;
		if (action instanceof RoverOperator){
			op =  (RoverOperator) action;
			Point point = new Point();
			switch (op){
				case N: point.setLocation(x, y-1);
				break;
				case S: point.setLocation(x, y+1);
				break;
				case E: point.setLocation(x+1, y);
				break;
				case W: point.setLocation(x-1, y);
				break;
				case NW:point.setLocation(x-1, y-1);
				break;
				case NE:point.setLocation(x+1, y-1);
				break;
				case SW:point.setLocation(x-1, y+1);
				break;
				case SE:point.setLocation(x+1, y+1);
			}
			if (point.x >= 0 && point.x < terrain.getHorizontalSize()
					&& point.y >= 0 && point.y < terrain.getVerticalSize())
				return Math.abs(terrain.getHeight(point.x,point.y)-height) <= 1;
		}
		return false; 
		
	}
	
	public Arc successorState(Object action){
		RoverOperator op;
		op =  (RoverOperator) action;
		if (applicableOperator(action)){	
			Point point = new Point();
			switch (op){
				case N: point.setLocation(x, y-1);
				break;
				case S: point.setLocation(x, y+1);
				break;
				case E: point.setLocation(x+1, y);
				break;
				case W: point.setLocation(x-1, y);
				break;
				case NW:point.setLocation(x-1, y-1);
				break;
				case NE:point.setLocation(x+1, y-1);
				break;
				case SW:point.setLocation(x-1, y+1);
				break;
				case SE:point.setLocation(x+1, y+1);
			}
			RoverState n = new RoverState(point.x, point.y, terrain);
			return new Arc(this, n, action);
		}
		return null;
		
	}	

	@Override
	public double applyOperator(Object op) {
		double cost = 0.0;
		if (applicableOperator(op)){
			Arc a = successorState(op);
			int prevX = x;
			int prevY = y;
			int prevH = height;
			x = ((RoverState)a.getChild()).getCoordX();
			y = ((RoverState)a.getChild()).getCoordY();
			height = terrain.getHeight(x, y);
			//System.out.println("("+prevX+","+prevY+","+prevH+"):("+x+","+y+","+height+')');
			switch(terrain.getTerrainType(prevX,prevY)){
				case PLAIN:cost = 1.0;
					break;
				case SAND: cost = 2.0;
					break;
				case ROCK: cost = 3.0;
					break;
			}
			/*return cost * Math.sqrt( Math.pow(x - prevX, 2 )
					+ Math.pow(y - prevY, 2)
					+ Math.pow(height - prevH, 2));*/
		}
		return cost;
	}

	@Override
	public Object clone() {
		return new RoverState(x,y,terrain);
	}

	@Override
	public int hashCode() {
		int prime = nextPrime(terrain.getVerticalSize());
		return prime * x + y;
	}

	@Override
	public boolean equals(Object obj) {
		RoverState state;
		if (obj instanceof RoverState){
			state =  (RoverState) obj;
			return (x == state.getCoordX() && y == state.getCoordY()); 
		}
		return false;
	}

	public int getCoordY(){
		return y;
	}
	
	public int getCoordX(){
		return x;
	}
	
	public int getHeight(){
		return height;
	}
	
	private int nextPrime( int number )
    {
        for ( int i = 0; i < PRIMES.length; i++ )
            if ( PRIMES[i] >= number )
                return PRIMES[i];
        return 0;
    }

}