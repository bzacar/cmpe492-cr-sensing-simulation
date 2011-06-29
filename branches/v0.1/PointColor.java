package firstproject;

import java.awt.Color;
import java.awt.geom.Point2D;

class PointColor {
	int x,y,r;
	Color c;

	/**
	 * Constructs a PointColor with specied coordinate and color
	 * @param x x coordinate of the point
	 * @param y y coordinate of the point
	 * @param r Radius of the point
	 * @param c Color of the point
	 */
	public PointColor(int x, int y, int r, Color c) {
		this.x = x;
		this.y = y;
		this.r = r;
		this.c = c;
	}
	
	/**
	 * Constructs a PointColor from Point2D by scaling its coordinate and with a specified color
	 * @param p Point2D to be converted
	 * @param r Radius of the point
	 * @param c Color of the point
	 * @param unit Scale unit
	 */
	public PointColor(Point2D.Double p, int r, Color c, int unit) {
		this.x = (int)(p.x*unit);
		this.y = (int)(p.y*unit);
		this.r = r;
		this.c = c;
	}
	
	/**
	 * Convert this PointColor object from regular x-y coordinate system to JFrame x-y coordinate system
	 * @return Returns this object for cascading
	 */
	public PointColor convertCoordinate(int cellRadius)
	{
		x+=cellRadius;
		y*=-1;
		y+=cellRadius;
		return this;
	}
}
