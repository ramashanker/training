package com.rama.spring.app.controller;

import com.rama.spring.app.model.CacheMessage;
import com.rama.spring.app.repository.RedisMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private RedisMessageRepository redisMessageRepository;

    @PostMapping("/save")
    public CacheMessage save(@RequestParam String vin){
        byte[] message = "message for the vin".getBytes();
        CacheMessage cacheMessage= new CacheMessage(new Date(),message);
        redisMessageRepository.save(vin, cacheMessage);
        return cacheMessage;
    }

    @GetMapping
    public Map<String, List<CacheMessage>> findAllMessage(){
        return redisMessageRepository.findAllMessages();
    }

    @GetMapping("/{vin}")
    public List<CacheMessage>  getUser(@PathVariable String vin){
        return redisMessageRepository.findMessagesByVin(vin);
    }


    @DeleteMapping("/delete/{vin}")
    public void deleteVin(@PathVariable String vin){
        redisMessageRepository.deleteMessagesByVin(vin);

    }
    @DeleteMapping("/delete")
    public void deleteAll(){
        redisMessageRepository.deleteAllMessages();

    }
}