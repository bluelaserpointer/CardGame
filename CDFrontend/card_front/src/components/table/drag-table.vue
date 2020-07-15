<template>
  <div id="ManageBooks">
    <div class="searchbar">
      <form @submit.prevent="searchBooks">
        <input id="searchinput" v-model="searchText" placeholder="Search for books">
        <button id="searchbutton">GO</button>
      </form>
    </div>
    <div style="margin-left:200px;padding:50px;">
      <div style="margin-left:50px;margin-bottom:50px;">
        <vxe-toolbar>
          <template v-slot:buttons>
            <vxe-button @click="insertEvent()">Add Book</vxe-button>
            <vxe-button @click="removeEvent()">Remove books</vxe-button>
            <vxe-button @click="updateEvent()">Update Changes</vxe-button>
            <input
              id="uploadImage"
              style="margin-left:30px;"
              type="file"
              name="myPhoto"
              @change="loadImageFile()"
            >
          </template>
        </vxe-toolbar>
        <!--        <vxe-table-->
        <!--          border="inner"-->
        <!--          :align="allAlign"-->
        <!--          :data="filteredinfo"-->
        <!--          resizable-->
        <!--          ref="xTable"-->
        <!--          keep-source-->
        <!--          highlight-hover-row-->
        <!--          :edit-config="{-->
        <!--            trigger: 'dblclick',-->
        <!--            mode: 'cell',-->
        <!--            showStatus: true,-->
        <!--            icon: 'fa fa-pencil',-->
        <!--          }"-->
        <!--          @edit-closed="editClosedEvent"-->
        <!--        >-->
        <!--          <vxe-table-column type="seq" width="80"></vxe-table-column>-->
        <!--          <vxe-table-column type="checkbox" width="80"></vxe-table-column>-->
        <!--          <vxe-table-column-->
        <!--            field="isbn"-->
        <!--            title="#ISBN"-->
        <!--            width="200"-->
        <!--            sortable-->
        <!--            :edit-render="{ name: 'input' }"-->
        <!--          ></vxe-table-column>-->
        <!--          <vxe-table-column-->
        <!--            field="title"-->
        <!--            title="Book Title"-->
        <!--            width="340"-->
        <!--            :edit-render="{ name: 'input' }"-->
        <!--            sortable-->
        <!--          ></vxe-table-column>-->
        <!--          <vxe-table-column-->
        <!--            field="author"-->
        <!--            title="Author"-->
        <!--            width="260"-->
        <!--            sortable-->
        <!--            :edit-render="{ name: 'input' }"-->
        <!--          ></vxe-table-column>-->
        <!--          <vxe-table-column-->
        <!--            field="storage"-->
        <!--            title="Storage"-->
        <!--            width="200"-->
        <!--            sortable-->
        <!--            :edit-render="{ name: 'input' }"-->
        <!--          ></vxe-table-column>-->
        <!--          <vxe-table-column-->
        <!--            field="price"-->
        <!--            title="Price"-->
        <!--            width="200"-->
        <!--            sortable-->
        <!--            :edit-render="{ name: 'input' }"-->
        <!--          ></vxe-table-column>-->
        <!--          <vxe-table-column-->
        <!--            field="bookDetails.bookDescription"-->
        <!--            title="Book Description"-->
        <!--            width="300"-->
        <!--            show-overflow="tooltip"-->
        <!--            :edit-render="{ name: 'input' }"-->
        <!--          ></vxe-table-column>-->
        <!--          <vxe-table-column title="Cover" width="260">-->
        <!--            <template v-slot="{ row }">-->
        <!--              <img-->
        <!--                :src="row.bookCover"-->
        <!--                style="height:155px;width:120px;-->
        <!--                box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);"-->
        <!--              />-->
        <!--            </template>-->
        <!--          </vxe-table-column>-->
        <!--          <vxe-table-column title="Upload New Image" width="160">-->
        <!--            <template v-slot="{ row, column }">-->
        <!--              <vxe-button @click="updateEvent(row, column)">Update</vxe-button>-->
        <!--            </template>-->
        <!--          </vxe-table-column>-->
        <!--        </vxe-table>-->

        <vxe-modal
          v-model="showEdit"
          :title="selectRow ? '编辑&保存' : '新增&保存'"
          width="800"
          :loading="submitLoading"
          resize
          destroy-on-close
        >
          <vxe-form
            :data="formData"
            :items="formItems"
            :rules="formRules"
            title-align="right"
            title-width="100"
            @submit="submitEvent"
          />
          <!--          <input-->
          <!--            style="margin-left:300px;"-->
          <!--            id="uploadImage"-->
          <!--            type="file"-->
          <!--            name="myPhoto"-->
          <!--            @change="loadImageFile()"-->
          <!--          />-->
        </vxe-modal>
      </div>
    </div>
  </div>
