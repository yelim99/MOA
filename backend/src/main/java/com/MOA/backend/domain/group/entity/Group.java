package com.MOA.backend.domain.group.entity;

import com.MOA.backend.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "`group`")
public class Group extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id", unique = true, nullable = false, updatable = false)
    private Long groupId;

    @Column(name = "group_pin")
    private String groupPin;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @Column(name = "group_description")
    private String groupDescription;

    @Column(name = "group_icon")
    private String groupIcon;

    @Column(name = "group_lightcolor")
    private String groupLightColor;

    @Column(name = "group_darkcolor")
    private String groupDarkColor;


}
