<template>
    <el-card class="container" style="display: flex;flex-direction: column;position: relative;">

      <div style="font-size: 18px;text-align: center;"> 
        <span>详细资料 <i>{{cp.patientName}}</i></span>  
        <span style="margin-left:100px">诊断医生 <i>{{cp.doctorName}}</i></span>
      </div>
      <hr style="transform: translateY(5px)"/>

      <div class="box-card" >
        <div class="box1">
          <div>姓名  {{cp.patientName}}</div>
          <div>年龄  {{cp.patientAge}}岁</div>
          <div>性别  {{cp.patientGender}}</div>
          <div>日期  {{cp.date}}</div>
        </div>
        <div class="box2" v-if="cp.caseDesc !== ''">诊断报告：{{cp.caseDesc}}</div>
        <div class="box2" v-else>诊断报告：暂无报告</div>
      </div>

      <el-divider style="transform: translateY(-5px)"></el-divider>

      <a href="#"><i class="el-icon-caret-top"></i></a>

      <div style="margin-left: 50px;margin-top: -15px">
        <div style="padding: 5px;font-weight: bold">左足底压力参数</div>
        <div >
          <div class="footBox">
            <div>大脚趾: {{cp.paraT1}}</div>
            <div>第一跖骨: {{cp.paraM1}}</div>
            <div>第二跖骨: {{cp.paraM2}}</div>
            <div>第三跖骨: {{cp.paraM3}}</div>
          </div>
          <div class="footBox">
            <div>第四跖骨: {{cp.paraM4}}</div>
            <div>第五跖骨: {{cp.paraM5}}</div>
            <div>足后跟外侧: {{cp.paraHL}}</div>
            <div>足后跟内侧: {{cp.paraHM}}</div>
          </div>
        </div>
        <div style="padding: 5px;font-weight: bold">右足底压力参数</div>
        <div class="footBox">
          <div>大脚趾: {{cp.paraRT1}}</div>
          <div>第一跖骨: {{cp.paraRM1}}</div>
          <div>第二跖骨: {{cp.paraRM2}}</div>
          <div>第三跖骨: {{cp.paraRM3}}</div>
        </div>
        <div class="footBox">
          <div>第四跖骨: {{cp.paraRM4}}</div>
          <div>第五跖骨: {{cp.paraRM5}}</div>
          <div>足后跟外侧: {{cp.paraRHL}}</div>
          <div>足后跟内侧: {{cp.paraRHM}}</div>
        </div>
        <div style="padding: 5px;font-weight: bold">平均压力大小  <span style="font-weight: normal">  {{ava}}</span></div>
        <div style="padding: 5px;font-weight: bold">最大压力大小  <span style="font-weight: normal">  {{max}}</span></div>
      </div>

      <el-divider style="transform: translateY(-10px)"></el-divider>

      <div id="main" style="width:920px;height:600px;margin: 10px auto"></div>
      <div style="display: flex;">
        <div id="main1" style="width:400px;height:400px"></div>
        <div id="main2" style="width:400px;height:400px"></div>
      </div>
      <el-upload
          ref="upload"
          action="/wm/upload"
          :show-file-list="false"
          :on-change="readExcel"
          :auto-upload="false"
          style="display: inline-block"
      >
       转换数组
    </el-upload>
      <div id="main3"  style="width:900px;height:600px;margin: 10px auto"></div>
      <el-divider></el-divider>

      <h3 style="transform: translateY(-10px)" v-if="cp.doctorName == user.name">请在此输入诊断报告</h3>
      <h3 style="transform: translateY(-10px)" v-else-if="cp.doctorName !== user.name">请在此输入诊断意见</h3>
      <div>
        <el-input type="textarea" :rows="4" v-model="desc" class="input" placeholder="发表你的看法"></el-input>
        <div style="text-align: right; padding: 10px 0 5px 0">
          <el-button type="primary" @click="saveCpData()" v-if="cp.doctorName == user.name">保存诊断报告</el-button>
          <el-button type="primary" @click="saveDcData()" v-else-if="cp.doctorName !== user.name">保存诊断意见</el-button>
        </div>
      </div>

      <el-divider></el-divider>

  </el-card>
</template>

