jest.mock('axios', () => ({
  get: jest.fn(() => Promise.resolve({ data: [{
      cardId: 0,
      cardName: '',
      rarity: '',
      healthPoint: 0,
      attack: 0,
      defense: 0,
      attackRange: 0,
      cd: 0,
      speed: 0,
      cardDetails: {
        cardImg: 'testImg',
        cardDescription: 'testCardText',
        shortDescription: 'testShortText'
      }
    }]})),
  post: jest.fn(() => Promise.resolve({data: true}))
}));

import {createLocalVue, mount, shallowMount} from '@vue/test-utils'
import CardEntityPanel from '@/components/table/cardEntityPanel'
import Element from 'element-ui';
import flushPromises from "flush-promises";
import axios from 'axios';

const localVue = createLocalVue();
localVue.use(Element);

describe('CardEntityPanel.vue', () => {
  const wrapper = mount(CardEntityPanel,
    {
      localVue
    });

  it('Card Entity Panel calling created',  async () => {
    expect(wrapper.vm.list).toStrictEqual([{
      cardId: 0,
      cardName: '',
      rarity: '',
      healthPoint: 0,
      attack: 0,
      defense: 0,
      attackRange: 0,
      cd: 0,
      speed: 0,
      cardImg: 'testImg',
      cardDescription: 'testCardText',
      shortDescription: 'testShortText',
      cardDetails: {
        cardImg: 'testImg',
        cardDescription: 'testCardText',
        shortDescription: 'testShortText'
      }
    }]);
    expect(axios.get).toHaveBeenCalledTimes(1);
    expect(axios.get).toBeCalledWith('http://localhost:8080/card/getAllCards');
  });

  it('Card Entity Panel calling getList, watchList',  async () => {
    expect(axios.get).toHaveBeenCalledTimes(1);

    await wrapper.vm.getList(()=>{wrapper.vm.watchList()});
    expect(wrapper.vm.list).toStrictEqual([{
      cardId: 0,
      cardName: '',
      rarity: '',
      healthPoint: 0,
      attack: 0,
      defense: 0,
      attackRange: 0,
      cd: 0,
      speed: 0,
      cardImg: 'testImg',
      cardDescription: 'testCardText',
      shortDescription: 'testShortText',
      cardDetails: {
        cardImg: 'testImg',
        cardDescription: 'testCardText',
        shortDescription: 'testShortText'
      }
    }]);
    expect(axios.get).toHaveBeenCalledTimes(2);
    expect(axios.get).toBeCalledWith('http://localhost:8080/card/getAllCards');
  });

  it('Card Entity Panel calling deleteData',  async () => {
    wrapper.vm.confirmDelete = true;

    expect(axios.post).toHaveBeenCalledTimes(0);
    expect(axios.get).toHaveBeenCalledTimes(2);
    expect(wrapper.vm.confirmDelete).toBeTruthy();

    await wrapper.vm.deleteData();

    expect(wrapper.vm.panelVisible).toBeFalsy();
    expect(wrapper.vm.deleteVisible).toBeFalsy();
    expect(axios.post).toHaveBeenCalledTimes(1);
    expect(axios.get).toHaveBeenCalledTimes(3);
    expect(axios.get).toBeCalledWith("http://localhost:8080/card/getAllCards");
  });

  it('Card Entity Panel calling resetTemp', () => {
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
      cardImg: '1',
      cardDescription: '1',
      shortDescription: '1'
    };

    wrapper.vm.resetTemp();
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
        cardImg: '',
        cardDescription: 'No description yet.',
        shortDescription: 'No description yet.'
      }
    );
  });

  it('Card Entity Panel calling handleCreate', () => {
    wrapper.vm.dialogStatus = 'xxx';
    wrapper.vm.panelVisible = false;

    wrapper.vm.handleCreate();
    expect(wrapper.vm.dialogStatus).toBe('create');
    expect(wrapper.vm.dialogStatus).toBeTruthy();
  });

  it('Card Entity Panel calling handleUpdate', () => {
    wrapper.vm.dialogStatus = 'xxx';
    wrapper.vm.panelVisible = false;

    wrapper.vm.handleUpdate();
    expect(wrapper.vm.dialogStatus).toBe('update');
    expect(wrapper.vm.dialogStatus).toBeTruthy();
  });

  it('Card Entity Panel calling createData', async () => {
    await wrapper.vm.createData();
    expect(wrapper.vm.panelVisible).toBeFalsy();
  });

  it('Card Entity Panel calling updateData', async () => {
    await wrapper.vm.updateData();
    expect(wrapper.vm.panelVisible).toBeFalsy();
  });

});
