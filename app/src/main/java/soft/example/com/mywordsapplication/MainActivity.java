package soft.example.com.mywordsapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by admin on 27.12.2015.
 */
public class MainActivity extends Activity {

    int row_count = 5,col_count = 5;
    MyGame game;
    MyDBHelper dbhelper;
    TableLayout tbl;
    TableRow tr;
    ListView listFounded;
    //ArrayList<MyView> mvs = new ArrayList<MyView>();
    MyView[][] mvs = new MyView[row_count][col_count];
    String touchedWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbhelper = new MyDBHelper(this);

        game = new MyGame(dbhelper,row_count,col_count);

        setContentView(R.layout.main_activity);

        tbl = (TableLayout)findViewById(R.id.myGameTableLetters);
        tbl.setFocusable(true);
        tbl.setFocusableInTouchMode(true);
        //listFounded = (ListView)findViewById(R.id.myListFoundedWords);

        drawGameField();
        game.fillWords(0, 0, game.getRandomWord(row_count*col_count),0);
        Log.i("mytag", "Hello 888");
    }


    private void drawGameField(){
        MyView mv;

        //tbl.splitMotionEvents();
        for(int i=0;i<row_count;i++){
            tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tr.setFocusable(true);
            tr.setFocusableInTouchMode(true);
            for(int j=0;j<col_count;j++){
                mv = new MyView(this);
                // set
                mv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });

                mv.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        int x = (int)event.getRawX();
                        int y = (int)event.getRawY();
                        MyView me = (MyView)v;
                        for(int ii=0;ii < row_count;ii++) {
                            for(int jj=0;jj < col_count;jj++) {
                                if ((!mvs[ii][jj].touched) && (mvs[ii][jj] != me) && inViewInBounds(mvs[ii][jj], x, y)) {
                                    mvs[ii][jj].dispatchTouchEvent(event);
                                }
                            }
                        }
                        if(event.getAction() == MotionEvent.ACTION_DOWN){
                               touchedWord = me.letter;
                                me.touched = true;
                        }
                        if(event.getAction() == MotionEvent.ACTION_UP){
                            //Log.i("mytag", "touched word is " + touchedWord);
                            if(game.isWord(touchedWord)){
                                Log.i("mytag", " GOOD touched word is " + touchedWord);


                                Toast.makeText(MainActivity.this, "Вы нашли слово-"+touchedWord, Toast.LENGTH_LONG).show();

                            }else{
                                Log.i("mytag", " NOT WORD touched word is " + touchedWord);

                                Toast.makeText(MainActivity.this, "Нет такого слова-"+touchedWord, Toast.LENGTH_LONG).show();
                            }

                            for(int ii=0;ii < row_count;ii++) {
                                for(int jj=0;jj < col_count;jj++) {
                                    mvs[ii][jj].touched = false;
                                }
                            }
                        }
                        if(event.getAction() == MotionEvent.ACTION_MOVE){
                            if(!me.touched){
                                touchedWord += me.letter;
                                me.touched = true;
                            }
                        }


                        return false;
                    }
                });
                // \set
                //mv.setLetter(String.valueOf(k));k++;

                mv.setFocusable(true);
                mv.setFocusableInTouchMode(true);
            tr.addView(mv);
            mvs[i][j] = mv;
                                      }
            tbl.addView(tr);
        }
        game.views = mvs;

    }

    Rect outRect = new Rect();
    int[] location = new int[2];

    private boolean inViewInBounds(View view, int x, int y){
        view.getDrawingRect(outRect);
        view.getLocationOnScreen(location);
        outRect.offset(location[0], location[1]);
        return outRect.contains(x, y);
    }


}
