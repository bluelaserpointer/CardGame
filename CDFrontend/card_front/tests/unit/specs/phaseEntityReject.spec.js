const mockData = { result: [{
  chapterId: 1,
  phaseNo: 1,
  phaseType: 1
}] }

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
import PhaseEntityPanel from '@/components/table/phaseEntityPanel'
import Element from 'element-ui'

const localVue = createLocalVue()
localVue.use(Element)

describe('PhaseEntityPanel.vue', () => {
  const wrapper = shallowMount(PhaseEntityPanel,
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

  it('Phase Entity Panel Resolves created', () => {
    wrapper.vm.$nextTick(() => {
      expect(spyPost).toHaveBeenCalledTimes(1)
    })
  })

  it('Phase Entity Panel Resolves confirmIdentity', async() => {
    wrapper.vm.confirmDelete = false
    mockAdapter.onAny().reply(400, true)
    await wrapper.vm.confirmIdentity()
  })

  it('Phase Entity Panel Resolves confirmIdentity result', () => {
    wrapper.vm.$nextTick(() => {
      expect(wrapper.vm.confirmDelete).toBeFalsy()
      expect(spyPost).toHaveBeenCalledTimes(2)
    })
  })

  it('Phase Entity Panel Resolves deleteData', async() => {
    wrapper.vm.confirmDelete = true
    mockAdapter.onAny().reply(400, true)
    await wrapper.vm.deleteData()
  })

  it('Phase Entity Panel Resolves deleteData result', () => {
    wrapper.vm.$nextTick(() => {
      expect(wrapper.vm.panelVisible).toBeTruthy()
      expect(wrapper.vm.deleteVisible).toBeTruthy()
      expect(spyPost).toHaveBeenCalledTimes(4)
    })
  })

  it('Phase Entity Panel Resolves handleCreate', () => {
    wrapper.vm.dialogStatus = 'xxx'
    wrapper.vm.panelVisible = false

    wrapper.vm.handleCreate()
    expect(wrapper.vm.dialogStatus).toBe('create')
  })

  it('Phase Entity Panel Resolves handleUpdate', () => {
    wrapper.vm.dialogStatus = 'xxx'
    wrapper.vm.panelVisible = false

    wrapper.vm.handleUpdate()
    expect(wrapper.vm.dialogStatus).toBe('update')
  })

  it('Phase Entity Panel Resolves createData', async() => {
    wrapper.vm.panelVisible = true
    mockAdapter.onAny().reply(400, true)
    wrapper.vm.createData('temp')
    await wrapper.vm.submitCreate()
  })

  it('Phase Entity Panel Resolves createData result', () => {
    wrapper.vm.$nextTick(() => {
      expect(spyPost).toHaveBeenCalledTimes(6)
    })
  })

  it('Phase Entity Panel Resolves updateData', async() => {
    wrapper.vm.panelVisible = true
    wrapper.vm.updateData('temp')
    await wrapper.vm.submitUpdate()
  })

  it('Phase Entity Panel Resolves updateData result', () => {
    wrapper.vm.$nextTick(() => {
      expect(spyPost).toHaveBeenCalledTimes(8)
    })
  })
})
