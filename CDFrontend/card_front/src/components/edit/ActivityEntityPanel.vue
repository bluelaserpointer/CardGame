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
      <el-table-column label="Limited" min-width="150px">
        <template slot-scope="{row}">
          <span>{{ row.type }}</span>
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

  <el-dialog :title="textMap[dialogStatus]" :visible.sync="panelVisible" top="5vh" class="editDialog">

  </el-dialog>

</template>

<script>
import waves from '@/directive/waves' // waves directive
import Pagination from '@/components/Pagination/index'
import axios from 'axios'
import moment from "moment"; // secondary package based on el-pagination

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
      };
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
        start: '2020-01-01 00:00:00',
        type: false,
      },
      confirmPassword: '',
      confirmDelete: false,
      deleteVisible: false,
      tableKey: 0,
      list: null,
      listLoading: false,
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
    formatDate(date){
      return moment(new Date(date)).format('YYYY-MM-DD HH:mm:ss');
    },

    watchList() {
      const list = this.list;
      for (const i in list) {
        const details = list[i].activityDetails;
        console.log(details);
        if (details === null) { continue }
        list[i].activityImg = details.activityImg;
        list[i].activityDescription = details.activityDescription;
      }
      this.list = list
    },

    confirmIdentity() {
      const postData = new FormData();
      const _this = this;
      postData.append('adminName', localStorage.getItem('AdminName'));
      postData.append('password', this.confirmPassword);
      axios.post('http://localhost:8080/admin/identifyAdmin', postData)
        .then(response => {
          if (response.data) {
            _this.confirmDelete = true
          } else {
            this.$message.error('Identification failed!');
          }
        })
        .catch(error =>
          {
            this.$message.error('Identification failed!');
          }
        );
    },

    deleteData() {
      const postData = new FormData();
      const _this = this;
      postData.append('activityId', this.temp.activityId);
      axios.post('http://localhost:8080/activity/deleteActivity', postData).then(response => {
        if (response.data) {
          _this.panelVisible = false;
          _this.deleteVisible = false;
          _this.getList()
        } else {
          this.$message.error('Deleting Data failed!');
        }
      })
      .catch(error =>
        {
          this.$message.error('Deleting Data failed!');
        }
      );
    },
    getList() {
      axios.get('http://localhost:8080/activity/getAllActivities')
        .then(response => {
        if(response.data) {
          this.list = response.data;
          this.watchList();
        }else
        {
          this.$message.error('Fetching Data Failed!');
        }
      })
        .catch(error =>
        {
          this.$message.error('Fetching Data Failed!');
        });
    },
    resetTemp() {
      this.temp = {
        activityId: undefined,
        activityName: '',
        activityImg: '',
        activityDescription: '',
        start: '2020-01-01 00:00:00',
        type: false,
      }
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row); // copy obj
      this.dialogStatus = 'update';
      this.panelVisible = true;
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      let postData = new FormData();
      let _this = this;
      postData.append('activityId', this.temp.activityId);
      postData.append('activityName', this.temp.activityName);
      postData.append('activityImg', this.temp.activityImg);
      postData.append('activityDescription', this.temp.activityDescription);
      postData.append('type', this.temp.type);
      postData.append('start', this.formatDate(this.temp.start));

      axios.post(`http://localhost:8080/activity/updateActivity`, postData).then(response => {
        if (response.data) {
          //
          _this.getList();
          _this.panelVisible = false;
          _this.resetTemp();
        }else {
          this.$message.error('Updating Data failed!');
        }
      })
      .catch(error =>
        {
          this.$message.error('Updating Data failed!');
        }
      );

    },

    uploadCover() {
      const _this = this;
      var file = this.$refs.img;
      var reader = new FileReader();
      reader.readAsDataURL(file.files[0]);
      reader.onload = function() {
        _this.temp.activityImg = this.result
      }
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
    getSortClass: function(key) {
      const sort = this.listQuery.sort;
      return sort === `+${key}` ? 'ascending' : 'descending'
    },

  }
}
</script>
