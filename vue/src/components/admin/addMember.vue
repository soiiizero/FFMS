<!-- 添加家庭成员 -->
<template>
  <section class="add">
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="姓名">
        <el-input v-model="form.memberName"></el-input>
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="form.tel"></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="form.email"></el-input>
      </el-form-item>
      <el-form-item label="账号密码">
        <el-input v-model="form.pwd"></el-input>
      </el-form-item>
      <el-form-item label="身份证号">
        <el-input v-model="form.cardId"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-input v-model="form.sex"></el-input>
      </el-form-item>
      <el-form-item label="职业">
        <el-input v-model="form.career"></el-input>
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
      form: { //表单数据初始化
        memberId: null,
        memberName: '',
        tel: '',
        email: '',
        pwd: '',
        cardId: '',
        sex:'',
        career: '',
        role: 1
      }
    };
  },
  methods: {
    onSubmit() { //数据提交
      this.$axios({
        url: '/api/member',
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
          this.$router.push({path: '/MemberManage'})
        }
      })
    },
    clearForm() {
      // 清空表单数据
      this.form = {
        memberId: null,
        memberName: '',
        tel: '',
        email: '',
        pwd: '',
        cardId: '',
        sex: '',
        career: '',
        role: 1
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

