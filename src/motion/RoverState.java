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
				case N: point.move(x, y-1);
				break;
				case S: point.move(x, y+1);
				break;
				case E: point.move(x+1, y);
				break;
				case W: point.move(x-1, y);
				break;
				case NW:point.move(x-1, y-1);
				break;
				case NE:point.move(x+1, y-1);
				break;
				case SW:point.move(x-1, y+1);
				break;
				case SE:point.move(x+1, y+1);
			}
			//System.out.println(point.x + ":" + point.y);
			if (point.x >= 0 && point.x < terrain.getHorizontalSize()
					&& point.y >= 0 && point.y < terrain.getVerticalSize())
				return Math.abs(terrain.getHeight(point.x,point.y)-height) <= 10;
		}
		return false; 
		
	}
	
	public Arc successorState(Object action){
		if (applicableOperator(action)){
			RoverState n = (RoverState)this.clone();
			double custo = n.applyOperator(action);
			return new Arc(this,n,action,custo);
		}
		return null;
		
	}	

	@Override
	public double applyOperator(Object op) {
		double cost = 0.0;
		if (applicableOperator(op)){
			Point point = new Point();
			RoverOperator opp =  (RoverOperator) op;
			switch (opp){
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
			int prevX = x;
			int prevY = y;
			int prevH = height;
			x = point.x;
			y = point.y;
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
			return cost * Math.exp(Math.abs(height - prevH)) * Math.sqrt(((x - prevX)*(x - prevX))
					+ ((y - prevY)*(y - prevY))
					+ ((height - prevH)*(height - prevH)));
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