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


    this.request.get("/echarts/memberDistribution").then(res =>{
      if (res.code === "200"){
        option.xAxis .data= res.data.x
        option.series[0].data = res.data.y
        option.series[1].data = res.data.y


        myChart.setOption(option);
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

    // var chartDom2 = document.getElementById('main2');
    // var myChart2 = echarts.init(chartDom2);
    // var option2;

    // option2 = {
    //   color: ['#67F9D8', '#FFE434', '#56A3F1', '#ff917c'],
    //   title: {
    //     text: 'Customized Radar Chart'
    //   },
    //   legend: {},
    //   radar: [
    //     {
    //       indicator: [
    //         { text: 'Indicator1' },
    //         { text: 'Indicator2' },
    //         { text: 'Indicator3' },
    //         { text: 'Indicator4' },
    //         { text: 'Indicator5' }
    //       ],
    //       center: ['25%', '50%'],
    //       radius: 120,
    //       startAngle: 90,
    //       splitNumber: 4,
    //       shape: 'circle',
    //       axisName: {
    //         formatter: '【{value}】',
    //         color: '#428BD4'
    //       },
    //       splitArea: {
    //         areaStyle: {
    //           color: ['#77EADF', '#26C3BE', '#64AFE9', '#428BD4'],
    //           shadowColor: 'rgba(0, 0, 0, 0.2)',
    //           shadowBlur: 10
    //         }
    //       },
    //       axisLine: {
    //         lineStyle: {
    //           color: 'rgba(211, 253, 250, 0.8)'
    //         }
    //       },
    //       splitLine: {
    //         lineStyle: {
    //           color: 'rgba(211, 253, 250, 0.8)'
    //         }
    //       }
    //     },
    //     {
    //       indicator: [
    //         { text: 'Indicator1', max: 150 },
    //         { text: 'Indicator2', max: 150 },
    //         { text: 'Indicator3', max: 150 },
    //         { text: 'Indicator4', max: 120 },
    //         { text: 'Indicator5', max: 108 },
    //         { text: 'Indicator6', max: 72 }
    //       ],
    //       center: ['75%', '50%'],
    //       radius: 120,
    //       axisName: {
    //         color: '#fff',
    //         backgroundColor: '#666',
    //         borderRadius: 3,
    //         padding: [3, 5]
    //       }
    //     }
    //   ],
    //   series: [
    //     {
    //       type: 'radar',
    //       emphasis: {
    //         lineStyle: {
    //           width: 4
    //         }
    //       },
    //       data: [
    //         {
    //           value: [100, 8, 0.4, -80, 2000],
    //           name: 'Data A'
    //         },
    //         {
    //           value: [60, 5, 0.3, -100, 1500],
    //           name: 'Data B',
    //           areaStyle: {
    //             color: 'rgba(255, 228, 52, 0.6)'
    //           }
    //         }
    //       ]
    //     },
    //     {
    //       type: 'radar',
    //       radarIndex: 1,
    //       data: [
    //         {
    //           value: [120, 118, 130, 100, 99, 70],
    //           name: 'Data C',
    //           symbol: 'rect',
    //           symbolSize: 12,
    //           lineStyle: {
    //             type: 'dashed'
    //           },
    //           label: {
    //             show: true,
    //             formatter: function (params) {
    //               return params.value;
    //             }
    //           }
    //         },
    //         {
    //           value: [100, 93, 50, 90, 70, 60],
    //           name: 'Data D',
    //           areaStyle: {
    //             color: new echarts.graphic.RadialGradient(0.1, 0.6, 1, [
    //               {
    //                 color: 'rgba(255, 145, 124, 0.1)',
    //                 offset: 0
    //               },
    //               {
    //                 color: 'rgba(255, 145, 124, 0.9)',
    //                 offset: 1
    //               }
    //             ])
    //           }
    //         }
    //       ]
    //     }
    //   ]
    // };

    // myChart2.setOption(option2);
  }
}
</script>

<style scoped>

</style>