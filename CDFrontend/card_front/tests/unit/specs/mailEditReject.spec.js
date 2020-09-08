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

describe('MailEditPanel.vue', () => {
  const wrapper = shallowMount(MailEditPanel, {
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
    mockAdapter.onAny().reply(400, false).onAny()

    await wrapper.vm.submitForm()
  })

  it('Mail Edit Panel Resolves submitForm Result', () => {
    wrapper.vm.$nextTick(() => {
      expect(spyPost).toHaveBeenCalledTimes(1)
    })
  })
})
