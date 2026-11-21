package engine.utils.helpers;

import java.io.Serializable;

public class DoubleVector implements Serializable {

    // region Properties
    public final double x;
    public final double y;
    public final double module;
    // endregion

    // region Constructors
    public DoubleVector(double x, double y) {
        this.x = x;
        this.y = y;
        this.module = Math.hypot(x, y);
    }

    public DoubleVector(double x, double y, double fixedModule) {
        double mag = Math.hypot(x, y);
        if (mag == 0 || fixedModule == 0) {
            this.x = 0;
            this.y = 0;
            this.module = 0;
        } else {
            double s = fixedModule / mag;
            this.x = x * s;
            this.y = y * s;
            this.module = fixedModule;
        }
    }

    public DoubleVector(DoubleVector v, double fixedModule) {
        double x, y;

        x = v.x;
        y = v.y;

        if (v.module <= 0 || fixedModule <= 0) {
            this.x = 0;
            this.y = 0;
            this.module = 0;
        } else {
            double s = fixedModule / v.module;
            this.x = x * s;
            this.y = y * s;
            this.module = fixedModule;
        }
    }

    public DoubleVector(DoubleVector v) {
        this.x = v.x;
        this.y = v.y;
        this.module = v.module;
    }
    // endregion

    // *** PUBLICS ***

    // region adders (add***)
    public DoubleVector add(DoubleVector v) {
        return new DoubleVector(this.x + v.x, this.y + v.y);
    }

    public DoubleVector addScaled(DoubleVector v, double s) {
        // Return: this + v*scaleFactor
        return new DoubleVector(this.x + v.x * s, this.y + v.y * s);
    }
    // endregion

    public DoubleVector distance(DoubleVector v) {
        return new DoubleVector(v.x - this.x, v.y - this.y);
    }

    public DoubleVector distance(double x, double y) {
        return new DoubleVector(x - this.x, y - this.y);
    }

    public double getModule() {
        return this.module;
    }

    public DoubleVector rotated(double angle) {
        double angleInRadians = Math.toRadians(angle);
        double x = this.x * Math.cos(angleInRadians) - this.y * Math.sin(angleInRadians);
        double y = this.x * Math.sin(angleInRadians) + this.y * Math.cos(angleInRadians);

        return new DoubleVector(x, y);
    }

    public DoubleVector scale(double s) {
        return new DoubleVector(this.x * s, this.y * s);
    }

    @Override
    public String toString() {

        return "("
                + String.format("%.3f", this.x) + " : "
                + String.format("%.3f", this.y) + ")";
    }

    public DoubleVector withModule(double fixedModule) {
        return new DoubleVector(this, fixedModule);
    }
}
