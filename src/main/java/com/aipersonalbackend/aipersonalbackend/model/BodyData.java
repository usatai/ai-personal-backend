package com.aipersonalbackend.aipersonalbackend.model;

import java.time.LocalDateTime;

import com.aipersonalbackend.aipersonalbackend.enumFile.UserGoalType;
import com.aipersonalbackend.aipersonalbackend.enumFile.UserSportType;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "body_metrics")
public class BodyData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "height")
    private Float height;

    @Column(name = "weight")
    private Float weight;

    @Column(name = "body_fat")
    private Float body_fat;

    @Column(name = "goal_weight")
    private Float goal_weight;

    @Column(name = "goal_body_fat")
    private Float goal_body_fat;

    @Column(name = "goal_type")
    private UserGoalType goal_type;

    @Column(name = "sport_type")
    private UserSportType sportType;

    @Column(name = "target_period")
    private String target_period;

    @Column(name = "created_at")
    private LocalDateTime created_at;

}
