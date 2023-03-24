package structure;

public class Segment implements Comparable<Segment>, Cloneable {

    private double x;
    private double y;
    private double xPrime;
    private double yPrime;

    public Segment(double x, double y, double xPrime, double yPrime) {
        if (y < yPrime || (y == yPrime && x <= xPrime)) {
            this.x = x;
            this.y = y;
            this.xPrime = xPrime;
            this.yPrime = yPrime;
        } else {
            this.x = xPrime;
            this.y = yPrime;
            this.xPrime = x;
            this.yPrime = y;
        }
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getxPrime() {
        return this.xPrime;
    }

    public void setxPrime(double xPrime) {
        this.xPrime = xPrime;
    }

    public double getyPrime() {
        return this.yPrime;
    }

    public void setyPrime(double yPrime) {
        this.yPrime = yPrime;
    }

    public double getMiddleY() {
        return (y + yPrime) / 2;
    }

    public int compareTo(Segment o) {
        if (o == null) throw new NullPointerException("the object to compare for compareTo is null");

        double c = Double.compare(y, o.getY());
        return (int) (c == 0 ? Double.compare(yPrime, o.getyPrime()) : c);
    }

    /**
     * opposed coordinates. All coordinates are opposed. Segment(1, 1, 1, 1) becomes (-1, -1, -1, -1).
     */
    public Segment oppose() {
        return new Segment(-x, -y, -xPrime, -yPrime);
    }

    /**
     * exchanged coordinates. X coordinates become Y coordinates and inversely. Segment(1, 2, 1, 2) becomes (2, 1, 2, 1).
     */
    public Segment exchange() {
        return new Segment(y, x, yPrime, xPrime);
    }

    // Override clone() method
    @Override
    public Segment clone() {
        return new Segment(x, y, xPrime, yPrime);
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