</template>

<script>
import XEUtils from 'xe-utils'
import axios from 'axios'
import Vue from 'vue'
// import api from "./backend-api";

// require styles
// import "vue-willtable/dist/vue-willtable.min.css";
// import router from "../router";

export default {
  name: 'DragTable',
  data() {
    return {
      allAlign: 'center',
      user: '',
      booktitle: '',
      searchText: '',
      currentRoute: window.location.pathname,
      submitLoading: false,
      formData: {
        title: null,
        author: null,
        cover: null,
        storage: null,
        isbn: null,
        price: null,
        bookDetails: {
          iconBase64: null,
          bookDescription: null
        }
      },
      formRules: {
        title: [
          { required: true, message: 'Please insert book title.' },
          { min: 1, message: 'Cannot be empty.' }
        ],
        author: [
          { required: true, message: 'Please insert book author.' },
          { min: 1, message: 'Cannot be empty.' }
        ],
        cover: [{ required: true, message: 'Please insert book image.' }],
        storage: [{ required: true, message: 'Please insert book storage.' }],
        isbn: [{ required: true, message: 'Please insert book ISBN.' }],
        price: [{ required: true, message: 'Please insert book price.' }],
        description: [
          { required: true, message: 'Please insert book description.' }
        ]
      },
      formItems: [
        {
          title: 'Basic information',
          span: 24,
          titleAlign: 'left',
          titleWidth: 200,
          titlePrefix: { icon: 'fa fa-address-card-o' }
        },
        {
          field: 'title',
          title: 'Book Title',
          span: 12,
          itemRender: { name: '$input' }
        },
        {
          field: 'author',
          title: 'Author',
          span: 12,
          itemRender: { name: '$input' }
        },
        {
          field: 'storage',
          title: 'Storage',
          span: 12,
          itemRender: { name: '$input', props: { type: 'number' }}
        },
        {
          field: 'isbn',
          title: 'ISBN',
          span: 12,
          itemRender: {
            name: '$input',
            props: { type: 'number' }
          }
        },
        {
          field: 'price',
          title: 'Price',
          span: 12,
          itemRender: {
            name: '$input',
            props: { type: 'number' }
          }
        },
        {
          title: 'Book Description',
          span: 24,
          titleAlign: 'left',
          titleWidth: 200,
          titlePrefix: { icon: 'fa fa-address-card-o' }
        },
        {
          field: 'bookDetails.bookDescription',
          span: 24,
          titleSuffix: {
            message: '提示信息！！',
            icon: 'fa fa-question-circle'
          },
          itemRender: {
            name: '$textarea',
            props: {
              placeholder: 'Insert book Description.'
            }
          }
        },
        {
          align: 'center',
          span: 24,
          height: 12,
          titleAlign: 'left',
          itemRender: {
            name: '$buttons',
            children: [
              {
                props: { type: 'submit', content: 'Submit', status: 'primary' }
              },
              { props: { type: 'reset', content: 'Reset' }}
            ]
          }
        }
      ],
      selectRow: null,
      showEdit: false,
      cnt: 0,
      books: [],
      infos: [],
      covers: [],
      upimg: ''
    }
  },
  computed: {
    filteredinfo: {
      get() {
        return this.infos.filter(info => {
          return info.title.toLowerCase().match(this.searchText)
        })
      },
      set(infos) {
        this.filteredinfo = infos
      }
    }
  },

  mounted() {
    this.fetchData()
  },

  methods: {
    insertEvent() {
      this.formData = {
        title: '',
        author: '',
        storage: '',
        isbn: '',
        price: '',
        bookDetails: {
          bookDescription: ''
        }
      }
      this.selectRow = null
      this.showEdit = true
    },
    submitEvent() {
      this.submitLoading = true
      setTimeout(() => {
        this.submitLoading = false
        this.showEdit = false
        if (this.selectRow) {
          this.$XModal.message({ message: '保存成功', status: 'success' })
          Object.assign(this.selectRow, this.formData)
        } else {
          this.$XModal.message({ message: '新增成功', status: 'success' })
          console.log(localStorage.getItem('upimg'))
          api.CreateBooks(this.formData, localStorage.getItem('upimg'))
          localStorage.removeItem('upimg')
          this.$refs.xTable.insert(this.formData)
        }
      }, 500)
    },
    loadImageFile() {
      if (document.getElementById('uploadImage').files.length === 0) {
        return
      }
      const oFile = document.getElementById('uploadImage').files[0]

      const oFReader = new FileReader()
      oFReader.onloadend = function() {
        console.log(oFReader.result)
        localStorage.setItem('upimg', oFReader.result)
        // console.log(this.upimg);
      }
      oFReader.readAsDataURL(oFile)
    },
    removeEvent() {
      console.log(this.$refs.xTable.getCheckboxRecords())
      api.deleteBooks(this.$refs.xTable.getCheckboxRecords()).then(response => {
        console.log(response)
        // router.go();
      })
    },
    editClosedEvent({ row, column }) {
      console.log(row)
      if (this.$refs.xTable.isUpdateByRow(row, column.property)) {
        api.UpdateBook(row, row.bookCover).then(response => {
          setTimeout(() => {
            this.$XModal.message({
              message: `Saved successfully！ ${column.property}=${
                row[column.property]
              }`,
              status: 'success'
            })
            // 局部更新单元格为已保存状态
            this.$refs.xTable.reloadRow(row, null, column.property)
          }, 300)
        })
      }
    },
    updateEvent(row, column) {
      console.log(row)
      if (localStorage.getItem('upimg')) {
        api.UpdateBook(row, localStorage.getItem('upimg')).then(response => {
          setTimeout(() => {
            this.$XModal.message({
              message: `Saved successfully！ ${column.property}=${
                row[column.property]
              }`,
              status: 'success'
            })
            // 局部更新单元格为已保存状态
            this.$refs.xTable.reloadRow(row, null, column.property)
          }, 300)
        })
      }
    },

    count(id) {
      //   console.log(id);
      //   console.log(this.cnt);

      if (this.cnt == id - 1) {
        this.cnt = this.cnt + 1
        console.log(id)
        console.log(this.cnt)
        return true
      }
      return false
    },
    fetchData() {
      console.log('Fetching data from backend...')
      api.getBooks().then(response => {
        this.infos = response.data
        this.infos.forEach(info => {
          info.bookCover = info.bookDetails.iconBase64
        })
        // console.log(this.infos);
        // console.log(this.covers);
      })
    }
  }
}
</script>

<style scoped>
.searchbar {
  width: 100%;
  height: 80px;
  background-color: rgb(91, 110, 144);
}

#ManageBooks {
  margin: auto 0;
  margin-left: 230px;
  width: calc(100% - 230px);
}

#booksarea {
  padding: 30px;
}

#searchinput {
  margin: 20px;
  margin-right: 0px;
  width: 400px;
  height: 40px;
  border-radius: 50px;
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.2);
  /*border: 1px solid rgb(0, 0, 0, 0.3);*/
  text-indent: 10px;
}

#searchbutton {
  width: 100px;
  height: 40px;
  border-radius: 50px;
  color: rgb(91, 110, 144);
  border: none;
  font-size: 18px;
  font-weight: bold;
  /*border: 1px solid rgb(0, 0, 0, 0.3);*/
  box-shadow: 0 1px 2px 0 rgba(0, 0, 0, 0.5);
}

img {
  height: 320px;
  width: 100%;
}
.window {
  text-align: left;
  padding: 20px;
  float: left;
  width: 290px;
  height: 450px;
  margin: 20px;
  display: block;

  border: 1px solid whitesmoke;
}

.Admin {
  background-color: dodgerblue;
}

.Guest {
  background-color: rgb(200, 200, 200);
}
</style>
