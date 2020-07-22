<template>
  <el-form ref="postForm" :model="postForm" :rules="rules" class="form-container">
    <sticky :z-index="10" :class-name="'sub-navbar ' + postForm.status">
      <!--        <CommentDropdown v-model = "postForm.comment_disabled" />-->
      <PlatformDropdown v-model="postForm.platforms" />
      <!--        <SourceUrlDropdown v-model = "postForm.source_uri" />-->
      <el-button class="activityEditPublishButton" v-loading="loading" style="margin-left: 10px;" type="success" @click="submitForm">
        Publish
      </el-button>
<!--      <el-button v-loading="loading" type="warning" @click="draftForm">-->
<!--        Draft-->
<!--      </el-button>-->
    </sticky>
    <div class="createPost-main-container" style="display:grid; grid-template-columns: 50% 50%; grid-template-rows: 30% 70%;">
      <el-row style="grid-row: 1 / span 1; grid-column: 1 / span 2">
        <!--          <Warning />-->
        <el-col :span="24">
          <el-form-item style="margin-bottom: 40px;" prop="title">
            <MDinput v-model="postForm.title" :maxlength="100" name="name" required>
              Title
            </MDinput>
          </el-form-item>

          <div class="postInfo-container">
            <el-row>
              <el-col :span="8">
<!--                <el-form-item label-width="60px" label="Author:" class="postInfo-container-item">-->
<!--                  <el-select v-model="postForm.author" :remote-method="getRemoteUserList" filterable default-first-option remote placeholder="Search user">-->
<!--                    <el-option v-for="(item,index) in userListOptions" :key="item + index" :label="item" :value="item" />-->
<!--                  </el-select>-->
<!--                </el-form-item>-->
                <el-switch
                  v-model="limit"
                  active-color="#13ce66"
                  inactive-color="#ff4949"
                  active-text="限时"
                  inactive-text="常规"
                  style="margin-left: 20px; margin-top: 5px"
                />
              </el-col>

              <el-col v-if="limit" :span="10">
                <el-form-item label-width="120px" label="Publish Time:" class="postInfo-container-item">
                  <el-date-picker v-model="displayTime" type="datetime" value-format="yyyy-MM-dd hh:mm:ss" placeholder="Select date and time" />
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </el-col>
      </el-row>

      <el-form-item prop="content" style="margin-bottom: 30px; width: 100%; grid-row: 2 / span 1; grid-column: 1 / span 1">
        <Tinymce ref="editor" v-model="postForm.content" :height="400" />
      </el-form-item>

      <div style="display: grid; grid-template-columns: 50% 50%">
        <el-image
          style="width: 200px; height: 200px"
          :src="postForm.image_uri"
          :fit="itemImg"
        />
        <div class="coverControl">
          <input ref="img" type="file" style="margin: 10px" @change="uploadCover">
        </div>
      </div>
    </div>
  </el-form>
</template>

<script>
import Tinymce from '@/components/Tinymce/index'
import MDinput from '@/components/MDinput/index'
import Sticky from '@/components/Sticky/index' // 粘性header组件
import { validURL } from '@/utils/validate'
import Warning from './Warning'
import { CommentDropdown, PlatformDropdown, SourceUrlDropdown } from '../../views/example/components/Dropdown'
import axios from 'axios'
import moment from "moment";

const defaultForm = {
  status: 'draft',
  title: '', // 文章题目
  content: '', // 文章内容
  // content_short: '', // 文章摘要
  // source_uri: '', // 文章外链
  image_uri: '', // 文章图片
  limit: false,
  display_time: undefined, // 前台展示时间
  id: undefined,
  platforms: ['a-platform'],
  comment_disabled: false
  // importance: 0
};

