package com.rippling.test.coding;

import java.util.*;

public class CurrencyExchangeRateProblem {

    public static void main(String[] args) {
        ExchangeRate rate1 = new ExchangeRate("USD", "JPY", 110.0);
        ExchangeRate rate2 = new ExchangeRate("USD", "AUD", 0.1);
        ExchangeRate rate3 = new ExchangeRate("USD", "MOS", 1.0);
        ExchangeRate rate4 = new ExchangeRate("AUD", "JPY", 4.0);
        ExchangeRate rate5 = new ExchangeRate("MOS", "JPY", 2.0);

        List<ExchangeRate> exchangeRateList = List.of(rate1,rate2,rate3,rate4,rate5);
        var adjList = prepareAdjList(exchangeRateList);
        var rate = findShortestPath("USD","JPY",adjList);
        System.out.println(rate);
        rate = findCheapestPath("USD","JPY",adjList);
        System.out.println(rate);
    }

    public static Map<String, Map<String,Double>> prepareAdjList(List<ExchangeRate> exchangeRates){
        Map<String,Map<String,Double>> adjList = new HashMap<>();
        for(ExchangeRate exchangeRate : exchangeRates){
            adjList.putIfAbsent(exchangeRate.source,new HashMap<>());
            adjList.get(exchangeRate.source).put(exchangeRate.dest, exchangeRate.rate);
            /*adjList.putIfAbsent(exchangeRate.dest,new HashMap<>());
            adjList.get(exchangeRate.dest).put(exchangeRate.source,1/exchangeRate.rate);*/
        }
        return adjList;
    }

    public static Double findShortestPath(String source, String dest, Map<String,Map<String,Double>> currencyMap){
        if(source.equals(dest)){
            return 1.0;
        }
        if(!currencyMap.containsKey(source) || !currencyMap.containsKey(dest)){
            return 0.0;
        }
        Queue<Pair> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.add(new Pair(source,1.0));
        visited.add(source);
        while(!queue.isEmpty()){
            Pair poll = queue.poll();
            if(poll.currency.equals(dest)){
                return poll.rate;
            }
            if(currencyMap.get(poll.currency).containsKey(dest)){
                return poll.rate *(currencyMap.get(poll.currency).get(dest));
            }
            Map<String,Double> next = currencyMap.get(poll.currency);
            for(var entry : next.entrySet()){
                double newRate = poll.rate* entry.getValue();
                if(!visited.contains(entry.getKey())){
                    visited.add(entry.getKey());
                    queue.add(new Pair(entry.getKey(),newRate));
                }
            }
        }
        return 0.0;
    }

    public static Double findCheapestPath(String source, String dest, Map<String,Map<String,Double>> currencyMap){
        if(source.equals(dest)){
            return 1.0;
        }
        if(!currencyMap.containsKey(source) || !currencyMap.containsKey(dest)){
            return 0.0;
        }
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        Map<String,Double> visited = new HashMap<>();
        pq.add(new Pair(source,1.0));
        while(!pq.isEmpty()){
            Pair poll = pq.poll();
            if(visited.containsKey(poll.currency) && visited.get(poll.currency)<=poll.rate)
                continue;
            visited.put(poll.currency,poll.rate);
            Map<String,Double> next = currencyMap.get(poll.currency);
            for(var entry : next.entrySet()){
                double newRate = poll.rate* entry.getValue();
                if(entry.getKey().equals(source))
                    continue;
                if(visited.containsKey(entry.getKey()) && visited.get(entry.getKey())>newRate){
                    visited.put(entry.getKey(),newRate);
                }
                pq.add(new Pair(entry.getKey(),newRate));
            }
        }
        return visited.get(dest);
    }

}

class ExchangeRate{
    String source;
    String dest;
    Double rate;

    public ExchangeRate(String source, String dest, Double rate) {
        this.source = source;
        this.dest = dest;
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "source='" + source + '\'' +
                ", dest='" + dest + '\'' +
                ", rate=" + rate +
                '}';
    }
}

class Pair implements Comparable<Pair>{
    String currency;
    Double rate;

    public Pair(String currency, Double rate) {
        this.currency = currency;
        this.rate = rate;
    }

    @Override
    public int compareTo(Pair pair) {
        return Double.compare(this.rate,pair.rate);
    }

    @Override
    public String toString() {
        return "Pair{" +
                "currency='" + currency + '\'' +
                ", rate=" + rate +
                '}';
    }
}
