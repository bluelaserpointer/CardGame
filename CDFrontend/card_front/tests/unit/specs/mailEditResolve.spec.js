import Vuex from 'vuex'

const validateStub = {
  render: () => {},
  methods: {
    setContent: () => {}
  }
}

const userData = [
  {
    userId: 1
  },
  {
    userId: 2
  }
]

jest.unmock('axios')
import axios from 'axios'
import MockAdapter from 'axios-mock-adapter'

import { createLocalVue, mount, shallowMount } from '@vue/test-utils'
import MailEditPanel from '@/components/article/MailEditPanel'
import Element from 'element-ui'

const localVue = createLocalVue()
localVue.use(Element)
localVue.use(Vuex)

const store = new Vuex.Store()

describe('MailEditPanel.vue', () => {
  const wrapper = shallowMount(MailEditPanel, {
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

  it('Mail Edit Panel Resolves created', async() => {
    expect(wrapper.vm.postForm.image_uri).toBe('')
    expect(wrapper.vm.postForm.title).toBe('')

    wrapper.vm.$nextTick(() => {
      expect(wrapper.vm.postForm.content).toBe('')
    })
  })

  it('Mail Edit Panel Resolves submitForm', async() => {
    wrapper.vm.postForm.title = 'TestTitle'
    wrapper.vm.postForm.content = 'TestContent'
    wrapper.vm.postForm.image_uri = undefined

    expect(spyPost).toHaveBeenCalledTimes(0)

    wrapper.vm.type = true
    mockAdapter.onAny().reply(200, { mailId: 1 }).onAny().reply(200, true)

    await wrapper.vm.submitForm()
  })

  it('Mail Edit Panel Resolves submitForm Result', () => {
    wrapper.vm.$nextTick(() => {
      expect(spyPost).toHaveBeenCalledTimes(1)
    })
  })

  it('Mail Edit Panel Resolves submitForm branch2', async() => {
    wrapper.vm.postForm.image_uri = undefined
    wrapper.vm.postForm.title = undefined
    wrapper.vm.postForm.content = undefined

    wrapper.vm.type = false
    mockAdapter.onAny().reply(200, { mailId: 1 }).onAny().reply(200, true)

    await wrapper.vm.submitForm()
  })

  it('Mail Edit Panel Resolves submitForm branch2 Result', () => {
    wrapper.vm.$nextTick(() => {
      expect(spyPost).toHaveBeenCalledTimes(4)
    })
  })

  it('Mail Edit Panel Resolves sendMail', async() => {
    wrapper.vm.type = false

    await wrapper.vm.sendMail(1)

    mockAdapter.onAny().reply(200, true)
  })

  it('Mail Edit Panel Resolves sendMail Result', () => {
    wrapper.vm.$nextTick(() => {
      expect(spyPost).toHaveBeenCalledTimes(5)
    })
  })

  it('Mail Edit Panel Resolves getList', async() => {
    mockAdapter.onAny().reply(200, false)
    await wrapper.vm.getList(1, wrapper.vm.listQuery.limit)

    wrapper.vm.$nextTick(() => {
      expect(spyPost).toHaveBeenCalledTimes(7)
    })

    mockAdapter.onAny().reply(200, userData)
    await wrapper.vm.getList(1, wrapper.vm.listQuery.limit)

    wrapper.vm.$nextTick(() => {
      expect(spyPost).toHaveBeenCalledTimes(8)
    })
  })

  it('Mail Edit Panel Resolves Rest', () => {
    wrapper.vm.handleSendUser({ userId: 1 })
    wrapper.vm.setTagsViewTitle()
    wrapper.vm.setPageTitle()
  })
})
