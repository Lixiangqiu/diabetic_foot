/**
 * @Classname DoctorCon
 * @Description 医生建议相关交互实现
 * @Date 2023/4/17 16:22
 * @Created by WangSanmu
 */

package cn.edu.cqu.boot.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DoctorCon {
    private static final long serialVersionUID = 1L;

    private Integer patientId;
    private String patientName;
    private String patientGender;
    private Integer patientAge;
    private String patientAddress;
    private String patientPhone;
    private String patientEmail;

    private String doctorName;

    private Integer cpId;
    private LocalDate date;
    private String caseDesc;

    private Integer dcId;
    private LocalDate dcDate;
    private String doctorCon;
}
