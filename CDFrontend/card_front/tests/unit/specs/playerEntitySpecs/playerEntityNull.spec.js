jest.mock('axios', () => ({
  get: jest.fn(() => Promise.resolve({data: false})),
  post: jest.fn(() => Promise.resolve({data: false}))
}));

import {createLocalVue, mount, shallowMount} from '@vue/test-utils'
import PlayerEntityPanel from '@/components/table/playerEntityPanel'
import Element from 'element-ui';
import axios from 'axios';

const localVue = createLocalVue();
localVue.use(Element);

describe('PlayerEntityPanel.vue', () => {
  let wrapper;
  beforeEach(() => {
    wrapper = shallowMount(PlayerEntityPanel,
      {
        localVue
      });
  });

  it('Player Entity Panel Nulls created',  async () => {
    expect(wrapper.vm.list).toStrictEqual(null);
    expect(axios.get).toHaveBeenCalledTimes(1);
    expect(axios.get).toBeCalledWith('http://localhost:8080/user/getAllUsers');
  });

  it('Player Entity Panel Nulls getList, watchList',  async () => {
    expect(axios.get).toHaveBeenCalledTimes(1);

    await wrapper.vm.getList(()=>{wrapper.vm.watchList()});
    expect(wrapper.vm.list).toStrictEqual(null);
    expect(axios.get).toHaveBeenCalledTimes(2);
    expect(axios.get).toBeCalledWith('http://localhost:8080/user/getAllUsers');
  });

  it('Player Entity Panel Nulls createData', async () => {
    wrapper.vm.panelVisible = true;
    await wrapper.vm.createData('temp');
    expect(wrapper.vm.panelVisible).toBeTruthy();
  });

  it('Player Entity Panel Nulls updateData', async () => {
    await wrapper.vm.updateData('temp');
    expect(wrapper.vm.panelVisible).toBeTruthy();
  });

});
