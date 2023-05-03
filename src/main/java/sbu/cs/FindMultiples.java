package sbu.cs;
import java.util.ArrayList;

/*
    In this exercise, you must write a multithreaded program that finds all
    integers in the range [1, n] that are divisible by 3, 5, or 7. Return the
    sum of all unique integers as your answer.
    Note that an integer such as 15 (which is a multiple of 3 and 5) is only
    counted once.

    The Positive integer n > 0 is given to you as input. Create as many threads as
    you need to solve the problem. You can use a Thread Pool for bonus points.

    Example:
    Input: n = 10
    Output: sum = 40
    Explanation: Numbers in the range [1, 10] that are divisible by 3, 5, or 7 are:
    3, 5, 6, 7, 9, 10. The sum of these numbers is 40.

    Use the tests provided in the test folder to ensure your code works correctly.
 */

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FindMultiples
{
    public static ArrayList<Integer> ans = new ArrayList<>();

    public static class Task implements Runnable {
        int n;
        int number;
        Lock lock;

        public Task(int n, int number, Lock lock) {
            this.n = n;
            this.number = number;
            this.lock = lock;
        }

        @Override
        public void run() {
           if (n < 3)
           {
               lock.lock();
               ans.add(0);
               lock.unlock();
           }
           else
           {
               for (int i=3; i<=this.n; i++)
               {
                   if (i % this.number == 0)
                   {
                       lock.lock();
                       ans.add(i);
                       lock.unlock();
                   }
               }
           }

        }
    }

    public ArrayList<Integer> uniqueAnswer ()
    {
        ArrayList<Integer> uniqueAns = new ArrayList<>();
        uniqueAns.add(ans.get(0));
        for (int i=1; i<ans.size(); i++)
        {
            if (!uniqueAns.contains(ans.get(i)))
            {
                uniqueAns.add(ans.get(i));
            }
        }

        return uniqueAns;
    }


    /*
    The getSum function should be called at the start of your program.
    New Threads and tasks should be created here.
    */
    public int getSum(int n) {
        int sum = 0;

        ReentrantLock lock = new ReentrantLock();
        Thread T1 = new Thread(new Task(n , 3, lock));
        Thread T2 = new Thread(new Task(n , 5, lock));
        Thread T3 = new Thread(new Task(n , 7, lock));

        T1.start();
        T2.start();
        T3.start();

        try {
            T1.join();
            T2.join();
            T3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList<Integer> uniqueAns = uniqueAnswer();
        for (int i=0; i<uniqueAns.size(); i++)
        {
            sum += uniqueAns.get(i);
        }

        return sum;
    }

    public static void main(String[] args) {
    }
}
