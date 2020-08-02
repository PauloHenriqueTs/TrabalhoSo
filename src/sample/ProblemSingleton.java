package sample;

import java.util.concurrent.Semaphore;

public class ProblemSingleton {
    private   static ProblemSingleton problemSingleton;
    public static Semaphore mutex ;
    public static Semaphore block ;
    public static volatile boolean must_wait ;
    public static volatile int drinking;
    public  static volatile int waiting ;
    public  static volatile int blockValue ;

    private static int numcadeiras;

    private ProblemSingleton(){
        this.numcadeiras =5;
        this.mutex = new Semaphore(1);
        this.block =new Semaphore(0,false);
        this.must_wait =false;
        this.drinking =0;
        this.waiting=0;
        this.blockValue=0;
    }
    public static  synchronized ProblemSingleton getInstance(){
        if(problemSingleton ==null){
            problemSingleton = new ProblemSingleton();
        }
        return problemSingleton;
    }


    public static Semaphore getMutex() {
        return mutex;
    }

    public static void setMutex(Semaphore mutex) {
        ProblemSingleton.mutex = mutex;
    }

    public static Semaphore getBlock() {
        return block;
    }

    public static void setBlock(Semaphore block) {
        ProblemSingleton.block = block;
    }

    public static boolean isMust_wait() {
        return must_wait;
    }

    public static void setMust_wait(boolean must_wait) {
        ProblemSingleton.must_wait = must_wait;
    }

    public static int getDrinking() {
        return drinking;
    }

    public static void setDrinking(int drinking) {
        ProblemSingleton.drinking = drinking;
    }
    public static void addDrinking() {
        ProblemSingleton.drinking++;
    }
    public static void subDrinking() {
        ProblemSingleton.drinking--;
    }

    public static int getWaiting() {
        return waiting;
    }

    public static void setWaiting(int waiting) {
        ProblemSingleton.waiting = waiting;
    }
    public static void addWaiting() {
        ProblemSingleton.waiting++;
    }
    public static void subWaiting() {
        ProblemSingleton.waiting--;
    }

    public static int getBlockValue() {
        return blockValue;
    }

    public static void setBlockValue(int blockValue) {
        ProblemSingleton.blockValue = blockValue;
    }
    public static void subBlockValue() {
        ProblemSingleton.blockValue--;
    }

    public static void setNumcadeiras(int numcadeiras) {
        ProblemSingleton.numcadeiras = numcadeiras;
    }

    public static int getNumcadeiras(){

        return  ProblemSingleton.numcadeiras;
    }

    public static void subWaiting(int n) {
        ProblemSingleton.waiting = ProblemSingleton.waiting - n;
    }

    public static void addDrinking(int n) {
        ProblemSingleton.drinking = ProblemSingleton.drinking + n;
    }
}
