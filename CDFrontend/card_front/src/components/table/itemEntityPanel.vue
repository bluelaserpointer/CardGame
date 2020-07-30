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

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="panelVisible" top="5vh" class="editDialog">
      <el-form ref="temp" :rules="rules" :model="temp" style="margin: auto 50px auto 50px; display:grid; grid-template-columns: 50% 50%; grid-column-gap: 10px" class="demo-form-inline">
        <el-form-item label="ID" prop="itemId"v-if="dialogStatus==='update'">
          <el-input v-model="temp.itemId" disabled/>
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
            <input ref="img" type="file" style="margin: 10px" @change="uploadCover">
          </div>
        </div>
      </el-form>

      <div slot="footer" class="dialog-footer outerDialog">
        <el-dialog
          title="Deletion Confirm"
          width="30%"
          :visible.sync="deleteVisible"
          append-to-body
          class="innerDialog"
        >
          <el-input v-model="confirmPassword" placeholder="Identification" show-password width="60%" />
          <el-button class="confirmInnerButton" @click="confirmIdentity">Confirm Identity</el-button>

          <span slot="footer" class="dialog-footer">
            <el-button class="cancelInnerButton" @click="deleteVisible = false">Cancel</el-button>
            <el-button class="deleteInnerButton" v-if="confirmDelete === false" type="danger" disabled>Delete</el-button>
            <el-button class="deleteInnerButton" v-else type="danger" @click="deleteData">Delete</el-button>
          </span>
        </el-dialog>

        <el-button class="deleteOuterButton" type="danger" @click="deleteVisible = true">
          Delete
        </el-button>
        <el-button class="cancelOuterButton" @click="panelVisible = false">
          Cancel
        </el-button>
        <el-button class="confirmOuterButton" type="primary" @click="dialogStatus==='create'?createData('temp'):updateData('temp')">
          Confirm
        </el-button>
      </div>

    </el-dialog>

  </div>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import Pagination from '@/components/Pagination/index' // secondary package based on el-pagination
import request from '@/utils/request'

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
      temp: {
        itemId: undefined,
        itemName: '',
        price: 999,
        itemImg: '',
        itemDescription: ''
      },
      confirmPassword: '',
      confirmDelete: false,
      deleteVisible: false,
      list: null,
      panelVisible: false,
      dialogStatus: '',
      rules: {
        itemId: [{ required: true, message: 'ItemId is required', trigger: 'change' }],
        itemName: [{ required: true, message: 'ItemName is required', trigger: 'change' }],
        price: [{ required: true, message: 'type is required', trigger: 'change' }],
      },


      tableKey: 0,
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
      sortOptions: [{ label: 'ID Ascending', key: '+id' }, { label: 'ID Descending', key: '-id' }],
      statusOptions: ['published', 'draft', 'deleted'],
      showReviewer: false,
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
      const postData = new FormData();
      const _this = this;
      postData.append('userName', localStorage.getItem('AdminName'));
      postData.append('password', this.confirmPassword);

      request({
        url: 'user/confirmDelete',
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
    },
    deleteData() {
      const postData = new FormData();
      const _this = this;
      postData.append('itemId', this.temp.itemId);

      request({
        url: 'item/deleteItem',
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

      // axios.post('http://localhost:8080/item/deleteItem', postData).then(response => {
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
      }
    },
    getList() {
      let _this = this;
      request({
        url: 'item/getAllItems',
        method: 'get',
      }).then( response => {
        if(response.data) {
          _this.list = response.data;
          _this.watchList();
        }else
        {
          this.$message.error('Fetching Data Failed!');
        }
      }).catch( error => {
        this.$message.error('Fetching Data Failed!');
      });
      //
      // axios.get('http://localhost:8080/item/getAllItems')
      //   .then(response => {
      //     this.list = response.data;
      //     this.watchList()
      //   })
      //   .catch(error =>
      //   {
      //     this.$message.error('Fetching Data Failed!');
      //   });
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
        this.$refs.temp.clearValidate()
      })
    },
    createData(formName) {
      this.$refs.temp.validate((valid) => {
        if (valid) {
          let postData = {
            itemName: this.temp.itemName,
            price: this.temp.price,
            itemDetails: {
              itemImg: this.temp.itemImg,
              itemDescription: this.temp.itemDescription
            }
          };

          request({
            url: 'item/addItem',
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
          const _this = this;

          let postData = {
            itemId: this.temp.itemId,
            itemName: this.temp.itemName,
            price: this.temp.price,
            itemDetails: {
              itemId: this.temp.itemId,
              itemImg: this.temp.itemImg,
              itemDescription: this.temp.itemDescription,
            }
          };

          request({
            url: 'item/updateItem',
            method: 'post',
            data: JSON.stringify(postData)
          }).then(response => {
            if (response.data) {
              this.getList();
              _this.panelVisible = false
            } else {
              this.$message.error('Updating Data failed!');
            }
          })
            .catch(error =>
              {
                this.$message.error('Updating Data failed!');
              }
            );
        } else {
          this.$message.error('Form Invalid!');
          return false;
        }
      });

    },
    getSortClass: function(key) {
      const sort = this.listQuery.sort;
      return sort === `+${key}` ? 'ascending' : 'descending'
    },
  }
}
</script>
