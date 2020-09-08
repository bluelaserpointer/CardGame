const mockData = { result: [{
  activityId: 0,
  activityName: '',
  start: '',
  type: false,
  activityDetails: {
    activityImg: '',
    activityDescription: ''
  }
}] }

const mockDataTrans = [{
  activityId: 0,
  activityName: '',
  activityImg: '',
  activityDescription: '',
  start: '',
  type: false,
  activityDetails: {
    activityImg: '',
    activityDescription: ''
  }
}]

const validateStub = {
  render: () => {},
  methods: {
    validate: () => {}
  }
}

jest.unmock('axios')
import axios from 'axios'
import MockAdapter from 'axios-mock-adapter'

import { createLocalVue, mount, shallowMount } from '@vue/test-utils'
import ActivityEntityPanel from '@/components/article/ActivityEntityPanel'
import Element from 'element-ui'
import moment from 'moment'

const localVue = createLocalVue()
localVue.use(Element)

describe('ActivityEntityPanel.vue', () => {
  const wrapper = shallowMount(ActivityEntityPanel,
    {
      localVue,
      stubs: {
        'el-form': validateStub
      }
    })

  const mockAdapter = new MockAdapter(axios)
  const spyPost = jest.spyOn(axios, 'post')

  mockAdapter.onPost('activity/List').reply(200, mockData)

  it('Startup', async() => {
    await wrapper.vm.getList()
  })

  it('Activity Entity Panel Resolves created getList watchList', async() => {
    expect(wrapper.vm.panelVisible).toBeFalsy()
    expect(wrapper.vm.list).toStrictEqual(mockDataTrans)
    expect(spyPost).toHaveBeenCalledTimes(1)
  })

  it('Activity Entity Panel Resolves handleUpdate', () => {
    wrapper.vm.dialogStatus = 'xxx'
    wrapper.vm.panelVisible = false

    wrapper.vm.handleUpdate()

    expect(wrapper.vm.panelVisible).toBeTruthy()
    expect(wrapper.vm.dialogStatus).toBe('update')
  })

  it('Activity Entity Panel Resolves formatDate', () => {
    const date = new Date()
    const formatDateStr = moment(wrapper.vm.formatDate(date)).format('YYYY-MM-DD HH:mm:ss')
    expect(wrapper.vm.formatDate(date)).toBe(formatDateStr)
  })

  it('Activity Entity Panel Resolves Rest', () => {
    wrapper.vm.handleFilter()
    wrapper.vm.sortChange({ prop: 'id' })
    wrapper.vm.sortByID('ascending')
  })
})
