//package cn.edu.cqu.boot.entity;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableId;
//import com.baomidou.mybatisplus.annotation.TableField;
//import java.io.Serializable;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.ToString;
//import lombok.experimental.Accessors;
//
///**
// * <p>
// *
// * </p>
// *
// * @author MisterDong
// * @since 2021-07-07
// */
//
//@Data
//@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true)
//@ToString
//public class User implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    @TableId(value = "UserID", type = IdType.AUTO)
//    private Integer UserID;
//
//    @TableField("UserName")
//    private String UserName;
//
//    @TableField("UserCode")
//    private Integer UserCode;
//
//    @TableField("UserSex")
//    private String UserSex;
//
//    @TableField("UserAge")
//    private Integer UserAge;
//
//    @TableField("UserAddress")
//    private String address;
//
//    @TableField("UserLog")
//    private String UserLog;
//
//    @TableField("UserPhone")
//    private String UserPhone;
//
//
//
//}
package cn.edu.cqu.boot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
@TableName("comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "commentId", type = IdType.AUTO)
    private Integer commentId;

    @TableField("commentPersonId")
    private Integer commentPersonId;

    @TableField("commentPersonPic")
    private String commentPersonPic;

    @TableField("commentPersonName")
    private String commentPersonName;

    @TableField("commentPic")
    private String commentPic;

    @TableField("commentTitle")
    private String commentTitle;

    @TableField("commentDes")
    private String commentDes;

    @TableField("commentTime")
    private LocalDate commentTime;

    @TableField("commentNum")
    private Integer commentNum;

    @TableField("commentGood")
    private Integer commentGood;

    @TableField("commentContent")
    private String commentContent;

}


