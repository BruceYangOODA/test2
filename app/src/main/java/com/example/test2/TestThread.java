package com.example.test2;

public class TestThread extends Thread {
    private MoveBoarder moveBoarder;
    private boolean flag;
    private int sleepSpan = 200;
    int moveSpan = 200;
    final static int towardUp = 11;
    final static int towardLeft = 12;
    final static int towardRight = 13;
    final static int towardDown = 14;
    final static int check = 15;
    final static int waiting = 0;
    int enter;

    public TestThread(MoveBoarder moveBoarder) {
        this.moveBoarder = moveBoarder;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void run() {
        while (flag) {
            try {
                Thread.sleep(sleepSpan);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            switch (enter) {
                case check:
                    if (moveBoarder.selectedInt == moveBoarder.bBB) {
                        //移動完畢的是bBB
                        moveBoarder.tutorialThread.status = 0;
                        moveBoarder.selectedInt = moveBoarder.bBB;
                        enter = waiting;
                    }
                    if(moveBoarder.selectedInt == moveBoarder.bAA) {
                        //移動完畢的是bAA
                        moveBoarder.tutorialThread.status = 3;
                        moveBoarder.selectedInt = moveBoarder.bAA;
                        enter = waiting;
                    }
                    if(moveBoarder.selectedInt!=moveBoarder.bAA && moveBoarder.selectedInt!=moveBoarder.bBB){
                        //移動完畢的是其他方塊
                        moveBoarder.tutorialThread.status = 0;
                        enter = waiting;
                    }
                    break;
                case towardUp:
                        moveBoarder.position[moveBoarder.startLayer][moveBoarder.startWhere] = 0;
                    moveBoarder.moveStartY -= moveSpan;
                    if (moveBoarder.moveStartY < 50) {
                        moveBoarder.moveStartY = 50;
                        if (moveBoarder.moveStartX > moveBoarder.moveEndX) {
                            enter = towardLeft;
                        } else if (moveBoarder.moveStartX < moveBoarder.moveEndX) {
                            enter = towardRight;
                        }
                    }
                    break;
                case towardLeft:
                    moveBoarder.moveStartX -= moveSpan;
                    if (moveBoarder.moveStartX < moveBoarder.moveEndX) {
                        moveBoarder.moveStartX = moveBoarder.moveEndX;
                        enter = towardDown;
                    }
                    break;
                case towardRight:
                    moveBoarder.moveStartX += moveSpan;
                    if (moveBoarder.moveStartX > moveBoarder.moveEndX) {
                        moveBoarder.moveStartX = moveBoarder.moveEndX;
                        enter = towardDown;
                    }
                    break;
                case towardDown:
                    moveBoarder.moveStartY += moveSpan;
                    if (moveBoarder.moveStartY > moveBoarder.moveEndY) {
                        moveBoarder.moveStartY = moveBoarder.moveEndY;
 //                       moveBoarder.removePosition(moveBoarder.selectedInt);
                        moveBoarder.position[moveBoarder.layer][moveBoarder.where] = moveBoarder.selectedInt;
                        enter = check;
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
