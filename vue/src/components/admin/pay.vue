<!--支出管理页面-->
<template>
  <div class="all">
    <div class="top">
      <el-input
        v-if="user.role == 0"
        v-model="searchpayName"
        placeholder="姓名"
        @input="searchforms"
        class="text"
      ></el-input>
      <el-select v-model="input.payway" placeholder="支付方式" @change="searchforms" class="text">
        <el-option v-for="item in payways" :key="item.value" :label="item.label" :value="item.value"></el-option>
      </el-select>
      <el-select v-model="label1value" filterable default-first-option @change="handleLabel1Change" placeholder="一级目录" class="text">
        <el-option v-for="item in label1s" :key="item.value" :label="item.label" :value="item.value"></el-option>
      </el-select>
      <el-select v-model="label2value" filterable default-first-option @change="handleLabel2Change" placeholder="二级目录" class="text">
        <el-option v-for="item in label2s" :key="item.value" :label="item.label" :value="item.value"></el-option>
      </el-select>
      <el-input v-model="searchtoperson" placeholder="消费对象" @input="searchforms" class="text"></el-input>
      <el-input v-model="searchnotes" placeholder="备注" @input="searchforms" class="text"></el-input>
      <el-input v-model="searchplace" placeholder="消费地点" @input="searchforms" class="text"></el-input>
      <el-date-picker v-model="input.startTime" @change="searchforms" type="datetime" placeholder="起始时间" style="width: 195px;" class="text"></el-date-picker>
      <el-date-picker v-model="input.endTime" @change="searchforms" type="datetime" placeholder="结束时间" style="width: 195px;" class="text"></el-date-picker>
      <el-button type="primary" @click="resetForm" style="background-color: #a7a7a8;border-color: #a7a7a8">重置</el-button>
    </div>
    <el-table :data="pagination.records" border>
      <el-table-column fixed="left" prop="payId" label="序号" width="50"></el-table-column>
      <el-table-column prop="payName" label="姓名" width="150"></el-table-column>
      <el-table-column prop="money" label="金额" width="200"></el-table-column>
      <el-table-column prop="payway" label="支付方式" width="100"></el-table-column>
      <el-table-column label="类别" width="auto">
        <template slot-scope="scope">
          <el-tag size="medium" type="info">{{ scope.row.label1 }}</el-tag>
          <el-tag size="medium" type="success">{{ scope.row.label2 }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="datetime" label="时间" width="auto">
        <template slot-scope="scope">
          {{ new Date(scope.row.datetime).toLocaleString('zh-CN', { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit', second: '2-digit' }) }}
        </template>
      </el-table-column>
      <el-table-column prop="toperson" label="消费对象" width="150"></el-table-column>
      <el-table-column fixed="right" label="操作" width="auto">
        <template slot-scope="scope">
          <el-button @click="checkGrade(scope.row.payId)" type="primary" size="small" style="background-color: #0f9876;border-color: #0f9876">编辑</el-button>
          <el-button @click="deleteById(scope.row.payId)" type="danger" size="small" style="background-color: #a7a7a8;border-color: #a7a7a8">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pagination.current" :page-sizes="[3, 6, 10]" :page-size="pagination.size" layout="total, sizes, prev, pager, next, jumper" :total="filteredTotal" class="page">
    </el-pagination>
    <!-- 编辑对话框-->
    <el-dialog title="编辑支出信息" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
      <section class="update">
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="ID">
            <el-input style="width: 200px;" v-model="form.payId"></el-input>
          </el-form-item>
          <el-form-item label="姓名">
            <el-input style="width: 200px;" v-model="form.payName"></el-input>
          </el-form-item>
          <el-form-item label="金额">
            <el-input style="width: 200px;" v-model="form.money"></el-input>
          </el-form-item>
          <el-form-item label="支付方式">
            <el-select v-model="form.payway" placeholder="支付方式">
              <el-option v-for="item in payways" :key="item.value" :label="item.label" :value="item.value" style="width: 200px;"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="一级目录">
            <el-input style="width: 200px;" v-model="form.label1"></el-input>
          </el-form-item>
          <el-form-item label="二级目录">
            <el-input style="width: 200px;" v-model="form.label2"></el-input>
          </el-form-item>
          <el-form-item label="时间">
            <el-date-picker v-model="form.datetime" type="datetime" style="width: 200px;"></el-date-picker>
          </el-form-item>
          <el-form-item label="备注">
            <el-input style="width: 200px;" v-model="form.notes"></el-input>
          </el-form-item>
          <el-form-item label="消费对象">
            <el-input style="width: 200px;" v-model="form.toperson"></el-input>
          </el-form-item>
          <el-form-item label="消费地点">
            <el-input style="width: 200px;" v-model="form.place"></el-input>
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
      user: {
        searchpayName: null,
        role: null
      },
      payways: [
        { value: '微信', label: '微信' },
        { value: '支付宝', label: '支付宝' },
        { value: '银联', label: '银联' },
        { value: '现金', label: '现金' },
        { value: '其他', label: '其他' }
      ],
      label1s: [],
      label2s: [],
      label1value: null,
      label2value: null,
      pagination: {
        current: 1,
        total: 0,
        size: 3,
        records: []
      },
      dialogVisible: false,
      form: {},
      forms: [],
      input: {
        payway: '',
        startTime: null,
        endTime: null
      },
      searchpayName: '',
      searchlabel1: '',
      searchlabel2: '',
      searchtoperson: '',
      searchnotes: '',
      searchplace: ''
    };
  },
  created() {
    this.getUserInfo();
    this.getAllPay();
  },
  mounted() {
    this.loadData();
  },
  computed: {
    filteredRecords() {
      let searchform = this.forms;
      if (this.input.payway) {
        searchform = searchform.filter(q => q.payway === this.input.payway);
      }
      if (this.input.startTime) {
        searchform = searchform.filter(q => new Date(q.datetime) >= new Date(this.input.startTime));
      }
      if (this.input.endTime) {
        searchform = searchform.filter(q => new Date(q.datetime) <= new Date(this.input.endTime));
      }
      if (this.user.role == 1) {
        this.searchpayName = this.user.searchpayName;
      }
      if (this.searchpayName) {
        searchform = searchform.filter(q => q.payName.includes(this.searchpayName));
      }
      if (this.searchlabel1) {
        searchform = searchform.filter(q => q.label1 === this.searchlabel1);
      }
      if (this.searchlabel2) {
        searchform = searchform.filter(q => q.label2 === this.searchlabel2);
      }
      if (this.searchtoperson) {
        searchform = searchform.filter(q => q.toperson.includes(this.searchtoperson));
      }
      if (this.searchnotes) {
        searchform = searchform.filter(q => q.notes.includes(this.searchnotes));
      }
      if (this.searchplace) {
        searchform = searchform.filter(q => q.place.includes(this.searchplace));
      }
      return searchform;
    },
    filteredTotal() {
      return this.filteredRecords.length;
    }
  },
  methods: {
    getUserInfo() {
      let userName = this.$cookies.get("cname");
      let role = this.$cookies.get("role");
      this.user.searchpayName = userName;
      this.user.role = role;
    },
    getAllPay() {
      this.$axios(`/api/pays`).then(res => {
        const label1s = res.data.data.map(item => ({ value: item.label1, label: item.label1 }));
        const uniqueLabel1s = [...new Set(label1s.map(item => item.value))];
        this.label1s = uniqueLabel1s.map(item => ({ value: item, label: item }));

        const label2s = res.data.data.map(item => ({ value: item.label2, label: item.label2 }));
        const uniqueLabel2s = [...new Set(label2s.map(item => item.value))];
        this.label2s = uniqueLabel2s.map(item => ({ value: item, label: item }));

        this.forms = res.data.data;
        this.loadData();
      }).catch(error => {
        console.error("请求失败:", error);
      });
    },
    handleLabel1Change(value) {
      if (value && this.label1s.find(item => item.value === value)) {
        this.searchlabel1 = value;
      } else {
        this.searchlabel1 = '';
      }
      this.searchforms();
    },
    handleLabel2Change(value) {
      if (value && this.label2s.find(item => item.value === value)) {
        this.searchlabel2 = value;
      } else {
        this.searchlabel2 = '';
      }
      this.searchforms();
    },
    loadData() {
      this.pagination.total = this.filteredTotal;
      this.updatePaginationRecords();
    },
    updatePaginationRecords() {
      this.pagination.records = this.filteredRecords.slice(
        (this.pagination.current - 1) * this.pagination.size,
        this.pagination.current * this.pagination.size
      );
    },
    handleSizeChange(val) {
      this.pagination.size = val;
      this.updatePaginationRecords();
    },
    handleCurrentChange(val) {
      this.pagination.current = val;
      this.updatePaginationRecords();
    },
    resetForm() {
      this.input = { payway: '', startTime: null, endTime: null };
      this.searchpayName = '';
      this.searchlabel1 = '';
      this.searchlabel2 = '';
      this.searchtoperson = '';
      this.searchnotes = '';
      this.searchplace = '';
      this.label1value = '';
      this.label2value = '';
      this.searchforms();
    },
    searchforms() {
      this.updatePaginationRecords();
    },
    checkGrade(payId) {
      this.dialogVisible = true;
      this.$axios(`/api/pay/${payId}`).then(res => {
        this.form = res.data.data;
      });
    },
    deleteById(payId) {
      this.$confirm("确定删除当前支出信息吗？删除后无法恢复", "Warning", {
        confirmButtonText: '确定删除',
        cancelButtonText: '算了,留着吧',
        type: 'danger'
      }).then(() => {
        this.$axios({
          url: `/api/pay/${payId}`,
          method: 'delete',
        }).then(res => {
          this.getAllPay();
        });
      }).catch(() => { });
    },
    submit() {
      this.dialogVisible = false;
      this.$axios({
        url: '/api/pay',
        method: 'put',
        data: { ...this.form }
      }).then(res => {
        console.log(res);
        if (res.data.code === 200) {
          this.$message({ message: '更新成功', type: 'success' });
          this.getAllPay();
        }
      });
    },
    handleClose(done) {
      this.$confirm('确认关闭？').then(_ => {
        done();
      }).catch(_ => { });
    }
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
  .el-button {
    margin: 10px;
  }
  .el-select {
    width: 110px;
  }
  .el-input {
    width: 100px;
  }
  .edit {
    margin-left: 20px;
  }
  .el-table tr {
    background-color: #dd5862 !important;
  }
}
.top {
  display: flex;
  align-items: center;
  .text {
    margin-right: 5px;
  }
}
.el-table .warning-row {
  background: #000 !important;
}
.secinput {
  width: 500px;
}
.el-table .success-row {
  background: #dd5862;
}
</style>

