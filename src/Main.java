
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger a = new AtomicInteger(0);
    public static AtomicInteger b = new AtomicInteger(0);
    public static AtomicInteger c = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        Runnable logicA = () -> {
            for (String s : texts) {
                if (s.length() == 3) {
                    if (s.charAt(0) == s.charAt(2)) {
                        a.getAndIncrement();
                    }
                }
                if (s.length() == 4) {
                    if (s.charAt(0) == s.charAt(3) && s.charAt(1) == s.charAt(2)) {
                        b.getAndIncrement();
                    }
                }
                if (s.length() == 5) {
                    if (s.charAt(0) == s.charAt(4) && s.charAt(1) == s.charAt(3)) {
                        c.getAndIncrement();
                    }
                }
            }
        };

        Runnable logicB = () -> {
            for (String s : texts) {
                if (s.length() == 3) {
                    if (s.charAt(0) == s.charAt(1) && s.charAt(1) == s.charAt(2)) {
                        a.getAndIncrement();
                    }
                }
                if (s.length() == 4) {
                    if (s.charAt(0) == s.charAt(1) && s.charAt(1) == s.charAt(2) &&
                            s.charAt(2) == s.charAt(3)) {
                        b.getAndIncrement();
                    }
                }
                if (s.length() == 5) {
                    if (s.charAt(0) == s.charAt(1) && s.charAt(1) == s.charAt(2) &&
                            s.charAt(2) == s.charAt(3) && s.charAt(3) == s.charAt(4)) {
                        c.getAndIncrement();
                    }
                }
            }
        };

        Runnable logicC = () -> {
            for (String s : texts) {
                if (s.length() == 3) {
                    if (s.charAt(0) <= s.charAt(1) && s.charAt(0) <= s.charAt(2)
                            && s.charAt(1) <= s.charAt(2)) {
                        a.getAndIncrement();
                    }
                }
                if (s.length() == 4) {
                    if (s.charAt(0) <= s.charAt(1) && s.charAt(0) <= s.charAt(2) &&
                            s.charAt(0) <= s.charAt(3) && s.charAt(1) <= s.charAt(2) &&
                            s.charAt(1) <= s.charAt(3) && s.charAt(2) <= s.charAt(3)) {
                        b.getAndIncrement();
                    }
                }
                if (s.length() == 5) {
                    if (s.charAt(0) <= s.charAt(1) && s.charAt(0) <= s.charAt(2) &&
                            s.charAt(0) <= s.charAt(3) && s.charAt(0) <= s.charAt(4) &&
                            s.charAt(1) <= s.charAt(2) && s.charAt(1) <= s.charAt(3) &&
                            s.charAt(1) <= s.charAt(4) && s.charAt(2) <= s.charAt(3) &&
                            s.charAt(2) <= s.charAt(4) && s.charAt(3) <= s.charAt(4)) {
                        c.getAndIncrement();
                    }
                }
            }
        };

        Thread thread = new Thread(logicA);
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        thread.start();

        Thread thread1 = new Thread(logicB);
        try {
            thread1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        thread1.start();

        Thread thread2 = new Thread(logicC);
        try {
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        thread2.start();
        thread1.join();
        thread2.join();
        thread.join();

        System.out.println(" Красивых слов с длиной 3:  " + a +
                " шт \n Красивых слов с длиной 4:  " + b + " шт \n Красивых слов с длиной 5:  " + c + " шт");

    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}