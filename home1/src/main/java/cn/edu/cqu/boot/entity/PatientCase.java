package cn.edu.cqu.boot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
public class PatientCase {
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
    private Float paraT1;
    private Float paraM1;
    private Float paraM2;
    private Float paraM3;
    private Float paraM4;
    private Float paraM5;
    private Float paraHL;
    private Float paraHM;
    private String caseDesc;
}
