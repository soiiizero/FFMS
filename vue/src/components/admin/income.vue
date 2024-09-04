<!--收入管理页面-->
<template>
  <div class="all">
    <div class="top">
      <el-input
        v-if="user.role == 0"
        v-model="searchincomeName"
        placeholder="姓名"
        @input="searchforms"
        class="text"
      ></el-input>
      <el-select v-model="input.incomeway" placeholder="收入方式" @change="searchforms" class="text">
        <el-option v-for="item in incomeways" :key="item.value" :label="item.label" :value="item.value"></el-option>
      </el-select>
      <el-select v-model="labelvalue" filterable default-first-option @change="handleLabelChange" placeholder="收入来源" class="text">
        <el-option v-for="item in labels" :key="item.value" :label="item.label" :value="item.value"></el-option>
      </el-select>
      <el-input v-model="searchnotes" placeholder="备注" @input="searchforms" class="text"></el-input>
      <el-date-picker v-model="input.startTime" @change="searchforms" type="datetime" placeholder="起始时间" style="width: 195px;" class="text"></el-date-picker>
      <el-date-picker v-model="input.endTime" @change="searchforms" type="datetime" placeholder="结束时间" style="width: 195px;" class="text"></el-date-picker>
      <el-button type="primary" @click="resetForm" style="background-color: #a7a7a8;border-color: #a7a7a8">重置</el-button>
    </div>
    <el-table :data="pagination.records" border>
      <el-table-column fixed="left" prop="incomeId" label="序号" width="50"></el-table-column>
      <el-table-column prop="incomeName" label="姓名" width="150"></el-table-column>
      <el-table-column prop="money" label="金额" width="200"></el-table-column>
      <el-table-column prop="incomeway" label="收入方式" width="100"></el-table-column>
      <el-table-column label="收入来源" width="auto">
        <template slot-scope="scope">
          <el-tag size="medium" type="success">{{ scope.row.label }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="datetime" label="时间" width="auto">
        <template slot-scope="scope">
          {{ new Date(scope.row.datetime).toLocaleString('zh-CN', { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit', second: '2-digit' }) }}
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="auto">
        <template slot-scope="scope">
          <el-button @click="checkGrade(scope.row.incomeId)" type="primary" size="small" style="background-color: #0f9876;border-color: #0f9876">编辑</el-button>
          <el-button @click="deleteById(scope.row.incomeId)" type="danger" size="small" style="background-color: #a7a7a8;border-color: #a7a7a8">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pagination.current" :page-sizes="[3, 6, 10]" :page-size="pagination.size" layout="total, sizes, prev, pager, next, jumper" :total="filteredTotal" class="page">
    </el-pagination>
    <!-- 编辑对话框-->
    <el-dialog title="编辑收入信息" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
      <section class="update">
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="ID">
            <el-input style="width: 200px;" v-model="form.incomeId"></el-input>
          </el-form-item>
          <el-form-item label="姓名">
            <el-input style="width: 200px;" v-model="form.incomeName"></el-input>
          </el-form-item>
          <el-form-item label="金额">
            <el-input style="width: 200px;" v-model="form.money"></el-input>
          </el-form-item>
          <el-form-item label="收入方式">
            <el-select v-model="form.incomeway" placeholder="收入方式">
              <el-option v-for="item in incomeways" :key="item.value" :label="item.label" :value="item.value" style="width: 200px;"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="收入来源">
            <el-input style="width: 200px;" v-model="form.label"></el-input>
          </el-form-item>
          <el-form-item label="时间">
            <el-date-picker v-model="form.datetime" type="datetime" style="width: 200px;"></el-date-picker>
          </el-form-item>
          <el-form-item label="备注">
            <el-input style="width: 200px;" v-model="form.notes"></el-input>
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
        searchincomeName: null,
        role: null
      },
      incomeways: [
        { value: '微信', label: '微信' },
        { value: '支付宝', label: '支付宝' },
        { value: '银联', label: '银联' },
        { value: '现金', label: '现金' },
        { value: '其他', label: '其他' }
      ],
      labels: [],
      labelvalue: null,
      pagination: {
        current: 1,
        total: 0,
        size: 6,
        records: []
      },
      dialogVisible: false,
      form: {},
      forms: [],
      input: {
        incomeway: '',
        startTime: null,
        endTime: null
      },
      searchincomeName: '',
      searchlabel: '',
      searchnotes: '',
    };
  },
  created() {
    this.getUserInfo();
    this.getAllIncome();
  },
  mounted() {
    this.loadData();
  },
  computed: {
    filteredRecords() {
      let searchform = this.forms;
      if (this.input.incomeway) {
        searchform = searchform.filter(q => q.incomeway === this.input.incomeway);
      }
      if (this.input.startTime) {
        searchform = searchform.filter(q => new Date(q.datetime) >= new Date(this.input.startTime));
      }
      if (this.input.endTime) {
        searchform = searchform.filter(q => new Date(q.datetime) <= new Date(this.input.endTime));
      }
      if (this.user.role == 1) {
        this.searchincomeName = this.user.searchincomeName;
      }
      if (this.searchincomeName) {
        searchform = searchform.filter(q => q.incomeName.includes(this.searchincomeName));
      }
      if (this.searchlabel) {
        searchform = searchform.filter(q => q.label === this.searchlabel);
      }
      if (this.searchnotes) {
        searchform = searchform.filter(q => q.notes.includes(this.searchnotes));
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
      this.user.searchincomeName = userName;
      this.user.role = role;
    },
    getAllIncome() {
      this.$axios(`/api/incomes`).then(res => {
        const labels = res.data.data.map(item => ({ value: item.label, label: item.label }));
        const uniqueLabels = [...new Set(labels.map(item => item.value))];
        this.labels = uniqueLabels.map(item => ({ value: item, label: item }));
        this.forms = res.data.data;
        this.loadData();
      }).catch(error => {
        console.error("请求失败:", error);
      });
    },
    handleLabelChange(value) {
      if (value && this.labels.find(item => item.value === value)) {
        this.searchlabel = value;
      } else {
        this.searchlabel = '';
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
      this.input = { incomeway: '', startTime: null, endTime: null };
      this.searchincomeName = '';
      this.searchlabel = '';
      this.searchnotes = '';
      this.labelvalue = '';
      this.searchforms();
    },
    searchforms() {
      this.updatePaginationRecords();
    },
    checkGrade(incomeId) {
      this.dialogVisible = true;
      this.$axios(`/api/income/${incomeId}`).then(res => {
        this.form = res.data.data;
      });
    },
    deleteById(incomeId) {
      this.$confirm("确定删除当前支出信息吗？删除后无法恢复", "Warning", {
        confirmButtonText: '确定删除',
        cancelButtonText: '算了,留着吧',
        type: 'danger'
      }).then(() => {
        this.$axios({
          url: `/api/income/${incomeId}`,
          method: 'delete',
        }).then(res => {
          this.getAllIncome();
        });
      }).catch(() => { });
    },
    submit() {
      this.dialogVisible = false;
      this.$axios({
        url: '/api/income',
        method: 'put',
        data: { ...this.form }
      }).then(res => {
        console.log(res);
        if (res.data.code === 200) {
          this.$message({ message: '更新成功', type: 'success' });
          this.getAllIncome();
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

