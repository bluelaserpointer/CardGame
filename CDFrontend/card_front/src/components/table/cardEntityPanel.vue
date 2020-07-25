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
// import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination/index'
// import axios from 'axios' // secondary package based on el-pagination
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
        speed: [{ required: true, message: 'Speed is required', trigger: 'change' }]
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
        url: '/card/getAllCards',
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
      // axios.get('http://localhost:8080/card/getAllCards')
      // .then(response => {
      //   if(response.data) {
      //     _this.list = response.data;
      //     _this.watchList();
      //   }else
      //   {
      //     this.$message.error('Fetching Data Failed!');
      //   }
      // })
      // .catch(error =>
      // {
      //   this.$message.error('Fetching Data Failed!');
      // });
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

      request({
        url: '/admin/identifyAdmin',
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

      // axios.post('http://localhost:8080/admin/identifyAdmin', postData)
      // .then(response => {
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
      const postData = new FormData();
      const _this = this;
      postData.append('cardId', this.temp.cardId);

      request({
        url: '/card/deleteCard',
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

      // axios.post('http://localhost:8080/card/deleteCard', postData).then(response => {
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
      let _this = this;
      this.resetTemp();
      this.dialogStatus = 'create';
      this.panelVisible = true;
      this.$nextTick(() => {
        _this.$refs.temp.clearValidate()
      })
    },
    createData(formName) {
      const _this = this;
      this.$refs.temp.validate((valid) => {
        if (valid) {
          const postData = new FormData();
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
          console.log("Within createData");

          request({
            url: '/card/addCard',
            method: 'post',
            data: postData
          }).then(response => {
            if (response.data) {
              // TODO: SHORTEN THE REQUESTS
              _this.getList();
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


      //     axios.post(`http://localhost:8080/card/addCard`, postData).then(response => {
      //       if (response.data) {
      //         // TODO: SHORTEN THE REQUESTS
      //         _this.getList();
      //         _this.panelVisible = false;
      //         _this.resetTemp();
      //       }else {
      //         this.$message.error('Creating Data failed!');
      //       }
      //     })
      //       .catch(error =>
      //         {
      //           this.$message.error('Creating Data failed!');
      //         }
      //       );
      //   } else {
      //     this.$message.error('Form Invalid!');
      //     return false;
      //   }
      // });


    },
    handleUpdate(row) {
      let _this = this;
      this.temp = Object.assign({}, row); // copy obj
      this.dialogStatus = 'update';
      this.panelVisible = true;
      this.$nextTick(() => {
        _this.$refs.temp.clearValidate()
      })
    },
    updateData(formName) {

      this.$refs.temp.validate((valid) => {
        if (valid) {
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

          request({
            url: '/card/updateCard',
            method: 'post',
            data: postData
          }).then(response => {
            if(response.data) {
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
        } else {
          this.$message.error('Form Invalid!');
          return false;
        }
      });


      //     axios.post(`http://localhost:8080/card/updateCard`, postData).then(response => {
      //       if(response.data) {
      //         _this.getList();
      //         _this.panelVisible = false;
      //         _this.resetTemp();
      //       }else {
      //         this.$message.error('Updating Data failed!');
      //       }
      //     })
      //       .catch(error =>
      //         {
      //           this.$message.error('Updating Data failed!');
      //         }
      //       );
      //   } else {
      //     this.$message.error('Form Invalid!');
      //     return false;
      //   }
      // });


    },

    uploadCover() {
      const _this = this;
      var file = this.$refs.img;
      var reader = new FileReader();
      reader.readAsDataURL(file.files[0]);
      reader.onload = function() {
        _this.temp.cardImg = this.result;
      }
    },  // untested
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

    // handleFilter() {
    //   this.listQuery.page = 1;
    //   this.getList()
    // },  // untested
    // handleModifyStatus(row, status) {
    //   this.$message({
    //     message: '操作Success',
    //     type: 'success'
    //   });
    //   row.status = status
    // },  // untested

  }
}
</script>
