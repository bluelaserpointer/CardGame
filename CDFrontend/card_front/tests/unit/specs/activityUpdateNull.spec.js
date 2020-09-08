const validateStub = {
  render: () => {},
  methods: {
    setContent: () => {}
  }
}

jest.unmock('axios')
import axios from 'axios'
import MockAdapter from 'axios-mock-adapter'

import { createLocalVue, mount, shallowMount } from '@vue/test-utils'
import ActivityUpdatePanel from '@/components/article/ActivityUpdatePanel'
import Element from 'element-ui'
import moment from 'moment'

const localVue = createLocalVue()
localVue.use(Element)

describe('ActivityUpdatePanel.vue', () => {
  const wrapper = shallowMount(ActivityUpdatePanel, {
    localVue,
    stubs: {
      'Tinymce': validateStub
    },
    propsData: {
      updateContent: {
        activity: 0,
        activityImg: '0',
        activityName: '0',
        activityDescription: '0',
        type: 'true',
        start: '0'
      }
    }
  })

  beforeEach(() => {
    wrapper.vm.$nextTick(() => {})
  })

  const mockAdapter = new MockAdapter(axios)
  const spyPost = jest.spyOn(axios, 'post')

  it('Activity Update Panel Nulls submitForm', async() => {
    wrapper.vm.postForm.title = 'TestTitle'
    wrapper.vm.postForm.content = 'TestContent'
    wrapper.vm.postForm.image_uri = undefined

    wrapper.vm.limit = false

    mockAdapter.onAny().reply(200, false)

    expect(spyPost).toHaveBeenCalledTimes(0)
    await wrapper.vm.submitForm()

    wrapper.vm.$nextTick(() => {
      expect(spyPost).toHaveBeenCalledTimes(1)
    })
  })

  it('Activity Entity Panel Nulls confirmIdentity', async() => {
    wrapper.vm.confirmDelete = false

    mockAdapter.onAny().reply(200, false)

    await wrapper.vm.confirmIdentity()

    wrapper.vm.$nextTick(() => {
      expect(wrapper.vm.confirmDelete).toBeFalsy()
      expect(spyPost).toHaveBeenCalledTimes(2)
    })
  })

  it('Activity Entity Panel Nulls deleteData', async() => {
    wrapper.vm.confirmDelete = true
    wrapper.vm.panelVisible = true
    wrapper.vm.deleteVisible = true

    mockAdapter.onAny().reply(200, false)

    await wrapper.vm.deleteData()

    wrapper.vm.$nextTick(() => {
      expect(wrapper.vm.panelVisible).toBeTruthy()
      expect(wrapper.vm.deleteVisible).toBeTruthy()
      expect(spyPost).toHaveBeenCalledTimes(3)
    })
  })
})
