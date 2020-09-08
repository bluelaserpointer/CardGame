<template>
  <div class="dashboard-editor-container">
    <!--    <span>hi</span>-->
    <github-corner class="github-corner" />
    <panel-group :curr-data="currData" @handleSetLineChartData="handleSetLineChartData" />

    <el-col style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <line-chart :chart-data="lineChartData" />
    </el-col>

    <el-row :gutter="32">
      <!--      <el-col :xs="24" :sm="24" :lg="8">-->
      <!--        <div class="chart-wrapper">-->
      <!--          <raddar-chart />-->
      <!--        </div>-->
      <!--      </el-col>-->

      <!--      <el-col style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">-->
      <el-col :xs="24" :sm="24" :lg="8" style="background:#fff">
        <line-chart :chart-data="onlineCounts" />
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8" style="background:#fff">
        <line-chart :chart-data="pveRecords" />
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8" style="background:#fff">
        <!--        <line-chart :chart-data="lineChartData" />-->
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8" style="background:#fff">
        <line-chart :chart-data="crashReports" />
      </el-col>

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
import request from '@/utils/request' // secondary package based on el-pagination
import moment from 'moment'

// import RaddarChart from './components/RaddarChart'
// import GithubCorner from '@/components/GithubCorner'
// import TransactionTable from './components/TransactionTable'
// import TodoList from './components/TodoList'
// import BoxCard from './components/BoxCard'
// import DragTable from '../../table/drag-table'

