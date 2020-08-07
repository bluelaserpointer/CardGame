<template>
  <div class="app-container" style="display: grid; grid-template-rows: 5% 95%;">
    <div class="filter-container" style="width: 100%; grid-row: 1 / span 1; display:grid; grid-template-columns: 50% 50%; grid-column-gap: 5px;">
      <div style="grid-column: 1 / span 2; display:grid; grid-template-columns: 50% 50%; grid-column-gap: 5px">
        <div style="display:grid; grid-template-columns: 50% 50%; grid-column-gap: 5px">
          <span style="margin-left: auto;" class="chapterSelectArea">Chapter:
            <el-select v-model="currChapter" class="chapterSelect" placeholder="请选择" @change="handleChangeChapter">
              <el-option-group class="chapterOption">
                <el-option
                  class="chapterSelectOption"
                  v-for="chapter in chapterList"
                  :key="chapter.chapterId"
                  :label="chapter.chapterId"
                  :value="chapter.chapterId"
                />
              </el-option-group>
            </el-select>
          </span>
          <span v-if="currChapter !== undefined" class="phaseSelectArea" style="margin-right: auto;">Phase:
            <el-select v-model="currPhase" class="phaseSelect" placeholder="请选择" @change="handleChangePhase">
              <el-option-group class="phaseOption">
                <el-option
                  class="phaseSelectOption"
                  v-for="phaseNo in currPhaseNo"
                  :key="phaseNo"
                  :label="phaseNo"
                  :value="phaseNo"
                />
              </el-option-group>
            </el-select>
          </span>
          <span v-else style="margin-right: auto;">Phase:
            <el-select v-model="currPhase" class="phaseSelect" placeholder="请选择" @change="handleChangePhase" disabled>
              <el-option-group class="phaseOption">
                <el-option
                  class="phaseSelectOption"
                  v-for="phaseNo in currPhaseNo"
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
          <el-button class="filter-item confirmButton" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleConfirm">
            Confirm
          </el-button>
          <el-button class="filter-item restoreButton" style="margin-left: 10px;" type="warning" @click="handleRefreshPhase(currPhase)">
            Restore
          </el-button>
        </div>
      </div>
    </div>

    <div style="display: grid; width: 100%; height: 100%; grid-template-columns: 50% 50%">
      <div style="display: grid; width: 100%; height: 100%; grid-column: 1 / span 1; grid-template-rows: 50% 50%">
        <div style="display: grid; grid-row: 1 / span 1; width: 100%; grid-template-columns: 50% 50%">
          <div>
            <el-table
            style="grid-column: 1 / span 1"
            :key="tableKey"
            v-loading="listLoading"
            :data="itemAwardList"
            class="itemAwardTable"
            border
            fit
            highlight-current-row
            height="350"
            max-height="350"
            @sort-change="sortChange"
          >
            <el-table-column label="ID" prop="itemId" sortable="custom" align="center" width="80" :class-name="getSortClass('id')">
              <template slot-scope="{row}">
                <span>{{ row.itemId }}</span>
              </template>
            </el-table-column>
            <el-table-column label="ItemName" width="80" align="center">
              <template slot-scope="{row}">
                <span class="link-type">{{ row.itemName }}</span>
              </template>
            </el-table-column>
            <el-table-column label="Phase" align="center">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  class="phaseAddItemButton"
                  @click="phaseAddItem(scope.$index, scope.row)">+</el-button>
                <el-button
                  size="mini"
                  class="phaseMinusItemButton"
                  @click="phaseMinusItem(scope.$index, scope.row)">-</el-button>
              </template>
            </el-table-column>
            <el-table-column label="Chapter" align="center">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  class="chapterAddItemButton"
                  @click="chapterAddItem(scope.$index, scope.row)">+</el-button>
                <el-button
                  size="mini"
                  class="chapterMinusItemButton"
                  @click="chapterMinusItem(scope.$index, scope.row)">-</el-button>
              </template>
            </el-table-column>
          </el-table>
            <pagination v-show="itemAwardListQuery.total > 0" :total.sync="itemAwardListQuery.total * itemAwardListQuery.limit" :page.sync="itemAwardListQuery.page" :limit.sync="itemAwardListQuery.limit" @pagination="getItemAwardList(itemAwardListQuery.page, itemAwardListQuery.limit)" />
          </div>
          <div>
            <el-table
            style="grid-column: 2 / span 1"
            :key="tableKey"
          v-loading="listLoading"
          :data="cardAwardList"
            class="cardAwardTable"
            border
          fit
          highlight-current-row
            height="350"
            max-height="350"
          @sort-change="sortChange"
        >
          <el-table-column label="ID" prop="cardId" sortable="custom" align="center" width="80" :class-name="getSortClass('id')">
            <template slot-scope="{row}">
              <span>{{ row.cardId }}</span>
            </template>
          </el-table-column>
          <el-table-column label="CardName" width="80" align="center">
            <template slot-scope="{row}">
              <span>{{ row.cardName }}</span>
            </template>
          </el-table-column>
          <el-table-column label="Phase" align="center">
            <template slot-scope="scope">
              <el-button
                size="mini"
                class="phaseAddCardButton"
                @click="phaseAddCard(scope.$index, scope.row)">+</el-button>
              <el-button
                size="mini"
                class="phaseMinusCardButton"
                @click="phaseMinusCard(scope.$index, scope.row)">-</el-button>
            </template>
          </el-table-column>
          <el-table-column label="Chapter" align="center">
            <template slot-scope="scope">
              <el-button
                size="mini"
                class="chapterAddCardButton"
                @click="chapterAddCard(scope.$index, scope.row)">+</el-button>
              <el-button
                size="mini"
                class="chapterMinusCardButton"
                @click="chapterMinusCard(scope.$index, scope.row)">-</el-button>
            </template>
          </el-table-column>
        </el-table>
            <pagination v-show="cardAwardListQuery.total > 0" :total.sync="cardAwardListQuery.total * cardAwardListQuery.limit" :page.sync="cardAwardListQuery.page" :limit.sync="cardAwardListQuery.limit" @pagination="getCardAwardList(cardAwardListQuery.page, cardAwardListQuery.limit)" />
          </div>
        </div>

        <div style="display: grid; grid-row: 2 / span 1; width: 100%; height: 100%; grid-template-columns: 50% 50%">
          <div style="display: grid; height: 100%; grid-template-columns: 50% 50%">
            <div>
              <el-table
              :key="tableKey"
              v-loading="listLoading"
              :data="phaseAwardItems"
              class="phaseAwardItemsTable"
              border
              fit
              highlight-current-row
              style="width: 100%; grid-column: 1 / span 1"
              height="350"
              max-height="350"
              @sort-change="sortChange"
            >
              <el-table-column label="ItemId" prop="itemId" sortable="custom" align="center" width="130" :class-name="getSortClass('id')">
                <template slot-scope="{row}">
                  <span>{{ row.itemId }}</span>
                </template>
              </el-table-column>
              <el-table-column label="Qty" align="center">
                <template slot-scope="{row}">
                  <span class="link-type">{{ row.quantity }}</span>
                </template>
              </el-table-column>
            </el-table>
            </div>
            <div>
              <el-table
              :key="tableKey"
              v-loading="listLoading"
              :data="phaseAwardCards"
              class="phaseAwardCardsTable"
              border
              fit
              highlight-current-row
              style="width: 100%; grid-column: 2 / span 1"
              height="350"
              max-height="350"
              @sort-change="sortChange"
            >
              <el-table-column label="CardId" prop="itemId" sortable="custom" align="center" :class-name="getSortClass('id')">
                <template slot-scope="{row}">
                  <span>{{ row }}</span>
                </template>
              </el-table-column>
            </el-table>
            </div>
          </div>
          <div style="display: grid; height: 100%; grid-template-columns: 50% 50%">
            <div>
              <el-table
              :key="tableKey"
              v-loading="listLoading"
              :data="chapterAwardItems"
              class="chapterAwardItemsTable"
              border
              fit
              highlight-current-row
              style="width: 100%; grid-column: 1 / span 1"
              height="350"
              max-height="350"
              @sort-change="sortChange"
            >
              <el-table-column label="ItemId" prop="itemId" sortable="custom" align="center" width="130" :class-name="getSortClass('id')">
                <template slot-scope="{row}">
                  <span>{{ row.itemId }}</span>
                </template>
              </el-table-column>
              <el-table-column label="Qty" align="center">
                <template slot-scope="{row}">
                  <span class="link-type">{{ row.quantity }}</span>
                </template>
              </el-table-column>
            </el-table>
            </div>
            <div>
              <el-table
          :key="tableKey"
          v-loading="listLoading"
          :data="chapterAwardCards"
          class="chapterAwardCardsTable"
          border
          fit
          highlight-current-row
          style="width: 100%; grid-column: 2 / span 1"
          height="350"
          max-height="350"
          @sort-change="sortChange"
        >
          <el-table-column label="CardId" prop="itemId" sortable="custom" align="center" :class-name="getSortClass('id')">
            <template slot-scope="{row}">
              <span>{{ row }}</span>
            </template>
          </el-table-column>
        </el-table>
            </div>
          </div>
        </div>
        <div style="display: grid; grid-template-columns: 50% 50%">
          <el-button class="confirmPhaseAwardButton" @click="handlePhaseAwardConfirm">Confirm Phase</el-button>
          <el-button class="confirmChapterAwardButton" @click="handleChapterAwardConfirm">Confirm Chapter</el-button>
        </div>
      </div>

      <div style="display: grid; width: 100%; height: 100%; grid-column: 2 / span 1; grid-template-rows: 50% 50%">
        <div style="margin: auto; width:350px; display: grid; grid-row: 1 / span 1; grid-template-columns: auto auto auto auto auto; grid-template-rows: auto auto auto auto auto;">
          <div v-for="pos in 25">
            <el-button :style="{'background':(posList.includes(pos) ? '#85b2dc' : '#fff'), 'color': (posList.includes(pos) ? '#fff' : '#000'), 'width': '80px'}" @click="placeCard(pos)">{{ pos }}</el-button>
          </div>
        </div>
        <div>
          <el-table
          :key="tableKey"
          v-loading="listLoading"
          :data="cardList"
          border
          fit
          highlight-current-row
          style="margin-right:auto; width: 100%; grid-row: 2 / span 1"
          height="350"
          max-height="350"
          @current-change="handleCardChoose"
          @sort-change="sortChange"
        >
          <el-table-column label="ID" prop="cardId" sortable="custom" align="center" width="80" :class-name="getSortClass('id')">
            <template slot-scope="{row}">
              <span>{{ row.cardId }}</span>
            </template>
          </el-table-column>
          <el-table-column label="CardName" width="150" align="center">
            <template slot-scope="{row}">
              <span>{{ row.cardName }}</span>
            </template>
          </el-table-column>
          <el-table-column label="HP" width="80" align="center">
            <template slot-scope="{row}">
              <span>{{ row.healthPoint }}</span>
            </template>
          </el-table-column>
          <el-table-column label="ATK" width="80" align="center">
            <template slot-scope="{row}">
              <span>{{ row.attack }}</span>
            </template>
          </el-table-column>
          <el-table-column label="DEF" align="center" width="80">
            <template slot-scope="{row}">
              <span>{{ row.defense }}</span>
            </template>
          </el-table-column>
          <el-table-column label="RANGE" class-name="status-col" width="80" align="center">
            <template slot-scope="{row}">
              <span>{{ row.attackRange }}</span>
            </template>
          </el-table-column>
          <el-table-column label="CD" class-name="status-col" width="80" align="center">
            <template slot-scope="{row}">
              <span>{{ row.cd }}</span>
            </template>
          </el-table-column>
          <el-table-column label="SPD" class-name="status-col" width="80" align="center">
            <template slot-scope="{row}">
              <span>{{ row.speed }}</span>
            </template>
          </el-table-column>
          <el-table-column label="Type" class-name="status-col" width="100">
            <template slot-scope="{row}">
              <span>{{ row.type }}</span>
            </template>
          </el-table-column>
          <el-table-column label="Cover" min-width="100px">
            <template slot-scope="{row}">
              <el-image
                style="width: auto; height: auto"
                :src="row.cardImg"
              />
            </template>
          </el-table-column>
        </el-table>
          <pagination v-show="cardListQuery.total > 0" :total.sync="cardListQuery.total * cardListQuery.limit" :page.sync="cardListQuery.page" :limit.sync="cardListQuery.limit" @pagination="getCardList(cardListQuery.page, cardListQuery.limit)" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import Pagination from '@/components/Pagination/index'
