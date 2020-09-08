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
import ActivityEditPanel from '@/components/article/ActivityEditPanel'
import Element from 'element-ui'
import moment from 'moment'
import Vuex from 'vuex'

const localVue = createLocalVue()
localVue.use(Element)
localVue.use(Vuex)

const store = new Vuex.Store()

describe('ActivityEditPanel.vue', () => {
  const wrapper = shallowMount(ActivityEditPanel, {
    store,
    localVue,
    stubs: {
      'Tinymce': validateStub
    }
  })

  beforeEach(() => {
    wrapper.vm.$nextTick(() => {})
  })

  const mockAdapter = new MockAdapter(axios)
  const spyPost = jest.spyOn(axios, 'post')

  it('Activity Edit Panel Resolves created', async() => {
    expect(wrapper.vm.postForm.image_uri).toBe('')
    expect(wrapper.vm.postForm.title).toBe('')
    expect(wrapper.vm.limit).toBeFalsy()
    expect(wrapper.vm.displayTime).toBe(undefined)
    wrapper.vm.$nextTick(() => {
      expect(wrapper.vm.postForm.content).toBe('')
    })
  })

  it('Activity Edit Panel Resolves submitForm branch1', async() => {
    wrapper.vm.postForm.title = 'TestTitle'
    wrapper.vm.postForm.content = 'TestContent'
    wrapper.vm.postForm.image_uri = undefined

    wrapper.vm.limit = false

    mockAdapter.onAny().reply(200, true)

    await wrapper.vm.submitForm()
    wrapper.vm.$nextTick(() => {
      expect(spyPost).toHaveBeenCalledTimes(1)
    })
  })

  it('Activity Edit Panel Resolves submitForm branch2', async() => {
    wrapper.vm.postForm.title = 'TestTitle'
    wrapper.vm.postForm.content = 'TestContent'
    wrapper.vm.postForm.image_uri = undefined
    wrapper.vm.limit = true
    wrapper.vm.displayTime = '2000-01-01 00:00:00'

    await wrapper.vm.submitForm()
    wrapper.vm.$nextTick(() => {
      expect(spyPost).toHaveBeenCalledTimes(2)
    })
  })

  it('Activity Edit Panel Resolves submitForm branch3', async() => {
    wrapper.vm.postForm.title = 'TestTitle'
    wrapper.vm.postForm.content = 'TestContent'
    wrapper.vm.postForm.image_uri = undefined
    wrapper.vm.limit = true
    wrapper.vm.displayTime = undefined

    await wrapper.vm.submitForm()
    wrapper.vm.$nextTick(() => {
      expect(spyPost).toHaveBeenCalledTimes(3)
    })
  })

  it('Activity Edit Panel Resolves submitForm branch4', async() => {
    wrapper.vm.postForm.image_uri = undefined
    wrapper.vm.postForm.title = undefined
    wrapper.vm.postForm.content = undefined

    await wrapper.vm.submitForm()
    wrapper.vm.$nextTick(() => {
      expect(spyPost).toHaveBeenCalledTimes(4)
    })
  })

  it('Activity Edit Panel Resolves formatDate', () => {
    const date = new Date()
    const formatDateStr = moment(wrapper.vm.formatDate(date)).format('YYYY-MM-DD HH:mm:ss')
    expect(wrapper.vm.formatDate(date)).toBe(formatDateStr)
  })

  it('Activity Edit Resolves Rest', () => {
    wrapper.vm.setTagsViewTitle()
    wrapper.vm.setPageTitle()
  })
})
