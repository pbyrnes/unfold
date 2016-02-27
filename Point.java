import java.io.*;
import java.util.*;

public class Point
{
  private Vector coord;

  public Point()
  {
    coord = new Vector();
  }

  public Point(Vector v)
  {
    coord = (Vector)v.clone();
  }

  public Point(String s)
  {
    coord = new Vector();
    StringTokenizer sT = new StringTokenizer(s);
    while(sT.hasMoreTokens())
    {
      Double tmpD = new Double(sT.nextToken());
      coord.addElement(tmpD);
    }
  }

  public String toString()
  {
    String s = new String();
    for(int i=0; i<coord.size(); i++)
    {
      Double tmpD = (Double)coord.elementAt(i);
      s.concat(tmpD.toString());
    }
    return s;
  }

  public double distance(Point p)
  {
    if(p.coord.size() != coord.size())
    {
      return -1;
    }

    double t = 0;

    for(int i=0; i<coord.size(); i++)
    {
      Double x = (Double)coord.elementAt(i);
      Double y = (Double)p.coord.elementAt(i);
      double s = x.doubleValue()-y.doubleValue();
      s = s * s;
      t = t + s;
    }

    t = Math.sqrt(t);

    return t;
  }

  public Point applyAffineTransform(Matrix M, Vector b)
  {
    Point p = new Point();

    Vector v = M.mult(coord);

    for(int i=0; i<coord.size(); i++)
    {
      Double d1 = (Double)v.elementAt(i);
      Double d2 = (Double)b.elementAt(i);

      double d = d1.doubleValue() + d2.doubleValue();
      p.coord.setElementAt(new Double(d), i);
    }
  
    return p;
  }
}
