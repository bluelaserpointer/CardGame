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
      <el-table-column label="ID" prop="ownCardId" sortable="custom" align="center" width="80" :class-name="getSortClass('id')" >
        <template slot-scope="{row}">
          <span class="link-type" @click="handleUpdate(row)">{{ row.ownCardId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="UserId" prop="cardId" sortable="custom" align="center" width="80" >
        <template slot-scope="{row}">
          <span>{{ row.userId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="CardId" width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.cardId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="CardLevel" prop="cardLevel" sortable="custom" align="center" width="80" >
        <template slot-scope="{row}">
          <span>{{ row.cardLevel }}</span>
        </template>
      </el-table-column>
      <el-table-column label="CardCurExp" prop="cardCurExp" sortable="custom" align="center" width="80" >
        <template slot-scope="{row}">
          <span>{{ row.cardCurExp }}</span>
        </template>
      </el-table-column>
      <el-table-column label="CardLevelLimit" prop="cardLevelLimit" sortable="custom" align="center" width="80" >
        <template slot-scope="{row}">
          <span>{{ row.cardLevelLimit }}</span>
        </template>
      </el-table-column>
      <el-table-column label="RepetitiveOwns" prop="repetitiveOwns" sortable="custom" align="center" width="80" >
        <template slot-scope="{row}">
          <span>{{ row.repetitiveOwns }}</span>
        </template>
      </el-table-column>
      <el-table-column label="AccquireDate" min-width="150px">
        <template slot-scope="{row}">
          <span>{{ formatDate(row.accquireDate) }}</span>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="panelVisible" top="5vh" class="editDialog">
      <el-form ref="dataForm" :rules="rules" :model="temp" style="margin: auto 50px auto 50px; display:grid; grid-template-columns: 50% 50%; grid-column-gap: 10px" class="demo-form-inline">
        <el-form-item label="ID" prop="ownCardId" v-if="dialogStatus==='update'">
          <el-input v-model="temp.ownCardId" disabled/>
        </el-form-item>
        <el-form-item label="UserId" prop="userId">
          <el-input v-model="temp.userId" />
        </el-form-item>
        <el-form-item label="CardId" prop="cardId">
          <el-input v-model="temp.cardId" />
        </el-form-item>
        <el-form-item label="CardLevel" prop="cardLevel" v-if="dialogStatus==='update'">
          <el-input v-model="temp.cardLevel" />
        </el-form-item>
        <el-form-item label="CardCurExp" prop="cardCurExp" v-if="dialogStatus==='update'">
          <el-input v-model="temp.cardCurExp" />
        </el-form-item>
        <el-form-item label="CardLevelLimit" prop="cardLevelLimit" v-if="dialogStatus==='update'">
          <el-input v-model="temp.cardLevelLimit" />
        </el-form-item>
        <el-form-item label="RepetitiveOwns" prop="repetitiveOwns" v-if="dialogStatus==='update'">
          <el-input v-model="temp.repetitiveOwns" />
        </el-form-item>
        <el-form-item label-width="120px" label="AccquireDate" class="postInfo-container-item" v-if="dialogStatus==='update'">
          <el-date-picker v-model="temp.accquireDate" type="datetime" value-format="yyyy-MM-dd hh:mm:ss" placeholder="Select date and time" />
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
        <el-button class="confirmOuterButton" type="primary" @click="dialogStatus==='create'?createData():updateData()">
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
import moment from "moment"

export default {
  name: 'PlayerCardPanel',
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
        ownCardId: undefined,
        userId: undefined,
        cardId: undefined,
        cardLevel: undefined,
        cardCurExp: undefined,
        cardLevelLimit: undefined,
        repetitiveOwns: undefined,
        accquireDate: undefined
      },
      confirmPassword: '',
      confirmDelete: false,
      deleteVisible: false,
      list: null,
      panelVisible: false,
      dialogStatus: '',
      rules: {
        userId: [{ required: true, message: 'UserId is required.', trigger: 'change' }],
        cardId: [{ required: true, message: 'CardId is required.', trigger: 'change' }],
        cardLevel: [{ required: true, message: 'CardLevel is required.', trigger: 'change' }],
        cardCurExp: [{ required: true, message: 'CardCurExp is required.', trigger: 'change' }],
        cardLevelLimit: [{ required: true, message: 'CardLevelLimit is required.', trigger: 'change' }],
        repetitiveOwns: [{ required: true, message: 'RepetitiveOwns is required.', trigger: 'change' }],
        accquireDate: [{ required: true, message: 'AccquireData is required.', trigger: 'change' }],
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
      axios.get('http://localhost:8080/ownCard/getAllOwnCards')
        .then(response => {
          this.list = response.data
        })
        .catch(error =>
        {
          this.$message.error('Fetching Data Failed!');
        });
    },
    resetTemp() {
      this.temp = {
        ownCardId: undefined,
        userId: undefined,
        cardId: undefined,
        cardLevel: undefined,
        cardCurExp: undefined,
        cardLevelLimit: undefined,
        repetitiveOwns: undefined,
        accquireDate: undefined
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
      let postData = new FormData();
      postData.append('cardId', this.temp.cardId);
      postData.append('userId', this.temp.userId);

      axios.post(`http://localhost:8080/ownCard/addOwnCard`, postData).then(response => {
        if (response.data) {
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
      postData.append('cardId', this.temp.cardId);
      postData.append('userId', this.temp.userId);
      postData.append('cardLevel', this.temp.cardLevel);
      postData.append('cardCurExp', this.temp.cardCurExp);
      postData.append('cardLevelLimit', this.temp.cardLevelLimit);
      postData.append('repetitiveOwns', this.temp.repetitiveOwns);
      postData.append('accquireDate', this.formatDate(this.temp.accquireDate));
      console.log(this.formatDate(this.temp.accquireDate));
      axios.post(`http://localhost:8080/ownCard/updateOwnCard`, postData).then(response => {
        if (response.data) {
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
    },
    confirmIdentity() {
      let postData = new FormData();
      let _this = this;
      postData.append('adminName', localStorage.getItem('AdminName'));
      postData.append('password', this.confirmPassword);
      axios.post('http://localhost:8080/admin/identifyAdmin', postData).then(response => {
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
      let postData = new FormData();
      let _this = this;
      postData.append('userId', this.temp.userId);
      postData.append('cardId', this.temp.cardId);
      axios.post('http://localhost:8080/ownCard/deleteOwnCard', postData).then(response => {
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
