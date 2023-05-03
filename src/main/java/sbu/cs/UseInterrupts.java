package sbu.cs;

/*
    In this exercise, you must analyse the following code and use interrupts
    in the main function to terminate threads that run for longer than 3 seconds.

    A thread may run for longer than 3 seconds due the many different reasons,
    including lengthy process times or getting stuck in an infinite loop.

    Take note that you are NOT ALLOWED to change or delete any existing line of code.
 */

public class UseInterrupts
{
/*
    TODO
     Analyse the following class and add new code where necessary.
     If an object from this type of thread is Interrupted, it must print this:
        "{ThreadName} has been interrupted"
     And then terminate itself.
 */
    public static class SleepThread extends Thread {
        int sleepCounter;
        boolean interrupted;
        public SleepThread(int sleepCounter) {
            super();
            this.sleepCounter = sleepCounter;
            this.interrupted = false;
        }

        @Override
        public void run() {
            System.out.println(this.getName() + " is Active.");
            while (this.sleepCounter > 0  && !Thread.currentThread().isInterrupted())
            {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }
                finally {
                    this.sleepCounter--;
                    System.out.println("Number of sleeps remaining: " + this.sleepCounter);
                }
            }
            System.out.println("{"+ Thread.currentThread().getName() + "}"+" has been interrupted");
        }
        public void interrupt() {
            this.interrupted = true;
        }
        public boolean isInterrupted() {
            return this.interrupted;
        }
    }

/*
    TODO
     Analyse the following class and add new code where necessary.
     If an object from this type of thread is Interrupted, it must print this:
        "{ThreadName} has been interrupted"
     And then terminate itself.
     (Hint: Use the isInterrupted() method)
 */
    public static class LoopThread extends Thread {
        int value;
        boolean interrupted;

        public LoopThread(int value) {
            super();
            this.value = value;
            this.interrupted = false;
        }

        @Override
        public void run() {
            System.out.println(this.getName() + " is Active.");

            for (int i = 0; i < 10; i += 3)
            {
                i -= this.value;
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            }
            System.out.println("{"+ Thread.currentThread().getName() + "}"+" has been interrupted");
        }
        public void interrupt() {
            this.interrupted = true;
        }
        public boolean isInterrupted() {
            return this.interrupted;
        }
    }


/*
    You can add new code to the main function. This is where you must utilize interrupts.
    No existing line of code should be changed or deleted.
 */
    public static void main(String[] args) {
        SleepThread sleepThread = new SleepThread(5);
        sleepThread.start();
        long startSleepThread = System.currentTimeMillis();
        while (sleepThread.isAlive()) {
            if (System.currentTimeMillis() - startSleepThread > 3000) {
                sleepThread.interrupt();
            }
        }

        LoopThread loopThread = new LoopThread(3);
        loopThread.start();
        long startLoopThread = System.currentTimeMillis();
        while (!loopThread.isAlive()) {
            if (System.currentTimeMillis() - startLoopThread > 3000) {
                loopThread.interrupt();
            }
        }
    }
}
