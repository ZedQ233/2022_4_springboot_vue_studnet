<template>
<div >
  <el-row >
    <el-col :span="12">
      <div id="main" style="width: 500px; height: 400px">  </div>
    </el-col>

    <el-col :span="12">
      <div id="main2" style="width: 500px; height: 400px">  </div>
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

    this.request.get("/echarts/memberDistribution").then(res =>{
      if (res.code === "200"){
        option.xAxis.data= res.data.x
        option.series[0].data = res.data.y
        option.series[1].data = res.data.y

        let data = res.data
        //设置名字
        option2.series[0].data[0].name = data.x[0]
        option2.series[0].data[1].name = data.x[1]
        option2.series[0].data[2].name = data.x[2]
        option2.series[0].data[3].name = data.x[3]

        // 设置数值
        option2.series[0].data[0].value = data.y[0]
        option2.series[0].data[1].value = data.y[1]
        option2.series[0].data[2].value = data.y[2]
        option2.series[0].data[3].value = data.y[3]

        myChart.setOption(option);
        myChart2.setOption(option2);
      }
    })
      option = {
      xAxis: {
        type: 'category',
        data: []
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          data: [],
          type: 'line'
        },
        {
          data: [],
          type: 'bar'
        }
      ]
    };



    option2 = {
      title: {
        text: '季度新增用户统计',
        subtext: '今年',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          name: 'Access From',
          type: 'pie',
          radius: '50%',
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