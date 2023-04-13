<template>
    <div class="content">
      <div class="test"></div>
      <h1>修改密码</h1>
      <div class="box_info">
        <div class="modify_box">
          <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" class="demo-ruleForm" size="medium">
            <el-form-item prop="oldPassword">
              <el-input type="password" v-model="ruleForm.oldPassword" autocomplete="off" placeholder="请输入旧密码"></el-input>
            </el-form-item>
            <el-form-item prop="newPassword">
              <el-input type="password" v-model="ruleForm.newPassword" autocomplete="off" placeholder="请输入新密码"></el-input>
            </el-form-item>
            <el-form-item prop="rePassword">
              <el-input type="password" v-model="ruleForm.rePassword" autocomplete="off" placeholder="密码确认"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitForm('ruleForm')" style="width: 40%;margin: 0px auto">修改密码</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
  </template>
  
  <script>
      import md5 from 'js-md5'
      export default {
          name: "passwordModify",
          data() {
              let validatePass = (rule, value, callback) => {
                  if (value === '') {
                      callback(new Error('请输入密码'));
                  } else if(value.length<3) {
                      callback(new Error('密码长度不能小于8'));
                  }else {
                      callback();
                  }
              };
              let validateRePass = (rule, value, callback) => {
                  if (value !==this.ruleForm.newPassword){
                      callback(new Error('密码不一致'));
                  }else{
                      callback();
                  }
              }
              return {
                  ruleForm: {
                      oldPassword: '',
                      newPassword: '',
                      rePassword: '',
                      comparePassword:'e10adc3949ba59abbe56e057f20f883e'
                  },
                  rules: {
                      oldPassword: [
                          { required: true, message: '旧密码不能为空', trigger: 'blur' },
                      ],
                      newPassword: [
                          { validator: validatePass, trigger: 'blur' }
                      ],
                      rePassword: [
                          { validator: validateRePass, trigger: 'blur' }
                      ]
                  }
              };
          },
          methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                      console.log('旧密码',md5(this.ruleForm.oldPassword))
                      console.log('比较密码',this.ruleForm.comparePassword)
                      /**********************************************************************************/ 
                      if(md5(this.ruleForm.oldPassword) == this.ruleForm.comparePassword){
                        console.log('修改成功')
                        this.ruleForm.comparePassword = md5(this.ruleForm.newPassword)
                        console.log('新密码',this.ruleForm.comparePassword)
                      }
                      /**********************************************************************************/
                        console.log("=====this.ruleForm.oldPassword:========"+this.ruleForm.newPassword+"====")
                        //数据校验成功，可以进行提交操作
                        reqModUserPwd(md5(this.ruleForm.oldPassword), md5(this.ruleForm.newPassword)).then((res)=>{
                          this.$message({
                            type: 'success',
                            message: '您的密码修改成功',
                            duration: 1000
                          })
                          sessionStorage.removeItem('user')
                          setTimeout(() => {
                            this.$router.push({path:'/login'});
                          }, 1000);
                        }).catch(err=>{
                          if (err.response.status === 403){
                            this.$message({
                              type: 'error',
                              message: "密码不匹配",
                              duration: 1000
                            })
                          } else {
                            this.$message({
                              type: 'error',
                              message: "密码修改失败",
                              duration: 1000
                            })
                          }
  
                        })
                    } else {
                        this.$message.error("请保证密码不为空且两次输入密码一致");
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            }
          }
  
      }
  </script>
  
  <style scoped>
    .content{
      margin: 0px auto;
      width:400px;
      height:400px;
      background-color: white;
      /* padding: 30px 20px; */
      overflow: hidden;
    }
    h1{
      color: #757575;
      font-family: 新宋体;
      display:block;
      width:4.1em;
      margin:70px auto;
      margin-bottom:0px;
    }
    .box_info{
      width: 960px;
      /* background-color: #8acfd1; */
      margin: 10px auto;
    }
    .modify_box{
      margin-top: 0px;
      margin-left: 250px;
      width: 400px;
      height: 260px;
      /*border: 1px solid #dfdfdf;*/
      text-align: center;
      padding: 15px;
      margin:0px auto;
    }
  
  </style>
  