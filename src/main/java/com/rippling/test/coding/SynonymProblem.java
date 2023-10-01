package com.rippling.test.coding;

import java.util.*;

public class SynonymProblem {

    public static void main(String[] args) {
        String[] syn8 = {"main", "primary"};
        String[] syn9 = {"rating", "score"};
        //String[] syn10 = {"primary", "secondary"};

        List<String[]> list2 = List.of(syn8,syn9/*,syn10*/);
        var synonymMap = synonymMapSimple(list2);
        System.out.println(synonymMap);
        List<String> sentences = new ArrayList<>();
        sentences.add("primary email");
        sentences.add("main email");
        sentences.add("performance rating");
        sentences.add("performance score");
        sentences.add("secondary email");
        sentences.add("bank account country");

        var result = similarityCheck(sentences,synonymMap);
        System.out.println(result);
    }

    public static Map<String,String> synonymMapSimple(List<String[]> synonyms){
        Map<String,String> synonymMap = new HashMap<>();
        for(String[] synonym : synonyms){
            String w1 = synonym[0];
            String w2 = synonym[1];

            if(synonymMap.containsKey(w1) || synonymMap.containsKey(w2)){
                String value = synonymMap.get(w1);
                if(value==null){
                    value = synonymMap.get(w2);
                }
                synonymMap.put(w1,value);
                synonymMap.put(w2,value);
            }else{
                synonymMap.put(w1,w1);
                synonymMap.put(w2,w1);
            }
        }
        return synonymMap;
    }

    public static Map<String,String> synonymMap(List<String[]> synonyms){
        Map<String,String> synonymMap = new HashMap<>();
        Map<String,Set<String>> adjList = new HashMap<>();
        Set<String> allWordsSet = new HashSet<>();
        for(String[] synonym : synonyms){
            String w1 = synonym[0];
            String w2 = synonym[1];
            allWordsSet.add(w1);
            allWordsSet.add(w2);

            Set<String> syn1 = adjList.getOrDefault(w1,new HashSet<>());
            syn1.add(w2);
            adjList.put(w1,syn1);

            Set<String> syn2 = adjList.getOrDefault(w2,new HashSet<>());
            syn2.add(w1);
            adjList.put(w2,syn2);

            List<String> allWords = new ArrayList<>(allWordsSet);
            while(!allWords.isEmpty()){
                Queue<String> queue = new LinkedList<>();
                queue.add(allWords.remove(0));
                Set<String> synonymSet = new HashSet<>();
                while(!queue.isEmpty()){
                    String poll = queue.poll();
                    synonymSet.add(poll);
                    for(String s : adjList.get(poll)){
                        if(!synonymSet.contains(s)){
                            queue.add(s);
                            allWords.remove(s);
                            synonymSet.add(s);
                        }
                    }
                }
                String head=null;
                for(String s : synonymSet){
                    if(head==null){
                        head = s;
                    }
                    synonymMap.put(s,head);
                }
            }
        }
        return synonymMap;
    }

    public static Map<String,List<String>> similarityCheck(List<String> sentences, Map<String,String> synonymMap){
        Map<String,List<String>> synonyms = new HashMap<>();
        for(String sentence : sentences){
            String normalized = normalizeSentence(sentence,synonymMap);
            if(synonyms.containsKey(normalized)){
                synonyms.get(normalized).add(sentence);
            }else{
                List<String> synList = new ArrayList<>();
                synList.add(sentence);
                synonyms.put(normalized,synList);
            }
        }
        return synonyms;
    }

    private static String normalizeSentence(String sentence, Map<String,String> synonymMap){
        String[] words = sentence.split(" ");
        StringBuilder normalizedSentence = new StringBuilder();
        for(String word : words){
           normalizedSentence.append(synonymMap.getOrDefault(word,word)).append(" ");
        }
        return normalizedSentence.toString();
    }
    

}
