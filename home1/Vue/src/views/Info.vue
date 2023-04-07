<template>
    <el-card class="container">

      <div style="font-size: 18px;"><i>{{form.name}}</i> 详细资料</div>
      <hr style="transform: translateY(5px);"/>

      <el-descriptions
          class="margin-top"
          size="Default"
          border
          style="margin-top: 20px;"
          v-if="form.role === 3"
      >
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">用户名</div>
          </template>
          {{form.name}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">年龄</div>
          </template>
          {{form.age}}岁
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">性别</div>
          </template>
          {{form.gender}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">邮箱</div>
          </template>
          {{form.email}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">密码</div>
          </template>
          {{form.password}}
        </el-descriptions-item>
        <el-descriptions-item >
          <template #label>
            <div class="cell-item" >家庭住址</div>
          </template>
          {{form.patientAddress}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item" >联系电话</div>
          </template>
          {{form.patientPhone}}
        </el-descriptions-item>
      </el-descriptions>

      <el-descriptions
          class="margin-top"
          size="Default"
          border
          style="margin-top: 20px;"
          v-if="form.role === 2"
      >
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">用户名</div>
          </template>
          {{form.name}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">年龄</div>
          </template>
          {{form.age}}岁
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">性别</div>
          </template>
          {{form.gender}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">邮箱</div>
          </template>
          {{form.email}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">密码</div>
          </template>
          {{form.password}}
        </el-descriptions-item>
        <el-descriptions-item >
          <template #label>
            <div class="cell-item">职位</div>
          </template>
          {{form.doctorPosition}}
        </el-descriptions-item>
      </el-descriptions>

      <el-descriptions
          class="margin-top"
          size="Default"
          border
          style="margin-top: 20px;"
          v-if="form.role === 1"
      >
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">用户名</div>
          </template>
          {{form.name}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">年龄</div>
          </template>
          {{form.age}}岁
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">性别</div>
          </template>
          {{form.gender}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">邮箱</div>
          </template>
          {{form.email}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">密码</div>
          </template>
          {{form.password}}
        </el-descriptions-item>
      </el-descriptions>


      <div style="margin:10px 0 0 400px;"><el-button type="primary" plain size="normal" @click="handleSelect">修改</el-button></div>
      <el-divider style="transform: translateY(-10px)"></el-divider>

      <div v-if="form.role === 3" style="transform: translateY(-10px)">
        <h2>就诊记录</h2>
        <el-collapse  v-model="activeNames" accordion>
          <el-collapse-item :title="cp.date" v-for="cp in cpList" :key="cp.cpId" :name="cp.cpId">
            <div>诊断报告 <span>{{cp.caseDesc}}</span></div>
          </el-collapse-item>
        </el-collapse>
      </div>

      <el-dialog title="更新信息" v-model="dialogVisible2" width="30%" >
        <el-form ref="form" :model="form" label-width="80px" :rules="rules">
          <el-form-item label="用户名" prop="name">
            <el-input v-model="form.name" disabled style="margin-bottom: 20px"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="form.password" show-password style="margin-bottom: 20px"></el-input>
          </el-form-item>
          <el-form-item label="年龄" prop="age">
            <el-input v-model="form.age" style="margin-bottom: 20px"></el-input>
          </el-form-item>
          <el-form-item label="性别" prop="gender">
            <el-input v-model="form.gender" style="margin-bottom: 20px"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="form.email" style="margin-bottom: 20px"></el-input>
          </el-form-item>
          <el-form-item label="职位" v-if="form.role === 2" prop="doctorPosition">
            <el-input v-model="form.doctorPosition" style="margin-bottom: 20px"></el-input>
          </el-form-item>
          <el-form-item label="住址" v-if="form.role === 3" prop="patientAddress">
            <el-input v-model="form.patientAddress" style="margin-bottom: 20px"></el-input>
          </el-form-item>
          <el-form-item label="电话" v-if="form.role === 3" prop="patientPhone">
            <el-input v-model="form.patientPhone" style="margin-bottom: 20px"></el-input>
          </el-form-item>
          <el-form-item label="图片">
            <el-upload ref="upload" class="upload-demo" action="http://localhost:8887/files/upload" :on-success="filesUploadSuccess">
              <el-button type="primary">点击上传</el-button>
            </el-upload>
          </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
              <el-button @click="cancel">取 消</el-button>
              <el-button v-if="form.role === 3" type="primary" @click="updatePatient">确 定</el-button>
              <el-button v-if="form.role === 2" type="primary" @click="updateDoctor">确 定</el-button>
              <el-button v-if="form.role === 1" type="primary" @click="update">确 定</el-button>
            </span>
        </template>
      </el-dialog>

  </el-card>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Info",
  data() {
    return {
      form: {},
      dialogVisible2:false,
      userPhoto:'',
      cpList:[
          {
            cpId:1,
            date:2020,
            caseDesc:"123"
          },
        {
          cpId:2,
          date:2021,
          caseDesc:"456"
        }
      ],
      activeNames:1,
      rules: {
        password: [
          { required: true, message: '请正确输入密码,长度在3到15个字符', pattern : /^[A-Za-z0-9]+$/g, type : "string", trigger: 'blur', min: 3, max: 15 },
        ],
        gender: [
          { required: true, message: '请正确输入性别', type: "enum", enum: ['男', '女'],trigger: 'blur' },
        ],
        age: [
          { required: true, message: '请正确输入年龄', pattern : /^([1-9]|[1-9][0-9]|[1][0-9][0-9])$/g, type : "string", trigger: 'blur' },
        ],
        doctorPosition: [
          { required: true, message: '请正确输入职位', type : "string", trigger: 'blur',min:3, pattern : /[\u4e00-\u9fa5]/g,},
        ],
        email: [
          { required: true, message: '请正确输入邮箱', type: 'email', trigger: 'blur' },
        ],
        patientPhone: [
          { required: true, message: '请正确输入电话号码', pattern : /^1[34578]\d{9}$/g, type : "string", trigger: 'blur' },
        ],
        patientAddress: [
          { required: true, message: '请正确输入家庭住址', pattern : /^((?:[\u3400-\u4DB5\u4E00-\u9FEA\uFA0E\uFA0F\uFA11\uFA13\uFA14\uFA1F\uFA21\uFA23\uFA24\uFA27-\uFA29]|[\uD840-\uD868\uD86A-\uD86C\uD86F-\uD872\uD874-\uD879][\uDC00-\uDFFF]|\uD869[\uDC00-\uDED6\uDF00-\uDFFF]|\uD86D[\uDC00-\uDF34\uDF40-\uDFFF]|\uD86E[\uDC00-\uDC1D\uDC20-\uDFFF]|\uD873[\uDC00-\uDEA1\uDEB0-\uDFFF]|\uD87A[\uDC00-\uDFE0])|(\d))+$/g, type : "string", trigger: 'blur' },
        ],
      },
    }
  },
  created() {
    let str = sessionStorage.getItem("user") || "{}"
    this.form = JSON.parse(str)
    this.$store.commit("update",this.form)
    this.userPhoto = this.$store.state.userPhoto
    this.load()
  },
  methods: {

    handleChange(val){
      this.activeNames = val
    },

    handleSelect(){
      this.dialogVisible2 = true
      this.$refs['upload'].clearFiles()
    },
    filesUploadSuccess(res){
      console.log(res)
      this.form.photo = res.data
    },
    cancel(){
      let str = sessionStorage.getItem("user") || "{}"
      this.form = JSON.parse(str)
      this.dialogVisible2 = false
    },
    updatePatient() {
        request.put("/api/allUser/updateDataPatient", this.form).then(res => {
          console.log(res)
          if (res.code === 0) {
            this.$message({
              type: "success",
              message: "更新成功"
            })
            sessionStorage.setItem("user", JSON.stringify(this.form))
            let str = sessionStorage.getItem("user") || "{}"
            this.form = JSON.parse(str)
            this.$store.commit("update",this.form)
            this.userPhoto = this.$store.state.userPhoto
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
        })
      this.dialogVisible2 = false
    },
    updateDoctor() {
      request.put("/api/allUser/updateDataDoctor", this.form).then(res => {
        console.log(res)
        if (res.code === 0) {
          this.$message({
            type: "success",
            message: "更新成功"
          })
          sessionStorage.setItem("user", JSON.stringify(this.form))
          let str = sessionStorage.getItem("user") || "{}"
          this.form = JSON.parse(str)
          this.$store.commit("update",this.form)
          this.userPhoto = this.$store.state.userPhoto
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
      this.dialogVisible2 = false
    },
    update() {
      request.put("/api/allUser/updateDataManager", this.form).then(res => {
        console.log(res)
        if (res.code === 0) {
          this.$message({
            type: "success",
            message: "更新成功"
          })
          sessionStorage.setItem("user", JSON.stringify(this.form))
          let str = sessionStorage.getItem("user") || "{}"
          this.form = JSON.parse(str)
          this.$store.commit("update",this.form)
          this.userPhoto = this.$store.state.userPhoto
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
      this.dialogVisible2 = false
    },

    load(){
      request.post("/api/allUser/showOneInfo",this.form).then(res =>{
        console.log(res)
        this.form = res.data
      })
      if(this.form.role === 3){
        this.loadCp()
      }
    },

    loadCp(){
      request.get("/api/cp/cpByPatientId1",{
        params:{
          id:this.form.id
        }}).then(res =>{
        console.log(res)
        this.cpList = res.data
      })
    },
  }
}
</script>

<style scoped>
.container{
  width:920px;
  min-height:1000px;
  background-color: #FEFDFD
}

.caption > h1,.caption > h2{
  width:200px;
  font-weight: bold;
  margin-bottom: 30px;
}

.cell-item {
  display: flex;
  align-items: center;
}
.margin-top {
  margin-top: 20px;
}
</style>
