import java.util.*;

public class Inequality
{
  private Vector v;
  private double b;

  public Inequality(String s)
  {
    v = new Vector();
    StringTokenizer sT = new StringTokenizer(s);
    Double tmpD = new Double(sT.nextToken());
    b = tmpD.doubleValue();

    while(sT.hasMoreTokens())
    {
      tmpD = new Double(sT.nextToken());
      double d = tmpD.doubleValue();
      d = -d;
      tmpD = new Double(d);
      v.add(tmpD);
    }
  }
}
