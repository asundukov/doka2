package io.cutebot.doka2.repository.entity;

import io.cutebot.doka2.model.Race;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.util.Calendar;

import static javax.persistence.EnumType.STRING;

@Entity
@Table(name =  "hero")
public class HeroEntity {
    @Id
    private Long heroId;

    private String firstName;

    private String lastName;

    private String userName;

    private String languageCode;

    private Calendar creationTime;

    private Calendar lastRequest;

    private String heroName;

    private Integer toolId;

    private Integer weaponId;

    private Integer experience;

    @Enumerated(value = STRING)
    private Race heroRace;

    public Long getHeroId() {
        return heroId;
    }

    public void setHeroId(Long heroId) {
        this.heroId = heroId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public void setCreationTime(Calendar creationTime) {
        this.creationTime = creationTime;
    }

    public Calendar getCreationTime() {
        return creationTime;
    }

    @PrePersist
    public void setCreationTime() {
        this.creationTime = Calendar.getInstance();
    }

    public Calendar getLastRequest() {
        return lastRequest;
    }

    public void setLastRequest(Calendar lastRequest) {
        this.lastRequest = lastRequest;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public String getHeroName() {
        return heroName;
    }

    public Race getHeroRace() {
        return heroRace;
    }

    public void setHeroRace(Race heroRace) {
        this.heroRace = heroRace;
    }

    public Integer getToolId() {
        return toolId;
    }

    public void setToolId(Integer toolId) {
        this.toolId = toolId;
    }

    public Integer getWeaponId() {
        return weaponId;
    }

    public void setWeaponId(Integer weaponId) {
        this.weaponId = weaponId;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }
}
