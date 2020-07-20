jest.mock('axios', () => ({
  get: jest.fn(() => Promise.reject()),
  post: jest.fn(() => Promise.reject())
}));

import {createLocalVue, mount, shallowMount} from '@vue/test-utils'
import ItemEntityPanel from '@/components/table/itemEntityPanel'
import Element from 'element-ui';
import axios from 'axios';

const localVue = createLocalVue();
localVue.use(Element);

describe('ItemEntityPanel.vue', () => {
  const wrapper = mount(ItemEntityPanel,
    {
      localVue
    });

  it('Item Entity Panel Rejects created',  async () => {
    expect(wrapper.vm.list).toStrictEqual(null);
    expect(axios.get).toHaveBeenCalledTimes(1);
    expect(axios.get).toBeCalledWith('http://localhost:8080/item/getAllItems');
  });

  it('Item Entity Panel Rejects getList, watchList',  async () => {
    expect(axios.get).toHaveBeenCalledTimes(1);

    await wrapper.vm.getList(()=>{wrapper.vm.watchList()});
    expect(wrapper.vm.list).toStrictEqual(null);
    expect(axios.get).toHaveBeenCalledTimes(2);
    expect(axios.get).toBeCalledWith('http://localhost:8080/item/getAllItems');
  });

  it('Item Entity Panel Rejects confirmIdentity', async () => {
    wrapper.vm.confirmDelete = false;

    await wrapper.vm.confirmIdentity();

    expect(wrapper.vm.confirmDelete).toBeFalsy();
  });

  it('Item Entity Panel Rejects deleteData',  async () => {
    wrapper.vm.confirmDelete = true;
    wrapper.vm.panelVisible = true;
    wrapper.vm.deleteVisible = true;

    expect(axios.post).toHaveBeenCalledTimes(1);
    expect(axios.get).toHaveBeenCalledTimes(2);
    expect(wrapper.vm.confirmDelete).toBeTruthy();

    await wrapper.vm.deleteData();

    expect(wrapper.vm.panelVisible).toBeTruthy();
    expect(wrapper.vm.deleteVisible).toBeTruthy();
    expect(axios.post).toHaveBeenCalledTimes(2);
    expect(axios.get).toHaveBeenCalledTimes(2);
    expect(axios.get).toBeCalledWith("http://localhost:8080/item/getAllItems");
  });

  it('Item Entity Panel Rejects createData', async () => {
    wrapper.vm.panelVisible = true;
    await wrapper.vm.createData();
    expect(wrapper.vm.panelVisible).toBeTruthy();
  });

  it('Item Entity Panel Rejects updateData', async () => {
    wrapper.vm.panelVisible = true;
    await wrapper.vm.updateData();
    expect(wrapper.vm.panelVisible).toBeTruthy();
  });

});
