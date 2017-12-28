package vn.loitp.views.animation.flyschool;

import android.graphics.Path;

public abstract class FlyPath {
    public static SingleLinePath getSimpleLinePath(FPoint mFPoint) {
        return new SingleLinePath(mFPoint);
    }

    public static BeizerPath getBeizerPath(FPoint mFPoint1, FPoint mFPoint2, FPoint mFPoint3) {
        return new BeizerPath(mFPoint1, mFPoint2, mFPoint3);
    }

    public static MultipleLinePath getMultipleLinePath(FPoint... mFPoints) {
        if (mFPoints == null) {
            throw new IllegalArgumentException("Passed FPoints should not be null");
        }

        MultipleLinePath multipleLinePath = null;
        if (mFPoints.length > 0) {
            multipleLinePath = new MultipleLinePath();
            for (FPoint fPoint : mFPoints) {
                multipleLinePath.addFPoint(fPoint);
            }
        }

        return multipleLinePath;

    }

    /**
     * @param mOrigin : Origin point where the PATH should start
     * @param width   : Width of the {@link ShapeFlyer} in pixels
     * @param height  : Height of the {@link ShapeFlyer} in pixels
     *                <p>
     *                Should return the path/trajectory of the animation.
     *                Please refer to {@link BeizerPath} and {@link SingleLinePath} for examples
     */
    public abstract Path getPath(FPoint mOrigin, float width, float height);
}
