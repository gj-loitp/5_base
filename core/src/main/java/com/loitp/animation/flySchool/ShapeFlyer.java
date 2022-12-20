package com.loitp.animation.flySchool;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.loitpcore.R;

import java.util.ArrayList;
import java.util.Random;

import kotlin.Suppress;

//https://www.desmos.com/calculator/cahqdxeshd
//21.12.2020 try to convert kotlin but failed
public class ShapeFlyer extends RelativeLayout {

    private ArrayList<Path> mPaths;
    private ArrayList<FlyBluePrint> mFlyBluePrints;
    private int mShapeWidth;
    private int mShapeHeight;

    private boolean isAlphaEnabled, isScaleEnabled;
    private float mFromScale = 1f, mToScale = 0.6f, mFromAlpha = 1f, mToAlpha = 0f;

    public ShapeFlyer(Context context) {
        super(context);
        init(null);
    }

    public ShapeFlyer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ShapeFlyer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Suppress(names = "unused")
    public ShapeFlyer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attributeSet) {
        mShapeWidth = Utils.dpToPx(50);
        mShapeHeight = Utils.dpToPx(50);
        if (attributeSet != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.ShapeFlyer);
            mShapeWidth = typedArray.getDimensionPixelSize(R.styleable.ShapeFlyer_shape_width, mShapeWidth);
            mShapeHeight = typedArray.getDimensionPixelSize(R.styleable.ShapeFlyer_shape_height, mShapeHeight);
            isAlphaEnabled = typedArray.getBoolean(R.styleable.ShapeFlyer_enable_alpha, false);
            isScaleEnabled = typedArray.getBoolean(R.styleable.ShapeFlyer_enable_scale, false);
            mFromAlpha = typedArray.getFloat(R.styleable.ShapeFlyer_from_alpha, 1f);
            mToAlpha = typedArray.getFloat(R.styleable.ShapeFlyer_to_alpha, 0f);
            mFromScale = typedArray.getFloat(R.styleable.ShapeFlyer_from_scale, 1f);
            mToScale = typedArray.getFloat(R.styleable.ShapeFlyer_to_scale, 0.5f);
            typedArray.recycle();
        }
    }

    public void addPath(FlyBluePrint flyBluePrint) {
        if (flyBluePrint != null) {
            if (mFlyBluePrints == null) {
                mFlyBluePrints = new ArrayList<>();
            }

            mFlyBluePrints.add(flyBluePrint);
        }
    }

    @Suppress(names = "unused")
    public void clearPaths() {
        if (mFlyBluePrints != null) {
            mFlyBluePrints.clear();
        }

        if (mPaths != null) {
            mPaths.clear();
        }
    }

    public void addPath(PATHS definedPath) {
        if (definedPath != null) {
            FlyBluePrint flyBluePrint = definedPath.getmFlyBluePrint();
            if (flyBluePrint != null) {
                addPath(flyBluePrint);
            }
        }
    }

    private void createAndAddPath(FlyBluePrint flyBluePrint) {
        if (flyBluePrint != null) {
            float w = getWidth(), h = getHeight();
            if (mPaths == null) {
                mPaths = new ArrayList<>();
            }

            Path path = flyBluePrint.getPath(w, h);
            mPaths.add(path);
        }
    }

    private void initPaths() {
        if (mFlyBluePrints != null) {
            for (FlyBluePrint flyBluePrint : mFlyBluePrints) {
                createAndAddPath(flyBluePrint);
            }
        }
    }

    private Path getRandomPath() {
        return mPaths.get(new Random().nextInt(mPaths.size()));
    }

    /**
     * @param drawable     : Drawable to be animated along the given path(s)
     * @param flyBluePrint : BluePrint to generate Path
     *                     You should use a vector drawable for better performance
     */
    public void startAnimation(int drawable, FlyBluePrint flyBluePrint) {
        float w = getWidth(), h = getHeight();
        startAnimation(drawable, flyBluePrint.getPath(w, h));
    }

    /**
     * @param drawable : Drawable to be animated along the given path(s)
     * @param path     : Path along which the animation should happen
     *                 You should use a vector drawable for better performance
     */
    private void startAnimation(int drawable, Path path) {
        initPaths();
        View shapeView;

        shapeView = new ShapeView(getContext());

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.height = mShapeHeight;
        layoutParams.width = mShapeWidth;
        shapeView.setLayoutParams(layoutParams);
        ((ShapeSetter) shapeView).setShape(drawable);
        addView(shapeView);

        ValueAnimator pathAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        pathAnimator.setDuration(5000);


        final Path finalPath = path;
        final View finalShapeView = shapeView;
        pathAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            final float[] point = new float[2];

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float val = animation.getAnimatedFraction();
                PathMeasure pathMeasure = new PathMeasure(finalPath, false);
                pathMeasure.getPosTan(pathMeasure.getLength() * val, point, null);
                finalShapeView.setX(point[0]);
                finalShapeView.setY(point[1]);
                if (isAlphaEnabled) {
                    finalShapeView.setAlpha(mFromAlpha + (mToAlpha - mFromAlpha) * val);
                }

                if (isScaleEnabled) {
                    finalShapeView.setScaleX(mFromScale + (mToScale - mFromScale) * val);
                    finalShapeView.setScaleY(mFromScale + (mToScale - mFromScale) * val);
                }
            }
        });

        pathAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                //do nothing
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                removeView(finalShapeView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                //do nothing
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                //do nothing
            }
        });

        pathAnimator.start();
    }

    private void startAnimation(ImgObject imgObject, Path path, int drawableRes) {
        if (imgObject == null) {
            return;
        }
        initPaths();
        View shapeView;
        shapeView = new LFlySchoolView(getContext());

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.height = mShapeHeight;
        layoutParams.width = mShapeWidth;
        shapeView.setLayoutParams(layoutParams);
        ((ShapeSetter) shapeView).setShape(imgObject, drawableRes);
        addView(shapeView);

        ValueAnimator pathAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        pathAnimator.setDuration(5000);

        final Path finalPath = path;
        final View finalShapeView = shapeView;
        pathAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            final float[] point = new float[2];

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float val = animation.getAnimatedFraction();
                PathMeasure pathMeasure = new PathMeasure(finalPath, false);
                pathMeasure.getPosTan(pathMeasure.getLength() * val, point, null);
                finalShapeView.setX(point[0]);
                finalShapeView.setY(point[1]);
                if (isAlphaEnabled) {
                    finalShapeView.setAlpha(mFromAlpha + (mToAlpha - mFromAlpha) * val);
                }

                if (isScaleEnabled) {
                    finalShapeView.setScaleX(mFromScale + (mToScale - mFromScale) * val);
                    finalShapeView.setScaleY(mFromScale + (mToScale - mFromScale) * val);
                }
            }
        });

        pathAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                //do nothing
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                removeView(finalShapeView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                //do nothing
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                //do nothing
            }
        });

        pathAnimator.start();
    }

    /**
     * @param drawable : Drawable to be animated along the given path(s)
     *                 You should use a vector drawable for better performance
     */
    public void startAnimation(int drawable) {
        initPaths();
        startAnimation(drawable, getRandomPath());
    }

    public void startAnimation(ImgObject imgObject, int drawableRes) {
        initPaths();
        startAnimation(imgObject, getRandomPath(), drawableRes);
    }

    public void release() {
        removeAllViews();
        if (mPaths != null) {
            mPaths.clear();
        }
    }
}
