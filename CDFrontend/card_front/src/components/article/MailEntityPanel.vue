<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="search" placeholder="Title" style="width: 200px;" class="filter-card" />
    </div>

<!--    :data="list.filter(data => !search || data.mailName.toLowerCase().includes(search.toLowerCase()))"-->
    <el-table
      :key="tableKey"
      v-loading="listLoading"
      border
      fit
      :data="list"
      highlight-current-row
      style="width: 100%;"
      @sort-change="sortChange"
    >
      <el-table-column label="MailId" prop="mailId" sortable="custom" align="center" width="80" :class-name="getSortClass('id')">
        <template slot-scope="{row}">
          <span class="link-type" @click="handleUpdate(row)">{{ row.mailId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Name" min-width="150px">
        <template slot-scope="{row}">
          <span class="link-type" @click="handleUpdate(row)">{{ row.mailName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Description" min-width="150px">
        <template slot-scope="{row}">
          <span :id="row.mailId">{{ row.mailDescription }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Cover" min-width="150px">
        <template slot-scope="{row}">
          <el-image
            style="width: 100px; height: 100px"
            :src="row.mailImg"
          />
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="listQuery.total > 0" :total.sync="listQuery.total * listQuery.limit" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList(listQuery.page, listQuery.limit)" />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="panelVisible" top="5vh" class="editDialog">
      <MailUpdatePanel v-bind:update-content="temp" @getList="getList" />
    </el-dialog>

  </div>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import Pagination from '@/components/Pagination/index'
import axios from 'axios' // secondary package based on el-pagination;
import MailUpdatePanel from "@/components/article/MailUpdatePanel";
import request from "@/utils/request"; // secondary package based on el-pagination

export default {
  name: 'MailEntityPanel',
  components: {MailUpdatePanel, Pagination },
  directives: { waves },
  data() {
    return {
      search: '',
      mailImg: '',
      temp: {
        mailId: undefined,
        mailName: '',
        mailImg: '',
        mailDescription: ''
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
    watchList() {
      let list = this.list;
      for (const i in list) {
        const details = list[i].mailDetails;
        if (details === null) { continue }
        list[i].mailImg = details.mailImg;
        list[i].mailDescription = details.mailDescription
      }
      this.list = list;
    },
    getList(page, limit) {
      let postData = {
        pageToken: page,
        pageSize: limit
      };
      request.post( 'mail/List', postData).then(response => {
        if(response.data) {
          this.panelVisible = false;
          this.list = response.data.result;
          this.listQuery.total = response.data.totalPages;
          this.watchList()
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
