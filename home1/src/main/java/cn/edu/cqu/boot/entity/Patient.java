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

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
@TableName("patient")
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "patientId")
    private Integer patientId;

    @TableField("patientName")
    private String patientName;

    @TableField("patientGender")
    private String patientGender;

    @TableField("patientAge")
    private Integer patientAge;

    @TableField("patientPhone")
    private String patientPhone;

    @TableField("patientAddress")
    private String patientAddress;

    @TableField("patientEmail")
    private String patientEmail;

//    @TableField("role")
//    private Integer role;
}


