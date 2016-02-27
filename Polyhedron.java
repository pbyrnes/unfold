import java.util.*;
import java.io.*;

public class Polyhedron
{
  private Matrix A;
  private Vector b; //the polyhedron is {x:Ax<=b}
  private boolean empty;

  public Polyhedron()
  {
    A = new Matrix();
    b = new Vector();
    empty = true;
  }

  public Polyhedron(Matrix m, Vector v)
  {
    A = (Matrix)m.clone();
    b = (Vector)v.clone();
    if(b.size() > 0)
    {
      empty = false;
    }
    else
    {
      empty = true;
    }
  }
    

  public Polyhedron intersectWith(Polyhedron p) throws IOException
  {
    //TODO: write this function (can use cdd to do the heavy lifting)
    File outFile = new File("temp.ine");
    FileWriter out = new FileWriter(outFile);
    PrintWriter fout = new PrintWriter(out);

    fout.println("H-representation");
    fout.println("begin");
   
    int m = p.A.numRows() + A.numRows();
    int d = A.numColumns();
    int dPlus = d+1;

    if(d != p.A.numColumns())
    {
      System.out.println("ERROR: INTERSECTION OF INCOMPATIBLE POLYHEDRA");
      return new Polyhedron();
    }

    fout.println(m + " " + dPlus + " real");

    for(int i=1; i<=A.numRows(); i++)
    {
      fout.print(((Double)b.elementAt(i)).doubleValue() + " ");
      for(int j=1; j<=d; j++)
      {
        fout.print(-1*A.getEntry(i,j) + " ");
      }
      fout.println();
    }

    for(int i=1; i<=p.A.numRows(); i++)
    {
      fout.print(((Double)p.b.elementAt(i)).doubleValue() + " ");
      for(int j=1; j<=d; j++)
      {
        fout.print(-1*p.A.getEntry(i,j) + " ");
      }
      fout.println();
    }

    fout.println("end");
    fout.close();

    Process proc = Runtime.getRuntime().exec("cdd temp.ine");
    proc = Runtime.getRuntime().exec("cdd temp.ext");

    File inFile = new File("temp.ine");
    FileReader in = new FileReader(inFile);
    BufferedReader br = new BufferedReader(in);

    String s = new String(br.readLine());
    while(s.startsWith("*"))
    {
      s = new String(br.readLine());
    }

    br.readLine();
    br.readLine();
    s = br.readLine();
    Vector tmpM = new Vector();
    Vector tmpB = new Vector();
    while(!(s.startsWith("e")))
    {
      StringTokenizer sT = new StringTokenizer(s);
      String s2 = sT.nextToken();
      tmpB.add(new Double(s2));

      Vector tmpV = new Vector();
      while(sT.hasMoreTokens())
      {
        s2 = sT.nextToken();
        Double dd = new Double(s2);
        tmpV.add(dd);
      }  
      tmpM.add(tmpV);
      
      s = br.readLine();
    }

    Matrix mat = new Matrix(tmpM);

    return new Polyhedron(mat,tmpB);

  }

  public boolean isEmpty()
  {
    return empty;
  }
}
