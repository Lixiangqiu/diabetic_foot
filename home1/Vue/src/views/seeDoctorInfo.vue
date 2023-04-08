<template>
  <el-card class="container" :id="doctorInfo.doctorId">
    <el-page-header @back="goBack" :content="'医生 '+doctorInfo.doctorName+' 详细资料'"></el-page-header>
<!--    <div style="font-size: 18px;">医生 <i>{{doctorInfo.doctorName}}</i> 详细资料
    {{doctorInfo.doctorName}}{{doctorInfo.doctorAge}}岁{{doctorInfo.doctorPosition}}-->

<!--    </div>-->
    <hr style="transform: translateY(5px)"/>
    <div class="box-card" >
      <img :src="doctorInfo.doctorPic">
      <div>
        <h3>{{doctorInfo.doctorName}}</h3>
        <h4>{{doctorInfo.doctorAge}}岁</h4>
        <h4>{{doctorInfo.doctorPosition}}</h4>
      </div>

    </div>
    <el-divider></el-divider>
    <h3>擅长领域：<span>{{doctorInfo.doctorDes}}</span></h3>
    <el-divider></el-divider>
    <div v-if="user.role ===3" style="margin:10px 0 0 400px;"><el-button type="primary" plain size="normal" @click="handleSelect">选择该医生</el-button></div>
    <el-button v-if="user.role===1" style="margin-left:400px;transform: translateY(-5px)" type="Info" plain size="mini" @click="handleEdit(doctorInfo)">修改</el-button>

    <el-dialog title="更新医生信息" v-model="dialogVisible" width="30%">
      <el-form :model="form" label-width="120px">
        <el-form-item label="姓名">
          <el-input v-model="form.doctorName" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="form.doctorAge" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="职位">
          <el-input v-model="form.doctorPosition" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.doctorDes" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="图片">
          <el-upload ref="upload" class="upload-demo" action="http://localhost:8887/files/upload" :on-success="filesUploadSuccess">
            <el-button type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="saveData">确 定</el-button>
          </span>
      </template>
    </el-dialog>

    <el-dialog title="提交自测生理参数" v-model="dialogVisible2" width="30%">
      <el-upload ref="upload" class="upload-demo" action="http://localhost:8887/cp/uploadTxt" :on-success="filesUploadSuccess2">
        <el-button type="primary">点击上传文件</el-button>
      </el-upload>
      <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible2 = false">取 消</el-button>
            <el-button type="primary" @click="selectDoctor">确 定</el-button>
          </span>
      </template>
    </el-dialog>
  </el-card>

</template>

<script>
import request from "@/utils/request";

export default {
  name: "seeDoctorInfo",
  data() {
    return {
      doctor: {},
      user:{},
      form:{},
      doctorInfo:{},
      dialogVisible:false,
      dialogVisible2:false,
    }
  },
  created() {
    let str = sessionStorage.getItem("doctor") || "{}"
    this.doctor = JSON.parse(str)
    let str1 = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(str1)
    this.load()
  },

  methods: {
    filesUploadSuccess(res){
      console.log(res)
      this.form.doctorPic = res.data
    },

    filesUploadSuccess2(res){
      console.log(res)
    },

    goBack(){
      this.$router.push("/doctorInfo")
    },

    load(){
      if(this.doctor.doctorId == undefined){
        this.$message({
          type:"error",
          message:"无医生有效信息"
        })
          this.$router.push("/doctorInfo")
          return
      }
      request.get("/api/allUser/findOneDoctor",{
        params:{
          doctorId:this.doctor.doctorId
        }
      }).then(res =>{
        console.log(res)
        this.doctorInfo = res.data
      })
    },

    handleEdit(row){
      this.form = row
      this.dialogVisible = true
    },

    handleSelect(){
      this.dialogVisible2 = true
    },

    selectDoctor(){
      request.get("/api/cp/insertCp",{
        params:{
          id:this.user.id,
          doctorId:this.doctor.doctorId
        }
      }).then(res=>{
        if(res.code === 0){
          this.$message({
            type:"success",
            message:"已成功将文件发送给医生"
          })}else{
          this.$message({
            type:"error",
            message:res.msg
          })
        }
      })
      this.dialogVisible2 = false
    },

    saveData(){
      if(this.form.doctorId){
        request.put("/api/allUser/updateDoctorData",this.form).then(res =>{
          console.log(res)
          if(res.code === 0){
            this.$message({
              type:"success",
              message:"修改成功"
            })}else{
            this.$message({
              type:"error",
              message:res.msg
            })
          }
        })
      }else {
        request.post("/api/allUser/saveDoctorData", this.form).then(res => {
          console.log(res)
          if(res.code === 0){
            this.$message({
              type:"success",
              message:"添加成功"
            })}else{
            this.$message({
              type:"error",
              message:res.msg
            })
          }
        })
      }
      this.dialogVisible = false
      this.load()
    },


  }
}
</script>

<style scoped>
  .container{
    margin-left: 370px;
    margin-top: 50px;
    width:920px;
    min-height:1000px;
    background-color: #FEFDFD
  }
  .box-card{
    display:flex;
    margin-top: 5px;
    align-items: center;
  }
  .box-card > img{
    margin-left: 50px;
    margin-top: 20px;
    width:200px;
    height:200px;
  }
  .box-card >div{
    margin-left: 50px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-evenly;
    height: 200px;
  }
  .caption{
    margin-left: 100px;
    margin-top: 50px;
    height:240px;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;
  }
  .caption > h1,.caption > h2{
    width:200px;
    margin-top: 15px;
    font-weight: bold;
    margin-bottom: 30px;
  }

</style>
