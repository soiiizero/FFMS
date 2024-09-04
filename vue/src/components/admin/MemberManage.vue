<!--家庭成员管理页面-->
<template>
  <div class="all">
    <div class="tablehead">
      <span class="custom-text-style">家主</span>
      <el-divider></el-divider>
    </div>
    <el-table :data="records" border>
      <el-table-column fixed="left" prop="adminId" label="成员编号" width="150"></el-table-column>
      <el-table-column prop="adminName" label="姓名" width="100"></el-table-column>
      <el-table-column prop="tel" label="电话" width="150"></el-table-column>
      <el-table-column prop="email" label="邮箱" width="auto"></el-table-column>
      <el-table-column prop="pwd" label="账号密码" width="100"></el-table-column>
      <el-table-column prop="cardId" label="身份证号" width="180"></el-table-column>
      <el-table-column prop="sex" label="性别" width="50"></el-table-column>
      <el-table-column prop="career" label="职业" width="80"></el-table-column>
      <el-table-column fixed="right" label="操作" width="auto">
        <template slot-scope="scope">
          <el-button @click="checkGradef(scope.row.adminId)" type="primary" size="small" style="background-color: #0f9876;border-color: #0f9876">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      title="编辑家主信息"
      :visible.sync="dialogVisiblef"
      width="30%"
      :before-close="handleClosef">
      <section class="update">
        <el-form ref="formf" :model="formf" label-width="80px">
          <el-form-item label="成员编号">
            <el-input v-model="formf.adminId"></el-input>
          </el-form-item>
          <el-form-item label="姓名">
            <el-input v-model="formf.adminName"></el-input>
          </el-form-item>
          <el-form-item label="电话">
            <el-input v-model="formf.tel"></el-input>
          </el-form-item>
          <el-form-item label="邮箱">
            <el-input v-model="formf.email"></el-input>
          </el-form-item>
          <el-form-item label="账号密码">
            <el-input v-model="formf.pwd"></el-input>
          </el-form-item>
          <el-form-item label="身份证号">
            <el-input v-model="formf.cardId"></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-input v-model="formf.sex"></el-input>
          </el-form-item>
          <el-form-item label="职业">
            <el-input v-model="formf.career"></el-input>
          </el-form-item>
        </el-form>
      </section>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisiblef = false">取 消</el-button>
        <el-button type="primary" @click="submitf()">确 定</el-button>
      </span>
    </el-dialog>
    <div class="tablehead">
      <span class="custom-text-style">家庭成员</span>
      <el-divider></el-divider>
    </div>
    <el-table :data="pagination.records" border>
      <el-table-column fixed="left" prop="memberId" label="成员编号" width="150"></el-table-column>
      <el-table-column prop="memberName" label="姓名" width="100"></el-table-column>
      <el-table-column prop="tel" label="电话" width="150"></el-table-column>
      <el-table-column prop="email" label="邮箱" width="auto"></el-table-column>
      <el-table-column prop="pwd" label="账号密码" width="100"></el-table-column>
      <el-table-column prop="cardId" label="身份证号" width="180"></el-table-column>
      <el-table-column prop="sex" label="性别" width="50"></el-table-column>
      <el-table-column prop="career" label="职业" width="80"></el-table-column>
      <el-table-column fixed="right" label="操作" width="auto">
        <template slot-scope="scope">
          <el-button @click="checkGrade(scope.row.memberId)" type="primary" size="small" style="background-color: #0f9876;border-color: #0f9876">编辑</el-button>
          <el-button @click="deleteById(scope.row.memberId)" type="danger" size="small" style="background-color: #a7a7a8;border-color: #a7a7a8">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pagination.current"
      :page-sizes="[4, 6, 10]"
      :page-size="pagination.size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pagination.total"
      class="page">
    </el-pagination>
    <!-- 编辑对话框-->
    <el-dialog
      title="编辑家庭成员信息"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <section class="update">
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="成员编号">
            <el-input v-model="form.memberId"></el-input>
          </el-form-item>
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
        </el-form>
      </section>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      records: [],
      pagination: {
        //分页后的考试信息
        current: 1, //当前页
        total: null, //记录条数
        size: 4, //每页条数
      },
      dialogVisible: false, //对话框
      dialogVisiblef: false, //对话框
      form: {}, //保存点击以后当前家庭成员信息
      formf: {},//保存点击以后当前管理员信息
    };
  },
  created() {
    this.getMemberInfo();
    this.getAdminInfo();
  },
  methods: {
    getAdminInfo() {
      //分页查询所有管理员信息
      this.$axios(`/api/admins`).then(res => {
        this.records = res.data.data;
      }).catch(error => {});
    },
    checkGradef(adminId) { //修改管理员信息
      this.dialogVisiblef = true
      this.$axios(`/api/admin/${adminId}`).then(res => {
        this.formf = res.data.data
      })
    },
    deleteByIdf(adminId) { //删除当前管理员
      this.$confirm("确定删除当前管理员吗？删除后无法恢复","Warning",{
        confirmButtonText: '确定删除',
        cancelButtonText: '算了,留着吧',
        type: 'danger'
      }).then(()=> { //确认删除
        this.$axios({
          url: `/api/admin/${adminId}`,
          method: 'delete',
        }).then(res => {
          this.getAdminInfo()
        })
      }).catch(() => {

      })
    },
    submitf() { //提交更改
      this.dialogVisiblef = false
      this.$axios({
        url: '/api/admin',
        method: 'put',
        data: {
          ...this.formf
        }
      }).then(res => {
        console.log(res)
        if(res.data.code ==200) {
          this.$message({
            message: '更新成功',
            type: 'success'
          })
        }
        this.getAdminInfo()
      })
    },
    handleClosef(done) { //关闭提醒
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        }).catch(_ => {});
    },
    getMemberInfo() {
      //分页查询所有家庭成员信息
      this.$axios(`/api/members/${this.pagination.current}/${this.pagination.size}`).then(res => {
        this.pagination = res.data.data;
      }).catch(error => {});
    },
    //改变当前记录条数
    handleSizeChange(val) {
      this.pagination.size = val;
      this.getMemberInfo();
    },
    //改变当前页码，重新发送请求
    handleCurrentChange(val) {
      this.pagination.current = val;
      this.getMemberInfo();
    },
    checkGrade(memberId) { //修改家庭成员信息
      this.dialogVisible = true
      this.$axios(`/api/member/${memberId}`).then(res => {
        this.form = res.data.data
      })
    },
    deleteById(memberId) { //删除当前家庭成员
      this.$confirm("确定删除当前家庭成员吗？删除后无法恢复","Warning",{
        confirmButtonText: '确定删除',
        cancelButtonText: '算了,留着吧',
        type: 'danger'
      }).then(()=> { //确认删除
        this.$axios({
          url: `/api/member/${memberId}`,
          method: 'delete',
        }).then(res => {
          this.getMemberInfo()
        })
      }).catch(() => {

      })
    },
    submit() { //提交更改
      this.dialogVisible = false
      this.$axios({
        url: '/api/member',
        method: 'put',
        data: {
          ...this.form
        }
      }).then(res => {
        console.log(res)
        if(res.data.code ==200) {
          this.$message({
            message: '更新成功',
            type: 'success'
          })
        }
        this.getMemberInfo()
      })
    },
    handleClose(done) { //关闭提醒
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        }).catch(_ => {});
    },
  }
};
</script>

<style lang="less" scoped>
.all {
  padding: 0px 40px;
  .page {
    margin-top: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .edit {
    margin-left: 20px;
  }
  .el-table tr {
    background-color: #dd5862 !important;
  }
}
.tablehead{
  margin-top: 25px;
}
.custom-text-style {
  color: rgb(15, 152, 118);
  font-weight: bold;
}
.el-table .warning-row {
  background: #000 !important;
}

.el-table .success-row {
  background: #dd5862;
}
</style>
