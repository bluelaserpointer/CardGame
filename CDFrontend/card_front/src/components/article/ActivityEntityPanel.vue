<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="search" placeholder="Title" style="width: 200px;" class="filter-card" />
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" />
    </div>

<!--    :data="list.filter(data => !search || data.activityName.toLowerCase().includes(search.toLowerCase()))"-->
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
      <el-table-column label="ID" prop="id" sortable="custom" align="center" width="80" :class-name="getSortClass('id')">
        <template slot-scope="{row}">
          <span class="link-type" @click="handleUpdate(row)">{{ row.activityId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Name" min-width="150px">
        <template slot-scope="{row}">
          <span class="link-type" @click="handleUpdate(row)">{{ row.activityName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Description" min-width="150px">
        <template slot-scope="{row}">
          <span>{{ row.activityDescription }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Limited" min-width="150px">
        <template slot-scope="{row}">
          <span>{{ row.type }}</span>
        </template>
      </el-table-column>
      <el-table-column label="StartTime" min-width="150px">
        <template slot-scope="{row}">
          <span>{{ formatDate(row.start) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Cover" min-width="150px">
        <template slot-scope="{row}">
          <el-image
            style="width: 100px; height: 100px"
            :src="row.activityImg"
          />
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="listQuery.total > 0" :total.sync="listQuery.total * listQuery.limit" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList(listQuery.page, listQuery.limit)" />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="panelVisible" top="5vh" class="editDialog">
      <ActivityUpdatePanel v-bind:update-content="temp" @getList="getList" v-bind:list-query="listQuery"/>
    </el-dialog>

  </div>



</template>

<script>
import waves from '@/directive/waves' // waves directive
import Pagination from '@/components/Pagination/index'
import axios from 'axios'
import moment from "moment"; // secondary package based on el-pagination
import ActivityUpdatePanel from "@/components/article/ActivityUpdatePanel";
import request from "@/utils/request"; // secondary package based on el-pagination

export default {
  name: 'ActivityEntityPanel',
  components: {ActivityUpdatePanel, Pagination },
  directives: { waves },
  data() {
    return {
      search: '',
      temp: {
        activityId: undefined,
        activityName: '',
        activityImg: '',
        activityDescription: '',
        start: this.delayDate(7),
        type: false,
      },
      tableKey: 0,
      list: null,
      listLoading: false,
      listQuery: {
        page: 1,
        limit: 20,
        total: 0,
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
      downloadLoading: false
    }
  },
  created() {
    this.getList(1, this.listQuery.limit);
  },
  methods: {
    delayDate(days){
      let newDate = new Date();
      let showDate;
      for (let i = 1; i <= days; i++) { //后7天
        let date = newDate.getDate() < 10 ? '0' + newDate.getDate() : newDate.getDate();
        let yue = (newDate.getMonth() + 1) < 10 ? '0' + (newDate.getMonth() + 1) : (newDate.getMonth() + 1);
        showDate = newDate.getFullYear() + '-' + yue + '-' + date;
        newDate.setDate(newDate.getDate() + 1);
      }
      return showDate + ' 00:00:00';
    },
    formatDate(date){
      return moment(new Date(date)).format('YYYY-MM-DD HH:mm:ss');
    },
    watchList() {
      const list = this.list;
      for (const i in list) {
        const details = list[i].activityDetails;
        if (details === null) { continue }
        list[i].activityImg = details.activityImg;
        list[i].activityDescription = details.activityDescription;
      }
      this.list = list;
    },
    getList(page, limit) {
      let postData = {
        pageToken: page,
        pageSize: limit
      };
      request.post('activity/List', postData).then(response => {
        if(response.data) {
          this.panelVisible = false;
          this.list = response.data.result;
          this.listQuery.total = response.data.totalPages;
          this.watchList();
        }else
        {
          this.$message.error('Fetching Data Failed!');
        }
      })
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row); // copy obj
      this.dialogStatus = 'update';
      this.panelVisible = true;
    },

    handleFilter() {
      this.listQuery.page = 1;
      this.getList(this.listQuery.page, this.listQuery.limit);
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
