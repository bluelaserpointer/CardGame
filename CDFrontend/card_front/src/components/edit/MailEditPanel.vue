<template>
  <el-form ref="postForm" :model="postForm" :rules="rules" class="form-container">
    <sticky :z-index="10" :class-name="'sub-navbar ' + postForm.status">
      <!--        <CommentDropdown v-model = "postForm.comment_disabled" />-->
      <PlatformDropdown v-model="postForm.platforms" />
      <!--        <SourceUrlDropdown v-model = "postForm.source_uri" />-->
      <el-button v-loading="loading" style="margin-left: 10px;" type="success" @click="submitForm">
        Publish
      </el-button>
      <el-button v-loading="loading" type="warning" @click="draftForm">
        Draft
      </el-button>
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
        </el-col>
      </el-row>

      <el-form-item prop="content" style="margin-bottom: 30px; width: 100%; grid-row: 2 / span 1; grid-column: 1 / span 1">
        <Tinymce ref="editor" v-model="postForm.content" :height="400" />
      </el-form-item>

      <el-form-item prop="image_uri" style="margin: 0 0 30px 30px; width: 80%; grid-row: 2 / span 1; grid-column: 2 / span 1">
        <Upload v-model="postForm.image_uri" />
      </el-form-item>
    </div>
  </el-form>
</template>

<script>
  import Tinymce from '@/components/Tinymce/index'
  import Upload from '@/components/Upload/SingleImage3'
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
    id: undefined,
    platforms: ['a-platform'],
    comment_disabled: false
    // importance: 0
  };

  export default {
    name: 'MailEditPanel',
    components: { Tinymce, MDinput, Upload, Sticky, Warning, CommentDropdown, PlatformDropdown, SourceUrlDropdown },
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

      setTagsViewTitle() {
        const title = 'Edit Mail';
        const route = Object.assign({}, this.tempRoute, { title: `${title}-${this.postForm.id}` });
        this.$store.dispatch('tagsView/updateVisitedView', route)
      },
      setPageTitle() {
        const title = 'Edit Mail';
        document.title = `${title} - ${this.postForm.id}`
      },
      resetArticle(){
        this.postForm = Object.assign({}, defaultForm);
        this.$refs.editor.setContent('');
      },
      submitForm() {
        const postData = new FormData();
        postData.append('mailImg', this.postForm.image_uri);
        postData.append('mailName', this.postForm.title);
        postData.append('mailDescription', this.postForm.content);

        axios.post(`http://localhost:8080/mail/addMail`, postData).then(response => {
          if (response.data) {
            //
            this.resetArticle();
          } else {
            this.$message.error('Publishing Mail failed!');
          }
        })
          .catch(error =>
            {
              this.$message.error('Publishing Mail failed!');
            }
          );

      },


      // draftForm() {
      //   if (this.postForm.content.length === 0 || this.postForm.title.length === 0) {
      //     this.$message({
      //       message: '请填写必要的标题和内容',
      //       type: 'warning'
      //     });
      //     return
      //   }
      //   this.$message({
      //     message: '保存成功',
      //     type: 'success',
      //     showClose: true,
      //     duration: 1000
      //   });
      //   this.postForm.status = 'draft'
      // },
      // getRemoteUserList(query) {
      //   searchUser(query).then(response => {
      //     if (!response.data.items) return;
      //     this.userListOptions = response.data.items.map(v => v.name)
      //   })
      // }

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
