<template>
  <div class="createPost-container" style="margin: 10px">
    <el-tabs v-model="activeName" class="phaseTab" type="card" @tab-click="handleClick">
      <el-tab-pane class="phaseEntityTab" label="Phase Entity Panel" name="first"><PhaseEntityPanel ref="entity" /></el-tab-pane>
      <el-tab-pane class="phaseEditPanel" label="Phase Edit Panel" name="second"><PhaseEditPanel ref="editor" /></el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import PhaseEntityPanel from './phaseEntityPanel'
import PhaseEditPanel from './phaseEditPanel'

export default {
  name: 'PhasePanel',
  components: { PhaseEditPanel, PhaseEntityPanel },
  data() {
    return {
      activeName: 'first'
    }
  },
  methods: {
    handleClick(tab, event) {
      const _this = this
      if (tab.$el.id === 'pane-first') {
        _this.$refs['entity'].getList(_this.$refs.entity.listQuery.page, _this.$refs.entity.listQuery.limit)
      } else if (tab.$el.id === 'pane-second') {
        _this.$refs['editor'].getList()
      }
    }
  }
}
</script>
