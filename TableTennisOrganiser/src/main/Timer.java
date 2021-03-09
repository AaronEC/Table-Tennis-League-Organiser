package main;

/**
 * Class which updates the team statistics every set amount of seconds (as per
 * brief).
 * @author  Aaron Cardwell 13009941
 * @version 1.0
 * @since 06/12/2020
 */
public class Timer extends UserController implements Runnable{

    // Volaitle veriables used to prevent CPU cache issues.
    private volatile int time;
    private volatile boolean running = true;

    public Timer(int time) {
        this.time = time;
    }

    @Override
    public void run() {
        try {
            while (running) {
                System.out.println("THREAD: Auto generating team statistics.");
                admin.generateTeamStats();
                //This is OK to run sleep in a loop as this is a daemon thread.
                System.out.printf("THREAD: sleeping for %d seconds\n", time/1000);
                Thread.sleep(time);
            }
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted");
        }
    }
    public void testMethod() {
        System.out.println("main.Timer.testMethod()");
    }
}