const lineChartData = {
  onlineCounts: {
    actualData: [100, 100, 100, 100, 100, 100, 100],
    actualAxis: ['', '', '', '', '', '', ''],
    name: 'Number of Players Online (Half Year)'
  },
  purchases: {
    actualData: [100, 100, 100, 100, 100, 100, 100],
    actualAxis: ['', '', '', '', '', '', ''],
    name: 'Number of Purchases (Half Year)'
  },
  pveRecords: {
    actualData: [100, 100, 100, 100, 100, 100, 100],
    actualAxis: ['', '', '', '', '', '', ''],
    name: 'Number of Players In PVE-Chapters'
  },
  crashReports: {
    actualData: [100, 100, 100, 100, 100, 100, 100],
    actualAxis: ['', '', '', '', '', '', ''],
    name: 'Number of Crashes (Half Year)'
  }
}

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
        pveRecord: 0
      },
      onlineCounts: {
        actualData: [100, 100, 100, 100, 100, 100, 100],
        actualAxis: ['', '', '', '', '', '', ''],
        name: 'Number of Players Online (One Day)'
      },
      purchases: {
        actualData: [100, 100, 100, 100, 100, 100, 100],
        actualAxis: ['', '', '', '', '', '', ''],
        name: 'Number of Players Online (One Day)'
      },
      pveRecords: {
        actualData: [100, 100, 100, 100, 100, 100, 100],
        actualAxis: ['', '', '', '', '', '', ''],
        name: 'Number of Players Online (One Day)'
      },
      crashReports: {
        actualData: [100, 100, 100, 100, 100, 100, 100],
        actualAxis: ['', '', '', '', '', '', ''],
        name: 'Number of Players Online (One Day)'
      },
      onlineCountId: null,
      purchaseId: null,
      crashReportId: null,
      pveRecordId: null

    }
  },
  created() {
    this.handleOnlineCountsFetch('day')
    this.handlePveRecordsFetch('day')
    this.handleOnlineCountsFetch('year')
    this.handleScheduled()
  },
  methods: {
    handleScheduled() {
      this.handleOnlineCountFetch()
      this.handlePveRecordFetch()
      this.handleCrashReportFetch()
      this.onlineCountId = setInterval(this.handleOnlineCountFetch, 30000)
      this.pveRecordId = setInterval(this.handlePveRecordFetch, 30000)
      this.crashReportId = setInterval(this.handleCrashReportFetch, 30000)
    },
    handleOnlineCountFetch() {
      request.get('record/onlineCountRecord/getOnlineCountRecord').then(response => {
        this.currData.onlineCount = response.data.onlineCount
        this.onlineCounts.actualData.push(response.data.onlineCount)
        this.onlineCounts.actualAxis.push(moment(response.data.recordTime).format('MM-DD HH:mm'))
      })
    },
    handlePveRecordFetch() {
      console.log('Here')
      request.get('record/pveRecord/getPveRecordsWithinOneDay').then(response => {
        console.log(response)
        const arrData = []
        const arrAxis = []
        let tmp = 0
        for (const i in response.data) {
          tmp += response.data[i][1]
          arrAxis.push(response.data[i][0])
          arrData.push(response.data[i][2])
        }
        this.pveRecords.actualData = arrData
        this.pveRecords.actualAxis = arrAxis
        this.currData.pveRecord = tmp
      })
    },
    handleCrashReportFetch() {
      request.get('record/crashReport/getCrashReportsWithinOneDay').then(response => {
        console.log(response)
        const map = new Map()
        for (const i in response.data) {
          const formattedDate = moment(response.data[i].recordTime).format('YYYY-MM-DD HH:mm')

          if (map.has(formattedDate)) {
            map.set(formattedDate, map.get(formattedDate) + 1)
          } else {
            map.set(formattedDate, 1)
          }
        }

        this.crashReports.actualAxis = Array.from(map.keys())
        this.crashReports.actualData = Array.from(map.values())
        this.currData.crashReport = response.data.length

        // let arrData = [];
        // let arrAxis = [];
        // let tmp = 0;
        // for(let i in response.data)
        // {
        //   tmp += response.data[i][1];
        //   arrAxis.push(response.data[i][0]);
        //   arrData.push(response.data[i][2]);
        // }
        // this.crashReports.actualData = arrData;
        // this.crashReports.actualAxis = arrAxis;
      })
    },
    handlePurchaseFetch() {

    },
    handleDataFetch() {

    },

    handleOnlineCountsFetch(type) {
      let dataType = ''
      switch (type) {
        case 'year':
          dataType = 'HalfYear'
          break
        case 'day':
          dataType = 'OneDay'
          break
      }
      request.get(`record/onlineCountRecord/getOnlineCountRecordsWithin${dataType}`).then(response => {
        const arrData = []
        const arrAxis = []
        for (const i in response.data) {
          arrData.push(response.data[i].onlineCount)
          arrAxis.push(moment(response.data[i].recordTime).format('MM-DD HH:mm'))
        }
        switch (type) {
          case 'year':
            lineChartData.onlineCounts.actualData = arrData
            lineChartData.onlineCounts.actualAxis = arrAxis
            break
          case 'day':
            this.onlineCounts.actualData = arrData
            this.onlineCounts.actualAxis = arrAxis
            break
        }
      })
    },
    handlePveRecordsFetch(type) {
      let dataType = ''
      switch (type) {
        case 'year':
          dataType = 'HalfYear'
          break
        case 'day':
          dataType = 'OneDay'
          break
      }
      request.get(`record/pveRecord/getPveRecordsWithin${dataType}`).then(response => {
        console.log(response)

        const arrData = []
        const arrAxis = []
        for (const i in response.data) {
          arrAxis.push(response.data[i][0])
          arrData.push(response.data[i][1])
        }
        switch (type) {
          case 'year':
            lineChartData.pveRecords.actualData = arrData
            lineChartData.pveRecords.actualAxis = arrAxis
            break
          case 'day':
            this.pveRecords.actualData = arrData
            this.pveRecords.actualAxis = arrAxis
            break
        }
      })
    },
    handleCrashReportsFetch(type) {
      let dataType = ''
      switch (type) {
        case 'year':
          dataType = 'HalfYear'
          break
        case 'day':
          dataType = 'OneDay'
          break
      }
      request.get(`record/crashReport/getCrashReportsWithin${dataType}`).then(response => {
        const map = new Map()
        for (const i in response.data) {
          const formattedDate = moment(response.data[i].recordTime).format('YYYY-MM-DD HH')

          if (map.has(formattedDate)) {
            map.set(formattedDate, map.get(formattedDate) + 1)
          } else {
            map.set(formattedDate, 1)
          }
        }
        lineChartData.crashReports.actualAxis = Array.from(map.keys())
        lineChartData.crashReports.actualData = Array.from(map.values())
      })
    },

    handleSetLineChartData(type) {
      switch (type) {
        case 'onlineCounts':
          this.handleOnlineCountsFetch('year')
          break
        case 'purchases':
          this.handlePurchasesFetch('year')
          break
        case 'crashReports':
          this.handleCrashReportsFetch('year')
          break
        case 'pveRecords':
          this.handlePveRecordsFetch('year')
          break
      }
      this.lineChartData = lineChartData[type]
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
