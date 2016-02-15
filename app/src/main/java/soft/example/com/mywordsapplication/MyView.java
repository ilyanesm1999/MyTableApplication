package soft.example.com.mywordsapplication;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

/**
 * Created by admin on 27.12.2015.
 */
public class MyView extends Button {


    public String letter;
    Context context;
    private final int VIEW_WIDTH = 80, VIEW_HEIGHT = VIEW_WIDTH;
    public boolean touched = false;
    public boolean filled = false;


    public MyView(Context context)
    {
        super(context);
        this.context = context;
        setWidth(VIEW_WIDTH);
        setHeight(VIEW_HEIGHT);
    }



    public void setLetter(String letter){
        this.letter = letter;
        setText(letter);
    }




}
