package hcl.policing.digitalpolicingplatform.models.layoutHelper;

import android.view.View;
import android.widget.CompoundButton;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

//Created by Prateek
public class PropertiesBean implements Serializable {

    @Expose
    @SerializedName("enabled")
    private boolean enabled;
    @Expose
    @SerializedName("isNestedScroll")
    private boolean isNestedScroll;
    @Expose
    @SerializedName("scrollToPosition")
    private int scrollToPosition;
    @Expose
    @SerializedName("isAdjustViewBounds")
    private boolean isAdjustViewBounds;
    @Expose
    @SerializedName("scrollFlag")
    private String scrollFlag;
    @Expose
    @SerializedName("icon")
    private String icon;
    @Expose
    @SerializedName("image")
    private String image;
    @Expose
    @SerializedName("imageType")
    private String imageType;
    @Expose
    @SerializedName("scaleType")
    private String scaleType;
    @Expose
    @SerializedName("buttonSelector")
    private String buttonSelector;
    @Expose
    @SerializedName("unCheckColor")
    private String unCheckColor;
    @Expose
    @SerializedName("checkColor")
    private String checkColor;
    @Expose
    @SerializedName("behaviour")
    private String behaviour;
    @Expose
    @SerializedName("iconColorUnselected")
    private String iconColorUnselected;
    @Expose
    @SerializedName("iconColorSelected")
    private String iconColorSelected;
    @Expose
    @SerializedName("textColorUnselected")
    private String textColorUnselected;
    @Expose
    @SerializedName("textColorSelected")
    private String textColorSelected;
    @Expose
    @SerializedName("isFitSystemWindow")
    private boolean isFitSystemWindow;
    @Expose
    @SerializedName("keyboardAction")
    private String keyboardAction;
    @Expose
    @SerializedName("mendatry")
    private boolean mendatry;
    @Expose
    @SerializedName("radius")
    private int radius;
    @Expose
    @SerializedName("elevation")
    private int elevation;
    @Expose
    @SerializedName("drawableBottom")
    private String drawableBottom;
    @Expose
    @SerializedName("drawableTop")
    private String drawableTop;
    @Expose
    @SerializedName("drawableRight")
    private String drawableRight;
    @Expose
    @SerializedName("drawableLeft")
    private String drawableLeft;
    @Expose
    @SerializedName("backGround")
    private String backGround;
    @Expose
    @SerializedName("backGroundColor")
    private String backGroundColor;
    @Expose
    @SerializedName("orientation")
    private String orientation;
    @Expose
    @SerializedName("gravity")
    private String gravity;
    @Expose
    @SerializedName("layoutGravity")
    private String layoutGravity;
    @Expose
    @SerializedName("inputType")
    private String inputType;
    @Expose
    @SerializedName("visibility")
    private String visibility;
    @Expose
    @SerializedName("designType")
    private String designType;
    @Expose
    @SerializedName("hintAppearance")
    private String hintAppearance;
    @Expose
    @SerializedName("hintColor")
    private String hintColor;
    @Expose
    @SerializedName("hint")
    private String hint;
    @Expose
    @SerializedName("hintSize")
    private int hintSize;
    @Expose
    @SerializedName("errorMsg")
    private String errorMsg;
    @Expose
    @SerializedName("textStyle")
    private String textStyle;
    @Expose
    @SerializedName("textSize")
    private int textSize;
    @Expose
    @SerializedName("isAllCaps")
    private boolean isAllCaps;
    @Expose
    @SerializedName("textColor")
    private String textColor;
    @Expose
    @SerializedName("text")
    private String text;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("minLength")
    private int minLength;
    @Expose
    @SerializedName("maxLength")
    private int maxLength;
    @Expose
    @SerializedName("maxLines")
    private int maxLines;
    @Expose
    @SerializedName("boxColor")
    private String boxColor;
    @Expose
    @SerializedName("boxBottomRightRadius")
    private int boxBottomRightRadius;
    @Expose
    @SerializedName("boxBottomLeftRadius")
    private int boxBottomLeftRadius;
    @Expose
    @SerializedName("boxTopRightRadius")
    private int boxTopRightRadius;
    @Expose
    @SerializedName("boxTopLeftRadius")
    private int boxTopLeftRadius;
    @Expose
    @SerializedName("paddingBottom")
    private int paddingBottom;
    @Expose
    @SerializedName("paddingTop")
    private int paddingTop;
    @Expose
    @SerializedName("paddingRight")
    private int paddingRight;
    @Expose
    @SerializedName("paddingLeft")
    private int paddingLeft;
    @Expose
    @SerializedName("marginBottom")
    private int marginBottom;
    @Expose
    @SerializedName("marginTop")
    private int marginTop;
    @Expose
    @SerializedName("marginRight")
    private int marginRight;
    @Expose
    @SerializedName("marginLeft")
    private int marginLeft;
    @Expose
    @SerializedName("maxWidth")
    private int maxWidth;
    @Expose
    @SerializedName("maxHeight")
    private int maxHeight;
    @Expose
    @SerializedName("minWidth")
    private int minWidth;
    @Expose
    @SerializedName("minHeight")
    private int minHeight;
    @Expose
    @SerializedName("weight")
    private int weight;
    @Expose
    @SerializedName("width")
    private int width;
    @Expose
    @SerializedName("height")
    private int height;
    @Expose
    @SerializedName("id")
    private int id;

