import Vuex from 'vuex'

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
import MailUpdatePanel from '@/components/article/MailUpdatePanel'
import Element from 'element-ui'

const localVue = createLocalVue()
localVue.use(Element)
localVue.use(Vuex)

const store = new Vuex.Store()

describe('MailUpdatePanel.vue', () => {
  const wrapper = shallowMount(MailUpdatePanel, {
    store,
    localVue,
    stubs: {
      'Tinymce': validateStub
    },
    propsData: {
      updateContent: {
        mail: 0,
        mailImg: '0',
        mailName: '0',
        mailDescription: '0'
      }
    }
  })

  beforeEach(() => {
    wrapper.vm.$nextTick(() => {})
  })

  const mockAdapter = new MockAdapter(axios)
  const spyPost = jest.spyOn(axios, 'post')

  it('Mail Update Panel Resolves created', async() => {
    expect(wrapper.vm.postForm.image_uri).toBe('0')
    expect(wrapper.vm.postForm.title).toBe('0')
    wrapper.vm.$nextTick(() => {
      expect(wrapper.vm.postForm.content).toBe('0')
    })
  })

  it('Mail Update Panel Resolves submitForm', async() => {
    wrapper.vm.postForm.title = 'TestTitle'
    wrapper.vm.postForm.content = 'TestContent'
    wrapper.vm.postForm.image_uri = undefined

    expect(spyPost).toHaveBeenCalledTimes(0)
    mockAdapter.onAny().reply(200, true)

    await wrapper.vm.submitForm()
  })

  it('Mail Update Panel Resolves submitForm Result', () => {
    wrapper.vm.$nextTick(() => {
      expect(spyPost).toHaveBeenCalledTimes(1)
    })
  })

  it('Mail Entity Panel Resolves confirmIdentity', async() => {
    wrapper.vm.confirmDelete = false
    mockAdapter.onAny().reply(200, true)

    await wrapper.vm.confirmIdentity()
  })

  it('Mail Entity Panel Resolves confirmIdentity Result', () => {
    wrapper.vm.$nextTick(() => {
      expect(wrapper.vm.confirmDelete).toBeTruthy()
      expect(spyPost).toHaveBeenCalledTimes(2)
    })
  })

  it('Mail Entity Panel Resolves deleteData', async() => {
    wrapper.vm.confirmDelete = true

    mockAdapter.onAny().reply(200, true)

    await wrapper.vm.deleteData()
  })

  it('Mail Entity Panel Resolves deleteData Result', () => {
    wrapper.vm.$nextTick(() => {
      expect(wrapper.vm.panelVisible).toBeFalsy()
      expect(wrapper.vm.deleteVisible).toBeFalsy()
      expect(spyPost).toHaveBeenCalledTimes(3)
    })
  })

  it('Mail Update Panel Resolves submitForm branchLast', async() => {
    wrapper.vm.postForm.image_uri = undefined
    wrapper.vm.postForm.title = undefined
    wrapper.vm.postForm.content = undefined

    mockAdapter.onAny().reply(200, true)

    await wrapper.vm.submitForm()
  })

  it('Mail Update Panel Resolves submitForm branchLast Result', () => {
    wrapper.vm.$nextTick(() => {
      expect(spyPost).toHaveBeenCalledTimes(4)
    })
  })

  it('Mail Update Panel Resolves submitForm Rest', () => {
    wrapper.vm.updateContent = { mailImg: 'Img', mailName: 'Name', mailDescription: 'Description' }
    wrapper.vm.deleteVisible = true
    wrapper.vm.$nextTick(() => {
      done()
    })

    wrapper.vm.updateContent = { mailImg: 'Img', mailName: 'Name' }
    wrapper.vm.$nextTick(() => {
      done()
    })

    wrapper.vm.setTagsViewTitle()
    wrapper.vm.setPageTitle()
  })
})
