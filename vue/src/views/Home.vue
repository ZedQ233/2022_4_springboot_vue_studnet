<template>
<div >
  <el-row gutter="10" style="margin-bottom: 40px">
    <el-col :span="6">
      <el-card >
        <div style="color:#F56C6C;">
          用户总数
        </div>
        <div style="padding: 10px 0;text-align: center;font-weight: bold">
          100
        </div>
      </el-card>
    </el-col>
    <el-col :span="6">
      <el-card>
        <div style="color:#F56C6C;">
          销售总数
        </div>
        <div style="padding: 10px 0;text-align: center;font-weight: bold">
          200
        </div>
      </el-card>
    </el-col>
    <el-col :span="6">
      <el-card>
        <div style="color:#F56C6C;">
          门店总数
        </div>
        <div style="padding: 10px 0;text-align: center;font-weight: bold">
          300
        </div>
      </el-card>
    </el-col>
    <el-col :span="6">
      <el-card>
        <div style="color:#F56C6C;">
          订单总数
        </div>
        <div style="padding: 10px 0;text-align: center;font-weight: bold">
          500
        </div>
      </el-card>
    </el-col>
  </el-row>
  <el-row >
    <el-col :span="12">
      <div id="main" style="width: 500px; height: 450px">  </div>
    </el-col>

    <el-col :span="12">
      <div id="main2" style="width: 500px; height: 450px">  </div>
    </el-col>
  </el-row>

</div>
</template>

<script>
import * as echarts from 'echarts'


export default {
  name: "Home.vue",
  data(){
    return{

    }
  },
  //渲染好之后触发
  mounted() {
    var chartDom = document.getElementById('main');
    var myChart = echarts.init(chartDom);
    var option;

    // 饼图
    var chartDom2 = document.getElementById('main2');
    var myChart2 = echarts.init(chartDom2);
    var option2;

    // 填充数据
    this.request.get("/echarts/memberDistribution").then(res =>{
      if (res.code === "200"){
        option.xAxis.data= res.data.x
        option.series[0].data = res.data.y
        option.series[1].data = res.data.y

        // 男
        option.series[2].data = res.data.y1
        option.series[3].data = res.data.y1
        // 女
        option.series[4].data = res.data.y0
        option.series[5].data = res.data.y0



        let data = res.data
        // //设置名字
        // option2.series[0].data[0].name = data.x[0]
        // option2.series[0].data[1].name = data.x[1]
        // option2.series[0].data[2].name = data.x[2]
        // option2.series[0].data[3].name = data.x[3]
        // // 设置数值
        // option2.series[0].data[0].value = data.y[0]
        // option2.series[0].data[1].value = data.y[1]
        // option2.series[0].data[2].value = data.y[2]
        // option2.series[0].data[3].value = data.y[3]

        //设置名字
        option2.series[0].data[0].name = data.x0[0]
        option2.series[0].data[1].name = data.x0[1]
        option2.series[0].data[2].name = data.x0[2]
        option2.series[0].data[3].name = data.x0[3]
        // 设置数值
        option2.series[0].data[0].value = data.y0[0]
        option2.series[0].data[1].value = data.y0[1]
        option2.series[0].data[2].value = data.y0[2]
        option2.series[0].data[3].value = data.y0[3]

        //设置名字
        option2.series[1].data[0].name = data.x1[0]
        option2.series[1].data[1].name = data.x1[1]
        option2.series[1].data[2].name = data.x1[2]
        option2.series[1].data[3].name = data.x1[3]
        // 设置数值
        option2.series[1].data[0].value = data.y1[0]
        option2.series[1].data[1].value = data.y1[1]
        option2.series[1].data[2].value = data.y1[2]
        option2.series[1].data[3].value = data.y1[3]




        myChart.setOption(option);
        myChart2.setOption(option2);
      }
    })


    option = {
    title: {
      text: '季度新增用户统计',
      subtext: '今年',
      left: 'center'
    },
    legend: {
      orient: 'vertical',
      left: 'left',
    },
    grid: {
      left: '11%',
      right: '10%',
      bottom: '6%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: []
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: '总计',
        data: [],
        type: 'line'
      },
      {
        name: '总计',
        data: [],
        type: 'bar'
      },
      {
        name: '男',
        data: [],
        type: 'line'
      },
      {
        name: '男',
        data: [],
        type: 'bar'
      },
      {
        name: '女',
        data: [],
        type: 'line'
      },
      {
        name: '女',
        data: [],
        type: 'bar'
      }

    ]
  };

    option2 = {
      title: {
        text: '季度新增用户统计',
        subtext: '今年',
        left: 'center',
      },
      tooltip: {
        trigger: 'item',
        formatter: '{b} <br/>{a} : {c}人 ({d}%)'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          name: '新增女性会员',
          type: 'pie',
          radius: '50%',
          center: ['25%', '50%'],
          label:{
            normal:{
              show:true,
              position:'inner',
              textStyle:{
                fontWeight:300,
                fontsize:16
              },
              formatter:'{d}%',
              color:'white',
            }
          },
          data: [
            { value: 0, name: "" },
            { value: 0, name: "" },
            { value: 0, name: "" },
            { value: 0, name: "" }
          ],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        },
        {
          name: '新增男性会员',
          type: 'pie',
          radius: '50%',
          center: ['75%', '50%'],
          label:{
            normal:{
              show:true,
              position:'inner',
              textStyle:{
                fontWeight:300,
                fontsize:16
              },
              formatter:'{d}%',
              color:'white',
            }
          },
          data: [
            { value: 0, name: "" },
            { value: 0, name: "" },
            { value: 0, name: "" },
            { value: 0, name: "" }
          ],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    };


  }
}
</script>

<style scoped>

</style>