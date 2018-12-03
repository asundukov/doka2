package io.cutebot.doka2.repository.entity;

import io.cutebot.doka2.model.BuildSizeType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Calendar;

@Entity
@Table(name = "build")
public class BuildEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer buildId;

    @Enumerated(EnumType.STRING)
    private BuildSizeType buildSizeType;

    private Long creatorId;

    private Calendar startTime;

    private Calendar finishTime;

    private Boolean buildActive;

    private Integer size;

    private Integer gainedExp;

    private Long destroyerId;

    private Calendar startDestroyTime;

    private Calendar finishDestroyTime;

    private Integer gainedDestroyExp;

    private Boolean destroyActive;

    public Integer getBuildId() {
        return buildId;
    }

    public BuildSizeType getBuildSizeType() {
        return buildSizeType;
    }

    public void setBuildSizeType(BuildSizeType buildSizeType) {
        this.buildSizeType = buildSizeType;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Calendar finishTime) {
        this.finishTime = finishTime;
    }

    public Boolean getBuildActive() {
        return buildActive;
    }

    public void setBuildActive(Boolean buildActive) {
        this.buildActive = buildActive;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getGainedExp() {
        return gainedExp;
    }

    public void setGainedExp(Integer gainedExp) {
        this.gainedExp = gainedExp;
    }

    public Long getDestroyerId() {
        return destroyerId;
    }

    public void setDestroyerId(Long destroyerId) {
        this.destroyerId = destroyerId;
    }

    public Calendar getStartDestroyTime() {
        return startDestroyTime;
    }

    public void setStartDestroyTime(Calendar startDestroyTime) {
        this.startDestroyTime = startDestroyTime;
    }

    public Calendar getFinishDestroyTime() {
        return finishDestroyTime;
    }

    public void setFinishDestroyTime(Calendar finishDestroyTime) {
        this.finishDestroyTime = finishDestroyTime;
    }

    public Integer getGainedDestroyExp() {
        return gainedDestroyExp;
    }

    public void setGainedDestroyExp(Integer gainedDestroyExp) {
        this.gainedDestroyExp = gainedDestroyExp;
    }

    public Boolean getDestroyActive() {
        return destroyActive;
    }

    public void setDestroyActive(Boolean destroyActive) {
        this.destroyActive = destroyActive;
    }
}
