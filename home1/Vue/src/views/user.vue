<template>
  <el-card class="box-card" style="background-color: #FEFDFD">
    <div style="padding: 10px">
      <div style="margin: 10px 0">
        <el-input v-model="search" placeholder="please input name" style="width:20%" clearable></el-input>
        <el-button type="primary" style="margin-left:5px" @click="load">查询</el-button>
        <el-button type="primary" @click="Add">新增</el-button>
      </div>
      <el-table
          :data="tableData" stripe
          :header-cell-style="{background:'#F0F8FF',color:'#303133'}"
          style="width: 100%">
        <el-table-column
            align="center"
            sortable
            prop="id"
            label="ID号" width="80px">
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
            <span v-if="scope.row.role===2">医生</span>
            <span v-if="scope.row.role===3">病人</span>
            <span v-if="scope.row.role===1">管理员</span>
          </template>
        </el-table-column>
        <el-table-column
            align="center"
            fixed="right"
            label="操作"
            width="150px">
          <template #default="scope">
            <!--                      <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>-->
            <el-popconfirm
                confirmButtonText='好的'
                cancelButtonText='不用了'
                icon="el-icon-info"
                iconColor="red"
                title="这是一段内容确定删除吗？" @confirm="handleDelete(scope.row.id)"
            >
              <template #reference>
                <el-button type="danger" icon="el-icon-delete" circle size="small"></el-button>
              </template>
            </el-popconfirm>
            <el-button type="primary" icon="el-icon-edit" circle size="small" @click="handleEdit(scope.row)"></el-button>
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

      <el-dialog title="更新信息" v-model="dialogVisible" width="30%">
        <el-form :model="form" label-width="120px" :rules="rules">
          <el-form-item label="名称">
            <el-input v-model="form.name" style="width:80%" ></el-input>
          </el-form-item>
          <el-form-item label="性别" prop="gender">
            <el-input v-model="form.gender" style="width:80%"></el-input>
          </el-form-item>
          <el-form-item label="年龄" prop="age">
            <el-input v-model="form.age" style="width:80%"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="form.email" style="width:80%"></el-input>
          </el-form-item>
          <el-form-item label="权限" prop="role">
            <el-input v-model="form.role" disabled = true style="width:80%" placeholder="3"></el-input>
          </el-form-item>
          <span class="dialog-footer" style="margin-left: 30px ;display: flex;justify-content: space-around;align-items: center">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="saveData">确 定</el-button>
          </span>
        </el-form>
      </el-dialog>

    </div>
  </el-card>
</template>

<script>

import request from "@/utils/request";

export default {
  name:"user",
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
      user:{},
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
        role: [
          { required: true, message: '权限设置：2为医生，3为病人', trigger: 'blur',type: "enum", enum: ['2', '3'] },
        ],
      },
    }
  },
  created(){
    this.load();
    let str = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(str)
  },
  methods:{
    Add(){
      this.dialogVisible = true
      this.form={role:3}
    },

    saveData(){
      if(this.form.id){
        request.put("/api/allUser/updateData",this.form).then(res =>{
          console.log(res)
          if(res.code === 0){
            this.$message({
              type:"success",
              message:"修改成功"
            })
            location.reload()
          }else{
            this.$message({
              type:"error",
              message:res.msg
            })
          }
        })
      }else {
        request.post("/api/allUser/saveData", this.form).then(res => {
          console.log(res)
          if(res.code === 0){
            this.$message({
              type:"success",
              message:"添加成功"
            })
            location.reload()
          }else{
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

    handleEdit(row){
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
    },

    handleDelete(workerID){
      console.log(workerID)
      request.delete("/api/allUser/"+workerID).then(res => {
        console.log(res)
        if(res.code === 0){
          this.$message({
            type:"success",
            message:"删除成功"
          })
          location.reload()
        }else{
          this.$message({
            type:"error",
            message:res.msg
          })
        }
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
    },

    load(){
      request.get("/api/allUser/findPage2",{
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
    }

  }
}
</script>