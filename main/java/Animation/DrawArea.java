package Animation;

import SimulationRunner.SimulationRunner;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * This class handles the animation window.
 */
public class DrawArea extends JPanel{
	/**
	 * Radius multiplied with unit
	 */
    private int cellRadius;
	private int numberOfSectors, numberOfAlpha, numberOfDSections;
	private HashMap<Integer, PointColor> primaryNodes;
	private HashMap<Integer, PointColor> crNodes;
    
	/**
	 * Creates an animation window.
	 * @param cellRadius		Radius of the cell
	 * @param numberOfSectors	Number of sectors in the cell
	 * @param numberOfAlpha		Number of alpha slices in a sector
	 * @param numberOfDSections Number of distance sections in a alpha slice
	 * @param numberOfCrNodes	Number of CR nodes in the zone
	 * @param numberOfPriNodes	Number of Primary nodes in the cell
	 */
    public DrawArea(int cellRadius,int numberOfSectors, int numberOfAlpha, int numberOfDSections, int numberOfCrNodes, int numberOfPriNodes) {
        super();
		this.cellRadius = cellRadius;
		this.numberOfSectors = numberOfSectors;
		this.numberOfAlpha = numberOfAlpha;
		primaryNodes = new HashMap<Integer, PointColor>();
		for(int i=0;i<numberOfPriNodes;i++)
			primaryNodes.put(i, null);
		crNodes = new HashMap<Integer, PointColor>();
		for(int i=0;i<numberOfCrNodes;i++)
			crNodes.put(i, null);
		this.numberOfDSections = 3;
    }
    
	@Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.drawOval(0, 0, cellRadius*2, cellRadius*2);
		int tx,ty;
		int sectorInc = 360/numberOfSectors;
		for(int degree=0;degree<360;degree+=sectorInc){
			tx=cellRadius+(int)(cellRadius*Math.cos(((double)(degree)/180.0)*Math.PI));
			ty=cellRadius-(int)(cellRadius*Math.sin(((double)(degree)/180.0)*Math.PI));
			g.drawLine(cellRadius, cellRadius, tx, ty);
		}
		
		g.setColor(Color.ORANGE);
		int alphaInc = sectorInc/numberOfAlpha;
		for(int degree=alphaInc;degree<360;degree+=alphaInc){
			if(degree % sectorInc == 0)
				continue;
			tx=cellRadius+(int)(cellRadius*Math.cos(((double)(degree)/180.0)*Math.PI));
			ty=cellRadius-(int)(cellRadius*Math.sin(((double)(degree)/180.0)*Math.PI));
			g.drawLine(cellRadius, cellRadius, tx, ty);
		}
		int dInc = cellRadius / numberOfDSections;
		g.setColor(Color.BLUE);
		for(int i = 0; i<SimulationRunner.crBase.registeredZones.size() ; i++){
			int sectorNumber = SimulationRunner.crBase.registeredZones.get(i).get(0);
			int alphaNumber = SimulationRunner.crBase.registeredZones.get(i).get(1);
			int dNumber = SimulationRunner.crBase.registeredZones.get(i).get(2);
			int zoneBegDegree = sectorNumber*sectorInc+alphaInc*alphaNumber;
			int zoneEndDegree = sectorNumber*sectorInc+alphaInc*(alphaNumber+1);
			int dmin = dNumber*dInc;
			int dmax = dmin+dInc;
			tx=cellRadius+(int)(dmin*Math.cos(((double)(zoneBegDegree)/180.0)*Math.PI));
			ty=cellRadius-(int)(dmin*Math.sin(((double)(zoneBegDegree)/180.0)*Math.PI));
			int tx2=cellRadius+(int)(dmax*Math.cos(((double)(zoneBegDegree)/180.0)*Math.PI));
			int ty2=cellRadius-(int)(dmax*Math.sin(((double)(zoneBegDegree)/180.0)*Math.PI));
			g.drawLine(tx, ty, tx2, ty2);
			tx=cellRadius+(int)(dmin*Math.cos(((double)(zoneEndDegree)/180.0)*Math.PI));
			ty=cellRadius-(int)(dmin*Math.sin(((double)(zoneEndDegree)/180.0)*Math.PI));
			tx2=cellRadius+(int)(dmax*Math.cos(((double)(zoneEndDegree)/180.0)*Math.PI));
			ty2=cellRadius-(int)(dmax*Math.sin(((double)(zoneEndDegree)/180.0)*Math.PI));
			g.drawLine(tx, ty, tx2, ty2);
			g.drawArc(cellRadius-dmin, cellRadius-dmin, 2*dmin, 2*dmin, zoneBegDegree, alphaInc);
			g.drawArc(cellRadius-dmax, cellRadius-dmax, 2*dmax, 2*dmax, zoneBegDegree, alphaInc);
		}
    }

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		for(Integer i:primaryNodes.keySet()){
			PointColor p = primaryNodes.get(i);
			if(p==null)
				continue;
			g.setColor(p.c);
			g.fillOval(p.x-p.r/2, p.y-p.r/2, p.r, p.r);
		}
		BufferedImage comm = null;
		BufferedImage idle = null;
		try {
			comm = ImageIO.read(new File("comm.JPG"));
			idle = ImageIO.read(new File("idle.JPG"));
		} catch (IOException ex) {
			Logger.getLogger(DrawArea.class.getName()).log(Level.SEVERE, null, ex);
		}
		for(Integer i:crNodes.keySet()){
			
			PointColor p = crNodes.get(i);
			if(p==null)
				continue;
			g.setColor(p.c);

			if(p.c != Color.GREEN)
				g.drawImage(idle, (int)(p.x - p.r/2.0), (int)(p.y - p.r/2.0), (int)(p.r), (int)(p.r), null);
		}
		for(Integer i:crNodes.keySet()){
			PointColor p = crNodes.get(i);
			if(p==null)
				continue;
			if(p.c == Color.GREEN){
				g.drawImage(comm, (int)(p.x - p.r/2.0), (int)(p.y - p.r/2.0), (int)(p.r), (int)(p.r), null);
			}
		}
	}
	
	/**
	 * This method paints a primary node with the given id, position, and color.
	 * @param id	ID of the primary node
	 * @param p		Position of the CR node
	 */
	public void paintPrimary(Integer id, PointColor p)
	{
		primaryNodes.put(id, p);
		repaint();
	}
	
	/**
	 * This method paints a CR node with the given id, position.
	 * @param id	ID of the CR node
	 * @param p		Position of the CR node
	 */
	public void paintCR(Integer id, PointColor p)
	{
		crNodes.put(id, p);
		repaint();
	}
}
