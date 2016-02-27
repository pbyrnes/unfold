import java.io.*;
import java.util.*;
import java.lang.*;

public class unfold
{
  public static void main(String[] args)
  {
    TreeSet events = new TreeSet();
    HashSet facets = new HashSet();

    //TODO: figure out how to input source point and the vertices
    File inFile = new File(args[0]);
    FileReader in = new FileReader(inFile);
    BufferedReader br = new BufferedReader(in);

    StringTokenizer sT = new StringTokenizer(new String(br.readLine()));
    Integer tmpInt = new Integer(sT.nextToken());
    int dim = tmpInt.intValue() - 1;
    Integer nV = new Integer(sT.nextToken());
    int numVertices = nV.intValue();

    Point source = new Point(new String(br.readLine()));

    HashSet vertices = new HashSet();

    for(int i=0; i<numVertices; i++)
    {
      Point p = new Point(new String(br.readLine()));
      vertices.add(p);
    }

    FileOutputStream out =  new FileOutputStream("temp.ext");
    PrintStream pS = new PrintStream(out);

    try
    {
      pS.println("V-representation");
      pS.println("begin");
      pS.print(numVertices);
      pS.print(" ");
      pS.print(dim+2);
      pS.println("  real");
      for(Iterator i = vertices.iterator(); i.hasNext();)
      {
        Point p = (Point)i.next();
        pS.print("1 ");
        pS.println(p);
      }

      pS.println("end");
      pS.println("adjacency");

      pS.close();
    }
    catch (Exception e)
    {
      System.err.println("Error writing to temp.ext");
    }

    //TODO: need to set cdd up on the math computers
    String[] cmd = {"sh", "-c", "../cdd/cdd-061/cdd temp.ext"};
    Process proc = Runtime.getRuntime().exec(cmd);

    inFile = new File("temp.ine");
    in = new FileReader(inFile);
    br = new BufferedReader(in);

    String tmpS = new String(br.readLine());
    while(tmpS.startsWith("*"))
    {
      tmpS = br.readLine();
    }

    br.readLine();
    br.readLine();

    sT = new StringTokenizer(new String(br.readLine()));
    tmpInt = new Integer(sT.nextToken());
    int numFacets = tmpInt.intValue();
    
    for(int i=1; i<=numFacets; i++)
    {
      Facet f = new Facet(new String(br.readLine()));
      facets.put(f);
    }

    inFile = new File("temp.iad");
    in = new FileReader(inFile);
    br = new BufferedReader(in);

    tmpS = new String(br.readLine());
    while(tmpS.startsWith("*"))
    {
      tmpS = br.readLine();
    }

    br.readLine();
    br.readLine();

    for(int i=1; i<=numFacets; i++)
    {
      //read in the adjacent facets
      sT = new StringTokenizer(new String(br.readLine())," :");
      sT.nextToken();
      tmpInt = new Integer(i);
      Facet f = facets.get(tmpInt);
      while(sT.hasMoreTokens())
      {
        tmpInt = new Integer(sT.nextToken());
        f.addNeighbor(tmpInt);
      }
    }
        
    
/*
    for(Iterator i = inequalities.iterator(); i.hasNext();)
    {
      Inequality ineq = (Inequality)i.next();
      Facet f = new Facet(ineq.v,ineq.b);
      facets.add(f);
    }

    //find all facets and ridges and build the facet "graph"
    for(Iterator i = facets.iterator(); i.hasNext();)
    {
      //check each pair of facets for a potential ridge
      Facet f1 = (Facet)i.next();
      for(Iterator j = facets.iterator(); j.hasNext();)
      {
        Facet f2 = (Facet)j.next();
        if(f2.key != f1.key)
        {
        //<a,b,c> is the vector defining the line that is the 
        //intersection of f1 and f2
        //(d,e,f) is a point in the intersection
        double a = f1.normal[1]*f2.normal[2]-f1.normal[2]*f2.normal[1];
        double b = f1.normal[2]*f2.normal[0]-f1.normal[0]*f2.normal[2];
        double c = f1.normal[0]*f2.normal[1]-f1.normal[1]*f2.normal[0];
        double d = 0;
        double e = 0;
        double f = 0;
        if(c != 0)
        {
          d = (f2.normal[1]*f1.b-f1.normal[1]*f2.b)/c;
          e = (f1.normal[0]*f2.b-f2.normal[0]*f1.b)/c;
          f = 0;
        }
        if(b != 0)
        {
          d = (f1.normal[2]*f2.b-f2.normal[2]*f1.b)/b;
          f = (f2.normal[0]*f1.b-f1.normal[0]*f2.b)/b;
          e = 0;
        }
        if(a != 0)
        {
          e = (f2.normal[2]*f1.b-f1.normal[2]*f2.b)/a;
          f = (f1.normal[1]*f2.b-f2.normal[1]*f1.b)/a;
          d = 0;
        }
        boolean nonPerp = true;
        if(a == 0 && b == 0 && c == 0)
        {
          nonPerp = false;
        }

//System.out.println("d,e,f is: " + d + ", " + e + ", " + f);
        double t1 = Double.NEGATIVE_INFINITY;
        double t2 = Double.POSITIVE_INFINITY;

        if(nonPerp)
        {
        for(Iterator k = facets.iterator(); k.hasNext();)
        {
          Facet f3 = (Facet)k.next();
          if(f3.key != f1.key && f3.key != f2.key)
          {
            
          //see what changes f3 forces on t1 and t2
          double bot = a*f3.normal[0] + b*f3.normal[1] + c*f3.normal[2];
          if(bot != 0)
          {
            double top = f3.b - f3.normal[0]*d-f3.normal[1]*e-f3.normal[2]*f;
            double t = top/bot;
            double p1x;
            double p1y;
            double p1z;
            double p2x;
            double p2y;
            double p2z;
            //we need to do if statements to take care of /infty*0
            if(a == 0)
            {
              p1x = d;
            }
            else
            {
              p1x = d+a*t1;
            }
            if(a == 0)
            {
              p2x = d;
            }
            else
            {
              p2x = d+a*t2;
            }
            if(b == 0)
            {
              p1y = e;
            }
            else
            {
              p1y = e+b*t1;
            }
            if(b == 0)
            {
              p2y = e;
            }
            else
            {
              p2y = e+b*t2;
            }
            if(c == 0)
            {
              p1z = f;
            }
            else
            {
              p1z = f+c*t1;
            }
            if(c == 0)
            {
              p2z = f;
            }
            else
            {
              p2z = f+c*t1;
            }
            boolean p1good = false;
            boolean p2good = false;
            //check if the points corresponding to t1 and t2 satisfy the inequality for f3
            if(p1x*f3.normal[0]+p1y*f3.normal[1]+p1z*f3.normal[2] <= f3.b)
            {
              p1good = true;
            }
            if(p2x*f3.normal[0]+p2y*f3.normal[1]+p2z*f3.normal[2] <= f3.b)
            {
              p2good = true;
            }

            //update t1 and t2
            if(t <= t1 || t >= t2)
            {
              if(!p1good)
              {
                t1 = t2;
              }
            }
            else
            {
              //so t1 < t < t2
              if(p1good)
              {
                //so p2 must be bad
                t2 = t;
              }
              else
              {
                t1 = t;
              }
            }
          }
          }
        }
        if(t2 > t1)
        {
          //we have a non-empty ridge
          Point p = new Point(d,e,f);
          double[] v = new double[3];
          v[0] = a;
          v[1] = b;
          v[2] = c;
          Ridge r = new Ridge(f1,f2,p,v,t1,t2);
          f1.addRidge(r);
        }
        }
      }
      }
    }
*/

    //add the source image to each facet it is on
    for(Iterator i = facets.values().iterator(); i.hasNext();)
    {
      Facet f = (Facet)i.next();
      if(f.contains(source))
      {
        f.addSourceImage(source);
      }
    }

    //create the set of events
    for(Iterator i = facets.values().iterator(); i.hasNext();)
    {
      events.addAll(((Facet)i.next()).events);
    }


//System.out.println("Number of events is: " + events.size());
    while(!(events.isEmpty()))
    {
       Event E = (Event)events.first();

       Integer FKey = E.getFacet();
//System.out.println("F normal is: " + F.normal[0] + ", " + F.normal[1] + ", " + F.normal[2]);
       Integer FPrimeKey = E.getTargetFacet();
       Facet F = facets.get(FKey);
       Facet FPrime = facets.get(FPrimeKey);
//System.out.println("FPrime normal is: " + FPrime.normal[0] + ", " + FPrime.normal[1] + ", " + FPrime.normal[2]);
       F.events.remove(E);
       Point s = E.potentialNewSourceImage();

//System.out.println(s.x + ", " + s.y + ", " + s.z);
       
       FPrime.addSourceImage(s); //should also recompute events of FPrime
       events.clear(); //empties events
       for(Iterator i = facets.values().iterator(); i.hasNext();)
       {
         events.addAll(((Facet)i.next()).events);
       }
//System.out.println("Number of events is: " + events.size());
//System.out.println("Events are:");
/*for(Iterator i = events.iterator(); i.hasNext();)
{
Event e = (Event)i.next();
System.out.println(e.sourceImage.x + ", " + e.sourceImage.y + ", " + e.sourceImage.z + "-> " + e.r.f2.normal[0] + "," + e.r.f2.normal[1] + "," 
+e.r.f2.normal[2] + "-> " + e.radius + "-> " + e.angle);
}*/

    }

    for(Iterator i = facets.values().iterator(); i.hasNext();)
    {
      Facet f = (Facet)i.next();
      f.outputSourceImages();
    }

    //TODO: compute the foldout

  }
}
