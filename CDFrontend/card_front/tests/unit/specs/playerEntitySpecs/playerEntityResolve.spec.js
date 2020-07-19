jest.mock('axios', () => ({
  get: jest.fn(() => Promise.resolve({ data: [{
      userId: 1,
      userName: '1',
      password: '1',
      phoneNumber: '1',
      email: '1',
      credits: 1,
      access: true,
      level: 1
    }]})),
  post: jest.fn(() => Promise.resolve({data: true}))
}));

import {createLocalVue, mount, shallowMount} from '@vue/test-utils'
import PlayerEntityPanel from '@/components/table/playerEntityPanel'
import Element from 'element-ui';
import axios from 'axios';

const localVue = createLocalVue();
localVue.use(Element);

describe('PlayerEntityPanel.vue', () => {
  const wrapper = mount(PlayerEntityPanel,
    {
      localVue
    });

  it('Player Entity Panel Resolves created',  async () => {
    expect(wrapper.vm.list).toStrictEqual([{
      userId: 1,
      userName: '1',
      password: '1',
      phoneNumber: '1',
      email: '1',
      credits: 1,
      access: true,
      level: 1
    }]);
    expect(axios.get).toHaveBeenCalledTimes(1);
    expect(axios.get).toBeCalledWith('http://localhost:8080/user/getAllUsers');
  });

  it('Player Entity Panel Resolves getList, watchList',  async () => {
    expect(axios.get).toHaveBeenCalledTimes(1);

    await wrapper.vm.getList(()=>{wrapper.vm.watchList()});
    expect(wrapper.vm.list).toStrictEqual([{
      userId: 1,
      userName: '1',
      password: '1',
      phoneNumber: '1',
      email: '1',
      credits: 1,
      access: true,
      level: 1
    }]);
    expect(axios.get).toHaveBeenCalledTimes(2);
    expect(axios.get).toBeCalledWith('http://localhost:8080/user/getAllUsers');
  });

  // it('Player Entity Panel Resolves confirmIdentity', async () => {
  //   wrapper.vm.confirmDelete = false;
  //
  //   await wrapper.vm.confirmIdentity();
  //
  //   expect(wrapper.vm.confirmDelete).toBeTruthy();
  // });

  // it('Player Entity Panel Resolves deleteData',  async () => {
  //   wrapper.vm.confirmDelete = true;
  //
  //   expect(axios.post).toHaveBeenCalledTimes(0);
  //   expect(axios.get).toHaveBeenCalledTimes(2);
  //   expect(wrapper.vm.confirmDelete).toBeTruthy();
  //
  //   await wrapper.vm.deleteData();
  //
  //   expect(wrapper.vm.panelVisible).toBeFalsy();
  //   expect(wrapper.vm.deleteVisible).toBeFalsy();
  //   expect(axios.post).toHaveBeenCalledTimes(1);
  //   expect(axios.get).toHaveBeenCalledTimes(3);
  //   expect(axios.get).toBeCalledWith('http://localhost:8080/user/getAllUsers');
  // });

  it('Player Entity Panel Resolves resetTemp', () => {
    wrapper.vm.temp = {
      userId: 1,
      userName: '1',
      password: '1',
      phoneNumber: '1',
      email: '1',
      credits: 1,
      access: true,
      level: 1
    };

    wrapper.vm.resetTemp();
    expect(wrapper.vm.temp).toStrictEqual(
      {
        userId: undefined,
        userName: '',
        password: '',
        phoneNumber: '',
        email: '',
        credits: 0,
        access: true,
        level: 0
      }
    );
  });

  it('Player Entity Panel Resolves handleCreate', () => {
    wrapper.vm.dialogStatus = 'xxx';
    wrapper.vm.panelVisible = false;

    wrapper.vm.handleCreate();
    expect(wrapper.vm.dialogStatus).toBe('create');
    expect(wrapper.vm.dialogStatus).toBeTruthy();
  });

  it('Player Entity Panel Resolves handleUpdate', () => {
    wrapper.vm.dialogStatus = 'xxx';
    wrapper.vm.panelVisible = false;

    wrapper.vm.handleUpdate();
    expect(wrapper.vm.dialogStatus).toBe('update');
    expect(wrapper.vm.dialogStatus).toBeTruthy();
  });

  it('Player Entity Panel Resolves createData', async () => {
    await wrapper.vm.createData();
    expect(wrapper.vm.panelVisible).toBeFalsy();
  });

  it('Player Entity Panel Resolves updateData', async () => {
    await wrapper.vm.updateData();
    expect(wrapper.vm.panelVisible).toBeFalsy();
  });

});
