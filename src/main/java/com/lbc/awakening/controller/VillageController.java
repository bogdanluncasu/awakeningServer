package com.lbc.awakening.controller;

import org.springframework.web.bind.annotation.*;

@RestController("/village")
public class VillageController {

    @PostMapping("/create")
    public void createVillage(@RequestBody String encryptedData){
        //TODO: decrypt data and parse to json, and based on data create a new village
    }

    @PutMapping("/{villageId}/upgrade")
    public void updateVillage(@RequestBody String encryptedData, @RequestParam long villageId){
        //TODO: upgrade village based on encrypted data (! upgrade building)
    }

    @PostMapping("/{villageId}/attack")
    public void attackVillage(@RequestBody String encryptedData, @RequestParam long villageId){
        //TODO: calculate distance between attacker and victim and put the attack in database. (! should change return type)
    }


    //TODO: should create an daemon/job for gold upgrade
    //TODO: take into consideration heroes attributes
}
