package vn.puresolutions.livestarv3.activity.homescreen.model.idolcategory;

/**
 * Created by loitp on 01-12-2015.
 */
public class ModelIdolCategoryDetail {
    //TODO if change type, should take a look in IdolCategoryAdapter.class & FrmHome.class
    public final static String TYPE_ALL = "9ac8fc48-86f2-11e7-b556-0242ac110005";
    public final static String TYPE_NGHESY = "1";
    public final static String TYPE_DJ = "2";
    public final static String TYPE_CARTOON = "9cabae85-80d2-11e7-b0f0-6057187c6b36";
    public final static String TYPE_HOT_IDOL = "9cabad29-80d2-11e7-b0f0-6057187c6b36";
    public final static String TYPE_HOT_BOY = "9caba9ec-80d2-11e7-b0f0-6057187c6b36";
    public final static String TYPE_HOT_GIRL = "9cab8105-80d2-11e7-b0f0-6057187c6b36";

    private String type;
    private String name;
    private boolean isChecked;

    public ModelIdolCategoryDetail() {
    }

    public ModelIdolCategoryDetail(String type, String name, boolean isChecked) {
        this.type = type;
        this.name = name;
        this.isChecked = isChecked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return getName();
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
