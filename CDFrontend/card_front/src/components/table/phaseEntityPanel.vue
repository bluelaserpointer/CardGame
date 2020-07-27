<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="search" placeholder="Title" style="width: 200px;" class="filter-card" />
      <el-button class="filter-card createButton" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
        Add
      </el-button>
    </div>

    <el-table
      :key="tableKey"
      v-loading="listLoading"
      border
      fit
      highlight-current-row
      style="width: 100%;"
      :data="list"
      @sort-change="sortChange"
    >
      <el-table-column label="ID" prop="chapterId" sortable="custom" align="center" width="80" :class-name="getSortClass('id')">
        <template slot-scope="{row}">
          <span class="link-type" @click="handleUpdate(row)">{{ row.chapterId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="PhaseCount" prop="phaseNo" sortable="custom" align="center" width="80" :class-name="getSortClass('id')">
        <template slot-scope="{row}">
          <span>{{ row.phaseNo }}</span>
        </template>
      </el-table-column>
      <el-table-column label="PhaseType" prop="phaseType" sortable="custom" align="center" width="80" :class-name="getSortClass('id')">
        <template slot-scope="{row}">
          <span>{{ row.phaseType }}</span>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="panelVisible" top="5vh" class="editDialog">
      <el-form ref="temp" :rules="rules" :model="temp" style="margin: auto 50px auto 50px; display:grid; grid-template-columns: 50% 50%; grid-column-gap: 10px" class="demo-form-inline">
        <el-form-item label="ID" prop="chapterId" v-if="dialogStatus==='update'">
          <el-input v-model="temp.chapterId" disabled />
        </el-form-item>
        <el-form-item label="PhaseCount" prop="phaseNo">
          <el-input v-model="temp.phaseNo" />
        </el-form-item>
        <el-form-item label="PhaseType" prop="phaseType">
          <el-input v-model="temp.phaseType" />
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
  import Pagination from '@/components/Pagination/index'
  import axios from 'axios' // secondary package based on el-pagination
  import request from '@/utils/request'

  export default {
    name: 'PhaseEntityPanel',
    components: { Pagination },
    directives: { waves },
    data() {
      return {
        search: '',
        temp: {
          chapterId: undefined,
          phaseNo: undefined,
          phaseType: undefined
        },
        confirmPassword: '',
        confirmDelete: false,
        deleteVisible: false,
        rules: {
          chapterId: [{ required: true, message: 'ChapterId is required', trigger: 'change' }],
          phaseNo: [{ required: true, message: 'PhaseNo is required', trigger: 'change' }],
          phaseType: [{ required: true, message: 'PhaseType is required', trigger: 'change' }],
        },
        list: null,
        panelVisible: false,
        dialogStatus: '',


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
        downloadLoading: false
      }
    },
    watch: {
      deleteVisible() {
        this.confirmDelete = false;
        this.confirmPassword = '';
      } // untested
    },
    created() {
      this.getList()
    },
    methods: {
      getList() {
        let _this = this;
        request({
          url: '/chapter/getAllChapters',
          method: 'get',
        }).then(response => {
          if(response.data) {
            _this.list = response.data;
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

      confirmIdentity() {
        const postData = new FormData();
        const _this = this;
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
      },
      deleteData() {
        const postData = new FormData();
        const _this = this;
        postData.append('chapterId', this.temp.chapterId);

        request({
          url: '/chapter/deleteChapter',
          method: 'post',
          data: postData
        }).then(response => {
          if (response.data) {
            _this.list = response.data;
            _this.panelVisible = false;
            _this.deleteVisible = false;
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

      resetTemp() {
        this.temp = {
          chapterId: undefined,
          phaseNo: undefined,
          phaseType: undefined
        }
      },
      handleCreate() {
        this.resetTemp();
        this.dialogStatus = 'create';
        this.panelVisible = true;
        this.$nextTick(() => {
          this.$refs['temp'].clearValidate()
        })
      },
      createData(formName) {
        this.$refs.temp.validate((valid) => {
          if (valid) {
            const postData = new FormData();
            const _this = this;

            postData.append('phaseNo', this.temp.phaseNo);
            postData.append('phaseType', this.temp.phaseType);


            request({
              url: '/chapter/addChapter',
              method: 'post',
              data: postData
            }).then(response => {
              if (response.data) {
                _this.list = response.data;
                _this.panelVisible = false;
                _this.resetTemp();
              }else {
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
          this.$refs['temp'].clearValidate()
        })
      },
      updateData(formName) {
        this.$refs.temp.validate((valid) => {
          if (valid) {
            const postData = new FormData();
            const _this = this;

            postData.append('chapterId', this.temp.chapterId);
            postData.append('phaseNo', this.temp.phaseNo);
            postData.append('phaseType', this.temp.phaseType);

            request({
              url: '/chapter/updateChapter',
              method: 'post',
              data: postData
            }).then(response => {
              if (response.data) {
                _this.list = response.data;
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
          } else {
            this.$message.error('Form Invalid!');
            return false;
          }
        });

      },

      sortChange(data) {
        const { prop, order } = data;
        if (prop === 'id') {
          this.sortByID(order)
        }
      },  // untested
      sortByID(order) {
        if (order === 'ascending') {
          this.listQuery.sort = '+id'
        } else {
          this.listQuery.sort = '-id'
        }
        this.handleFilter()
      },  // untested
      getSortClass: function(key) {
        const sort = this.listQuery.sort;
        return sort === `+${key}` ? 'ascending' : 'descending'
      } // untested
    }
  }
</script>
