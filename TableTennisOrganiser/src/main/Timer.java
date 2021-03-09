package main;



/**
 * Class which updates the team statistics every set amount of seconds (as per
 * brief).
 * TODO: Threaded timer.
 * @author  Aaron Cardwell 13009941
 * @version 1.0
 * @since 06/12/2020
 */
public class Timer extends Admin implements Runnable{

    // Volaitle veriables used to prevent CPU cache issues.
    private volatile int time;
    private volatile boolean running = true;

    public Timer(int time) {
        this.time = time;
    }

    @Override
    public void run() {
        System.out.println("main.Timer.run()");
        try {
            while (running) {
                System.out.println("Auto generating team statistics for all Leagues");
                generateTeamStats();
                //This is OK to run sleep in a loop as this is a daemon thread.
                Thread.sleep(time);
                System.out.printf("Timer sleeping for %d seconds\n", time/1000);
            }
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted");
        }
    }
    public void testMethod() {
        System.out.println("main.Timer.testMethod()");
    }
}
