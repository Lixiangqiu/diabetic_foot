<template>
    <el-card class="container">

      <div style="font-size: 18px;"><i>{{userData.name}}</i> 详细资料</div>
      <hr style="transform: translateY(5px);"/>

      <el-descriptions
          class="margin-top"
          size="Default"
          border
          style="margin-top: 20px;"
          v-if="userData.role === 3"
      >
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">用户名</div>
          </template>
          {{userData.name}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">年龄</div>
          </template>
          {{userData.age}}岁
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">性别</div>
          </template>
          {{userData.gender}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">邮箱</div>
          </template>
          {{userData.email}}
        </el-descriptions-item>
        <el-descriptions-item >
          <template #label>
            <div class="cell-item" >家庭住址</div>
          </template>
          {{userData.patientAddress}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item" >联系电话</div>
          </template>
          {{userData.patientPhone}}
        </el-descriptions-item>
      </el-descriptions>

      <el-descriptions
          class="margin-top"
          size="Default"
          border
          style="margin-top: 20px;"
          v-if="userData.role === 2"
      >
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">用户名</div>
          </template>
          {{userData.name}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">年龄</div>
          </template>
          {{userData.age}}岁
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">性别</div>
          </template>
          {{userData.gender}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">邮箱</div>
          </template>
          {{userData.email}}
        </el-descriptions-item>
        <el-descriptions-item >
          <template #label>
            <div class="cell-item">职位</div>
          </template>
          {{userData.doctorPosition}}
        </el-descriptions-item>
      </el-descriptions>

      <el-descriptions
          class="margin-top"
          size="Default"
          border
          style="margin-top: 20px;"
          v-if="userData.role === 1"
      >
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">用户名</div>
          </template>
          {{userData.name}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">年龄</div>
          </template>
          {{userData.age}}岁
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">性别</div>
          </template>
          {{userData.gender}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">邮箱</div>
          </template>
          {{userData.email}}
        </el-descriptions-item>
      </el-descriptions>


      <div style="margin:10px 0 0 400px;"><el-button type="primary" plain size="normal" @click="handleSelect">修改</el-button></div>
      <el-divider style="transform: translateY(-10px)"></el-divider>

      <div v-if="userData.role === 3" style="transform: translateY(-10px);">
        <h2>就诊记录</h2>
        <el-collapse  v-model="activeNames">
          <el-collapse-item  :title="cp.date+'&emsp;&emsp;&emsp;&emsp;'+cp.doctorName" v-for= "(cp,index) in cpList" :key="cp.cpId" :name="cp.cpId">
              <el-tooltip class="item" effect="dark" content="选择公开病历可以让其他医生也能看到您的病历并提供意见" placement="top-start">
                <i class="el-icon-question" style="float:right;transform: translateY(2.5px);"></i>
              </el-tooltip>
              <el-switch
                v-model="value1[cp.cpId]"
                active-text="公开病历"
                inactive-text="不公开病历"
                class="publicMedicalRecords"
                @change="hanldeChangeSwitch(cp)"
                >
              </el-switch>
           
            <div>
              诊断报告 <span>{{cp.caseDesc}}</span>
              <span class="jumpToMedicalRecord" @click="jumpToMedicalRecord(cp.doctorId)">查看详细病历></span>
            </div>
            <h3 style="margin-left:50px">医生建议</h3>
            <div v-for="dc in dcList[cp.cpId]">
              
              <hr>
              &emsp;&emsp;&emsp;&emsp;<span>{{ dc.dcDate }}</span>
              &emsp;&emsp;&emsp;&emsp;<span>{{ dc.doctorName }}</span>
              &emsp;&emsp;&emsp;&emsp;职位: 
              <span>{{dc.doctorPosition}}</span><br>
              &emsp;&emsp;&emsp;&emsp;医生建议： <span>{{ dc.doctorCon }}</span>
            </div>
          </el-collapse-item>
        </el-collapse>
      </div>

      <el-dialog title="更新信息" v-model="dialogVisible2" width="30%" >
        <el-form ref="form" :model="form" label-width="80px" :rules="rules">
          <el-form-item label="用户名" prop="name">
            <el-input v-model="form.name" disabled style="margin-bottom: 20px"></el-input>
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
          <el-form-item label="住址" v-if="form.role === 3" prop="patientAddress">
            <el-input v-model="form.patientAddress" style="margin-bottom: 20px"></el-input>
          </el-form-item>
          <el-form-item label="电话" v-if="form.role === 3" prop="patientPhone">
            <el-input v-model="form.patientPhone" style="margin-bottom: 20px"></el-input>
          </el-form-item>
          <el-form-item label="图片">
            <el-upload class="upload-demo" action="http://localhost:8887/files/upload" :on-success="filesUploadSuccess">
              <el-button type="primary">点击上传</el-button>
            </el-upload>
          </el-form-item>
          <el-form-item label="密码">
            <el-button type="primary" @click="$router.push('/passwordModify')">点击修改</el-button>
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
      value1:[true],
      num:1,
      form: {},
      userData: {},
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
      dcList:[
        {
          dcid:1,
          cpid:6,
          doctorId:34,
          doctorName:'黄沙',
          doctorPosition:'主治医师',
          caseDesc:'还需要进一步观察',
          date:'2022-5-6'
        },
        {
          dcid:1,
          cpid:6,
          doctorId:36,
          doctorName:'罗艳',
          doctorPosition:'主治医师',
          caseDesc:'有恶化倾向',
          date:'2022-5-7'
        },
        {
          dcid:1,
          cpid:6,
          doctorId:38,
          doctorName:'李军',
          doctorPosition:'副院长',
          caseDesc:'建议住院观察',
          date:'2022-5-8'
        }
      ],
      activeNames:['1'],
      rules: {
        gender: [
          { required: true, message: '请正确输入性别', type: "enum", enum: ['男', '女'],trigger: 'blur' },
        ],
        age: [
          { required: true, message: '请正确输入年龄', pattern : /^([1-9]|[1-9][0-9]|[1][0-9][0-9])$/g, type : "string", trigger: 'blur' },
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
    this.userData = JSON.parse(str)
    this.$store.commit("update",this.form)
    this.userPhoto = this.$store.state.userPhoto
    this.load()
  },
  methods: {
    hanldeChangeSwitch(cp){
      cp.isPublic = this.value1[cp.cpId]
      console.log('cp',cp)
      request.put("/api/cp/setPublic", cp).then(res => {
          console.log(res)
          if (res.code === 0) {
            this.$message({
              type: "success",
              message: "修改成功"
            })
            
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
        })
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
      this.userData = JSON.parse(str)
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
            this.userData = JSON.parse(str)
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
          this.userData = JSON.parse(str)
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
        if (res.code === 0) {
          this.$message({
            type: "success",
            message: "更新成功"
          })
          sessionStorage.setItem("user", JSON.stringify(this.form))
          let str = sessionStorage.getItem("user") || "{}"
          this.form = JSON.parse(str)
          this.userData = JSON.parse(str)
          this.$store.commit("update",this.form)
          console.log('update',this.$store.state)
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
        this.userData = res.data
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
        console.log('cp',res)
        this.cpList = res.data
        this.cpList.forEach(item =>
          request.get("/api/cp/dcByCpId",{
            params:{
              id:item.cpId
            }}).then(res =>{
            console.log(res)
            this.value1[item.cpId] = item.isPublic
            this.dcList[item.cpId] = res.data
            console.log('dcList',this.dcList[item.cpId])
          })
        )
      })
    },

    jumpToMedicalRecord(doctorId){
      this.$router.push({
                    path: "/detailedMedicalRecord",
                    query: {
                      doctorId: doctorId,
                      patientName:this.userData.name
                    }
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

.jumpToMedicalRecord{
    cursor:pointer;
    position:relative;
    right:50px;
    float: right;
    font-weight:bolder
  }
  .jumpToMedicalRecord:hover{
    color:rgb(64,158,255);
  }

  .publicMedicalRecords{
    position:relative;
    z-index:100;
    float:right;
    margin-right:5px;
  }
</style>
