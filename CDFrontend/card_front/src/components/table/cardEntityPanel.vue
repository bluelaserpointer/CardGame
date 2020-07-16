<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="search" placeholder="Title" style="width: 200px;" class="filter-card" />
      <!--      <el-select v-model="listQuery.importance" placeholder="Imp" clearable style="width: 90px" class="filter-card">-->
      <!--        <el-option v-for="card in importanceOptions" :key="card" :label="card" :value="card" />-->
      <!--      </el-select>-->
      <!--      <el-select v-model="listQuery.type" placeholder="Type" clearable class="filter-card" style="width: 130px">-->
      <!--        <el-option v-for="card in calendarTypeOptions" :key="card.key" :label="card.display_name+'('+card.key+')'" :value="card.key" />-->
      <!--      </el-select>-->
      <!--      <el-select v-model="listQuery.sort" style="width: 140px" class="filter-card" @change="handleFilter">-->
      <!--        <el-option v-for="card in sortOptions" :key="card.key" :label="card.label" :value="card.key" />-->
      <!--      </el-select>-->
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
      <el-table-column label="ID" prop="cardId" sortable="custom" align="center" width="80" :class-name="getSortClass('id')">
        <template slot-scope="{row}">
          <span>{{ row.cardId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="CardName" width="150px" align="center">
        <template slot-scope="{row}">
          <span class="link-type" @click="handleUpdate()">{{ row.cardName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Rarity" min-width="150px">
        <template slot-scope="{row}">
          <span>{{ row.rarity }}</span>
        </template>
      </el-table-column>
      <el-table-column label="HP" width="110px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.healthPoint }}</span>
        </template>
      </el-table-column>
      <el-table-column label="ATK" width="80px">
        <template slot-scope="{row}">
          <span>{{ row.attack }}</span>
        </template>
      </el-table-column>
      <el-table-column label="DEF" align="center" width="95">
        <template slot-scope="{row}">
          <span>{{ row.defense }}</span>
        </template>
      </el-table-column>
      <el-table-column label="RANGE" class-name="status-col" width="100">
        <template slot-scope="{row}">
          <span>{{ row.attackRange }}</span>
        </template>
      </el-table-column>
      <el-table-column label="CD" class-name="status-col" width="100">
        <template slot-scope="{row}">
          <span>{{ row.cd }}</span>
        </template>
      </el-table-column>
      <el-table-column label="SPD" class-name="status-col" width="100">
        <template slot-scope="{row}">
          <span>{{ row.speed }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Card-Description" min-width="150px">
        <template slot-scope="{row}">
          <span>{{ row.cardDescription }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Short-Description" min-width="150px">
        <template slot-scope="{row}">
          <span>{{ row.shortDescription }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Cover" min-width="150px">
        <template slot-scope="{row}">
          <el-image
            style="width: 100px; height: 100px"
            :src="row.cardImg"
          />
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="panelVisible" top="5vh">
      <el-form ref="dataForm" :rules="rules" :model="temp" style="margin: auto 50px auto 50px; display:grid; grid-template-columns: 50% 50%; grid-column-gap: 10px" class="demo-form-inline">
        <el-form-item v-if="dialogStatus!=='create'" label="ID" prop="cardId">
          <el-input v-model="temp.cardId" />
        </el-form-item>
        <el-form-item label="CardName" prop="cardName">
          <el-input v-model="temp.cardName" />
        </el-form-item>
        <el-form-item label="Rarity" prop="rarity">
          <el-input v-model="temp.rarity" />
        </el-form-item>
        <el-form-item label="HP" prop="healthPoint">
          <el-input v-model="temp.healthPoint" />
        </el-form-item>
        <el-form-item label="ATK" prop="attack">
          <el-input v-model="temp.attack" />
        </el-form-item>
        <el-form-item label="DEF" prop="defense">
          <el-input v-model="temp.defense" />
        </el-form-item>
        <el-form-item label="RANGE" prop="attackRange">
          <el-input v-model="temp.attackRange" />
        </el-form-item>
        <el-form-item label="CD" prop="cd">
          <el-input v-model="temp.cd" />
        </el-form-item>
        <el-form-item label="SPD" prop="speed">
          <el-input v-model="temp.speed" />
        </el-form-item>
        <el-form-item label="Description" prop="cardDescription">
          <el-input v-model="temp.cardDescription" />
        </el-form-item>
        <div class="modalWrapper" style="display: grid; grid-template-columns: 50% 50%">
          <el-image
            style="width: 200px; height: 200px"
            :src="temp.cardImg"
            :fit="cardImg"
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
          <el-button class="confirmButton" @click="confirmIdentity">Confirm Identity</el-button>

          <span slot="footer" class="dialog-footer">
            <el-button class="cancelInnerButton" @click="deleteVisible = false">Cancel</el-button>
            <el-button v-if="confirmDelete === false" type="danger" disabled>Delete</el-button>
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
// import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination/index'
import axios from 'axios' // secondary package based on el-pagination

export default {
  name: 'CardEntityPanel',
  components: { Pagination },
  directives: { waves },
  // filters: {
  //   statusFilter(status) {
  //     const statusMap = {
  //       published: 'success',
  //       draft: 'info',
  //       deleted: 'danger'
  //     };
  //     return statusMap[status]
  //   }
  // },
  data() {
    return {
      search: '',
      cardImg: '',
      temp: {
        cardId: undefined,
        cardName: '',
        rarity: 'SSS',
        healthPoint: 0,
        attack: 0,
        defense: 0,
        attackRange: 0,
        cd: 0,
        speed: 0,
        cardImg: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
        cardDescription: ''
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
      rules: {
        // cardId: [{ required: true, message: 'type is required', trigger: 'change' }],
        // cardName: [{ type: 'date', required: true, message: 'timestamp is required', trigger: 'change' }],
        // rarity: [{ required: true, message: 'title is required', trigger: 'blur' }]
      },
      downloadLoading: false
    }
  },
  watch: {
    deleteVisible() {
      this.confirmDelete = false;
      this.confirmPassword = '';
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      let _this = this;
      // _this.listLoading = true;
      axios.get('http://localhost:8080/card/getAllCards')
      .then(response => {
          _this.list = response.data;
          _this.watchList();
          // _this.listLoading = false
        })
        .catch(error => console.log(error));
    },
    watchList() {
      const list = this.list;
      for (const i in list) {
        const details = list[i].cardDetails;
        list[i].cardImg = details.cardImg;
        list[i].cardDescription = details.cardDescription;
        list[i].shortDescription = details.shortDescription
      }
      this.list = list;
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
          this.$message.error('Identification failed!')
        }
      })
      .catch(error => console.log(error));
    },
    deleteData() {
      const postData = new FormData();
      const _this = this;
      postData.append('cardId', this.temp.cardId);
      axios.post('http://localhost:8080/card/deleteCard', postData).then(response => {
        if (response.data) {
          _this.panelVisible = false;
          _this.deleteVisible = false;
          _this.getList()
        } else {
          this.$message.error('Identification failed!')
        }
      })
      .catch(error => console.log(error));
    },
    resetTemp() {
      this.temp = {
        cardId: undefined,
        cardName: 'New Card',
        rarity: 'N1',
        healthPoint: 0,
        attack: 0,
        defense: 0,
        attackRange: 0,
        cd: 0,
        speed: 0,
        cardImg: '',
        cardDescription: 'No description yet.',
        shortDescription: 'No description yet.'
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
      postData.append('cardName', this.temp.cardName);
      postData.append('rarity', this.temp.rarity);
      postData.append('healthPoint', this.temp.healthPoint);
      postData.append('attack', this.temp.attack);
      postData.append('defense', this.temp.defense);
      postData.append('attackRange', this.temp.attackRange);
      postData.append('cd', this.temp.cd);
      postData.append('speed', this.temp.speed);

      postData.append('cardImg', this.temp.cardImg);
      postData.append('cardDescription', this.temp.cardDescription);
      postData.append('shortDescription', this.temp.shortDescription);

      axios.post(`http://localhost:8080/card/addCard`, postData).then(response => {
        if (response.data) {
          // TODO: SHORTEN THE REQUESTS
          _this.getList();
          _this.panelVisible = false;
          _this.resetTemp();
        }
      })
      .catch(error => console.log(error));
    },
    handleUpdate() {
      this.dialogStatus = 'update';
      this.panelVisible = true;
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      const postData = new FormData();
      const _this = this;
      postData.append('cardId', this.temp.cardId);
      postData.append('cardName', this.temp.cardName);
      postData.append('rarity', this.temp.rarity);
      postData.append('healthPoint', this.temp.healthPoint);
      postData.append('attack', this.temp.attack);
      postData.append('defense', this.temp.defense);
      postData.append('attackRange', this.temp.attackRange);
      postData.append('cd', this.temp.cd);
      postData.append('speed', this.temp.speed);
      postData.append('cardImg', this.temp.cardImg);
      postData.append('cardDescription', this.temp.cardDescription);
      postData.append('shortDescription', this.temp.shortDescription);

      axios.post(`http://localhost:8080/card/updateCard`, postData).then(response => {
          _this.getList();
          _this.panelVisible = false;
          _this.resetTemp();
      })
      .catch(error => console.log(error));

    },
    uploadCover() {
      const _this = this;
      var file = this.$refs.img;
      var reader = new FileReader();
      reader.readAsDataURL(file.files[0]);
      reader.onload = function() {
        _this.temp.cardImg = this.result
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
    }
  }
}
</script>
