const mockData = false

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

  it('Activity Entity Panel Nulls created getList watchList', async() => {
    expect(wrapper.vm.panelVisible).toBeFalsy()
    expect(wrapper.vm.list).toStrictEqual(null)
    expect(spyPost).toHaveBeenCalledTimes(1)
  })
})
