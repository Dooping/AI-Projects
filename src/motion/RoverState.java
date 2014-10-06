package motion;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;
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
	
	protected static final int[] PRIMES = 
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
				case S: point.move(x, y+1);
				case E: point.move(x+1, y);
				case W: point.move(x-1, y);
				case NW:point.move(x-1, y-1);
				case NE:point.move(x+1, y-1);
				case SW:point.move(x-1, y+1);
				case SE:point.move(x+1, y+1);
			}
			return Math.abs(terrain.getHeight(point.x,point.y)-height) <= 1
					&& point.x >= 0 && point.x <= terrain.getHorizontalSize()
					&& point.y >= 0 && point.y <= terrain.getVerticalSize();
		}
		return false; 
		
	}
	
	public Arc successorState(Object action){
		RoverOperator op;
		op =  (RoverOperator) action;
		if (applicableOperator(action)){	
			Point point = new Point();
			switch (op){
				case N: point.move(x, y-1);
				case S: point.move(x, y+1);
				case E: point.move(x+1, y);
				case W: point.move(x-1, y);
				case NW:point.move(x-1, y-1);
				case NE:point.move(x+1, y-1);
				case SW:point.move(x-1, y+1);
				case SE:point.move(x+1, y+1);
			}
			return new Arc(this, new RoverState(point.x, point.y, terrain), action);
		}
		return null;
		
	}	

	@Override
	public double applyOperator(Object op) {
		double cost = 10000.0;
		if (applicableOperator(op)){
			Arc a = successorState(op);
			int prevX = x;
			int prevY = y;
			int prevH = height;
			x = ((RoverState)a.getChild()).getCoordX();
			y = ((RoverState)a.getChild()).getCoordY();
			height = terrain.getHeight(x, y);
			//System.out.println('('+prevX+','+prevY+','+prevH+"):("+x+','+y+','+height+')');
			switch(terrain.getTerrainType(x, y)){
				case PLAIN:cost = 1.0;
					break;
				case SAND: cost = 2.0;
					break;
				case ROCK: cost = 3.0;
					break;
			}
			return cost * sqrt( pow(x - prevX, 2 )
					+ pow(y - prevY, 2)
					+ pow(height - prevH, 2));
		}
		return cost;
	}

	@Override
	public Object clone() {
		return new RoverState(x,y,terrain);
	}

	@Override
	public int hashCode() {
		int PRIME = nextPrime(terrain.getVerticalSize());
		return PRIME * x + y;
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