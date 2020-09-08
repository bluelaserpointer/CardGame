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
import PlayerCardPanel from '@/components/table/playerCardPanel'
import Element from 'element-ui'

const localVue = createLocalVue()
localVue.use(Element)

describe('PlayerCardPanel.vue', () => {
  const wrapper = shallowMount(PlayerCardPanel,
    {
      localVue,
      stubs: {
        'el-form': validateStub
      }
    })

  const mockAdapter = new MockAdapter(axios)
  const spyPost = jest.spyOn(axios, 'post')

  mockAdapter.onAny().reply(400, mockData)

  it('Startup', async() => {
    await wrapper.vm.getList()
  })

  it('PlayerCard Entity Panel Rejects created', async() => {
    expect(wrapper.vm.list).toBeNull()

    expect(spyPost).toHaveBeenCalledTimes(1)
  })

  it('PlayerCard Entity Panel Rejects confirmIdentity', async() => {
    wrapper.vm.confirmDelete = false
    mockAdapter.onAny().reply(400, false)
    await wrapper.vm.confirmIdentity()
  })

  it('PlayerCard Entity Panel Rejects confirmIdentity result', async() => {
    expect(wrapper.vm.confirmDelete).toBeFalsy()
    expect(spyPost).toHaveBeenCalledTimes(2)
  })

  it('PlayerCard Entity Panel Rejects deleteData', async() => {
    wrapper.vm.confirmDelete = true
    wrapper.vm.panelVisible = true
    wrapper.vm.deleteVisible = true

    mockAdapter.onAny().reply(400, false)
    await wrapper.vm.deleteData()
  })

  it('PlayerCard Entity Panel Rejects deleteData result', async() => {
    expect(wrapper.vm.panelVisible).toBeTruthy()
    expect(wrapper.vm.deleteVisible).toBeTruthy()
    expect(spyPost).toHaveBeenCalledTimes(3)
  })

  it('PlayerCard Entity Panel Rejects createData', async() => {
    wrapper.vm.panelVisible = true
    mockAdapter.onAny().reply(400, false)
    wrapper.vm.createData('temp')
    await wrapper.vm.submitCreate()
  })

  it('PlayerCard Entity Panel Rejects updateData', async() => {
    wrapper.vm.panelVisible = true
    wrapper.vm.updateData('temp')
    await wrapper.vm.submitUpdate()
  })

  it('PlayerCard Entity Panel Rejects updateData result', () => {
    expect(spyPost).toHaveBeenCalledTimes(5)
  })
})
