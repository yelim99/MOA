package com.MOA.backend.domain.group.entity;

import com.MOA.backend.domain.member.entity.Member;
import com.MOA.backend.global.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "`group`")
public class Group extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "group_pin")
    private String groupPin;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @Column(name = "group_description")
    private String groupDescription;

    @Column(name = "group_icon")
    private String groupIcon;

    @Column(name = "group_color")
    private String groupColor;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Member> members = new ArrayList<>();

}
