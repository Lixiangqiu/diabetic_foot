package cn.edu.cqu.boot.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
public class OneInfo {
    private static final long serialVersionUID = 1L;


    private Integer id;
    private String name;
    private String password;
    private String gender;
    private Integer age;
    private String email;
    private Integer role;
    private String photo;

    private Integer doctorId;
    private String doctorName;
    private Integer doctorAge;
    private String doctorGender;
    private String doctorEmail;
    private String doctorPosition;
    private String doctorDes;
    private String doctorPic;

    private String patientAddress;
    private String patientPhone;

    public void doctor(){
        this.id = this.doctorId;
        this.age = this.doctorAge;
        this.gender = this.doctorGender;
        this.email = this.doctorEmail;
        this.name = this.doctorName;
//        this.photo = this.doctorPic;
    }

}
