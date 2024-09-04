<template>
  <div class="part">
    <div v-if="user.role == 0" class="top">
      <el-input
        v-model="form.Name"
        placeholder="姓名"
        class="text"
      ></el-input>
      <el-button type="primary" @click="printform" style="background-color: #0f9876;border-color: #0f9876">搜索</el-button>
      <el-button type="primary" @click="resetForm" style="background-color: #a7a7a8; border-color: #a7a7a8">重置</el-button>
    </div>
    <div class="bottom">
      <div class="chart">
        <div ref="chartContainer" class="chart-container1"></div>
        <div ref="chartContainer2" class="chart-container2"></div>
      </div>
      <div class="chartside">
        <div>
          <span class="spans">资产分析报告</span>
          <el-divider></el-divider>
          <div>
            <span class="income-text">本月收入总金额 <span class="income-yuan">￥{{ totalIncome }}</span></span>
            <p class="texttype income-text2">> 相比上一个月
              <span v-if="incomeChangeStatus === '没有变化'" class="income-text2">没有变化</span>
              <span v-else class="income-text2 income-text4">{{ incomeChangeStatus }} <span class="income-text4">{{ incomeChangePercentage }}%</span></span>
            </p>
            <p class="income-text5">> 最多的一项是<span class="income-text3">{{ maxLabelIncomeName }}</span>，占总金额<span class="income-text4">{{ maxLabelIncomePercentage }}%</span></p>
          </div>
          <div>
            <span class="income-text">本月支出总金额 <span class="income-yuan">￥{{ totalExpense }}</span></span>
            <p class="texttype income-text2">> 相比上一个月
              <span v-if="expenseChangeStatus === '没有变化'" class="income-text2">没有变化</span>
              <span v-else class="income-text2 income-text4">{{ expenseChangeStatus }} <span class="income-text4">{{ expenseChangePercentage }}%</span></span>
            </p>
            <p class="income-text5">> 最多的一项是<span class="income-text3">{{ maxLabelExpenseName }}</span>，占总金额<span class="income-text4">{{ maxLabelExpensePercentage }}%</span></p>
          </div>
          <div>
            <span class="income-text">本月平均支出 <span class="income-yuan">￥{{ averageExpense }}</span></span>
            <p class="income-text6" v-if="averageExpenseChangeStatus !== '没有变化'">> 相比上一个月平均支出 <span class="income-text4">{{ averageExpenseChangeStatus }} {{ averageExpenseChangePercentage }}%</span></p>
            <p class="income-text6" v-else>> 相比上一个月平均支出没有变化</p>
            <span class="income-text">本月平均收入 <span class="income-yuan">￥{{ averageIncome }}</span></span>
            <p class="income-text6" v-if="averageIncomeChangeStatus !== '没有变化'">> 相比上一个月平均收入 <span class="income-text4">{{ averageIncomeChangeStatus }} {{ averageIncomeChangePercentage }}%</span></p>
            <p class="income-text6" v-else>> 相比上一个月平均收入没有变化</p>
          </div>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import * as echarts from 'echarts';

