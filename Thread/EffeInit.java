public class EffeInit extends Thread{
    private static int start;
    private int dim;
    public static int[] data;
    public static final int SIZE = 10000000;
    public static final int MAX_THR = 8;

    public EffeInit(int start, int size){
        this.start = start;
        this.dim = size;
    }

    public void run(){
        int j;
        for(int i = 0; i < this.dim; i++){
            data[this.start + i] = i;
        }//end for
    }



    public static void main (String[] args){
        data = new int[SIZE];
        long begin, end;
        int start, j;
        EffeInit[] threads;

        for(int numThread = 1; numThread <= MAX_THR; numThread++){
            begin = System.currentTimeMillis();
            start = 0;
            threads = new EffeInit[numThread];
            for(j = 0; j < numThread; j++){
                threads[j] = new EffeInit(start, SIZE / numThread);
                threads[j].start();
                start += SIZE / numThread;

            } //end for
            for(j = 0; j < numThread; j++){
                try{
                    threads[j].join();
                }catch(InterruptedException e){
                    e.printStackTrace();
                } //end catch
            } //end for
            end = System.currentTimeMillis();
            System.out.println(numThread + "Thread(s) :" + (end - begin) + "ms");
        } //end for esterno
    } //end main
} //end EffeInit
