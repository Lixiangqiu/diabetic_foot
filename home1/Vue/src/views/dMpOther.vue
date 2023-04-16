<template>
    <el-card class="box-card" style="background-color: #FEFDFD">
      <div style="padding: 10px">
        <div style="margin: 10px 0">
          <el-input v-model="search" placeholder="please input name" style="width:20%" clearable></el-input>
          <el-button type="primary" style="margin-left:5px" @click="load">查询</el-button>
        </div>
        <el-table
            :data="tableData" stripe
            :header-cell-style="{background:'#F0F8FF',color:'#303133'}"
            style="width: 100%">
          <el-table-column
              align="center"
              prop="date"
              label="日期" width="100px">
          </el-table-column>
          <el-table-column
              align="center"
              prop="doctorName"
              label="医生姓名" width="100px">
          </el-table-column>
          <el-table-column
              align="center"
              prop="patientName"
              label="姓名">
          </el-table-column>
          <el-table-column
              align="center"
              prop="patientGender"
              label="性别">
          </el-table-column>
          <el-table-column
              align="center"
              prop="patientAge"
              label="年龄">
          </el-table-column>
          <el-table-column
              align="center"
              prop="caseDesc"
              label="诊断报告">
          </el-table-column>
          <el-table-column
              align="center"
              fixed="right"
              label="操作"
              width="150px">
            <template #default="scope">
              <el-button type="text"  @click="handleEdit(scope.row)">查看足生理参数</el-button>
            </template>
          </el-table-column>
        </el-table>
  
        <div style="margin-bottom: 20px"></div>
        <div style="margin: 10px 0">
          <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :page-sizes="[3, 6, 9]"
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
  
  export default {
    name:"dMpOther",
    components:{
    },
    data() {
      return {
        form:{},
        search:'',
        currentPage:1,
        pageSize:3,
        total:0,
        tableData: [
          {
            cpId:"",
            date:"",
            name:"",
            gender:"",
            age:"",
            caseDesc:""
          }
        ],
        user:{},
        patient:{}
      }
    },
    created(){
      let str = sessionStorage.getItem("user") || "{}"
      this.user = JSON.parse(str)
      this.load();
    },
  
    methods:{
      handleEdit(row){
        sessionStorage.setItem("patient", JSON.stringify(row))  // 缓存该病人信息
        this.$router.push("/seePatientCp")
      },
  
      handleSizeChange(pageSize){
        this.pageSize = pageSize
        this.load()
      },
  
      handleCurrentChange(pageNum){
        this.currentPage = pageNum
        this.load()
      },
  
      load(){
        request.get("/api/doctor/showPublicCaseByDoctorId",{
          params:{
            doctorId:this.user.id,
          }
        }).then(res =>{  
          this.tableData = res.data
          console.log('返回值',this.tableData)
          // this.tableData.forEach(item =>{
          //   if(item.caseDesc === null || item.caseDesc === ""){
          //     item.caseDesc = "暂无诊断报告"
          //   }
          // })
         
        })
      }
    }
  }
  </script>