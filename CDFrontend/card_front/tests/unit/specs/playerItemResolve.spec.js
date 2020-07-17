jest.mock('axios', () => ({
  get: jest.fn(() => Promise.resolve({ data: [{
      ownItemId: 0,
      userId: 0,
      itemId: 0,
      itemCount: 0,
      accquireDate: '2000-01-01 01:01:01'
    }]})),
  post: jest.fn(() => Promise.resolve({data: true}))
}));

import {createLocalVue, mount} from '@vue/test-utils'
import PlayerItemPanel from '@/components/table/playerItemPanel'
import Element from 'element-ui';
import axios from 'axios';

const localVue = createLocalVue();
localVue.use(Element);

describe('PlayerItemPanel.vue', () => {
  const wrapper = mount(PlayerItemPanel,
    {
      localVue
    });

  it('Player Item Panel calling created',  async () => {
    expect(wrapper.vm.list).toStrictEqual([{
      ownItemId: 0,
      userId: 0,
      itemId: 0,
      itemCount: 0,
      accquireDate: '2000-01-01 01:01:01'
    }]);
    expect(axios.get).toHaveBeenCalledTimes(1);
    expect(axios.get).toBeCalledWith('http://localhost:8080/ownItem/getAllOwnItems');
  });

  it('Player Card Panel rejecting confirmIdentity', async () => {
    wrapper.vm.confirmDelete = false;

    await wrapper.vm.confirmIdentity();

    expect(wrapper.vm.confirmDelete).toBeTruthy();
  });

  it('Player Item Panel calling deleteData',  async () => {
    wrapper.vm.confirmDelete = true;

    expect(axios.post).toHaveBeenCalledTimes(1);
    expect(axios.get).toHaveBeenCalledTimes(1);
    expect(wrapper.vm.confirmDelete).toBeTruthy();

    await wrapper.vm.deleteData();

    expect(wrapper.vm.panelVisible).toBeFalsy();
    expect(wrapper.vm.deleteVisible).toBeFalsy();
    expect(axios.post).toHaveBeenCalledTimes(2);
    expect(axios.get).toHaveBeenCalledTimes(2);
    expect(axios.get).toBeCalledWith("http://localhost:8080/ownItem/getAllOwnItems");
  });

  it('Player Item Panel calling resetTemp', () => {
    wrapper.vm.temp = {
      ownItemId: 0,
      userId: 0,
      itemId: 0,
      itemCount: 0,
      accquireDate: '2000-01-01 01:01:01'
    };

    wrapper.vm.resetTemp();
    expect(wrapper.vm.temp).toStrictEqual(
      {
        ownItemId: undefined,
        userId: undefined,
        itemId: undefined,
        itemCount: undefined,
        accquireDate: undefined
      }
    );
  });

  it('Player Item Panel calling handleCreate', () => {
    wrapper.vm.dialogStatus = 'xxx';
    wrapper.vm.panelVisible = false;

    wrapper.vm.handleCreate();
    expect(wrapper.vm.dialogStatus).toBe('create');
    expect(wrapper.vm.dialogStatus).toBeTruthy();
  });

  it('Player Item Panel calling handleUpdate', () => {
    wrapper.vm.dialogStatus = 'xxx';
    wrapper.vm.panelVisible = false;

    wrapper.vm.handleUpdate();
    expect(wrapper.vm.dialogStatus).toBe('update');
    expect(wrapper.vm.dialogStatus).toBeTruthy();
  });

  it('Player Item Panel calling createData', async () => {
    await wrapper.vm.createData();
    expect(wrapper.vm.panelVisible).toBeFalsy();
  });

  it('Player Item Panel calling updateData', async () => {
    await wrapper.vm.updateData();
    expect(wrapper.vm.panelVisible).toBeFalsy();
  });

});
