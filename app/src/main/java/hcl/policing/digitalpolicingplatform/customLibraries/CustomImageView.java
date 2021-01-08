package hcl.policing.digitalpolicingplatform.customLibraries;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageView;

import com.squareup.picasso.Picasso;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;
import hcl.policing.digitalpolicingplatform.utils.Base64ToBitmapUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class CustomImageView extends AppCompatImageView {

    private CustomCommonProperties customCommonProperties;

    public CustomImageView(Context context) {
        super(context);
        customCommonProperties = new CustomCommonProperties();
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void SetImageView(Context context, PropertiesBean imageViewDTO) {
        //this.context = context;
        try {
            LinearLayout.LayoutParams layoutparams;
            layoutparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            setLayoutParams(layoutparams);

            //To Set Width
            if (imageViewDTO.getWidth() != 0) {
                customCommonProperties.setWidth(context, this, imageViewDTO.getWidth());
            }

            //Set Height
            if (imageViewDTO.getHeight() != 0) {
                customCommonProperties.setHeight(context, this, imageViewDTO.getHeight());
            }

            //Set Weight
            if (imageViewDTO.getWeight() != 0) {
                customCommonProperties.setWeight(context, this, imageViewDTO.getWeight());
            }

            //To set Layout Gravity
            if (imageViewDTO.getLayoutGravity() != null && !imageViewDTO.getLayoutGravity().equalsIgnoreCase("")) {
                customCommonProperties.setLayoutGravity(context, this, imageViewDTO.getLayoutGravity());
            }

            //Set Margin
            if (imageViewDTO.getMarginLeft() != 0 || imageViewDTO.getMarginRight() != 0 || imageViewDTO.getMarginTop() != 0 || imageViewDTO.getMarginBottom() != 0) {
                customCommonProperties.setMargins(context, this, imageViewDTO.getMarginLeft(), imageViewDTO.getMarginTop(), imageViewDTO.getMarginRight(), imageViewDTO.getMarginBottom());
            }

            //To set ID
            if (imageViewDTO.getId() != 0) {
                customCommonProperties.setID(context, this, imageViewDTO.getId());
            }

            // To srt PADDING
            if (imageViewDTO.getPaddingLeft() != 0 || imageViewDTO.getPaddingRight() != 0 || imageViewDTO.getPaddingTop() != 0 || imageViewDTO.getPaddingBottom() != 0) {
                customCommonProperties.setPadding(context, this, imageViewDTO.getPaddingLeft(), imageViewDTO.getPaddingTop(), imageViewDTO.getPaddingRight(), imageViewDTO.getPaddingBottom());
            }

            //To set Visibility
            if (imageViewDTO.getVisibility() != null && !imageViewDTO.getVisibility().equalsIgnoreCase("")) {
                customCommonProperties.setVisibility(context, this, imageViewDTO.getVisibility().toLowerCase());
            }

            //Set drawable background
            if (imageViewDTO.getBackGround() != null && !imageViewDTO.getBackGround().equalsIgnoreCase("")) {
                customCommonProperties.setBackgroundDrawable(context, this, imageViewDTO.getBackGround());
            }

            //Set image Resource
            if (imageViewDTO.getIcon() != null && !imageViewDTO.getIcon().equalsIgnoreCase("")) {
                setImageResource(context, this, imageViewDTO.getIcon());
            }

            if (imageViewDTO.getImageType() != null && !imageViewDTO.getImageType().equalsIgnoreCase("")) {
                setImage(context, this, imageViewDTO.getImageType(), imageViewDTO.getImage());
            }

            if (imageViewDTO.getIsAdjustViewBounds()) {
                setAdjustViewBounds(context, this, true);
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomImageView.class, "");
        }
    }

    public void setImageResource(Context context, CustomImageView view, String drawable) {
        try {
            switch (drawable.toLowerCase()) {
                case "mic":
                    view.setImageResource(R.drawable.ic_mic);
                    break;
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomImageView.class, "setImageResource");
        }
    }

    public void setAdjustViewBounds(Context context, CustomImageView view, boolean flag) {
        try {
            view.setAdjustViewBounds(flag);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomImageView.class, "setAdjustViewBounds");
        }
    }

    public void setImage(Context context, CustomImageView view, String type, String image) {
        try {
            switch (type.toLowerCase()) {
                case "base64":
                    if (image != null && !image.equalsIgnoreCase("")) {
                        setImageBitmap(Base64ToBitmapUtil.base64ToBitmap(image));
                    }
                    break;

                case "url":
                    if (image != null && !image.equalsIgnoreCase("")) {
                        Picasso.get().load(image).into(view);
                    }
                    break;
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomImageView.class, "setImage");
        }
    }
}