export default {
  data() {
    return {
      user: { //用户信息
        Name: null,
        role: null
      },
      form: {
        Name: '',
      },
      originalData: [],
      originalData2: [],
      chart: null, // 用于存储图表实例
      chart2: null, // 用于存储第二个图表实例
      filteredPayResults: [],  // 用于存储筛选后的支出结果
      filteredIncomeResults: [], // 用于存储筛选后的收入结果
      totalIncome: 0,
      totalExpense: 0,
      maxLabelIncomePercentage: 0,
      maxLabelExpensePercentage: 0,
      incomeChangeStatus: '',
      expenseChangeStatus: '',
      averageIncome: 0,
      averageExpense: 0,
      maxLabelIncomeChangeStatus: '',
      maxLabelExpenseChangeStatus: '',
      averageIncomeChangeStatus: '',
      averageExpenseChangeStatus: '',
      maxLabelIncomeName: '', // 存储最大收入标签名称
      maxLabelExpenseName: '', // 存储最大支出标签名称
      lastMonthTotalIncome: 0,
      lastMonthTotalExpense: 0,
      lastMonthAverageIncome: 0,
      lastMonthAverageExpense: 0,
      incomeChangePercentage: 0,
      expenseChangePercentage: 0,
      maxLabelIncomeChangePercentage: 0,
      maxLabelExpenseChangePercentage: 0,
      averageIncomeChangePercentage: 0,
      averageExpenseChangePercentage: 0,
    };
  },
  created() {
    this.getUserInfo();
  },
  mounted() {
    this.getAllInfo();
    this.getAllInfo2();
  },
  methods: {
    getUserInfo() { //获取用户信息
      let userName = this.$cookies.get("cname")
      let role = this.$cookies.get("role")
      this.user.Name = userName
      this.user.role = role
      if (this.user.role == 1) {
        this.form.Name = this.user.Name;
      }
    },
    getAllInfo() {
      this.$axios(`/api/pays`).then(res => {
        this.originalData = res.data.data;
        this.originalData.sort((a, b) => new Date(a.datetime) - new Date(b.datetime));
        this.printform();
        console.log('支出原始数据:', this.originalData);
      }).catch(error => {
        console.error("请求失败:", error);
      });
    },
    getAllInfo2() {
      this.$axios(`/api/incomes`).then(res => {
        this.originalData2 = res.data.data;
        this.originalData2.sort((a, b) => new Date(a.datetime) - new Date(b.datetime));
        this.printform();
        console.log('收入原始数据:', this.originalData2);
      }).catch(error => {
        console.error("请求失败:", error);
      });
    },
    filteredRecords1() {
      let searchform = this.originalData;
      if (this.form.Name) {
        searchform = searchform.filter(q => q.payName.includes(this.form.Name));
      }
      console.log('最终支出筛选结果:', searchform);
      return searchform;
    },
    filteredRecords2() {
      let searchform2 = this.originalData2;
      if (this.form.Name) {
        searchform2 = searchform2.filter(q => q.incomeName.includes(this.form.Name));
      }
      console.log('最终收入筛选结果:', searchform2);
      return searchform2;
    },
    printform() {
      this.renderChart();
      this.renderChart2();
      this.filteredPayResults = this.filteredRecords1();
      this.filteredIncomeResults = this.filteredRecords2();
      this.calculateFinancials();
    },
    resetForm() {
      this.form.Name = '';
      this.filteredPayResults = [];
      this.filteredIncomeResults = [];
      this.totalIncome = 0;
      this.totalExpense = 0;
      this.maxLabelIncomePercentage = 0;
      this.maxLabelExpensePercentage = 0;
      this.incomeChangeStatus = '';
      this.expenseChangeStatus = '';
      this.averageIncome = 0;
      this.averageExpense = 0;
      this.maxLabelIncomeChangeStatus = '';
      this.maxLabelExpenseChangeStatus = '';
      this.averageIncomeChangeStatus = '';
      this.averageExpenseChangeStatus = '';
      this.lastMonthTotalIncome = 0;
      this.lastMonthTotalExpense = 0;
      this.lastMonthAverageIncome = 0;
      this.lastMonthAverageExpense = 0;
      this.incomeChangePercentage = 0;
      this.expenseChangePercentage = 0;
      this.maxLabelIncomeChangePercentage = 0;
      this.maxLabelExpenseChangePercentage = 0;
      this.averageIncomeChangePercentage = 0;
      this.averageExpenseChangePercentage = 0;
      if (this.chart) {
        this.chart.clear();
        this.chart = null;
        this.printform();
      }
      if (this.chart2) {
        this.chart2.clear();
        this.chart2 = null;
        this.printform();
      }
    },
    renderChart() {
      const chartContainer = this.$refs.chartContainer;
      if (!chartContainer) return;
      this.chart = echarts.init(chartContainer);
      const filteredData = this.filteredRecords2();
      const xAxisData = filteredData.map(item => item.datetime);
      const seriesData = filteredData.map(item => item.money);
      const tooltipLabels = filteredData.map(item => {
        return `支付方式: ${item.incomeway}<br/>标签: ${item.label}<br/>备注: ${item.notes}`;
      });

      function formatDate(dateStr) {
        const date = new Date(dateStr);
        const options = { month: '2-digit', day: '2-digit' };
        return date.toLocaleDateString(undefined, options);
      }

      const option = {
        title: {
          text: '收入',
          left: 'left',
          top: 'top',
          textStyle: {
            fontSize: 18,
            fontWeight: 'bold'
          }
        },
        tooltip: {
          trigger: 'axis',
          formatter: (params) => {
            const [param] = params;
            return `
          时间: ${formatDate(param.name)}<br/>
          金额: ${param.value}<br/>
          ${tooltipLabels[param.dataIndex]}
        `;
          }
        },
        xAxis: {
          type: 'category',
          data: xAxisData.map(formatDate),
          axisLabel: {
            rotate: 45
          }
        },
        yAxis: {
          type: 'value',
          axisLine: { show: false },
          axisTick: { show: false },
          splitLine: { show: true },
          axisLabel: { show: true }
        },
        series: [{
          data: seriesData,
          type: 'line',
          smooth: true,
          lineStyle: { color: '#5470c6' },
          itemStyle: { color: '#91cc75' }
        }]
      };

      this.chart.setOption(option);
    },
    renderChart2() {
      const chartContainer2 = this.$refs.chartContainer2;
      if (!chartContainer2) return;
      this.chart2 = echarts.init(chartContainer2);
      const filteredData = this.filteredRecords1();
      const xAxisData = filteredData.map(item => item.datetime);
      const seriesData = filteredData.map(item => item.money);
      const tooltipLabels = filteredData.map(item => {
        return `支付方式: ${item.payway}<br/>一级目录: ${item.label1}<br/>二级目录: ${item.label2}<br/>备注: ${item.notes}<br/>消费对象: ${item.toperson}<br/>消费地点: ${item.place}`;
      });

      function formatDate(dateStr) {
        const date = new Date(dateStr);
        const options = { month: '2-digit', day: '2-digit' };
        return date.toLocaleDateString(undefined, options);
      }

      const option = {
        title: {
          text: '支出',
          left: 'left',
          top: 'top',
          textStyle: {
            fontSize: 18,
            fontWeight: 'bold',
          },
        },
        tooltip: {
          trigger: 'axis',
          formatter: (params) => {
            const [param] = params;
            return `
          时间: ${formatDate(param.name)}<br/>
          金额: ${param.value}<br/>
          ${tooltipLabels[param.dataIndex]}
        `;
          }
        },
        xAxis: {
          type: 'category',
          data: xAxisData.map(formatDate),
          position: 'top',
          axisLabel: {
            rotate: 45
          },
          inverse: false
        },
        yAxis: {
          type: 'value',
          inverse: true,
          axisLine: { show: false },
          axisTick: { show: false },
          splitLine: { show: true },
          axisLabel: { show: true }
        },
        series: [{
          data: seriesData,
          type: 'line',
          smooth: true,
          lineStyle: { color: '#91cc75' },
          itemStyle: { color: '#5470c6' }
        }]
      };

      this.chart2.setOption(option);
    },
    calculateFinancials() {
      const currentMonth = new Date().getMonth();
      const currentYear = new Date().getFullYear();

      let totalIncomeThisMonth = 0;
      let totalExpenseThisMonth = 0;
      let incomeLabels = {};
      let expenseLabels = {};

      const filteredIncomeThisMonth = this.filteredIncomeResults.filter(item => {
        const month = new Date(item.datetime).getMonth();
        const year = new Date(item.datetime).getFullYear();
        return month === currentMonth && year === currentYear;
      });

      const filteredExpenseThisMonth = this.filteredPayResults.filter(item => {
        const month = new Date(item.datetime).getMonth();
        const year = new Date(item.datetime).getFullYear();
        return month === currentMonth && year === currentYear;
      });

      filteredIncomeThisMonth.forEach(item => {
        totalIncomeThisMonth += item.money;
        if (incomeLabels[item.label]) {
          incomeLabels[item.label] += item.money;
        } else {
          incomeLabels[item.label] = item.money;
        }
      });

      filteredExpenseThisMonth.forEach(item => {
        totalExpenseThisMonth += item.money;
        if (expenseLabels[item.label1]) {
          expenseLabels[item.label1] += item.money;
        } else {
          expenseLabels[item.label1] = item.money;
        }
      });

      const maxIncomeLabel = Object.keys(incomeLabels).reduce((a, b) => incomeLabels[a] > incomeLabels[b] ? a : b, ' ');
      const maxExpenseLabel = Object.keys(expenseLabels).reduce((a, b) => expenseLabels[a] > expenseLabels[b] ? a : b, ' ');

      this.totalIncome = totalIncomeThisMonth.toFixed(2);
      this.totalExpense = totalExpenseThisMonth.toFixed(2);
      this.maxLabelIncomePercentage = (incomeLabels[maxIncomeLabel] / totalIncomeThisMonth * 100).toFixed(2);
      this.maxLabelExpensePercentage = (expenseLabels[maxExpenseLabel] / totalExpenseThisMonth * 100).toFixed(2);

      // 更新前端显示的label和label1名称
      this.maxLabelIncomeName = maxIncomeLabel.trim();
      this.maxLabelExpenseName = maxExpenseLabel.trim();

      // 计算平均收入和支出
      this.averageIncome = (filteredIncomeThisMonth.length > 0) ? (totalIncomeThisMonth / filteredIncomeThisMonth.length).toFixed(2) : '0.00';
      this.averageExpense = (filteredExpenseThisMonth.length > 0) ? (totalExpenseThisMonth / filteredExpenseThisMonth.length).toFixed(2) : '0.00';

      // 计算本月与上月花费相差的值
      this.calculateLastMonthData(currentMonth, currentYear, filteredIncomeThisMonth, filteredExpenseThisMonth);
    },
    calculateLastMonthData(currentMonth, currentYear, filteredIncomeThisMonth, filteredExpenseThisMonth) {
      const lastMonth = currentMonth === 0 ? 11 : currentMonth - 1;
      const lastYear = currentMonth === 0 ? currentYear - 1 : currentYear;

      let totalIncomeLastMonth = 0;
      let totalExpenseLastMonth = 0;
      let incomeLabelsLastMonth = {};
      let expenseLabelsLastMonth = {};

      const filteredIncomeLastMonth = this.filteredIncomeResults.filter(item => {
        const month = new Date(item.datetime).getMonth();
        const year = new Date(item.datetime).getFullYear();
        return month === lastMonth && year === lastYear;
      });

      const filteredExpenseLastMonth = this.filteredPayResults.filter(item => {
        const month = new Date(item.datetime).getMonth();
        const year = new Date(item.datetime).getFullYear();
        return month === lastMonth && year === lastYear;
      });

      filteredIncomeLastMonth.forEach(item => {
        totalIncomeLastMonth += item.money;
        if (incomeLabelsLastMonth[item.label]) {
          incomeLabelsLastMonth[item.label] += item.money;
        } else {
          incomeLabelsLastMonth[item.label] = item.money;
        }
      });

      filteredExpenseLastMonth.forEach(item => {
        totalExpenseLastMonth += item.money;
        if (expenseLabelsLastMonth[item.label1]) {
          expenseLabelsLastMonth[item.label1] += item.money;
        } else {
          expenseLabelsLastMonth[item.label1] = item.money;
        }
      });

      const maxIncomeLabelLastMonth = Object.keys(incomeLabelsLastMonth).reduce((a, b) => incomeLabelsLastMonth[a] > incomeLabelsLastMonth[b] ? a : b, ' ');
      const maxExpenseLabelLastMonth = Object.keys(expenseLabelsLastMonth).reduce((a, b) => expenseLabelsLastMonth[a] > expenseLabelsLastMonth[b] ? a : b, ' ');

      this.lastMonthTotalIncome = totalIncomeLastMonth.toFixed(2);
      this.lastMonthTotalExpense = totalExpenseLastMonth.toFixed(2);
      this.lastMonthAverageIncome = (filteredIncomeLastMonth.length > 0) ? (totalIncomeLastMonth / filteredIncomeLastMonth.length).toFixed(2) : '0.00';
      this.lastMonthAverageExpense = (filteredExpenseLastMonth.length > 0) ? (totalExpenseLastMonth / filteredExpenseLastMonth.length).toFixed(2) : '0.00';

      this.calculatePercentageChanges();
    },
    calculatePercentageChanges() {
      // 计算总收入和总支出的月度变化百分比
      let incomeChange = ((this.totalIncome - parseFloat(this.lastMonthTotalIncome)) / parseFloat(this.lastMonthTotalIncome) * 100).toFixed(2);
      let expenseChange = ((this.totalExpense - parseFloat(this.lastMonthTotalExpense)) / parseFloat(this.lastMonthTotalExpense) * 100).toFixed(2);

      // 确定总收入和总支出的变化状态
      this.incomeChangeStatus = incomeChange === '0.00' ? '没有变化' : incomeChange > 0 ? '增加' : '减少';
      this.expenseChangeStatus = expenseChange === '0.00' ? '没有变化' : expenseChange > 0 ? '增加' : '减少';

      // 转换为绝对值
      this.incomeChangePercentage = incomeChange === '0.00' ? '没有变化' : Math.abs(incomeChange);
      this.expenseChangePercentage = expenseChange === '0.00' ? '没有变化' : Math.abs(expenseChange);

      // 计算平均收入和支出的月度变化百分比
      let averageIncomeChange = ((this.averageIncome - parseFloat(this.lastMonthAverageIncome)) / parseFloat(this.lastMonthAverageIncome) * 100).toFixed(2);
      let averageExpenseChange = ((this.averageExpense - parseFloat(this.lastMonthAverageExpense)) / parseFloat(this.lastMonthAverageExpense) * 100).toFixed(2);

      // 确定平均收入和支出的变化状态
      this.averageIncomeChangeStatus = averageIncomeChange === '0.00' ? '没有变化' : averageIncomeChange > 0 ? '增加' : '减少';
      this.averageExpenseChangeStatus = averageExpenseChange === '0.00' ? '没有变化' : averageExpenseChange > 0 ? '增加' : '减少';

      // 转换为绝对值
      this.averageIncomeChangePercentage = averageIncomeChange === '0.00' ? '没有变化' : Math.abs(averageIncomeChange);
      this.averageExpenseChangePercentage = averageExpenseChange === '0.00' ? '没有变化' : Math.abs(averageExpenseChange);
    }
  },
};
</script>

