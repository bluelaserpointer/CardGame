<template>
  <div class="createPost-container" style="margin: 10px">
    <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
      <el-tab-pane label="Activity Edit" name="first"><ActivityEditPanel /></el-tab-pane>
      <el-tab-pane label="Activity Table" name="second"><ActivityEntityPanel ref="entity" /></el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import ActivityEditPanel from './ActivityEditPanel'
import ActivityEntityPanel from './ActivityEntityPanel'

export default {
  name: 'ActivityPanel',
  components: { ActivityEditPanel, ActivityEntityPanel },
  data() {
    return {
      activeName: 'first'
    }
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab.$el.id);
      if (tab.$el.id === 'pane-second') {
        this.$refs.entity.getList()
      }
    }
  }
}
</script>
