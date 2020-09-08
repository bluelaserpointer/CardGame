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

    <!--    :data="list.filter(data => !search || data.itemName.toLowerCase().includes(search.toLowerCase()))"-->
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
          <span class="link-type" @click="handleUpdate(row)">{{ row.cardName }}</span>
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
      <el-table-column label="Type" class-name="status-col" width="100">
        <template slot-scope="{row}">
          <span>{{ row.type }}</span>
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

    <pagination v-show="listQuery.total > 0" :total.sync="listQuery.total * listQuery.limit" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList(listQuery.page, listQuery.limit)" />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="panelVisible" top="5vh" class="editDialog">
      <el-form ref="temp" :rules="rules" :model="temp" style="margin: auto 50px auto 50px; display:grid; grid-template-columns: 50% 50%; grid-column-gap: 10px" class="demo-form-inline">
        <el-form-item label="ID" prop="cardId" v-if="dialogStatus==='update'">
          <el-input v-model="temp.cardId" disabled />
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
        <el-form-item label="Type" prop="type">
          <el-input v-model="temp.type" />
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
import Pagination from '@/components/Pagination/index'
import request from '@/utils/request'


export default {
  name: 'CardEntityPanel',
  components: { Pagination },
  directives: { waves },
  data() {
    return {
      search: '',
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
        type: 1,
        cardImg: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
        cardDescription: ''
      },
      confirmPassword: '',
      confirmDelete: false,
      deleteVisible: false,
      rules: {
        cardId: [{ required: true, message: 'CardId is required', trigger: 'change' }],
        cardName: [{ required: true, message: 'CardName is required', trigger: 'change' }],
        rarity: [{ required: true, message: 'Rarity is required', trigger: 'change' }],
        healthPoint: [{ required: true, message: 'HealthPoint is required', trigger: 'change' }],
        attack: [{ required: true, message: 'Attack is required', trigger: 'change' }],
        defense: [{ required: true, message: 'Defense is required', trigger: 'change' }],
        attackRange: [{ required: true, message: 'AttackRange is required', trigger: 'change' }],
        cd: [{ required: true, message: 'Cd is required', trigger: 'change' }],
        speed: [{ required: true, message: 'Speed is required', trigger: 'change' }],
        type: [{ required: true, message: 'Type is required', trigger: 'change' }]
      },
      list: null,
      panelVisible: false,
      dialogStatus: '',

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
    this.getList(1, this.listQuery.limit);
  },
  methods: {
    getList(page, limit) {
      console.log("In getList");
      console.log(this.listQuery.page);
      let _this = this;
      let postData = {
        pageToken: page,
        pageSize: limit
      };
      request.post('card/List', postData).then( response => {
        console.log("In response");
        if(response.data) {
          _this.list = response.data.result;
          console.log("In if1");
          console.log(_this.list);
          _this.listQuery.total = response.data.totalPages;
          // _this.listQuery.total = 3;
          _this.watchList();
        }else
        {
          console.log("In if2");
          this.$message.error('Fetching Data Failed!');
        }
      })
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
      postData.append('userName', localStorage.getItem('AdminName'));
      postData.append('password', this.confirmPassword);

      request.post('user/confirmDelete', postData).then(response => {
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
      postData.append('cardId', this.temp.cardId);

      request.post('card/deleteCard',postData).then(response => {
        if (response.data) {
          _this.panelVisible = false;
          _this.deleteVisible = false;
          this.getList(this.listQuery.page, this.listQuery.limit);
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
        cardId: undefined,
        cardName: 'New Card',
        rarity: 'N1',
        healthPoint: 0,
        attack: 0,
        defense: 0,
        attackRange: 0,
        cd: 0,
        speed: 0,
        type: 1,
        cardImg: '',
        cardDescription: 'No description yet.',
        shortDescription: 'No description yet.'
      }
    },
    handleCreate() {
      let _this = this;
      this.resetTemp();
      this.dialogStatus = 'create';
      this.panelVisible = true;
      this.$nextTick(() => {
        _this.$refs['temp'].clearValidate()
      })
    },
    createData(formName) {
      const _this = this;
      this.$refs['temp'].validate((valid) => {
        if (valid) {
          this.submitCreate();
        } else {
          this.$message.error('Form Invalid!');
          return false;
        }
      });
    },
    submitCreate(){
      let _this = this;
      let postData = {
        cardName: this.temp.cardName,
        rarity: this.temp.rarity,
        healthPoint: this.temp.healthPoint,
        attack: this.temp.attack,
        defense: this.temp.defense,
        attackRange: this.temp.attackRange,
        cd: this.temp.cd,
        speed: this.temp.speed,
        type: this.temp.type,
        cardDetails: {
          cardImg: this.temp.cardImg,
          cardDescription: this.temp.cardDescription,
          shortDescription: this.temp.shortDescription,
        }
      };

      request.post('card/addCard', JSON.stringify(postData)).then(response => {
        console.log("In response addCard");
        if (response.data) {
          // TODO: SHORTEN THE REQUESTS
          console.log("In addCard");
          _this.panelVisible = false;
          _this.getList(this.listQuery.page, this.listQuery.limit);
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
    },
    submitUpdate(){
      const _this = this;
      let postData = {
        cardId: this.temp.cardId,
        cardName: this.temp.cardName,
        rarity: this.temp.rarity,
        healthPoint: this.temp.healthPoint,
        attack: this.temp.attack,
        defense: this.temp.defense,
        attackRange: this.temp.attackRange,
        cd: this.temp.cd,
        speed: this.temp.speed,
        type: this.temp.type,
        cardDetails: {
          cardId: this.temp.cardId,
          cardImg: this.temp.cardImg,
          cardDescription: this.temp.cardDescription,
          shortDescription: this.temp.shortDescription,
        }
      };

      request.post('card/updateCard',JSON.stringify(postData)).then(response => {
        if(response.data) {
          _this.getList(this.listQuery.page, this.listQuery.limit);
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
    handleUpdate(row) {
      let _this = this;
      this.temp = Object.assign({}, row); // copy obj
      this.dialogStatus = 'update';
      this.panelVisible = true;
      this.$nextTick(() => {
        _this.$refs['temp'].clearValidate()
      })
    },
    updateData(formName) {
      this.$refs['temp'].validate((valid) => {
        if (valid) {
          this.submitUpdate();
        } else {
          this.$message.error('Form Invalid!');
          return false;
        }
      });
    },

    uploadCover() {
      const _this = this;
      var file = this.$refs.img;
      var reader = new FileReader();
      reader.readAsDataURL(file.files[0]);
      reader.onload = function() {
        _this.temp.cardImg = this.result;
        console.log("result");
        console.log(this.result);
      }
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

    handleFilter() {
      this.listQuery.page = 1;
      this.getList(this.listQuery.page, this.listQuery.limit);
    },
  }
}
</script>