<style lang="less" scoped>
.part {
  height: 80vh;
  display: flex;
  flex-direction: column;
}
.top {
  display: flex;
  justify-content: end; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  margin-bottom: 10px;
  margin-right: 20px;
  flex: 0.5;
}
.bottom{
  display: flex;
  flex-direction: row;
  flex: 9.5;
  margin: 10px;
  padding: 10px;
  background-color: #ececed; // 背景颜色
  border-radius: 8px; // 圆角
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); // 阴影效果
}
.chart{
  flex: 6;
  margin-right: 10px;
  padding: 20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  background-color: white;
  border-radius: 8px; // 圆角
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); // 阴影效果
}
.chartside{
  flex: 4;
  //margin-right: 10px;
  display: flex;
  flex-direction: column;
  //justify-content: center;
  background-color: white;
  border-radius: 8px; // 圆角
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); // 阴影效果
  padding: 20px;
}
.chart-container1{
  flex: 5;
  margin: 10px;
  display: flex;
  justify-content: center;
}
.chart-container2{
  flex: 5;
  margin: 10px;
  display: flex;
  justify-content: center;
}
.spans{
  color: #0f9876;
  font-weight: bold;
  font-size: 25px;
  margin: 20px 20px 0 20px;
}
.income-text {
  //color: white;
  font-size: 20px;
  margin: 20px;
  padding-top: 10px;
}

.income-text2 {
  color: #a7a7a8;
  margin-left: 20px;
}
.income-text3{
  color: #0f9876;
  font-size: 20px;
  font-weight: bold;
}
.income-text4{
  color: #0f9876;
  //font-size: 20px;
  //font-weight: bold;
}
.income-text5{
  color: #a7a7a8;
  margin-left: 20px;
  margin-bottom: 10px;
}
.income-text6{
  color: #a7a7a8;
  margin-left: 20px;
  margin-top: 10px;
  margin-bottom: 10px;
}
.income-yuan{
  font-size: 25px;
  font-weight: bold;
  //margin: 20px;
}
.text {
  margin-right: 5px;
  width: 150px;
}
.legend {
  margin-bottom: 10px;
  font-size: 14px;
}
.titletype{
  margin: 20px;
}
.texttype{
  //margin-left: 20px;
  margin-top: 10px;
}
</style>
