package hcl.policing.digitalpolicingplatform.utils;

import android.content.Context;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import hcl.policing.digitalpolicingplatform.activities.sketch.SketchActivity;
import hcl.policing.digitalpolicingplatform.customLibraries.MovableImageView;

public class ChoiceDragListener implements View.OnDragListener {

    Context context;

    public ChoiceDragListener(Context context){
        this.context = context;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        //handle drag events
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                //no action necessary
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                //no action necessary
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                //no action necessary
                break;
            case DragEvent.ACTION_DROP:
                //handle the dragged view being dropped over a drop view
                View view = (View) event.getLocalState();
                ImageView dropped = (ImageView) view;

                MovableImageView imageView = new MovableImageView(context);
                imageView.setBackground(dropped.getDrawable());
                ViewGroup.LayoutParams params = imageView.getLayoutParams();
                if (params != null) {
                    params.width = dropped.getWidth();
                    params.height = dropped.getHeight();
                }
                imageView.requestLayout();
                imageView.setX(event.getX());
                imageView.setY(event.getY());
                //viewGroup.addView(imageButton);
                //ViewGroup owner = (ViewGroup) view.getParent();
                //owner.removeView(view);
                SketchActivity.rlDrop.addView(imageView);
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                //no action necessary
                break;
            default:
                break;
        }
        return true;
    }
}
