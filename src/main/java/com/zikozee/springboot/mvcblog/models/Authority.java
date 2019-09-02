package com.zikozee.springboot.mvcblog.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
@Getter @Setter @NoArgsConstructor @ToString
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String username;

    @Column(nullable = false)
    private String authority;


    public Authority(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }
}
