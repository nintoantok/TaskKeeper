package gyri.taskkeeper.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by CWL on 06-01-2015.
 */
public class TimeLeftView extends View {
    private Paint _CirclePaint;
    private Paint _CircleStrokePaint;
    private RectF _CircleArc;


    //Attributes
    private int _CircleRadius;
    private int _CircleFillColor;
    private int _CircleStrokeColor;
    private int _CircleStartAngle;
    private int _CircleEndAngle;

    public TimeLeftView(Context context, AttributeSet attrs){
            super(context,attrs);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
