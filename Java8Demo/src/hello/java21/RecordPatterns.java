package hello.java21;

enum Color {RED, GREEN, BLUE}

record Point(int x, int y) {
}

record ColoredPoint(Point point, Color color) {
}

record RandomPoint(ColoredPoint cp) {
}

public class RecordPatterns {

    // Before
    public static int beforeRecordPattern(Object obj) {
        int sum = 0;
        if (obj instanceof Point p) {
            int x = p.x();
            int y = p.y();
            sum = x + y;
        }
        return sum;
    }

    // After: destructure instance
    public static int afterRecordPattern(Object obj) {
        if (obj instanceof Point(int x, int y)) {
            return x + y;
        }
        return 0;
    }

    public static Color getRandomPointColor(RandomPoint r) {
        if (r instanceof RandomPoint(ColoredPoint cp)) {
            return cp.color();
        }
        return null;
    }
}

