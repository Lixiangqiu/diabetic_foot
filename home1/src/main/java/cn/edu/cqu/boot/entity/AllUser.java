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
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
@TableName("alluser")
public class AllUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("name")
    private String name;

    @TableField("gender")
    private String gender;

    @TableField("age")
    private Integer age;

    @TableField("role")
    private Integer role;

    @TableField("email")
    private String email;

    @TableField("password")
    private String password;

    @TableField("photo")
    private String photo;

}


