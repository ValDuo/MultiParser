package resources;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class lab1 {

    private static final int SIMULATION_TIME = 1000;
    private static final int CHANNELS = 5;
    private static final int QUEUE_SIZE = 4;
    private static final double LAMBDA = 18.0;
    private static final double SERVICE_TIME = 15.0 / 60.0;

    static class Request {
        int id;
        int endTime;

        Request(int id, int endTime) {
            this.id = id;
            this.endTime = endTime;
        }
    }

    public static void main(String[] args) {
        Random random = new Random();
        Queue<Integer> requestQueue = new LinkedList<>();
        Request[] channels = new Request[CHANNELS];
        int[] stateCounts = new int[QUEUE_SIZE + CHANNELS + 1];
        int totalRequests = 0, acceptedRequests = 0, queuedRequests = 0, deniedRequests = 0, freeChannelTime = 0;

        double mu = 1.0 / SERVICE_TIME;

        System.out.println("Время\tЗаявка\tКанал1\tКанал2\tКанал3\tКанал4\tКанал5\tОчередь\tОтказано\tПринято");
        System.out.println("______________________________________________________________________________________");

        for (int t = 1; t <= SIMULATION_TIME; ++t) {
            boolean requestArrived = random.nextDouble() < LAMBDA / 60.0;
            int requestId = requestArrived ? ++totalRequests : 0;

            for (int i = 0; i < CHANNELS; i++) {
                if (channels[i] != null && channels[i].endTime == t) {
                    channels[i] = null;
                }
                if (channels[i] == null && !requestQueue.isEmpty()) {
                    int id = requestQueue.poll();
                    channels[i] = new Request(id, t + (int)(SERVICE_TIME * 60));
                }
            }

            if (requestArrived) {
                boolean assigned = false;
                for (int i = 0; i < CHANNELS; i++) {
                    if (channels[i] == null) {
                        channels[i] = new Request(requestId, t + (int)(SERVICE_TIME * 60));
                        assigned = true;
                        break;
                    }
                }

                if (!assigned) {
                    if (requestQueue.size() < QUEUE_SIZE) {
                        requestQueue.offer(requestId);
                        queuedRequests++;
                        acceptedRequests++;
                    } else {
                        deniedRequests++;
                    }
                } else {
                    acceptedRequests++;
                }
            }

            for (Request ch : channels) {
                if (ch == null) {
                    freeChannelTime++;
                }
            }

            int busyChannels = 0;
            for (Request ch : channels) {
                if (ch != null) {
                    busyChannels++;
                }
            }
            int queueSize = requestQueue.size();
            int totalBusy = busyChannels + queueSize;
            if (totalBusy <= QUEUE_SIZE + CHANNELS) {
                stateCounts[totalBusy]++;
            }

            System.out.print(String.format("%-8d%-8s", t, (requestId != 0 ? requestId : "")));
            for (Request ch : channels) {
                System.out.print(String.format("%-8s", (ch != null ? ch.id : "-")));
            }
            System.out.println(String.format("%-8d%-10d%-8d", requestQueue.size(), deniedRequests, acceptedRequests));
        }

        double avgFreeChannels = (double) freeChannelTime / SIMULATION_TIME;
        double acceptanceProb = (double) acceptedRequests / totalRequests;
        double queueProb = (double) queuedRequests / totalRequests;

        System.out.println("\n=== Результаты симуляции ===");
        System.out.println("Среднее количество свободных каналов: " + avgFreeChannels);
        System.out.println("Принято: " + acceptanceProb * 100 + "%");
        System.out.println("В очереди: " + queueProb * 100 + "%");
        // Ответы на вопросы варианта №7
        double rho = LAMBDA / (CHANNELS * mu);
        if (rho < 1) {
            System.out.println("\nСтационарный режим работы системы существует.");
        } else {
            System.out.println("\nСтационарный режим работы системы не существует.");
        }

        double L = (LAMBDA / mu) * (1 + Math.pow(rho, CHANNELS) / factorial(CHANNELS) * (1 / (1 - rho)));
        System.out.println("Среднее количество заявок в системе: " + L);

        double W = 1 / (mu - LAMBDA / 60.0);
        System.out.println("Среднее время пребывания заявки в системе: " + W + " часов");

        double Pn = Math.pow(rho, CHANNELS) / factorial(CHANNELS) * (1 / (1 - rho));
        System.out.println("Вероятность того, что все каналы заняты: " + Pn);

        double Wfree = (1 / mu) - W;
        if (Wfree < 0) {
            Wfree = 0;  // Исправление, чтобы время простоя канала не было отрицательным
        }
        System.out.println("Среднее время простоя одного канала: " + Wfree + " часов");


    }

    public static double factorial(int n) {
        double result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}
