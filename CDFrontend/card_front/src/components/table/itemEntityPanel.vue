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
      <el-button class="filter-card" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
        Add
      </el-button>
      <!--      <el-button v-waves :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload">-->
      <!--        Export-->
      <!--      </el-button>-->
      <!--      <el-checkbox v-model="showReviewer" class="filter-item" style="margin-left:15px;" @change="tableKey=tableKey+1">-->
      <!--        reviewer-->
      <!--      </el-checkbox>-->
    </div>


<!--    :data="list.filter(data => !search || data.itemName.toLowerCase().includes(search.toLowerCase()))"-->
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
      <el-table-column label="ID" prop="itemId" sortable="custom" align="center" width="80" :class-name="getSortClass('id')">
        <template slot-scope="{row}">
          <span>{{ row.itemId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="ItemName" width="150px" align="center">
        <template slot-scope="{row}">
          <span class="link-type" @click="handleUpdate(row)">{{ row.itemName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Price" min-width="150px">
        <template slot-scope="{row}">
          <span>{{ row.price }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Description" min-width="150px">
        <template slot-scope="{row}">
          <span>{{ row.itemDescription }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Cover" min-width="150px">
        <template slot-scope="{row}">
          <el-image
            style="width: 100px; height: 100px"
            :src="row.itemImg"
          />
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="panelVisible" top="5vh">
      <el-form ref="dataForm" :rules="rules" :model="temp" style="margin: auto 50px auto 50px; display:grid; grid-template-columns: 50% 50%; grid-column-gap: 10px" class="demo-form-inline">
        <el-form-item v-if="dialogStatus!=='create'" label="ID" prop="itemId">
          <el-input v-model="temp.itemId" />
        </el-form-item>
        <el-form-item label="ItemName" prop="itemName">
          <el-input v-model="temp.itemName" />
        </el-form-item>
        <el-form-item label="Price" prop="price">
          <el-input v-model="temp.price" />
        </el-form-item>
        <el-form-item label="Description" prop="itemDescription">
          <el-input v-model="temp.itemDescription" />
        </el-form-item>

        <div class="modalWrapper" style="display: grid; grid-template-columns: 50% 50%">
          <el-image
            style="width: 200px; height: 200px"
            :src="temp.itemImg"
            :fit="itemImg"
          />
          <div class="coverControl">
            <el-button type="primary" style="margin: 10px">上传<i class="el-icon-upload el-icon--right" /></el-button>
            <input ref="img" type="file" style="margin: 10px" @change="uploadCover">
          </div>
        </div>
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

    <!--    <el-dialog :visible.sync="dialogPvVisible" title="Reading statistics">-->
    <!--      <el-table :data="pvData" border fit highlight-current-row style="width: 100%">-->
    <!--        <el-table-column prop="key" label="Channel" />-->
    <!--        <el-table-column prop="pv" label="Pv" />-->
    <!--      </el-table>-->
    <!--      <span slot="footer" class="dialog-footer">-->
    <!--        <el-button type="primary" @click="dialogPvVisible = false">Confirm</el-button>-->
    <!--      </span>-->
    <!--    </el-dialog>-->
  </div>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import Pagination from '@/components/Pagination/index' // secondary package based on el-pagination
import axios from 'axios'

export default {
  name: 'ItemEntityPanel',
  components: { Pagination },
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

  },
  data() {
    return {
      search: '',
      confirmPassword: '',
      confirmDelete: false,
      deleteVisible: false,
      tableKey: 0,
      list: null,
      total: 0,
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
      statusOptions: ['published', 'draft', 'deleted'],
      showReviewer: false,
      temp: {
        itemId: undefined,
        itemName: '',
        price: 999,
        itemImg: '',
        itemDescription: ''
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
  watch: {
    deleteVisible() {
      this.confirmDelete = false;
      this.confirmPassword = ''
    }
  },
  created() {
    this.getList()
  },
  methods: {
    watchList() {
      const list = this.list;
      for (const i in list) {
        const details = list[i].itemDetails;
        list[i].itemImg = details.itemImg;
        list[i].itemDescription = details.itemDescription
      }
      this.list = list
    },
    confirmIdentity() {
      // TODO: REQUEST --- PWD USR MATCH
      const postData = new FormData();
      const _this = this;
      postData.append('adminName', localStorage.getItem('AdminName'));
      postData.append('password', this.confirmPassword);
      axios.post('http://localhost:8080/admin/identifyAdmin', postData).then(response => {
        console.log(response);
        if (response.data) {
          _this.confirmDelete = true
        } else {
          this.$message.error('Identification failed!')
        }
      })
    },
    deleteData() {
      const postData = new FormData();
      const _this = this;
      postData.append('itemId', this.temp.itemId);
      axios.post('http://localhost:8080/item/deleteItem', postData).then(response => {
        if (response.data) {
          _this.panelVisible = false;
          _this.deleteVisible = false;
          _this.getList()
        } else {
          this.$message.error('Identification failed!')
        }
      })
    },
    uploadCover() {
      const _this = this;
      // 根据ref得到图片文件
      var file = this.$refs.img;
      // 使用h5的读取文件api
      var reader = new FileReader();
      reader.readAsDataURL(file.files[0]);
      // 读取完成后触发
      reader.onload = function() {
        // 改变img的路径
        _this.temp.itemImg = this.result;
        console.log('In onload')
      }
    },
    getList() {
      axios.get('http://localhost:8080/item/getAllItems')
        .then(response => {
          this.list = response.data;
          this.watchList()
        });
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
        itemId: undefined,
        itemName: 'New Item',
        price: 999,
        itemImg: '',
        itemDescription: 'No description yet.'
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
      postData.append('itemName', this.temp.itemName);
      postData.append('price', this.temp.price);
      postData.append('itemImg', this.temp.itemImg);
      postData.append('itemDescription', this.temp.itemDescription);

      axios.post(`http://localhost:8080/item/addItem`, postData).then(response => {
        if (response.data) {
          // TODO: SHORTEN THE REQUESTS
          this.getList();
          _this.panelVisible = false
        } else {
          //
        }
      })
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row); // copy obj
      // this.temp.timestamp = new Date(this.temp.timestamp)
      this.dialogStatus = 'update';
      this.panelVisible = true;
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      const postData = new FormData();
      const _this = this;
      postData.append('itemId', this.temp.itemId);
      postData.append('itemName', this.temp.itemName);
      postData.append('price', this.temp.price);
      postData.append('itemImg', this.temp.itemImg);
      postData.append('itemDescription', this.temp.itemDescription);
      console.log(postData);
      axios.post(`http://localhost:8080/item/updateItem`, postData).then(response => {
        if (response.data) {
          //
          this.getList();
          _this.panelVisible = false
        } else {
          //
        }
      })
    },
    getSortClass: function(key) {
      const sort = this.listQuery.sort;
      return sort === `+${key}` ? 'ascending' : 'descending'
    },
  }
}
</script>
