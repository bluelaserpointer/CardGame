jest.mock('axios', () => ({
  get: jest.fn(() => Promise.resolve({ data: [{
      itemId: 0,
      itemName: '',
      price: 0,
      itemDetails: {
        itemImg: 'testImg',
        itemDescription: 'testItemText',
      }
    }]})),
  post: jest.fn(() => Promise.resolve({data: true}))
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

  it('Item Entity Panel resolves Resolves created',  async () => {
    expect(wrapper.vm.list).toStrictEqual([{
      itemId: 0,
      itemName: '',
      price: 0,
      itemImg: 'testImg',
      itemDescription: 'testItemText',
      itemDetails: {
        itemImg: 'testImg',
        itemDescription: 'testItemText',
      }
    }]);
    expect(axios.get).toHaveBeenCalledTimes(1);
    expect(axios.get).toBeCalledWith('http://localhost:8080/item/getAllItems');
  });

  it('Item Entity Panel resolves Resolves getList, watchList',  async () => {
    expect(axios.get).toHaveBeenCalledTimes(1);

    await wrapper.vm.getList(()=>{wrapper.vm.watchList()});
    expect(wrapper.vm.list).toStrictEqual([{
      itemId: 0,
      itemName: '',
      price: 0,
      itemImg: 'testImg',
      itemDescription: 'testItemText',
      itemDetails: {
        itemImg: 'testImg',
        itemDescription: 'testItemText',
      }
    }]);
    expect(axios.get).toHaveBeenCalledTimes(2);
    expect(axios.get).toBeCalledWith('http://localhost:8080/item/getAllItems');
  });

  it('Item Entity Panel resolves Resolves confirmIdentity', async () => {
    wrapper.vm.confirmDelete = false;

    await wrapper.vm.confirmIdentity();

    expect(wrapper.vm.confirmDelete).toBeTruthy();
  });

  it('Item Entity Panel resolves Resolves deleteData',  async () => {
    wrapper.vm.confirmDelete = true;

    expect(axios.post).toHaveBeenCalledTimes(1);
    expect(axios.get).toHaveBeenCalledTimes(2);
    expect(wrapper.vm.confirmDelete).toBeTruthy();

    await wrapper.vm.deleteData();

    expect(wrapper.vm.panelVisible).toBeFalsy();
    expect(wrapper.vm.deleteVisible).toBeFalsy();
    expect(axios.post).toHaveBeenCalledTimes(2);
    expect(axios.get).toHaveBeenCalledTimes(3);
    expect(axios.get).toBeCalledWith("http://localhost:8080/item/getAllItems");
  });

  it('Item Entity Panel resolves Resolves resetTemp', () => {
    wrapper.vm.temp = {
      itemId: 1,
      itemName: '1',
      price: 1,
      itemImg: '1',
      itemDescription: '1',
    };

    wrapper.vm.resetTemp();
    expect(wrapper.vm.temp).toStrictEqual(
      {
        itemId: undefined,
        itemName: 'New Item',
        price: 999,
        itemImg: '',
        itemDescription: 'No description yet.'
      }
    );
  });

  it('Item Entity Panel resolves Resolves handleCreate', () => {
    wrapper.vm.dialogStatus = 'xxx';
    wrapper.vm.panelVisible = false;

    wrapper.vm.handleCreate();
    expect(wrapper.vm.dialogStatus).toBe('create');
    expect(wrapper.vm.dialogStatus).toBeTruthy();
  });

  it('Item Entity Panel resolves Resolves handleUpdate', () => {
    wrapper.vm.dialogStatus = 'xxx';
    wrapper.vm.panelVisible = false;

    wrapper.vm.handleUpdate();
    expect(wrapper.vm.dialogStatus).toBe('update');
    expect(wrapper.vm.dialogStatus).toBeTruthy();
  });

  it('Item Entity Panel resolves Resolves createData', async () => {
    await wrapper.vm.createData('temp');
    expect(wrapper.vm.panelVisible).toBeFalsy();
  });

  it('Item Entity Panel resolves Resolves updateData', async () => {
    await wrapper.vm.updateData('temp');
    expect(wrapper.vm.panelVisible).toBeFalsy();
  });

});
