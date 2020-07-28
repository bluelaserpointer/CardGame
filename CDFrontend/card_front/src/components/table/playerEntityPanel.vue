<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="search" placeholder="Title" style="width: 200px;" class="filter-card" />
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
        Add
      </el-button>
    </div>

<!--    :data="list.filter(data => !search || data.userName.toLowerCase().includes(search.toLowerCase()))"-->
    <el-table
      :key="tableKey"
      border
      fit
      :data="list"
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
      <el-table-column label="PhoneNumber" width="80px">
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

      <el-table-column label="CurExpPoint" class-name="status-col" width="100">
        <template slot-scope="{row}">
          <span>{{ row.curExpPoint }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Stamina" class-name="status-col" width="100">
        <template slot-scope="{row}">
          <span>{{ row.stamina }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Money" class-name="status-col" width="100">
        <template slot-scope="{row}">
          <span>{{ row.money }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Grade" class-name="status-col" width="100">
        <template slot-scope="{row}">
          <span>{{ row.grade }}</span>
        </template>
      </el-table-column>
      <el-table-column label="EngKnowledge" class-name="status-col" width="100">
        <template slot-scope="{row}">
          <span>{{ row.engKnowledge }}</span>
        </template>
      </el-table-column>
      <el-table-column label="MathKnowledge" class-name="status-col" width="100">
        <template slot-scope="{row}">
          <span>{{ row.mathKnowledge }}</span>
        </template>
      </el-table-column>
      <el-table-column label="ChiKnowledge" class-name="status-col" width="100">
        <template slot-scope="{row}">
          <span>{{ row.chiKnowledge }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Role" class-name="status-col" width="100">
        <template slot-scope="{row}">
          <span>{{ row.identity }}</span>
        </template>
      </el-table-column>

    </el-table>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="panelVisible">
      <el-form ref="temp" :rules="rules" :model="temp" label-position="left" label-width="70px" style="margin: auto 20px auto 20px; display:grid; grid-template-columns: 50% 50%; grid-column-gap: 10px">
        <el-form-item label="ID" prop="userId" v-if="dialogStatus==='update'">
          <el-input v-model="temp.userId" disabled />
        </el-form-item>
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

        <el-form-item label="CurExpPoint" prop="curExpPoint" v-if="dialogStatus==='update'">
          <el-input v-model="temp.curExpPoint" />
        </el-form-item>
        <el-form-item label="Stamina" prop="stamina" v-if="dialogStatus==='update'">
          <el-input v-model="temp.stamina" />
        </el-form-item>
        <el-form-item label="Money" prop="money" v-if="dialogStatus==='update'">
          <el-input v-model="temp.money" />
        </el-form-item>
        <el-form-item label="Grade" prop="grade" v-if="dialogStatus==='update'">
          <el-input v-model="temp.grade" />
        </el-form-item>
        <el-form-item label="EngKnowledge" prop="engKnowledge" v-if="dialogStatus==='update'">
          <el-input v-model="temp.engKnowledge" />
        </el-form-item>
        <el-form-item label="MathKnowledge" prop="mathKnowledge" v-if="dialogStatus==='update'">
          <el-input v-model="temp.mathKnowledge" />
        </el-form-item>
        <el-form-item label="ChiKnowledge" prop="chiKnowledge" v-if="dialogStatus==='update'">
          <el-input v-model="temp.chiKnowledge" />
        </el-form-item>
        <el-form-item label="Role" prop="identity">
          <el-input v-model="temp.identity" />
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
import request from "@/utils/request"; // secondary package based on el-pagination

export default {
  name: 'PlayerEntityPanel',
  components: { },
  directives: { waves },
  data() {
    return {
      search: '',
      tableKey: 0,
      list: null,
      total: 0,
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
        level: 0,
        curExpPoint: 0,
        stamina: 0,
        money: 0,
        grade: 0.0,
        engKnowledge: 0,
        mathKnowledge: 0,
        chiKnowledge: 0,
        identity: 'ROLE_USER'
      },
      panelVisible: false,
      dialogStatus: '',

      confirmPassword: '',
      confirmDelete: false,
      deleteVisible: false,

      textMap: {
        update: 'Edit',
        create: 'Create'
      },
      rules: {
        userId: [{ required: true, message: 'UserId is required', trigger: 'change' }],
        userName: [{ required: true, message: 'UserName is required', trigger: 'change' }],
        password: [{ required: true, message: 'Password is required', trigger: 'change' }],
        phoneNumber: [{ required: true, message: 'PhoneNumber is required', trigger: 'change' }],
        email: [{ required: true, message: 'Email is required', trigger: 'change' }],
        credits: [{ required: true, message: 'Credits is required', trigger: 'change' }],
        level: [{ required: true, message: 'Level is required', trigger: 'change' }],
        identity: [{ required: true, message: 'Identity is required', trigger: 'change' }],
        curExpPoint: [{ required: true, message: 'CurExpPoint is required', trigger: 'change' }],
        stamina: [{ required: true, message: 'Stamina is required', trigger: 'change' }],
        money: [{ required: true, message: 'Money is required', trigger: 'change' }],
        grade: [{ required: true, message: 'Grade is required', trigger: 'change' }],
        engKnowledge: [{ required: true, message: 'EngKnowledge is required', trigger: 'change' }],
        mathKnowledge: [{ required: true, message: 'MathKnowledge is required', trigger: 'change' }],
        chiKnowledge: [{ required: true, message: 'ChiKnowledge is required', trigger: 'change' }]
      }
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
      request({
        url: '/user/getAllUsers',
        method: 'get',
      }).then(response =>
      {
        if(response.data)
        {
          console.log(response.data);
          this.list = response.data;
        }else{
          this.$message.error('Fetching Data failed!');
        }
      }).catch(error => {
        this.$message.error('Fetching Data failed!');
      });

    },

    resetTemp() {
      this.temp = {
        userId: undefined,
        userName: '',
        password: '',
        phoneNumber: '',
        email: '',
        credits: 0,
        access: true,
        level: 0,
        curExpPoint: 0,
        stamina: 0,
        money: 0,
        grade: 0.0,
        engKnowledge: 0,
        mathKnowledge: 0,
        chiKnowledge: 0,
        identity: 'ROLE_USER'
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
          const _this = this;

          let postData = {
            userName: this.temp.userName,
            password: this.temp.password,
            phoneNumber: this.temp.phoneNumber,
            credits: this.temp.credits,
            access: this.temp.access,
            level: this.temp.level,
            email: this.temp.email,
            identity: this.temp.identity
          };

          request({
            url: '/user/register',
            method: 'post',
            data: JSON.stringify(postData)
          }).then(response => {
            if (response.data) {
              _this.getList();
              _this.panelVisible = false;
              _this.resetTemp();
            }else {
              this.$message.error('Adding Data failed!');
            }
          })
            .catch(error =>
              {
                this.$message.error('Adding Data failed!');
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
      this.temp.timestamp = new Date(this.temp.timestamp);
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
            userId: this.temp.userId,
            userName: this.temp.userName,
            password: this.temp.password,
            phoneNumber: this.temp.phoneNumber,
            credits: this.temp.credits,
            access: this.temp.access,
            level: this.temp.level,
            email: this.temp.email,
            curExpPoint: this.temp.curExpPoint,
            stamina: this.temp.stamina,
            money: this.temp.money,
            grade: this.temp.grade,
            engKnowledge: this.temp.engKnowledge,
            mathKnowledge: this.temp.mathKnowledge,
            chiKnowledge: this.temp.chiKnowledge,
            identity: this.temp.identity
          };

          request({
            url: '/user/updateUser',
            method: 'post',
            data: JSON.stringify(postData)
          }).then(response => {
            if (response.data) {
              _this.getList();
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
        console.log(response);
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
      postData.append('userId', this.temp.userId);

      request({
        url: '/user/deleteUser',
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
