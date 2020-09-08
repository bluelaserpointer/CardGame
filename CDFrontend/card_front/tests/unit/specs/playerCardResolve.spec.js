const mockData = { result: [{
  ownCardId: 0,
  userId: 0,
  cardId: 0,
  cardLevel: 0,
  cardCurExp: 0,
  cardLevelLimit: 0,
  repetitiveOwns: 0,
  accquireDate: '2000-01-01 01:01:01'
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

  mockAdapter.onAny().reply(200, mockData)

  it('Startup', async() => {
    await wrapper.vm.getList()
  })

  it('PlayerCard Entity Panel Resolves created', async() => {
    expect(spyPost).toHaveBeenCalledTimes(1)
  })

  it('PlayerCard Entity Panel Resolves confirmIdentity', async() => {
    wrapper.vm.confirmDelete = false
    mockAdapter.onAny().reply(200, true)
    await wrapper.vm.confirmIdentity()
  })

  it('PlayerCard Entity Panel Resolves confirmIdentity result', async() => {
    expect(wrapper.vm.confirmDelete).toBeTruthy()
    expect(spyPost).toHaveBeenCalledTimes(2)
  })

  it('PlayerCard Entity Panel Resolves deleteData', async() => {
    wrapper.vm.confirmDelete = true
    mockAdapter.onAny().reply(200, true)
    await wrapper.vm.deleteData()
  })

  it('PlayerCard Entity Panel Resolves deleteData result', async() => {
    expect(wrapper.vm.panelVisible).toBeFalsy()
    expect(wrapper.vm.deleteVisible).toBeFalsy()
    expect(spyPost).toHaveBeenCalledTimes(4)
  })

  it('PlayerCard Entity Panel Resolves resetTemp', () => {
    wrapper.vm.temp = null

    wrapper.vm.resetTemp()
    expect(wrapper.vm.temp).toStrictEqual(
      {
        ownCardId: undefined,
        userId: undefined,
        cardId: undefined,
        cardLevel: undefined,
        cardCurExp: undefined,
        cardLevelLimit: undefined,
        repetitiveOwns: undefined,
        accquireDate: undefined,
        enhancePoint: undefined,
        leftPoints: undefined,
        enhanceHP: undefined,
        enhanceAttack: undefined,
        enhanceDefense: undefined,
        enhanceAttackRange: undefined,
        enhanceCD: undefined,
        enhanceSpeed: undefined
      }
    )
  })

  it('PlayerCard Entity Panel Resolves handleCreate', () => {
    wrapper.vm.dialogStatus = 'xxx'
    wrapper.vm.panelVisible = false

    wrapper.vm.handleCreate()
    expect(wrapper.vm.dialogStatus).toBe('create')
    expect(wrapper.vm.dialogStatus).toBeTruthy()
  })

  it('PlayerCard Entity Panel Resolves handleUpdate', () => {
    wrapper.vm.dialogStatus = 'xxx'
    wrapper.vm.panelVisible = false

    wrapper.vm.handleUpdate()
    expect(wrapper.vm.dialogStatus).toBe('update')
    expect(wrapper.vm.dialogStatus).toBeTruthy()
  })

  it('PlayerCard Entity Panel Resolves createData', async() => {
    wrapper.vm.panelVisible = true
    mockAdapter.onAny().reply(200, true)
    wrapper.vm.createData('temp')
    await wrapper.vm.submitCreate()
  })

  it('PlayerCard Entity Panel Resolves createData result', () => {
    expect(spyPost).toHaveBeenCalledTimes(6)
  })

  it('PlayerCard Entity Panel Resolves updateData', async() => {
    wrapper.vm.panelVisible = true
    wrapper.vm.updateData('temp')
    await wrapper.vm.submitUpdate()
  })

  it('PlayerCard Entity Panel Resolves updateData result', () => {
    expect(spyPost).toHaveBeenCalledTimes(8)
  })

  it('PlayerCard Entity Panel Resolves Rest', () => {
    wrapper.vm.handleFilter()
    wrapper.vm.sortChange({ prop: 'id' })
    wrapper.vm.sortByID('ascending')
  })
})
