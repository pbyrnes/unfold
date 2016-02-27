import java.io.*;
import java.util.*;

public class Matrix
{
  private Vector A; //a vector of the rows of A

  public Matrix()
  {
    A = new Vector();
  }

  public Object clone()
  {
    Matrix m = new Matrix();
    m.A = (Vector)A.clone();

    return m;
  }

  public Matrix(Vector v)
  {
    A = new Vector();
    for(int i=0; i<v.size(); i++)
    {
      A.add(((Vector)v.elementAt(i)).clone());
    }      
  }

  public Vector mult(Vector x)
  {
    Vector v = new Vector();
    for(int i=0; i<A.size(); i++)
    {
      Vector row = (Vector)A.elementAt(i);

      double tot = 0;
      for(int j=0; j<row.size(); j++)
      {
        Double d1 = (Double)x.elementAt(j);
        Double d2 = (Double)row.elementAt(j);

        double d = d1.doubleValue() * d2.doubleValue();
        tot = tot + d;
      }
      
      v.setElementAt(new Double(tot), i);
    }
    return v;
  }

  public Vector getRow(int i)
  {
    return (Vector)A.elementAt(i);
  }

  public int numRows()
  {
    return A.size();
  }

  public int numColumns()
  {
    return ((Vector)A.firstElement()).size();
  }

  public double getEntry(int i, int j)
  {
    Vector tmp = (Vector)A.elementAt(i-1);
    Double d = (Double)tmp.elementAt(j-1);
    return d.doubleValue();
  }
}
