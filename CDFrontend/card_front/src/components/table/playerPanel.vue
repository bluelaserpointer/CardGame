<template>
  <div class="createPost-container" style="margin: 10px">
    <el-tabs v-model="activeName" class="playerTab" type="card" @tab-click="handleClick">
      <el-tab-pane class="playerEntityTab" label="Player Entity Panel" name="first"><PlayerEntityPanel /></el-tab-pane>
      <el-tab-pane class="playerOwnItemTab" label="Player Own Item" name="second"><PlayerItemPanel /></el-tab-pane>
      <el-tab-pane class="playerOwnCardTab" label="Player Own Card" name="third"><PlayerCardPanel /></el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import PlayerItemPanel from './playerItemPanel'
import PlayerCardPanel from './playerCardPanel'
import PlayerEntityPanel from './playerEntityPanel'

export default {
  name: 'PlayerPanel',
  components: { PlayerItemPanel, PlayerCardPanel, PlayerEntityPanel },
  data() {
    return {
      activeName: 'first'
    }
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab.$el.id);
    }
  }
}
</script>