    private View.OnClickListener onClickListener;

    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean getIsNestedScroll() {
        return isNestedScroll;
    }

    public void setIsNestedScroll(boolean isNestedScroll) {
        this.isNestedScroll = isNestedScroll;
    }

    public int getScrollToPosition() {
        return scrollToPosition;
    }

    public void setScrollToPosition(int scrollToPosition) {
        this.scrollToPosition = scrollToPosition;
    }

    public boolean getIsAdjustViewBounds() {
        return isAdjustViewBounds;
    }

    public void setIsAdjustViewBounds(boolean isAdjustViewBounds) {
        this.isAdjustViewBounds = isAdjustViewBounds;
    }

    public String getScrollFlag() {
        return scrollFlag;
    }

    public void setScrollFlag(String scrollFlag) {
        this.scrollFlag = scrollFlag;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getScaleType() {
        return scaleType;
    }

    public void setScaleType(String scaleType) {
        this.scaleType = scaleType;
    }

    public String getButtonSelector() {
        return buttonSelector;
    }

    public void setButtonSelector(String buttonSelector) {
        this.buttonSelector = buttonSelector;
    }

    public String getUnCheckColor() {
        return unCheckColor;
    }

    public void setUnCheckColor(String unCheckColor) {
        this.unCheckColor = unCheckColor;
    }

    public String getCheckColor() {
        return checkColor;
    }

    public void setCheckColor(String checkColor) {
        this.checkColor = checkColor;
    }

    public String getBehaviour() {
        return behaviour;
    }

    public void setBehaviour(String behaviour) {
        this.behaviour = behaviour;
    }

    public String getIconColorUnselected() {
        return iconColorUnselected;
    }

    public void setIconColorUnselected(String iconColorUnselected) {
        this.iconColorUnselected = iconColorUnselected;
    }

    public String getIconColorSelected() {
        return iconColorSelected;
    }

    public void setIconColorSelected(String iconColorSelected) {
        this.iconColorSelected = iconColorSelected;
    }

    public String getTextColorUnselected() {
        return textColorUnselected;
    }

    public void setTextColorUnselected(String textColorUnselected) {
        this.textColorUnselected = textColorUnselected;
    }

    public String getTextColorSelected() {
        return textColorSelected;
    }

    public void setTextColorSelected(String textColorSelected) {
        this.textColorSelected = textColorSelected;
    }

    public boolean getIsFitSystemWindow() {
        return isFitSystemWindow;
    }

    public void setIsFitSystemWindow(boolean isFitSystemWindow) {
        this.isFitSystemWindow = isFitSystemWindow;
    }

    public String getKeyboardAction() {
        return keyboardAction;
    }

    public void setKeyboardAction(String keyboardAction) {
        this.keyboardAction = keyboardAction;
    }

    public boolean getMendatry() {
        return mendatry;
    }

    public void setMendatry(boolean mendatry) {
        this.mendatry = mendatry;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    public String getDrawableBottom() {
        return drawableBottom;
    }

    public void setDrawableBottom(String drawableBottom) {
        this.drawableBottom = drawableBottom;
    }

    public String getDrawableTop() {
        return drawableTop;
    }

    public void setDrawableTop(String drawableTop) {
        this.drawableTop = drawableTop;
    }

    public String getDrawableRight() {
        return drawableRight;
    }

    public void setDrawableRight(String drawableRight) {
        this.drawableRight = drawableRight;
    }

    public String getDrawableLeft() {
        return drawableLeft;
    }

    public void setDrawableLeft(String drawableLeft) {
        this.drawableLeft = drawableLeft;
    }

    public String getBackGround() {
        return backGround;
    }

    public void setBackGround(String backGround) {
        this.backGround = backGround;
    }

    public String getBackGroundColor() {
        return backGroundColor;
    }

    public void setBackGroundColor(String backGroundColor) {
        this.backGroundColor = backGroundColor;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getGravity() {
        return gravity;
    }

    public void setGravity(String gravity) {
        this.gravity = gravity;
    }

    public String getLayoutGravity() {
        return layoutGravity;
    }

    public void setLayoutGravity(String layoutGravity) {
        this.layoutGravity = layoutGravity;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getDesignType() {
        return designType;
    }

    public void setDesignType(String designType) {
        this.designType = designType;
    }

    public String getHintAppearance() {
        return hintAppearance;
    }

    public void setHintAppearance(String hintAppearance) {
        this.hintAppearance = hintAppearance;
    }

    public String getHintColor() {
        return hintColor;
    }

    public void setHintColor(String hintColor) {
        this.hintColor = hintColor;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getHintSize() {
        return hintSize;
    }

    public void setHintSize(int hintSize) {
        this.hintSize = hintSize;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getTextStyle() {
        return textStyle;
    }

    public void setTextStyle(String textStyle) {
        this.textStyle = textStyle;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public boolean getIsAllCaps() {
        return isAllCaps;
    }

    public void setIsAllCaps(boolean isAllCaps) {
        this.isAllCaps = isAllCaps;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getMaxLines() {
        return maxLines;
    }

    public void setMaxLines(int maxLines) {
        this.maxLines = maxLines;
    }

    public String getBoxColor() {
        return boxColor;
    }

    public void setBoxColor(String boxColor) {
        this.boxColor = boxColor;
    }

    public int getBoxBottomRightRadius() {
        return boxBottomRightRadius;
    }

    public void setBoxBottomRightRadius(int boxBottomRightRadius) {
        this.boxBottomRightRadius = boxBottomRightRadius;
    }

    public int getBoxBottomLeftRadius() {
        return boxBottomLeftRadius;
    }

    public void setBoxBottomLeftRadius(int boxBottomLeftRadius) {
        this.boxBottomLeftRadius = boxBottomLeftRadius;
    }

    public int getBoxTopRightRadius() {
        return boxTopRightRadius;
    }

    public void setBoxTopRightRadius(int boxTopRightRadius) {
        this.boxTopRightRadius = boxTopRightRadius;
    }

    public int getBoxTopLeftRadius() {
        return boxTopLeftRadius;
    }

    public void setBoxTopLeftRadius(int boxTopLeftRadius) {
        this.boxTopLeftRadius = boxTopLeftRadius;
    }

    public int getPaddingBottom() {
        return paddingBottom;
    }

    public void setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    public int getPaddingTop() {
        return paddingTop;
    }

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
    }

    public int getPaddingRight() {
        return paddingRight;
    }

    public void setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
    }

    public int getPaddingLeft() {
        return paddingLeft;
    }

    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public int getMarginBottom() {
        return marginBottom;
    }

    public void setMarginBottom(int marginBottom) {
        this.marginBottom = marginBottom;
    }

    public int getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
    }

    public int getMarginRight() {
        return marginRight;
    }

    public void setMarginRight(int marginRight) {
        this.marginRight = marginRight;
    }

    public int getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public int getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public CompoundButton.OnCheckedChangeListener getOnCheckedChangeListener() {
        return onCheckedChangeListener;
    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }
}
