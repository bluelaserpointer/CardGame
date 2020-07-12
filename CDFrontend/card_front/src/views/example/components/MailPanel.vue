<template>
  <div class="createPost-container" style="margin: 10px">
    <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
      <el-tab-pane label="Mail Edit" name="first"><MailEditPanel/></el-tab-pane>
      <el-tab-pane label="Mail Table" name="second"><MailEntityPanel ref="entity"/></el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
  import MailEditPanel from './MailEditPanel'
  import MailEntityPanel from './MailEntityPanel'

  export default {
    name:"MailPanel",
    components: { MailEditPanel, MailEntityPanel },
    data() {
      return {
        activeName: 'first'
      };
    },
    methods: {
      handleClick(tab, event) {
        console.log(tab, event);
        if(tab.$el.id === 'pane-second')
        {
          this.$refs.entity.getList();
        }
      }
    }
  };
</script>
