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
      <el-table-column label="ID" prop="ownCardId" sortable="custom" align="center" width="80" :class-name="getSortClass('id')">
        <template slot-scope="{row}">
          <span class="link-type" @click="handleUpdate(row)">{{ row.ownCardId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="UserId" prop="cardId" sortable="custom" align="center" width="80">
        <template slot-scope="{row}">
          <span>{{ row.userId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="CardId" width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.cardId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="CardLevel" prop="cardLevel" sortable="custom" align="center" width="80">
        <template slot-scope="{row}">
          <span>{{ row.cardLevel }}</span>
        </template>
      </el-table-column>
      <el-table-column label="CardCurExp" prop="cardCurExp" sortable="custom" align="center" width="80">
        <template slot-scope="{row}">
          <span>{{ row.cardCurExp }}</span>
        </template>
      </el-table-column>
      <el-table-column label="CardLevelLimit" prop="cardLevelLimit" sortable="custom" align="center" width="80">
        <template slot-scope="{row}">
          <span>{{ row.cardLevelLimit }}</span>
        </template>
      </el-table-column>
      <el-table-column label="RepetitiveOwns" prop="repetitiveOwns" sortable="custom" align="center" width="80">
        <template slot-scope="{row}">
          <span>{{ row.repetitiveOwns }}</span>
        </template>
      </el-table-column>
      <el-table-column label="AccquireDate" width="160">
        <template slot-scope="{row}">
          <span>{{ formatDate(row.accquireDate) }}</span>
        </template>
      </el-table-column>

      <el-table-column label="EnhancePoint" prop="enhancePoint" sortable="custom" align="center">
        <template slot-scope="{row}">
          <span>{{ row.enhancePoint }}</span>
        </template>
      </el-table-column>
      <el-table-column label="LeftPoints" prop="leftPoints" sortable="custom" align="center">
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
      <el-table-column label="EnhanceDefense" prop="enhanceDefense" sortable="custom" align="center">
        <template slot-scope="{row}">
          <span>{{ row.enhanceDefense }}</span>
        </template>
      </el-table-column>
      <el-table-column label="EnhanceAttackRange" prop="enhanceAttackRange" sortable="custom" align="center">
        <template slot-scope="{row}">
          <span>{{ row.enhanceAttackRange }}</span>
        </template>
      </el-table-column>
      <el-table-column label="EnhanceCD" prop="enhanceCD" sortable="custom" align="center">
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

    <pagination v-show="listQuery.total > 0" :total.sync="listQuery.total * listQuery.limit" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList(listQuery.page, listQuery.limit)" />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="panelVisible" top="5vh" class="editDialog">
      <el-form ref="temp" :rules="rules" :model="temp" style="margin: auto 50px auto 50px; display:grid; grid-template-columns: 50% 50%; grid-column-gap: 10px" class="demo-form-inline">
        <el-form-item v-if="dialogStatus==='update'" label="ID" prop="ownCardId">
          <el-input v-model="temp.ownCardId" class="ownCardIdOwnCardInput" disabled />
        </el-form-item>
        <el-form-item v-if="dialogStatus==='create'" label="UserId" prop="userId">
          <el-input v-model="temp.userId" class="userIdOwnCardInput" />
        </el-form-item>
        <el-form-item v-if="dialogStatus==='create'" label="CardId" prop="cardId">
          <el-input v-model="temp.cardId" class="cardIdOwnCardInput" />
        </el-form-item>
        <el-form-item v-else label="UserId" prop="userId">
          <el-input v-model="temp.userId" class="userIdOwnCardInput" disabled />
        </el-form-item>
        <el-form-item v-else label="CardId" prop="cardId">
          <el-input v-model="temp.cardId" class="cardIdOwnCardInput" disabled />
        </el-form-item>
        <el-form-item v-if="dialogStatus==='update'" label="CardLevel" prop="cardLevel">
          <el-input v-model="temp.cardLevel" class="cardLevelOwnCardInput" />
        </el-form-item>
        <el-form-item v-if="dialogStatus==='update'" label="CardCurExp" prop="cardCurExp">
          <el-input v-model="temp.cardCurExp" class="cardCurExpOwnCardInput" />
        </el-form-item>
        <el-form-item v-if="dialogStatus==='update'" label="CardLevelLimit" prop="cardLevelLimit">
          <el-input v-model="temp.cardLevelLimit" class="cardLevelLimitOwnCardInput" />
        </el-form-item>
        <el-form-item v-if="dialogStatus==='update'" label="RepetitiveOwns" prop="repetitiveOwns">
          <el-input v-model="temp.repetitiveOwns" class="repetitiveOwnsOwnCardInput" />
        </el-form-item>
        <el-form-item v-if="dialogStatus==='update'" label-width="120px" label="AccquireDate" class="postInfo-container-item">
          <el-date-picker v-model="temp.accquireDate" type="datetime" value-format="yyyy-MM-dd hh:mm:ss" placeholder="Select date and time" />
        </el-form-item>
        <el-form-item v-if="dialogStatus==='update'" label="EnhancePoint" prop="enhancePoint">
          <el-input v-model="temp.enhancePoint" class="enhancePointInput" />
        </el-form-item>
        <el-form-item v-if="dialogStatus==='update'" label="LeftPoints" prop="leftPoints">
          <el-input v-model="temp.leftPoints" class="leftPointsInput" />
        </el-form-item>
        <el-form-item v-if="dialogStatus==='update'" label="EnhanceHP" prop="enhanceHP">
          <el-input v-model="temp.enhanceHP" class="enhanceHPInput" />
        </el-form-item>
        <el-form-item v-if="dialogStatus==='update'" label="EnhanceAttack" prop="enhanceAttack">
          <el-input v-model="temp.enhanceAttack" class="enhanceAttackInput" />
        </el-form-item>
        <el-form-item v-if="dialogStatus==='update'" label="EnhanceDefense" prop="enhanceDefense">
          <el-input v-model="temp.enhanceDefense" class="enhanceDefenseInput" />
        </el-form-item>
        <el-form-item v-if="dialogStatus==='update'" label="EnhanceAttackRange" prop="enhanceAttackRange">
          <el-input v-model="temp.enhanceAttackRange" class="enhanceAttackRangeInput" />
        </el-form-item>
        <el-form-item v-if="dialogStatus==='update'" label="EnhanceCD" prop="enhanceCD">
          <el-input v-model="temp.enhanceCD" class="enhanceCDInput" />
        </el-form-item>
        <el-form-item v-if="dialogStatus==='update'" label="EnhanceSpeed" prop="enhanceSpeed">
          <el-input v-model="temp.enhanceSpeed" class="enhanceSpeedInput" />
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
          <el-input v-model="confirmPassword" class="confirmOwnCardInput" placeholder="Identification" show-password width="60%" />
          <el-button class="confirmOwnCardInnerButton" @click="confirmIdentity">Confirm Identity</el-button>

          <span slot="footer" class="dialog-footer">
            <el-button class="cancelOwnCardInnerButton" @click="deleteVisible = false">Cancel</el-button>
            <el-button v-if="confirmDelete === false" class="deleteOwnCardInnerButton" type="danger" disabled>Delete</el-button>
            <el-button v-else class="deleteOwnCardInnerButton" type="danger" @click="deleteData">Delete</el-button>
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
import moment from 'moment'
import request from '@/utils/request'

export default {
  name: 'PlayerCardPanel',
  components: { Pagination },
  directives: { waves },
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
        enhanceSpeed: undefined
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
        enhanceSpeed: [{ required: true, message: 'EnhanceSpeed is required.', trigger: 'change' }]
      },

      tableKey: 0,
      listLoading: false,
      listQuery: {
        page: 1,
        limit: 20,
        total: 0,
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
      this.confirmDelete = false
      this.confirmPassword = ''
    }
  },
  created() {
    this.getList(this.listQuery.page, this.listQuery.limit)
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
    formatDate(date) {
      return moment(new Date(date)).format('YYYY-MM-DD HH:mm:ss')
    },
    getList(page, limit) {
      const postData = {
        pageToken: page,
        pageSize: limit
      }
      request.post('ownCard/List', postData).then(response => {
        if (response.data) {
          this.list = response.data.result
          this.listQuery.total = response.data.totalPages
        } else {
          this.$message.error('Fetching Data Failed!')
        }
      })
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
        enhanceSpeed: undefined
      }
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.panelVisible = true
      this.$nextTick(() => {
        this.$refs['temp'].clearValidate()
      })
    },
    submitCreate() {
      const postData = new FormData()
      postData.append('cardId', this.temp.cardId)
      postData.append('userId', this.temp.userId)

      request.post('ownCard/addOwnCard', postData).then(response => {
        if (response.data) {
          this.getList(this.listQuery.page, this.listQuery.limit)
          this.panelVisible = false
        } else {
          this.$message.error('Creating Data failed!')
        }
      })
        .catch(error => {
          this.$message.error('Creating Data failed!')
        }
        )
    },
    createData(formName) {
      this.$refs['temp'].validate((valid) => {
        if (valid) {
          this.submitCreate()
        } else {
          this.$message.error('Form Invalid!')
          return false
        }
      })
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      this.dialogStatus = 'update'
      this.panelVisible = true
      this.$nextTick(() => {
        this.$refs['temp'].clearValidate()
      })
    },
    submitUpdate() {
      const postData = {
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
        enhanceSpeed: this.temp.enhanceSpeed
      }

      request.post('ownCard/updateOwnCard', postData).then(response => {
        if (response.data) {
          this.getList(this.listQuery.page, this.listQuery.limit)
          this.panelVisible = false
          this.resetTemp()
        } else {
          this.$message.error('Updating Data failed!')
        }
      })
        .catch(error => {
          this.$message.error('Updating Data failed!')
        }
        )
    },
    updateData(formName) {
      this.$refs['temp'].validate((valid) => {
        if (valid) {
          this.submitUpdate()
        } else {
          this.$message.error('Form Invalid!')
          return false
        }
      })
    },
    confirmIdentity() {
      const postData = new FormData()
      const _this = this
      postData.append('userName', localStorage.getItem('AdminName'))
      postData.append('password', this.confirmPassword)

      request.post('user/confirmDelete', postData).then(response => {
        if (response.data) {
          _this.confirmDelete = true
        } else {
          this.$message.error('Identification failed!')
        }
      })
        .catch(error => {
          this.$message.error('Identification failed!')
        }
        )
    },
    deleteData() {
      const postData = new FormData()
      const _this = this
      postData.append('userId', this.temp.userId)
      postData.append('cardId', this.temp.cardId)

      request.post('ownCard/deleteOwnCard', postData).then(response => {
        if (response.data) {
          _this.panelVisible = false
          _this.deleteVisible = false
          _this.getList(this.listQuery.page, this.listQuery.limit)
        } else {
          this.$message.error('Deleting Data failed!')
        }
      })
        .catch(error => {
          this.$message.error('Deleting Data failed!')
        }
        )
    },

    handleFilter() {
      this.listQuery.page = 1
      this.getList(this.listQuery.page, this.listQuery.limit)
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
    getSortClass: function(key) {
      const sort = this.listQuery.sort
      return sort === `+${key}` ? 'ascending' : 'descending'
    }
  }
}
</script>
