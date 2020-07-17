<template>
  <div class="app-container" style="display: grid; grid-template-rows: 20% 80%; grid-template-columns: 50% 50%">
    <div class="filter-container" style="width: 100%; grid-row: 1 / span 1; grid-column: 1 / span 2; display:grid; grid-template-columns: 50% 50%; grid-column-gap: 5px">
      <div style="grid-column: 1 / span 2; display:grid; grid-template-columns: 50% 50%; grid-column-gap: 5px">
        <div style="display:grid; grid-template-columns: 50% 50%; grid-column-gap: 5px">
          <span style="margin-left: auto;">Chapter:
            <el-select v-model="currChapter" placeholder="请选择" @change="handleChangeChapter">
              <el-option-group>
                <el-option
                  v-for="chapter in chapterList"
                  :key="chapter.chapterId"
                  :label="chapter.chapterId"
                  :value="chapter.chapterId"
                />
              </el-option-group>
            </el-select>
          </span>
          <span v-if="currChapter !== undefined" style="margin-right: auto;">Phase:
            <el-select v-model="currPhase" placeholder="请选择" @change="handleChangePhase">
              <el-option-group>
                <el-option
                  v-for="phaseNo in chapterList[currChapter-1].phaseNo"
                  :key="phaseNo"
                  :label="phaseNo"
                  :value="phaseNo"
                />
              </el-option-group>
            </el-select>
          </span>
          <span v-else style="margin-right: auto;" />
        </div>
        <div>
          <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleConfirm">
            Confirm
          </el-button>
          <el-button class="filter-item" style="margin-left: 10px;" type="warning" @click="handleRefreshPhase(currPhase)">
            Restore
          </el-button>
        </div>
      </div>
    </div>

    <div style="margin: auto; width:400px; display: grid; grid-template-columns: auto auto auto auto auto; grid-template-rows: auto auto auto auto auto;">
      <div v-for="pos in 25">
        <el-button :style="{'background':(posList.includes(pos) ? '#85b2dc' : '#fff'), 'color': (posList.includes(pos) ? '#fff' : '#000'), 'width': '80px'}" @click="placeCard(pos)">{{ pos }}</el-button>
      </div>
    </div>
    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="cardList"
      border
      fit
      highlight-current-row
      style="margin-right:auto; width: auto; grid-row: 2 / span 1; grid-column: 2 / span 1"
      @current-change="handleCardChoose"
      @sort-change="sortChange"
    >
      <el-table-column label="ID" prop="cardId" sortable="custom" align="center" width="90" :class-name="getSortClass('id')">
        <template slot-scope="{row}">
          <span>{{ row.cardId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="CardName" width="90" align="center">
        <template slot-scope="{row}">
          <span>{{ row.cardName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="HP" width="90" align="center">
        <template slot-scope="{row}">
          <span>{{ row.healthPoint }}</span>
        </template>
      </el-table-column>
      <el-table-column label="ATK" width="90">
        <template slot-scope="{row}">
          <span>{{ row.attack }}</span>
        </template>
      </el-table-column>
      <el-table-column label="DEF" align="center" width="90">
        <template slot-scope="{row}">
          <span>{{ row.defense }}</span>
        </template>
      </el-table-column>
      <el-table-column label="RANGE" class-name="status-col" width="90">
        <template slot-scope="{row}">
          <span>{{ row.attackRange }}</span>
        </template>
      </el-table-column>
      <el-table-column label="CD" class-name="status-col" width="90">
        <template slot-scope="{row}">
          <span>{{ row.cd }}</span>
        </template>
      </el-table-column>
      <el-table-column label="SPD" class-name="status-col" width="90">
        <template slot-scope="{row}">
          <span>{{ row.speed }}</span>
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
  </div>
</template>

<script>
import { fetchList, fetchPv, createArticle, updateArticle } from '@/api/article'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination/index'
import axios from 'axios' // secondary package based on el-pagination

export default {
  name: 'PhaseEntityPanel',
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
      mapData: null,
      cardList: null,
      chapterList: null,
      posMap: new Map(),
      posList: [],
      prevList: [],
      currChapter: undefined,
      currPhase: undefined,
      prevChapter: undefined,
      prevPhase: undefined,
      currCard: undefined,
      switchedChapter: true,
      confirmed: true,
      placedCard: false,
      tableKey: 0,
      total: 0,
      listLoading: false,
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
      dialogFormVisible: false,
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
  watch: {
    currPhase(val) {
      this.prevPhase = val;
      this.handleRefreshPhase(val)
      // this.prevList = this.posList;
    },
    currChapter(val) {
      this.prevChapter = val
    }

  },
  created() {
    this.getList()
  },
  methods: {
    watchList() {
      const list = this.cardList;
      for (const i in list) {
        const details = list[i].cardDetails;
        list[i].cardImg = details.cardImg;
        list[i].cardDescription = details.cardDescription;
        list[i].shortDescription = details.shortDescription
      }
      console.log(list);
      this.cardList = list
    },
    handleRefreshPhase(val) {
      this.confirmed = true;
      this.placedCard = false;
      this.posList = [];
      const chapterData = this.chapterData;
      for (const i in chapterData) {
        if (chapterData[i].phaseId === val) {
          this.posList.push(chapterData[i].positionId);
          this.posMap.set(chapterData[i].positionId, chapterData[i].cardId)
        }
      }
    },
    handleConfirm() {
      // TODO: Popup
      // TODO: Send Request
      // TODO: Clear all the necessary attributes & Able to select another phase/chapter without asking
      const chapterPhaseData = Array.from(this.posMap);
      const postData = new FormData();
      const _this = this;
      postData.append('chapterId', this.currChapter);
      postData.append('phaseId', this.currPhase);
      postData.append('phaseData', JSON.stringify(chapterPhaseData));
      axios.post(`http://localhost:8080/chapter/updateChapter`, postData).then(response => {
        if (response.data) {
          _this.chapterData = response.data
        }
        this.handleRefreshPhase(this.currPhase)
      })
    },
    handleChangePhase() {
      this.handleChange()
      // TODO: GET PHASE
    },
    handleChangeChapter() {
      this.handleChange();

      // TODO: GET CHAPTER
      const postData = new FormData();
      const _this = this;
      postData.append('chapterId', this.currChapter);
      axios.post('http://localhost:8080/chapter/getChapterDetailsByChapter', postData).then(response => {
        if (response.data) {
          _this.chapterData = response.data;
          _this.handleRefreshPhase(1)
        }
      })
    },
    handleChange() {
      if (!this.placedCard) {
        return
      }
      if (this.confirmed || this.posList.length === 0) {
        this.$message({
          message: 'Choices confirmed!',
          type: 'success'
        })
      } else {
        this.currPhase = this.prevPhase;
        this.currChapter = this.prevChapter;
        this.$message.error('Choices not yet confirmed!');
        return
      }
      if (this.currPhase !== undefined && this.posList.length > 0) { this.confirmed = false }
    },
    placeCard(pos) {
      if (this.currChapter === undefined || this.currPhase === undefined || this.currCard === undefined) {
        this.$message.error('Choices unspecified!');
        return
      }
      this.confirmed = false;
      this.placedCard = true;
      const currCard = parseInt(this.currCard);
      const index = this.posList.indexOf(pos);
      if (this.posList.indexOf(pos) >= 0) {
        this.posMap.delete(pos);
        this.posList.splice(index, 1)
      } else {
        this.posMap.set(pos, currCard);
        this.posList.push(pos)
      }
    },
    uploadCover() {
      const _this = this;
      // 根据ref得到图片文件
      var file = this.$refs.img;
      // 使用h5的读取文件api
      var reader = new FileReader();
      reader.readAsDataURL(file.files[0]);
      // 读取完成后触发
      reader.onload = function() {
        // 改变img的路径
        _this.temp.cardImg = this.result;
        console.log('In onload')
      }
    },
    getList() {
      this.listLoading = true;
      axios.get('http://localhost:8080/chapter/getAllChapters').then(response => {
        this.chapterList = response.data;
        axios.get('http://localhost:8080/card/getAllCards')
          .then(res => {
            this.cardList = res.data;
            this.watchList()
          })
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
    createData() {
      //
      // let postData = new FormData();
      // // postData.append('cardId', this.temp.id);
      // postData.append('cardName', this.temp.cardName);
      // postData.append('rarity', this.temp.price);
      // postData.append('healthPoint', this.temp.healthPoint);
      // postData.append('attack', this.temp.attack);
      // postData.append('defense', this.temp.defense);
      // postData.append('attackRange', this.temp.attackRange);
      // postData.append('cd', this.temp.cd);
      // postData.append('speed', this.temp.speed);
      //
      // postData.append('cardImg', this.temp.cardImg);
      // postData.append('cardDescription', this.temp.cardDescription);
      //
      //
      // console.log(postData);
      //
      // axios.post(`http://localhost:8080/card/add`, postData).then(response => {
      //   if(response.data) {
      //     //
      //   }
      //   else {
      //     //
      //   }
      //   axios.get('http://localhost:8080/card/get/all')
      //     .then(response => this.cardList = response.data);
      // })

    },
    handleCardChoose(val) {
      this.currCard = val.cardId
    },
    updateData() {
      // let postData = new FormData();
      // postData.append('cardId', this.temp.id);
      // postData.append('cardName', this.temp.cardName);
      // postData.append('rarity', this.temp.price);
      // postData.append('healthPoint', this.temp.healthPoint);
      // postData.append('attack', this.temp.attack);
      // postData.append('defense', this.temp.defense);
      // postData.append('attackRange', this.temp.attackRange);
      // postData.append('cd', this.temp.cd);
      // postData.append('speed', this.temp.speed);
      //
      // postData.append('cardImg', this.temp.cardImg);
      // postData.append('cardDescription', this.temp.cardDescription);
      //
      // axios.post(`http://localhost:8080/card/update`, postData).then(response => {
      //   if(response.data) {
      //     //
      //   }
      //   else {
      //     //
      //   }
      //   axios.get('http://localhost:8080/card/get/all')
      //     .then(response => this.cardList = response.data);
      // })
    },
    handleDelete(row, index) {
      this.$notify({
        title: 'Success',
        message: 'Delete Successfully',
        type: 'success',
        duration: 2000
      });
      this.cardList.splice(index, 1)
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
