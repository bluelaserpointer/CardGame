const mockData = { result: [{
  cardId: 0,
  cardName: '',
  rarity: '',
  healthPoint: 0,
  attack: 0,
  defense: 0,
  attackRange: 0,
  cd: 0,
  speed: 0,
  type: 1,
  cardDetails: {
    cardImg: 'testImg',
    cardDescription: 'testCardText',
    shortDescription: 'testShortText'
  }
}] }

const mockDataTrans = [{
  cardId: 0,
  cardName: '',
  rarity: '',
  healthPoint: 0,
  attack: 0,
  defense: 0,
  attackRange: 0,
  cd: 0,
  speed: 0,
  type: 1,
  cardImg: 'testImg',
  cardDescription: 'testCardText',
  shortDescription: 'testShortText',
  cardDetails: {
    cardImg: 'testImg',
    cardDescription: 'testCardText',
    shortDescription: 'testShortText'
  }
}]

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
import CardEntityPanel from '@/components/table/cardEntityPanel'
import Element from 'element-ui'

const localVue = createLocalVue()
localVue.use(Element)

describe('CardEntityPanel.vue', () => {
  const wrapper = shallowMount(CardEntityPanel,
    {
      localVue,
      stubs: {
        'el-form': validateStub
      }
    })

  const mockAdapter = new MockAdapter(axios)
  const spyPost = jest.spyOn(axios, 'post')

  mockAdapter.onPost('card/List').reply(200, mockData)

  it('Startup', async() => {
    await wrapper.vm.getList()
  })

  it('Card Entity Panel Resolves created', async() => {
    expect(wrapper.vm.list).toStrictEqual(mockDataTrans)

    expect(spyPost).toHaveBeenCalledTimes(1)
  })

  it('Card Entity Panel Resolves confirmIdentity', async() => {
    wrapper.vm.confirmDelete = false
    mockAdapter.onPost('user/confirmDelete').reply(200, true)
    await wrapper.vm.confirmIdentity()
  })

  it('Card Entity Panel Resolves confirmIdentity result', async() => {
    expect(wrapper.vm.confirmDelete).toBeTruthy()
    expect(spyPost).toHaveBeenCalledTimes(2)
  })

  it('Card Entity Panel Resolves deleteData', async() => {
    wrapper.vm.confirmDelete = true
    mockAdapter.onPost('card/deleteCard').reply(200, true)
    await wrapper.vm.deleteData()
  })

  it('Card Entity Panel Resolves deleteData result', async() => {
    expect(wrapper.vm.panelVisible).toBeFalsy()
    expect(wrapper.vm.deleteVisible).toBeFalsy()
    expect(spyPost).toHaveBeenCalledTimes(4)
  })

  it('Card Entity Panel Resolves resetTemp', () => {
    wrapper.vm.temp = {
      cardId: 1,
      cardName: '1',
      rarity: '1',
      healthPoint: 1,
      attack: 1,
      defense: 1,
      attackRange: 1,
      cd: 1,
      speed: 1,
      type: 1,
      cardImg: '1',
      cardDescription: '1',
      shortDescription: '1'
    }

    wrapper.vm.resetTemp()
    expect(wrapper.vm.temp).toStrictEqual(
      {
        cardId: undefined,
        cardName: 'New Card',
        rarity: 'N1',
        healthPoint: 0,
        attack: 0,
        defense: 0,
        attackRange: 0,
        cd: 0,
        speed: 0,
        type: 1,
        cardImg: '',
        cardDescription: 'No description yet.',
        shortDescription: 'No description yet.'
      }
    )
  })

  it('Card Entity Panel Resolves handleCreate', () => {
    wrapper.vm.dialogStatus = 'xxx'
    wrapper.vm.panelVisible = false

    wrapper.vm.handleCreate()
    expect(wrapper.vm.dialogStatus).toBe('create')
    expect(wrapper.vm.dialogStatus).toBeTruthy()
  })

  it('Card Entity Panel Resolves handleUpdate', () => {
    wrapper.vm.dialogStatus = 'xxx'
    wrapper.vm.panelVisible = false

    wrapper.vm.handleUpdate()
    expect(wrapper.vm.dialogStatus).toBe('update')
    expect(wrapper.vm.dialogStatus).toBeTruthy()
  })

  it('Card Entity Panel Resolves createData', async() => {
    wrapper.vm.panelVisible = true
    mockAdapter.onAny().reply(200, true)
    wrapper.vm.createData('temp')
    await wrapper.vm.submitCreate()
  })

  it('Card Entity Panel Resolves createData result', () => {
    expect(spyPost).toHaveBeenCalledTimes(6)
  })

  it('Card Entity Panel Resolves updateData', async() => {
    wrapper.vm.panelVisible = true
    wrapper.vm.updateData('temp')
    await wrapper.vm.submitUpdate()
  })

  it('Card Entity Panel Resolves updateData result', () => {
    expect(spyPost).toHaveBeenCalledTimes(8)
  })

  it('Card Entity Panel Resolves Rest', () => {
    wrapper.vm.handleFilter()
    wrapper.vm.sortChange({ prop: 'id' })
    wrapper.vm.sortByID('ascending')
  })
})
