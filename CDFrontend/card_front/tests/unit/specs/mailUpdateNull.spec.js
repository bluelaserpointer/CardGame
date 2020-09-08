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

describe('MailUpdatePanel.vue', () => {
  const wrapper = shallowMount(MailUpdatePanel, {
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

  it('Mail Update Panel Nulls submitForm', async() => {
    wrapper.vm.postForm.title = 'TestTitle'
    wrapper.vm.postForm.content = 'TestContent'
    wrapper.vm.postForm.image_uri = undefined

    expect(spyPost).toHaveBeenCalledTimes(0)
    mockAdapter.onAny().reply(200, false)

    await wrapper.vm.submitForm()
  })

  it('Mail Update Panel Nulls submitForm Result', () => {
    wrapper.vm.$nextTick(() => {
      expect(spyPost).toHaveBeenCalledTimes(1)
    })
  })

  it('Mail Entity Panel Nulls confirmIdentity', async() => {
    wrapper.vm.confirmDelete = false
    mockAdapter.onAny().reply(200, false)

    await wrapper.vm.confirmIdentity()
  })

  it('Mail Entity Panel Nulls confirmIdentity Result', () => {
    wrapper.vm.$nextTick(() => {
      expect(wrapper.vm.confirmDelete).toBeFalsy()
      expect(spyPost).toHaveBeenCalledTimes(2)
    })
  })

  it('Mail Entity Panel Nulls deleteData', async() => {
    wrapper.vm.confirmDelete = true

    mockAdapter.onAny().reply(200, false)

    await wrapper.vm.deleteData()
  })

  it('Mail Entity Panel Nulls deleteData Result', () => {
    wrapper.vm.$nextTick(() => {
      expect(wrapper.vm.panelVisible).toBeTruthy()
      expect(wrapper.vm.deleteVisible).toBeTruthy()
      expect(spyPost).toHaveBeenCalledTimes(3)
    })
  })

  it('Mail Update Panel Nulls submitForm branchLast', async() => {
    wrapper.vm.postForm.image_uri = undefined
    wrapper.vm.postForm.title = undefined
    wrapper.vm.postForm.content = undefined

    mockAdapter.onAny().reply(200, false)

    await wrapper.vm.submitForm()
  })

  it('Mail Update Panel Nulls submitForm branchLast Result', () => {
    wrapper.vm.$nextTick(() => {
      expect(spyPost).toHaveBeenCalledTimes(4)
    })
  })
})
