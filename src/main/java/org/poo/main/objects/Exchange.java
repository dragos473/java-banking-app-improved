package org.poo.main.objects;

import java.util.*;

public class Exchange {

    private final Map<String, Map<String, Double>> graph = new HashMap<>();

    /**
     * Add a new exchange rate to the graph
     * @param from sender currency
     * @param to receiver currency
     * @param rate exchange rate
     */
    public void addRate(final String from, final String to,
                        final double rate) {
        graph.computeIfAbsent(from, k -> new HashMap<>()).put(to, rate);
        graph.computeIfAbsent(to, k -> new HashMap<>()).put(from, 1 / rate);
    }

    /**
     *
     * Dijkstra's algorithm to find the shortest path between two currencies
     * @param from sender currency
     * @param to receiver currency
     * @return the exchange rate between the two currencies
     * @throws Exception if the currency is not found in the graph
     */
    public double getExchangeRate(final String from, final String to) throws Exception {
        if (from.equals(to)) {
            return 1.0;
        }

        if (!graph.containsKey(from) || !graph.containsKey(to)) {
            throw new Exception("Currency not found in the graph");
        }

        Map<String, Double> distances = new HashMap<>();
        PriorityQueue<Rate> queue = new PriorityQueue<>(Comparator
                .comparingDouble(node -> node.cost));
        Set<String> visited = new HashSet<>();

        distances.put(from, 1.0);
        queue.add(new Rate(from, 1.0));

        while (!queue.isEmpty()) {
            Rate current = queue.poll();
            if (!visited.add(current.currency)) {
                continue;
            }

            if (current.currency.equals(to)) {
                return current.cost;
            }

            for (Map.Entry<String, Double> neighbor : graph.get(current.currency).entrySet()) {
                if (!visited.contains(neighbor.getKey())) {
                    double newDist = current.cost * neighbor.getValue();
                    if (newDist < distances.getOrDefault(neighbor.getKey(), Double.MAX_VALUE)) {
                        distances.put(neighbor.getKey(), newDist);
                        queue.add(new Rate(neighbor.getKey(), newDist));
                    }
                }
            }
        }

        throw new Exception("No path found between the currencies");
    }
}
