package com.example.test2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MoveBoarder extends SurfaceView implements SurfaceHolder.Callback {

    TestThread testThread;
    MoveBoarderThread moveBoarderThread;
    public TutorialThread tutorialThread;
    private Paint paint;
    public BoarderActivity boarderActivity;
    public static int bA = 0, bB = 0, bAA = 0, bBB = 0;
    int boarderWidth, boarderHeight;
    int boarderDepth = 100;
    int[][] position = new int[][]{{1, 2, 3, 4, 5, 6, 7, 8, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}};
    int widthCounter;
    int heightCounter;
    final int cellWidth = 100;
    final int cellHeight = 100;
    int leftPading;
    float textSize = 30f;
    int selectedInt = 0;

    static int where;
    static int layer;
    static int startWhere;
    static int startLayer;
    int moveStartX;
    int moveStartY;
    int moveEndX;
    int moveEndY;


    public MoveBoarder(BoarderActivity boarderActivity) {
        super(boarderActivity);
        this.boarderActivity = boarderActivity;
        getHolder().addCallback(this);
        tutorialThread = new TutorialThread(this);
        moveBoarderThread = new MoveBoarderThread(getHolder(), this);
        testThread = new TestThread(this);
//        prepareEndDataThread = new PrepareEndDataThread(this);
//        prepareStartDataThread =new PrepareStartDataThread(this);

    }

    public void doDraw(Canvas canvas) {
        DisplayMetrics metrics = new DisplayMetrics();
        this.boarderActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        boarderWidth = metrics.widthPixels;
        boarderHeight = metrics.heightPixels / 5 * 4;
        leftPading = (boarderWidth - cellWidth * 11) / 2;
        canvas.drawColor(Color.WHITE);
        paint = new Paint();
        paint.setColor(Color.DKGRAY);
        canvas.drawRect(0, boarderHeight - boarderDepth, boarderWidth, boarderHeight, paint);

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        int widthCounter = 0;
        int heightCounter = 0;
        int width;
        int height;
        for (Integer integer : position[0]) {
            width = getStatus(integer)[0];
            height = getStatus(integer)[1];
            if (integer > 0 && integer < 12) {
                canvas.drawLine(leftPading + widthCounter * cellWidth, boarderHeight - boarderDepth - height * cellHeight,
                        leftPading  + widthCounter * cellWidth + width * cellWidth, boarderHeight - boarderDepth - height * cellHeight, paint);
                canvas.drawLine(leftPading + widthCounter * cellWidth, boarderHeight - boarderDepth,
                        leftPading  + widthCounter * cellWidth + width * cellWidth, boarderHeight - boarderDepth, paint);
                canvas.drawLine(leftPading  + widthCounter * cellWidth, boarderHeight - boarderDepth - height * cellHeight,
                        leftPading  + widthCounter * cellWidth, boarderHeight - boarderDepth, paint);
                canvas.drawLine(leftPading  + widthCounter * cellWidth + width * cellWidth, boarderHeight - boarderDepth - height * cellHeight,
                        leftPading  + widthCounter * cellWidth + width * cellWidth, boarderHeight - boarderDepth, paint);
                canvas.drawRect(leftPading  + widthCounter * cellWidth, boarderHeight - boarderDepth - height * cellHeight,
                        leftPading  + widthCounter * cellWidth + width * cellWidth, boarderHeight - boarderDepth, getColor(integer));
                paint.setTextSize(textSize);
                canvas.drawText("B" + integer, leftPading  + widthCounter * cellWidth + (width * cellWidth) / 2 - textSize / 2,
                        boarderHeight - boarderDepth - (height * cellHeight) / 2 + textSize / 2, paint);
            }
            widthCounter += width;
        }

        for(int i=0;i<position[1].length;i++){
            if(position[1][i]!=0){
                widthCounter = 0;
                int selfWidth=getStatus(position[1][i])[0];
                int selfHeight=getStatus(position[1][i])[1];
                height=getStatus(position[0][i])[1];
                int where =i;
                for(int j=0;j<where;j++){
                    widthCounter+=getStatus(position[0][j])[0];
                }
                canvas.drawLine(leftPading  + widthCounter * cellWidth,boarderHeight - boarderDepth - (height+selfHeight) * cellHeight,leftPading  + widthCounter * cellWidth,boarderHeight - boarderDepth - height * cellHeight,paint);
                canvas.drawLine(leftPading  + widthCounter * cellWidth + selfWidth * cellWidth,boarderHeight - boarderDepth - (height+selfHeight) * cellHeight,leftPading  + widthCounter * cellWidth + selfWidth * cellWidth,boarderHeight - boarderDepth - height * cellHeight,paint);
                canvas.drawLine(leftPading  + widthCounter * cellWidth,boarderHeight - boarderDepth - (height+selfHeight) * cellHeight,leftPading  + widthCounter * cellWidth + selfWidth * cellWidth,boarderHeight - boarderDepth - (height+selfHeight) * cellHeight,paint);
                canvas.drawLine(leftPading  + widthCounter * cellWidth,boarderHeight - boarderDepth - height * cellHeight,leftPading  + widthCounter * cellWidth + selfWidth * cellWidth,boarderHeight - boarderDepth - height * cellHeight,paint);
                canvas.drawRect(leftPading  + widthCounter * cellWidth, boarderHeight - boarderDepth - (height+selfHeight) * cellHeight,
                        leftPading  + widthCounter * cellWidth + selfWidth * cellWidth, boarderHeight - boarderDepth - height * cellHeight, getColor(position[1][i]));
                paint.setTextSize(textSize);
                canvas.drawText("B" +position[1][i],leftPading  + (widthCounter) * cellWidth+ (selfWidth * cellWidth) / 2 - textSize / 2,
                        boarderHeight - boarderDepth - (height) * cellHeight-(selfHeight*cellHeight)/2+ textSize / 2,paint);
            }
        }

  /*
        for (int i = 0; i < position[0].length; i++) {
            int where=0;
            width = getStatus(position[0][i])[0];
            if (position[1][i] > 0 && position[1][i] < 12) {
                height = 0;
                heightCounter = 0;
                heightCounter = getStatus(position[0][i])[1];
                height = getStatus(position[1][i])[1];
                height += heightCounter;
                canvas.drawLine(leftPading  + widthCounter * cellWidth, boarderHeight - boarderDepth - height * cellHeight,
                        leftPading  + widthCounter * cellWidth + width * cellWidth, boarderHeight - boarderDepth - height * cellHeight, paint);
                canvas.drawLine(leftPading  + widthCounter * cellWidth, boarderHeight - boarderDepth - heightCounter * cellHeight,
                        leftPading  + widthCounter * cellWidth + width * cellWidth, boarderHeight - boarderDepth - heightCounter * cellHeight, paint);
                canvas.drawLine(leftPading + widthCounter * cellWidth, boarderHeight - boarderDepth - height * cellHeight,
                        leftPading + widthCounter * cellWidth, boarderHeight - boarderDepth - heightCounter * cellHeight, paint);
                canvas.drawLine(leftPading  + widthCounter * cellWidth + width * cellWidth, boarderHeight - boarderDepth - height * cellHeight,
                        leftPading  + widthCounter * cellWidth + width * cellWidth, boarderHeight - boarderDepth - heightCounter * cellHeight, paint);
                canvas.drawRect(leftPading  + widthCounter * cellWidth, boarderHeight - boarderDepth - height * cellHeight,
                        leftPading  + widthCounter * cellWidth + width * cellWidth, boarderHeight - boarderDepth - heightCounter * cellHeight, getColor(position[1][i]));
                paint.setTextSize(textSize);
                canvas.drawText("B" + position[1][i], leftPading  + widthCounter * cellWidth + (width * cellWidth) / 2 - textSize / 2,
                        boarderHeight - boarderDepth - (height * cellHeight) / 2 + textSize / 2, paint);
            }
            widthCounter += width;
        }
*/
        if (testThread.enter != 0) {
            paint.setTextSize(textSize);
            paint.setColor(Color.BLACK);
            canvas.drawLine(moveStartX, moveStartY,moveStartX + getStatus(selectedInt)[0] * cellWidth, moveStartY,paint);
            canvas.drawLine(moveStartX, moveStartY + getStatus(selectedInt)[1] * cellHeight,moveStartX + getStatus(selectedInt)[0] * cellWidth, moveStartY + getStatus(selectedInt)[1] * cellHeight,paint);
            canvas.drawLine(moveStartX, moveStartY,moveStartX, moveStartY + getStatus(selectedInt)[1] * cellHeight,paint);
            canvas.drawLine(moveStartX + getStatus(selectedInt)[0] * cellWidth, moveStartY,moveStartX + getStatus(selectedInt)[0] * cellWidth, moveStartY + getStatus(selectedInt)[1] * cellHeight,paint);
            canvas.drawRect(moveStartX, moveStartY, moveStartX + getStatus(selectedInt)[0] * cellWidth,
                    moveStartY + getStatus(selectedInt)[1] * cellHeight, getColor(selectedInt));
            canvas.drawText("B" + selectedInt,moveStartX+ textSize ,moveStartY+ textSize*2,paint);
        }

        paint.setTextSize(46f);
        paint.setColor(Color.BLACK);
        canvas.drawText("將      放在      上面", 100, 100, paint);
        if (bAA != 0 && bBB != 0) {
            paint.setColor(Color.BLACK);
            canvas.drawText("     B" + bAA + "         B" + bBB, 100, 100, paint);
        }
        if (bA != 0) {
            paint.setColor(Color.RED);
            canvas.drawText("     B" + bA, 100, 100, paint);
        }
        if (bA != 0 && bB != 0) {
            paint.setColor(Color.RED);
            canvas.drawText("     B" + bA + "         B" + bB, 100, 100, paint);
        }



    }

    private Paint getColor(int type) {
        Paint paint = new Paint();
        switch (type) {
            case 1:
            case 5:
                paint.setColor(Color.YELLOW);
                break;
            case 2:
            case 6:
                paint.setColor(Color.BLUE);
                break;
            case 3:
            case 7:
                paint.setColor(Color.GREEN);
                break;
            case 4:
            case 8:
                paint.setColor(Color.RED);
                break;
            case 11:
                paint.setColor(Color.TRANSPARENT);
                break;

        }
        return paint;
    }

    private int[] getStatus(int type) {
        int[] status = new int[]{0, 0};
        switch (type) {
            case 0:
                status = new int[]{1, 0};
                break;
            case 1:
                status = new int[]{1, 1};
                break;
            case 2:
                status = new int[]{1, 1};
                break;
            case 3:
                status = new int[]{1, 1};
                break;
            case 4:
                status = new int[]{2, 1};
                break;
            case 5:
                status = new int[]{1, 2};
                break;
            case 6:
                status = new int[]{1, 1};
                break;
            case 7:
                status = new int[]{1, 2};
                break;
            case 8:
                status = new int[]{2, 2};
                break;
            case 11:
                status = new int[]{2, 1};
                break;
            default:
                break;
        }
        return status;
    }

    public boolean exameBottom(int type) {
        for (Integer integer : position[0]) {
            if (integer == type) {
                return true;
            }
        }
        return false;
    }

    public int getTopType(int type) {
        int checkPosition = -1;
        int target = 0;
        for (int i = 0; i < position[0].length; i++) {
            if (position[0][i] == type) {
                checkPosition = i;
            }
        }
        if (position[1][checkPosition] != 0) {
            target = position[1][checkPosition];
        }
        return target;
    }

    /*
        private int[] getStartPointBottom(int type,int movePattern){
            DisplayMetrics metrics = new DisplayMetrics();
            this.boarderActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            boarderWidth = metrics.widthPixels;
            boarderHeight = metrics.heightPixels/5*4;
            leftPading = (boarderWidth-cellWidth*11)/2;
            int moveStartX = 0;
            int moveStartY = 0;
            boolean keepOn = true;
            for(int i=0;i<11;i++){
                if(type==position[movePattern][i]){
                    moveStartY = getStatus(position[movePattern][i])[1];
                    keepOn =false;
                }
                if(keepOn) {
                    moveStartX += getStatus(position[movePattern][i])[0];
                }
            }
            return new int[] {leftPading+cellWidth*moveStartX,
                    boarderHeight - boarderDepth -cellHeight* moveStartY};
        }

        private int[] getStartPointUper(int type,int movePattern){
            DisplayMetrics metrics = new DisplayMetrics();
            this.boarderActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            boarderWidth = metrics.widthPixels;
            boarderHeight = metrics.heightPixels/5*4;
            leftPading = (boarderWidth-cellWidth*11)/2;
            int moveStartX = 0;
            int moveStartY = 0;
            boolean keepOn = true;
            for(int i=0;i<11;i++){
                if(type==position[movePattern][i]){
                    moveStartY += getStatus(position[movePattern-1][i])[1];
                    moveStartY += getStatus(position[movePattern][i])[1];
                    keepOn =false;
                }
                if(keepOn){
                    moveStartX+=getStatus(position[movePattern][i])[0];
                }
            }
            return new int[] {leftPading+cellWidth*moveStartX,
                    boarderHeight - boarderDepth -cellHeight* moveStartY};
        }
    */
    public void removePosition(int type) {
        for (int i = 0; i < position[0].length; i++) {
            for (Integer integer : position[0]) {
                if (integer == type) {
                    position[0][i] = 0;
                    if (type == 4) {
                        position[0][i + 1] = 0;
                    }
                    if (type == 8) {
                        position[0][i + 1] = 0;
                    }
                }
            }
            for (Integer integer : position[1]) {
                if (integer == type) {
                    position[1][i] = 0;
                    if (type == 4) {
                        position[1][i + 1] = 0;
                    }
                    if (type == 8) {
                        position[1][i + 1] = 0;
                    }
                }
            }
        }
    }


    public void prepareStartData(int type) {
        startWhere = 0;
        startLayer = 0;
        int widthCounter = 0;
        int heightCounter = 0;
        boolean uper = false;

        if (exameBottom(type)) {
            for (int i = 0; i < position[0].length; i++) {
                if (position[0][i] == type) {
                    startWhere = i;
                }
            }
        } else {
            for (int i = 0; i < position[1].length; i++) {
                if (position[1][i] == type) {
                    startWhere = i;
                    uper = true;
                    startLayer=1;
                }
            }
        }
        heightCounter = getStatus(position[0][startWhere])[1];
        if (uper) {
            heightCounter += getStatus(position[1][startWhere])[1];
        }
        for (int i = 0; i < startWhere; i++) {
            widthCounter += getStatus(position[0][i])[0];
        }
  /*
        for (Integer integer:position[0]){
            if(type==integer){
                bingo=true;
                bingoUper=false;
            }

        }
        while (bingo){
            for(Integer integer:position[0]){
                if(type==integer){
                    bingo=false;
                    heightCounter=getStatus(position[0][where])[1];
                }
                if(bingo){
                    widthCounter+=getStatus(position[0][where])[0];
                    where++;
                }

            }
        }
        while (bingoUper==true){

            where=0;
            for(Integer integer:position[1]){
                if(type==integer){
                    bingoUper=false;
                    heightCounter+=getStatus(position[0][where])[1];
                    heightCounter+=getStatus(position[1][where])[1];
                }
                if(bingoUper==true){
                    widthCounter+=getStatus(position[1][where])[0];
                    where++;
                }
            }
        }
*/
        //準備moveStartX,moveStartY 坐標軸
        DisplayMetrics metrics = new DisplayMetrics();
        this.boarderActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        boarderWidth = metrics.widthPixels;
        boarderHeight = metrics.heightPixels / 5 * 4;
        leftPading = (boarderWidth - cellWidth * 11) / 2;
        moveStartX = leftPading + cellWidth * widthCounter;
        moveStartY = boarderHeight - boarderDepth - cellHeight * heightCounter;
    }

    public void prepareEndData(int type) {
        where = 0;
        layer = 0;
        widthCounter = 0;
        heightCounter = 0;
        boolean uper = false;
        boolean bingo = true;
        if (type == bBB) {
            if (exameBottom(bBB)) {
                for (int i = 0; i < position[0].length; i++) {
                    if (position[0][i] == type) {
                        where = i;
                    }
                }
                heightCounter = getStatus(position[0][where])[1];
                for (int i = 0; i < where; i++) {
                    widthCounter += getStatus(position[0][i])[0];
                }
            } else {
                for (int i = position[0].length-1; i > -1; i--) {
                    if (position[0][i] == 0) {
                        where = i;
                    }
                }
                heightCounter = getStatus(position[0][where])[1];
                for (int i = 0; i < where; i++) {
                    widthCounter += getStatus(position[0][i])[0];
                }
            }
        } else if (type == bAA) {
            layer=1;
            for (int i = 0; i < position[0].length; i++) {
                if (position[0][i] == bBB) {
                    where = i;

                }
            }
            heightCounter += getStatus(bBB)[1];
            heightCounter += getStatus(bAA)[1];
            for (int i = 0; i < where; i++) {
                widthCounter += getStatus(position[0][i])[0];
            }
        }

        //非目標方塊
        else {
            for (int i = position[0].length-1; i > -1; i--) {
                if (position[0][i] == 0) {
                    where = i;
                }
            }
            heightCounter = getStatus(position[0][where])[1];
            for (int i = 0; i < where; i++) {
                widthCounter += getStatus(position[0][i])[0];
            }
        }

        /*
        if(type==bBB){
            if(exameBottom(bBB)){
                while (bingo){
                    for(Integer integer:position[0]){
                        if(type==integer){
                            bingo=false;
                            heightCounter=getStatus(type)[1];
                            layer=0;
                        }
                        if(bingo){
                           widthCounter+=getStatus(position[0][where])[0];
                           where++;
                        }
                    }
                }
            }
            else{
                if(type==4 ^ type==8){
                    if(position[0][0]==0){
                        if(position[0][1]==0){
                            position[0][1]=13;
                            where=0;
                            layer=0;
                            widthCounter=0;
                            heightCounter=getStatus(type)[1];
                        }
                        else{
                            if(position[1][1]!=0){
                                selectedInt=position[1][1];
                                tutorialThread.status=99;
                            }
                            else{
                                selectedInt=position[0][1];
                                tutorialThread.status=99;
                            }
                        }
                    }
                    else if(position[0][10]==0){
                        if(position[0][9]==0){
                            position[0][10]=13;
                            where =9;
                            layer =0;
                            heightCounter=getStatus(type)[1];
                            for(int i=0;i<where;i++){
                                widthCounter+=getStatus(position[0][i])[0];
                            }
                        }
                        else{
                            if(position[1][9]!=0){
                                selectedInt=position[1][9];
                                tutorialThread.status=99;
                            }
                            else{
                                selectedInt=position[0][9];
                                tutorialThread.status=99;
                            }
                        }
                    }
                    else{
                        for(int i=0;i<position[0].length;i++){
                            if(position[0][i]==0){
                                if(position[0][i-1]==0){
                                    where =i-1;
                                    layer=0;
                                    position[0][i]=13;
                                    heightCounter = getStatus(type)[1];
                                    for(int j=0;j<where;j++){
                                        widthCounter+=getStatus(position[0][j])[0];
                                    }
                                }
                                else if(position[0][i+1]==0){
                                    where=i;
                                    layer=0;
                                    position[0][i+1]=13;
                                    heightCounter =getStatus(type)[1];
                                    for(int j=0;j<where;j++){
                                        widthCounter+=getStatus(position[0][j])[0];
                                    }
                                }
                                else{
                                    if(position[1][i+1]!=0){
                                        selectedInt = position[1][i+1];
                                        tutorialThread.status=99;
                                    }
                                    else {
                                        selectedInt = position[0][i+1];
                                        tutorialThread.status=99;
                                    }
                                }
                            }
                        }
                    }
                }
                else{
                    while (bingo){
                        for (int i=0;i<position[0].length;i++){
                            if (position[0][i]==0){
                                bingo=false;
                                where=i;
                                layer=0;
                                heightCounter=getStatus(type)[1];
                            }
                            if(bingo){
                                widthCounter+=getStatus(position[0][where])[0];
                                where++;
                            }
                        }
                    }
                }
            }
        }
*/
        /*
        if (type == bAA) {
            if (bAA == 4) {
                if (bBB != 8) {
                    tutorialThread.status = 100;
                }
                if (bBB == 8) {
                    if (getTopType(bAA) != 0) {
                        selectedInt = getTopType(bAA);
                        tutorialThread.status = 99;
                    } else {
                        while (bingo) {
                            for (Integer integer : position[0]) {
                                if (integer == bBB) {
                                    bingo = false;
                                    layer = 1;
                                    position[1][where + 1] = 13;
                                    heightCounter += getStatus(position[0][where])[1];
                                    heightCounter += getStatus(bAA)[1];
                                }
                                if (bingo) {
                                    widthCounter += getStatus(position[0][where])[0];
                                    where++;
                                }
                            }
                        }
                    }
                }
            } else if (bAA == 8) {
                if (bBB != 4) {
                    tutorialThread.status = 100;
                }
                if (bBB == 4) {
                    if (getTopType(bAA) != 0) {
                        selectedInt = getTopType(bAA);
                        tutorialThread.status = 99;
                    } else {
                        while (bingo) {
                            for (Integer integer : position[0]) {
                                if (integer == bBB) {
                                    bingo = false;
                                    layer = 1;
                                    position[1][where + 1] = 13;
                                    heightCounter += getStatus(position[0][where])[1];
                                    heightCounter += getStatus(bAA)[1];
                                }
                                if (bingo) {
                                    widthCounter += getStatus(position[0][where])[0];
                                    where++;
                                }
                            }
                        }
                    }
                }
            }
            //bAA不是4或8的情況
            else {
                if (!exameBottom(bAA)) {
                    while (bingo) {
                        for (Integer integer : position[0]) {
                            if (integer == bBB) {
                                bingo = false;
                                layer = 1;
                                heightCounter += getStatus(position[0][where])[1];
                                heightCounter += getStatus(bAA)[1];
                            }
                            if (bingo) {
                                widthCounter += getStatus(position[0][where])[0];
                                where++;
                            }
                        }
                    }
                } else {
                    if (getTopType(bAA) != 0) {
                        selectedInt = getTopType(bAA);
                        tutorialThread.status = 99;
                    } else {
                        while (bingo) {
                            for (Integer integer : position[0]) {
                                if (integer == bBB) {
                                    bingo = false;
                                    layer = 1;
                                    heightCounter += getStatus(position[0][where])[1];
                                    heightCounter += getStatus(bAA)[1];
                                }
                                if (bingo) {
                                    widthCounter += getStatus(position[0][where])[0];
                                    where++;
                                }
                            }
                        }
                    }
                }
            }
        }

        //非目標方塊
        else {
            if (bBB == 4) {
                int there = -1;
                int[] target = new int[]{1, 2, 3, 6};
                for (Integer integer : target) {
                    if (integer == bAA) {
                        integer = 0;
                    }
                    for (int i = 0; i < 11; i++) {
                        if (integer == position[1][i]) {
                            integer = 0;
                        }
                    }
                }
                for (Integer integer : target) {
                    if (integer != 0) {
                        there = integer;
                    }
                }
                while (bingo) {
                    for (Integer integer : position[0]) {
                        if (integer == there) {
                            bingo = false;
                            layer = 1;
                            heightCounter += getStatus(position[0][where])[1];
                            heightCounter += getStatus(there)[1];
                        }
                        if (bingo) {
                            widthCounter += getStatus(position[0][where])[0];
                            where++;
                        }
                    }
                }
            } else if (bBB == 8) {
                int there = -1;
                int[] target = new int[]{1, 2, 3, 6};
                for (Integer integer : target) {
                    if (integer == bAA) {
                        integer = 0;
                    }
                    for (int i = 0; i < position[0].length; i++) {
                        if (integer == position[1][i]) {
                            integer = 0;
                        }
                    }
                }
                for (Integer integer : target) {
                    if (integer != 0) {
                        there = integer;
                    }
                }
                while (bingo) {
                    for (Integer integer : position[0]) {
                        if (integer == there) {
                            bingo = false;
                            layer = 1;
                            heightCounter += getStatus(position[0][where])[1];
                            heightCounter += getStatus(there)[1];
                        }
                        if (bingo) {
                            widthCounter += getStatus(position[0][where])[0];
                            where++;
                        }
                    }
                }
            } else {
                while (bingo) {
                    for (Integer integer : position[0]) {
                        if (integer == 0) {
                            bingo = false;
                            layer = 0;
                            heightCounter += getStatus(position[0][where])[1];
                        }
                        if (bingo) {
                            widthCounter += getStatus(position[0][where])[0];
                            where++;
                        }
                    }
                }
            }
        }
*/
        //準備endX,endY坐標軸
        DisplayMetrics metrics = new DisplayMetrics();
        this.boarderActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        boarderWidth = metrics.widthPixels;
        boarderHeight = metrics.heightPixels / 5 * 4;
        leftPading = (boarderWidth - cellWidth * 11) / 2;
        moveEndX = leftPading + cellWidth * widthCounter;
        moveEndY = boarderHeight - boarderDepth - cellHeight * heightCounter;
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        tutorialThread.setFlag(true);
        tutorialThread.start();
        testThread.setFlag(true);
        testThread.start();
        moveBoarderThread.setFlag(true);
        moveBoarderThread.start();
        //         prepareStartDataThread.setFlag(true);
        //          prepareStartDataThread.start();
        //           prepareEndDataThread.setFlag(true);
        //           prepareEndDataThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        boolean retry = true;
        tutorialThread.setFlag(false);
        moveBoarderThread.setFlag(false);
        testThread.setFlag(false);
        //      prepareEndDataThread.setFlag(false);
        //      prepareStartDataThread.setFlag(false);
        while (retry) {
            try {
                //               prepareStartDataThread.join();
                //               prepareEndDataThread.join();
                testThread.join();
                tutorialThread.join();
                moveBoarderThread.join();
                retry = false;
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

    }

    class MoveBoarderThread extends Thread {
        private SurfaceHolder surfaceHolder;
        private MoveBoarder moveBoarder;
        private boolean flag = false;
        private int span = 200;

        public MoveBoarderThread(SurfaceHolder surfaceHolder, MoveBoarder moveBoarder) {
            this.surfaceHolder = surfaceHolder;
            this.moveBoarder = moveBoarder;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public void run() {
            Canvas canvas = null;
            while (flag) {
                try {
                    Thread.sleep(span);
                } catch (Exception ex) {
                }
                try {
                    canvas = this.surfaceHolder.lockCanvas(null);
                    synchronized (this.surfaceHolder) {
                        doDraw(canvas);
                    }
                } finally {
                    if (canvas != null) {
                        this.surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }

/*
    class TutorialThread extends Thread{

        private SurfaceHolder surfaceHolder;
        private MoveBoarder moveBoarder;
        private boolean flag = false;
        private int span = 200;
        public TutorialThread(SurfaceHolder surfaceHolder,MoveBoarder moveBoarder){
            this.surfaceHolder= surfaceHolder;
            this.moveBoarder = moveBoarder;
        }
        public void setFlag(boolean flag){this.flag = flag;}
        public void run(){
            Canvas canvas =null;
            while(flag) {
                try {
                    canvas = this.surfaceHolder.lockCanvas(null);
                    synchronized (this.surfaceHolder) {
                        moveBoarder.doDraw(canvas);
                        if(startMovebB){
                            Toast.makeText(moveBoarder.getContext(),"向上",Toast.LENGTH_SHORT).show();
                            switch (moveOthersbB){
                                case 1:
                                    selectedInt = selectedIntB;
                                    prepareStartData(selectedInt);
                                    prepareEndData(selectedInt);
                                    moveOthersbB =towardUp;
                                    break;
                                case towardUp:
                                    moveStartY-=moveSpan;
                                    if(moveStartY<100){
                                        moveStartY=100;
                                        if(moveStartX>moveEndX){
                                            moveOthersbB=towardLeft;
                                        }
                                        else if(moveStartX<moveEndX){
                                            moveOthersbB=towardRight;
                                        }
                                    }
                                    break;
                                case towardLeft:
                                    moveStartX-=moveSpan;
                                    if(moveStartX<moveEndX){
                                        moveStartX=moveEndX;
                                        moveOthersbB=towardDown;
                                    }
                                    break;
                                case towardRight:
                                    moveStartX+=moveSpan;
                                    if(moveStartX>moveEndX){
                                        moveStartX=moveEndX;
                                        moveOthersbB=towardDown;
                                    }
                                    break;
                                case towardDown:
                                    moveStartY+=moveSpan;
                                    if(moveStartY>moveEndY){
                                        moveStartY=moveEndY;
                                        moveOthersbB=check;
                                    }
                                    break;
                                case check:
                                    if(selectedInt!=bBB){
                                        position[layer][where]=selectedInt;
                                        //開始移動bBB
                                        moveOthersbB=0;
                                    }
                                    //移動完畢的是bBB
                                    else{
                                        position[layer][where]=bBB;
                                        startMovebB=false;
                                        startMovebA=true;
                                    }
                                    break;
                                case 0:
                                    selectedInt = bBB;
                                    prepareStartData(selectedInt);
                                    prepareEndData(selectedInt);
                                    if(moveStartX!=moveEndX){
                                        moveOthersbB =towardUp;
                                    }
                                    else{
                                        position[layer][where]=bBB;
                                        startMovebB=false;
                                        startMovebA=true;
                                    }
                                    break;
                                case 99:
                                    prepareStartData(selectedInt);
                                    prepareEndData(selectedInt);
                                    moveOthersbB =towardUp;
                                    break;
                            }
                        }

                        if(startMovebA){
                            switch (moveOthersbA){
                                case 1:
                                    selectedInt = selectedIntA;
                                    prepareStartData(selectedInt);
                                    prepareEndData(selectedInt);
                                    moveOthersbB =towardUp;
                                    break;
                                case towardUp:
                                    moveStartY-=moveSpan;
                                    if(moveStartY<100){
                                        moveStartY=100;
                                        if(moveStartX>moveEndX){
                                            moveOthersbB=towardLeft;
                                        }
                                        else if(moveStartX<moveEndX){
                                            moveOthersbB=towardRight;
                                        }
                                    }
                                    break;
                                case towardLeft:
                                    moveStartX-=moveSpan;
                                    if(moveStartX<moveEndX){
                                        moveStartX=moveEndX;
                                        moveOthersbB=towardDown;
                                    }
                                    break;
                                case towardRight:
                                    moveStartX+=moveSpan;
                                    if(moveStartX>moveEndX){
                                        moveStartX=moveEndX;
                                        moveOthersbB=towardDown;
                                    }
                                    break;
                                case towardDown:
                                    moveStartY+=moveSpan;
                                    if(moveStartY>moveEndY){
                                        moveStartY=moveEndY;
                                        moveOthersbB=check;
                                    }
                                    break;
                                case check:
                                    if(selectedInt!=bAA){
                                        position[layer][where]=selectedInt;
                                        //開始移動bAA
                                        moveOthersbA=0;
                                    }
                                    //移動完畢的是bAA
                                    else{
                                        position[layer][where]=bAA;
                                        startMovebA=false;
                                        moveBoarder.boarderActivity.btExec.setEnabled(true);
                                    }
                                    break;
                                case 0:
                                    selectedInt = bAA;
                                    prepareStartData(selectedInt);
                                    prepareEndData(selectedInt);
                                    if(moveStartX!=moveEndX){
                                        moveOthersbB =towardUp;
                                    }
                                    else{
                                        position[layer][where]=bAA;
                                        startMovebA=false;
                                        moveBoarder.boarderActivity.btExec.setEnabled(true);
                                    }
                                    break;
                                case 99:
                                    prepareStartData(selectedInt);
                                    prepareEndData(selectedInt);
                                    moveOthersbB =towardUp;
                                    break;
                                case 100:
                                    break;
                            }
                        }

                    }
                } finally {
                    if (canvas != null) {
                        this.surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
                try {
                    Thread.sleep(span);
                } catch (Exception ex) {}
            }
        }
    }
*/
}
