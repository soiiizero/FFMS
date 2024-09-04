<!-- 添加支出 -->
<template>
  <section class="add">
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item v-if="user.role == 0" label="姓名">
        <el-input v-model="form.payName"></el-input>
      </el-form-item>
      <el-form-item v-if="user.role == 1" label="姓名">
        <el-input
          :placeholder="user.payName"
          v-model="input"
          :disabled="true">
        </el-input>
      </el-form-item>
      <el-form-item label="金额">
        <el-input v-model="form.money"></el-input>
      </el-form-item>
      <el-form-item label="支付方式">
        <el-select v-model="form.payway">
          <el-option v-for="item in payways" :key="item.value" :label="item.label" :value="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="一级目录">
        <el-select
          v-model="label1value"
          multiple
          filterable
          allow-create
          default-first-option
          placeholder="一级目录"
          @change="handleLabel1Change">
          <el-option
            v-for="item in label1s"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="二级目录">
        <el-select
          v-model="label2value"
          multiple
          filterable
          allow-create
          default-first-option
          placeholder="二级目录"
          @change="handleLabel2Change">
          <el-option
            v-for="item in label2s"
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
      <el-form-item label="消费对象">
        <el-input v-model="form.toperson"></el-input>
      </el-form-item>

      <el-form-item label="消费地点">
        <el-input v-model="form.place"></el-input>
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
  data() {
    return {
      user: { //用户信息
        payName: null,
        role: null
      },
      payways: [
        { value: '微信', label: '微信' },
        { value: '支付宝', label: '支付宝' },
        { value: '银联', label: '银联' },
        { value: '现金', label: '现金' },
        { value: '其他', label: '其他' }
      ],
      form: {
        payName: '',
        money: null,
        payway: '',
        label1: '',
        label2: '',
        notes: '',
        datetime: null,
        toperson: '',
        place: ''
      },
      label1s: [],
      label2s: [], // 新增二级目录选项数组
      label1value: null,
      label2value: null, // 新增二级目录选中值绑定
    };
  },
  created() {
    this.getUserInfo();
  },
  mounted() {
    this.getAllPay();
  },
  methods: {
    getUserInfo() { //获取用户信息
      let userName = this.$cookies.get("cname")
      let role = this.$cookies.get("role")
      this.user.payName = userName
      this.user.role = role
      if (this.user.role == 1) {
        this.form.payName = this.user.payName;
      }
    },
    getAllPay() {
      this.$axios(`/api/pays`).then(res => {
        const label1s = res.data.data.map(item => ({
          value: item.label1,
          label: item.label1
        }));
        const uniqueLabel1s = Array.from(new Set(label1s.map(item => item.value)));
        this.label1s = uniqueLabel1s.map(item => ({
          value: item,
          label: item
        }));
        const label2s = res.data.data.map(item => ({
          value: item.label2,
          label: item.label2
        }));
        const uniqueLabel2s = Array.from(new Set(label2s.map(item => item.value)));
        this.label2s = uniqueLabel2s.map(item => ({
          value: item,
          label: item
        }));
        console.log(this.label2s);
      }).catch(error => {
        console.error("请求失败:", error);
      });
    },
    handleLabel1Change(value) {
      if (value) {
        this.form.label1 = value[0]; // 直接使用选中的值更新 form.label1
      } else {
        this.form.label1 = '';
      }
      console.log(this.form.label1);
    },
    handleLabel2Change(value) {
      if (value) {
        this.form.label2 = value[0]; // 直接使用选中的值更新 form.label2
      } else {
        this.form.label2 = '';
      }
      console.log(this.form.label2);
    },
    onSubmit() { //数据提交
      this.$axios({
        url: '/api/pay',
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
          this.$router.push({path: '/pay'})
        }
      })
    },
    clearForm() {
      // 清空表单数据
      this.form = { //表单数据初始化
        payName: '',
        money: null,
        payway: '',
        label1: '',
        label2: '',
        notes: '',
        datetime: null,
        toperson: '',
        place: ''
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
