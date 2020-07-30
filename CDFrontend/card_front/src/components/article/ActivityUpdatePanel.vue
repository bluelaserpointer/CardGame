<template>
  <el-form ref="postForm" :model="postForm" :rules="rules" class="form-container">
    <sticky :z-index="10" :class-name="'sub-navbar ' + postForm.status">
      <!--        <CommentDropdown v-model = "postForm.comment_disabled" />-->
      <!--      <PlatformDropdown v-model="postForm.platforms" />-->
      <!--        <SourceUrlDropdown v-model = "postForm.source_uri" />-->
      <el-button class="activityUpdatePublishButton" v-loading="loading" style="margin-left: 10px;" type="success" @click="submitForm">
        Publish
      </el-button>
      <el-button class="deleteOuterButton" type="danger" @click="deleteVisible = true">
        Delete
      </el-button>

    </sticky>
    <div class="createPost-main-container" style="display:grid; grid-template-columns: 50% 50%; grid-template-rows: 30% 70%;">
      <el-row style="grid-row: 1 / span 1; grid-column: 1 / span 2">
        <el-col :span="24">
          <el-form-item style="margin-bottom: 40px;" prop="title">
            <MDinput v-model="postForm.title" :maxlength="100" name="name" required>
              Title
            </MDinput>
          </el-form-item>

          <div class="postInfo-container">
            <el-row>
              <el-col :span="8">>
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
    <el-dialog
      title="Deletion Confirm"
      width="30%"
      :visible.sync="deleteVisible"
      append-to-body
      class="innerDialog"
    >
      <el-input v-model="confirmPassword" placeholder="Identification" show-password width="60%" />
      <el-button class="confirmInnerButton" @click="confirmIdentity">Confirm Identity</el-button>

      <span slot="footer" class="dialog-footer">
        <el-button class="cancelInnerButton" @click="deleteVisible = false">Cancel</el-button>
        <el-button class="deleteInnerButton" v-if="confirmDelete === false" type="danger" disabled>Delete</el-button>
        <el-button class="deleteInnerButton" v-else type="danger" @click="deleteData">Delete</el-button>
      </span>
    </el-dialog>
  </el-form>
</template>

<script>
  import Tinymce from '@/components/Tinymce/index'
  import MDinput from '@/components/MDinput/index'
  import Sticky from '@/components/Sticky/index' // 粘性header组件
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
    limit: false,
    display_time: undefined, // 前台展示时间
    id: undefined,
    platforms: ['a-platform'],
    comment_disabled: false
    // importance: 0
  };

  export default {
    name: 'ActivityUpdatePanel',
    components: { Tinymce, MDinput, Sticky, Warning, CommentDropdown, PlatformDropdown, SourceUrlDropdown },
    props: ['updateContent'] ,
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

      return {
        deleteVisible: false,
        confirmDelete: false,
        confirmPassword: '',
        limit: false,
        postForm: Object.assign({}, defaultForm),
        loading: false,
        userListOptions: [],
        rules: {
          // image_uri: [{ validator: validateRequire }],
          title: [{ validator: validateRequire }],
          content: [{ validator: validateRequire }],
          // source_uri: [{ validator: validateSourceUri, trigger: 'blur' }]
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
    watch:{
      updateContent:{
        handler(newVal, oldVal)
        {
          this.postForm.image_uri = newVal.activityImg;
          this.postForm.title = newVal.activityName;
          this.$refs.editor.setContent(newVal.activityDescription);
          this.limit = newVal.type === "true";
          this.displayTime = newVal.start;
        },
        deep:true
      },
      deleteVisible() {
        this.confirmDelete = false;
        this.confirmPassword = '';
      } // untested
    },
    created() {
      // Why need to make a copy of this.$route here?
      // Because if you enter this page and quickly switch tag, may be in the execution of the setTagsViewTitle function, this.$route is no longer pointing to the current page
      // https://github.com/PanJiaChen/vue-element-admin/issues/1221

      this.postForm.image_uri = this.updateContent.activityImg;
      this.postForm.title = this.updateContent.activityName;
      this.postForm.content = this.updateContent.activityDescription;
      this.limit = this.updateContent.type === "true";
      this.displayTime = this.updateContent.start;

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
      confirmIdentity() {
        let postData = new FormData();
        let _this = this;
        postData.append('userName', localStorage.getItem('AdminName'));
        postData.append('password', this.confirmPassword);

        request({
          url: 'user/confirmDelete',
          method: 'post',
          data: postData
        }).then(response => {
          if (response.data) {
            _this.confirmDelete = true
          } else {
            this.$message.error('Identification failed!');
          }
        })
          .catch(error =>
            {
              this.$message.error('Identification failed!');
            }
          );


        // axios.post('http://localhost:8080/admin/identifyAdmin', postData)
        //   .then(response => {
        //     if (response.data) {
        //       _this.confirmDelete = true
        //     } else {
        //       this.$message.error('Identification failed!');
        //     }
        //   })
        //   .catch(error =>
        //     {
        //       this.$message.error('Identification failed!');
        //     }
        //   );
      },
      deleteData() {
        let postData = new FormData();
        let _this = this;
        postData.append('activityId', this.updateContent.activityId);

        request({
          url: 'activity/deleteActivity',
          method: 'post',
          data: postData
        }).then(response => {
          if (response.data) {
            _this.deleteVisible = false;
            _this.$emit('getList');
          } else {
            this.$message.error('Deleting Data failed!');
          }
        })
          .catch(error =>
            {
              this.$message.error('Deleting Data failed!');
            }
          );

        // axios.post('http://localhost:8080/activity/deleteActivity', postData).then(response => {
        //   if (response.data) {
        //     _this.deleteVisible = false;
        //     _this.$emit('getList');
        //   } else {
        //     this.$message.error('Deleting Data failed!');
        //   }
        // })
        //   .catch(error =>
        //     {
        //       this.$message.error('Deleting Data failed!');
        //     }
        //   );
      },
      submitForm() {
        let _this = this;

        if(this.postForm.title === undefined || this.postForm.content === undefined || this.postForm.title === '' || this.postForm.content === '')
        {
          this.$message.error('Data From Invalid!');
          return false;
        }

        let start;

        if (this.limit === false) {
          start = this.formatDate(new Date());
        } else if (this.displayTime !== null && this.displayTime !== undefined ) {
          start = this.formatDate(this.displayTime);
        }else {
          start = this.delayDate(7);
        }

        let postData = {
        activityId: this.updateContent.activityId,
        activityName: this.postForm.title,
          type: this.limit === true ? "true" : "false",
          start: start,
          activityDetails: {
            activityId: this.updateContent.activityId,
            activityDescription: this.postForm.content,
            activityImg: this.postForm.image_uri === undefined ? '' : this.postForm.image_uri
          }
        };

        request({
          url: 'activity/updateActivity',
          method: 'post',
          data: postData
        }).then(response => {
          if (response.data) {
            //
            _this.$emit('getList');
          } else {
            //
            this.$message.error('Updating Data failed!');
          }
        })
          .catch(error =>
            {
              this.$message.error('Updating Data failed!');
            }
          );

        // axios.post(`http://localhost:8080/activity/updateActivity`, postData).then(response => {
        //   if (response.data) {
        //     //
        //     _this.$emit('getList');
        //   } else {
        //     //
        //     this.$message.error('Updating Data failed!');
        //   }
        // })
        //   .catch(error =>
        //     {
        //       this.$message.error('Updating Data failed!');
        //     }
        //   );
      },
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
