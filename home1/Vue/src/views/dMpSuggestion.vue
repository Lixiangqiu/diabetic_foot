<template>
    <el-card class="box-card" style="background-color: #FEFDFD">
      <div style="padding: 10px">
        <div style="margin: 10px 0">
            <h2 style="text-align:center">提供意见病历</h2>
          <el-input v-model="search" placeholder="please input name" style="width:20%" clearable></el-input>
          <el-button type="primary" style="margin-left:5px" @click="load">查询</el-button>
        </div>
        <el-table
            :data="tableData" stripe
            :header-cell-style="{background:'#F0F8FF',color:'#303133'}"
            style="width: 100%">
          <el-table-column
              align="center"
              prop="patientName"
              label="患者姓名">
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
              prop="doctorName"
              label="诊断医生姓名" width="100px">
          </el-table-column>
          <el-table-column
              align="center"
              prop="doctorCon"
              label="诊断意见">
          </el-table-column>
          <el-table-column
              align="center"
              prop="isPublic"
              label="公开情况">
          </el-table-column>
          <el-table-column
              align="center"
              prop="date"
              label="日期" width="100px">
          </el-table-column>
          <el-table-column
              align="center"
              fixed="right"
              label="操作"
              width="150px">
            <template #default="scope">
              <el-button type="text"  @click="handleEdit(scope.row)" :disabled = "scope.row.isPublic == '未公开'" >查看足生理参数</el-button>
            </template>
          </el-table-column>
        </el-table>
  
        <div style="margin-bottom: 20px"></div>
        <div style="margin: 10px 0">
          <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :page-sizes="[9, 6, 3]"
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
    name:"dMpSuggestion",
    components:{
    },
    data() {
      return {
        form:{},
        search:'',
        currentPage:1,
        pageSize:9,
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
        sessionStorage.setItem('cpId',row.cpId)
        sessionStorage.setItem('dcId',row.dcId)
        this.$router.push({
                    path: "/seePatientCp",
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
  
      load(){
        request.get("/api/dc/findDcPage",{
          params:{
            doctorId:this.user.id,
            pageNum:this.currentPage,
            pageSize:this.pageSize,
            patientName:this.search
          }
        }).then(res =>{
            console.log(res)
          this.tableData = res.data
          this.total = this.tableData.length
          this.tableData.forEach(item =>{
            if(item.isPublic == false){
              item.isPublic = '未公开'
            }else {
              item.isPublic = '公开'
            }
          })
          
          // this.total=res.data.total
          // this.currentPage=res.data.current
          // this.pageSize=res.data.size
        })
      }
    }
  }
  </script>