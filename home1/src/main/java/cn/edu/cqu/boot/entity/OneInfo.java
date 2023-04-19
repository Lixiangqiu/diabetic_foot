package cn.edu.cqu.boot.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.io.Serializable;
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
public class OneInfo extends AllUser implements Serializable{
    private static final long serialVersionUID = 1L;


    private Integer id;
    private String name;
    private String password;
    private String gender;
    private Integer age;
    private String email;
    private Integer role;
    private String photo;

    private String doctorPosition;
    private String doctorDes;
    private String doctorPic;

    private String patientAddress;
    private String patientPhone;

}
