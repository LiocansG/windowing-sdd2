package structure;

public class Segment {
    private final int x;
    private final int y;
    private final int xprime;
    private final int yprime;

    public Segment(int x, int y, int xprime, int yprime) {
        this.x = x;
        this.y = y;
        this.xprime = xprime;
        this.yprime = yprime;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getXPrime() {
        return this.xprime;
    }

    public int getYPrime() {
        return this.yprime;
    }
}

