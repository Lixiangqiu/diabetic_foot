package cn.edu.cqu.boot.controller;


import cn.edu.cqu.boot.config.Result;
import cn.edu.cqu.boot.config.Hash;
import cn.edu.cqu.boot.entity.*;
import cn.edu.cqu.boot.mapper.AllUserMapper;
import cn.edu.cqu.boot.mapper.DoctorMapper;
import cn.edu.cqu.boot.mapper.PatientMapper;
import cn.edu.cqu.boot.service.IAllUserService;
import cn.edu.cqu.boot.service.IDoctorService;
import cn.edu.cqu.boot.service.IPatientService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

@RestController
@RequestMapping("/allUser")
public class AllUserController {

    @Resource
    private IAllUserService userService;

    @Resource
    private AllUserMapper allUserMapper;

    @Resource
    private IDoctorService doctorService;

    @Resource
    private DoctorMapper doctorMapper;

    @Resource
    private IPatientService patientService;

    @Resource
    private PatientMapper patientMapper;


    private static final File file = new File("E:/pycharm/code/t1/detector.py");
    private static final String PATH = file.getPath();

    /**
     * @return cn.edu.cqu.boot.config.Result<?>
     * @Description 个人信息展示
     * @Param [user]
     * @Date 2023/4/12 15:32
     * @Auther WangSanmu
     */
    @PostMapping(value = "/showOneInfo")
    public Result<?> showOneInfo(@RequestBody AllUser user) {
//        AllUser user = allUserMapper.selectOne(Wrappers.<AllUser>query().lambda().eq(AllUser::getId, userid));
        System.err.println("paraR");
        OneInfo oneInfo = new OneInfo();
        if (user.getRole() == 2) {//展示医生个人信息
            oneInfo = allUserMapper.selectJoinOne(OneInfo.class,
                    new MPJLambdaWrapper<AllUser>()
                            .select(AllUser.class, i -> !i.getProperty().startsWith("password"))
                            .select(AllUser::getId)
                            .select(Doctor::getDoctorPosition, Doctor::getDoctorDes)
                            .leftJoin(Doctor.class, Doctor::getDoctorId, AllUser::getId)
                            .eq(AllUser::getId, user.getId())
            );
            return Result.success(oneInfo);
        } else if (user.getRole() == 3) {//展示病人个人信息
            oneInfo = allUserMapper.selectJoinOne(OneInfo.class,
                    new MPJLambdaWrapper<AllUser>()
                            .select(AllUser.class, i -> !i.getProperty().startsWith("password"))
                            .select(AllUser::getId)
                            .select(Patient::getPatientAddress, Patient::getPatientPhone)
                            .leftJoin(Patient.class, Patient::getPatientId, AllUser::getId)
                            .eq(AllUser::getId, user.getId())
            );
            return Result.success(oneInfo);
        }

        return Result.success(user);
    }

    @PostMapping("/saveData")   //管理员新增用户保存
    public Result<?> saveData(@RequestBody AllUser user) throws Exception {
        if (user.getPassword() == null) {
            user.setPassword(Hash.encode("e10adc3949ba59abbe56e057f20f883e"));
        }
        user.setPhoto("http://localhost:8887/files/cae603dda1974bc6bf347e4e2be2b703");
        user.setPassword(Hash.encode(user.getPassword()));
        userService.save(user);
        System.err.println(user.getId());
        if (user.getRole() == 2) {  //同步更新医生表
            Doctor d = new Doctor();
            d.setDoctorId(user.getId());
//            d.setRole(user.getRole());
            //System.err.println(d.getDoctorId());
            d.setDoctorName(user.getName());
            doctorService.save(d);
        } else if (user.getRole() == 3) {  //同步更新病人表
            Patient p = new Patient();
            p.setPatientId(user.getId());
            p.setPatientName(user.getName());
            patientService.save(p);
        }
        System.err.println(user);
        return Result.success();
    }