<script>
import request from "@/utils/request";
import * as echarts from "echarts";
import $ from "jquery";
import foot from '../assets/1.svg';
import {toRaw} from '@vue/reactivity';
import XLSX from "xlsx/dist/xlsx.full.min";

export default {
  name: "seePatientCp",

  data() {
    return {
      form: {},
      dialogVisible2:false,
      cp: {},
      desc:"",
      list:[],
      paraLData:[],
      paraRData:[],
      paraL:[],
      paraR:[],
      date:[],
      serie:[],
      serie1:[],
      ava:'',
      max:'',
      user:'',
      dc:{}
    }
  },

  created() {
    this.user = JSON.parse(sessionStorage.getItem('user'))||{}
    this.loadCp()
  },

  methods: {

    readExcel(file){ //此处接受的file，为文件上传的file
      var reader = new FileReader();
      //以二进制方式读取文件
      reader.readAsBinaryString(file.raw);
      reader.onload = (e) => {
          //获取文件数据
          const data = e.target.result;//e.target.value  
          //XLSX读取文件
          const wb = XLSX.read(data, { type: "binary" });
          //获取第一张表
          const wsname = wb.SheetNames[0];
          const ws = wb.Sheets[wsname];
          var result = XLSX.utils.sheet_to_json(ws, { header: 1 });
          // this.dataDetail(result)
          result.shift()
          this.draw3(result.reverse())
      };
    },

    loadCp() {
      request.get("/api/cp/cpByCpId", {
        params: {
          cpId: JSON.parse(sessionStorage.getItem('cpId'))
        }
      }).then(res => {
        this.cp = res.data
        console.log('cp',this.cp)
        let max1 = Math.max(this.cp.paraT1,this.cp.paraM1,this.cp.paraM2,this.cp.paraM3,this.cp.paraM4,this.cp.paraM5,this.cp.paraHL,this.cp.paraHM)
        let max2 = Math.max(this.cp.paraRT1,this.cp.paraRM1,this.cp.paraRM2,this.cp.paraRM3,this.cp.paraRM4,this.cp.paraRM5,this.cp.paraRHL,this.cp.paraRHM)
        this.max = Math.max(max1,max2)
        this.ava = (this.cp.paraT1+this.cp.paraM1+this.cp.paraM2+this.cp.paraM3+this.cp.paraM4+this.cp.paraM5+this.cp.paraHL+this.cp.paraHM+
                   this.cp.paraRT1+this.cp.paraRM1+this.cp.paraRM2+this.cp.paraRM3+this.cp.paraRM4+this.cp.paraRM5+this.cp.paraRHL+this.cp.paraRHM)/16
        this.list= [
          {name: 'LT',  value: 0},
          {name: 'LH',  value: this.cp.paraT1},
          {name: 'LM1', value: this.cp.paraM1},
          {name: 'LM2', value: this.cp.paraM2},
          {name: 'LM3', value: this.cp.paraM3},
          {name: 'LM4', value: this.cp.paraM4},
          {name: 'LM5', value: this.cp.paraM5},
          {name: 'LMF', value: 0},
          {name: 'LHL', value: this.cp.paraHL},
          {name: "LHM", value: this.cp.paraHM},
          {name: 'RT',  value: 0},
          {name: 'RH',  value: this.cp.paraRT1},
          {name: 'RM1', value: this.cp.paraRM1},
          {name: 'RM2', value: this.cp.paraRM2},
          {name: 'RM3', value: this.cp.paraRM3},
          {name: 'RM4', value: this.cp.paraRM4},
          {name: 'RM5', value: this.cp.paraRM5},
          {name: 'RMF', value: 0},
          {name: 'RHL', value: this.cp.paraRHL},
          {name: 'RHM', value: this.cp.paraRHM}
        ]
        //console.log(this.cp.paraHL)
        this.$nextTick(()=>{
          this.draw()
          // this.draw3()
        })
      this.init()
      
      })
    },

    saveCpData() {
      if (!this.desc) {
        this.$message({
          message: "未填写内容",
          type: "warning"
        });
        return;
      }else {
        this.cp.caseDesc = this.desc
        request.post("/api/cp/updateCaseDesc", this.cp).then(res => {
          if (res.code === 0) {
            this.$message({
              type: "success",
              message: "保存成功"
            })
            this.loadCp()
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
        })
        this.desc = ''
      }
    },

    saveDcData() {
      if (!this.desc) {
        this.$message({
          message: "未填写内容",
          type: "warning"
        });
        return;
      }else {
        this.dc.dcId = JSON.parse(sessionStorage.getItem('dcId'))
        this.dc.doctorId = this.user.id
        this.dc.cpId = this.cp.cpId
        this.dc.doctorCon = this.desc
        console.log('dc',this.dc)
        request.post("/api/dc/newDoctorCon", this.dc).then(res => {
          if (res.code === 0) {
            this.$message({
              type: "success",
              message: "保存成功"
            })
            this.loadCp()
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
        })
        this.desc = ''
      }
    },

    draw1(a){
      let chartDom  = document.getElementById('main1');
      let myChart  = echarts.init(chartDom);
      //console.log()
      for(let i=0;i<a.length;i++){
        let item={
          type: 'line',
          data:a[i]
        };
        this.serie.push(item);
      }
      //console.log(this.serie)

      var option = {
        title: {
          text: '左脚数据历史对比图',
          x:'center'
        },
        tooltip: {
          trigger: 'axis',
          textStyle:{
            align:'left'
          },
        formatter:(params)=>{
          let date = this.date
          let count = params.length
          let str = params[0].axisValue+"</br>";
          for(let i=0;i<count;i++){
            str= str+params[i].marker+date[i]+": "+ params[i].data+"</br>";
          }
            return str
          }
        },
        grid: {
          top: '10%',
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: ['LT1','LM1','LM2','LM3','LM4','LM5','LHL','LHM']
        },
        yAxis: {
          axisLine:{
            show: true
          },
          type: 'value',
          min:0,
          max:20
        },
        series: this.serie
      };

      option && myChart.setOption(option);
    },

    draw2(a){
      let chartDom  = document.getElementById('main2');
      let myChart  = echarts.init(chartDom);
      //console.log()
      for(let i=0;i<a.length;i++){
        let item={
          type: 'line',
          data:a[i]
        };
        this.serie1.push(item);
      }
     // console.log(this.serie1)

      var option = {
        title: {
          text: '右脚数据历史对比图',
          x:'center'
        },
        tooltip: {
          trigger: 'axis',
          textStyle:{
            align:'left'
          },
          formatter:(params)=>{
            let date = this.date
            let count = params.length
            let str = params[0].axisValue+"</br>";
            for(let i=0;i<count;i++){
              str= str+params[i].marker+date[i]+": "+ params[i].data+"</br>";
            }
            return str
          }
        },
        grid: {
          top: '10%',
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: ['RT1','RM1','RM2','RM3','RM4','RM5','RHL','RHM']
        },
        yAxis: {
          axisLine:{
            show: true
          },
          type: 'value',
          min:0,
          max:20
        },
        series: this.serie1
      };

      option && myChart.setOption(option);
    },

    draw() {
      let chartDom = document.getElementById('main');
      let myChart = echarts.init(chartDom);
      let option;
      let that = this
      $.get(foot, function (svg) { 
        echarts.registerMap('foot', {svg: svg});
        option = {
          tooltip: {},
          title: {
            text:'足底压力示意图',
            x:'center',
          },
          visualMap: {
            left: 70,
            bottom: '30%',
            min: 0,
            max: 10,
            orient: 'vertical',
            text: ['','压力大小'],
            realtime: true,
            calculable: true,
            inRange: {
              color: ['#FFF500', '#FFAA00','#FF4900','#F5001D']
            }
          },
          series: [
            {
              name: 'foot',
              type: 'map',
              map: 'foot',
              roam: true,
              emphasis: {
                label: {
                  show: false
                }
              },
              selectedMode: false,
              data: that.list
            }
          ]
        };
        myChart.setOption(option);
      });
      option && myChart.setOption(option);
    },

   
    draw3(result){
      console.log('result',result)
      var chartDom = document.getElementById('main3');
      var myChart = echarts.init(chartDom);
      var option;
      var url = result
      $.get('', function (data) {
        console.log('data',result)
        let date = result
        myChart.setOption(
          (option = {
            title: {
              text: '足部时间积分表',
              left: '1%'
            },
            tooltip: {
              trigger: 'axis'
            },
            grid: {
              left: '5%',
              right: '15%',
              bottom: '10%'
            },
            xAxis: {
              data: date.map(function (item) {
                return item[0];
              })
            },
            yAxis: {},
            toolbox: {
              right: 10,
              feature: {
                dataZoom: {
                  yAxisIndex: 'none'
                },
                restore: {},
                saveAsImage: {}
              }
            },
            dataZoom: [
              {
                startValue: date[0][0],
                endValue: date[100][0]
              },
              {
                type: 'inside'
              }
            ],
            visualMap: {
              top: 50,
              right: 10,
              pieces: [
                {
                  gt: 0,
                  lte: 50,
                  color: '#93CE07'
                },
                {
                  gt: 50,
                  lte: 100,
                  color: '#FBDB0F'
                },
                {
                  gt: 100,
                  lte: 150,
                  color: '#FC7D02'
                },
                {
                  gt: 150,
                  lte: 200,
                  color: '#FD0100'
                },
                {
                  gt: 200,
                  lte: 300,
                  color: '#AA069F'
                },
                {
                  gt: 300,
                  color: '#AC3B2A'
                }
              ],
              outOfRange: {
                color: '#999'
              }
            },
            series: {
              name: '足部压力时间积分表',
              type: 'line',
              data: date.map(function (item) {
                return item[1];
              }),
              markLine: {
                silent: true,
                lineStyle: {
                  color: '#333'
                },
                data: [
                  {
                    yAxis: 50
                  },
                  {
                    yAxis: 100
                  },
                  {
                    yAxis: 150
                  },
                  {
                    yAxis: 200
                  },
                  {
                    yAxis: 300
                  }
                ]
              }
            }
          })
        );
      });

      option && myChart.setOption(option);
    },
    init(){
      request.post("/api/cp/cpByPatientId",this.cp).then(res=>{
        //console.log(res)
        for(let i = 0;i < res.data.length; i++){
          this.date.push(res.data[i].date)
          this.paraR.push(res.data[i].paraRT1,res.data[i].paraRM1,res.data[i].paraRM2,res.data[i].paraRM3,res.data[i].paraRM4,res.data[i].paraRM5,res.data[i].paraRHL,res.data[i].paraRHM)
          this.paraL.push(res.data[i].paraT1,res.data[i].paraM1,res.data[i].paraM2,res.data[i].paraM3,res.data[i].paraM4,res.data[i].paraM5,res.data[i].paraHL,res.data[i].paraHM)
          this.paraLData.push([...this.paraL])
          this.paraRData.push([...this.paraR])
          this.paraL = []
          this.paraR = []
        }
        //console.log(this.paraLData)
        const a = toRaw(this.paraLData)
        const b = toRaw(this.paraRData)
        console.log(b)
        this.$nextTick(()=>{
          this.draw1(a)
          this.draw2(b)
        })
      })
    }
  }
}
</script>

<style scoped>
.container{
  width:920px;
  min-height:1000px;
  background-color: #FEFDFD
}
.box-card{
  display:flex;
  flex-direction: column;
  margin-top: 10px;
}

.box1{
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  width: 540px;
  font-size: 14px;
  margin-top: 10px;
  margin-left: 55px;
}

.box1>div{
  width: 180px;
}

.box2{
  margin-left: 45px;
  padding: 10px;
  font-size: 15px;
  line-height: 15px;
  margin-top: 10px;
}

.box-card > img{
  margin-left: 50px;
  width:240px;
  height:240px;
}

.caption > h1,.caption > h2{
  width:200px;
  font-weight: bold;
  margin-bottom: 30px;
}

.footBox{
  width: 800px;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  margin-left: 40px;
}
.footBox >div{
  width: 200px;
  font-size: 15px;
}

a{
  width:40px;
  height: 40px;
  position: fixed;border-radius: 50%;
  bottom:60px;
  right:135px;
  background-color: #F0F8FF;
  border:1px solid #C6E2FF;
}

a i{
  position: fixed;
  bottom: 73px;
  right: 146px;
}

a:hover{
  background: linear-gradient(200deg,#f3e7e9,#e3eeff);
  transition: background-color 0.5s ease;
}

</style>
