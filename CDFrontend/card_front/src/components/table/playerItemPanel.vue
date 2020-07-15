<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="search" placeholder="Title" style="width: 200px;" class="filter-card" />
      <el-button class="filter-card" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
        Add
      </el-button>
    </div>

    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
      @sort-change="sortChange"
    >
      <el-table-column label="UserId" prop="userId" sortable="custom" align="center" width="80" :class-name="getSortClass('id')">
        <template slot-scope="{row}">
          <span>{{ row.userId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="ItemId" width="150px" align="center">
        <template slot-scope="{row}">
          <span class="link-type" @click="handleUpdate(row)">{{ row.itemId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="ItemCount" min-width="150px">
        <template slot-scope="{row}">
          <span>{{ row.itemCount }}</span>
        </template>
      </el-table-column>
      <el-table-column label="AccquireDate" min-width="150px">
        <template slot-scope="{row}">
          <span>{{ row.accquireDate }}</span>
        </template>
      </el-table-column>

    </el-table>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="panelVisible" top="5vh">
      <el-form ref="dataForm" :rules="rules" :model="temp" style="margin: auto 50px auto 50px; display:grid; grid-template-columns: 50% 50%; grid-column-gap: 10px" class="demo-form-inline">
        <el-form-item label="UserId" prop="userId">
          <el-input v-model="temp.userId" />
        </el-form-item>
        <el-form-item label="ItemId" prop="itemId">
          <el-input v-model="temp.itemId" />
        </el-form-item>
        <el-form-item label="ItemCount" prop="itemCount">
          <el-input v-model="temp.itemCount" />
        </el-form-item>
        <el-form-item label="AccquireDate" prop="accquireDate">
          <el-input v-model="temp.accquireDate" />
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-dialog
          title="Deletion Confirm"
          width="30%"
          :visible.sync="deleteVisible"
          append-to-body
        >
          <el-input v-model="confirmPassword" placeholder="Identification" show-password width="60%" />
          <el-button @click="confirmIdentity">Confirm Identity</el-button>

          <span slot="footer" class="dialog-footer">
            <el-button @click="deleteVisible = false">Cancel</el-button>
            <el-button v-if="confirmDelete === false" type="danger" disabled>Delete</el-button>
            <el-button v-else type="danger" @click="deleteData">Delete</el-button>
          </span>
        </el-dialog>

        <el-button type="danger" @click="deleteVisible = true">
          Delete
        </el-button>
        <el-button @click="panelVisible = false">
          Cancel
        </el-button>
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
          Confirm
        </el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { fetchList, fetchPv, createArticle, updateArticle } from '@/api/article'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination/index'
import axios from 'axios' // secondary package based on el-pagination

export default {
  name: 'PlayerItemPanel',
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
        ownItemId: undefined,
        userId: undefined,
        itemId: undefined,
        itemCount: undefined,
        accquireDate: undefined
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
  watch: {
    deleteVisible() {
      this.confirmDelete = false
      this.confirmPassword = ''
    }
  },
  created() {
    this.getList()
  },
  methods: {
    watchList() {
      const list = this.list
      for (const i in list) {
        const details = list[i].cardDetails
        list.cardImg = details.cardImg
        list.cardDescription = details.cardDescription
        list.shortDescription = details.shortDescription
      }
      this.list = list
    },
    confirmIdentity() {
      // TODO: REQUEST --- PWD USR MATCH
      const postData = new FormData()
      const _this = this
      postData.append('adminName', localStorage.getItem('AdminName'))
      postData.append('password', this.confirmPassword)
      axios.post('http://localhost:8080/admin/identifyAdmin', postData).then(response => {
        console.log(response)
        if (response.data) {
          _this.confirmDelete = true
        } else {
          this.$message.error('Identification failed!')
        }
      })
    },
    deleteData() {
      const postData = new FormData()
      const _this = this
      postData.append('userId', this.temp.userId)
      postData.append('itemId', this.temp.itemId)
      axios.post('http://localhost:8080/ownItem/deleteOwnItem', postData).then(response => {
        if (response.data) {
          _this.panelVisible = false
          _this.deleteVisible = false
          _this.getList()
        } else {
          this.$message.error('Identification failed!')
        }
      })
    },
    uploadCover() {
      const _this = this
      var file = this.$refs.img
      var reader = new FileReader()
      reader.readAsDataURL(file.files[0])
      reader.onload = function() {
        _this.temp.cardImg = this.result
      }
    },
    getList() {
      this.listLoading = true
      axios.get('http://localhost:8080/ownItem/getAllOwnItems')
        .then(response => {
          this.list = response.data
          // this.watchList();
        })

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
        ownItemId: undefined,
        userId: undefined,
        itemId: undefined,
        itemCount: undefined,
        accquireDate: undefined
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
      console.log('Inside CreateData')
      const postData = new FormData()
      postData.append('itemId', this.temp.itemId)
      postData.append('userId', this.temp.userId)
      postData.append('itemCount', this.temp.itemCount)

      axios.post(`http://localhost:8080/ownItem/addOwnItem`, postData).then(response => {
        if (response.data) {
          // TODO: SHORTEN THE REQUESTS
          this.getList()
        } else {
          //
        }
      })
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      // this.temp.timestamp = new Date(this.temp.timestamp)
      this.dialogStatus = 'update'
      this.panelVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      // let postData = new FormData();
      // postData.append('cardId', this.temp.cardId);
      // postData.append('userId', this.temp.userId);
      //
      // axios.post(`http://localhost:8080/ownCard/addOwnCard`, postData).then(response => {
      //   if(response.data) {
      //     // TODO: SHORTEN THE REQUESTS
      //     this.getList();
      //   }
      //   else {
      //     //
      //   }
      // });
    },
    handleDelete(row, index) {

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
  }
}
</script>
