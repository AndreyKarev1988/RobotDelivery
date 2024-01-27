import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {

        String[] routs = new String[1000];

        for (int i = 0; i < routs.length; i++) {
            routs[i] = generateRoute("RLRFR", 100);
        }

        List<Thread> threads = new ArrayList<>();

        for (String rout : routs) {
            Runnable logic = () -> {
                Integer countR = 0;
                for (int i = 0; i < rout.length(); i++) {
                    if (rout.charAt(i) == 'R') {
                        countR++;
                    }
                }
                System.out.println(rout.substring(0, 100) + " -> " + countR);

                synchronized (countR) {
                    if (sizeToFreq.containsKey(countR)) {
                        sizeToFreq.put(countR, sizeToFreq.get(countR) + 1);
                    } else {
                        sizeToFreq.put(countR, 1);
                    }
                }
            };
            Thread thread = new Thread(logic);
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        int maxV = 0;
        Integer maxK = 0;
        for (Integer key : sizeToFreq.keySet()) {
            if (sizeToFreq.get(key) > maxV) {
                maxV = sizeToFreq.get(key);
                maxK = key;
            }
        }
        System.out.println();
        System.out.println("Наибольшая часта повторений " + maxK + " - повторялось " + maxV + " раз");
        sizeToFreq.remove(maxK);

        System.out.println();
        System.out.println("Другое кол-во повторений:");
        for (Integer key : sizeToFreq.keySet()) {
            int value = sizeToFreq.get(key);
            System.out.println("- " + key + " (" + value + " раз)");
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}