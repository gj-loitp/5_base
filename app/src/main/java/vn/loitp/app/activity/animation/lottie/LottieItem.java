package vn.loitp.app.activity.animation.lottie;

public class LottieItem {
    private String name;
    private String pathAsset;

    public LottieItem(String name, String pathAsset) {
        this.name = name;
        this.pathAsset = pathAsset;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPathAsset() {
        return pathAsset;
    }

    public void setPathAsset(String pathAsset) {
        this.pathAsset = pathAsset;
    }
}
