<template>
  <div class="dashboard-editor-container">
    <!--    <span>hi</span>-->
    <github-corner class="github-corner" />
    <panel-group @handleSetLineChartData="handleSetLineChartData" :curr-data="currData"/>

    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <line-chart :chart-data="lineChartData" />
    </el-row>

    <el-row :gutter="32">
      <!--      <el-col :xs="24" :sm="24" :lg="8">-->
      <!--        <div class="chart-wrapper">-->
      <!--          <raddar-chart />-->
      <!--        </div>-->
      <!--      </el-col>-->
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <pie-chart />
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <bar-chart />
        </div>
      </el-col>
    </el-row>

    <!--    <el-row :gutter="8">-->
    <!--      <el-col :xs="{span: 24}" :sm="{span: 24}" :md="{span: 24}" :lg="{span: 12}" :xl="{span: 12}" style="padding-right:8px;margin-bottom:30px;">-->
    <!--        <transaction-table />-->
    <!--      </el-col>-->
    <!--      <el-col :xs="{span: 24}" :sm="{span: 12}" :md="{span: 12}" :lg="{span: 6}" :xl="{span: 6}" style="margin-bottom:30px;">-->
    <!--        <todo-list />-->
    <!--      </el-col>-->
    <!--      <el-col :xs="{span: 24}" :sm="{span: 12}" :md="{span: 12}" :lg="{span: 6}" :xl="{span: 6}" style="margin-bottom:30px;">-->
    <!--        <box-card />-->
    <!--      </el-col>-->
    <!--    </el-row>-->
  </div>
</template>

<script>
  import PanelGroup from './components/PanelGroup'
  import LineChart from './components/LineChart'
  import PieChart from './components/PieChart'
  import BarChart from './components/BarChart'
  import request from "@/utils/request"; // secondary package based on el-pagination
  import moment from "moment"

  // import RaddarChart from './components/RaddarChart'
  // import GithubCorner from '@/components/GithubCorner'
  // import TransactionTable from './components/TransactionTable'
  // import TodoList from './components/TodoList'
  // import BoxCard from './components/BoxCard'
  // import DragTable from '../../table/drag-table'

let lineChartData = {
  onlineCounts: {
    actualData: [100, 100, 100, 100, 100, 100, 100],
    actualAxis: ['', '', '', '', '', '', '']
  },
  purchases: {
    actualData: [100, 100, 100, 100, 100, 100, 100],
    actualAxis: ['', '', '', '', '', '', '']
  },
  crashReports: {
    actualData: [100, 100, 100, 100, 100, 100, 100],
    actualAxis: ['', '', '', '', '', '', '']
  }
};

export default {
  name: 'DashboardAdmin',
  components: {
    PanelGroup,
    LineChart,
    PieChart,
    BarChart
    // DragTable,
    // GithubCorner,
    // RaddarChart,
    // TransactionTable,
    // TodoList,
    // BoxCard
  },
  data() {
    return {
      lineChartData: lineChartData.onlineCounts,
      currData: {
        onlineCount: 0,
        purchase: 0,
        crashReport: 0,
      },
      onlineCountId: null,
      purchaseId: null,
      crashReportId: null,
    }
  },
  created(){
      this.handleOnlineCountsFetch('day');
      this.handleScheduled();
  },
  methods: {
    handleScheduled(){
      this.onlineCountId = setInterval(this.handleOnlineCountFetch, 30000);
    },
    handleOnlineCountFetch(){
      request({
        url: 'record/onlineCountRecord/getOnlineCountRecord',
        method: 'get'
      }).then(response => {
        this.currData.onlineCount = response.data.onlineCount;
        lineChartData.onlineCounts.actualData.push(response.data.onlineCount);
        lineChartData.onlineCounts.actualAxis.push(moment(response.data.recordTime).format('MM-DD HH:mm'));
      })
    },
    handlePurchaseFetch(){

    },
    handleCrashReportFetch(){

    },
    handleDataFetch(){

    },
    handleOnlineCountsFetch(type){
      let dataType = '';
      switch(type)
      {
        case 'year':
          dataType = 'HalfYear';
          break;
        case 'day':
          dataType = 'OneDay';
          break;
      }
      request({
        url: 'record/onlineCountRecord/getOnlineCountRecordsWithinOneDay',
        method: 'get',
      }).then(response => {
        let arrData = [];
        let arrAxis = [];
        for(let i in response.data)
        {
          arrData.push(response.data[i].onlineCount);
          arrAxis.push(moment(response.data[i].recordTime).format('MM-DD HH:mm'));
        }
        lineChartData.onlineCounts.actualData = arrData;
        lineChartData.onlineCounts.actualAxis = arrAxis;
      });
    },
    handleSetLineChartData(type) {
    //   this.lineChartData = lineChartData[type];
    //   switch(type)
    //   {
    //     case 'onlineCounts':
    //       this.handleOnlineCountsFetch('day');
    //       break;
    //     case 'purchases':
    //       break;
    //     case 'crashReports':
    //       break;
    //   }
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .github-corner {
    position: absolute;
    top: 0px;
    border: 0;
    right: 0;
  }

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width:1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
