<template>
  <el-form ref="postForm" :model="postForm" :rules="rules" class="form-container">
    <sticky :z-index="10" :class-name="'sub-navbar ' + postForm.status">
      <!--        <CommentDropdown v-model = "postForm.comment_disabled" />-->
      <PlatformDropdown v-model="postForm.platforms" />
      <!--        <SourceUrlDropdown v-model = "postForm.source_uri" />-->
      <el-button class="mailEditPublishButton" v-loading="loading" style="margin-left: 10px;" type="success" @click="submitForm">
        Publish
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
  import request from "@/utils/request"; // secondary package based on el-pagination

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
      resetArticle(){
        this.postForm = Object.assign({}, defaultForm);
        this.$refs.editor.setContent('');
      },
      submitForm() {
        if(this.postForm.title === undefined || this.postForm.content === undefined || this.postForm.title === '' || this.postForm.content === '')
        {
          this.$message.error('Data From Invalid!');
          return false;
        }

        const postData = new FormData();
        postData.append('mailImg', this.postForm.image_uri === undefined ? '' : this.postForm.image_uri);
        postData.append('mailName', this.postForm.title);
        postData.append('mailDescription', this.postForm.content);


        request({
          url: '/mail/addMail',
          method: 'post',
          data: postData
        }).then(response => {
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

        // axios.post(`http://localhost:8080/mail/addMail`, postData).then(response => {
        //   if (response.data) {
        //     //
        //     this.resetArticle();
        //   } else {
        //     this.$message.error('Publishing Mail failed!');
        //   }
        // })
        //   .catch(error =>
        //     {
        //       this.$message.error('Publishing Mail failed!');
        //     }
        //   );

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
