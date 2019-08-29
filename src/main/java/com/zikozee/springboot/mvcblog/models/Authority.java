package com.zikozee.springboot.mvcblog.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter @Setter
@Table(name = "authorities")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // no need for @Column(name ="") since database field and entity field are same
    private String username;

    private String authority;

    @ManyToMany(mappedBy = "authorities")
    private Set<User> users;

}