    @PostMapping("/createDoctorData")   //管理员新增用户保存
    public Result<?> createDoctorData(@RequestBody OneInfo info) throws Exception {
        AllUser user = new AllUser();
        if (info.getPassword() == null) {
            user.setPassword(Hash.encode("e10adc3949ba59abbe56e057f20f883e"));
        } else {
            user.setPassword(Hash.encode(info.getPassword()));
        }
        user.setPhoto("http://localhost:8887/files/cae603dda1974bc6bf347e4e2be2b703");
        user.setName(info.getDoctorName());
        user.setGender(info.getDoctorGender());
        user.setAge(info.getDoctorAge());
        user.setEmail(info.getDoctorEmail());
        user.setRole(info.getRole());
//        user.setPassword(Hash.encode(info.getPassword()));
        userService.save(user);
        System.err.println(user.getId());
        if (user.getRole() == 2) {  //同步更新医生表
            Doctor d = new Doctor();
            d.setDoctorId(user.getId());
//            d.setRole(user.getRole());
            //System.err.println(d.getDoctorId());
            d.setDoctorName(user.getName());
            d.setDoctorPic(info.getDoctorPic());
            d.setDoctorPosition(info.getDoctorPosition());
            d.setDoctorAge(user.getAge());
            d.setDoctorEmail(user.getEmail());
            d.setDoctorGender(user.getGender());
            doctorService.save(d);
        }
        System.err.println(user);
        return Result.success();
    }

    @PutMapping("/updateDataManager") //管理员更新信息
    public Result<?> updateDataManager(@RequestBody AllUser user) {
        userService.updateById(user);
        return Result.success();
    }


    @PutMapping("/updateDataPatient") //病人更新信息
    public Result<?> updateDataPatient(@RequestBody OneInfo oneInfo) {
        Patient res = patientMapper.selectOne(Wrappers.<Patient>query().lambda().eq(Patient::getPatientId, oneInfo.getId()));
        res.setPatientName(oneInfo.getName());
        res.setPatientEmail(oneInfo.getEmail());
        res.setPatientGender(oneInfo.getGender());
        res.setPatientAge(oneInfo.getAge());
        res.setPatientAddress(oneInfo.getPatientAddress());
        res.setPatientPhone(oneInfo.getPatientPhone());
        patientService.updateById(res);
        AllUser res1 = allUserMapper.selectOne(Wrappers.<AllUser>query().lambda().eq(AllUser::getId, oneInfo.getId()));
        res1.setName(oneInfo.getName());
        res1.setAge(oneInfo.getAge());
        res1.setPhoto(oneInfo.getPhoto());
        res1.setPassword(oneInfo.getPassword());
        res1.setEmail(oneInfo.getEmail());
        res1.setGender(oneInfo.getGender());
        userService.updateById(res1);
        return Result.success();
    }


    @PutMapping("/updateDataDoctor") //医生更新信息
    public Result<?> updateDataDoctor(@RequestBody OneInfo oneInfo) {
        oneInfo.doctor();
        AllUser res_user = allUserMapper.selectOne(Wrappers.<AllUser>query().lambda().eq(AllUser::getId, oneInfo.getId()));
        res_user.setName(oneInfo.getName());
        res_user.setEmail(oneInfo.getEmail());
        res_user.setAge(oneInfo.getAge());
        res_user.setGender(oneInfo.getGender());
//        res_user.setPhoto(oneInfo.getPhoto());
        Doctor res_doctor = doctorMapper.selectOne(Wrappers.<Doctor>query().lambda().eq(Doctor::getDoctorId, oneInfo.getId()));
        res_doctor.setDoctorAge(oneInfo.getAge());
        res_doctor.setDoctorName(oneInfo.getName());
        res_doctor.setDoctorPosition(oneInfo.getDoctorPosition());
        res_doctor.setDoctorPic(oneInfo.getDoctorPic());
        userService.updateById(res_user);
        doctorService.updateById(res_doctor);
//        AllUser res1 = allUserMapper.selectOne(Wrappers.<AllUser>query().lambda().eq(AllUser::getId, oneInfo.getId()));
//        res1.setName(oneInfo.getName());
//        res1.setAge(oneInfo.getAge());
//        res1.setPhoto(oneInfo.getPhoto());
//        res1.setPassword(oneInfo.getPassword());
//        res1.setEmail(oneInfo.getEmail());
//        res1.setGender(oneInfo.getGender());
//        userService.updateById(res1);
        return Result.success();
    }

