package com.rama.spring.app.repository;

import com.google.gson.Gson;
import com.rama.spring.app.model.CacheMessage;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class RedisMessageRepository {

    private HashOperations hashOperations;

    private RedisTemplate redisTemplate;
    private Gson jsonParser;
    public RedisMessageRepository(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
        this.hashOperations = this.redisTemplate.opsForHash();
        jsonParser = new Gson();
    }

    public void save(String vin, CacheMessage cacheMessage){

        if( hashOperations.hasKey("MESSAGE",vin)){
            List<CacheMessage> cacheMessages= (List<CacheMessage>) hashOperations.get("MESSAGE", vin);
            cacheMessages.add(cacheMessage);
            hashOperations.put("MESSAGE", vin, cacheMessages);

        }else{
            List<CacheMessage> cacheMessages = new ArrayList<>();
            cacheMessages.add(cacheMessage);
            hashOperations.put("MESSAGE", vin, cacheMessages);
        }


    }
    public Map<String, List<CacheMessage>> findAllMessages(){
        return hashOperations.entries("MESSAGE");

    }

    public List<CacheMessage> findMessagesByVin(String vin){

        return (List<CacheMessage>) hashOperations.get("MESSAGE", vin);
    }


    public void deleteMessagesByVin(String vin){

        hashOperations.delete("MESSAGE", vin);
    }

    public void deleteAllMessages( ){
        hashOperations.delete("MESSAGE");

    }


}
