<!-- 添加支出 -->
<template>
  <section class="add">
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item v-if="user.role == 0" label="姓名">
        <el-input v-model="form.incomeName"></el-input>
      </el-form-item>
      <el-form-item v-if="user.role == 1" label="姓名">
        <el-input
          :placeholder="user.incomeName"
          v-model="input"
          :disabled="true">
        </el-input>
      </el-form-item>
      <el-form-item label="金额">
        <el-input v-model="form.money"></el-input>
      </el-form-item>
      <el-form-item label="收入方式">
        <el-select v-model="form.incomeway">
          <el-option v-for="item in incomeways" :key="item.value" :label="item.label" :value="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="收入来源">
        <el-select
          v-model="labelvalue"
          multiple
          filterable
          allow-create
          default-first-option
          placeholder="收入来源"
          @change="handleLabelChange">
          <el-option
            v-for="item in labels"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="form.notes"></el-input>
      </el-form-item>
      <el-form-item label="时间">
        <el-date-picker
          v-model="form.datetime"
          type="datetime">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit()" style="background-color: #0f9876;border-color: #0f9876">立即添加</el-button>
        <el-button type="primary" @click="clearForm()" style="background-color: #a7a7a8;border-color: #a7a7a8">清空</el-button>
      </el-form-item>
    </el-form>
  </section>
</template>

<script>
export default {
  name: "addIncome",
  data() {
    return {
      user: { //用户信息
        incomeName: null,
        role: null
      },
      incomeways: [
        { value: '微信', label: '微信' },
        { value: '支付宝', label: '支付宝' },
        { value: '银联', label: '银联' },
        { value: '现金', label: '现金' },
        { value: '其他', label: '其他' }
      ],
      form: {
        incomeName: '',
        money: null,
        incomeway: '',
        label: '',
        notes: '',
        datetime: null
      },
      labels: [],
      labelvalue: null,
    };
  },
  created() {
    this.getUserInfo();
  },
  mounted() {
    this.getAllIncome();
  },
  methods: {
    getUserInfo() { //获取用户信息
      let userName = this.$cookies.get("cname")
      let role = this.$cookies.get("role")
      this.user.incomeName = userName
      this.user.role = role
      if (this.user.role == 1) {
        this.form.incomeName = this.user.incomeName;
      }
    },
    getAllIncome() {
      this.$axios(`/api/incomes`).then(res => {
        const labels = res.data.data.map(item => ({
          value: item.label,
          label: item.label
        }));
        const uniqueLabels = Array.from(new Set(labels.map(item => item.value)));
        this.labels = uniqueLabels.map(item => ({
          value: item,
          label: item
        }));

      }).catch(error => {
        console.error("请求失败:", error);
      });
    },
    handleLabelChange(value) {
      if (value) {
        this.form.label = value[0]; // 直接使用选中的值更新 form.label
      } else {
        this.form.label = '';
      }
      console.log(this.form.label);
    },
    onSubmit() { //数据提交
      this.$axios({
        url: '/api/income',
        method: 'post',
        data: {
          ...this.form
        }
      }).then(res => {
        if(res.data.code == 200) {
          this.$message({
            message: '数据添加成功',
            type: 'success'
          })
          this.$router.push({path: '/income'})
        }
      })
    },
    clearForm() {
      // 清空表单数据
      this.form = { //表单数据初始化
        incomeName: '',
        money: null,
        incomeway: '',
        label: '',
        notes: '',
        datetime: null
      };
    },
  }
};
</script>

<style lang="less" scoped>
.add {
  padding: 0px 40px;
  width: 400px;
}
</style>
