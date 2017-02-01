package lolPing;

public class InterpreterManager extends Thread {
    String ip;
    public InterpreterManager (String ip){
        this.ip=ip;

    }

    public void run(){
        System.out.println("Iniciando");
        for (int x=0;x<10;x++){
            Interpreter interp=new Interpreter(ip);
            interp.start();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
