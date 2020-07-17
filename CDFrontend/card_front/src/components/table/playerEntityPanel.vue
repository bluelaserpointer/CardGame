<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="search" placeholder="Title" style="width: 200px;" class="filter-card" />
      <!--      <el-select v-model="listQuery.importance" placeholder="Imp" clearable style="width: 90px" class="filter-item">-->
      <!--        <el-option v-for="item in importanceOptions" :key="item" :label="item" :value="item" />-->
      <!--      </el-select>-->
      <!--      <el-select v-model="listQuery.type" placeholder="Type" clearable class="filter-item" style="width: 130px">-->
      <!--        <el-option v-for="item in calendarTypeOptions" :key="item.key" :label="item.display_name+'('+item.key+')'" :value="item.key" />-->
      <!--      </el-select>-->
      <!--      <el-select v-model="listQuery.sort" style="width: 140px" class="filter-item" @change="handleFilter">-->
      <!--        <el-option v-for="item in sortOptions" :key="item.key" :label="item.label" :value="item.key" />-->
      <!--      </el-select>-->
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
        Add
      </el-button>
      <!--      <el-button v-waves :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload">-->
      <!--        Export-->
      <!--      </el-button>-->
      <!--      <el-checkbox v-model="showReviewer" class="filter-item" style="margin-left:15px;" @change="tableKey=tableKey+1">-->
      <!--        reviewer-->
      <!--      </el-checkbox>-->
    </div>

    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="list.filter(data => !search || data.userName.toLowerCase().includes(search.toLowerCase()))"
      border
      fit
      highlight-current-row
      style="width: 100%;"
      @sort-change="sortChange"
    >
      <el-table-column label="ID" prop="userId" sortable="custom" align="center" width="80" :class-name="getSortClass('id')">
        <template slot-scope="{row}">
          <span>{{ row.userId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Username" width="150px" align="center">
        <template slot-scope="{row}">
          <span class="link-type" @click="handleUpdate(row)">{{ row.userName }}</span>
          <!--          <el-tag>{{ row.type | typeFilter }}</el-tag>-->
        </template>
      </el-table-column>
      <el-table-column label="Email" min-width="150px">
        <template slot-scope="{row}">
          <span>{{ row.email }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Password" width="110px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.password }}</span>
        </template>
      </el-table-column>
      <!--      <el-table-column v-if="showReviewer" label="Reviewer" width="110px" align="center">-->
      <!--        <template slot-scope="{row}">-->
      <!--          <span style="color:red;">{{ row.reviewer }}</span>-->
      <!--        </template>-->
      <!--      </el-table-column>-->
      <el-table-column label="PhoneNumber" width="80px">
        <!--        <template slot-scope="{row}">-->
        <!--          <svg-icon v-for="n in + row.importance" :key="n" icon-class="star" class="meta-item__icon" />-->
        <!--        </template>-->
        <template slot-scope="{row}">
          <span>{{ row.phoneNumber }}</span>
        </template>
      </el-table-column>

      <el-table-column label="Credits" align="center" width="95">
        <template slot-scope="{row}">
          <span>{{ row.credits }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Level" class-name="status-col" width="100">
        <template slot-scope="{row}">
          <span>{{ row.level }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Access" class-name="status-col" width="100">
        <template slot-scope="{row}">
          <span>{{ row.access }}</span>
        </template>
      </el-table-column>

      <!--      <el-table-column label="Actions" align="center" width="230" class-name="small-padding fixed-width">-->
      <!--        <template slot-scope="{row,$index}">-->
      <!--          <el-button type="primary" size="mini" @click="handleUpdate(row)">-->
      <!--            Edit-->
      <!--          </el-button>-->
      <!--          <el-button v-if="row.status!='published'" size="mini" type="success" @click="handleModifyStatus(row,'published')">-->
      <!--            Publish-->
      <!--          </el-button>-->
      <!--          <el-button v-if="row.status!='draft'" size="mini" @click="handleModifyStatus(row,'draft')">-->
      <!--            Draft-->
      <!--          </el-button>-->
      <!--          <el-button v-if="row.status!='deleted'" size="mini" type="danger" @click="handleDelete(row,$index)">-->
      <!--            Delete-->
      <!--          </el-button>-->
      <!--        </template>-->
      <!--      </el-table-column>-->
    </el-table>

    <!--    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />-->

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="panelVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="70px" style="width: 400px; margin-left:50px;">
        <el-form-item label="Username" prop="userName">
          <el-input v-model="temp.userName" />
        </el-form-item>
        <el-form-item label="Email" prop="email">
          <el-input v-model="temp.email" />
        </el-form-item>
        <el-form-item label="Password" prop="password">
          <el-input v-model="temp.password" />
        </el-form-item>
        <el-form-item label="PhoneNumber" prop="phoneNumber">
          <el-input v-model="temp.phoneNumber" />
        </el-form-item>
        <el-form-item label="Credits" prop="credits">
          <el-input v-model="temp.credits" />
        </el-form-item>
        <el-form-item label="Level" prop="level">
          <el-input v-model="temp.level" />
        </el-form-item>
        <el-form-item label="Access">
          <el-switch
            v-model="temp.access"
            active-color="#13ce66"
            inactive-color="#ff4949"
          />
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="panelVisible = false">
          Cancel
        </el-button>
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
          Confirm
        </el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="dialogPvVisible" title="Reading statistics">
      <el-table :data="pvData" border fit highlight-current-row style="width: 100%">
        <el-table-column prop="key" label="Channel" />
        <el-table-column prop="pv" label="Pv" />
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogPvVisible = false">Confirm</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { fetchPv } from '@/api/article'
import waves from '@/directive/waves' // waves directive
// import Pagination from '@/components/Pagination'
import axios from 'axios' // secondary package based on el-pagination

export default {
  name: 'PlayerEntityPanel',
  components: { },
  directives: { waves },
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'info',
        deleted: 'danger'
      };
      return statusMap[status]
    }
    // typeFilter(type) {
    //   return calendarTypeKeyValue[type]
    // }
  },
  data() {
    return {
      search: '',
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: false,
      listQuery: {
        page: 1,
        limit: 20,
        importance: undefined,
        title: undefined,
        type: undefined,
        sort: '+id'
      },
      importanceOptions: [1, 2, 3],
      sortOptions: [{ label: 'ID Ascending', key: '+id' }, { label: 'ID Descending', key: '-id' }],
      statusOptions: ['published', 'draft', 'deleted'],
      showReviewer: false,
      temp: {
        userId: undefined,
        userName: '',
        password: '',
        phoneNumber: '',
        email: '',
        credits: 0,
        access: true,
        level: 0
      },
      panelVisible: false,
      dialogStatus: '',
      textMap: {
        update: 'Edit',
        create: 'Create'
      },
      dialogPvVisible: false,
      pvData: [],
      rules: {
        // type: [{ required: true, message: 'type is required', trigger: 'change' }],
        // timestamp: [{ type: 'date', required: true, message: 'timestamp is required', trigger: 'change' }],
        // title: [{ required: true, message: 'title is required', trigger: 'blur' }]
      },
      downloadLoading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true;
      axios.get('http://localhost:8080/user/getAllUsers')
        .then(response => this.list = response.data);
      setTimeout(() => {
        this.listLoading = false
      }, 1.5 * 10)
    },
    handleFilter() {
      this.listQuery.page = 1;
      this.getList()
    },
    handleModifyStatus(row, status) {
      this.$message({
        message: '操作Success',
        type: 'success'
      });
      row.status = status
    },
    sortChange(data) {
      const { prop, order } = data;
      if (prop === 'id') {
        this.sortByID(order)
      }
    },
    sortByID(order) {
      if (order === 'ascending') {
        this.listQuery.sort = '+id'
      } else {
        this.listQuery.sort = '-id'
      }
      this.handleFilter()
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        userName: '',
        password: '',
        phoneNumber: '',
        email: '',
        credits: 0,
        access: true,
        level: 0
      }
    },
    handleCreate() {
      this.resetTemp();
      this.dialogStatus = 'create';
      this.panelVisible = true;
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      const postData = new FormData();
      const _this = this;
      postData.append('userName', this.temp.userName);
      postData.append('password', this.temp.password);
      postData.append('phoneNumber', this.temp.phoneNumber);
      postData.append('credits', this.temp.credits);
      postData.append('access', this.temp.access);
      postData.append('level', this.temp.level);
      postData.append('email', this.temp.email);

      axios.post(`http://localhost:8080/user/addUser`, postData).then(response => {
        if (response.data) {
          //
        } else {
          //
        }
        axios.get('http://localhost:8080/user/getAllUsers')
          .then(response => this.list = response.data);
        _this.panelVisible = false
      })
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row); // copy obj
      this.temp.timestamp = new Date(this.temp.timestamp);
      this.dialogStatus = 'update';
      this.panelVisible = true;
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      const postData = new FormData();
      const _this = this;
      postData.append('userId', this.temp.userId);
      postData.append('userName', this.temp.userName);
      postData.append('password', this.temp.password);
      postData.append('phoneNumber', this.temp.phoneNumber);
      postData.append('credits', this.temp.credits);
      postData.append('access', this.temp.access);
      postData.append('level', this.temp.level);
      postData.append('email', this.temp.email);

      axios.post(`http://localhost:8080/user/updateUser`, postData).then(response => {
        if (response.data) {
          //
        } else {
          //
        }
        axios.get('http://localhost:8080/user/getAllUsers')
          .then(response => this.list = response.data);
        _this.panelVisible = false
      })

      // this.$refs['dataForm'].validate((valid) => {
      //   if (valid) {
      //     const tempData = Object.assign({}, this.temp)
      //     tempData.timestamp = +new Date(tempData.timestamp) // change Thu Nov 30 2017 16:41:05 GMT+0800 (CST) to 1512031311464
      //     updateArticle(tempData).then(() => {
      //       const index = this.list.findIndex(v => v.id === this.temp.userId)
      //       this.list.splice(index, 1, this.temp)
      //       this.panelVisible = false
      //       this.$notify({
      //         title: 'Success',
      //         message: 'Update Successfully',
      //         type: 'success',
      //         duration: 2000
      //       })
      //     })
      //   }
      // })
    },
    handleDelete(row, index) {
      this.$notify({
        title: 'Success',
        message: 'Delete Successfully',
        type: 'success',
        duration: 2000
      });
      this.list.splice(index, 1)
    },
    getSortClass: function(key) {
      const sort = this.listQuery.sort;
      return sort === `+${key}` ? 'ascending' : 'descending'
    },
    handleFetchPv(pv) {
      fetchPv(pv).then(response => {
        this.pvData = response.data.pvData;
        this.dialogPvVisible = true
      })
    }

    // handleDownload() {
    //   this.downloadLoading = true
    //   import('@/vendor/Export2Excel').then(excel => {
    //     const tHeader = ['timestamp', 'title', 'type', 'importance', 'status']
    //     const filterVal = ['timestamp', 'title', 'type', 'importance', 'status']
    //     const data = this.formatJson(filterVal)
    //     excel.export_json_to_excel({
    //       header: tHeader,
    //       data,
    //       filename: 'table-list'
    //     })
    //     this.downloadLoading = false
    //   })
    // },
    // formatJson(filterVal) {
    //   return this.list.map(v => filterVal.map(j => {
    //     if (j === 'timestamp') {
    //       return parseTime(v[j])
    //     } else {
    //       return v[j]
    //     }
    //   }))
    // },

  }
}
</script>
