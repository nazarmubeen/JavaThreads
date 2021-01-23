package utils;

public class ThreadUtils {

    public  static void printThreadMessage(Thread t,String text){
        System.out.println(text+" "+t.getName());
    }
}
