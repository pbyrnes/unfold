import java.util.*;

public class Facet
{
  private Vector normal;
  private double b;
  private HashSet events;
  private HashSet sourceImages;
  private HashSet ridges;
  private Integer key;
  private HashSet neighbors;

  public void addRidge(Ridge r)
  {
    ridges.add(r);
  }

  public int hashCode()
  {
    return key.hashCode();
  }

  public void addNeighbor(Integer i)
  {
    neighbors.add(i);
  }

  public Facet(Vector v, double a, Integer k)
  {
    key = k;
    normal = (Vector)v.clone();
    b = a;
    events = new HashSet();
    sourceImages = new HashSet();
    ridges = new HashSet();
    neighbors = new HashSet();
  }

  public Facet(String s, Integer k)
  {
    key = k;
    StringTokenizer sT = new StringTokenizer(s);

    Double d = new Double(sT.nextToken());
    b = d.doubleValue();

    while(sT.hasMoreTokens())
    {
      d = new Double(sT.nextToken());
      d = new Double(-1*d.doubleValue());
      normal.add(d);
    }
    events = new HashSet();
    sourceImages = new HashSet();
    ridges = new HashSet();
    neighbors = new HashSet();
  }

  public void addSourceImage(Point s)
  {
    sourceImages.add(s);

//System.out.println("Number source images: " + sourceImages.size());

    //now update the events
    events.clear();

    HashSet vorCells = new HashSet();
    //TODO: create the voronoi diagram of the source images of the facet    
    
    for(Iterator i = ridges.iterator(); i.hasNext();)
    {
      Ridge r = (Ridge)i.next();
      Polyhedron rP = r.getPolyhedron();
      
      for(Iterator j = vorCells.iterator(); j.hasNext();)
      {
        Polyhedron v = (Polyhedron)j.next();
      
        Polyhedron vr = v.intersectWith(rP);

        if(!vr.isEmpty())
        {
          //TODO: create appropriate event
        }
      }
    }
  }
}
