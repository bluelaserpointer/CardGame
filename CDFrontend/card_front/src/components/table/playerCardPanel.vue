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
      <el-table-column label="AccquireDate" width="160">
        <template slot-scope="{row}">
          <span>{{ formatDate(row.accquireDate) }}</span>
        </template>
      </el-table-column>

      <el-table-column label="EnhancePoint" prop="enhancePoint" sortable="custom" align="center" >
        <template slot-scope="{row}">
          <span>{{ row.enhancePoint }}</span>
        </template>
      </el-table-column>
      <el-table-column label="LeftPoints" prop="leftPoints" sortable="custom" align="center" >
        <template slot-scope="{row}">
          <span>{{ row.leftPoints }}</span>
        </template>
      </el-table-column>
      <el-table-column label="EnhanceHP" prop="enhanceHP" sortable="custom" align="center">
        <template slot-scope="{row}">
          <span>{{ row.enhanceHP }}</span>
        </template>
      </el-table-column>
      <el-table-column label="EnhanceAttack" prop="enhanceAttack" sortable="custom" align="center">
        <template slot-scope="{row}">
          <span>{{ row.enhanceAttack }}</span>
        </template>
      </el-table-column>
      <el-table-column label="EnhanceDefense" prop="enhanceDefense" sortable="custom" align="center" >
        <template slot-scope="{row}">
          <span>{{ row.enhanceDefense }}</span>
        </template>
      </el-table-column>
      <el-table-column label="EnhanceAttackRange" prop="enhanceAttackRange" sortable="custom" align="center">
        <template slot-scope="{row}">
          <span>{{ row.enhanceAttackRange }}</span>
        </template>
      </el-table-column>
      <el-table-column label="EnhanceCD" prop="enhanceCD" sortable="custom" align="center" >
        <template slot-scope="{row}">
          <span>{{ row.enhanceCD }}</span>
        </template>
      </el-table-column>
      <el-table-column label="EnhanceSpeed" prop="enhanceSpeed" sortable="custom" align="center">
        <template slot-scope="{row}">
          <span>{{ row.enhanceSpeed }}</span>
        </template>
      </el-table-column>

    </el-table>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="panelVisible" top="5vh" class="editDialog">
      <el-form ref="temp" :rules="rules" :model="temp" style="margin: auto 50px auto 50px; display:grid; grid-template-columns: 50% 50%; grid-column-gap: 10px" class="demo-form-inline">
        <el-form-item label="ID" prop="ownCardId" v-if="dialogStatus==='update'">
          <el-input class="ownCardIdOwnCardInput" v-model="temp.ownCardId" disabled/>
        </el-form-item>
        <el-form-item label="UserId" prop="userId" v-if="dialogStatus==='create'">
          <el-input class="userIdOwnCardInput" v-model="temp.userId"/>
        </el-form-item>
        <el-form-item label="CardId" prop="cardId" v-if="dialogStatus==='create'">
          <el-input class="cardIdOwnCardInput" v-model="temp.cardId"/>
        </el-form-item>
        <el-form-item label="UserId" prop="userId" v-else>
          <el-input class="userIdOwnCardInput" v-model="temp.userId" disabled/>
        </el-form-item>
        <el-form-item label="CardId" prop="cardId" v-else>
          <el-input class="cardIdOwnCardInput" v-model="temp.cardId" disabled/>
        </el-form-item>
        <el-form-item label="CardLevel" prop="cardLevel" v-if="dialogStatus==='update'">
          <el-input class="cardLevelOwnCardInput" v-model="temp.cardLevel" />
        </el-form-item>
        <el-form-item label="CardCurExp" prop="cardCurExp" v-if="dialogStatus==='update'">
          <el-input class="cardCurExpOwnCardInput" v-model="temp.cardCurExp" />
        </el-form-item>
        <el-form-item label="CardLevelLimit" prop="cardLevelLimit" v-if="dialogStatus==='update'">
          <el-input class="cardLevelLimitOwnCardInput" v-model="temp.cardLevelLimit" />
        </el-form-item>
        <el-form-item label="RepetitiveOwns" prop="repetitiveOwns" v-if="dialogStatus==='update'">
          <el-input class="repetitiveOwnsOwnCardInput" v-model="temp.repetitiveOwns" />
        </el-form-item>
        <el-form-item label-width="120px" label="AccquireDate" class="postInfo-container-item" v-if="dialogStatus==='update'">
          <el-date-picker v-model="temp.accquireDate" type="datetime" value-format="yyyy-MM-dd hh:mm:ss" placeholder="Select date and time" />
        </el-form-item>
        <el-form-item label="EnhancePoint" prop="enhancePoint" v-if="dialogStatus==='update'">
          <el-input class="enhancePointInput" v-model="temp.enhancePoint" />
        </el-form-item>
        <el-form-item label="LeftPoints" prop="leftPoints" v-if="dialogStatus==='update'">
          <el-input class="leftPointsInput" v-model="temp.leftPoints" />
        </el-form-item>
        <el-form-item label="EnhanceHP" prop="enhanceHP" v-if="dialogStatus==='update'">
          <el-input class="enhanceHPInput" v-model="temp.enhanceHP" />
        </el-form-item>
        <el-form-item label="EnhanceAttack" prop="enhanceAttack" v-if="dialogStatus==='update'">
          <el-input class="enhanceAttackInput" v-model="temp.enhanceAttack" />
        </el-form-item>
        <el-form-item label="EnhanceDefense" prop="enhanceDefense" v-if="dialogStatus==='update'">
          <el-input class="enhanceDefenseInput" v-model="temp.enhanceDefense" />
        </el-form-item>
        <el-form-item label="EnhanceAttackRange" prop="enhanceAttackRange" v-if="dialogStatus==='update'">
          <el-input class="enhanceAttackRangeInput" v-model="temp.enhanceAttackRange" />
        </el-form-item>
        <el-form-item label="EnhanceCD" prop="enhanceCD" v-if="dialogStatus==='update'">
          <el-input class="enhanceCDInput" v-model="temp.enhanceCD" />
        </el-form-item>
        <el-form-item label="EnhanceSpeed" prop="enhanceSpeed" v-if="dialogStatus==='update'">
          <el-input class="enhanceSpeedInput" v-model="temp.enhanceSpeed" />
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
          <el-input class="confirmOwnCardInput" v-model="confirmPassword" placeholder="Identification" show-password width="60%" />
          <el-button class="confirmOwnCardInnerButton" @click="confirmIdentity">Confirm Identity</el-button>

          <span slot="footer" class="dialog-footer">
            <el-button class="cancelOwnCardInnerButton" @click="deleteVisible = false">Cancel</el-button>
            <el-button class="deleteOwnCardInnerButton" v-if="confirmDelete === false" type="danger" disabled>Delete</el-button>
            <el-button class="deleteOwnCardInnerButton" v-else type="danger" @click="deleteData">Delete</el-button>
          </span>
        </el-dialog>

        <el-button class="deleteOwnCardOuterButton" type="danger" @click="deleteVisible = true">
          Delete
        </el-button>
        <el-button class="cancelOwnCardOuterButton" @click="panelVisible = false">
          Cancel
        </el-button>
        <el-button class="confirmOwnCardOuterButton" type="primary" @click="dialogStatus==='create'?createData('temp'):updateData('temp')">
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
import request from "@/utils/request";

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
        accquireDate: undefined,
        enhancePoint: undefined,
        leftPoints: undefined,
        enhanceHP: undefined,
        enhanceAttack: undefined,
        enhanceDefense: undefined,
        enhanceAttackRange: undefined,
        enhanceCD: undefined,
        enhanceSpeed: undefined,
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

        enhancePoint: [{ required: true, message: 'EnhancePoint is required.', trigger: 'change' }],
        leftPoints: [{ required: true, message: 'LeftPoints is required.', trigger: 'change' }],
        enhanceHP: [{ required: true, message: 'EnhanceHP is required.', trigger: 'change' }],
        enhanceAttack: [{ required: true, message: 'EnhanceAttack is required.', trigger: 'change' }],
        enhanceDefense: [{ required: true, message: 'EnhanceDefense is required.', trigger: 'change' }],
        enhanceAttackRange: [{ required: true, message: 'EnhanceAttackRange is required.', trigger: 'change' }],
        enhanceCD: [{ required: true, message: 'EnhanceCD is required.', trigger: 'change' }],
        enhanceSpeed: [{ required: true, message: 'EnhanceSpeed is required.', trigger: 'change' }],
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
        url: '/ownCard/getAllOwnCards',
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
        ownCardId: undefined,
        userId: undefined,
        cardId: undefined,
        cardLevel: undefined,
        cardCurExp: undefined,
        cardLevelLimit: undefined,
        repetitiveOwns: undefined,
        accquireDate: undefined,
        enhancePoint: undefined,
        leftPoints: undefined,
        enhanceHP: undefined,
        enhanceAttack: undefined,
        enhanceDefense: undefined,
        enhanceAttackRange: undefined,
        enhanceCD: undefined,
        enhanceSpeed: undefined,
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

          let postData = new FormData();
          postData.append('cardId', this.temp.cardId);
          postData.append('userId', this.temp.userId);

          request({
            url: '/ownCard/addOwnCard',
            method: 'post',
            data: postData
          }).then(response => {
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
            ownCardId: this.temp.ownCardId,
            cardId: this.temp.cardId,
            userId: this.temp.userId,
            cardLevel: this.temp.cardLevel,
            cardCurExp: this.temp.cardCurExp,
            cardLevelLimit: this.temp.cardLevelLimit,
            repetitiveOwns: this.temp.repetitiveOwns,
            accquireDate: this.formatDate(this.temp.accquireDate),

            enhancePoint: this.temp.enhancePoint,
            leftPoints: this.temp.leftPoints,
            enhanceHP: this.temp.enhanceHP,
            enhanceAttack: this.temp.enhanceAttack,
            enhanceDefense: this.temp.enhanceDefense,
            enhanceAttackRange: this.temp.enhanceAttackRange,
            enhanceCD: this.temp.enhanceCD,
            enhanceSpeed: this.temp.enhanceSpeed,
          };

          request({
            url: '/ownCard/updateOwnCard',
            method: 'post',
            data: postData
          }).then(response => {
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
    },
    deleteData() {
      let postData = new FormData();
      let _this = this;
      postData.append('userId', this.temp.userId);
      postData.append('cardId', this.temp.cardId);


      request({
        url: '/ownCard/deleteOwnCard',
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
