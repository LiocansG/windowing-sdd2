package structure;

public class Segment implements Comparable<Segment> {

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

    public int compareTo(Segment o) {
        double c = Double.compare(y, o.getY());
        return (int) (c == 0 ? Double.compare(yPrime, o.getyPrime()) : c);
    }

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