    @PutMapping("/updateData") //管理员更新信息
    public Result<?> updateData(@RequestBody AllUser user) {
        userService.updateById(user);
        if (user.getRole() == 2) {  //同步更新医生表
            Doctor res = doctorMapper.selectOne(Wrappers.<Doctor>query().lambda().eq(Doctor::getDoctorId, user.getId()));
            res.setDoctorAge(user.getAge());
            res.setDoctorName(user.getName());
            doctorService.updateById(res);
        } else if (user.getRole() == 3) {  //同步更新病人表
            Patient res = patientMapper.selectOne(Wrappers.<Patient>query().lambda().eq(Patient::getPatientId, user.getId()));
            res.setPatientName(user.getName());
            res.setPatientEmail(user.getEmail());
            res.setPatientGender(user.getGender());
            res.setPatientAge(user.getAge());
            patientService.updateById(res);
        }
        return Result.success();
    }

    @DeleteMapping("/{id}")   //根据ID删除用户
    public Result<?> deleteData(@PathVariable Long id) {
        AllUser u = userService.getById(id);
        if (u.getRole() == 2) {  //同步删除医生表
            QueryWrapper<Doctor> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("doctorId", u.getId());
            System.out.println(u);
            doctorService.remove(queryWrapper);
        } else if (u.getRole() == 3) {  //同步删除病人表
            QueryWrapper<Patient> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("patientId", u.getId());
            patientService.remove(queryWrapper);
        }
        userService.removeById(id);
        return Result.success();
    }

