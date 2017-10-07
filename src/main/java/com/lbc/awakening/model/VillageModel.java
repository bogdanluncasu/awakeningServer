package com.lbc.awakening.model;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table
public class VillageModel implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String villageName;
    private int castleLevel;
    private int farmLevel;
    private int wallLevel;
    private int woodWorkLevel;
    private int ironMineLevel;
    private int rockMineLevel;
    private int politicalResidenceLevel;
    private int barracksLevel;
    private int towersLevel;
    private int villageType;
    private int extraBuilding;
    @OneToOne
    private MapModel mapModel;

    public MapModel getMapModel() {
        return mapModel;
    }

    public void setMapModel(MapModel mapModel) {
        this.mapModel = mapModel;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public int getCastleLevel() {
        return castleLevel;
    }

    public void setCastleLevel(int castleLevel) {
        this.castleLevel = castleLevel;
    }

    public int getFarmLevel() {
        return farmLevel;
    }

    public void setFarmLevel(int farmLevel) {
        this.farmLevel = farmLevel;
    }

    public int getWallLevel() {
        return wallLevel;
    }

    public void setWallLevel(int wallLevel) {
        this.wallLevel = wallLevel;
    }

    public int getWoodWorkLevel() {
        return woodWorkLevel;
    }

    public void setWoodWorkLevel(int woodWorkLevel) {
        this.woodWorkLevel = woodWorkLevel;
    }

    public int getIronMineLevel() {
        return ironMineLevel;
    }

    public void setIronMineLevel(int ironMineLevel) {
        this.ironMineLevel = ironMineLevel;
    }

    public int getRockMineLevel() {
        return rockMineLevel;
    }

    public void setRockMineLevel(int rockMineLevel) {
        this.rockMineLevel = rockMineLevel;
    }

    public int getPoliticalResidenceLevel() {
        return politicalResidenceLevel;
    }

    public void setPoliticalResidenceLevel(int politicalResidenceLevel) {
        this.politicalResidenceLevel = politicalResidenceLevel;
    }

    public int getBarracksLevel() {
        return barracksLevel;
    }

    public void setBarracksLevel(int barracksLevel) {
        this.barracksLevel = barracksLevel;
    }

    public int getTowersLevel() {
        return towersLevel;
    }

    public void setTowersLevel(int towersLevel) {
        this.towersLevel = towersLevel;
    }

    public int getVillageType() {
        return villageType;
    }

    public void setVillageType(int villageType) {
        this.villageType = villageType;
    }

    public int getExtraBuilding() {
        return extraBuilding;
    }

    public void setExtraBuilding(int extraBuilding) {
        this.extraBuilding = extraBuilding;
    }
}
