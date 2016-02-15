package soft.example.com.mywordsapplication;


import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by admin on 27.12.2015.
 */
public class MyGame {
    HashMap<String, Integer> words = new HashMap<String, Integer>();
    int width,height;
    public MyView[][] views;

    public MyGame(MyDBHelper dbh,int w, int h) {
        views = new MyView[h][w];
        words = dbh.getAllWords();
        width = w;
        height = h;
    }

    public String getRandomWord(int maxLen){
        boolean f = true;
        String s = "";
        Random generator = new Random();
        Object[] keys = words.keySet().toArray();

        while(f) {
            s = (String) keys[generator.nextInt(words.size() - 1)];
            if (s.length() <= maxLen) {
                f = false;
            }
        }
        return s;

    }

    public boolean isWord(String s){
        return words.containsKey(s);
    }

    public void fillWords(int i, int j, String word, int position){
        Random generator = new Random();




        views[i][j].setLetter(Character.toString(word.charAt(position)));
        // предусмотреть запоминание пути
        views[i][j].filled = true;
        position++;

        // определяем есть ли соседняя пустая
        boolean goodGoExist = false;
        if((i != 0) && !views[i-1][j].filled)goodGoExist = true;
        if((j != width-1) && !views[i][j+1].filled)goodGoExist = true;
        if((i != height-1) && !views[i+1][j].filled)goodGoExist = true;
        if((j != 0) && !views[i][j-1].filled)goodGoExist = true;
        if(goodGoExist) {
//            наугад выбираем соседнюю пустую
            boolean goodGo = false;
            while (!goodGo) {
                switch (generator.nextInt(4)) {
                    case 0:
                        i--;
                        break; // up
                    case 1:
                        j++;
                        break; // right
                    case 2:
                        i++;
                        break; // down
                    case 3:
                        j--;
                        break; // left
                }
                if ((i < height) && (i >= 0) && (j < width) && (j >= 0) && (!views[i][j].filled)) {
                    goodGo = true;
                }
            }
        }else{
            // ищем первую пустую клетку
            for(int ii=0;ii < height;ii++) {
                for(int jj=0;jj < width;jj++) {
                    if(!views[ii][jj].filled){
                        goodGoExist = true;
                        i = ii;
                        j = jj;
                    }
                }
            }
        }

        if(!goodGoExist){
            // совсем некуда пихать
            return;
        }

        if(position >= word.length()){
            // определить максимальн возм длину слова ?????????? дыркоопределитель
            int maxLen = 10;
            fillWords(i,j,getRandomWord(maxLen),0);
        }else{
            fillWords(i,j,word,position);
        }


    }


}