import axios from 'axios' // secondary package based on el-pagination
import request from '@/utils/request'

export default {
  name: 'PhaseEditPanel',
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
      cardAwardList: null,
      itemAwardList: null,
      cardList: null,
      chapterList: null,
      phaseAwardItems: null,
      phaseAwardCards: null,
      chapterAwardItems: null,
      chapterAwardCards: null,

      posMap: new Map(),
      posList: [],
      prevList: [],
      currChapter: undefined,
      currPhase: undefined,
      currPhaseNo: undefined,
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
        total: 0,
        sort: '+id'
      },
      cardListQuery: {
        page: 1,
        limit: 20,
        total: 0,
        sort: '+id'
      },
      itemAwardListQuery:{
        page: 1,
        limit: 20,
        total: 0,
        sort: '+id'
      },
      cardAwardListQuery:{
        page: 1,
        limit: 20,
        total: 0,
        sort: '+id'
      },
      importanceOptions: [1, 2, 3],
      sortOptions: [{ label: 'ID Ascending', key: '+id' }, { label: 'ID Descending', key: '-id' }],
      statusOptions: ['published', 'draft', 'deleted'],
      showReviewer: false,
      dialogStatus: '',
      textMap: {
        update: 'Edit',
        create: 'Create'
      },
    }
  },
  watch: {
    currPhase(val) {
      this.prevPhase = val;
      this.handleRefreshPhase(val)
    },
    currChapter(val) {
      this.prevChapter = val
    }

  },
  created() {
    this.getList();
  },
  methods: {
    phaseAddItem(index, row){
      for(let i in this.phaseAwardItems)
      {
        if(this.phaseAwardItems[i].itemId === row.itemId)
        {
          this.phaseAwardItems[i].quantity++;
          return;
        }
      }
      let tmp = {
        "itemId": parseInt(row.itemId),
        "quantity": 1
      };
      this.phaseAwardItems.push(tmp);
    },
    phaseMinusItem(index, row){
      for(let i in this.phaseAwardItems)
      {
        if(this.phaseAwardItems[i].itemId === row.itemId)
        {
          if(this.phaseAwardItems[i].quantity === 1)
            this.phaseAwardItems.splice(i, 1);
          else
            this.phaseAwardItems[i].quantity--;
          return;
        }
      }
    },

    phaseAddCard(index, row){
      this.phaseAwardCards.push(row.cardId);
    },
    phaseMinusCard(index, row){
      for(let i in this.phaseAwardCards)
      {
        if(this.phaseAwardCards[i] === row.cardId)
        {
          this.phaseAwardCards.splice(i, 1);
          console.log("Splice");
          break;
        }
      }
    },

    chapterAddItem(index, row){
      for(let i in this.chapterAwardItems)
      {
        if(this.chapterAwardItems[i].itemId === row.itemId)
        {
          this.chapterAwardItems[i].quantity++;
          return;
        }
      }
      let tmp = {
        "itemId": parseInt(row.itemId),
        "quantity": 1
      };
      this.chapterAwardItems.push(tmp);
    },
    chapterMinusItem(index, row){
      for(let i in this.chapterAwardItems)
      {
        if(this.chapterAwardItems[i].itemId === row.itemId)
        {
          if(this.chapterAwardItems[i].quantity === 1)
            this.chapterAwardItems.splice(i, 1);
          else
            this.chapterAwardItems[i].quantity--;
          return;
        }
      }
    },

    chapterAddCard(index, row){
      this.chapterAwardCards.push(row.cardId);
    },
    chapterMinusCard(index, row){
      for(let i in this.chapterAwardCards)
      {
        if(this.chapterAwardCards[i] === row.cardId)
        {
          this.chapterAwardCards.splice(i, 1);
          break;
        }
      }
    },


    transObjToArr(obj)
    {
      let arr = [];
      for(let key in obj) {
        let tmp = {
          "itemId": parseInt(key),
          "quantity": obj[key]
        };
        arr.push(tmp);
      }
      return arr;
    },

    handleRefreshPhase(val) {
      this.confirmed = true;
      this.placedCard = false;
      this.posList = [];
      let chapterData = this.chapterData;
      for (let i in chapterData) {
        if (chapterData[i].phaseId === val) {
          this.posList.push(chapterData[i].positionId);
          this.posMap.set(chapterData[i].positionId, chapterData[i].cardId)
        }
      }

      let chapterPhaseData = this.chapterPhaseData;
      let foundFlag = false;
      for(let i in chapterPhaseData)
      {
        if(chapterPhaseData[i].phaseId === val){
          foundFlag = true;
          console.log("flag");
          console.log(chapterPhaseData[i].awardCards);
          this.phaseAwardItems = this.transObjToArr(chapterPhaseData[i].awardItems);
          this.phaseAwardCards = Array.from(chapterPhaseData[i].awardCards);
          break;
        }
      }

      if(!foundFlag)
      {
        this.phaseAwardCards = [];
        this.phaseAwardItems = [];
      }

    },

    handleChangeChapter() {
      this.handleChange();
      let postData = new FormData();
      let _this = this;
      postData.append('chapterId', this.currChapter);

      console.log("Inside handleChangeChapter");
      console.log(this.chapterList);
      for(let i in this.chapterList)
      {
        if(this.chapterList[i].chapterId === this.currChapter)
        {
          this.currPhaseNo = this.chapterList[i].phaseNo;
          this.chapterAwardItems = this.transObjToArr(this.chapterList[i].awardItems);
          this.chapterAwardCards = Array.from(this.chapterList[i].awardCards);
        }
      }

      request({
        url: 'chapter/getChapterDetailsByChapter',
        method: 'post',
        data: postData
      }).then(response => {
        if (response.data) {

          _this.chapterData = response.data;

          request({
            url: 'chapter/getChapterPhasesByChapter',
            method: 'post',
            data: postData
          }).then(res => {
            console.log(res.data);
            _this.chapterPhaseData = res.data;
            _this.handleRefreshPhase(1);
          })

        } else {
          // this.$message.error('Fetching Data Failed!');
        }
      }).catch(error => {
        this.$message.error('Fetching ChapterDetails Failed!');
      });


      // axios.post('http://localhost:8080/chapter/getChapterDetailsByChapter', postData).then(response => {
      //   if (response.data) {
      //
      //     _this.chapterData = response.data;
      //     // console.log(response.data);
      //     axios.post('http://localhost:8080/chapter/getChapterPhasesByChapter', postData).then(res => {
      //       console.log(res.data);
      //         _this.chapterPhaseData = res.data;
      //         _this.handleRefreshPhase(1);
      //     })
      //     // .catch(error => {
      //     //   this.$message.error('Fetching ChapterPhases Failed!');
      //     // });
      //
      //   }else{
      //     // this.$message.error('Fetching Data Failed!');
      //   }
      // })
      // .catch(error => {
      //   this.$message.error('Fetching ChapterDetails Failed!');
      // });


    },

    handleChangePhase() {
      this.handleChange()
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
        this.posList.push(pos);
      }
    },
    handleCardChoose(val) {
      this.currCard = val.cardId
    },



    handleConfirm() {
      let chapterPhaseData = Array.from(this.posMap);
      let postData = new FormData();
      let _this = this;
      postData.append('chapterId', this.currChapter);
      postData.append('phaseId', this.currPhase);
      postData.append('phaseData', JSON.stringify(chapterPhaseData));

      request({
        url: 'chapter/updateChapte',
        method: 'post',
        data: postData
      }).then(response => {
        if (response.data) {
          _this.chapterData = response.data
        }else{
          this.$message.error('Updating Data Failed!');
        }
        this.handleRefreshPhase(this.currPhase)
      })
        .catch(error => {
          this.$message.error('Updating Data Failed!');
        })

      // axios.post(`http://localhost:8080/chapter/updateChapter`, postData).then(response => {
      //   if (response.data) {
      //     _this.chapterData = response.data
      //   }else{
      //     this.$message.error('Updating Data Failed!');
      //   }
      //   this.handleRefreshPhase(this.currPhase)
      // })
      //   .catch(error => {
      //     this.$message.error('Updating Data Failed!');
      //   })
    },

    transObjToMap(arr)
    {
      let map = new Map();
      for(let i in arr)
        map.set(arr[i].itemId, arr[i].quantity);
      return Array.from(map);
    },

    handleChapterAwardConfirm(){
      if(this.currChapter === undefined) {
        this.$message.error('Chapter is undefined!');
        return;
      }

      let postData = new FormData();

      postData.append('chapterId', this.currChapter);
      postData.append('awardItems', JSON.stringify(this.transObjToMap(this.chapterAwardItems)));
      postData.append('awardCards', JSON.stringify(this.chapterAwardCards));

      request({
        url: 'chapter/updateChapterAwards',
        method: 'post',
        data: postData
      }).then(response => {
        if (response.data) {
          this.chapterList = response.data;
        }else{
          this.$message.error('Updating Data Failed!');
        }
        this.handleRefreshPhase(this.currPhase)
      })
        .catch(error => {
          this.$message.error('Updating Data Failed!');
        });
    },

    handlePhaseAwardConfirm(){
      if(this.currChapter === undefined || this.currPhase === undefined) {
        this.$message.error('Chapter or phase is undefined!');
        return;
      }

      let postData = new FormData();

      postData.append('chapterId', this.currChapter);
      postData.append('phaseId', this.currPhase);
      postData.append('awardItems', JSON.stringify(this.transObjToMap(this.phaseAwardItems)));
      postData.append('awardCards', JSON.stringify(this.phaseAwardCards));


      request({
        url: 'chapter/updateChapterPhaseAwards',
        method: 'post',
        data: postData
      }).then(response => {
        if (response.data) {
          this.chapterPhaseData = response.data;
        }else{
          this.$message.error('Updating Data Failed!');
        }
        this.handleRefreshPhase(this.currPhase)
      })
        .catch(error => {
          this.$message.error('Updating Data Failed!');
        })
    },

    getItemAwardList(page, limit) {
      let postData = {
        pageToken: page,
        pageSize: limit
      };
      request({
        url: 'item/List',
        method: 'post',
        data: postData
      }).then(res => {
        if(res.data) {
          this.itemAwardList = res.data.result;
          this.itemAwardListQuery.total = res.data.totalPages;
          this.watchList()
        }else
        {
          this.$message.error('Fetching Data Failed!');
        }
      })
    },


    getCardAwardList(page, limit) {
      let postData = {
        pageToken: page,
        pageSize: limit
      };
      request({
        url: 'card/List',
        method: 'post',
        data: postData
      }).then(res => {
        if(res.data) {
          this.cardAwardList = res.data.result;
          this.cardAwardListQuery.total = res.data.totalPages;
          this.watchList()
        }else
        {
          this.$message.error('Fetching Data Failed!');
        }
      })
    },

    getCardList(page, limit) {
      let postData = {
        pageToken: page,
        pageSize: limit
      };
      request({
        url: 'card/List',
        method: 'post',
        data: postData
      }).then(res => {
        if(res.data) {
          this.cardList = res.data.result;
          this.cardListQuery.total = res.data.totalPages;
          this.watchList()
        }else
        {
          this.$message.error('Fetching Data Failed!');
        }
      })
    },

    getList() {
      request({
        url: 'chapter/getAllChapters',
        method: 'get',
      }).then(response => {
        if(response.data)
        {
          this.chapterList = response.data;
          this.getItemAwardList(this.itemAwardListQuery.page, this.itemAwardListQuery.limit);
          this.getCardAwardList(this.cardAwardListQuery.page, this.cardAwardListQuery.limit);
          this.getCardList(this.cardListQuery.page, this.cardListQuery.limit);
        }else{
          this.$message.error('Fetching Data Failed!');
        }
      })
    },

    // handleFilter() {
    //   this.listQuery.page = 1;
    //   this.getList();
    // },
    sortChange(data) {
      // const { prop, order } = data;
      // if (prop === 'id') {
      //   this.sortByID(order)
      // }
    },
    // sortByID(order) {
    //   if (order === 'ascending') {
    //     this.listQuery.sort = '+id'
    //   } else {
    //     this.listQuery.sort = '-id'
    //   }
    //   this.handleFilter()
    // },
    getSortClass: function(key) {
      // const sort = this.listQuery.sort;
      // return sort === `+${key}` ? 'ascending' : 'descending'
    },
  }
}
</script>
