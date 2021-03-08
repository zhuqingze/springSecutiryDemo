package com.test.securityjwt.demo.entity.base;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
public class BaseEntity {

    @Id
    @Column(name = "Id")
    private String id;

    @Column(name = "CRT_TIME")
    private Date crtTime;
    @Column(name = "CRT_USER")
    private String crtUser;

    @Column(name = "CRT_NAME")
    private String crtName;

    @Column(name = "CRT_HOST")
    private String crtHost;

    @Column(name = "UPD_TIME")
    private Date updTime;

    @Column(name = "UPD_USER")
    private String updUser;

    @Column(name = "UPD_NAME")
    private String updName;

    @Column(name = "UPD_HOST")
    private String updHost;

    @Column(name = "ATTR1")
    private String attr1;

    @Column(name = "ATTR2")
    private String attr2;

    @Column(name = "ATTR3")
    private String attr3;

    @Column(name = "ATTR4")
    private String attr4;
}
