package Threads13.callableandfuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CallableImpl {

    public static void main(String[] args) {

        Helper h=new Helper();

        List<Callable> callableList=new ArrayList<>();

        Callable<ArrayList<String>> namesCall=new Callable<ArrayList<String>>() {
            @Override
            public ArrayList<String> call() throws Exception {
                return h.addNames();
            }


        };

      callableList.add(namesCall);

        Callable printNamesCall=new Callable() {
            @Override
            public Void call() throws Exception {
                h.printNames(new ArrayList<>());
                return null;
            }
        };

        callableList.add(printNamesCall);


        ExecutorService service= Executors.newFixedThreadPool(3);

        callableList.stream().forEach(a -> service.submit(a));

        try {
            h.asyncOperation();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        System.out.println("finished");

        service.shutdown();
    }



}
class Helper{
    AsyncHelper async=new AsyncHelper();

    void printNames( ArrayList<String> names){

        System.out.println(names);

    }

    ArrayList<String> addNames(){
        ArrayList<String> list=new ArrayList<>();
        System.out.println("adding names ");
        for(int i=0;i<5;i++){
            list.add(" "+i);
        }

        return list;
    }

    void asyncOperation() throws ExecutionException, InterruptedException {
        ExecutorService threadpool = Executors.newCachedThreadPool();
        Future<String> futureTask = threadpool.submit(() -> async.getName());

        while (!futureTask.isDone()) {
            System.out.println("FutureTask is not finished yet...");
        }

        String result = futureTask.get();
        System.out.println("FutureTask is finished "+result);
        threadpool.shutdown();

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> async.getName());
        while (!completableFuture.isDone()) {
            System.out.println("CompletableFuture is not finished yet...");
        }
         result = completableFuture.get();


        CompletableFuture<Void> completableFuture1 = CompletableFuture.runAsync(() -> async.getName());
        while (!completableFuture1.isDone()) {
            System.out.println("completableFuture1 is not finished yet...");
        }

         //result = completableFuture1.get();




    }
}

class AsyncHelper{


    String getName(){
        System.out.println(" my name is test");
       return "my name is";
    }

}