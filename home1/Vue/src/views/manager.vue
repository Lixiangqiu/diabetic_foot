<template>
  <el-card class="box-card" style="background-color: #fffff9">
  <div class="home" style="padding: 10px;">
    <div style="margin: 10px 0">

    </div>
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="please input name" style="width:20%" clearable></el-input>
      <el-button type="primary" style="margin-left:5px" @click="load">查询</el-button>
      <el-button type="primary" @click="Add">新增</el-button>
    </div>
    <el-table
        :data="tableData"
        :header-cell-style="{background:'#F0F8FF',color:'#303133'}"
        stripe
        style="width: 100%">
      <el-table-column
          sortable
          align="center"
          prop="id"
          label="编号" width="80px">
      </el-table-column>
      <el-table-column
          align="center"
          prop="name"
          label="姓名">
      </el-table-column>
      <el-table-column
          align="center"
          prop="gender"
          label="性别">
      </el-table-column>
      <el-table-column
          align="center"
          prop="age"
          label="年龄">
      </el-table-column>
      <el-table-column
          align="center"
          prop="email"
          label="Email">
      </el-table-column>
      <el-table-column
          align="center"
          label="角色">
        <template #default="scope">
          <span v-if="scope.row.role===1">管理员</span>
          <span v-if="scope.row.role===2">医生</span>
          <span v-if="scope.row.role===3">病人</span>
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
    <el-dialog title="增加用户" v-model="dialogVisible" width="30%">
      <el-form :model="form" label-width="120px">
        <el-form-item label="用户名">
          <el-input v-model="form.name" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="权限">
          <el-input v-model="form.role" disabled=true style="width:80%" placeholder="1"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveData">确 定</el-button>
        </span>
      </template>
    </el-dialog>

  </div>
  </el-card>
</template>


<script>


import request from "@/utils/request";
import md5 from 'js-md5'
export default {
  name:"manager",
  components:{
  },
  data() {
    return {
      form:{},
      dialogVisible:false,
      search:'',
      currentPage:1,
      pageSize:9,
      total:0,
      tableData: [],
      user:{}
    }
  },
  created(){
    let str = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(str)
    this.load()
  },
  methods:{
    filesUploadSuccess(res){
      console.log(res)
      this.form.managerCover = res.data
    },
    load(){
      request.get("/api/allUser/findPage",{
        params:{
          pageNum:this.currentPage,
          pageSize:this.pageSize,
          search:this.search
        }
      }).then(res =>{
        console.log(res)
        this.tableData = res.data.records
        this.total=res.data.total
        this.currentPage=res.data.current
        this.pageSize=res.data.size
      })
    },

    Add(){
      this.dialogVisible = true
      this.form={role:1}
      // request.post("/api/allUser/saveData")
      //this.$refs['upload'].clearFiles()
    },

    saveData(){
      if(this.form.id){
        this.form.password = md5(this.form.password)
        request.put("/api/allUser/updateData",this.form).then(res =>{
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
        this.form.password = md5(this.form.password)
        request.post("/api/allUser/saveData", this.form).then(res => {
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
      location.reload()
    },

    handleEdit(row){
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true

    },

    handleDelete(managerID){
      console.log(managerID)
      request.delete("/api/manager/"+managerID).then(res => {
        console.log(res)
        if(res.code === 0){
          this.$message({
            type:"success",
            message:"删除成功"
          })}else{
          this.$message({
            type:"error",
            message:res.msg
          })
        }
        location.reload()
      })
      this.load()
    },

    handleSizeChange(pageSize){
      this.pageSize = pageSize
      this.load()
    },

    handleCurrentChange(pageNum){
      this.currentPage = pageNum
      this.load()
    }


  }
}
</script>