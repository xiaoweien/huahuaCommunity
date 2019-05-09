package com.huahua.article.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class CommentMongoDB {

    @Id
    private String _id;
    private String articleid;
    private String content;
    private String userid;
    private String parentid;
    private Date publishtime;

}
