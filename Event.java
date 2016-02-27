import java.util.*;

public class Event implements Comparable
{
  private Ridge r;
  private Point sourceImage;
  private double radius;
  private Vector angleSequence;
  private double t1;
  private double t2;

  public int compareTo(Object o)
  {
    if (this.radius < ((Event)o).radius)
    {
      return -1;
    }
    else
    {
      if(this.radius > ((Event)o).radius)
      {
        return 1;
      }
      else
      {
        //TODO: change this to comparing angle sequences
        //the radii are equal, so compare angles
        if(this.angle < ((Event)o).angle)
        {
          return -1;
        }
        else
        {
          return 1;
        }
      }
    }        
  }

  public Event(Ridge rdg, Point s, double a, double b)
  {
    r = rdg;
    sourceImage = s;
    t1 = a;
    t2 = b;

    //find the radius
    double t = 
(r.v[0]*(s.x-r.p.x)+r.v[1]*(s.y-r.p.y)+r.v[2]*(s.z-r.p.z))/(r.v[0]*r.v[0]+r.v[1]*r.v[1]+r.v[2]*r.v[2]);
    Point p = new Point(r.p.x+t*r.v[0],r.p.y+t*r.v[1],r.p.z+t*r.v[2]);
    Point p1 = new Point(r.p.x+r.t1*r.v[0],r.p.y+r.t1*r.v[1],r.p.z+r.t1*r.v[2]);
    Point p2 = new Point(r.p.x+r.t2*r.v[0],r.p.y+r.t2*r.v[1],r.p.z+r.t2*r.v[2]);
//System.out.println("p is: " + p.x + ", " + p.y + ", " + p.z);
//System.out.println("p1 is: " + p1.x + ", " + p1.y + ", " + p1.z);
//System.out.println("p2 is: " + p2.x + ", " + p2.y + ", " + p2.z);

    double d = dist(p,s);
    double d1 = dist(p1,s);
    double d2 = dist(p2,s);
    if(t >= t1 && t <= t2)
    {
//System.out.println("Radius is d");
      radius = d;
      //find the angle
      angle = ((s.x-p.x)*r.v[0]+(s.y-p.y)*r.v[1]+(s.z-p.z)*r.v[2])
              /Math.sqrt(((s.x-p.x)*(s.x-p.x)+(s.y-p.y)*(s.y-p.y)+(s.z-p.z)*(s.z-p.z))) 
              /Math.sqrt((r.v[0]*r.v[0]+r.v[1]*r.v[1]+r.v[2]*r.v[2]));
    }
    else
    {
      if(d1 < d2)
      {
//System.out.println("Radius is d1");
        radius = d1;
        //find the angle
        angle = ((s.x-p1.x)*r.v[0]+(s.y-p1.y)*r.v[1]+(s.z-p1.z)*r.v[2])
                /Math.sqrt(((s.x-p1.x)*(s.x-p1.x)+(s.y-p1.y)*(s.y-p1.y)+(s.z-p1.z)*(s.z-p1.z))) 
                /Math.sqrt((r.v[0]*r.v[0]+r.v[1]*r.v[1]+r.v[2]*r.v[2]));
      }
      else
      {
//System.out.println("Radius is d2");
        radius = d2;
        //find the angle
        angle = ((s.x-p2.x)*r.v[0]+(s.y-p2.y)*r.v[1]+(s.z-p2.z)*r.v[2])
                /Math.sqrt(((s.x-p2.x)*(s.x-p2.x)+(s.y-p2.y)*(s.y-p2.y)+(s.z-p2.z)*(s.z-p2.z))) 
                /Math.sqrt((r.v[0]*r.v[0]+r.v[1]*r.v[1]+r.v[2]*r.v[2]));
      }
    }
    if(angle < 0)
    {
      angle = angle*-1;
    }
//System.out.println("f2 normal is: " + r.f2.normal[0] + ", " + r.f2.normal[1] + ", " + r.f2.normal[2]);
//System.out.println("Radius is: " + radius);    
  }

  public Facet getFacet()
  {
    return r.getFacet();
  }

  public Facet getTargetFacet()
  {
    return r.getTargetFacet();
  }

  public Point potentialNewSourceImage()
  {
    return r.getImage(sourceImage);
  }

  double dist(Point p1, Point p2)
  {
    return Math.sqrt((p1.x-p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y)+(p1.z-p2.z)*(p1.z-p2.z));
  }
}
