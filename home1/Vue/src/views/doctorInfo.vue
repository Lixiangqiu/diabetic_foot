<template>
  <el-card class="wrapper">
    <div>
      <div style="margin: 8px 0 23px 0">
        <el-input v-model="search" placeholder="please input name" style="width:20%" clearable></el-input>
        <el-button type="primary" style="margin-left:5px" @click="load">查询</el-button>
      </div>

      <div class="doctorInfo">
        <div v-for="(doctor) in doctorList" :key="doctor.doctorId">
          <div :id="doctor.id" class="box-card" style="">
            <img :src="doctor.doctorPic">
            <div >姓名：{{doctor.doctorName}}</div>
            <div >职位：{{doctor.doctorPosition}}</div>
            <div >擅长：{{doctor.doctorDes}}</div>
            <p><el-button type="primary" plain size="mini" @click="handleEdit(doctor)">详情</el-button></p>
          </div>
        </div>
      </div>

      <div>
        <el-pagination
            style="margin: 10px 0 10px 200px;"
           @size-change="handleSizeChange"
           @current-change="handleCurrentChange"
           :current-page="currentPage"
           :page-sizes="[4,8]"
           :page-size="pageSize"
           layout="total, sizes, prev, pager, next, jumper"
           :total="total">
        </el-pagination>
      </div>

    </div>
  </el-card>
</template>

<script>
import request from "@/utils/request";
import PubSub from 'pubsub-js';
export default {
  name: "doctorInfo",
  created() {
    let str = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(str)
    this.load();
  },

  data(){
    return {
      radio:[],
      value:0,
      form:{},
      dialogVisible:false,
      search:'',
      currentPage:1,
      pageSize:4,
      total:0,
      user:{},
      doctorList:[],
    }
  },
  methods:{
    load(){
      request.get("/api/allUser/findAllDoctor", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res => {
        console.log(res)
        this.doctorList = res.data.records
        this.total = res.data.total
        this.currentPage = res.data.current
        this.pageSize = res.data.size
      })
    },

    handleSizeChange(pageSize){
      this.pageSize = pageSize
      this.load()
    },

    handleCurrentChange(pageNum){
      this.currentPage = pageNum
      this.load()
    },

    handleEdit(doctor){
      // sessionStorage.setItem("doctor", JSON.stringify(doctor))  // 缓存用户信息
      console.log('doctor',doctor)
      this.$router.push({
                    path: "/seeDoctorInfo",
                    query: {
                      doctorId: doctor.doctorId
                    }
                })
    },

    ///////

    filesUploadSuccess(res){
      console.log(res)
      this.form.dishPicPath = res.data
    },

    Add(){
      this.dialogVisible = true
      this.form={}
      this.$refs['upload'].clearFiles()
    },

    saveData(){
      if(this.form.dishID){
        request.put("/api/dish/updateData",this.form).then(res =>{
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
        request.post("/api/dish/saveData", this.form).then(res => {
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
    .wrapper{
      width:100%;
      height:1200px;
      position: relative;
      background-color: #FEFDFD
    }

    .doctorInfo{
      width:940px;
      display:flex;
      justify-content: flex-start;
      align-items: center;
      flex-wrap: wrap;

    }

    img{
      width:73px;
      height:73px;
    }

    .box-card{
      width:195px;
      height:275px;
      display:flex;
      flex-direction: column;
      justify-content: space-evenly;
      align-items: center;
      margin-top: 5px;
      margin-bottom: 20px;
      font-size: 14px;
      background-color: rgba(255,255,255,0.9);
      border: 1px solid #eaeefb;
      border-radius: 5px;
      position: relative;
      transition: all 0.2s ease-in-out;
      bottom: 0;
    }

    .box-card:hover{
      box-shadow: 0 2px 10px 0 rgba(0, 0, 0, 0.1);
      bottom: 4px;
    }

    .box-card:nth-child(1){
      margin-left: 20px;
    }


</style>