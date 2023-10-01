package com.rippling.test.coding;

import java.util.*;

interface IKeyValueStore{
    void set(String key,String value);
    String get(String key);
    String delete(String key);
    void clear();
    Set<Map.Entry<String,String>> entrySet();
    Map<String,String> getInternalCopy();
}

class KeyValueStore implements IKeyValueStore{
    Map<String,String> keyValueStore = new HashMap<>();

    public KeyValueStore() {

    }

    public KeyValueStore(Map<String, String> keyValueStore) {
        this.keyValueStore.putAll(keyValueStore);
    }

    @Override
    public void set(String key, String value) {
        keyValueStore.put(key, value);
    }

    @Override
    public String get(String key) {
        if(!keyValueStore.containsKey(key)){
            throw new IllegalArgumentException("Key: "+key+" not found.");
        }
        return keyValueStore.get(key);
    }

    @Override
    public String delete(String key) {
        if(!keyValueStore.containsKey(key)){
            throw new IllegalArgumentException("Key: "+key+" not found.");
        }
        return keyValueStore.remove(key);
    }

    @Override
    public void clear() {
        keyValueStore.clear();
    }

    @Override
    public Set<Map.Entry<String, String>> entrySet() {
        return keyValueStore.entrySet();
    }

    @Override
    public Map<String, String> getInternalCopy() {
        return new HashMap<>(keyValueStore);
    }
}

interface IKeyValueStoreTransactional extends IKeyValueStore{
    void begin();
    void commit();
    void rollback();
    void end();
}

class Transaction extends KeyValueStore{
    Set<String> deletedKeys = new HashSet<>();

    public Transaction(Map<String,String> map){
        super(map);
    }

    @Override
    public String delete(String key) {
        deletedKeys.add(key);
        return super.delete(key);
    }
}

class KeyValueStoreTransactional implements IKeyValueStoreTransactional{
    IKeyValueStore globalStore = new KeyValueStore();
    Stack<Transaction> transactions = new Stack<>();

    public KeyValueStoreTransactional() {
    }

    @Override
    public void set(String key, String value) {
        if(transactions.isEmpty()){
            globalStore.set(key, value);
        }else{
            var txnStore = transactions.peek();
            txnStore.set(key, value);
        }
    }

    @Override
    public String get(String key) {
        if(transactions.isEmpty()){
            return globalStore.get(key);
        }
        var txnStore = transactions.peek();
        return txnStore.get(key);
    }

    @Override
    public String delete(String key) {
        if(transactions.isEmpty()){
            return globalStore.delete(key);
        }
        var txnStore = transactions.peek();
        return txnStore.delete(key);
    }

    @Override
    public void clear() {
        if(transactions.isEmpty()){
            globalStore.clear();
        }else{
            var txnStore = transactions.peek();
            txnStore.clear();
        }
    }

    @Override
    public Set<Map.Entry<String, String>> entrySet() {
        if(transactions.isEmpty()){
            return globalStore.entrySet();
        }
        var txnStore = transactions.peek();
        return txnStore.entrySet();
    }

    @Override
    public Map<String, String> getInternalCopy() {
        return null;
    }

    @Override
    public void begin() {
        if(transactions.isEmpty()){
            Transaction transaction =new Transaction(globalStore.getInternalCopy());
            transactions.push(transaction);
        }else{
            transactions.push(new Transaction(transactions.peek().getInternalCopy()));
        }
    }

    @Override
    public void commit() {
        if(transactions.isEmpty()){
            throw new IllegalStateException("No Active Transaction Found.");
        }
        Transaction current = transactions.pop();
        Transaction next = transactions.isEmpty()?null:transactions.peek();
        for(var entry : current.entrySet()){
            if(next==null){
                globalStore.set(entry.getKey(),entry.getValue());
            }else {
                next.set(entry.getKey(), entry.getValue());
            }
        }

        for(var key : current.deletedKeys){
            if(next==null){
                globalStore.delete(key);
            }else{
                next.delete(key);
            }
        }
    }

    @Override
    public void rollback() {
        end();
    }

    @Override
    public void end() {
        if(transactions.isEmpty()){
            throw new IllegalStateException("No Active Transaction Found.");
        }
        transactions.pop();
    }
}

public class KeyValueStoreProblem {
    public static void main(String[] args) {
        KeyValueStoreTransactional db = new KeyValueStoreTransactional();
        db.set("Key1","1");
        db.set("Key2","2");
        System.out.println(db.entrySet());
        db.begin();
        db.set("Key3","3");
        db.commit();
        System.out.println(db.entrySet());
        System.out.println(db.get("Key1"));
        System.out.println(db.get("Key2"));
        System.out.println(db.get("Key3"));
        db.begin();
        db.delete("Key3");
        db.commit();
        System.out.println(db.get("Key3"));
    }

}
