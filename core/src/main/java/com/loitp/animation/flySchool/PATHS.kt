package com.loitp.animation.flySchool

enum class PATHS {
    // S Shapes
    S_BOTTOM_LEFT,
    S_INVERTED_BOTTOM_RIGHT,
    S_TOP_RIGHT,
    S_INVERTED_TOP_LEFT,

    // Lines
    LINE_DIAGONAL_BOTTOM_LEFT,
    LINE_DIAGONAL_BOTTOM_RIGHT,
    LINE_DIAGONAL_TOP_LEFT,
    LINE_DIAGONAL_TOP_RIGHT,
    LINE_MIDDLE_TOP,
    LINE_MIDDLE_BOTTOM,
    LINE_MIDDLE_LEFT,
    LINE_MIDDLE_RIGHT;

    private var mFlyBluePrint: FlyBluePrint? = null

    fun getmFlyBluePrint(): FlyBluePrint? {
        if (mFlyBluePrint == null) {
            mFlyBluePrint = when (this) {
                S_BOTTOM_LEFT -> FlyBluePrint(
                    FPoint(0f, 1f),
                    FlyPath.getBeizerPath(
                        FPoint(1.4f, -0.35f),
                        FPoint(-0.5f, -0.8f),
                        FPoint(1f, -1f)
                    )
                )
                S_INVERTED_BOTTOM_RIGHT -> FlyBluePrint(
                    FPoint(1f, 1f),
                    FlyPath.getBeizerPath(
                        FPoint(-1.5f, -0.35f),
                        FPoint(0.5f, -0.8f),
                        FPoint(-1f, -1f)
                    )
                )
                S_TOP_RIGHT -> FlyBluePrint(
                    FPoint(1f, 0f),
                    FlyPath.getBeizerPath(
                        FPoint(-1.8f, 0.35f),
                        FPoint(0.5f, 0.8f),
                        FPoint(-1f, 0.9f)
                    )
                )
                S_INVERTED_TOP_LEFT -> FlyBluePrint(
                    FPoint(0f, 0f),
                    FlyPath.getBeizerPath(
                        FPoint(1.5f, 0.35f),
                        FPoint(-0.5f, 0.8f),
                        FPoint(1f, 0.9f)
                    )
                )
                LINE_DIAGONAL_BOTTOM_LEFT -> FlyBluePrint(
                    FPoint(0f, 1f),
                    FlyPath.getSimpleLinePath(FPoint(1f, 0f))
                )
                LINE_DIAGONAL_BOTTOM_RIGHT -> FlyBluePrint(
                    FPoint(1f, 1f),
                    FlyPath.getSimpleLinePath(FPoint(0f, 0f))
                )
                LINE_DIAGONAL_TOP_LEFT -> FlyBluePrint(
                    FPoint(0f, 0f),
                    FlyPath.getSimpleLinePath(FPoint(1f, 1f))
                )
                LINE_DIAGONAL_TOP_RIGHT -> FlyBluePrint(
                    FPoint(1f, 0f),
                    FlyPath.getSimpleLinePath(FPoint(0f, 1f))
                )
                LINE_MIDDLE_TOP -> FlyBluePrint(
                    FPoint(0.5f, 0f),
                    FlyPath.getSimpleLinePath(FPoint(0.5f, 1f))
                )
                LINE_MIDDLE_BOTTOM -> FlyBluePrint(
                    FPoint(0.5f, 1f),
                    FlyPath.getSimpleLinePath(FPoint(0.5f, 0f))
                )
                LINE_MIDDLE_LEFT -> FlyBluePrint(
                    FPoint(0f, 0.5f),
                    FlyPath.getSimpleLinePath(FPoint(1f, 0.5f))
                )
                LINE_MIDDLE_RIGHT -> FlyBluePrint(
                    FPoint(1f, 0.5f),
                    FlyPath.getSimpleLinePath(FPoint(0f, 0.5f))
                )
            }
        }
        return mFlyBluePrint
    }
}
