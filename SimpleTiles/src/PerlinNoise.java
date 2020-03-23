import java.util.ArrayList;
import java.util.List;

public class PerlinNoise {
    double x;
    double a;
    double b;
    double M = 4294967296d;
    double A = 1664525d;
    double C = 1.0;
    double[] pos;
    double[][] result;

    public PerlinNoise() {
    }
    public double[] perlin(double amp, double wl, int width) {
        pos = new double[width];
        int inc = 0;
        x = 0;
        a = this.rand();
        b = this.rand();
        while (x < width) {
            if (x % wl == 0) {
                a = b;
                b = this.rand();
                pos[inc] = a*amp;
            }
            else {
                pos[inc] = this.interpolate(a, b, (x % wl) / wl) * amp;
            }
            inc++;
            x++;
        }
        return pos;
    }
    public double[][] generateNoise(double amp, double wl, int octaves, double divisor, int width) {
        result = new double[octaves][width];
        double[] newPos = new double[width];
;        for (int i=0; i<octaves; i++) {
            newPos = this.perlin(amp, wl, width);
            for (int j=0; j<newPos.length; j++) {
                result[i][j] = newPos[j];
            }
            amp = amp / divisor;
            wl = wl / divisor;
        }
        return result;
    }
    public List<Integer> combineNoise(double[][] pl) {
        List<Integer> newResult = new ArrayList<Integer>();
        
        for (int i=0,total=0,j=0; i<pl[0].length; i++) {
            total = 0;
            for (j=0; j<pl.length; j++) {
                total += pl[j][i];
            }
            newResult.add(total);
        }
        return newResult;
    }
    public double rand() {
        double Z = Math.floor(Math.random() * M);
        Z = ((A * Z + C) % M);
        return Z / M - 0.5d;
    }
    public double interpolate(double pa, double pb, double px) {
        double ft = px * Math.PI;
        double f = (1.0d - Math.cos(ft)) * 0.5d;
        return pa * (1.0d - f) + pb * f;
    }
}