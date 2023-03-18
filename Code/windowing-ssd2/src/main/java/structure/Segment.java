package structure;

public class Segment implements Comparable<Segment>, Cloneable {


    private double x;
    private double y;
    private double xPrime;
    private double yPrime;

    public Segment(double x, double y, double xPrime, double yPrime) {
        if(y < yPrime || (y == yPrime && x <= xPrime)){
            this.x = x;
            this.y = y;
            this.xPrime = xPrime;
            this.yPrime = yPrime;
        }else{
            this.x = xPrime;
            this.y = yPrime;
            this.xPrime = x;
            this.yPrime = y;
        }
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getxPrime() {
        return this.xPrime;
    }

    public double getyPrime() {
        return this.yPrime;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setxPrime(int xPrime) {
        this.xPrime = xPrime;
    }

    public void setyPrime(int yPrime) {
        this.yPrime = yPrime;
    }

    public double getMiddleY(){return (y + yPrime) / 2;}

    public int compareTo(Segment o) {
        if(o == null) throw new NullPointerException("the object to compare for compareTo is null");

        double c = Double.compare(y, o.getY());
        return  (int) (c == 0 ? Double.compare(yPrime, o.getyPrime()) :c);
    }

    /**
     * opposed coordinates. All coordinates are opposed. Segment(1, 1, 1, 1) becomes (-1, -1, -1, -1).
     */
    public void oppose() {
        x *= -1;
        xPrime *= -1;
        y *= -1;
        yPrime *= -1;
    }

    /**
     * exchanged coordinates. X coordinates become Y coordinates and inversely. Segment(1, 1, 2, 2) becomes (2, 2, 1, 1).
     */
    public void exchange() {
        double tempyPrime = yPrime;
        double tempy = y;

        yPrime = xPrime;
        xPrime = tempyPrime;
        y = x;
        x = tempy;

    }

    // Override clone() method
    @Override
    public Segment clone() {
        Segment cloned = null;
        try {
            cloned = (Segment) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return cloned;
    }

    @Override
    public String toString() {
        return "Segment{" +
                "x=" + x +
                ", y=" + y +
                ", xPrime=" + xPrime +
                ", yPrime=" + yPrime +
                '}';
    }
}

