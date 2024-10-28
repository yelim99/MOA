package com.MOA.backend.domain.user.entity;

import com.MOA.backend.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Data
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true, nullable = false, updatable = false)
    private Long userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "user_email", nullable = false, unique = true)
    private String userEmail;

    @Column(name = "user_image")
    private String userImage;

    @Lob
    @Column()
    private byte[] faceEmbedding;

    public void setOAuthUserInfo(String userName, String userEmail, String userImage) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userImage = userImage;
    }

    public void updateFaceEmbedding(byte[] faceEmbedding) {
        this.faceEmbedding = faceEmbedding;
    }
}
