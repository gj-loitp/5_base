package vn.loitp.app.activity.customviews.progress.segmentedProgressBar

/**
 * Returns a list with random size between 1 and 15
 * With Page {index} strings
 */
fun dataSource(): List<Int> {
    return List((1..5).random()) { index -> index }
}
