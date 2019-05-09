package com.huahua.friend.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tb_nofriend")
@Data
@IdClass(NoFriend.class)
public class NoFriend implements Serializable {

    private static final long serialVersionUID = -8263657805917697894L;
    @Id
    private String userid;
    @Id
    private String friendid;
}
