<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="search" placeholder="Title" style="width: 200px;" class="filter-card"/>
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
      <el-table-column label="UserId" prop="cardId" sortable="custom" align="center" width="80" :class-name="getSortClass('id')">
        <template slot-scope="{row}">
          <span>{{ row.userId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="CardId" width="150px" align="center">
        <template slot-scope="{row}">
          <span class="link-type" @click="handleUpdate(row)">{{ row.cardId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="AccquireDate" min-width="150px">
        <template slot-scope="{row}">
          <span>{{ row.accquireDate }}</span>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="panelVisible" top="5vh">
      <el-form ref="dataForm" :rules="rules" :model="temp" style="margin: auto 50px auto 50px; display:grid; grid-template-columns: 50% 50%; grid-column-gap: 10px" class="demo-form-inline">
        <el-form-item label="UserId" prop="userId">
          <el-input v-model="temp.userId" />
        </el-form-item>
        <el-form-item label="CardId" prop="cardId">
          <el-input v-model="temp.cardId" />
        </el-form-item>
        <el-form-item label="AccquireDate" prop="accquireDate">
          <el-input v-model="temp.accquireDate" />
        </el-form-item>
<!--        <div class="modalWrapper" style="display: grid; grid-template-columns: 50% 50%">-->
<!--          <el-image-->
<!--            style="width: 200px; height: 200px"-->
<!--            :src="temp.cardImg"-->
<!--            :fit='cardImg'></el-image>-->
<!--          <div class="coverControl">-->
<!--            <el-button type="primary" style="margin: 10px">上传<i class="el-icon-upload el-icon&#45;&#45;right"></i></el-button>-->
<!--            <input type="file" @change="uploadCover" ref="img" style="margin: 10px"/>-->
<!--          </div>-->
<!--        </div>-->
      </el-form>


      <div slot="footer" class="dialog-footer">
        <el-dialog
          title="Deletion Confirm"
          width="30%"
          :visible.sync="deleteVisible"
          append-to-body
        >
          <el-input placeholder="Identification" v-model="confirmPassword" show-password width="60%"></el-input>
          <el-button @click="confirmIdentity">Confirm Identity</el-button>

          <span slot="footer" class="dialog-footer">
            <el-button @click="deleteVisible = false">Cancel</el-button>
            <el-button type="danger" disabled v-if="confirmDelete === false">Delete</el-button>
            <el-button type="danger" @click="deleteData" v-else>Delete</el-button>
          </span>
        </el-dialog>


        <el-button @click="deleteVisible = true" type="danger">
          Delete
        </el-button>
        <el-button @click="panelVisible = false">
          Cancel
        </el-button>
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
          Confirm
        </el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
  import { fetchList, fetchPv, createArticle, updateArticle } from '@/api/article'
  import waves from '@/directive/waves' // waves directive
  import { parseTime } from '@/utils'
  import Pagination from '@/components/Pagination'
  import axios from 'axios' // secondary package based on el-pagination

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
      },
    },
    data() {
      return {
        search:'',
        temp: {
          ownCardId: undefined,
          userId: undefined,
          cardId: undefined,
          accquireDate: undefined
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
        dialogPvVisible: false,
        pvData: [],
        rules: {
          // type: [{ required: true, message: 'type is required', trigger: 'change' }],
          // timestamp: [{ type: 'date', required: true, message: 'timestamp is required', trigger: 'change' }],
          // title: [{ required: true, message: 'title is required', trigger: 'blur' }]
        },
        downloadLoading: false
      }
    },
    created() {
      this.getList();
    },
    watch:{
      deleteVisible(){
        this.confirmDelete = false;
        this.confirmPassword = '';
      }
    },
    methods: {
      watchList(){
        let list = this.list;
        for(let i in list)
        {
          let details = list[i].cardDetails;
          list.cardImg = details.cardImg;
          list.cardDescription = details.cardDescription;
          list.shortDescription = details.shortDescription;
        }
        this.list = list;
      },
      confirmIdentity(){
        // TODO: REQUEST --- PWD USR MATCH
        let postData = new FormData();
        let _this = this;
        postData.append('adminName', localStorage.getItem('AdminName'));
        postData.append('password', this.confirmPassword);
        axios.post('http://localhost:8080/admin/identifyAdmin', postData).then(response => {
          console.log(response);
          if(response.data)
          {
            _this.confirmDelete = true;
          }else{
            this.$message.error('Identification failed!');
          }
        })
      },
      deleteData(){
        let postData = new FormData();
        let _this = this;
        postData.append('userId', this.temp.userId);
        postData.append('cardId', this.temp.cardId);
        axios.post('http://localhost:8080/ownCard/deleteOwnCard', postData).then(response => {
          if(response.data)
          {
            _this.panelVisible = false;
            _this.deleteVisible = false;
            _this.getList();
          }else{
            this.$message.error('Identification failed!');
          }
        })
      },
      uploadCover(){
        let _this = this;
        var file = this.$refs.img;
        var reader = new FileReader();
        reader.readAsDataURL(file.files[0]);
        reader.onload = function () {
          _this.temp.cardImg = this.result;
        };
      },
      getList() {
        this.listLoading = true;
        axios.get("http://localhost:8080/ownCard/getAllOwnCards")
          .then(response =>
          {
            this.list = response.data;
            // this.watchList();
          });

        setTimeout(() => {
          this.listLoading = false
        }, 1.5 * 10)
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
          ownCardId: undefined,
          userId: undefined,
          cardId: undefined,
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
        console.log("Inside CreateData");
        let postData = new FormData();
        postData.append('cardId', this.temp.cardId);
        postData.append('userId', this.temp.userId);

        axios.post(`http://localhost:8080/ownCard/addOwnCard`, postData).then(response => {
          if(response.data) {
            // TODO: SHORTEN THE REQUESTS
            this.getList();
          }
          else {
            //
          }
        });
      },
      handleUpdate(row) {
        this.temp = Object.assign({}, row); // copy obj
        // this.temp.timestamp = new Date(this.temp.timestamp)
        this.dialogStatus = 'update';
        this.panelVisible = true;
        this.$nextTick(() => {
          this.$refs['dataForm'].clearValidate();
        })
      },
      updateData() {
        // let postData = new FormData();
        // postData.append('cardId', this.temp.cardId);
        // postData.append('userId', this.temp.userId);
        //
        // axios.post(`http://localhost:8080/ownCard/addOwnCard`, postData).then(response => {
        //   if(response.data) {
        //     // TODO: SHORTEN THE REQUESTS
        //     this.getList();
        //   }
        //   else {
        //     //
        //   }
        // });
      },
      handleDelete(row, index) {

      },
      getSortClass: function(key) {
        const sort = this.listQuery.sort;
        return sort === `+${key}` ? 'ascending' : 'descending'
      },
      handleFetchPv(pv) {
        fetchPv(pv).then(response => {
          this.pvData = response.data.pvData;
          this.dialogPvVisible = true
        })
      }
    }
  }
</script>
