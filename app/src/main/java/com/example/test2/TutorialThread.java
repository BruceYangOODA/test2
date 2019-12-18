package com.example.test2;

public class TutorialThread extends Thread {

    private MoveBoarder moveBoarder;
    private boolean flag = false;
    private int span = 200;
    int status;
    final static int checkB = 0;
    final static int moveSelfB = 1;
    final static int moveOtherB = 2;
    final static int checkA = 3;
    final static int moveSelfA = 4;
    final static int moveOtherA = 5;
    final static int waiting = 13;

    final static int towardUp = 11;

    int leftPading;
    int boarderWidth,boarderHeight;
    int boarderDepth = 100;

    public TutorialThread(MoveBoarder moveBoarder) {
        this.moveBoarder = moveBoarder;
        flag=true;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void run() {

        while (flag) {
            try {
                Thread.sleep(span);
            } catch (Exception ex) {
            }
            switch (status) {
                case checkB:
                    if (moveBoarder.exameBottom(moveBoarder.bBB)) {
                        if (moveBoarder.getTopType(moveBoarder.bBB) != 0) {
                            if (moveBoarder.getTopType(moveBoarder.bBB) == moveBoarder.bAA) {
                                moveBoarder.selectedInt = 0;
 //                               moveBoarder.boarderActivity.btExec.setEnabled(true);
                                status = waiting;
                            } else {
                                moveBoarder.selectedInt = moveBoarder.getTopType(moveBoarder.bBB);
                                status = moveOtherB;
                            }
                        } else {
                            status = checkA;
                        }
                    } else {
                        moveBoarder.selectedInt = moveBoarder.bBB;
                        status = moveSelfB;
                    }
                    break;
                case moveSelfB:

                    moveBoarder.prepareEndData(moveBoarder.selectedInt);
                    moveBoarder.prepareStartData(moveBoarder.selectedInt);
    //                moveBoarder.prepareStartDataThread.status=0;
    //                moveBoarder.prepareEndDataThread.status=0;
   //                 try{Thread.sleep(2000);}
  //                  catch (Exception ex){ex.printStackTrace();}

                    if (moveBoarder.moveStartX != moveBoarder.moveEndX) {
                        moveBoarder.testThread.enter = towardUp;
                        status = waiting;
                    } else {
                        moveBoarder.position[moveBoarder.layer][moveBoarder.where] = moveBoarder.bBB;
                        status = checkA;
                    }
                    break;
                case moveOtherB:

                    moveBoarder.prepareEndData(moveBoarder.selectedInt);
                    moveBoarder.prepareStartData(moveBoarder.selectedInt);
                    //                moveBoarder.prepareStartDataThread.status=0;
                    //                moveBoarder.prepareEndDataThread.status=0;
  //                  try{Thread.sleep(2000);}
  //                  catch (Exception ex){ex.printStackTrace();}

                    moveBoarder.testThread.enter = towardUp;
                    status = waiting;
                    break;
                case checkA:
                    if (!moveBoarder.exameBottom(moveBoarder.bAA)) {
                        if (moveBoarder.bAA == moveBoarder.getTopType(moveBoarder.bBB)) {
                            moveBoarder.selectedInt = 0;
                            status = waiting;
                        } else {
                            moveBoarder.selectedInt = moveBoarder.bAA;
                            status = moveSelfA;
                        }
                    } else {
                        if (moveBoarder.getTopType(moveBoarder.bAA) == 0) {
                            moveBoarder.selectedInt = moveBoarder.bAA;
                            status = moveSelfA;
                        } else {
                            moveBoarder.selectedInt = moveBoarder.getTopType(moveBoarder.bAA);
                            status = moveOtherA;
                        }
                    }
                    break;
                case moveSelfA:

                    moveBoarder.prepareEndData(moveBoarder.selectedInt);
                    moveBoarder.prepareStartData(moveBoarder.selectedInt);
                    //                moveBoarder.prepareStartDataThread.status=0;
                    //                moveBoarder.prepareEndDataThread.status=0;
 //                   try{Thread.sleep(2000);}
 //                   catch (Exception ex){ex.printStackTrace();}

                    if (moveBoarder.moveStartX != moveBoarder.moveEndX) {
                        moveBoarder.testThread.enter = towardUp;
                        status = waiting;
                    } else {
                        status = waiting;
                    }
                    break;
                case moveOtherA:

                    moveBoarder.prepareEndData(moveBoarder.selectedInt);
                    moveBoarder.prepareStartData(moveBoarder.selectedInt);
                    //                moveBoarder.prepareStartDataThread.status=0;
                    //                moveBoarder.prepareEndDataThread.status=0;
 //                   try{Thread.sleep(2000);}
 //                   catch (Exception ex){ex.printStackTrace();}

                    moveBoarder.testThread.enter = towardUp;
                    status = waiting;
                    break;
                case waiting:
                    try {
                        Thread.sleep(2000);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    break;
                case 99:
                    moveBoarder.prepareEndData(moveBoarder.selectedInt);
                    moveBoarder.prepareStartData(moveBoarder.selectedInt);
                    //                moveBoarder.prepareStartDataThread.status=0;
                    //                moveBoarder.prepareEndDataThread.status=0;
 //                   try{Thread.sleep(2000);}
 //                   catch (Exception ex){ex.printStackTrace();}

                    moveBoarder.testThread.enter = towardUp;
                    status = waiting;
                    break;
                case 100:
                    status = waiting;
                    break;
                case 101:
                    moveBoarder.selectedInt=moveBoarder.bAA;
                    moveBoarder.moveStartX=100;
                    moveBoarder.moveStartY=100;
                    status=102;
                    break;
                case 102:
                    moveBoarder.moveStartX+=30;
                    if(moveBoarder.moveStartX>1000){
                        moveBoarder.moveStartX=1000;
                        status=103;
                    }
                    break;
                case 103:
                    moveBoarder.moveStartX-=30;
                    if(moveBoarder.moveStartX<50){
                        moveBoarder.moveStartX=50;
                        status=102;
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
