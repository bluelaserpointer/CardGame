jest.mock('axios', () => ({
  get: jest.fn(() => Promise.resolve({data: null})),
  post: jest.fn(() => Promise.resolve({data: null}))
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

  it('Player Card Panel Nulls created',  async () => {
    expect(wrapper.vm.list).toStrictEqual(null);
    expect(axios.get).toHaveBeenCalledTimes(1);
    expect(axios.get).toBeCalledWith('http://localhost:8080/ownCard/getAllOwnCards');
  });

  it('Player Card Panel Nulls confirmIdentity', async () => {
    wrapper.vm.confirmDelete = false;

    await wrapper.vm.confirmIdentity();

    expect(wrapper.vm.confirmDelete).toBeFalsy();
  });

  it('Player Card Panel Nulls deleteData',  async () => {
    wrapper.vm.confirmDelete = true;
    wrapper.vm.panelVisible = true;
    wrapper.vm.deleteVisible = true;

    expect(axios.post).toHaveBeenCalledTimes(1);
    expect(axios.get).toHaveBeenCalledTimes(1);
    expect(wrapper.vm.confirmDelete).toBeTruthy();

    await wrapper.vm.deleteData();

    expect(wrapper.vm.panelVisible).toBeTruthy();
    expect(wrapper.vm.deleteVisible).toBeTruthy();
    expect(axios.post).toHaveBeenCalledTimes(2);
    expect(axios.get).toHaveBeenCalledTimes(1);
    expect(axios.get).toBeCalledWith("http://localhost:8080/ownCard/getAllOwnCards");
  });

  it('Player Card Panel Nulls createData', async () => {
    wrapper.vm.panelVisible = true;
    await wrapper.vm.createData();
    expect(wrapper.vm.panelVisible).toBeTruthy();
  });

  it('Player Card Panel Nulls updateData', async () => {
    wrapper.vm.panelVisible = true;
    await wrapper.vm.updateData();
    expect(wrapper.vm.panelVisible).toBeTruthy();
  });

});
