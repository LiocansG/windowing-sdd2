package structure;

public class Segment {
    private final double x;
    private final double y;
    private final double xPrime;
    private final double yPrime;

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

    public double getMiddleY(){return (y + yPrime) / 2;}

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