    @GetMapping("/findPage")  //查询权限为管理员的用户
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "5") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        IPage<AllUser> userIPage = allUserMapper.selectPage(new Page<>(pageNum, pageSize), Wrappers.<AllUser>query().lambda().eq(AllUser::getRole, "1").like(AllUser::getName, search));
        System.out.println(userIPage);
        return Result.success(userIPage);
    }

    @GetMapping("/findPage3")  //查询权限为医生的用户
    public Result<?> findPage3(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "5") Integer pageSize,
                               @RequestParam(defaultValue = "") String search) {
//        IPage<AllUser> userIPage = allUserMapper.selectPage(new Page<>(pageNum, pageSize), Wrappers.<AllUser>query().lambda().eq(AllUser::getRole, "2").like(AllUser::getName, search));
//        Doctor doctor = doctorMapper.selectOne(Wrappers.<AllUser>query().lambda().eq(AllUser::getName, user.getName()).
//                eq(AllUser::getPassword, Hash.encode(user.getPassword())));
        IPage<Doctor> doctorIPage = doctorMapper.selectPage(new Page<>(pageNum, pageSize),
                Wrappers.<Doctor>query().lambda().like(Doctor::getDoctorName, search));
//        IPage<OneInfo> oneInfoIPage =
        System.out.println(doctorIPage);
        return Result.success(doctorIPage);
    }

    @GetMapping("/findPage2")   //查询权限为病人的用户
    public Result<?> findPage2(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "5") Integer pageSize,
                               @RequestParam(defaultValue = "") String search) {
        IPage<AllUser> userIPage = allUserMapper.selectPage(new Page<>(pageNum, pageSize), Wrappers.<AllUser>query().lambda().eq(AllUser::getRole, "3").like(AllUser::getName, search));
        System.out.println(userIPage);
        return Result.success(userIPage);
    }


    @PostMapping("/saveDoctorData")  //管理员增加医生
    public Result<?> saveDoctorData(@RequestBody Doctor doctor) {
        doctorService.save(doctor);
        return Result.success();
    }

    @PutMapping("/updateDoctorData")  //管理员更新医师信息
    public Result<?> updateDoctorData(@RequestBody Doctor doctor) {
        AllUser res = allUserMapper.selectOne(Wrappers.<AllUser>query().lambda().eq(AllUser::getId, doctor.getDoctorId()));
        res.setAge(doctor.getDoctorAge());
        res.setName(doctor.getDoctorName());
        doctorService.updateById(doctor);
        userService.updateById(res);
        return Result.success();
    }


    @GetMapping("/findAllDoctor")   //查询所有医生
    public Result<?> findAllDoctor(@RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(defaultValue = "3") Integer pageSize,
                                   @RequestParam(defaultValue = "") String search) {
        IPage<Doctor> doctorIPage = doctorMapper.selectPage(new Page<>(pageNum, pageSize), Wrappers.<Doctor>query().lambda().like(Doctor::getDoctorName, search));
        System.out.println(doctorIPage);
        return Result.success(doctorIPage);
    }

    @GetMapping("/findOneDoctor")   //查询医生详情
    public Result<?> findOneDoctor(@RequestParam(defaultValue = "1") Integer doctorId) {
        Doctor d = doctorMapper.selectById(doctorId);
        System.out.println(d);
        return Result.success(d);
    }

    @GetMapping("/selectDoctor")  //病人选择医生, @RequestParam Integer did+ did
    public Result<?> selectDoctor(@RequestParam Integer userId, @RequestParam Integer doctorId) {
        System.out.println(userId + "**12315**" + doctorId);

        return Result.success();
    }

    @PostMapping("/login")  //用户登录页
    public Result<?> login(@RequestBody AllUser user) throws Exception {
        AllUser res = allUserMapper.selectOne(Wrappers.<AllUser>query().lambda().eq(AllUser::getName, user.getName()).
                eq(AllUser::getPassword, Hash.encode(user.getPassword())));
        if (res == null) {
            return Result.error(-1, "用户名或密码错误");
        }
        res.setPassword(null);
//        System.out.println(res);
//        System.err.println("正在进行人脸检测...");
//
//        final ProcessBuilder processBuilder = new ProcessBuilder("python", PATH);
//        processBuilder.redirectErrorStream(true);
//
//        final Process process = processBuilder.start();
//
//        final BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
//
//        String s = null;
//        while ((s = in.readLine()) != null)
//        {
//            System.out.println(s);
//        }
//
//        final int exitCode = process.waitFor();
//        System.out.println(exitCode == 0);
//        System.err.println("0");
        return Result.success(res);
    }

    @PostMapping("/register")  //用户注册页 用户名不能重复
    public Result<?> register(@RequestBody AllUser user) throws Exception {
        AllUser res = allUserMapper.selectOne(Wrappers.<AllUser>query().lambda().eq(AllUser::getName, user.getName()));
        if (res != null) {
            return Result.error(-1, "用户名重复");
        }
        user.setPhoto("http://localhost:8887/files/cae603dda1974bc6bf347e4e2be2b703");
        user.setRole(3);
        user.setPassword(Hash.encode(user.getPassword()));
        userService.save(user);

        Patient p = new Patient();
        p.setPatientId(user.getId());
        p.setPatientName(user.getName());
        p.setPatientEmail(user.getEmail());
        patientService.save(p);
        return Result.success();
    }

    //    @PostMapping("/passwordModify")
//    public Result<?> passwordModify(HttpServletRequest request, @RequestBody PasswordModify passwordModify) throws Exception {
//        String userId = (String) request.getSession().getAttribute("id");
//        AllUser user = allUserMapper.selectOne(Wrappers.<AllUser>query().lambda().eq(AllUser::getId, userId));
//        assert user != null;
//        if (Objects.equals(passwordModify.getOldPassword(), user.getPassword())) {
//            if (Objects.equals(passwordModify.getRePassword(), passwordModify.getNewPassword())) {
//                user.setPassword(passwordModify.getNewPassword());
//                userService.save(user);
//                return Result.success();
//            }
//        } else {
//            return Result.error(-1,"旧密码错误！");
//        }
//        return Result.success();
//    }
    @PostMapping("/passwordModify")
    public Result<?> passwordModify(@RequestBody PasswordModify passwordModify) throws Exception {
        AllUser user = allUserMapper.selectOne(Wrappers.<AllUser>query().lambda().eq(AllUser::getId, passwordModify.getId()));
        assert user != null;
        if (Objects.equals(Hash.encode(passwordModify.getOldPassword()), user.getPassword())) {
            user.setPassword(Hash.encode(passwordModify.getNewPassword()));
            userService.updateById(user);
            return Result.success();
        } else {
            return Result.error(-1, "旧密码错误！");
        }
    }
}
