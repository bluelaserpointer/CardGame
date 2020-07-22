<template>
  <div class="createPost-container" style="margin: 10px">
    <el-tabs v-model="activeName" class="phaseTab" type="card" @tab-click="handleClick">
      <el-tab-pane class="phaseEntityTab" label="Phase Entity Panel" name="first"><PhaseEntityPanel /></el-tab-pane>
      <el-tab-pane class="PhaseEditPanel" label="Phase Edit Panel" name="second"><PhaseEditPanel ref="edit" /></el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
  import PhaseEntityPanel from './phaseEntityPanel'
  import PhaseEditPanel from './phaseEditPanel'

  export default {
    name: 'PhasePanel',
    components: { PhaseEditPanel , PhaseEntityPanel },
    data() {
      return {
        activeName: 'first'
      }
    },
    methods: {
      handleClick(tab, event) {
        console.log(tab.$el.id);
        if (tab.$el.id === 'pane-second') {
          this.$refs.edit.getList()
        }
      }
    }
  }
</script>
