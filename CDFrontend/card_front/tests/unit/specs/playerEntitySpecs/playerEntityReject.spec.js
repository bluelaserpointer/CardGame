jest.mock('axios', () => ({
  get: jest.fn(() => Promise.reject()),
  post: jest.fn(() => Promise.reject())
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

  it('Player Entity Panel Rejects created',  async () => {
    expect(wrapper.vm.list).toStrictEqual(null);
    expect(axios.get).toHaveBeenCalledTimes(1);
    expect(axios.get).toBeCalledWith('http://localhost:8080/user/getAllUsers');
  });

  it('Player Entity Panel Rejects getList, watchList',  async () => {
    expect(axios.get).toHaveBeenCalledTimes(1);

    await wrapper.vm.getList(()=>{wrapper.vm.watchList()});
    expect(wrapper.vm.list).toStrictEqual(null);
    expect(axios.get).toHaveBeenCalledTimes(2);
    expect(axios.get).toBeCalledWith('http://localhost:8080/user/getAllUsers');
  });

  it('Player Entity Panel Rejects createData', async () => {
    wrapper.vm.panelVisible = true;
    await wrapper.vm.createData();
    expect(wrapper.vm.panelVisible).toBeTruthy();
  });

  it('Player Entity Panel Rejects updateData', async () => {
    await wrapper.vm.updateData();
    expect(wrapper.vm.panelVisible).toBeTruthy();
  });

});
