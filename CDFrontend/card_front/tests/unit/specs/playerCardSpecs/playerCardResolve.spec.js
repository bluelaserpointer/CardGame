jest.mock('axios', () => ({
  get: jest.fn(() => Promise.resolve({ data: [{
      ownCardId: 0,
      userId: 0,
      cardId: 0,
      cardLevel: 0,
      cardCurExp: 0,
      cardLevelLimit: 0,
      repetitiveOwns: 0,
      accquireDate: '2000-01-01 01:01:01'
    }]}).catch()),
  post: jest.fn(() => Promise.resolve({data: true}))
}));

import {createLocalVue, mount} from '@vue/test-utils'
import PlayerCardPanel from '@/components/table/playerCardPanel'
import Element from 'element-ui';
import axios from 'axios';

const localVue = createLocalVue();
localVue.use(Element);

describe('PlayerCardPanel.vue', () => {
  const wrapper = mount(PlayerCardPanel,
    {
      localVue
    });

  it('Player Card Panel Resolves created',  async () => {
    expect(wrapper.vm.list).toStrictEqual([{
      ownCardId: 0,
      userId: 0,
      cardId: 0,
      cardLevel: 0,
      cardCurExp: 0,
      cardLevelLimit: 0,
      repetitiveOwns: 0,
      accquireDate: '2000-01-01 01:01:01'
    }]);
    expect(axios.get).toHaveBeenCalledTimes(1);
    expect(axios.get).toBeCalledWith('http://localhost:8080/ownCard/getAllOwnCards');
  });

  it('Player Card Panel Resolves confirmIdentity', async () => {
    wrapper.vm.confirmDelete = false;

    await wrapper.vm.confirmIdentity();

    expect(wrapper.vm.confirmDelete).toBeTruthy();
  });

  it('Player Card Panel Resolves deleteData',  async () => {
    wrapper.vm.confirmDelete = true;

    expect(axios.post).toHaveBeenCalledTimes(1);
    expect(axios.get).toHaveBeenCalledTimes(1);
    expect(wrapper.vm.confirmDelete).toBeTruthy();

    await wrapper.vm.deleteData();

    expect(wrapper.vm.panelVisible).toBeFalsy();
    expect(wrapper.vm.deleteVisible).toBeFalsy();
    expect(axios.post).toHaveBeenCalledTimes(2);
    expect(axios.get).toHaveBeenCalledTimes(2);
    expect(axios.get).toBeCalledWith("http://localhost:8080/ownCard/getAllOwnCards");
  });

  it('Player Card Panel Resolves resetTemp', () => {
    wrapper.vm.temp = {
      ownCardId: 0,
      userId: 0,
      cardId: 0,
      cardLevel: 0,
      cardCurExp: 0,
      cardLevelLimit: 0,
      repetitiveOwns: 0,
      accquireDate: '2000-01-01 01:01:01'
    };

    wrapper.vm.resetTemp();
    expect(wrapper.vm.temp).toStrictEqual(
      {
        ownCardId: undefined,
        userId: undefined,
        cardId: undefined,
        cardLevel: undefined,
        cardCurExp: undefined,
        cardLevelLimit: undefined,
        repetitiveOwns: undefined,
        accquireDate: undefined
      }
    );
  });

  it('Player Card Panel Resolves handleCreate', () => {
    wrapper.vm.dialogStatus = 'xxx';
    wrapper.vm.panelVisible = false;

    wrapper.vm.handleCreate();
    expect(wrapper.vm.dialogStatus).toBe('create');
    expect(wrapper.vm.dialogStatus).toBeTruthy();
  });

  it('Player Card Panel Resolves handleUpdate', () => {
    wrapper.vm.dialogStatus = 'xxx';
    wrapper.vm.panelVisible = false;

    wrapper.vm.handleUpdate();
    expect(wrapper.vm.dialogStatus).toBe('update');
    expect(wrapper.vm.dialogStatus).toBeTruthy();
  });

  it('Player Card Panel Resolves createData', async () => {
    await wrapper.vm.createData();
    expect(wrapper.vm.panelVisible).toBeFalsy();
  });

  it('Player Card Panel Resolves updateData', async () => {
    await wrapper.vm.updateData();
    expect(wrapper.vm.panelVisible).toBeFalsy();
  });

});
