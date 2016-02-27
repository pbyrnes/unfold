import java.util.*;

public class Ridge
{
  private Facet f1;
  private Facet f2;
  private Polyhedron r;
  private Matrix A;  //the rotation from f1 to f2 is Ax+b
  private Vector b;

  public Facet getFacet()
  {
    return f1;
  }

  public Facet getTargetFacet()
  {
    return f2;
  }

  public Ridge(Facet f, Facet g, Polyhedron p)
  {
    f1 = f;
    f2 = g;
    r = p;
    
    //TODO: create A and b
  }

  public Point getImage(Point p)
  {
    return p.applyAffineTransform(A, b);
  }

  public Polyhedron getPolyhedron()
  {
    return r;
  }
}
