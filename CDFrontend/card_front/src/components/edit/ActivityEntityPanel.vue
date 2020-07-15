<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="search" placeholder="Title" style="width: 200px;" class="filter-card" />
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate" />
    </div>

    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="list.filter(data => !search || data.activityName.toLowerCase().includes(search.toLowerCase()))"
      border
      fit
      highlight-current-row
      style="width: 100%;"
      @sort-change="sortChange"
    >
      <el-table-column label="ID" prop="id" sortable="custom" align="center" width="80" :class-name="getSortClass('id')">
        <template slot-scope="{row}">
          <span>{{ row.activityId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Name" min-width="150px">
        <template slot-scope="{row}">
          <span>{{ row.activityName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Description" min-width="150px">
        <template slot-scope="{row}">
          <span>{{ row.activityDescription }}</span>
        </template>
      </el-table-column>
      <el-table-column label="StartTime" min-width="150px">
        <template slot-scope="{row}">
          <span>{{ row.start }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Cover" min-width="150px">
        <template slot-scope="{row}">
          <el-image
            style="width: 100px; height: 100px"
            :src="row.activityImg"
          />
        </template>
      </el-table-column>
    </el-table>

  </div>
</template>

<script>
import { fetchList, fetchPv, createArticle, updateArticle } from '@/api/article'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination/index'
import axios from 'axios' // secondary package based on el-pagination

export default {
  name: 'ActivityEntityPanel',
  components: { Pagination },
  directives: { waves },
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'info',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      search: '',
      temp: {
        activityId: undefined,
        activityName: '',
        activityImg: '',
        activityDescription: '',
        start: '2020-01-01 00:00:00'
      },
      confirmPassword: '',
      confirmDelete: false,
      deleteVisible: false,
      tableKey: 0,
      list: null,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        importance: undefined,
        title: undefined,
        type: undefined,
        sort: '+id'
      },
      sortOptions: [{ label: 'ID Ascending', key: '+id' }, { label: 'ID Descending', key: '-id' }],
      // statusOptions: ['published', 'draft', 'deleted'],
      // showReviewer: false,
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
    watchList() {
      console.log('In watchList')
      const list = this.list
      for (const i in list) {
        const details = list[i].activityDetails
        console.log(details)
        if (details === null) { continue }
        list[i].activityImg = details.activityImg
        list[i].activityDescription = details.activityDescription
        console.log(list[i])
      }
      this.list = list
    },
    confirmIdentity() {
      // TODO: REQUEST --- PWD USR MATCH
      // IF MATCH
      //    CONFIRM DELETE
      // ELSE
      //    DELETION FAILED
    },
    deleteData() {
      // TODO: REQUEST --- DELETE THE DATA
    },
    uploadCover() {
      const _this = this
      var file = this.$refs.img
      var reader = new FileReader()
      reader.readAsDataURL(file.files[0])
      reader.onload = function() {
        _this.temp.activityImg = this.result
      }
    },
    getList() {
      console.log('In getList')
      this.listLoading = true
      axios.get('http://localhost:8080/activity/getAllActivities')
        .then(response => {
          console.log(response.data)
          this.list = response.data
          this.watchList()
        })
      // Just to simulate the time of the request
      setTimeout(() => {
        this.listLoading = false
      }, 1.5 * 10)
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    handleModifyStatus(row, status) {
      this.$message({
        message: '操作Success',
        type: 'success'
      })
      row.status = status
    },
    sortChange(data) {
      const { prop, order } = data
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
        activityId: undefined,
        activityName: '',
        activityImg: '',
        activityDescription: '',
        start: '2020-01-01 00:00:00'
      }
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.panelVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      this.dialogStatus = 'update'
      this.panelVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      const postData = new FormData()
      postData.append('activityId', this.temp.activityId)
      postData.append('activityName', this.temp.activityName)
      postData.append('activityImg', this.temp.activityImg)
      postData.append('activityDescription', this.temp.activityDescription)
      postData.append('start', this.temp.start)

      axios.post(`http://localhost:8080/activity/updateActivity`, postData).then(response => {
        if (response.data) {
          //
        } else {
          //
        }
        axios.get('http://localhost:8080/activity/getAllActivities')
          .then(response => this.list = response.data)
      })
      // this.$refs['dataForm'].validate((valid) => {
      //   if (valid) {
      //     const tempData = Object.assign({}, this.temp)
      //     tempData.timestamp = +new Date(tempData.timestamp) // change Thu Nov 30 2017 16:41:05 GMT+0800 (CST) to 1512031311464
      //     updateArticle(tempData).then(() => {
      //       const index = this.list.findIndex(v => v.id === this.temp.id)
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
      // this.$notify({
      //   title: 'Success',
      //   message: 'Delete Successfully',
      //   type: 'success',
      //   duration: 2000
      // })
      // this.list.splice(index, 1)
    },
    getSortClass: function(key) {
      const sort = this.listQuery.sort
      return sort === `+${key}` ? 'ascending' : 'descending'
    },
    handleFetchPv(pv) {
      fetchPv(pv).then(response => {
        this.pvData = response.data.pvData
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
