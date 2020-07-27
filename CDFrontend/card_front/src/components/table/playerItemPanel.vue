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
      <el-table-column label="ID" prop="ownItemId" sortable="custom" align="center" width="80" :class-name="getSortClass('id')" >
        <template slot-scope="{row}">
          <span class="link-type" @click="handleUpdate(row)">{{ row.ownItemId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="UserId" prop="userId" sortable="custom" align="center" width="80" :class-name="getSortClass('id')">
        <template slot-scope="{row}">
          <span>{{ row.userId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="ItemId" width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.itemId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="ItemCount" min-width="150px">
        <template slot-scope="{row}">
          <span>{{ row.itemCount }}</span>
        </template>
      </el-table-column>
      <el-table-column label="AccquireDate" min-width="150px">
        <template slot-scope="{row}">
          <span>{{ formatDate(row.accquireDate) }}</span>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="panelVisible" top="5vh" class="editDialog">
      <el-form ref="temp" :rules="rules" :model="temp" style="margin: auto 50px auto 50px; display:grid; grid-template-columns: 50% 50%; grid-column-gap: 10px" class="demo-form-inline">
        <el-form-item label="ID" prop="ownItemId" v-if="dialogStatus==='update' ">
          <el-input v-model="temp.ownItemId" disabled/>
        </el-form-item>
        <el-form-item label="UserId" prop="userId" v-if="dialogStatus==='create'">
          <el-input class="userIdOwnItemInput" v-model="temp.userId"/>
        </el-form-item>
        <el-form-item label="ItemId" prop="itemId" v-if="dialogStatus==='create'">
          <el-input class="itemIdOwnItemInput" v-model="temp.itemId"/>
        </el-form-item>
        <el-form-item label="UserId" prop="userId" v-else>
          <el-input class="userIdOwnItemInput" v-model="temp.userId" disabled/>
        </el-form-item>
        <el-form-item label="ItemId" prop="itemId" v-else>
          <el-input class="itemIdOwnItemInput" v-model="temp.itemId" disabled/>
        </el-form-item>
        <el-form-item label="ItemCount" prop="itemCount">
          <el-input class="itemCountOwnItemInput" v-model="temp.itemCount" />
        </el-form-item>
        <el-form-item label-width="120px" label="AccquireDate" class="postInfo-container-item" v-if="dialogStatus==='update'">
          <el-date-picker class="dateOwnItemInput" v-model="temp.accquireDate" type="datetime" value-format="yyyy-MM-dd hh:mm:ss" placeholder="Select date and time" />
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer outerDialog">
        <el-dialog
          title="Deletion Confirm"
          width="30%"
          :visible.sync="deleteVisible"
          append-to-body
          class="innerDialog"
        >
          <el-input class="confirmOwnItemInput" v-model="confirmPassword" placeholder="Identification" show-password width="60%" />
          <el-button class="confirmOwnItemInnerButton" @click="confirmIdentity">Confirm Identity</el-button>

          <span slot="footer" class="dialog-footer">
            <el-button class="cancelOwnItemInnerButton" @click="deleteVisible = false">Cancel</el-button>
            <el-button class="deleteOwnItemInnerButton" v-if="confirmDelete === false" type="danger" disabled>Delete</el-button>
            <el-button class="deleteOwnItemInnerButton" v-else type="danger" @click="deleteData">Delete</el-button>
          </span>
        </el-dialog>

        <el-button class="deleteOwnItemOuterButton" type="danger" @click="deleteVisible = true">
          Delete
        </el-button>
        <el-button class="cancelOwnItemOuterButton" @click="panelVisible = false">
          Cancel
        </el-button>
        <el-button class="confirmOwnItemOuterButton" type="primary" @click="dialogStatus==='create'?createData('temp'):updateData('temp')">
          Confirm
        </el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import Pagination from '@/components/Pagination/index'
import axios from 'axios'
import moment from "moment";
import request from "@/utils/request"; // secondary package based on el-pagination

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
      };
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
      list: null,
      panelVisible: false,
      dialogStatus: '',
      rules: {
        ownItemId: [{ required: true, message: 'OwnItemId is required', trigger: 'change' }],
        userId: [{ required: true, message: 'UserId is required', trigger: 'change' }],
        itemId: [{ required: true, message: 'ItemId is required', trigger: 'change' }],
        itemCount: [{ required: true, message: 'ItemCount is required', trigger: 'change' }],
        accquireDate: [{ required: true, message: 'AccquireDate is required', trigger: 'change' }],
      },


      tableKey: 0,
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
      textMap: {
        update: 'Edit',
        create: 'Create'
      },
      dialogPvVisible: false,
      pvData: [],
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
    // watchList() {
    //   const list = this.list;
    //   for (const i in list) {
    //     const details = list[i].cardDetails;
    //     list.cardImg = details.cardImg;
    //     list.cardDescription = details.cardDescription;
    //     list.shortDescription = details.shortDescription
    //   }
    //   this.list = list
    // },
    formatDate(date){
      return moment(new Date(date)).format('YYYY-MM-DD HH:mm:ss');
    },
    getList() {

      request({
        url: '/ownItem/getAllOwnItems',
        method: 'get',
      }).then(response => {
        this.list = response.data
      })
        .catch(error =>
        {
          this.$message.error('Fetching Data Failed!');
        });
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
      this.resetTemp();
      this.dialogStatus = 'create';
      this.panelVisible = true;
      this.$nextTick(() => {
        this.$refs.temp.clearValidate()
      })
    },
    createData(formName) {
      this.$refs.temp.validate((valid) => {
        if (valid) {
          let postData = {
            itemId: this.temp.itemId,
            userId: this.temp.userId,
            itemCount: this.temp.itemCount
          };

          request({
            url: '/ownItem/addOwnItem',
            method: 'post',
            data: JSON.stringify(postData)
          }).then(response => {
            if (response.data) {
              // TODO: SHORTEN THE REQUESTS
              this.getList();
              this.panelVisible = false;
            } else {
              this.$message.error('Creating Data failed!');
            }
          })
            .catch(error =>
              {
                this.$message.error('Creating Data failed!');
              }
            );

          // axios.post(`http://localhost:8080/ownItem/addOwnItem`, postData).then(response => {
          //   if (response.data) {
          //     // TODO: SHORTEN THE REQUESTS
          //     this.getList();
          //     this.panelVisible = false;
          //   } else {
          //     this.$message.error('Creating Data failed!');
          //   }
          // })
          //   .catch(error =>
          //     {
          //       this.$message.error('Creating Data failed!');
          //     }
          //   );

        } else {
          this.$message.error('Form Invalid!');
          return false;
        }
      });

    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row); // copy obj
      this.dialogStatus = 'update';
      this.panelVisible = true;
      this.$nextTick(() => {
        this.$refs.temp.clearValidate()
      })
    },
    updateData(formName) {
      this.$refs.temp.validate((valid) => {
        if (valid) {

          let postData = {
            ownItemId: this.temp.ownItemId,
            itemId: this.temp.itemId,
            userId: this.temp.userId,
            itemCount: this.temp.itemCount
          };

          request({
            url: '/ownItem/updateOwnItem',
            method: 'post',
            data: JSON.stringify(postData)
          }).then(response => {
            if (response.data) {
              // TODO: SHORTEN THE REQUESTS
              this.getList();
              this.panelVisible = false;
              this.resetTemp();
            } else {
              this.$message.error('Updating Data failed!');
            }
          })
            .catch(error =>
              {
                this.$message.error('Updating Data failed!');
              }
            );

          // axios.post(`http://localhost:8080/ownItem/updateOwnItem`, postData).then(response => {
          //   if (response.data) {
          //     // TODO: SHORTEN THE REQUESTS
          //     this.getList();
          //     this.panelVisible = false;
          //     this.resetTemp();
          //   } else {
          //     this.$message.error('Updating Data failed!');
          //   }
          // })
          //   .catch(error =>
          //     {
          //       this.$message.error('Updating Data failed!');
          //     }
          //   );


        } else {
          this.$message.error('Form Invalid!');
          return false;
        }
      });

    },
    confirmIdentity() {
      let postData = new FormData();
      let _this = this;
      postData.append('userName', localStorage.getItem('AdminName'));
      postData.append('password', this.confirmPassword);

      request({
        url: '/user/confirmDelete',
        method: 'post',
        data: postData
      }).then(response => {
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

      // axios.post('http://localhost:8080/admin/identifyAdmin', postData).then(response => {
      //   if (response.data) {
      //     _this.confirmDelete = true
      //   } else {
      //     this.$message.error('Identification failed!');
      //   }
      // })
      // .catch(error =>
      //   {
      //     this.$message.error('Identification failed!');
      //   }
      // );
    },
    deleteData() {
      let postData = new FormData();
      let _this = this;
      postData.append('userId', this.temp.userId);
      postData.append('itemId', this.temp.itemId);

      request({
        url: '/ownItem/deleteOwnItem',
        method: 'post',
        data: postData
      }).then(response => {
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

      // axios.post('http://localhost:8080/ownItem/deleteOwnItem', postData).then(response => {
      //   if (response.data) {
      //     _this.panelVisible = false;
      //     _this.deleteVisible = false;
      //     _this.getList()
      //   } else {
      //     this.$message.error('Deleting Data failed!');
      //   }
      // })
      // .catch(error =>
      //   {
      //     this.$message.error('Deleting Data failed!');
      //   }
      // );
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
