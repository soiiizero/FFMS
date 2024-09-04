<template>
  <div class="part">
    <div v-if="isNull">
      <span>系统还未录入任何收入和支出信息。</span>
    </div>
    <div v-else class="bottom">
      <div class="charts1">
        <div class="top">
          <span class="spans">家庭总资产情况</span>
          <div class="balance-container">
            <div>
              <span class="balance-span">总收入></span>
              <div class="balance-item">{{ totalIncome }}</div>
            </div>
            <div>
              <span class="balance-span">总支出></span>
              <div class="balance-item">{{ totalPay }}</div>
            </div>
            <div>
              <span class="balance-span">余额></span>
              <div class="balance-item">{{ balance }}</div>
            </div>
          </div>
        </div>
        <div ref="chart" class="chart-container topside"></div>
      </div>
      <div class="charts2">
        <div ref="payincomeChart" class="chart-container chart-container-large"></div>
        <div ref="toPersonChart" class="chart-container chart-container-small"></div>
      </div>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  data() {
    return {
      isNull: false,
      name: null,
      category: {
        '收入': 0,
        '支出': 0,
        incomeway: [], // 存储收入方式数据
        payway: [], // 存储支出方式数据
        personData: [], // 存储按人消费的金额数据
      },
      chart: null, // 用于存储图表实例
      payincomeChart: null,
      totalIncome: 0, // 总收入
      totalPay: 0,    // 总支出
      balance: 0,     // 余额
    };
  },
  created() {
    this.getPayInfo();
    this.getIncomeInfo();
  },
  methods: {
    async getPayInfo() {
      try {
        const res = await this.$axios('/api/pays');
        let pays = res.data.data;
        this.category['支出'] = pays.reduce((sum, item) => sum + item.money, 0);
        this.category.payway = pays.reduce((acc, item) => {
          let found = acc.find(el => el.payway === item.payway);
          if (found) {
            found.money += item.money;
          } else {
            acc.push({ payway: item.payway, money: item.money });
          }
          return acc;
        }, []);
        this.category.personData = pays.reduce((acc, item) => {
          let found = acc.find(el => el.name === item.toperson);
          if (found) {
            found.money += item.money;
          } else {
            acc.push({ name: item.toperson, money: item.money });
          }
          return acc;
        }, []);
        this.isNull = pays.length === 0;
        if (!this.isNull) {
          this.initChart();
          this.drawPayIncomeChart();
          this.inittoPersonChart();
        }
        this.totalPay = this.category['支出']; // 更新总支出
        this.balance = this.totalIncome - this.totalPay; // 更新余额
      } catch (error) {
        console.error('Error fetching pay info:', error);
      }
    },
    async getIncomeInfo() {
      try {
        const res = await this.$axios('/api/incomes');
        let incomes = res.data.data;
        this.category['收入'] = incomes.reduce((sum, item) => sum + item.money, 0);
        this.category.incomeway = incomes.reduce((acc, item) => {
          let found = acc.find(el => el.incomeway === item.incomeway);
          if (found) {
            found.money += item.money;
          } else {
            acc.push({ incomeway: item.incomeway, money: item.money });
          }
          return acc;
        }, []);
        this.isNull = incomes.length === 0;
        if (!this.isNull) {
          this.initChart();
          this.drawPayIncomeChart();
        }
        this.totalIncome = this.category['收入']; // 更新总收入
        this.balance = this.totalIncome - this.totalPay; // 更新余额
      } catch (error) {
        console.error('Error fetching income info:', error);
      }
    },
    initChart() {
      if (this.chart) {
        this.chart.dispose(); // 如果已有图表实例则销毁
      }
      if (!this.isNull) {
        this.chart = echarts.init(this.$refs.chart);
        const option = {
          title: {
            text: '收入与支出比例',
            x:'center'
          },
          tooltip: {
            trigger: 'item',
            formatter: '{b}: {c} ({d}%)', // {b} 是名称，{c} 是数值
          },
          legend: {
            orient: 'vertical',
            left: 'left',
          },
          series: [
            {
              name: '支出',
              type: 'pie',
              radius: '70%',
              center: ['50%', '50%'],
              data: [
                {value: this.category['支出'], name: '支出'},
                {value: this.category['收入'], name: '收入'},
              ],
              emphasis: {
                label: {
                  show: true, // 鼠标悬停时显示标签
                  formatter: '{b}: {c} ({d}%)', // {b} 是名称，{c} 是数值
                },
                itemStyle: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)',
                },
              },
            },
          ],
        };
        this.chart.setOption(option);
      }
    },
    resetForm() {
      this.input = {
        payway:'',
        startTime:null,
        endTime:null
      };
      this.searchpayName = '';
      this.searchlabel1 = '';
      this.searchlabel2 = '';

      this.getPayInfo();
      this.getIncomeInfo();
    },
    initPayIncomeCharts(){
      if (this.payincomeChart) {
        this.payincomeChart.dispose(); // 如果已有图表实例则销毁
      }
      this.payincomeChart = echarts.init(this.$refs.payincomeChart);

      const option = {
        title: {
          text: '收支方式对比',
          x: 'center'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {
          data: ['收入', '支出'],
          x: 'left'
        },
        xAxis: {
          type: 'category',
          data: ['微信', '支付宝', '银联', '现金', '其他']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '收入',
            type: 'line',
            //stack: '总量',
            areaStyle: {}, // 启用面积图样式
            data: ['微信', '支付宝', '银联', '现金', '其他'].map(method => {
              const item = this.category.incomeway.find(d => d.incomeway === method);
              return item ? item.money : 0;
            })
          },
          {
            name: '支出',
            type: 'line',
            //stack: '总量',
            areaStyle: {}, // 启用面积图样式
            data: ['微信', '支付宝', '银联', '现金', '其他'].map(method => {
              const item = this.category.payway.find(d => d.payway === method);
              return item ? item.money : 0;
            })
          },
        ]
      };

      this.payincomeChart.setOption(option);
    },
    drawPayIncomeChart(){
      // 初始化图表
      this.initPayIncomeCharts();
    },
    inittoPersonChart() {
      if (this.toPersonChart) {
        this.toPersonChart.dispose();
      }
      this.toPersonChart = echarts.init(this.$refs.toPersonChart);
      this.drawtoPersonChart();
    },
    drawtoPersonChart() {
      // 过滤出唯一的人员名称
      const personData = this.category.personData.map(item => item.name).filter((value, index, self) => self.indexOf(value) === index);

      this.toPersonChart.setOption({
        title: {
          text: '按消费对象统计',
          x: 'center',
          textStyle: {
            fontSize: 18,
            fontWeight: 'bold',
            color: '#333'
          }
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow',
          },
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true, // 包含标签
          // 设置边框样式
          borderColor: '#ccc',
          borderWidth: 1,
        },
        xAxis: {
          type: 'value', // 修改为数值轴
          show: false, // 隐藏x轴线
        },
        yAxis: {
          type: 'category', // 修改为类别轴
          data: personData, // 类别名称
          axisLine: {
            show: false, // 隐藏y轴线
          },
          axisTick: {
            show: true, // 保留纵坐标的刻度值
          },
        },
        series: [
          {
            name: '消费金额',
            type: 'bar', // 确保是柱状图类型
            data: this.category.personData.map(item => item.money),
            // 如果需要，可以设置柱子的样式
            itemStyle: {
              color: (params) => {
                const colors = ['#5470C6', '#91CC75', '#EE6666', '#FFC93C', '#FF6E9C'];
                return colors[params.dataIndex % colors.length]; // 轮流使用颜色
              },
              borderRadius: [10, 10, 10, 10],// 设置柱子的四个角为圆角，第一个和第二个参数为左上和右上角的半径，第三个和第四个为右下和左下角的半径
              // color: '#5470C6' // 如果需要，可以设置柱子的颜色
              shadowColor: 'rgba(0, 0, 0, 0.3)',
              shadowBlur: 10,
            },
          },
        ],
      });
    }


  },
};
</script>