export default {
  name: 'ActivityEditPanel',
  components: { Tinymce, MDinput, Sticky, Warning, CommentDropdown, PlatformDropdown, SourceUrlDropdown },
  props: {
    isEdit: {
      type: Boolean,
      default: false
    }
  },
  data() {
    const validateRequire = (rule, value, callback) => {
      if (value === '') {
        this.$message({
          message: rule.field + '为必传项',
          type: 'error'
        });
        callback(new Error(rule.field + '为必传项'))
      } else {
        callback()
      }
    };
    const validateSourceUri = (rule, value, callback) => {
      if (value) {
        if (validURL(value)) {
          callback()
        } else {
          this.$message({
            message: '外链url填写不正确',
            type: 'error'
          });
          callback(new Error('外链url填写不正确'))
        }
      } else {
        callback()
      }
    };
    return {
      limit: false,
      postForm: Object.assign({}, defaultForm),
      loading: false,
      userListOptions: [],
      rules: {
        // image_uri: [{ validator: validateRequire }],
        title: [{ validator: validateRequire }],
        content: [{ validator: validateRequire }],
        source_uri: [{ validator: validateSourceUri, trigger: 'blur' }]
      },
      tempRoute: {},
      displayTime: undefined
    }
  },
  computed: {
    contentShortLength() {
      return this.postForm.content_short.length
    }
  },
  created() {
    // Why need to make a copy of this.$route here?
    // Because if you enter this page and quickly switch tag, may be in the execution of the setTagsViewTitle function, this.$route is no longer pointing to the current page
    // https://github.com/PanJiaChen/vue-element-admin/issues/1221

    this.tempRoute = Object.assign({}, this.$route)
  },
  methods: {
    delayDate(days){
      let newDate = new Date();
      let showDate;
      for (let i = 1; i <= days; i++) { //后7天
        let date = newDate.getDate() < 10 ? '0' + newDate.getDate() : newDate.getDate();
        let yue = (newDate.getMonth() + 1) < 10 ? '0' + (newDate.getMonth() + 1) : (newDate.getMonth() + 1);
        showDate = newDate.getFullYear() + '-' + yue + '-' + date;
        newDate.setDate(newDate.getDate() + 1);
      }
      return showDate + ' 00:00:00';
    },
    formatDate(date){
      return moment(new Date(date)).format('YYYY-MM-DD HH:mm:ss');
    },
    resetArticle(){
      this.postForm = Object.assign({}, defaultForm);
      this.$refs.editor.setContent('');
      this.displayTime = null;
      this.limit = false;
    },
    submitForm() {
      if(this.postForm.title === undefined || this.postForm.content === undefined || this.postForm.title === '' || this.postForm.content === '')
      {
        this.$message.error('Data From Invalid!');
        return false;
      }

      const postData = new FormData();
      postData.append('activityImg', this.postForm.image_uri === undefined ? '' : this.postForm.image_uri);
      postData.append('activityName', this.postForm.title);
      postData.append('activityDescription', this.postForm.content);

      if (this.limit === false) {
        postData.append('start', this.formatDate(new Date()));
      } else if (this.displayTime !== null && this.displayTime !== undefined ) {
        postData.append('start', this.formatDate(this.displayTime));
      }else {
        postData.append('start', this.delayDate(7));
      }

      postData.append('type', this.limit === true ? "true" : "false");

      axios.post(`http://localhost:8080/activity/addActivity`, postData).then(response => {
        if (response.data) {
          //
          this.resetArticle();
        } else {
          this.$message.error('Publishing Activity failed!');
        }
      })
      .catch(error =>
        {
          this.$message.error('Publishing Activity failed!');
        }
      );
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
        _this.postForm.image_uri = this.result;
      }
    },


    setTagsViewTitle() {
      const title = 'Edit Activity';
      const route = Object.assign({}, this.tempRoute, { title: `${title}-${this.postForm.id}` });
      this.$store.dispatch('tagsView/updateVisitedView', route)
    },
    setPageTitle() {
      const title = 'Edit Activity';
      document.title = `${title} - ${this.postForm.id}`
    },
  }
}
</script>

<style lang="scss" scoped>
@import "~@/styles/mixin.scss";

.createPost-container {
  position: relative;

  .createPost-main-container {
    padding: 40px 45px 20px 50px;

    .postInfo-container {
      position: relative;
      @include clearfix;
      margin-bottom: 10px;

      .postInfo-container-item {
        float: left;
      }
    }
  }

  .word-counter {
    width: 40px;
    position: absolute;
    right: 10px;
    top: 0px;
  }
}

.article-textarea ::v-deep {
  textarea {
    padding-right: 40px;
    resize: none;
    border: none;
    border-radius: 0px;
    border-bottom: 1px solid #bfcbd9;
  }
}
</style>
