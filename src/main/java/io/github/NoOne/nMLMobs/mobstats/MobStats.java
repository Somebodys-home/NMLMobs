package io.github.NoOne.nMLMobs.mobstats;

import java.util.HashMap;

public class MobStats {
    private int level;

    private double maxHealth;
    private double maxOverhealth;

    // damage stats
    private int physicalDamage;
    private int fireDamage;
    private int coldDamage;
    private int earthDamage;
    private int lightningDamage;
    private int airDamage;
    private int radiantDamage;
    private int necroticDamage;
    private int pureDamage;
    private int critChance;
    private int critDamage;

    // defense stats
    private int evasion;
    private int defense;
    private int physicalResist;
    private int fireResist;
    private int coldResist;
    private int earthResist;
    private int lightningResist;
    private int airResist;
    private int radiantResist;
    private int necroticResist;

    public MobStats(int level, double maxHealth, double maxOverhealth,
                 int physicalDamage, int fireDamage, int coldDamage, int earthDamage, int lightningDamage, int airDamage, int radiantDamage, int necroticDamage, int pureDamage,
                 int critChance, int critDamage,
                 int evasion, int defense, int physicalResist, int fireResist, int coldResist, int earthResist, int lightningResist, int airResist, int radiantResist, int necroticResist) {

        this.level = level;

        this.maxHealth = maxHealth;
        this.maxOverhealth = maxOverhealth;

        this.physicalDamage = physicalDamage;
        this.fireDamage = fireDamage;
        this.coldDamage = coldDamage;
        this.earthDamage = earthDamage;
        this.lightningDamage = lightningDamage;
        this.airDamage = airDamage;
        this.radiantDamage = radiantDamage;
        this.necroticDamage = necroticDamage;
        this.pureDamage = pureDamage;
        this.critChance = critChance;
        this.critDamage = critDamage;

        this.evasion = evasion;
        this.defense = defense;
        this.physicalResist = physicalResist;
        this.fireResist = fireResist;
        this.coldResist = coldResist;
        this.earthResist = earthResist;
        this.lightningResist = lightningResist;
        this.airResist = airResist;
        this.radiantResist = radiantResist;
        this.necroticResist = necroticResist;
    }

    public HashMap<String, Integer> getAllDamages() {
        HashMap<String, Integer> damages = new HashMap<>();

        damages.put("physicaldamage", physicalDamage);
        damages.put("firedamage", fireDamage);
        damages.put("colddamage", coldDamage);
        damages.put("earthdamage", earthDamage);
        damages.put("lightningdamage", lightningDamage);
        damages.put("airdamage", airDamage);
        damages.put("radiantdamage", radiantDamage);
        damages.put("necroticdamage", necroticDamage);
        damages.put("puredamage", pureDamage);

        damages.entrySet().removeIf(entry -> entry.getValue() <= 0);
        return damages;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public double getMaxOverhealth() {
        return maxOverhealth;
    }

    public double getmaxHealth() {
        return maxHealth;
    }

    public void setmaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setMaxOverhealth(double maxOverhealth) {
        this.maxOverhealth = maxOverhealth;
    }

    public int getEvasion() {
        return evasion;
    }

    public void setEvasion(int evasion) {
        this.evasion = evasion;
    }

    public int getPhysicalDamage() {
        return physicalDamage;
    }

    public void setPhysicalDamage(int physicalDamage) {
        this.physicalDamage = physicalDamage;
    }

    public int getFireDamage() {
        return fireDamage;
    }

    public void setFireDamage(int fireDamage) {
        this.fireDamage = fireDamage;
    }

    public int getColdDamage() {
        return coldDamage;
    }

    public void setColdDamage(int coldDamage) {
        this.coldDamage = coldDamage;
    }

    public int getEarthDamage() {
        return earthDamage;
    }

    public void setEarthDamage(int earthDamage) {
        this.earthDamage = earthDamage;
    }

    public int getAirDamage() {
        return airDamage;
    }

    public void setAirDamage(int airDamage) {
        this.airDamage = airDamage;
    }

    public int getPureDamage() {
        return pureDamage;
    }

    public void setPureDamage(int pureDamage) {
        this.pureDamage = pureDamage;
    }

    public int getNecroticDamage() {
        return necroticDamage;
    }

    public void setNecroticDamage(int necroticDamage) {
        this.necroticDamage = necroticDamage;
    }

    public int getRadiantDamage() {
        return radiantDamage;
    }

    public void setRadiantDamage(int radiantDamage) {
        this.radiantDamage = radiantDamage;
    }

    public int getLightningDamage() {
        return lightningDamage;
    }

    public void setLightningDamage(int lightningDamage) {
        this.lightningDamage = lightningDamage;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getNecroticResist() {
        return necroticResist;
    }

    public void setNecroticResist(int necroticResist) {
        this.necroticResist = necroticResist;
    }

    public int getRadiantResist() {
        return radiantResist;
    }

    public void setRadiantResist(int radiantResist) {
        this.radiantResist = radiantResist;
    }

    public int getLightningResist() {
        return lightningResist;
    }

    public void setLightningResist(int lightningResist) {
        this.lightningResist = lightningResist;
    }

    public int getAirResist() {
        return airResist;
    }

    public void setAirResist(int airResist) {
        this.airResist = airResist;
    }

    public int getEarthResist() {
        return earthResist;
    }

    public void setEarthResist(int earthResist) {
        this.earthResist = earthResist;
    }

    public int getColdResist() {
        return coldResist;
    }

    public void setColdResist(int coldResist) {
        this.coldResist = coldResist;
    }

    public int getFireResist() {
        return fireResist;
    }

    public void setFireResist(int fireResist) {
        this.fireResist = fireResist;
    }

    public int getPhysicalResist() {
        return physicalResist;
    }

    public void setPhysicalResist(int physicalResist) {
        this.physicalResist = physicalResist;
    }

    public int getCritChance() {
        return critChance;
    }

    public void setCritChance(int critChance) {
        this.critChance = critChance;
    }

    public int getCritDamage() {
        return critDamage;
    }

    public void setCritDamage(int critDamage) {
        this.critDamage = critDamage;
    }
}