<style lang="less" scoped>
.part {
  //width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
}
.top{
  //padding: 10px;
  //text-align: center;
  flex: 4;
  margin-right: 10px;
  background-color: #0f9876; // 背景颜色
  border-radius: 8px; // 圆角
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); // 阴影效果
  display: flex;
  //justify-content: center;
  flex-direction: column;
}
.topside{
  flex: 6;
  margin-right: 10px;
  display: flex;
  justify-content: center;
  background-color: #f0f0f0;
  border-radius: 8px; // 圆角
}
.spans{
  color: #ffffff;
  font-weight: bold;
  font-size: 30px;
  margin: 20px;
}
.spans3{
  color: #ffffff;
  font-weight: bold;
  font-size: 30px;
  margin: 20px;
}
.bottom{
  display: flex;
  flex-direction: column;
  flex: 1;
  margin: 10px;
  background-color: #ffffff; // 背景颜色
  border-radius: 8px; // 圆角
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); // 阴影效果
}
.charts1{
  display: flex;
  flex-direction: row;
  padding: 10px;
  //height: 70%;
  flex: 5.7;
}
.charts2{
  display: flex;
  flex-direction: row;
  padding: 10px;
  //height: 30%;
  flex: 4.3;
}
.charts3{
  padding: 10px;
  //height: 30%;
  flex: 2;
}
.chart-container3 {
  border: 1px solid #f0f0f0;
  padding: 5px;
  box-sizing: border-box;
  background-color: #f0f0f0;
  border-radius: 8px; // 圆角
}
.chart-container {
  border: 1px solid #f0f0f0;
  padding: 5px;
  box-sizing: border-box;
}

.chart-container-large {
  flex: 7; /* 70% of charts2 width */
  margin-right: 10px;
  //display: flex;
  //justify-content: center;
  background-color: #f0f0f0;
  border-radius: 8px; // 圆角
}

.chart-container-small {
  flex: 3; /* 30% of charts2 width */
  //display: flex;
  //justify-content: center;
  background-color: #f0f0f0;
  border-radius: 8px; // 圆角
}
.balance-container {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  padding: 10px;
}
.balance-span{
  text-align: left;
  margin: 20px;
  color: rgba(210, 209, 209, 0.82);
  //font-weight: bold;
  font-size: 20px;
}
.balance-item {
  text-align: left;
  margin: 20px;
  color: #ffffff;
  font-weight: bold;
  font-size: 30px;
}
</style>
